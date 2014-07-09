package de.sanandrew.mods.claysoldiers.util.upgrades.behavior;

import de.sanandrew.core.manpack.util.javatuples.Pair;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.upgrades.ISoldierUpgrade;
import de.sanandrew.mods.claysoldiers.util.upgrades.SoldierUpgradeInst;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

/**
 * @author SanAndreas
 * @version 1.0
 */
public abstract class AUpgradeBehavior
    implements ISoldierUpgrade
{
    @Override
    public void onConstruct(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) { }

    @Override
    public AttackState onTargeting(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan target) {
        return AttackState.SKIP;
    }

    @Override
    public AttackState onBeingTargeted(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan attacker) {
        return AttackState.SKIP;
    }

    @Override
    public float onSoldierAttack(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan target, float damage) {
        return damage;
    }

    @Override
    public void onSoldierDamage(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan target) { }

    @Override
    public boolean onUpdate(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        return false;
    }

    @Override
    public Pair<Float, Boolean> onSoldierHurt(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, DamageSource source, float damage) {
        return Pair.with(damage, true);
    }

    @Override
    public boolean canBePickedUp(EntityClayMan clayMan, ItemStack stack, ISoldierUpgrade upgrade) {
        return !(upgrade instanceof AUpgradeBehavior);
    }

    @Override
    public void onPickup(EntityClayMan clayMan, ItemStack stack) { }

    @Override
    public void onSoldierDeath(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, DamageSource source) { }
}
