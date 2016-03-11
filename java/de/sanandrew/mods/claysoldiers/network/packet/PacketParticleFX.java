/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.network.packet;

import net.darkhax.bookshelf.common.network.AbstractMessage;
import net.darkhax.bookshelf.lib.javatuples.Quartet;
import net.darkhax.bookshelf.lib.javatuples.Sextet;
import net.darkhax.bookshelf.lib.javatuples.Triplet;
import net.darkhax.bookshelf.lib.javatuples.Tuple;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetHandler;
import org.apache.logging.log4j.Level;

import java.io.IOException;

public class PacketParticleFX
        extends AbstractMessage<PacketParticleFX>
{
    private EnumParticleFx type;
    private double posX;
    private double posY;
    private double posZ;
    private Tuple data;

    public PacketParticleFX() {}

    public PacketParticleFX(EnumParticleFx particleType, double x, double y, double z) {
        this.type = particleType;
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.data = null;
    }

    public PacketParticleFX(EnumParticleFx particleType, double x, double y, double z, Tuple particleData) {
        this(particleType, x, y, z);
        this.data = particleData;
    }

    @Override
    public void handleClientMessage(PacketParticleFX packet, EntityPlayer player) {
        if( packet.type == null ) {
            return;
        }

        switch( packet.type ) {
            case FX_DIGGING:            // FALL_THROUGH
            case FX_BREAK:              // FALL_THROUGH
            case FX_SOLDIER_DEATH:
                ClaySoldiersMod.proxy.spawnParticles(packet.type, packet.posX, packet.posY + 0.5D, packet.posZ, packet.data);
                break;
            case FX_CRIT:
                ClaySoldiersMod.proxy.spawnParticles(packet.type, packet.posX, packet.posY + 0.1D, packet.posZ, null);
                break;
            case FX_HORSE_DEATH:        // FALL_THROUGH
            case FX_BUNNY_DEATH:        // FALL_THROUGH
            case FX_TURTLE_DEATH:
                ClaySoldiersMod.proxy.spawnParticles(packet.type, packet.posX, packet.posY + 0.5D, packet.posZ, packet.data);
                break;
            case FX_SPELL:
                ClaySoldiersMod.proxy.spawnParticles(packet.type, packet.posX, packet.posY + 0.5D, packet.posZ, packet.data);
                break;
            case FX_SHOCKWAVE:          // FALL_THROUGH
            case FX_MAGMAFUSE:
                ClaySoldiersMod.proxy.spawnParticles(packet.type, packet.posX, packet.posY, packet.posZ, null);
                break;
        }
    }

    @Override
    public void handleServerMessage(PacketParticleFX packet, EntityPlayer player) {

    }

    @Override
    public void fromBytes(ByteBuf buf) {
        try( ByteBufInputStream bis = new ByteBufInputStream(buf) ) {
            this.type = EnumParticleFx.VALUES[bis.readByte()];
            this.posX = bis.readDouble();
            this.posY = bis.readDouble();
            this.posZ = bis.readDouble();
            if( buf.readBoolean() ) {
                this.data = Tuple.readFromByteBufStream(bis);
            } else {
                this.data = null;
            }
        } catch( IOException ex ) {
            ClaySoldiersMod.MOD_LOG.log(Level.WARN, String.format("An IOException occurred while processing particle data for type %1$s",
                                                                  this.type == null ? "UNKNOWN" : this.type.name()), ex);
            this.type = null;
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        try( ByteBufOutputStream bos = new ByteBufOutputStream(buf) ) {
            bos.writeByte(this.type.ordinalByte());
            bos.writeDouble(this.posX);
            bos.writeDouble(this.posY);
            bos.writeDouble(this.posZ);
            if( this.data != null ) {
                bos.writeBoolean(true);
                Tuple.writeToByteBufStream(this.data, bos);
            } else {
                bos.writeBoolean(false);
            }
        } catch( IOException ex ) {
            ClaySoldiersMod.MOD_LOG.log(Level.WARN, String.format("An IOException occurred while processing particle data for type %1$s",
                                                                  this.type == null ? "UNKNOWN" : this.type.name()), ex);
            this.type = null;
        }
    }
//    @Override
//    public void process(ByteBufInputStream stream, ByteBuf rawData, INetHandler handler) throws IOException {
//        EnumParticleFx fxType = EnumParticleFx.VALUES[stream.readByte()];
//        switch( fxType ) {
//            case FX_DIGGING:            // FALL_THROUGH
//            case FX_BREAK:              // FALL_THROUGH
//            case FX_SOLDIER_DEATH:
//                ClaySoldiersMod.proxy.spawnParticles(fxType, Quartet.with(stream.readDouble(), stream.readDouble() + 0.5D, stream.readDouble(), stream.readUTF()));
//                break;
//            case FX_CRIT:
//                ClaySoldiersMod.proxy.spawnParticles(fxType, Triplet.with(stream.readDouble(), stream.readDouble() + 0.1D, stream.readDouble()));
//                break;
//            case FX_HORSE_DEATH:        // FALL_THROUGH
//            case FX_BUNNY_DEATH:        // FALL_THROUGH
//            case FX_TURTLE_DEATH:
//                ClaySoldiersMod.proxy.spawnParticles(fxType, Quartet.with(stream.readDouble(), stream.readDouble() + 0.5D, stream.readDouble(), stream.readByte()));
//                break;
//            case FX_SPELL:
//                ClaySoldiersMod.proxy.spawnParticles(fxType, Sextet.with(stream.readDouble(), stream.readDouble() + 0.5D, stream.readDouble(),
//                                                                    stream.readDouble(), stream.readDouble(), stream.readDouble())
//                );
//                break;
//            case FX_SHOCKWAVE:          // FALL_THROUGH
//            case FX_MAGMAFUSE:
//                ClaySoldiersMod.proxy.spawnParticles(fxType, Triplet.with(stream.readDouble(), stream.readDouble(), stream.readDouble()));
//                break;
//        }
//    }
//
//    @Override
//    public void writeData(ByteBufOutputStream stream, Tuple dataTuple) throws IOException {
//        EnumParticleFx fxType = (EnumParticleFx) dataTuple.getValue(0);
//        stream.writeByte(fxType.ordinalByte());
//        switch( fxType ) {
//            case FX_BREAK:          // FALL_THROUGH
//            case FX_SOLDIER_DEATH:  // FALL_THROUGH
//            case FX_DIGGING:
//                stream.writeDouble((double) dataTuple.getValue(1));
//                stream.writeDouble((double) dataTuple.getValue(2));
//                stream.writeDouble((double) dataTuple.getValue(3));
//                stream.writeUTF((String) dataTuple.getValue(4));
//                break;
//            case FX_CRIT:       // FALL_THROUGH
//            case FX_SHOCKWAVE:  // FALL_THROUGH
//            case FX_MAGMAFUSE:
//                stream.writeDouble((double) dataTuple.getValue(1));
//                stream.writeDouble((double) dataTuple.getValue(2));
//                stream.writeDouble((double) dataTuple.getValue(3));
//                break;
//            case FX_HORSE_DEATH:    // FALL_THROUGH
//            case FX_BUNNY_DEATH:    // FALL_THROUGH
//            case FX_TURTLE_DEATH:
//                stream.writeDouble((double) dataTuple.getValue(1));
//                stream.writeDouble((double) dataTuple.getValue(2));
//                stream.writeDouble((double) dataTuple.getValue(3));
//                stream.writeByte((byte) dataTuple.getValue(4));
//                break;
//            case FX_SPELL:
//                stream.writeDouble((double) dataTuple.getValue(1));
//                stream.writeDouble((double) dataTuple.getValue(2));
//                stream.writeDouble((double) dataTuple.getValue(3));
//                stream.writeDouble((double) dataTuple.getValue(4));
//                stream.writeDouble((double) dataTuple.getValue(5));
//                stream.writeDouble((double) dataTuple.getValue(6));
//                break;
//        }
//    }
}
