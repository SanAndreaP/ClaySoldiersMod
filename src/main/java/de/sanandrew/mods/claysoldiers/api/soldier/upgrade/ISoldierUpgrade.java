/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.api.soldier.upgrade;

import de.sanandrew.mods.claysoldiers.api.soldier.ISoldier;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import org.apache.commons.lang3.mutable.MutableFloat;

import javax.annotation.Nonnull;
import java.util.List;

public interface ISoldierUpgrade
{
    @Nonnull
    ItemStack[] getStacks();

    @Nonnull
    EnumFunctionCalls[] getFunctionCalls();

    @Nonnull
    EnumUpgradeType getType(ISoldier<?> checker);

    default int getPriority() { return 0; }

    default boolean syncData() { return false; }

    default boolean syncNbtData() { return false; }

    default void writeSyncData(ByteBuf buf, NBTTagCompound nbt) { }

    default void readSyncData(ByteBuf buf, NBTTagCompound nbt) { }

    default boolean checkPickupable(ISoldier<?> soldier, ItemStack stack) { return true; }

    default void onAdded(ISoldier<?> soldier, ItemStack stack, ISoldierUpgradeInst upgradeInst) { }

    default ItemStack onPickup(ISoldier<?> soldier, EntityItem item, ISoldierUpgradeInst upgradeInst) { return null; }

    default void onTick(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst) { }

    default void onLoad(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst, NBTTagCompound nbt) { }

    default void onSave(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst, NBTTagCompound nbt) { }

    default void onDestroyed(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst) { }

    default void onUpgradeDestroyed(ISoldier soldier, ISoldierUpgradeInst upgradeInst, ISoldierUpgrade destroyedUpgInst) { }

    default void onAttack(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst, Entity target, DamageSource dmgSource, float damage) { }

    default void onAttackSuccess(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst, Entity target) { }

    default void onDamaged(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst, Entity attacker, DamageSource dmgSource, MutableFloat damage) { }

    default void onDamagedSuccess(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst, Entity attacker, DamageSource dmgSource, float damage) { }

    default void onDeath(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst, DamageSource dmgSource, List<ItemStack> drops) { }

    enum EnumFunctionCalls {
        ON_PICKUP,
        ON_TICK,
        ON_OTHR_DESTROYED,
        ON_ATTACK,
        ON_DAMAGED,
        ON_DEATH,
        ON_ATTACK_SUCCESS,
        ON_DAMAGED_SUCCESS;

        public static final EnumFunctionCalls[] VALUES = values();
    }

}
