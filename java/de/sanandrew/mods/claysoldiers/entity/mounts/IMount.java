/*******************************************************************************************************************
 * Authors:    SanAndreasP
 * Copyright: SanAndreasP and SilverChiren
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported
 *            (http://creativecommons.org/licenses/by-nc-sa/3.0/)
 *******************************************************************************************************************/

package de.sanandrew.mods.claysoldiers.entity.mounts;

import net.minecraft.entity.EntityLivingBase;

public interface IMount<T extends EntityLivingBase>
{
    public IMount setSpawnedFromNexus();

    public int getType();

    public void setSpecial();

    public boolean isSpecial();
}
