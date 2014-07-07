package de.sanandrew.mods.claysoldiers.network;

import de.sanandrew.core.manpack.util.javatuples.Tuple;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.minecraft.network.INetHandler;

import java.io.IOException;

/**
 * @author SanAndreas
 * @version 1.0
 */
public interface IPacket
{
    public void process(ByteBufInputStream stream, INetHandler handler) throws IOException;
    public void writeData(ByteBufOutputStream stream, Tuple dataTuple) throws IOException;
}
