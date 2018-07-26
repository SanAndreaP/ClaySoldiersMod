/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.api.entity.companion;

import de.sanandrew.mods.claysoldiers.api.entity.IDisruptable;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.ITeam;
import net.minecraft.entity.EntityLivingBase;

public interface ICompanion<T extends EntityLivingBase>
        extends IDisruptable
{
    ICompanion setSpawnedFromNexus();

    void setSpecial();

    boolean isSpecial();

    T getEntity();

    ITeam getOccupation();

    void applyOccupation(ITeam team);
}
