/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util.soldier.upgrade.core;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.ASoldierUpgrade;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgradeInst;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public abstract class AUpgradeCore
        extends ASoldierUpgrade
{
    @Override
    public boolean canBePickedUp(EntityClayMan clayMan, ItemStack stack, ASoldierUpgrade upgrade) {
        return !(upgrade instanceof AUpgradeCore);
    }

    @Override
    public void onItemDrop(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, ArrayList<ItemStack> droppedItems) {
        droppedItems.add(upgradeInst.getStoredItem());
    }
}
