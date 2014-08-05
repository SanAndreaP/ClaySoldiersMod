package de.sanandrew.mods.claysoldiers.util.soldier.upgrade;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.entity.projectile.ISoldierProjectile;
import de.sanandrew.mods.claysoldiers.util.soldier.MethodState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import org.apache.commons.lang3.mutable.MutableFloat;

/**
 * @author SanAndreas
 * @version 1.0
 */
public abstract class ASoldierUpgrade
{
    /**
     * Fired when an instance for this upgrade is created. Use this to initiate all needed values in the NBT provided by the instance
     * @param clayMan the soldier calling the method
     * @param upgradeInst the instance of this upgrade (like an ItemStack for an Item)
     */
    public void onConstruct(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {

    }

    /**
     * Determines if the calling soldier should target the other soldier.
     * Note that it does NOT allow targeting of other soldiers if they are marked as dead!
     * @param clayMan the soldier calling the method
     * @param upgradeInst the instance of this upgrade (like an ItemStack for an Item)
     * @param target the soldier which is targeted by the calling soldier
     * @return <code>ALLOW, forces the calling soldier to take the other soldier as target<br>
     *         <code>DENY, forces the calling soldier to ignore the other soldier<br>
     *         <code>SKIP, if the calling soldier should skip this upgrade for checking
     */
    public MethodState onTargeting(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan target) {
        return MethodState.SKIP;
    }

    /**
     * Determines if the calling soldier should be targeted by the targeting soldier.
     * @param clayMan the soldier calling the method
     * @param upgradeInst the instance of this upgrade (like an ItemStack for an Item)
     * @param attacker the solder targeting the calling soldier
     * @return An AttackStage Enum value. See
     *         {@link de.sanandrew.mods.claysoldiers.util.soldier.MethodState AttackStage}
     *         for more information.
     */
    public MethodState onBeingTargeted(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan attacker) {
        return MethodState.SKIP;
    }

    /**
     * Triggered when a soldier attempts to attack the target.
     * @param clayMan the soldier calling the method
     * @param upgradeInst the instance of this upgrade (like an ItemStack for an Item)
     * @param target the soldier which is targeted
     * @param damage the initial damage the soldier would've dealt
     * @return the new damage value. Note that with multiple upgrade modifying the damage value, the highest damage from those upgrade will be applied!
     */
    public void onSoldierAttack(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan target, MutableFloat damage) {

    }

    public void getAttackRange(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, Entity target, MutableFloat attackRange) {

    }

    /**
     * Triggered when a soldier successfully damaged the target.
     * @param clayMan the soldier calling the method
     * @param upgradeInst the instance of this upgrade (like an ItemStack for an Item)
     * @param target the soldier which is targeted
     */
    public void onSoldierDamage(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan target) {

    }

    public void onSoldierDeath(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, DamageSource source) {

    }

    public boolean onSoldierHurt(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, DamageSource source, MutableFloat damage) {
        return true;
    }

    /**
     * Called when a soldier holding the upgrade updates.
     * @param clayMan the soldier calling the method
     * @param upgradeInst the instance of this upgrade (like an ItemStack for an Item)
     * @return true, if the upgrade should be removed from the soldiers upgrade list
     */
    public boolean onUpdate(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        return false;
    }

    public void onClientUpdate(EntityClayMan clayMan) {

    }

    /**
     * Checks if the given upgrade is compatible with this upgrade.
     * @param clayMan the soldier calling the method
     * @param stack the ItemStack which should be picked up
     * @param upgrade the upgrade to be checked
     * @return true, if the upgrade can co-exist with this upgrade
     */
    public abstract boolean canBePickedUp(EntityClayMan clayMan, ItemStack stack, ASoldierUpgrade upgrade);

    /**
     * Called when a soldier picks the itemData up
     * @param clayMan the soldier calling the method
     * @param stack the ItemStack which is picked up
     */
    public void onPickup(EntityClayMan clayMan, SoldierUpgradeInst upgInst, ItemStack stack) {

    }

    public void getAiMoveSpeed(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, MutableFloat speed) {

    }

    public void onProjectileHit(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, MovingObjectPosition target,
                                ISoldierProjectile<? extends EntityThrowable> projectile) {

    }

    protected void consumeItem(ItemStack stack, SoldierUpgradeInst upgInst) {
        upgInst.setStoredItem(stack.splitStack(1));
    }

    public boolean isTargetStillValid(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, Entity target) {
        return true;
    }
}
