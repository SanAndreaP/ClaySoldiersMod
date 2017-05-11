/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.network.packet;

import de.sanandrew.mods.claysoldiers.api.soldier.effect.ISoldierEffect;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.entity.EntityClaySoldier;
import de.sanandrew.mods.claysoldiers.registry.upgrade.UpgradeEntry;
import de.sanandrew.mods.claysoldiers.registry.upgrade.UpgradeRegistry;
import de.sanandrew.mods.sanlib.lib.network.AbstractMessage;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import de.sanandrew.mods.sanlib.lib.util.UuidUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PacketSyncEffects
        extends AbstractMessage<PacketSyncEffects>
{
    private int soldierId;
    private ISoldierEffect[] effects;
    private boolean add;
    private Map<ISoldierEffect, NBTTagCompound> effectNBT = new HashMap<>();

    public PacketSyncEffects() { }

    public PacketSyncEffects(EntityClaySoldier soldier, boolean add, ISoldierEffect... effects) {
        this.add = add;
        this.effects = effects;
        this.soldierId = soldier.getEntityId();
        if( this.add ) {
            Arrays.stream(this.effects).forEach(entry -> {
                if( entry.syncNbtData() ) {
                    this.effectNBT.put(entry, soldier.getEffectInstance(entry).getNbtData());
                }
            });
        }
    }

    @Override
    public void handleClientMessage(PacketSyncEffects pkt, EntityPlayer player) {
        Entity entity = player.world.getEntityByID(pkt.soldierId);
        if( entity instanceof EntityClaySoldier ) {
            pkt.applyUpgrades((EntityClaySoldier) entity);
        }
    }

    public void applyUpgrades(EntityClaySoldier soldier) {
        for( ISoldierEffect eff : this.effects ) {
            if( eff != null && this.add ) {
                ISoldierUpgradeInst inst = soldier.addUpgrade(eff., eff.type, eff.upgrade.getStacks()[0]);
                if( this.effectNBT.containsKey(eff) ) {
                    inst.setNbtData(this.effectNBT.get(eff));
                }
            } else {
                soldier.destroyUpgrade(eff.upgrade, eff.type, false);
            }
        }
    }

    @Override
    public void handleServerMessage(PacketSyncEffects pkt, EntityPlayer player) { }

    @Override
    public void fromBytes(ByteBuf buf) {
        try( ByteBufInputStream bis = new ByteBufInputStream(buf) ) {
            this.add = buf.readBoolean();
            this.soldierId = buf.readInt();
            this.effects = new UpgradeEntry[buf.readInt()];
            for( int i = 0; i < this.effects.length; i++ ) {
                String idStr = ByteBufUtils.readUTF8String(buf);
                if( UuidUtils.isStringUuid(idStr) ) {
                    this.effects[i] = new UpgradeEntry(UpgradeRegistry.INSTANCE.getUpgrade(UUID.fromString(idStr)), EnumUpgradeType.VALUES[buf.readByte()]);
                    if( this.add && this.effects[i].upgrade.syncNbtData() ) {
                        NBTTagCompound newNbt = new NBTTagCompound();
                        this.effects[i].upgrade.readSyncData(buf, newNbt);
                        this.effectNBT.put(this.effects[i], newNbt);
                    }
                }
            }
        } catch( IOException e ) {
            e.printStackTrace();
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(this.add);
        buf.writeInt(this.soldierId);
        buf.writeInt(this.effects.length);
        for( UpgradeEntry upg : effects ) {
            UUID id = UpgradeRegistry.INSTANCE.getId(upg.upgrade);
            ByteBufUtils.writeUTF8String(buf, MiscUtils.defIfNull(id, UuidUtils.EMPTY_UUID).toString());
            buf.writeByte(upg.type.ordinal());
            if( this.add && this.effectNBT.containsKey(upg) ) {
                upg.upgrade.writeSyncData(buf, this.effectNBT.get(upg));
            }
        }
    }
}
