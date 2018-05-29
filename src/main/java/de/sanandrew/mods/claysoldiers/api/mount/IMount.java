/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP, SilverChiren and CliffracerX
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.api.mount;

import de.sanandrew.mods.claysoldiers.api.IDisruptable;
import net.minecraft.entity.EntityLivingBase;

public interface IMount<T extends EntityLivingBase>
        extends IDisruptable
{
    IMount setSpawnedFromNexus();

    void setSpecial();

    boolean isSpecial();

    T getEntity();

    int getMaxPassengers();
}
