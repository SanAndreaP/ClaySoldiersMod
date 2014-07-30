package de.sanandrew.mods.claysoldiers.util.soldier.upgrade.misc;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.righthand.AUpgradeRightHanded;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.mutable.MutableFloat;

/**
 * @author SanAndreas
 * @version 1.0
 */
public class UpgradeStoneButton
    extends AUpgradeMisc
{
    @Override
    public void onSoldierAttack(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan target, MutableFloat damage) {
        if( !clayMan.hasUpgrade(AUpgradeRightHanded.class) ) {
            damage.add(2.0F + clayMan.getRNG().nextFloat());
        }
    }

    @Override
    public void onPickup(EntityClayMan clayMan, SoldierUpgradeInst upgInst, ItemStack stack) {
        this.consumeItem(stack, upgInst);
        clayMan.playSound("random.pop", 1.0F, 1.0F);
    }
}
