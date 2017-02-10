/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.network;

import de.sanandrew.core.manpack.util.javatuples.Quartet;
import de.sanandrew.core.manpack.util.javatuples.Quintet;
import de.sanandrew.core.manpack.util.javatuples.Septet;
import de.sanandrew.mods.claysoldiers.network.packet.EnumParticleFx;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public final class ParticlePacketSender
{
    public static void sendSoldierDeathFx(double x, double y, double z, int dimension, String team) {
        PacketManager.sendToAllAround(PacketManager.PKG_PARTICLES, dimension, x, y, z, 64.0D, Quintet.with(EnumParticleFx.FX_SOLDIER_DEATH, x, y, z, team));
    }

    public static void sendBreakFx(double x, double y, double z, int dimension, Item item) {
        PacketManager.sendToAllAround(PacketManager.PKG_PARTICLES, dimension, x, y, z, 64.0D,
                                        Quintet.with(EnumParticleFx.FX_BREAK, x, y, z, Item.itemRegistry.getNameForObject(item))
        );
    }

    public static void sendDiggingFx(double x, double y, double z, int dimension, Block block) {
        PacketManager.sendToAllAround(PacketManager.PKG_PARTICLES, dimension, x, y, z, 64.0D,
                                        Quintet.with(EnumParticleFx.FX_DIGGING, x, y, z, Block.blockRegistry.getNameForObject(block))
        );
    }

    public static void sendSpellFx(double x, double y, double z, int dimension, double red, double green, double blue) {
        PacketManager.sendToAllAround(PacketManager.PKG_PARTICLES, dimension, x, y, z, 64.0D,
                                        Septet.with(EnumParticleFx.FX_SPELL, x, y, z, red, green, blue)
        );
    }

    public static void sendCritFx(double x, double y, double z, int dimension) {
        PacketManager.sendToAllAround(PacketManager.PKG_PARTICLES, dimension, x, y, z, 64.0D, Quartet.with(EnumParticleFx.FX_CRIT, x, y, z));
    }

    public static void sendHorseDeathFx(double x, double y, double z, int dimension, byte type) {
        PacketManager.sendToAllAround(PacketManager.PKG_PARTICLES, dimension, x, y, z, 64.0D, Quintet.with(EnumParticleFx.FX_HORSE_DEATH, x, y, z, type));
    }

    public static void sendBunnyDeathFx(double x, double y, double z, int dimension, byte type) {
        PacketManager.sendToAllAround(PacketManager.PKG_PARTICLES, dimension, x, y, z, 64.0D, Quintet.with(EnumParticleFx.FX_BUNNY_DEATH, x, y, z, type));
    }

    public static void sendTurtleDeathFx(double x, double y, double z, int dimension, byte type) {
        PacketManager.sendToAllAround(PacketManager.PKG_PARTICLES, dimension, x, y, z, 64.0D, Quintet.with(EnumParticleFx.FX_TURTLE_DEATH, x, y, z, type));
    }

    public static void sendShockwaveFx(double x, double y, double z, float yOff, int dimension) {
        PacketManager.sendToAllAround(PacketManager.PKG_PARTICLES, dimension, x, y, z, 64.0D,
                                        Quartet.with(EnumParticleFx.FX_SHOCKWAVE, x, y - 0.20000000298023224D - yOff, z)
        );
    }

    public static void sendMagmafuseFx(double x, double y, double z, int dimension) {
        PacketManager.sendToAllAround(PacketManager.PKG_PARTICLES, dimension, x, y, z, 64.0D, Quartet.with(EnumParticleFx.FX_MAGMAFUSE, x, y, z));
    }
}
