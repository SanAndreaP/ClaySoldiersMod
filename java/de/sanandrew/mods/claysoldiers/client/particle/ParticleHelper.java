/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.particle;

import de.sanandrew.core.manpack.util.SAPUtils;
import de.sanandrew.core.manpack.util.javatuples.Quartet;
import de.sanandrew.core.manpack.util.javatuples.Triplet;
import de.sanandrew.mods.claysoldiers.entity.mounts.EnumHorseType;
import de.sanandrew.mods.claysoldiers.util.soldier.ClaymanTeam;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityBreakingFX;
import net.minecraft.client.particle.EntityCritFX;
import net.minecraft.item.Item;

public final class ParticleHelper
{
    public static void spawnBreakFx(Quartet<Double, Double, Double, String> data, Minecraft mc) {
        Item item = (Item) Item.itemRegistry.getObject(data.getValue3());

        for (int i = 0; i < 5; i++) {
            EntityBreakingFX fx = new EntityBreakingFX(mc.theWorld, data.getValue0(), data.getValue1(), data.getValue2(), item);
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
}
