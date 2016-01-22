/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.particle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.mods.claysoldiers.item.ItemTurtleDoll;
import de.sanandrew.mods.claysoldiers.util.RegistryItems;
import de.sanandrew.mods.claysoldiers.util.mount.EnumTurtleType;
import net.darkhax.bookshelf.lib.ColorObject;
import net.minecraft.client.particle.EntityBreakingFX;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class ParticleTurtleDeath
        extends EntityBreakingFX
{
    public ParticleTurtleDeath(World world, double x, double y, double z, EnumTurtleType type) {
        super(world, x, y, z, RegistryItems.dollTurtleMount);

        ItemStack stack = ItemTurtleDoll.setType(new ItemStack(RegistryItems.dollTurtleMount), type);
        ColorObject splitClr = new ColorObject(RegistryItems.dollTurtleMount.getColorFromItemStack(stack, 0));

        this.setParticleIcon(RegistryItems.dollTurtleMount.getIcon(stack, 0));

        this.particleRed = splitClr.getRed();
        this.particleGreen = splitClr.getGreen();
        this.particleBlue = splitClr.getBlue();
    }
}
