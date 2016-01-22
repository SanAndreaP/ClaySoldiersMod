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
import net.darkhax.bookshelf.lib.javatuples.Triplet;
import net.darkhax.bookshelf.lib.javatuples.Tuple;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetHandler;

import java.io.IOException;

public class PacketSendUpgradeNBT
        extends AbstractMessage<PacketSendUpgradeNBT>
{
    private int entityId;
    private byte upgRenderId;
    private NBTTagCompound upgNbt;

    public PacketSendUpgradeNBT() {}

    public PacketSendUpgradeNBT(EntityClayMan clayMan, byte renderId, NBTTagCompound nbt) {
        this.entityId = clayMan.getEntityId();
        this.upgRenderId = renderId;
        this.upgNbt = nbt;
    }

    @Override
    public void handleClientMessage(PacketSendUpgradeNBT packet, EntityPlayer player) {
        ClaySoldiersMod.proxy.applyUpgradeNbt(packet.entityId, packet.upgRenderId, packet.upgNbt);
    }

    @Override
    public void handleServerMessage(PacketSendUpgradeNBT packet, EntityPlayer player) {

    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.entityId = buf.readInt();
        this.upgRenderId = buf.readByte();
        this.upgNbt = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.entityId);
        buf.writeByte(this.upgRenderId);
        ByteBufUtils.writeTag(buf, this.upgNbt);
    }
}
