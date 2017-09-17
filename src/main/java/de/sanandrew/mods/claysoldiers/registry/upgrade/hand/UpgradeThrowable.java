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
import de.sanandrew.mods.claysoldiers.entity.soldier.EntityClaySoldier;
import de.sanandrew.mods.claysoldiers.entity.ai.EntityAISoldierAttack;
import de.sanandrew.mods.claysoldiers.entity.projectile.EntityProjectileFirecharge;
import de.sanandrew.mods.claysoldiers.entity.projectile.EntityProjectileGravel;
import de.sanandrew.mods.claysoldiers.entity.projectile.EntityProjectileSnow;
import de.sanandrew.mods.sanlib.lib.util.EntityUtils;
import de.sanandrew.mods.sanlib.lib.util.ItemStackUtils;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import org.apache.commons.lang3.mutable.MutableFloat;

import javax.annotation.Nonnull;

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
    public void onAdded(ISoldier<?> soldier, ItemStack stack, ISoldierUpgradeInst upgradeInst) {
        if( !soldier.getEntity().world.isRemote ) {
            upgradeInst.getNbtData().setByte("uses", this.getMaxUses(stack));
            soldier.getEntity().playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.2F, ((MiscUtils.RNG.randomFloat() - MiscUtils.RNG.randomFloat()) * 0.7F + 1.0F) * 2.0F);
            soldier.getEntity().tasks.addTask(2, new EntityAISoldierAttack.Ranged((EntityClaySoldier) soldier, 1.0F));
            stack.shrink(1);
        }
    }

    @Override
    public void onLoad(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst, NBTTagCompound nbt) {
        soldier.getEntity().tasks.addTask(2, new EntityAISoldierAttack.Ranged((EntityClaySoldier) soldier, 1.0F));
    }

    @Override
    public void onDestroyed(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst) {
        EntityUtils.getAisFromTaskList(soldier.getEntity().tasks.taskEntries, EntityAISoldierAttack.Ranged.class).forEach(soldier::removeTask);
        soldier.setMoveMultiplier(1.0F);
    }

    @Override
    public void onAttack(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst, Entity target, DamageSource dmgSource, MutableFloat damage) {
        byte uses = (byte) (upgradeInst.getNbtData().getByte("uses") - 1);

        Entity proj = this.createProjectile(soldier.getEntity().world, soldier.getEntity(), target);
        soldier.getEntity().world.spawnEntity(proj);

        if( uses < 1 ) {
            soldier.destroyUpgrade(upgradeInst.getUpgrade(), upgradeInst.getUpgradeType(), false);
            soldier.getEntity().playSound(SoundEvents.ENTITY_ITEM_BREAK, 0.8F, 0.8F + MiscUtils.RNG.randomFloat() * 0.4F);
        } else {
            upgradeInst.getNbtData().setByte("uses", uses);
        }
    }

    @Override
    public void onDeath(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst, DamageSource dmgSource, NonNullList<ItemStack> drops) {
        if( upgradeInst.getNbtData().getByte("uses") >= this.getMaxUses(upgradeInst.getSavedStack()) ) {
            drops.add(upgradeInst.getSavedStack());
        }
    }

    @Override
    @Nonnull
    public abstract ItemStack[] getStacks();

    protected abstract byte getMaxUses(ItemStack savedStack);

    @Nonnull
    protected abstract Entity createProjectile(World world, Entity shooter, Entity target);

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
        protected byte getMaxUses(ItemStack savedStack) {
            return 15;
        }

        @Nonnull
        @Override
        protected Entity createProjectile(World world, Entity shooter, Entity target) {
            return new EntityProjectileGravel(world, shooter, target);
        }
    }

    public static class Snow
            extends UpgradeThrowable
    {
        private static final ItemStack[] ITEMS = { new ItemStack(Blocks.SNOW, 1), new ItemStack(Blocks.SNOW_LAYER), new ItemStack(Items.SNOWBALL) };

        @Nonnull
        @Override
        public ItemStack[] getStacks() {
            return ITEMS;
        }

        @Override
        protected byte getMaxUses(ItemStack savedStack) {
            return (byte) (ItemStackUtils.isItem(savedStack, Items.SNOWBALL) ? 5
                            : ItemStackUtils.isBlock(savedStack, Blocks.SNOW_LAYER) ? 10
                            : ItemStackUtils.isBlock(savedStack, Blocks.SNOW) ? 20
                            : 0);
        }

        @Nonnull
        @Override
        protected Entity createProjectile(World world, Entity shooter, Entity target) {
            return new EntityProjectileSnow(world, shooter, target);
        }
    }

    public static class Firecharge
            extends UpgradeThrowable
    {
        private static final ItemStack[] ITEMS = { new ItemStack(Items.FIRE_CHARGE) };

        @Nonnull
        @Override
        public ItemStack[] getStacks() {
            return ITEMS;
        }

        @Override
        protected byte getMaxUses(ItemStack savedStack) {
            return 15;
        }

        @Nonnull
        @Override
        protected Entity createProjectile(World world, Entity shooter, Entity target) {
            return new EntityProjectileFirecharge(world, shooter, target);
        }
    }
}
