/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.network.packet;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import net.darkhax.bookshelf.common.network.AbstractMessage;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class PacketSoldierRender
        extends AbstractMessage<PacketSoldierRender>
{
    private int entityId;
    private long[] renderFlags;

    public PacketSoldierRender() {}

    public PacketSoldierRender(EntityClayMan clayMan) {
        this.entityId = clayMan.getEntityId();
        this.renderFlags = clayMan.getRenderFlags();
    }

    @Override
    public void handleClientMessage(PacketSoldierRender packet, EntityPlayer player) {
        ClaySoldiersMod.proxy.applySoldierRenderFlags(packet.entityId, packet.renderFlags[0], packet.renderFlags[1], packet.renderFlags[2], packet.renderFlags[3]);
    }

    @Override
    public void handleServerMessage(PacketSoldierRender packet, EntityPlayer player) {

    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.entityId = buf.readInt();
        this.renderFlags = new long[] {buf.readLong(), buf.readLong(), buf.readLong(), buf.readLong()};
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.entityId);
        buf.writeLong(this.renderFlags[0]);
        buf.writeLong(this.renderFlags[1]);
        buf.writeLong(this.renderFlags[2]);
        buf.writeLong(this.renderFlags[3]);
    }
}
