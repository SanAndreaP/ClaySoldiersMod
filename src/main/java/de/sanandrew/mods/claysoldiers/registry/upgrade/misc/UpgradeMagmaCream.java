/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry.upgrade.misc;

import de.sanandrew.mods.claysoldiers.api.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgFunctions;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgrade;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.registry.effect.EffectTimeBomb;
import de.sanandrew.mods.claysoldiers.registry.upgrade.Upgrades;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.UpgradeFunctions;

@UpgradeFunctions({EnumUpgFunctions.ON_DEATH})
public class UpgradeMagmaCream
        implements ISoldierUpgrade
{
    public static final int MAX_TIME_DETONATION = 40;
    private static final ItemStack[] UPG_ITEMS = { new ItemStack(Items.MAGMA_CREAM, 1) };

    @Override
    @Nonnull
    public ItemStack[] getStacks() {
        return UPG_ITEMS;
    }

    @Nonnull
    @Override
    public EnumUpgradeType getType(ISoldier<?> checker) {
        return EnumUpgradeType.MISC;
    }

    @Override
    public boolean isApplicable(ISoldier<?> soldier, ItemStack stack) {
        return !soldier.hasUpgrade(Upgrades.MC_GUNPOWDER, EnumUpgradeType.MISC) && !soldier.hasUpgrade(Upgrades.MC_FIREWORKSTAR, EnumUpgradeType.MISC);
    }

    @Override
    public boolean syncData() {
        return true;
    }

    @Override
    public void onAdded(ISoldier<?> soldier, ItemStack stack, ISoldierUpgradeInst upgradeInst) {
        if( !soldier.getEntity().world.isRemote ) {
            soldier.getEntity().playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.2F, ((MiscUtils.RNG.randomFloat() - MiscUtils.RNG.randomFloat()) * 0.7F + 1.0F) * 2.0F);
            stack.shrink(1);
        }
    }

    @Override
    public void onDeath(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst, DamageSource dmgSource, NonNullList<ItemStack> drops) {
        if( dmgSource.getTrueSource() instanceof ISoldier && !dmgSource.isFireDamage() && !dmgSource.isProjectile() ) {
            ISoldier target = (ISoldier) dmgSource.getTrueSource();
            if( MiscUtils.RNG.randomBool() || !target.hasUpgrade(Upgrades.EM_IRONBLOCK, EnumUpgradeType.ENHANCEMENT) ) {
                target.addEffect(EffectTimeBomb.INSTANCE, MAX_TIME_DETONATION);
            }
        } else {
            drops.add(upgradeInst.getSavedStack());
        }
    }
}
