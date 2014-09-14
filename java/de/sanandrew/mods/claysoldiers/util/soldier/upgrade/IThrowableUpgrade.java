/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util.soldier.upgrade;

import de.sanandrew.mods.claysoldiers.entity.projectile.ISoldierProjectile;
import de.sanandrew.mods.claysoldiers.tileentity.TileEntityClayNexus;
import net.minecraft.entity.projectile.EntityThrowable;

public interface IThrowableUpgrade
{
    public Class<? extends ISoldierProjectile<? extends EntityThrowable>> getThrowableClass();

    public void renderNexusThrowable(TileEntityClayNexus nexus, float partTicks);
}
