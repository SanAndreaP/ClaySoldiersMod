/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry.upgrade.hand;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgrade;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.entity.ai.attributes.AttributeModifierRnd;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;
import java.util.UUID;

public class UpgradeStick
        implements ISoldierUpgrade
{
    private static final ItemStack[] UPG_ITEMS = { new ItemStack(Items.STICK, 1) };
    private static final EnumFunctionCalls[] FUNC_CALLS = new EnumFunctionCalls[] { EnumFunctionCalls.ON_ATTACK_SUCCESS,
                                                                                    EnumFunctionCalls.ON_DEATH};
    private static final short MAX_USES = 20;

    @Override
    @Nonnull
    public ItemStack[] getStacks() {
        return UPG_ITEMS;
    }

    @Override
    @Nonnull
    public EnumFunctionCalls[] getFunctionCalls() {
        return FUNC_CALLS;
    }

    @Nonnull
    @Override
    public EnumUpgradeType getType(ISoldier<?> checker) {
        return EnumUpgradeType.MAIN_HAND;
    }

    @Override
    public boolean syncData() {
        return true;
    }

    @Override
    public void onAdded(ISoldier<?> soldier, ItemStack stack, ISoldierUpgradeInst upgradeInst) {
        if( !soldier.getEntity().world.isRemote ) {
            upgradeInst.getNbtData().setShort("uses", MAX_USES);
            soldier.getEntity().getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(ATTACK_DMG);
            soldier.getEntity().playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.2F, ((MiscUtils.RNG.randomFloat() - MiscUtils.RNG.randomFloat()) * 0.7F + 1.0F) * 2.0F);
            stack.shrink(1);
        }
    }

    @Override
    public void onAttackSuccess(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst, Entity target) {
        short uses = (short) (upgradeInst.getNbtData().getShort("uses") - 1);
        if( uses < 1 ) {
            soldier.getEntity().getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).removeModifier(ATTACK_DMG);
            soldier.destroyUpgrade(upgradeInst.getUpgrade(), upgradeInst.getUpgradeType(), false);
            soldier.getEntity().playSound(SoundEvents.ENTITY_ITEM_BREAK, 0.8F, 0.8F + MiscUtils.RNG.randomFloat() * 0.4F);
        } else {
            upgradeInst.getNbtData().setShort("uses", uses);
        }
    }

    @Override
    public void onDeath(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst, DamageSource dmgSource, NonNullList<ItemStack> drops) {
        if( upgradeInst.getNbtData().getShort("uses") >= MAX_USES ) {
            drops.add(upgradeInst.getSavedStack());
        }
    }

    private static final AttributeModifier ATTACK_DMG = new AttributeModifierRnd(UUID.fromString("7455374C-2247-4EE5-B163-19728E3C7B17"), CsmConstants.ID + ".stick_upg", 2.0D, 1.0D, 0);
}
