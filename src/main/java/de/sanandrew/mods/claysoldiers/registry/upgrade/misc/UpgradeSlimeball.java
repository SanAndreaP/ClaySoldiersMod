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
import de.sanandrew.mods.claysoldiers.registry.effect.EffectSlimeball;
import de.sanandrew.mods.claysoldiers.registry.effect.Effects;
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

import javax.annotation.Nonnull;

import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.UpgradeFunctions;

@UpgradeFunctions({EnumUpgFunctions.ON_DEATH, EnumUpgFunctions.ON_ATTACK_SUCCESS})
public class UpgradeSlimeball
        implements ISoldierUpgrade
{
    private static final ItemStack[] UPG_ITEMS = { new ItemStack(Items.SLIME_BALL, 1), new ItemStack(Blocks.SLIME_BLOCK) };
    private static final short MAX_USES = 5;

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
    public boolean syncData() {
        return true;
    }

    @Override
    public void onAdded(ISoldier<?> soldier, ItemStack stack, ISoldierUpgradeInst upgradeInst) {
        if( !soldier.getEntity().world.isRemote ) {
            upgradeInst.getNbtData().setShort("uses", MAX_USES);
            upgradeInst.getNbtData().setBoolean("isItem", ItemStackUtils.isItem(stack, Items.SLIME_BALL));
            soldier.getEntity().playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.2F, ((MiscUtils.RNG.randomFloat() - MiscUtils.RNG.randomFloat()) * 0.7F + 1.0F) * 2.0F);

            if( ItemStackUtils.isItem(stack, Items.SLIME_BALL) ) {
                stack.shrink(1);
            }
        }
    }

    @Override
    public void onAttackSuccess(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst, Entity target) {
        if( !soldier.getEntity().world.isRemote && target instanceof ISoldier ) {
            ISoldier targetS = (ISoldier) target;
            if( !targetS.hasEffect(Effects.STICKING_SLIMEBALL) ) {
                if( MiscUtils.RNG.randomBool() || !targetS.hasUpgrade(Upgrades.EM_IRONBLOCK, EnumUpgradeType.ENHANCEMENT) ) {
                    targetS.addEffect(EffectSlimeball.INSTANCE, 60);
                }
                soldier.getEntity().playSound(SoundEvents.ENTITY_SLIME_ATTACK, 0.2F, ((MiscUtils.RNG.randomFloat() - MiscUtils.RNG.randomFloat()) * 0.7F + 1.0F) * 2.0F);

                short uses = (short) (upgradeInst.getNbtData().getShort("uses") - 1);
                if( uses < 1 ) {
                    soldier.destroyUpgrade(upgradeInst.getUpgrade(), upgradeInst.getUpgradeType(), false);
                } else {
                    upgradeInst.getNbtData().setShort("uses", uses);
                }
            }
        }
    }

    @Override
    public void onDeath(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst, DamageSource dmgSource, NonNullList<ItemStack> drops) {
        if( upgradeInst.getNbtData().getBoolean("isItem") && upgradeInst.getNbtData().getShort("uses") >= MAX_USES ) {
            drops.add(upgradeInst.getSavedStack());
        }
    }
}
