package de.sanandrew.mods.claysoldiers.util.soldier.upgrade.misc;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.ModItems;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.ASoldierUpgrade;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgrades;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.lefthand.AUpgradeLeftHanded;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.righthand.AUpgradeRightHanded;
import net.minecraft.item.ItemStack;

/**
 * @author SanAndreas
 * @version 1.0
 */
public class UpgradeHelperShearBlade
    extends AUpgradeMisc
{
    @Override
    public boolean onUpdate(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        return true;
    }

    @Override
    public void onPickup(EntityClayMan clayMan, SoldierUpgradeInst upgInst, ItemStack stack) {
        ASoldierUpgrade upgrade = null;
        ItemStack savedItem = null;

        if( stack.getItem() == ModItems.shearBlade ) {
            if( !clayMan.hasUpgradeInst(AUpgradeLeftHanded.class) ) {
                upgrade = SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_SHEARLEFT);
                savedItem = stack;
            } else if( !clayMan.hasUpgradeInst(AUpgradeRightHanded.class) ) {
                upgrade = SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_SHEARRIGHT);
                savedItem = stack;
            }
        } else {
            if( !clayMan.hasUpgradeInst(AUpgradeLeftHanded.class) ) {
                clayMan.entityDropItem(new ItemStack(ModItems.shearBlade, 1), 0.0F);
                upgrade = SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_SHEARLEFT);
                savedItem = new ItemStack(ModItems.shearBlade, 1);
            } else if( !clayMan.hasUpgradeInst(AUpgradeRightHanded.class) ) {
                clayMan.entityDropItem(new ItemStack(ModItems.shearBlade, 1), 0.0F);
                upgrade = SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_SHEARRIGHT);
                savedItem = new ItemStack(ModItems.shearBlade, 1);
            }
        }

        if( upgrade != null ) {
            if( savedItem != stack ) {
                stack.stackSize--;
            }

            SoldierUpgradeInst upgradeInst = clayMan.addNewUpgrade(upgrade);
            this.consumeItem(savedItem, upgradeInst);
            upgrade.onConstruct(clayMan, upgradeInst);
            clayMan.playSound("random.pop", 1.0F, 1.0F);
        }
    }

    @Override
    public boolean canBePickedUp(EntityClayMan clayMan, ItemStack stack, ASoldierUpgrade upgrade) {
        return !clayMan.hasUpgradeInst(AUpgradeLeftHanded.class) || !clayMan.hasUpgradeInst(AUpgradeRightHanded.class);
    }
}
