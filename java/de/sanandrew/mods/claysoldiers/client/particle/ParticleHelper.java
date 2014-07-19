/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.particle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.core.manpack.util.SAPUtils;
import de.sanandrew.core.manpack.util.javatuples.Quartet;
import de.sanandrew.core.manpack.util.javatuples.Sextet;
import de.sanandrew.core.manpack.util.javatuples.Triplet;
import de.sanandrew.core.manpack.util.javatuples.Tuple;
import de.sanandrew.mods.claysoldiers.entity.mounts.EnumHorseType;
import de.sanandrew.mods.claysoldiers.network.packet.PacketParticleFX;
import de.sanandrew.mods.claysoldiers.util.soldier.ClaymanTeam;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityBreakingFX;
import net.minecraft.client.particle.EntityCritFX;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.client.particle.EntitySpellParticleFX;
import net.minecraft.item.Item;

@SideOnly(Side.CLIENT)
public final class ParticleHelper
{
    @SuppressWarnings("unchecked")
    public static void spawnParticles(byte particleId, Tuple particleData) {
        Minecraft mc = Minecraft.getMinecraft();

        switch( particleId ) {                                                      // TODO: use lambdas (Java 8) instead of this when Java 7 gets its EOL!
            case PacketParticleFX.FX_BREAK:
                ParticleHelper.spawnBreakFx((Quartet) particleData, mc);
                break;
            case PacketParticleFX.FX_CRIT:
                ParticleHelper.spawnCritFx((Triplet) particleData, mc);
                break;
            case PacketParticleFX.FX_SOLDIER_DEATH:
                ParticleHelper.spawnSoldierDeathFx((Quartet) particleData, mc);
                break;
            case PacketParticleFX.FX_HORSE_DEATH:
                ParticleHelper.spawnHorseDeathFx((Quartet) particleData, mc);
                break;
            case PacketParticleFX.FX_DIGGING:
                ParticleHelper.spawnDiggingFx((Quartet) particleData, mc);
                break;
            case PacketParticleFX.FX_SPELL:
                ParticleHelper.spawnSpellFx((Sextet) particleData, mc);
                break;
        }
    }

    public static void spawnBreakFx(Quartet<Double, Double, Double, String> data, Minecraft mc) {
        Item item = (Item) Item.itemRegistry.getObject(data.getValue3());

        for (int i = 0; i < 5; i++) {
            EntityBreakingFX fx = new EntityBreakingFX(mc.theWorld, data.getValue0(), data.getValue1(), data.getValue2(), item);
            mc.effectRenderer.addEffect(fx);
        }
    }

    public static void spawnDiggingFx(Quartet<Double, Double, Double, String> data, Minecraft mc) {
        Block block = (Block) Block.blockRegistry.getObject(data.getValue3());

        for (int i = 0; i < 8; i++) {
            EntityDiggingFX fx = new EntityDiggingFX(mc.theWorld, data.getValue0(), data.getValue1(), data.getValue2(), SAPUtils.RANDOM.nextGaussian() * 0.15D,
                                                     SAPUtils.RANDOM.nextDouble() * 0.2D, SAPUtils.RANDOM.nextGaussian() * 0.15D, block, 0);
            mc.effectRenderer.addEffect(fx);
        }
    }

    public static void spawnCritFx(Triplet<Double, Double, Double> data, Minecraft mc) {
        for (int i = 0; i < 10; i++) {
            double motX = SAPUtils.RANDOM.nextDouble() - 0.5D;
            double motY = SAPUtils.RANDOM.nextDouble() * 0.5D;
            double motZ = SAPUtils.RANDOM.nextDouble() - 0.5D;
            EntityCritFX fx = new EntityCritFX(mc.theWorld, data.getValue0(), data.getValue1(), data.getValue2(), motX, motY, motZ);
            mc.effectRenderer.addEffect(fx);
        }
    }

    public static void spawnSoldierDeathFx(Quartet<Double, Double, Double, String> data, Minecraft mc) {
        ClaymanTeam team = ClaymanTeam.getTeamFromName(data.getValue3());

        for (int i = 0; i < 5; i++) {
            EntitySoldierDeathFX fx = new EntitySoldierDeathFX(mc.theWorld, data.getValue0(), data.getValue1(), data.getValue2(), team);
            mc.effectRenderer.addEffect(fx);
        }
    }

    public static void spawnHorseDeathFx(Quartet<Double, Double, Double, Byte> data, Minecraft mc) {
        EnumHorseType type = EnumHorseType.values[data.getValue3()];

        for (int i = 0; i < 5; i++) {
            EntityHorseDeathFX fx = new EntityHorseDeathFX(mc.theWorld, data.getValue0(), data.getValue1(), data.getValue2(), type);
            mc.effectRenderer.addEffect(fx);
        }
    }

    public static void spawnSpellFx(Sextet<Double, Double, Double, Double, Double, Double> data, Minecraft mc) {
        for (int i = 0; i < 4; i++) {
            EntitySpellParticleFX fx = new EntitySpellParticleFX(mc.theWorld, data.getValue0(), data.getValue1() - SAPUtils.RANDOM.nextDouble() * 0.2D,
                                                                 data.getValue2(), data.getValue3(), data.getValue4(), data.getValue5());
            mc.effectRenderer.addEffect(fx);
        }
    }
}
