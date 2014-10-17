/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util.soldier.upgrade.misc;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.RegistryItems;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.ASoldierUpgrade;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgrades;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.lefthand.AUpgradeLeftHanded;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.righthand.AUpgradeRightHanded;
import net.minecraft.item.ItemStack;

public class UpgradeHelperShearBlade
        extends AUpgradeMisc
{
    @Override
    public boolean onUpdate(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        return !clayMan.hasUpgrade(AUpgradeLeftHanded.class) || !clayMan.hasUpgrade(AUpgradeRightHanded.class);
    }

    @Override
    public void onPickup(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, ItemStack stack) {
        ASoldierUpgrade upgrade = null;
        ItemStack savedItem = null;

        if( stack.getItem() == RegistryItems.shearBlade ) {
            if( !clayMan.hasUpgrade(AUpgradeLeftHanded.class) ) {
                upgrade = SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_SHEARLEFT);
                savedItem = stack;
            } else if( !clayMan.hasUpgrade(AUpgradeRightHanded.class) ) {
                upgrade = SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_SHEARRIGHT);
                savedItem = stack;
            }
        } else {
            if( !clayMan.hasUpgrade(AUpgradeLeftHanded.class) ) {
                clayMan.entityDropItem(new ItemStack(RegistryItems.shearBlade, 1), 0.0F);
                upgrade = SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_SHEARLEFT);
                savedItem = new ItemStack(RegistryItems.shearBlade, 1);
            } else if( !clayMan.hasUpgrade(AUpgradeRightHanded.class) ) {
                clayMan.entityDropItem(new ItemStack(RegistryItems.shearBlade, 1), 0.0F);
                upgrade = SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_SHEARRIGHT);
                savedItem = new ItemStack(RegistryItems.shearBlade, 1);
            }
        }

        if( upgrade != null ) {
            if( savedItem != stack ) {
                stack.stackSize--;
            }

            SoldierUpgradeInst newUpgrade = clayMan.addUpgrade(upgrade);
            this.consumeItem(savedItem, newUpgrade);
            clayMan.playSound("random.pop", 1.0F, 1.0F);
        }
    }

    @Override
    public boolean canBePickedUp(EntityClayMan clayMan, ItemStack stack, ASoldierUpgrade upgrade) {
        return !clayMan.hasUpgrade(AUpgradeLeftHanded.class) || !clayMan.hasUpgrade(AUpgradeRightHanded.class);
    }
}
