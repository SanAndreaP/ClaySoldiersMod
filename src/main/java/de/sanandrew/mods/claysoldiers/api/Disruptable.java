/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.api;

import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgrade;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgradeInst;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;

public interface Disruptable
{
    DamageSource DISRUPT_DAMAGE = new DamageSource(CsmConstants.ID + ".disrupt").setDamageIsAbsolute();

    void disrupt();

    DisruptType getDisruptType();

    enum DisruptType {
        SOLDIER,
        ALL
    }
}
