/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util.soldier.upgrade.core;

import de.sanandrew.core.manpack.util.javatuples.Triplet;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgrades;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import org.apache.commons.lang3.mutable.MutableFloat;

public class UpgradeIronIngot
        extends AUpgradeCore
{
    @Override
    public void onConstruct(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
    }

    @Override
    public void onSoldierAttack(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan target, MutableFloat damage) {
        target.knockBack = Triplet.with(1.2D, 0.8D, 1.2D);
    }

    @Override
    public boolean onSoldierHurt(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, DamageSource source, MutableFloat damage) {
        if( source.getEntity() instanceof EntityClayMan && ((EntityClayMan) source.getEntity()).hasUpgrade(SoldierUpgrades.UPG_IRON_INGOT) ) {
            clayMan.knockBack = Triplet.with(0.8D, 0.8D, 0.8D);
        } else {
            clayMan.knockBack = Triplet.with(0.4D, 0.4D, 0.4D);
        }

        return super.onSoldierHurt(clayMan, upgradeInst, source, damage);
    }

    @Override
    public void onPickup(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, ItemStack stack) {
        this.consumeItem(stack, upgradeInst);
        clayMan.playSound("random.pop", 1.0F, 1.0F);
    }
}
