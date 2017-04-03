/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry.upgrade.hand;

import de.sanandrew.mods.claysoldiers.api.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgrade;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.entity.EntityClaySoldier;
import de.sanandrew.mods.claysoldiers.entity.ai.EntityAISoldierAttack;
import de.sanandrew.mods.claysoldiers.entity.projectile.EntityProjectileGravel;
import de.sanandrew.mods.sanlib.lib.util.EntityUtils;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;

import javax.annotation.Nonnull;
import java.util.List;

public abstract class UpgradeThrowable
        implements ISoldierUpgrade
{
    private static final EnumFunctionCalls[] FUNC_CALLS = new EnumFunctionCalls[] { EnumFunctionCalls.ON_ATTACK,
                                                                                    EnumFunctionCalls.ON_ATTACK_SUCCESS,
                                                                                    EnumFunctionCalls.ON_DEATH};

    @Override
    @Nonnull
    public EnumFunctionCalls[] getFunctionCalls() {
        return FUNC_CALLS;
    }

    @Nonnull
    @Override
    public EnumUpgradeType getType(ISoldier<?> checker) {
        return EnumUpgradeType.OFF_HAND;
    }

    @Override
    public boolean syncData() {
        return true;
    }

    @Override
    public void onAdded(ISoldier<?> soldier, ItemStack stack, ISoldierUpgradeInst upgInstance) {
        if( !soldier.getEntity().world.isRemote ) {
            upgInstance.getNbtData().setByte("uses", this.getMaxUses());
            soldier.getEntity().playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.2F, ((MiscUtils.RNG.randomFloat() - MiscUtils.RNG.randomFloat()) * 0.7F + 1.0F) * 2.0F);
            soldier.getEntity().tasks.addTask(2, new EntityAISoldierAttack.Ranged((EntityClaySoldier) soldier, 1.0F));
            stack.stackSize--;
        }
    }

    @Override
    public void onLoad(ISoldier<?> soldier, ISoldierUpgradeInst upgInstance, NBTTagCompound upgNbt) {
        soldier.getEntity().tasks.addTask(2, new EntityAISoldierAttack.Ranged((EntityClaySoldier) soldier, 1.0F));
    }

    @Override
    public void onDestroyed(ISoldier<?> soldier, ISoldierUpgradeInst upgInstance) {
        EntityUtils.getAisFromTaskList(soldier.getEntity().tasks.taskEntries, EntityAISoldierAttack.Ranged.class).forEach(soldier::removeTask);
        soldier.setMoveForwardMultiplier(1.0F);
    }

    @Override
    public void onAttack(ISoldier<?> soldier, ISoldierUpgradeInst upgInstance, Entity target, DamageSource dmgSource, float damage) {
        byte uses = (byte) (upgInstance.getNbtData().getByte("uses") - 1);

        EntityProjectileGravel proj = new EntityProjectileGravel(soldier.getEntity().world, soldier.getEntity(), target);
        soldier.getEntity().world.spawnEntityInWorld(proj);

        if( uses < 1 ) {
            soldier.destroyUpgrade(upgInstance.getUpgrade(), upgInstance.getUpgradeType(), false);
            soldier.getEntity().playSound(SoundEvents.ENTITY_ITEM_BREAK, 0.8F, 0.8F + MiscUtils.RNG.randomFloat() * 0.4F);
        } else {
            upgInstance.getNbtData().setByte("uses", uses);
        }
    }

    @Override
    public void onDeath(ISoldier<?> soldier, ISoldierUpgradeInst upgInstance, List<ItemStack> drops) {
        if( upgInstance.getNbtData().getByte("uses") >= this.getMaxUses() ) {
            drops.add(upgInstance.getSavedStack());
        }
    }

    @Override
    @Nonnull
    public abstract ItemStack[] getStacks();

    protected abstract byte getMaxUses();

    public static class Gravel
            extends UpgradeThrowable
    {
        private static final ItemStack[] ITEMS = { new ItemStack(Blocks.GRAVEL, 1) };

        @Nonnull
        @Override
        public ItemStack[] getStacks() {
            return ITEMS;
        }

        @Override
        protected byte getMaxUses() {
            return 15;
        }
    }
}
