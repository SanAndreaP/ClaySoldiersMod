/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util.soldier.upgrade.righthand;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.ASoldierUpgrade;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgrades;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class UpgradeArrow
        extends AUpgradeRightHanded
{
    @Override
    public boolean onUpdate(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        return !clayMan.hasUpgrade(SoldierUpgrades.UPG_STICK);
    }

    @Override
    public void onPickup(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, ItemStack stack) {
        clayMan.playSound("random.pop", 1.0F, 1.0F);
        clayMan.addUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_STICK));
        clayMan.addUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_FLINT));
        clayMan.addUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_FEATHER));
        this.consumeItem(stack, upgradeInst);
    }

    @Override
    public boolean canBePickedUp(EntityClayMan clayMan, ItemStack stack, ASoldierUpgrade upgrade) {
        return super.canBePickedUp(clayMan, stack, upgrade) && upgrade != SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_FEATHER);
    }

    @Override
    public void onItemDrop(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, ArrayList<ItemStack> droppedItems) {
    }
}
