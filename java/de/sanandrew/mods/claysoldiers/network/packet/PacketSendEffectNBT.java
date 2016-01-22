/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.network.packet;

import cpw.mods.fml.common.network.ByteBufUtils;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import net.darkhax.bookshelf.common.network.AbstractMessage;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class PacketSendEffectNBT
        extends AbstractMessage<PacketSendEffectNBT>
{
    private int entityId;
    private byte effectRenderId;
    private NBTTagCompound effectNbt;

    public PacketSendEffectNBT() {}

    public PacketSendEffectNBT(EntityClayMan clayMan, byte renderId, NBTTagCompound nbt) {
        this.entityId = clayMan.getEntityId();
        this.effectRenderId = renderId;
        this.effectNbt = nbt;
    }

    @Override
    public void handleClientMessage(PacketSendEffectNBT packet, EntityPlayer player) {
        ClaySoldiersMod.proxy.applyEffectNbt(packet.entityId, packet.effectRenderId, packet.effectNbt);
    }

    @Override
    public void handleServerMessage(PacketSendEffectNBT packet, EntityPlayer player) {

    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.entityId = buf.readInt();
        this.effectRenderId = buf.readByte();
        this.effectNbt = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.entityId);
        buf.writeByte(this.effectRenderId);
        ByteBufUtils.writeTag(buf, this.effectNbt);
    }
}
