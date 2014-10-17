/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util.soldier.upgrade.misc;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgradeInst;
import org.apache.commons.lang3.mutable.MutableDouble;

public class UpgradeGlass
        extends AUpgradeMisc
{
    @Override
    public void getLookRange(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, MutableDouble radius) {
        radius.add(radius);
    }

    @Override
    public boolean shouldNbtSyncToClient(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        return true;
    }
}
