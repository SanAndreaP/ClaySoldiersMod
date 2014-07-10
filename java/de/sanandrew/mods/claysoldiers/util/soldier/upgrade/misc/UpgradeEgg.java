package de.sanandrew.mods.claysoldiers.util.soldier.upgrade.misc;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.soldier.AttackState;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgradeInst;
import net.minecraft.item.ItemStack;

/**
 * @author SanAndreas
 * @version 1.0
 */
public class UpgradeEgg
    extends AUpgradeMisc
{
    @Override
    public AttackState onBeingTargeted(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan attacker) {
        return AttackState.DENY;
    }

    @Override
    public void onSoldierDamage(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan target) {
        target.targetSoldier(clayMan);
        super.onSoldierDamage(clayMan, upgradeInst, target);
    }

    @Override
    public void onPickup(EntityClayMan clayMan, ItemStack stack) {
        stack.stackSize--;
        clayMan.playSound("random.pop", 1.0F, 1.0F);
    }
}
