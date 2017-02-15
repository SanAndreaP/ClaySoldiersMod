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
    ItemStack getItem();

    ItemStack onPickup(ISoldier<?> soldier, ItemStack stack, IUpgradeInst upgInstance);

    void onTick(ISoldier<?> soldier, IUpgradeInst upgInstance);

    void onLoad(ISoldier<?> soldier, IUpgradeInst upgInstance, NBTTagCompound upgNbt);

    void onSave(ISoldier<?> soldier, IUpgradeInst upgInstance, NBTTagCompound upgNbt);

    void onDestroyed(ISoldier<?> soldier, IUpgradeInst upgInstance);

    void onAttack(ISoldier<?> soldier, IUpgradeInst upgInstance, Entity target, DamageSource dmgSource, MutableFloat damage);

    void onDamaged(ISoldier<?> soldier, IUpgradeInst upgInstance, Entity attacker, DamageSource dmgSource, MutableFloat damage);

    void onDeath(ISoldier<?> soldier, IUpgradeInst upgInstance, ItemStack stack);
}
