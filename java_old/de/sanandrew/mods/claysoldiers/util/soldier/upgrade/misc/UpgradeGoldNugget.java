/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util.soldier.upgrade.misc;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.ASoldierUpgrade;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgradeInst;
import net.minecraft.item.ItemStack;

public class UpgradeGoldNugget
        extends AUpgradeMisc
{
    @Override
    public boolean onUpdate(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        for( EntityClayMan minion : clayMan.getSoldiersInRange() ) {
            if( minion.getClayTeam().equals(clayMan.getClayTeam()) && !minion.hasPath() && minion.getTargetFollowing() == null ) {
                minion.setTargetFollowing(clayMan);
            }
        }

        return false;
    }

    @Override
    public void onPickup(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, ItemStack stack) {
        this.consumeItem(stack, upgradeInst);
        clayMan.playSound("random.pop", 1.0F, 1.0F);
    }

    @Override
    public boolean canBePickedUp(final EntityClayMan clayMan, ItemStack stack, ASoldierUpgrade upgrade) {
        Predicate<EntityClayMan> filterBy = new Predicate<EntityClayMan>()
        {
            @Override
            public boolean apply(EntityClayMan input) {
                return input != null && input.hasUpgrade(UpgradeGoldNugget.class) && input.getClayTeam().equals(clayMan.getClayTeam());
            }
        };
        return Collections2.filter(clayMan.getSoldiersInRange(), filterBy).isEmpty();
    }
}
