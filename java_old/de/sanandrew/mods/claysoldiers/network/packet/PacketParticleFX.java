/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.network.packet;

import de.sanandrew.core.manpack.network.IPacket;
import de.sanandrew.core.manpack.util.javatuples.Quartet;
import de.sanandrew.core.manpack.util.javatuples.Sextet;
import de.sanandrew.core.manpack.util.javatuples.Triplet;
import de.sanandrew.core.manpack.util.javatuples.Tuple;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.minecraft.network.INetHandler;

import java.io.IOException;

public class PacketParticleFX
        implements IPacket
{
    @Override
    public void process(ByteBufInputStream stream, ByteBuf rawData, INetHandler handler) throws IOException {
        EnumParticleFx fxType = EnumParticleFx.VALUES[stream.readByte()];
        switch( fxType ) {
            case FX_DIGGING:            // FALL_THROUGH
            case FX_BREAK:              // FALL_THROUGH
            case FX_SOLDIER_DEATH:
                ClaySoldiersMod.proxy.spawnParticles(fxType, Quartet.with(stream.readDouble(), stream.readDouble() + 0.5D, stream.readDouble(), stream.readUTF()));
                break;
            case FX_CRIT:
                ClaySoldiersMod.proxy.spawnParticles(fxType, Triplet.with(stream.readDouble(), stream.readDouble() + 0.1D, stream.readDouble()));
                break;
            case FX_HORSE_DEATH:        // FALL_THROUGH
            case FX_BUNNY_DEATH:        // FALL_THROUGH
            case FX_TURTLE_DEATH:
                ClaySoldiersMod.proxy.spawnParticles(fxType, Quartet.with(stream.readDouble(), stream.readDouble() + 0.5D, stream.readDouble(), stream.readByte()));
                break;
            case FX_SPELL:
                ClaySoldiersMod.proxy.spawnParticles(fxType, Sextet.with(stream.readDouble(), stream.readDouble() + 0.5D, stream.readDouble(),
                                                                    stream.readDouble(), stream.readDouble(), stream.readDouble())
                );
                break;
            case FX_SHOCKWAVE:          // FALL_THROUGH
            case FX_MAGMAFUSE:
                ClaySoldiersMod.proxy.spawnParticles(fxType, Triplet.with(stream.readDouble(), stream.readDouble(), stream.readDouble()));
                break;
        }
    }

    @Override
    public void writeData(ByteBufOutputStream stream, Tuple dataTuple) throws IOException {
        EnumParticleFx fxType = (EnumParticleFx) dataTuple.getValue(0);
        stream.writeByte(fxType.ordinalByte());
        switch( fxType ) {
            case FX_BREAK:          // FALL_THROUGH
            case FX_SOLDIER_DEATH:  // FALL_THROUGH
            case FX_DIGGING:
                stream.writeDouble((double) dataTuple.getValue(1));
                stream.writeDouble((double) dataTuple.getValue(2));
                stream.writeDouble((double) dataTuple.getValue(3));
                stream.writeUTF((String) dataTuple.getValue(4));
                break;
            case FX_CRIT:       // FALL_THROUGH
            case FX_SHOCKWAVE:  // FALL_THROUGH
            case FX_MAGMAFUSE:
                stream.writeDouble((double) dataTuple.getValue(1));
                stream.writeDouble((double) dataTuple.getValue(2));
                stream.writeDouble((double) dataTuple.getValue(3));
                break;
            case FX_HORSE_DEATH:    // FALL_THROUGH
            case FX_BUNNY_DEATH:    // FALL_THROUGH
            case FX_TURTLE_DEATH:
                stream.writeDouble((double) dataTuple.getValue(1));
                stream.writeDouble((double) dataTuple.getValue(2));
                stream.writeDouble((double) dataTuple.getValue(3));
                stream.writeByte((byte) dataTuple.getValue(4));
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
