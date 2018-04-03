/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry.upgrade.hand;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgFunctions;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgradeThrowable;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.UpgradeFunctions;
import de.sanandrew.mods.claysoldiers.entity.projectile.EntityClayProjectile;
import de.sanandrew.mods.claysoldiers.entity.projectile.EntityProjectileEmerald;
import de.sanandrew.mods.claysoldiers.entity.projectile.EntityProjectileFirecharge;
import de.sanandrew.mods.claysoldiers.entity.projectile.EntityProjectileGravel;
import de.sanandrew.mods.claysoldiers.entity.projectile.EntityProjectileSnow;
import de.sanandrew.mods.claysoldiers.registry.upgrade.Upgrades;
import de.sanandrew.mods.sanlib.lib.util.ItemStackUtils;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import org.apache.commons.lang3.mutable.MutableFloat;

import javax.annotation.Nonnull;

@UpgradeFunctions({EnumUpgFunctions.ON_ATTACK, EnumUpgFunctions.ON_ATTACK_SUCCESS, EnumUpgFunctions.ON_DEATH})
public abstract class UpgradeThrowable
        implements ISoldierUpgradeThrowable
{
    @Override
    public String getModId() {
        return CsmConstants.ID;
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
            upgradeInst.getNbtData().setShort("uses", this.getMaxUses(stack));
            soldier.getEntity().playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.2F, ((MiscUtils.RNG.randomFloat() - MiscUtils.RNG.randomFloat()) * 0.7F + 1.0F) * 2.0F);
            stack.shrink(1);
        }
    }

    @Override
    public void onAttack(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst, Entity target, DamageSource dmgSource, MutableFloat damage) {
        if( !soldier.getEntity().world.isRemote ) {
            short uses = (short) (upgradeInst.getNbtData().getShort("uses") - 1);

            if( uses < 1 ) {
                soldier.destroyUpgrade(upgradeInst.getUpgrade(), upgradeInst.getUpgradeType(), false);
                soldier.getEntity().playSound(SoundEvents.ENTITY_ITEM_BREAK, 0.8F, 0.8F + MiscUtils.RNG.randomFloat() * 0.4F);
            } else {
                upgradeInst.getNbtData().setShort("uses", uses);
            }
        }
    }

    @Override
    public void onDeath(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst, DamageSource dmgSource, NonNullList<ItemStack> drops) {
        if( upgradeInst.getNbtData().getShort("uses") >= this.getMaxUses(upgradeInst.getSavedStack()) ) {
            drops.add(upgradeInst.getSavedStack());
        }
    }

    @Override
    public void onDestroyed(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst) {
        soldier.setMoveMultiplier(1.0F);
    }

    protected static Entity setHoming(Entity shooter, Entity in) {
        if( shooter instanceof ISoldier && ((ISoldier) shooter).hasUpgrade(Upgrades.EM_SUGARCANE, EnumUpgradeType.ENHANCEMENT) && in instanceof EntityClayProjectile ) {
            ((EntityClayProjectile) in).setHoming();
        }

        return in;
    }

    @Override
    @Nonnull
    public abstract ItemStack[] getStacks();

    protected abstract short getMaxUses(ItemStack savedStack);

    public static class Gravel
            extends UpgradeThrowable
    {
        private static final ItemStack[] ITEMS = { new ItemStack(Blocks.GRAVEL, 1) };

        @Override
        public String getShortName() {
            return "gravel";
        }

        @Nonnull
        @Override
        public ItemStack[] getStacks() {
            return ITEMS;
        }

        @Override
        protected short getMaxUses(ItemStack savedStack) {
            return 15;
        }

        @Nonnull
        @Override
        public Entity createProjectile(World world, Entity shooter, Entity target) {
            return setHoming(shooter, new EntityProjectileGravel(world, shooter, target));
        }
    }

    public static class Snow
            extends UpgradeThrowable
    {
        private static final ItemStack[] ITEMS = { new ItemStack(Blocks.SNOW, 1), new ItemStack(Blocks.SNOW_LAYER), new ItemStack(Items.SNOWBALL) };

        @Override
        public String getShortName() {
            return "snow";
        }

        @Nonnull
        @Override
        public ItemStack[] getStacks() {
            return ITEMS;
        }

        @Override
        protected short getMaxUses(ItemStack savedStack) {
            return (short) (ItemStackUtils.isItem(savedStack, Items.SNOWBALL) ? 5
                            : ItemStackUtils.isBlock(savedStack, Blocks.SNOW_LAYER) ? 10
                            : ItemStackUtils.isBlock(savedStack, Blocks.SNOW) ? 20
                            : 0);
        }

        @Nonnull
        @Override
        public Entity createProjectile(World world, Entity shooter, Entity target) {
            return setHoming(shooter, new EntityProjectileSnow(world, shooter, target));
        }
    }

    public static class Firecharge
            extends UpgradeThrowable
    {
        private static final ItemStack[] ITEMS = { new ItemStack(Items.FIRE_CHARGE) };

        @Override
        public String getShortName() {
            return "firecharge";
        }

        @Nonnull
        @Override
        public ItemStack[] getStacks() {
            return ITEMS;
        }

        @Override
        protected short getMaxUses(ItemStack savedStack) {
            return 15;
        }

        @Nonnull
        @Override
        public Entity createProjectile(World world, Entity shooter, Entity target) {
            return setHoming(shooter, new EntityProjectileFirecharge(world, shooter, target));
        }
    }

    public static class Emerald
            extends UpgradeThrowable
    {
        private static final ItemStack[] ITEMS = { new ItemStack(Items.EMERALD), new ItemStack(Blocks.EMERALD_BLOCK) };

        @Override
        public String getShortName() {
            return "emerald";
        }

        @Nonnull
        @Override
        public ItemStack[] getStacks() {
            return ITEMS;
        }

        @Override
        protected short getMaxUses(ItemStack savedStack) {
            return (short) (ItemStackUtils.isBlock(savedStack, Blocks.EMERALD_BLOCK) ? 45 : 5);
        }

        @Nonnull
        @Override
        public Entity createProjectile(World world, Entity shooter, Entity target) {
            return setHoming(shooter, new EntityProjectileEmerald(world, shooter, target));
        }
    }
}
