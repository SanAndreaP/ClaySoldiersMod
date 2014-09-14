/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.network.packet;

import de.sanandrew.core.manpack.util.javatuples.Triplet;
import de.sanandrew.core.manpack.util.javatuples.Tuple;
import de.sanandrew.mods.claysoldiers.network.IPacket;
import de.sanandrew.mods.claysoldiers.util.CSM_Main;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.minecraft.network.INetHandler;

import java.io.IOException;

public class PacketSoldierRender
        implements IPacket
{
    @Override
    public void process(ByteBufInputStream stream, ByteBuf rawData, INetHandler handler) throws IOException {
        CSM_Main.proxy.applySoldierRenderFlags(stream.readInt(), stream.readLong(), stream.readLong(), stream.readLong(), stream.readLong());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void writeData(ByteBufOutputStream stream, Tuple dataTuple) throws IOException {
        Triplet<Integer, long[], long[]> data = (Triplet) dataTuple;
        stream.writeInt(data.getValue0());          // entity id
        stream.writeLong(data.getValue1()[0]);      // upgrade flags 1
        stream.writeLong(data.getValue1()[1]);      // upgrade flags 2
        stream.writeLong(data.getValue2()[0]);      // effect flags 1
        stream.writeLong(data.getValue2()[1]);      // effect flags 2
    }
}
