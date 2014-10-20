/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util.soldier.upgrade;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.entity.projectile.ISoldierProjectile;
import de.sanandrew.mods.claysoldiers.util.soldier.EnumMethodState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import org.apache.commons.lang3.mutable.MutableDouble;
import org.apache.commons.lang3.mutable.MutableFloat;

import java.util.ArrayList;

public abstract class ASoldierUpgrade
{
    /**
     * A field which should be used as the key for the upgrade NBT when having a usage (like sticks, gravel, etc.).
     * This is meant for the diamond / gold block upgrade, which increase the uses of the upgrades.
     * This NBT Tag MUST be a short!
     */
    public static final String NBT_USES = "uses";

    /**
     * Fired when an instance for this upgrade is created. Use this to initiate all needed values in the NBT provided by the instance
     *
     * @param clayMan     the soldier calling the method
     * @param upgradeInst the instance of this upgrade (like an ItemStack for an Item)
     */
    public void onConstruct(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
    }

    /**
     * Determines if the calling soldier should target the other soldier.
     * Note that it does NOT allow targeting of other soldiers if they are marked as dead!
     *
     * @param clayMan     the soldier calling the method
     * @param upgradeInst the instance of this upgrade (like an ItemStack for an Item)
     * @param target      the soldier which is targeted by the calling soldier
     * @return <code>ALLOW, forces the calling soldier to take the other soldier as target<br>
     * <code>DENY, forces the calling soldier to ignore the other soldier<br>
     * <code>SKIP, if the calling soldier should skip this upgrade for checking
     */
    public EnumMethodState onTargeting(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan target) {
        return EnumMethodState.SKIP;
    }

    /**
     * Determines if the calling soldier should be targeted by the targeting soldier.
     *
     * @param clayMan     the soldier calling the method
     * @param upgradeInst the instance of this upgrade (like an ItemStack for an Item)
     * @param attacker    the solder targeting the calling soldier
     * @return An AttackStage Enum value. See
     * {@link de.sanandrew.mods.claysoldiers.util.soldier.EnumMethodState AttackStage}
     * for more information.
     */
    public EnumMethodState onBeingTargeted(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan attacker) {
        return EnumMethodState.SKIP;
    }

    /**
     * Triggered when a soldier attempts to attack the target.
     *
     * @param clayMan     the soldier calling the method
     * @param upgradeInst the instance of this upgrade (like an ItemStack for an Item)
     * @param target      the soldier which is targeted
     * @param damage      the damage value the soldier will do (can be changed)
     */
    public void onSoldierAttack(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan target, MutableFloat damage) {
    }

    public void getAttackRange(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, Entity target, MutableFloat attackRange) {
    }

    /**
     * Triggered when a soldier successfully damaged the target.
     *
     * @param clayMan     the soldier calling the method
     * @param upgradeInst the instance of this upgrade (like an ItemStack for an Item)
     * @param target      the soldier which is targeted
     */
    public void onSoldierDamage(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan target) {
    }

    public void onSoldierDeath(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, DamageSource source) {
    }

    /**
     * Called whenever the soldier gets hurt
     *
     * @param clayMan the soldier calling the method
     * @param upgradeInst the instance of this upgrade (like an ItemStack for an Item)
     * @param source the source of damage
     * @param damage the damage value (can be changed)
     * @return true, if the soldier shouldn't receive any damage (and thus not playing the hurt animation)
     */
    @SuppressWarnings("BooleanMethodNameMustStartWithQuestion")
    public boolean onSoldierHurt(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, DamageSource source, MutableFloat damage) {
        return false;
    }

    /**
     * Called when a soldier holding the upgrade updates.
     *
     * @param clayMan     the soldier calling the method
     * @param upgradeInst the instance of this upgrade (like an ItemStack for an Item)
     * @return true, if the upgrade should be removed from the soldiers upgrade list
     */
    @SuppressWarnings("BooleanMethodNameMustStartWithQuestion")
    public boolean onUpdate(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        return false;
    }

    public void onClientUpdate(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
    }

    /**
     * Checks if the given upgrade is compatible with this upgrade.
     *
     * @param clayMan the soldier calling the method
     * @param stack   the ItemStack which should be picked up
     * @param upgrade the upgrade to be checked
     * @return true, if the upgrade can be picked up / co-exist with this upgrade
     */
    public abstract boolean canBePickedUp(EntityClayMan clayMan, ItemStack stack, ASoldierUpgrade upgrade);

    /**
     * Called when a soldier picks the itemData up
     *
     * @param clayMan the soldier calling the method
     * @param stack   the ItemStack which is picked up
     */
    public void onPickup(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, ItemStack stack) {
    }

    public void getAiMoveSpeed(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, MutableFloat speed) {
    }

    public void onProjectileHit(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, MovingObjectPosition target, ISoldierProjectile<? extends EntityThrowable> projectile) {
    }

    public void getLookRange(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, MutableDouble radius) {
    }

    public boolean shouldNbtSyncToClient(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        return false;
    }

    public void onUpgradeAdded(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, SoldierUpgradeInst appliedUpgradeInst) {
    }

    public void consumeItem(ItemStack stack, SoldierUpgradeInst upgradeInst) {
        upgradeInst.setStoredItem(stack.splitStack(1));
    }

    public abstract void onItemDrop(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, ArrayList<ItemStack> droppedItems);
}
