package de.sanandrew.mods.claysoldiers.network;

import com.google.common.collect.Maps;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.relauncher.Side;
import de.sanandrew.core.manpack.util.javatuples.Quintet;
import de.sanandrew.core.manpack.util.javatuples.Tuple;
import de.sanandrew.core.manpack.util.javatuples.Unit;
import de.sanandrew.mods.claysoldiers.network.packet.PacketParticleFX;
import de.sanandrew.mods.claysoldiers.network.packet.PacketSendEffectNBT;
import de.sanandrew.mods.claysoldiers.network.packet.PacketSendUpgradeNBT;
import de.sanandrew.mods.claysoldiers.network.packet.PacketSoldierRender;
import de.sanandrew.mods.claysoldiers.util.CSM_Main;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetHandler;
import org.apache.logging.log4j.Level;

import java.io.IOException;
import java.util.Map;

/**
 * @author SanAndreas
 * @version 1.0
 */
public final class PacketProcessor
{
    public static final short PKG_SOLDIER_RENDERS = 0;
    public static final short PKG_PARTICLES = 1;
    public static final short PKG_SOLDIER_EFFECT_NBT = 2;
    public static final short PKG_SOLDIER_UPGRADE_NBT = 3;

    private static final Map<Short, Class<? extends IPacket>> ID_TO_PACKET_MAP_ = Maps.newHashMap();

    public static void processPacket(ByteBuf data, Side side, INetHandler handler) {
        short packetId = -1;
        try( ByteBufInputStream bbis = new ByteBufInputStream(data) ) {
            packetId = bbis.readShort();
            if( ID_TO_PACKET_MAP_.containsKey(packetId) ) {
                IPacket pktInst = ID_TO_PACKET_MAP_.get(packetId).newInstance();
                pktInst.process(bbis, data, handler);
            }
        } catch( IOException ioe ) {
            FMLLog.log(CSM_Main.MOD_LOG, Level.ERROR, "The packet with the ID %d cannot be processed!", packetId);
            ioe.printStackTrace();
        } catch( IllegalAccessException | InstantiationException rex ) {
            FMLLog.log(CSM_Main.MOD_LOG, Level.ERROR, "The packet with the ID %d cannot be instantiated!", packetId);
            rex.printStackTrace();
        }
    }

    public static void sendToServer(short packedId, Tuple data) {
        sendPacketTo(packedId, PacketDirections.TO_SERVER, null, data);
    }

    public static void sendToAll(short packedId, Tuple data) {
        sendPacketTo(packedId, PacketDirections.TO_ALL, null, data);
    }

    public static void sendToPlayer(short packedId, EntityPlayerMP player, Tuple data) {
        sendPacketTo(packedId, PacketDirections.TO_PLAYER, Unit.with(player), data);
    }

    public static void sendToAllInDimension(short packedId, int dimensionId, Tuple data) {
        sendPacketTo(packedId, PacketDirections.TO_ALL_IN_DIMENSION, Unit.with(dimensionId), data);
    }

    public static void sendToAllAround(short packedId, int dimensionId, double x, double y, double z, double range, Tuple data) {
        sendPacketTo(packedId, PacketDirections.TO_ALL_IN_RANGE, Quintet.with(dimensionId, x, y, z, range), data);
    }

    private static void sendPacketTo(short packetId, PacketDirections direction, Tuple dirData, Tuple packetData) {
        try( ByteBufOutputStream bbos = new ByteBufOutputStream(Unpooled.buffer()) ) {
            if( ID_TO_PACKET_MAP_.containsKey(packetId) ) {
                bbos.writeShort(packetId);
                IPacket pktInst = ID_TO_PACKET_MAP_.get(packetId).newInstance();
                pktInst.writeData(bbos, packetData);
                FMLProxyPacket packet = new FMLProxyPacket(bbos.buffer(), CSM_Main.MOD_CHANNEL);
                switch( direction ) {
                    case TO_SERVER:
                        CSM_Main.channel.sendToServer(packet);
                        break;
                    case TO_ALL:
                        CSM_Main.channel.sendToAll(packet);
                        break;
                    case TO_PLAYER:
                        CSM_Main.channel.sendTo(packet, (EntityPlayerMP) dirData.getValue(0));
                        break;
                    case TO_ALL_IN_RANGE:
                        CSM_Main.channel.sendToAllAround(packet,
                                                         new NetworkRegistry.TargetPoint((int) dirData.getValue(0),
                                                                                         (double) dirData.getValue(1),
                                                                                         (double) dirData.getValue(2),
                                                                                         (double) dirData.getValue(3),
                                                                                         (double) dirData.getValue(4))
                        );
                        break;
                    case TO_ALL_IN_DIMENSION:
                        CSM_Main.channel.sendToDimension(packet, (int) dirData.getValue(0));
                        break;
                }
            }
        } catch( IOException ioe ) {
            FMLLog.log(CSM_Main.MOD_LOG, Level.ERROR, "The packet with the ID %d cannot be processed!", packetId);
            ioe.printStackTrace();
        } catch( IllegalAccessException | InstantiationException rex ) {
            FMLLog.log(CSM_Main.MOD_LOG, Level.ERROR, "The packet with the ID %d cannot be instantiated!", packetId);
            rex.printStackTrace();
        }
    }

    private static enum PacketDirections {
        TO_SERVER, TO_PLAYER, TO_ALL, TO_ALL_IN_RANGE, TO_ALL_IN_DIMENSION
    }

    static {
        ID_TO_PACKET_MAP_.put(PKG_PARTICLES, PacketParticleFX.class);
        ID_TO_PACKET_MAP_.put(PKG_SOLDIER_RENDERS, PacketSoldierRender.class);
        ID_TO_PACKET_MAP_.put(PKG_SOLDIER_EFFECT_NBT, PacketSendEffectNBT.class);
        ID_TO_PACKET_MAP_.put(PKG_SOLDIER_UPGRADE_NBT, PacketSendUpgradeNBT.class);
    }
}
