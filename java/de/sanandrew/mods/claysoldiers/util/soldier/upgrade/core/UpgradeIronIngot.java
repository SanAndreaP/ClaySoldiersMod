package de.sanandrew.mods.claysoldiers.util.soldier.upgrade.core;

import de.sanandrew.core.manpack.util.javatuples.Triplet;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgrades;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import org.apache.commons.lang3.mutable.MutableFloat;

/**
 * @author SanAndreas
 * @version 1.0
 */
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
        if( source.getEntity() instanceof EntityClayMan
                && ((EntityClayMan) source.getEntity()).hasUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_IRONINGOT)) )
        {
            clayMan.knockBack = Triplet.with(0.8D, 0.8D, 0.8D);
        } else {
            clayMan.knockBack = Triplet.with(0.4D, 0.4D, 0.4D);
        }
        return super.onSoldierHurt(clayMan, upgradeInst, source, damage);
    }

    @Override
    public void onPickup(EntityClayMan clayMan, SoldierUpgradeInst upgInst, ItemStack stack) {
        this.consumeItem(stack, upgInst);
        clayMan.playSound("random.pop", 1.0F, 1.0F);
    }
}
