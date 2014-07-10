package de.sanandrew.mods.claysoldiers.network.packet;

import de.sanandrew.core.manpack.util.javatuples.Quintet;
import de.sanandrew.core.manpack.util.javatuples.Tuple;
import de.sanandrew.mods.claysoldiers.network.IPacket;
import de.sanandrew.mods.claysoldiers.util.CSM_Main;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.minecraft.network.INetHandler;

import java.io.IOException;

/**
 * @author SanAndreas
 * @version 1.0
 */
public class PacketSoldierRender
    implements IPacket
{
    @Override
    public void process(ByteBufInputStream stream, INetHandler handler) throws IOException {
        CSM_Main.proxy.applySoldierRenderFlags(stream.readInt(), stream.readLong(), stream.readLong(), stream.readLong(), stream.readLong());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void writeData(ByteBufOutputStream stream, Tuple dataTuple) throws IOException {
        Quintet<Integer, Long, Long, Long, Long> data = (Quintet) dataTuple;
        stream.writeInt(data.getValue0());      // entity id
        stream.writeLong(data.getValue1());     // upgrade flags 1
        stream.writeLong(data.getValue2());     // upgrade flags 2
        stream.writeLong(data.getValue3());     // effect flags 1
        stream.writeLong(data.getValue4());     // effect flags 2
    }
}
