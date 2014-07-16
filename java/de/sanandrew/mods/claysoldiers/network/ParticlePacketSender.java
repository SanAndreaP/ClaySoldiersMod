/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.network;

import de.sanandrew.core.manpack.util.javatuples.Quartet;
import de.sanandrew.core.manpack.util.javatuples.Quintet;
import de.sanandrew.mods.claysoldiers.network.packet.PacketParticleFX;
import net.minecraft.item.Item;

public final class ParticlePacketSender
{
    public static void sendSoldierDeathFx(double x, double y, double z, int dimension, String team) {
        PacketProcessor.sendToAllAround(PacketProcessor.PKG_PARTICLES, dimension, x, y, z, 64.0D, Quintet.with(PacketParticleFX.FX_SOLDIER_DEATH, x, y, z, team));
    }

    public static void sendBreakFx(double x, double y, double z, int dimension, Item item) {
        PacketProcessor.sendToAllAround(PacketProcessor.PKG_PARTICLES, dimension, x, y, z, 64.0D,
                                        Quintet.with(PacketParticleFX.FX_BREAK, x, y, z, Item.itemRegistry.getNameForObject(item))
        );
    }

    public static void sendCritFx(double x, double y, double z, int dimension) {
        PacketProcessor.sendToAllAround(PacketProcessor.PKG_PARTICLES, dimension, x, y, z, 64.0D, Quartet.with(PacketParticleFX.FX_CRIT, x, y, z));
    }

    public static void sendHorseDeathFx(double x, double y, double z, int dimension, byte type) {
        PacketProcessor.sendToAllAround(PacketProcessor.PKG_PARTICLES, dimension, x, y, z, 64.0D, Quintet.with(PacketParticleFX.FX_HORSE_DEATH, x, y, z, type));
    }
}
