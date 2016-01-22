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
import de.sanandrew.mods.claysoldiers.util.soldier.ClaymanTeam;
import net.darkhax.bookshelf.lib.ColorObject;
import net.minecraft.client.particle.EntityBreakingFX;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class ParticleSoldierDeath
        extends EntityBreakingFX
{
    public ParticleSoldierDeath(World world, double x, double y, double z, ClaymanTeam team) {
        super(world, x, y, z, RegistryItems.dollSoldier);

        ColorObject splitClr = new ColorObject(team.getIconColor());

        this.setParticleIcon(team.getIconInstance());

        this.particleRed = splitClr.getRed();
        this.particleGreen = splitClr.getGreen();
        this.particleBlue = splitClr.getBlue();
    }
}
