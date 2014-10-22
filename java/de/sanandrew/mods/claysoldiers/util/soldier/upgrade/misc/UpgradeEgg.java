/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util.soldier.upgrade.misc;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.soldier.EnumMethodState;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgrades;
import net.minecraft.item.ItemStack;

public class UpgradeEgg
        extends AUpgradeMisc
{
    @Override
    public EnumMethodState onBeingTargeted(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan attacker) {
        return attacker.hasUpgrade(SoldierUpgrades.UPG_GLASS) ? EnumMethodState.SKIP : EnumMethodState.DENY;
    }

    @Override
    public void onSoldierDamage(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan target) {
        target.canTargetSoldier(clayMan);
        super.onSoldierDamage(clayMan, upgradeInst, target);
    }

    @Override
    public void onPickup(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, ItemStack stack) {
        this.consumeItem(stack, upgradeInst);
        clayMan.playSound("random.pop", 1.0F, 1.0F);
    }
}
