/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry.upgrade.misc;

import de.sanandrew.mods.claysoldiers.api.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgrade;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.registry.effect.EffectRedstone;
import de.sanandrew.mods.claysoldiers.registry.effect.Effects;
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

public class UpgradeRedstone
        implements ISoldierUpgrade
{
    private static final ItemStack[] UPG_ITEMS = { new ItemStack(Items.REDSTONE, 1), new ItemStack(Blocks.REDSTONE_BLOCK) };
    private static final EnumFunctionCalls[] FUNC_CALLS = new EnumFunctionCalls[] { EnumFunctionCalls.ON_DEATH, EnumFunctionCalls.ON_ATTACK_SUCCESS };
    private static final short MAX_USES = 4;

    @Nonnull
    @Override
    public ItemStack[] getStacks() {
        return UPG_ITEMS;
    }

    @Nonnull
    @Override
    public EnumFunctionCalls[] getFunctionCalls() {
        return FUNC_CALLS;
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
            upgradeInst.getNbtData().setBoolean("isItem", ItemStackUtils.isItem(stack, Items.REDSTONE));
            soldier.getEntity().playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.2F, ((MiscUtils.RNG.randomFloat() - MiscUtils.RNG.randomFloat()) * 0.7F + 1.0F) * 2.0F);

            if( ItemStackUtils.isItem(stack, Items.REDSTONE) ) {
                stack.shrink(1);
            }
        }
    }

    @Override
    public void onAttackSuccess(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst, Entity target) {
        if( !soldier.getEntity().world.isRemote && target instanceof ISoldier ) {
            ISoldier targetS = (ISoldier) target;
            if( !targetS.hasEffect(Effects.BLINDING_REDSTONE) ) {
                targetS.addEffect(EffectRedstone.INSTANCE, 60);
                targetS.getEntity().setAttackTarget(null);
                soldier.getEntity().playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 0.2F, ((MiscUtils.RNG.randomFloat() - MiscUtils.RNG.randomFloat()) * 0.7F + 1.0F) * 2.0F);

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
