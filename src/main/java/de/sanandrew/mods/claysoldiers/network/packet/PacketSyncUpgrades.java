/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.network.packet;

import de.sanandrew.mods.claysoldiers.api.soldier.ISoldierUpgrade;
import de.sanandrew.mods.claysoldiers.entity.EntityClaySoldier;
import de.sanandrew.mods.claysoldiers.registry.UpgradeRegistry;
import de.sanandrew.mods.sanlib.lib.network.AbstractMessage;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import de.sanandrew.mods.sanlib.lib.util.UuidUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import java.util.UUID;

public class PacketSyncUpgrades
        extends AbstractMessage<PacketSyncUpgrades>
{
    private int soldierId;
    private ISoldierUpgrade[] upgrades;
    private boolean add;

    public PacketSyncUpgrades() { }

    public PacketSyncUpgrades(EntityClaySoldier soldier, boolean add, ISoldierUpgrade... upgrades) {
        this.add = add;
        this.upgrades = upgrades;
        this.soldierId = soldier.getEntityId();
    }

    @Override
    public void handleClientMessage(PacketSyncUpgrades pkt, EntityPlayer player) {
        Entity entity = player.world.getEntityByID(pkt.soldierId);
        if( entity instanceof EntityClaySoldier ) {
            pkt.applyUpgrades((EntityClaySoldier) entity);
        }
    }

    public void applyUpgrades(EntityClaySoldier soldier) {
        for( ISoldierUpgrade upg : this.upgrades ) {
            if( upg != null && this.add ) {
                soldier.addUpgrade(upg, upg.getStack());
            } else {
                soldier.destroyUpgrade(upg);
            }
        }
    }

    @Override
    public void handleServerMessage(PacketSyncUpgrades pkt, EntityPlayer player) { }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.add = buf.readBoolean();
        this.soldierId = buf.readInt();
        this.upgrades = new ISoldierUpgrade[buf.readInt()];
        for( int i = 0; i < this.upgrades.length; i++ ) {
            String idStr = ByteBufUtils.readUTF8String(buf);
            if( UuidUtils.isStringUuid(idStr) ) {
                this.upgrades[i] = UpgradeRegistry.INSTANCE.getUpgrade(UUID.fromString(idStr));
            }
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(this.add);
        buf.writeInt(this.soldierId);
        buf.writeInt(this.upgrades.length);
        for( ISoldierUpgrade upg : upgrades ) {
            UUID id = UpgradeRegistry.INSTANCE.getId(upg);
            ByteBufUtils.writeUTF8String(buf, MiscUtils.defIfNull(id, UuidUtils.EMPTY_UUID).toString());
        }
    }
}
