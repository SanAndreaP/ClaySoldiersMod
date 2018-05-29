/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.api;

import net.minecraft.util.DamageSource;

public interface IDisruptable
{
    DamageSource DISRUPT_DAMAGE = new DamageSource(CsmConstants.ID + ".disrupt").setDamageIsAbsolute().setDamageBypassesArmor();

    void disrupt();

    DisruptType getDisruptType();

    enum DisruptType {
        SOLDIER,
        MOUNT,
        ALL
    }
}
