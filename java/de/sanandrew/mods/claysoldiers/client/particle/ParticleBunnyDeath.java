/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.particle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.mods.claysoldiers.util.RegistryItems;
import de.sanandrew.mods.claysoldiers.util.mount.EnumBunnyType;
import net.darkhax.bookshelf.lib.ColorObject;
import net.minecraft.client.particle.EntityBreakingFX;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class ParticleBunnyDeath
        extends EntityBreakingFX
{
    public ParticleBunnyDeath(World world, double x, double y, double z, EnumBunnyType type) {
        super(world, x, y, z, RegistryItems.dollBunnyMount);

        ColorObject splitClr = new ColorObject(type.typeColor);

        this.setParticleIcon(RegistryItems.dollBunnyMount.getIconFromDamage(0));

        this.particleRed = splitClr.getRed();
        this.particleGreen = splitClr.getGreen();
        this.particleBlue = splitClr.getBlue();
    }
}
