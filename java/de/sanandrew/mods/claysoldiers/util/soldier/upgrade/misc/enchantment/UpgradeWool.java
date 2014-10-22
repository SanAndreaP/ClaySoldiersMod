/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util.soldier.upgrade.misc.enchantment;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgrades;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.misc.AUpgradeMisc;
import net.minecraft.util.DamageSource;
import org.apache.commons.lang3.mutable.MutableFloat;

public class UpgradeWool
        extends AUpgradeMisc
{
    @Override
    public boolean onSoldierHurt(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, DamageSource source, MutableFloat damage) {
        if( !source.isUnblockable() ) {
            damage.setValue(Math.max(0.25F, damage.getValue() - 1.0F));
        }

        return false;
    }

    @Override
    public boolean onUpdate(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        return !clayMan.hasUpgrade(SoldierUpgrades.UPG_LEATHER);
    }
}
