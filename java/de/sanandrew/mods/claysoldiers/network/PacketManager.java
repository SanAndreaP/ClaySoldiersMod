/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.network;

import de.sanandrew.core.manpack.network.NetworkManager;
import de.sanandrew.core.manpack.util.javatuples.Tuple;
import de.sanandrew.mods.claysoldiers.network.packet.PacketParticleFX;
import de.sanandrew.mods.claysoldiers.network.packet.PacketSendEffectNBT;
import de.sanandrew.mods.claysoldiers.network.packet.PacketSendUpgradeNBT;
import de.sanandrew.mods.claysoldiers.network.packet.PacketSoldierRender;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import net.minecraft.entity.player.EntityPlayerMP;

public final class PacketManager
{
    public static final short PKG_SOLDIER_RENDERS = 0;
    public static final short PKG_PARTICLES = 1;
    public static final short PKG_SOLDIER_EFFECT_NBT = 2;
    public static final short PKG_SOLDIER_UPGRADE_NBT = 3;

    public static void registerPackets() {
        NetworkManager.registerModHandler(ClaySoldiersMod.MOD_ID, ClaySoldiersMod.MOD_CHANNEL);

        NetworkManager.registerModPacketCls(ClaySoldiersMod.MOD_ID, PKG_SOLDIER_RENDERS, PacketSoldierRender.class);
        NetworkManager.registerModPacketCls(ClaySoldiersMod.MOD_ID, PKG_PARTICLES, PacketParticleFX.class);
        NetworkManager.registerModPacketCls(ClaySoldiersMod.MOD_ID, PKG_SOLDIER_EFFECT_NBT, PacketSendEffectNBT.class);
        NetworkManager.registerModPacketCls(ClaySoldiersMod.MOD_ID, PKG_SOLDIER_UPGRADE_NBT, PacketSendUpgradeNBT.class);
    }

    public static void sendToServer(short packet, Tuple data) {
        NetworkManager.sendToServer(ClaySoldiersMod.MOD_ID, packet, data);
    }

    public static void sendToAll(short packed, Tuple data) {
        NetworkManager.sendToAll(ClaySoldiersMod.MOD_ID, packed, data);
    }

    public static void sendToPlayer(short packed, EntityPlayerMP player, Tuple data) {
        NetworkManager.sendToPlayer(ClaySoldiersMod.MOD_ID, packed, player, data);
    }

    public static void sendToAllInDimension(short packed, int dimensionId, Tuple data) {
        NetworkManager.sendToAllInDimension(ClaySoldiersMod.MOD_ID, packed, dimensionId, data);
    }

    public static void sendToAllAround(short packed, int dimensionId, double x, double y, double z, double range, Tuple data) {
        NetworkManager.sendToAllAround(ClaySoldiersMod.MOD_ID, packed, dimensionId, x, y, z, range, data);
    }
}
