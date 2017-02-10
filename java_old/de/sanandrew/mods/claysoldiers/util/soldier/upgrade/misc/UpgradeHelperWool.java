/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util.soldier.upgrade.misc;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.ASoldierUpgrade;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgrades;
import net.minecraft.item.ItemStack;

public class UpgradeHelperWool
        extends AUpgradeMisc
{
    @Override
    public boolean onUpdate(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        return true;
    }

    @Override
    public void onPickup(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, ItemStack stack) {
        clayMan.setMiscColorIndex(15 - stack.getItemDamage());
        clayMan.playSound("dig.cloth", 1.0F, 1.0F);

        if( clayMan.hasUpgrade(SoldierUpgrades.UPG_LEATHER) && !clayMan.hasUpgrade(SoldierUpgrades.UPG_WOOL) ) {
            clayMan.addUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_WOOL));
        }
    }

    @Override
    public boolean canBePickedUp(EntityClayMan clayMan, ItemStack stack, ASoldierUpgrade upgrade) {
        return clayMan.getMiscColorIndex() != 15 - stack.getItemDamage() || !clayMan.hasUpgrade(SoldierUpgrades.UPG_WOOL);
    }
}
