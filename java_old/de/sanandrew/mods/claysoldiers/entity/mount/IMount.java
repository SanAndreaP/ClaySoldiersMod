/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.mount;

import net.minecraft.entity.EntityLivingBase;

public interface IMount<T extends EntityLivingBase>
{
    public IMount setSpawnedFromNexus();

    public int getType();

    public void setSpecial();

    public boolean isSpecial();
}
