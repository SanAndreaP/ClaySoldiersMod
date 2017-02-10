/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.projectile;

import de.sanandrew.mods.claysoldiers.util.soldier.ClaymanTeam;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;

public interface ISoldierProjectile<T extends EntityThrowable>
{
    public void initProjectile(EntityLivingBase target, boolean homing, String clayTeam);

    public String getTrowingTeam();

    public T getProjectileEntity();
}
