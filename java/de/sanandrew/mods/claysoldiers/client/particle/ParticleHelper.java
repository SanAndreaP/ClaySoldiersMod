/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.particle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import net.darkhax.bookshelf.lib.javatuples.*;
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
    public static void spawnParticles(EnumParticleFx particleId, double posX, double posY, double posZ, Tuple particleData) {
        Minecraft mc = Minecraft.getMinecraft();

        switch( particleId ) {                                                      // TODO: use lambdas (Java 8) instead of this when Java 7 gets its EOL!
            case FX_BREAK:
                spawnBreakFx(posX, posY, posZ, (Unit) particleData, mc);
                break;
            case FX_CRIT:
                spawnCritFx(posX, posY, posZ, mc);
                break;
            case FX_SOLDIER_DEATH:
                spawnSoldierDeathFx(posX, posY, posZ, (Unit) particleData, mc);
                break;
            case FX_HORSE_DEATH:
                spawnHorseDeathFx(posX, posY, posZ, (Unit) particleData, mc);
                break;
            case FX_BUNNY_DEATH:
                spawnBunnyDeathFx(posX, posY, posZ, (Unit) particleData, mc);
                break;
            case FX_TURTLE_DEATH:
                spawnTurtleDeathFx(posX, posY, posZ, (Unit) particleData, mc);
                break;
            case FX_DIGGING:
                spawnDiggingFx(posX, posY, posZ, (Unit) particleData, mc);
                break;
            case FX_SPELL:
                spawnSpellFx(posX, posY, posZ, (Triplet) particleData, mc);
                break;
            case FX_NEXUS:
                spawnNexusFx(posX, posY, posZ, (Triplet) particleData, mc);
                break;
            case FX_SHOCKWAVE:
                spawnShockwaveFx(posX, posY, posZ, mc);
                break;
            case FX_MAGMAFUSE:
                spawnMagmafuseFx(posX, posY, posZ, mc);
                break;
        }
    }

    public static void spawnBreakFx(double posX, double posY, double posZ, Unit<String> data, Minecraft mc) {
        Item item = (Item) Item.itemRegistry.getObject(data.getValue0());

        for( int i = 0; i < 5; i++ ) {
            EntityBreakingFX fx = new EntityBreakingFX(mc.theWorld, posX, posY, posZ, item);
            mc.effectRenderer.addEffect(fx);
        }
    }

    public static void spawnCritFx(double posX, double posY, double posZ, Minecraft mc) {
        for( int i = 0; i < 10; i++ ) {
            double motX = ClaySoldiersMod.RNG.nextDouble() - 0.5D;
            double motY = ClaySoldiersMod.RNG.nextDouble() * 0.5D;
            double motZ = ClaySoldiersMod.RNG.nextDouble() - 0.5D;
            EntityCritFX fx = new EntityCritFX(mc.theWorld, posX, posY, posZ, motX, motY, motZ);
            mc.effectRenderer.addEffect(fx);
        }
    }

    public static void spawnSoldierDeathFx(double posX, double posY, double posZ, Unit<String> data, Minecraft mc) {
        ClaymanTeam team = ClaymanTeam.getTeam(data.getValue0());

        for( int i = 0; i < 10; i++ ) {
            ParticleSoldierDeath fx = new ParticleSoldierDeath(mc.theWorld, posX, posY, posZ, team);
            mc.effectRenderer.addEffect(fx);
        }
    }

    public static void spawnHorseDeathFx(double posX, double posY, double posZ, Unit<Byte> data, Minecraft mc) {
        EnumHorseType type = EnumHorseType.VALUES[data.getValue0()];

        for( int i = 0; i < 5; i++ ) {
            ParticleHorseDeath fx = new ParticleHorseDeath(mc.theWorld, posX, posY, posZ, type);
            mc.effectRenderer.addEffect(fx);
        }
    }

    public static void spawnBunnyDeathFx(double posX, double posY, double posZ, Unit<Byte> data, Minecraft mc) {
        EnumBunnyType type = EnumBunnyType.VALUES[data.getValue0()];

        for( int i = 0; i < 5; i++ ) {
            ParticleBunnyDeath fx = new ParticleBunnyDeath(mc.theWorld, posX, posY, posZ, type);
            mc.effectRenderer.addEffect(fx);
        }
    }

    public static void spawnTurtleDeathFx(double posX, double posY, double posZ, Unit<Byte> data, Minecraft mc) {
        EnumTurtleType type = EnumTurtleType.VALUES[data.getValue0()];

        for( int i = 0; i < 5; i++ ) {
            ParticleTurtleDeath fx = new ParticleTurtleDeath(mc.theWorld, posX, posY, posZ, type);
            mc.effectRenderer.addEffect(fx);
        }
    }

    public static void spawnDiggingFx(double posX, double posY, double posZ, Unit<String> data, Minecraft mc) {
        Block block = (Block) Block.blockRegistry.getObject(data.getValue0());

        for( int i = 0; i < 8; i++ ) {
            EntityDiggingFX fx = new EntityDiggingFX(mc.theWorld, posX, posY, posZ,
                                                     ClaySoldiersMod.RNG.nextGaussian() * 0.15D,
                                                     ClaySoldiersMod.RNG.nextDouble() * 0.2D,
                                                     ClaySoldiersMod.RNG.nextGaussian() * 0.15D, block, 0);
            mc.effectRenderer.addEffect(fx);
        }
    }

    public static void spawnSpellFx(double posX, double posY, double posZ, Triplet<Double, Double, Double> data, Minecraft mc) {
        for( int i = 0; i < 4; i++ ) {
            EntitySpellParticleFX fx = new EntitySpellParticleFX(mc.theWorld, posX, posY - ClaySoldiersMod.RNG.nextDouble() * 0.2D,
                    posZ, data.getValue0(), data.getValue1(), data.getValue2()
            );
            mc.effectRenderer.addEffect(fx);
        }
    }

    public static void spawnNexusFx(double posX, double posY, double posZ, Triplet<Float, Float, Float> data, Minecraft mc) {
        ParticleNexusFX fx = new ParticleNexusFX(mc.theWorld,
                                             posX + 0.2F + ClaySoldiersMod.RNG.nextDouble() * 0.6F,
                                             posY,
                                             posZ + 0.2F + ClaySoldiersMod.RNG.nextDouble() * 0.6F,
                                             0.1F + ClaySoldiersMod.RNG.nextFloat() * 0.2F,
                                             data.getValue0(),
                                             data.getValue1(),
                                             data.getValue2()
        );
        fx.motionY = 0.02F;
        mc.effectRenderer.addEffect(fx);
    }

    public static void spawnShockwaveFx(double posX, double posY, double posZ, Minecraft mc) {
        int blockX = MathHelper.floor_double(posX);
        int blockY = MathHelper.floor_double(posY);
        int blockZ = MathHelper.floor_double(posZ);

        Block block = mc.theWorld.getBlock(blockX, blockY, blockZ);

        if( block.getMaterial() != Material.air ) {
            double radius = 0.5D;
            int particleCount = (int) (150.0D * radius);

            for( int i2 = 0; i2 < particleCount; ++i2 ) {
                float rad = MathHelper.randomFloatClamp(ClaySoldiersMod.RNG, 0.0F, ((float) Math.PI * 2.0F));
                double multi = MathHelper.randomFloatClamp(ClaySoldiersMod.RNG, 0.75F, 1.0F);
                double partY = 0.2D + radius / 100.0D;
                double partX = (MathHelper.cos(rad) * 0.2F) * multi * multi * (radius + 0.2D);
                double partZ = (MathHelper.sin(rad) * 0.2F) * multi * multi * (radius + 0.2D);

                mc.theWorld.spawnParticle("blockdust_" + Block.getIdFromBlock(block) + '_' + mc.theWorld.getBlockMetadata(blockX, blockY, blockZ),
                        posX + 0.5D, posY + 1.0D, posZ + 0.5D, partX, partY, partZ
                );
            }
        }
    }

    public static void spawnMagmafuseFx(double posX, double posY, double posZ, Minecraft mc) {
        float red = ClaySoldiersMod.RNG.nextInt(2) - 0.001F;
        float green = ClaySoldiersMod.RNG.nextInt(2);

        if( red + green <= 0.5F ) {
            if( ClaySoldiersMod.RNG.nextBoolean() ) {
                green = 1.0F;
            } else {
                red = 1.0F;
            }
        }

        EntityReddustFX fx = new EntityReddustFX(mc.theWorld, posX, posY, posZ, red, green, 0.0F);
        mc.effectRenderer.addEffect(fx);
    }
}
