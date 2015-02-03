/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.network.packet;

import cpw.mods.fml.common.network.ByteBufUtils;
import de.sanandrew.core.manpack.network.IPacket;
import de.sanandrew.core.manpack.util.javatuples.Triplet;
import de.sanandrew.core.manpack.util.javatuples.Tuple;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetHandler;

import java.io.IOException;

public class PacketSendEffectNBT
        implements IPacket
{
    @Override
    public void process(ByteBufInputStream stream, ByteBuf rawData, INetHandler handler) throws IOException {
        ClaySoldiersMod.proxy.applyEffectNbt(stream.readInt(), stream.readByte(), ByteBufUtils.readTag(rawData));
    }

    @Override
    public void writeData(ByteBufOutputStream stream, Tuple dataTuple) throws IOException {
        @SuppressWarnings("unchecked")
        Triplet<Integer, Byte, NBTTagCompound> data = (Triplet) dataTuple;

        stream.writeInt(data.getValue0());
        stream.writeByte(data.getValue1());
        ByteBufUtils.writeTag(stream.buffer(), data.getValue2());
    }
}
