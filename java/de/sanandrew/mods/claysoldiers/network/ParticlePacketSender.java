/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.network;

import de.sanandrew.mods.claysoldiers.network.packet.PacketParticleFX;
import net.darkhax.bookshelf.lib.javatuples.*;
import de.sanandrew.mods.claysoldiers.network.packet.EnumParticleFx;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public final class ParticlePacketSender
{
    public static void sendSoldierDeathFx(double x, double y, double z, int dimension, String team) {
        PacketManager.sendToAllAround(new PacketParticleFX(EnumParticleFx.FX_SOLDIER_DEATH, x, y, z, Unit.with(team)), dimension, x, y, z, 64.0D);
    }

    public static void sendBreakFx(double x, double y, double z, int dimension, Item item) {
        PacketManager.sendToAllAround(new PacketParticleFX(EnumParticleFx.FX_BREAK, x, y, z, Unit.with(Item.itemRegistry.getNameForObject(item))), dimension, x, y, z, 64.0D);
    }

    public static void sendDiggingFx(double x, double y, double z, int dimension, Block block) {
        PacketManager.sendToAllAround(new PacketParticleFX(EnumParticleFx.FX_DIGGING, x, y, z, Unit.with(Block.blockRegistry.getNameForObject(block))), dimension, x, y, z, 64.0D);
    }

    public static void sendSpellFx(double x, double y, double z, int dimension, double red, double green, double blue) {
        PacketManager.sendToAllAround(new PacketParticleFX(EnumParticleFx.FX_SPELL, x, y, z, Triplet.with(red, green, blue)), dimension, x, y, z, 64.0D);
    }

    public static void sendCritFx(double x, double y, double z, int dimension) {
        PacketManager.sendToAllAround(new PacketParticleFX(EnumParticleFx.FX_CRIT, x, y, z), dimension, x, y, z, 64.0D);
    }

    public static void sendHorseDeathFx(double x, double y, double z, int dimension, byte type) {
        PacketManager.sendToAllAround(new PacketParticleFX(EnumParticleFx.FX_HORSE_DEATH, x, y, z, Unit.with(type)), dimension, x, y, z, 64.0D);
    }

    public static void sendBunnyDeathFx(double x, double y, double z, int dimension, byte type) {
        PacketManager.sendToAllAround(new PacketParticleFX(EnumParticleFx.FX_BUNNY_DEATH, x, y, z, Unit.with(type)), dimension, x, y, z, 64.0D);
    }

    public static void sendTurtleDeathFx(double x, double y, double z, int dimension, byte type) {
        PacketManager.sendToAllAround(new PacketParticleFX(EnumParticleFx.FX_TURTLE_DEATH, x, y, z, Unit.with(type)), dimension, x, y, z, 64.0D);
    }

    public static void sendShockwaveFx(double x, double y, double z, float yOff, int dimension) {
        PacketManager.sendToAllAround(new PacketParticleFX(EnumParticleFx.FX_SHOCKWAVE, x, y - 0.20000000298023224D - yOff, z), dimension, x, y, z, 64.0D);
    }

    public static void sendMagmafuseFx(double x, double y, double z, int dimension) {
        PacketManager.sendToAllAround(new PacketParticleFX(EnumParticleFx.FX_MAGMAFUSE, x, y, z), dimension, x, y, z, 64.0D);
    }
}
