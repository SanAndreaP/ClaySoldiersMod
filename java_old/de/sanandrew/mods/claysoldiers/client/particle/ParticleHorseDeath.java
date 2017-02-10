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
import de.sanandrew.core.manpack.util.helpers.SAPUtils.RGBAValues;
import de.sanandrew.mods.claysoldiers.util.RegistryItems;
import de.sanandrew.mods.claysoldiers.util.mount.EnumHorseType;
import net.minecraft.client.particle.EntityBreakingFX;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class ParticleHorseDeath
        extends EntityBreakingFX
{
    public ParticleHorseDeath(World world, double x, double y, double z, EnumHorseType type) {
        super(world, x, y, z, RegistryItems.dollHorseMount);

        RGBAValues splitClr = SAPUtils.getRgbaFromColorInt(type.typeColor);

        this.setParticleIcon(RegistryItems.dollHorseMount.getIconFromType(type));

        this.particleRed = splitClr.getRed() / 255.0F;
        this.particleGreen = splitClr.getGreen() / 255.0F;
        this.particleBlue = splitClr.getBlue() / 255.0F;
    }
}
