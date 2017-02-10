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
import org.apache.commons.lang3.mutable.MutableFloat;

public class UpgradeFeather
        extends AUpgradeMisc
{
    @Override
    public EnumMethodState onTargeting(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan target) {
        if( clayMan.ridingEntity != null || clayMan.hasUpgrade(SoldierUpgrades.UPG_IRON_INGOT) ) {
            return EnumMethodState.SKIP;
        }

        return !clayMan.onGround && clayMan.motionY < -0.2D && clayMan.fallDistance >= 1.4F ? EnumMethodState.DENY : EnumMethodState.SKIP;
    }

    @Override
    public boolean onUpdate(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        if( clayMan.ridingEntity == null && !clayMan.hasUpgrade(SoldierUpgrades.UPG_IRON_INGOT) && clayMan.motionY < -0.2D && clayMan.fallDistance >= 1.4F ) {
            clayMan.motionY *= 0.2D;
            clayMan.fallDistance = 1.5F;
        }

        return false;
    }

    @Override
    public void onPickup(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, ItemStack stack) {
        this.consumeItem(stack, upgradeInst);
        clayMan.playSound("random.pop", 1.0F, 1.0F);
    }

    @Override
    public void getAiMoveSpeed(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, MutableFloat speed) {
        if( clayMan.ridingEntity == null && !clayMan.hasUpgrade(SoldierUpgrades.UPG_IRON_INGOT) && clayMan.motionY < -0.3D && clayMan.fallDistance >= 1.4F ) {
            speed.setValue(speed.getValue() * 0.25F);
        }
    }
}
