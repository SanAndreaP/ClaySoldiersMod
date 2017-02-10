/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util.soldier.upgrade.misc;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgrades;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;

public class UpgradeLilyPads
        extends AUpgradeMisc
{
    @Override
    public boolean onUpdate(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        if( clayMan.isInWater() && !clayMan.hasUpgrade(SoldierUpgrades.UPG_IRON_INGOT) ) {
            clayMan.setJumping(false);
            if( clayMan.isCollidedHorizontally ) {
                clayMan.motionY = 0.2D;
            } else {
                clayMan.motionY = Math.abs(clayMan.motionY * 0.1D);
            }
        }

        return false;
    }

    @Override
    public void onPickup(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, ItemStack stack) {
        this.consumeItem(stack, upgradeInst);
        clayMan.playSound("random.pop", 1.0F, 1.0F);
        if( clayMan.worldObj.getBlock((int) clayMan.posX, (int) clayMan.posY, (int) clayMan.posZ).getMaterial() == Material.water ) {
            clayMan.setJumping(true);
        }
    }
}
