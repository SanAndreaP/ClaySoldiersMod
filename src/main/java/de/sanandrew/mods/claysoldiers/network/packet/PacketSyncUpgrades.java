/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.network.packet;

import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.ISoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.entity.soldier.EntityClaySoldier;
import de.sanandrew.mods.claysoldiers.registry.upgrade.UpgradeRegistry;
import de.sanandrew.mods.claysoldiers.registry.upgrade.UpgradeEntry;
import de.sanandrew.mods.sanlib.lib.network.AbstractMessage;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import de.sanandrew.mods.sanlib.lib.util.UuidUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PacketSyncUpgrades
        extends AbstractMessage<PacketSyncUpgrades>
{
    private int soldierId;
    private UpgradeEntry[] upgrades;
    private boolean add;
    private final Map<UpgradeEntry, NBTTagCompound> upgradeNBT = new HashMap<>();

    public PacketSyncUpgrades() { }

    public PacketSyncUpgrades(EntityClaySoldier soldier, boolean add, UpgradeEntry... upgrades) {
        this.add = add;
        this.upgrades = upgrades;
        this.soldierId = soldier.getEntityId();
        if( this.add ) {
            Arrays.stream(this.upgrades).forEach(entry -> {
                if( entry.upgrade.syncNbtData() ) {
                    this.upgradeNBT.put(entry, soldier.getUpgradeInstance(entry).getNbtData());
                }
            });
        }
    }

    @Override
    public void handleClientMessage(PacketSyncUpgrades pkt, EntityPlayer player) {
        Entity entity = player.world.getEntityByID(pkt.soldierId);
        if( entity instanceof EntityClaySoldier ) {
            pkt.applyUpgrades((EntityClaySoldier) entity);
        }
    }

    public void applyUpgrades(EntityClaySoldier soldier) {
        for( UpgradeEntry upg : this.upgrades ) {
            if( upg != null ) {
                if (this.add) {
                    ISoldierUpgradeInst inst = soldier.addUpgrade(upg.upgrade, upg.type, upg.upgrade.getStacks()[0].copy());
                    if (this.upgradeNBT.containsKey(upg)) {
                        inst.setNbtData(this.upgradeNBT.get(upg));
                    }
                } else {
                    soldier.destroyUpgrade(upg.upgrade, upg.type, true);
                }
            }
        }
    }

    @Override
    public void handleServerMessage(PacketSyncUpgrades pkt, EntityPlayer player) { }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.add = buf.readBoolean();
        this.soldierId = buf.readInt();
        this.upgrades = new UpgradeEntry[buf.readInt()];
        for( int i = 0; i < this.upgrades.length; i++ ) {
            String idStr = ByteBufUtils.readUTF8String(buf);
            if( UuidUtils.isStringUuid(idStr) ) {
                this.upgrades[i] = new UpgradeEntry(UpgradeRegistry.INSTANCE.getUpgrade(UUID.fromString(idStr)), EnumUpgradeType.VALUES[buf.readByte()]);
                if( this.add && this.upgrades[i].upgrade.syncNbtData() ) {
                    NBTTagCompound newNbt = new NBTTagCompound();
                    this.upgrades[i].upgrade.readSyncData(buf, newNbt);
                    this.upgradeNBT.put(this.upgrades[i], newNbt);
                }
            }
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(this.add);
        buf.writeInt(this.soldierId);
        buf.writeInt(this.upgrades.length);
        for( UpgradeEntry upg :upgrades ) {
            UUID id = UpgradeRegistry.INSTANCE.getId(upg.upgrade);
            ByteBufUtils.writeUTF8String(buf, MiscUtils.defIfNull(id, UuidUtils.EMPTY_UUID).toString());
            buf.writeByte(upg.type.ordinal());
            if( this.add && this.upgradeNBT.containsKey(upg) ) {
                upg.upgrade.writeSyncData(buf, this.upgradeNBT.get(upg));
            }
        }
    }
}
