package de.sanandrew.mods.claysoldiers.util.upgrades.misc;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.ModItems;
import de.sanandrew.mods.claysoldiers.util.upgrades.ISoldierUpgrade;
import de.sanandrew.mods.claysoldiers.util.upgrades.SoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.util.upgrades.SoldierUpgrades;
import de.sanandrew.mods.claysoldiers.util.upgrades.lefthand.AUpgradeLeftHanded;
import de.sanandrew.mods.claysoldiers.util.upgrades.righthand.AUpgradeRightHanded;
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
    public void onPickup(EntityClayMan clayMan, ItemStack stack) {
        ISoldierUpgrade upgrade = null;
        if( stack.getItem() == ModItems.shearBlade ) {
            if( !clayMan.hasUpgradeInst(AUpgradeLeftHanded.class) ) {
                upgrade = SoldierUpgrades.getUpgradeFromName(SoldierUpgrades.UPG_SHEARLEFT);
            } else if( !clayMan.hasUpgradeInst(AUpgradeRightHanded.class) ) {
                upgrade = SoldierUpgrades.getUpgradeFromName(SoldierUpgrades.UPG_SHEARRIGHT);
            }
        } else {
            if( !clayMan.hasUpgradeInst(AUpgradeLeftHanded.class) ) {
                upgrade = SoldierUpgrades.getUpgradeFromName(SoldierUpgrades.UPG_SHEARLEFT);
            } else if( !clayMan.hasUpgradeInst(AUpgradeRightHanded.class) ) {
                if( upgrade == null ) {
                    clayMan.entityDropItem(new ItemStack(ModItems.shearBlade, 1), 0.0F);
                }

                upgrade = SoldierUpgrades.getUpgradeFromName(SoldierUpgrades.UPG_SHEARRIGHT);
            }
        }

        if( upgrade != null ) {
            SoldierUpgradeInst upgradeInst = clayMan.addNewUpgrade(upgrade);
            upgrade.onConstruct(clayMan, upgradeInst);
            stack.stackSize--;
            clayMan.playSound("random.pop", 1.0F, 1.0F);
        }
    }

    @Override
    public boolean canBePickedUp(EntityClayMan clayMan, ItemStack stack, ISoldierUpgrade upgrade) {
        return !clayMan.hasUpgradeInst(AUpgradeLeftHanded.class) || !clayMan.hasUpgradeInst(AUpgradeRightHanded.class);
    }
}
