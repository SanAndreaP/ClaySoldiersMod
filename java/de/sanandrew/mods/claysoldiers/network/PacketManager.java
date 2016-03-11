/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import net.darkhax.bookshelf.common.network.AbstractMessage;
import net.darkhax.bookshelf.lib.javatuples.Tuple;
import de.sanandrew.mods.claysoldiers.network.packet.PacketParticleFX;
import de.sanandrew.mods.claysoldiers.network.packet.PacketSendEffectNBT;
import de.sanandrew.mods.claysoldiers.network.packet.PacketSendUpgradeNBT;
import de.sanandrew.mods.claysoldiers.network.packet.PacketSoldierRender;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import net.darkhax.bookshelf.lib.util.Utilities;
import net.minecraft.entity.player.EntityPlayerMP;

public final class PacketManager
{
    public static SimpleNetworkWrapper network;

//    public static final short PKG_SOLDIER_RENDERS = 0;
//    public static final short PKG_PARTICLES = 1;
//    public static final short PKG_SOLDIER_EFFECT_NBT = 2;
//    public static final short PKG_SOLDIER_UPGRADE_NBT = 3;

    public static void registerPackets() {
        network = NetworkRegistry.INSTANCE.newSimpleChannel(ClaySoldiersMod.MOD_CHANNEL);

        Utilities.registerMessage(network, PacketSoldierRender.class, 0, Side.CLIENT);
        Utilities.registerMessage(network, PacketParticleFX.class, 1, Side.CLIENT);
        Utilities.registerMessage(network, PacketSendEffectNBT.class, 2, Side.CLIENT);
        Utilities.registerMessage(network, PacketSendUpgradeNBT.class, 3, Side.CLIENT);
//        NetworkManager.registerModHandler(ClaySoldiersMod.MOD_ID, ClaySoldiersMod.MOD_CHANNEL);
//
//        NetworkManager.registerModPacketCls(ClaySoldiersMod.MOD_ID, PKG_SOLDIER_RENDERS, PacketSoldierRender.class);
//        NetworkManager.registerModPacketCls(ClaySoldiersMod.MOD_ID, PKG_PARTICLES, PacketParticleFX.class);
//        NetworkManager.registerModPacketCls(ClaySoldiersMod.MOD_ID, PKG_SOLDIER_EFFECT_NBT, PacketSendEffectNBT.class);
//        NetworkManager.registerModPacketCls(ClaySoldiersMod.MOD_ID, PKG_SOLDIER_UPGRADE_NBT, PacketSendUpgradeNBT.class);
    }

//    public static void sendToServer(short packet, Tuple data) {
//        NetworkManager.sendToServer(ClaySoldiersMod.MOD_ID, packet, data);
//    }
//
//    public static void sendToAll(short packed, Tuple data) {
//        NetworkManager.sendToAll(ClaySoldiersMod.MOD_ID, packed, data);
//    }
//
//    public static void sendToPlayer(short packed, EntityPlayerMP player, Tuple data) {
//        NetworkManager.sendToPlayer(ClaySoldiersMod.MOD_ID, packed, player, data);
//    }
//
//    public static void sendToAllInDimension(short packed, int dimensionId, Tuple data) {
//        NetworkManager.sendToAllInDimension(ClaySoldiersMod.MOD_ID, packed, dimensionId, data);
//    }
//
    public static void sendToAllAround(AbstractMessage<?> packet, int dimensionId, double x, double y, double z, double range) {
        network.sendToAllAround(packet, new NetworkRegistry.TargetPoint(dimensionId, x, y, z, range));
    }
}
