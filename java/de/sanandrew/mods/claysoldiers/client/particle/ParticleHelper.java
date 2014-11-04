/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.particle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import de.sanandrew.core.manpack.util.javatuples.Quartet;
import de.sanandrew.core.manpack.util.javatuples.Sextet;
import de.sanandrew.core.manpack.util.javatuples.Triplet;
import de.sanandrew.core.manpack.util.javatuples.Tuple;
import de.sanandrew.mods.claysoldiers.network.packet.EnumParticleFx;
import de.sanandrew.mods.claysoldiers.util.mount.EnumBunnyType;
import de.sanandrew.mods.claysoldiers.util.mount.EnumHorseType;
import de.sanandrew.mods.claysoldiers.util.mount.EnumTurtleType;
import de.sanandrew.mods.claysoldiers.util.soldier.ClaymanTeam;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.*;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;

@SideOnly(Side.CLIENT)
public final class ParticleHelper
{
    @SuppressWarnings("unchecked")
    public static void spawnParticles(EnumParticleFx particleId, Tuple particleData) {
        Minecraft mc = Minecraft.getMinecraft();

        switch( particleId ) {                                                      // TODO: use lambdas (Java 8) instead of this when Java 7 gets its EOL!
            case FX_BREAK:
                spawnBreakFx((Quartet) particleData, mc);
                break;
            case FX_CRIT:
                spawnCritFx((Triplet) particleData, mc);
                break;
            case FX_SOLDIER_DEATH:
                spawnSoldierDeathFx((Quartet) particleData, mc);
                break;
            case FX_HORSE_DEATH:
                spawnHorseDeathFx((Quartet) particleData, mc);
                break;
            case FX_BUNNY_DEATH:
                spawnBunnyDeathFx((Quartet) particleData, mc);
                break;
            case FX_TURTLE_DEATH:
                spawnTurtleDeathFx((Quartet) particleData, mc);
                break;
            case FX_DIGGING:
                spawnDiggingFx((Quartet) particleData, mc);
                break;
            case FX_SPELL:
                spawnSpellFx((Sextet) particleData, mc);
                break;
            case FX_NEXUS:
                spawnNexusFx((Sextet) particleData, mc);
                break;
            case FX_SHOCKWAVE:
                spawnShockwaveFx((Triplet) particleData, mc);
                break;
            case FX_MAGMAFUSE:
                spawnMagmafuseFx((Triplet) particleData, mc);
                break;
        }
    }

    public static void spawnBreakFx(Quartet<Double, Double, Double, String> data, Minecraft mc) {
        Item item = (Item) Item.itemRegistry.getObject(data.getValue3());

        for( int i = 0; i < 5; i++ ) {
            EntityBreakingFX fx = new EntityBreakingFX(mc.theWorld, data.getValue0(), data.getValue1(), data.getValue2(), item);
            mc.effectRenderer.addEffect(fx);
        }
    }

    public static void spawnCritFx(Triplet<Double, Double, Double> data, Minecraft mc) {
        for( int i = 0; i < 10; i++ ) {
            double motX = SAPUtils.RNG.nextDouble() - 0.5D;
            double motY = SAPUtils.RNG.nextDouble() * 0.5D;
            double motZ = SAPUtils.RNG.nextDouble() - 0.5D;
            EntityCritFX fx = new EntityCritFX(mc.theWorld, data.getValue0(), data.getValue1(), data.getValue2(), motX, motY, motZ);
            mc.effectRenderer.addEffect(fx);
        }
    }

    public static void spawnSoldierDeathFx(Quartet<Double, Double, Double, String> data, Minecraft mc) {
        ClaymanTeam team = ClaymanTeam.getTeam(data.getValue3());

        for( int i = 0; i < 10; i++ ) {
            ParticleSoldierDeath fx = new ParticleSoldierDeath(mc.theWorld, data.getValue0(), data.getValue1(), data.getValue2(), team);
            mc.effectRenderer.addEffect(fx);
        }
    }

    public static void spawnHorseDeathFx(Quartet<Double, Double, Double, Byte> data, Minecraft mc) {
        EnumHorseType type = EnumHorseType.VALUES[data.getValue3()];

        for( int i = 0; i < 5; i++ ) {
            ParticleHorseDeath fx = new ParticleHorseDeath(mc.theWorld, data.getValue0(), data.getValue1(), data.getValue2(), type);
            mc.effectRenderer.addEffect(fx);
        }
    }

    public static void spawnBunnyDeathFx(Quartet<Double, Double, Double, Byte> data, Minecraft mc) {
        EnumBunnyType type = EnumBunnyType.VALUES[data.getValue3()];

        for( int i = 0; i < 5; i++ ) {
            ParticleBunnyDeath fx = new ParticleBunnyDeath(mc.theWorld, data.getValue0(), data.getValue1(), data.getValue2(), type);
            mc.effectRenderer.addEffect(fx);
        }
    }

    public static void spawnTurtleDeathFx(Quartet<Double, Double, Double, Byte> data, Minecraft mc) {
        EnumTurtleType type = EnumTurtleType.VALUES[data.getValue3()];

        for( int i = 0; i < 5; i++ ) {
            ParticleTurtleDeath fx = new ParticleTurtleDeath(mc.theWorld, data.getValue0(), data.getValue1(), data.getValue2(), type);
            mc.effectRenderer.addEffect(fx);
        }
    }

    public static void spawnDiggingFx(Quartet<Double, Double, Double, String> data, Minecraft mc) {
        Block block = (Block) Block.blockRegistry.getObject(data.getValue3());

        for( int i = 0; i < 8; i++ ) {
            EntityDiggingFX fx = new EntityDiggingFX(mc.theWorld, data.getValue0(), data.getValue1(), data.getValue2(), SAPUtils.RNG.nextGaussian() * 0.15D,
                                                     SAPUtils.RNG.nextDouble() * 0.2D, SAPUtils.RNG.nextGaussian() * 0.15D, block, 0
            );
            mc.effectRenderer.addEffect(fx);
        }
    }

    public static void spawnSpellFx(Sextet<Double, Double, Double, Double, Double, Double> data, Minecraft mc) {
        for( int i = 0; i < 4; i++ ) {
            EntitySpellParticleFX fx = new EntitySpellParticleFX(mc.theWorld, data.getValue0(), data.getValue1() - SAPUtils.RNG.nextDouble() * 0.2D,
                                                                 data.getValue2(), data.getValue3(), data.getValue4(), data.getValue5()
            );
            mc.effectRenderer.addEffect(fx);
        }
    }

    public static void spawnNexusFx(Sextet<Double, Double, Double, Float, Float, Float> data, Minecraft mc) {
        ParticleNexusFX fx = new ParticleNexusFX(mc.theWorld,
                                             data.getValue0() + 0.2F + SAPUtils.RNG.nextDouble() * 0.6F,
                                             data.getValue1(),
                                             data.getValue2() + 0.2F + SAPUtils.RNG.nextDouble() * 0.6F,
                                             0.1F + SAPUtils.RNG.nextFloat() * 0.2F,
                                             data.getValue3(),
                                             data.getValue4(),
                                             data.getValue5()
        );
        fx.motionY = 0.02F;
        mc.effectRenderer.addEffect(fx);
    }

    public static void spawnShockwaveFx(Triplet particleData, Minecraft mc) {
        double x = (double) particleData.getValue0();
        double y = (double) particleData.getValue1();
        double z = (double) particleData.getValue2();

        int blockX = MathHelper.floor_double(x);
        int blockY = MathHelper.floor_double(y);
        int blockZ = MathHelper.floor_double(z);

        Block block = mc.theWorld.getBlock(blockX, blockY, blockZ);

        if( block.getMaterial() != Material.air ) {
            double radius = 0.5D;
            int particleCount = (int) (150.0D * radius);

            for( int i2 = 0; i2 < particleCount; ++i2 ) {
                float rad = MathHelper.randomFloatClamp(SAPUtils.RNG, 0.0F, ((float) Math.PI * 2.0F));
                double multi = MathHelper.randomFloatClamp(SAPUtils.RNG, 0.75F, 1.0F);
                double partY = 0.2D + radius / 100.0D;
                double partX = (MathHelper.cos(rad) * 0.2F) * multi * multi * (radius + 0.2D);
                double partZ = (MathHelper.sin(rad) * 0.2F) * multi * multi * (radius + 0.2D);

                mc.theWorld.spawnParticle("blockdust_" + Block.getIdFromBlock(block) + '_' + mc.theWorld.getBlockMetadata(blockX, blockY, blockZ),
                                          x + 0.5D, y + 1.0D, z + 0.5D, partX, partY, partZ
                );
            }
        }
    }

    public static void spawnMagmafuseFx(Triplet<Double, Double, Double> data, Minecraft mc) {
        float red = SAPUtils.RNG.nextInt(2) - 0.001F;
        float green = SAPUtils.RNG.nextInt(2);

        if( red + green <= 0.5F ) {
            if( SAPUtils.RNG.nextBoolean() ) {
                green = 1.0F;
            } else {
                red = 1.0F;
            }
        }

        EntityReddustFX fx = new EntityReddustFX(mc.theWorld, data.getValue0(), data.getValue1(), data.getValue2(), red, green, 0.0F);
        mc.effectRenderer.addEffect(fx);
    }
}
