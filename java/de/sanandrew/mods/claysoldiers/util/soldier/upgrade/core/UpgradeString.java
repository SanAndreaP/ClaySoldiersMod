/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util.soldier.upgrade.core;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgradeInst;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import org.apache.commons.lang3.mutable.MutableFloat;

public class UpgradeString
        extends AUpgradeCore
{
    @Override
    public void onConstruct(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        upgradeInst.getNbtTag().setBoolean("destroyed", false);
    }

    @Override
    public boolean onSoldierHurt(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, DamageSource source, MutableFloat damage) {
        if( source.isExplosion() && !upgradeInst.getNbtTag().getBoolean("destroyed") ) {
            upgradeInst.getNbtTag().setBoolean("destroyed", true);
            return true;
        }

        return false;
    }

    @Override
    public boolean onUpdate(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        return upgradeInst.getNbtTag().getBoolean("destroyed");
    }

    @Override
    public void onPickup(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, ItemStack stack) {
        this.consumeItem(stack, upgradeInst);
        clayMan.playSound("random.pop", 1.0F, 1.0F);
    }
}
