package de.sanandrew.mods.claysoldiers.network.packet;

import de.sanandrew.core.manpack.util.javatuples.Quartet;
import de.sanandrew.core.manpack.util.javatuples.Sextet;
import de.sanandrew.core.manpack.util.javatuples.Triplet;
import de.sanandrew.core.manpack.util.javatuples.Tuple;
import de.sanandrew.mods.claysoldiers.network.IPacket;
import de.sanandrew.mods.claysoldiers.util.CSM_Main;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.minecraft.network.INetHandler;

import java.io.IOException;

/**
 * @author SanAndreas
 * @version 1.0
 */
public class PacketParticleFX
    implements IPacket
{
    /** DataTuple: <br>&nbsp;&nbsp;posX (Double)<br>&nbsp;&nbsp;posY (Double)<br>&nbsp;&nbsp;posZ (Double)<br>&nbsp;&nbsp;item name (String) */
    public static final byte FX_BREAK = 0;
    /** DataTuple: <br>&nbsp;&nbsp;posX (Double)<br>&nbsp;&nbsp;posY (Double)<br>&nbsp;&nbsp;posZ (Double) */
    public static final byte FX_CRIT = 1;
    /** DataTuple: <br>&nbsp;&nbsp;posX (Double)<br>&nbsp;&nbsp;posY (Double)<br>&nbsp;&nbsp;posZ (Double)<br>&nbsp;&nbsp;team name (String) */
    public static final byte FX_SOLDIER_DEATH = 2;
    /** DataTuple: <br>&nbsp;&nbsp;posX (Double)<br>&nbsp;&nbsp;posY (Double)<br>&nbsp;&nbsp;posZ (Double)<br>&nbsp;&nbsp;horse type (Byte) */
    public static final byte FX_HORSE_DEATH = 3;
    /** DataTuple: <br>&nbsp;&nbsp;posX (Double)<br>&nbsp;&nbsp;posY (Double)<br>&nbsp;&nbsp;posZ (Double)<br>&nbsp;&nbsp;block name (String) */
    public static final byte FX_DIGGING = 4;
    /** DataTuple: <br>&nbsp;&nbsp;posX (Double)<br>&nbsp;&nbsp;posY (Double)<br>&nbsp;&nbsp;posZ (Double)
     *             <br>&nbsp;&nbsp;red (Double)<br>&nbsp;&nbsp;green (Double)<br>&nbsp;&nbsp;blue (Double) */
    public static final byte FX_SPELL = 5;
    /** DataTuple: <i>n/a</i> */
    public static final byte FX_NEXUS = 6;

    @Override
    public void process(ByteBufInputStream stream, ByteBuf rawData, INetHandler handler) throws IOException {
        switch( stream.readByte() ) {
            case FX_BREAK:
                CSM_Main.proxy.spawnParticles(FX_BREAK, Quartet.with(stream.readDouble(), stream.readDouble() + 0.5D, stream.readDouble(), stream.readUTF()));
                break;
            case FX_CRIT:
                CSM_Main.proxy.spawnParticles(FX_CRIT, Triplet.with(stream.readDouble(), stream.readDouble() + 0.1D, stream.readDouble()));
                break;
            case FX_SOLDIER_DEATH:
                CSM_Main.proxy.spawnParticles(FX_SOLDIER_DEATH, Quartet.with(stream.readDouble(), stream.readDouble() + 0.5D, stream.readDouble(), stream.readUTF()));
                break;
            case FX_HORSE_DEATH:
                CSM_Main.proxy.spawnParticles(FX_HORSE_DEATH, Quartet.with(stream.readDouble(), stream.readDouble() + 0.5D, stream.readDouble(), stream.readByte()));
                break;
            case FX_DIGGING:
                CSM_Main.proxy.spawnParticles(FX_DIGGING, Quartet.with(stream.readDouble(), stream.readDouble() + 0.5D, stream.readDouble(), stream.readUTF()));
                break;
            case FX_SPELL:
                CSM_Main.proxy.spawnParticles(FX_SPELL, Sextet.with(stream.readDouble(), stream.readDouble() + 0.5D, stream.readDouble(),
                                                                    stream.readDouble(), stream.readDouble(), stream.readDouble())
                );
                break;
            case FX_NEXUS:
                break;
        }
    }

    @Override
    public void writeData(ByteBufOutputStream stream, Tuple dataTuple) throws IOException {
        byte particleId = (byte) dataTuple.getValue(0);
        stream.writeByte(particleId);
        switch( particleId ) {
            case FX_BREAK:
                stream.writeDouble((double) dataTuple.getValue(1));
                stream.writeDouble((double) dataTuple.getValue(2));
                stream.writeDouble((double) dataTuple.getValue(3));
                stream.writeUTF((String) dataTuple.getValue(4));
                break;
            case FX_CRIT:
                stream.writeDouble((double) dataTuple.getValue(1));
                stream.writeDouble((double) dataTuple.getValue(2));
                stream.writeDouble((double) dataTuple.getValue(3));
                break;
            case FX_SOLDIER_DEATH:
                stream.writeDouble((double) dataTuple.getValue(1));
                stream.writeDouble((double) dataTuple.getValue(2));
                stream.writeDouble((double) dataTuple.getValue(3));
                stream.writeUTF((String) dataTuple.getValue(4));
                break;
            case FX_HORSE_DEATH:
                stream.writeDouble((double) dataTuple.getValue(1));
                stream.writeDouble((double) dataTuple.getValue(2));
                stream.writeDouble((double) dataTuple.getValue(3));
                stream.writeByte((byte) dataTuple.getValue(4));
                break;
            case FX_DIGGING:
                stream.writeDouble((double) dataTuple.getValue(1));
                stream.writeDouble((double) dataTuple.getValue(2));
                stream.writeDouble((double) dataTuple.getValue(3));
                stream.writeUTF((String) dataTuple.getValue(4));
                break;
            case FX_SPELL:
                stream.writeDouble((double) dataTuple.getValue(1));
                stream.writeDouble((double) dataTuple.getValue(2));
                stream.writeDouble((double) dataTuple.getValue(3));
                stream.writeDouble((double) dataTuple.getValue(4));
                stream.writeDouble((double) dataTuple.getValue(5));
                stream.writeDouble((double) dataTuple.getValue(6));
                break;
        }
    }
}
