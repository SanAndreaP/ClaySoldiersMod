/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.api.soldier;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import org.apache.commons.lang3.mutable.MutableFloat;

public interface IUpgrade
{
    ItemStack getStack();

    EnumFunctionCalls[] getFunctionCalls();

    default int getPriority() { return 0; }

    default boolean syncData() { return true; }

    default boolean checkPickupable(ItemStack stack) { return true; }

    default ItemStack onPickup(ISoldier<?> soldier, ItemStack stack, IUpgradeInst upgInstance) { return null; }

    default void onTick(ISoldier<?> soldier, IUpgradeInst upgInstance) { }

    default void onLoad(ISoldier<?> soldier, IUpgradeInst upgInstance, NBTTagCompound upgNbt) { }

    default void onSave(ISoldier<?> soldier, IUpgradeInst upgInstance, NBTTagCompound upgNbt) { }

    default void onDestroyed(ISoldier<?> soldier, IUpgradeInst upgInstance) { }

    default void onAttack(ISoldier<?> soldier, IUpgradeInst upgInstance, Entity target, DamageSource dmgSource, float damage) { }

    default void onAttackSuccess(ISoldier<?> soldier, IUpgradeInst upgInstance, Entity target) { }

    default void onDamaged(ISoldier<?> soldier, IUpgradeInst upgInstance, Entity attacker, DamageSource dmgSource, MutableFloat damage) { }

    default void onDeath(ISoldier<?> soldier, IUpgradeInst upgInstance, ItemStack stack) { }

    enum EnumFunctionCalls {
        ON_PICKUP,
        ON_TICK,
        ON_LOAD,
        ON_SAVE,
        ON_DESTROYED,
        ON_ATTACK,
        ON_DAMAGED,
        ON_DEATH,
        ON_ATTACK_SUCCESS;

        public static final EnumFunctionCalls[] VALUES = values();
    }
}
