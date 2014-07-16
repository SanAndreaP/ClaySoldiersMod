/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.particle;

import de.sanandrew.core.manpack.util.SAPUtils;
import de.sanandrew.core.manpack.util.SAPUtils.RGBAValues;
import de.sanandrew.mods.claysoldiers.util.ModItems;
import de.sanandrew.mods.claysoldiers.util.soldier.ClaymanTeam;
import net.minecraft.client.particle.EntityBreakingFX;
import net.minecraft.world.World;

public class EntitySoldierDeathFX
    extends EntityBreakingFX
{
    public EntitySoldierDeathFX(World world, double x, double y, double z, ClaymanTeam team) {
        super(world, x, y, z, ModItems.dollSoldier);

        RGBAValues splitClr = SAPUtils.getRgbaFromColorInt(team.getIconColor());

        this.setParticleIcon(team.getIconInstance());

        this.particleRed = splitClr.getRed() / 255.0F;
        this.particleGreen = splitClr.getGreen() / 255.0F;
        this.particleBlue = splitClr.getBlue() / 255.0F;
    }
}
