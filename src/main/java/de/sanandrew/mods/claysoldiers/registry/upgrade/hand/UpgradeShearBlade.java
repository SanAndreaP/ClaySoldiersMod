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
import de.sanandrew.mods.claysoldiers.registry.ItemRegistry;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

public class UpgradeShearBlade
        implements ISoldierUpgrade
{
    private static final ItemStack[] UPG_ITEMS = { new ItemStack(ItemRegistry.shear_blade, 1) };
    private static final EnumFunctionCalls[] FUNC_CALLS = new EnumFunctionCalls[] { EnumFunctionCalls.ON_ATTACK_SUCCESS,
                                                                                    EnumFunctionCalls.ON_DEATH};
    private static final byte MAX_USAGES = 25;

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
        return checker.hasMainHandUpgrade() ? EnumUpgradeType.OFF_HAND : EnumUpgradeType.MAIN_HAND;
    }

    @Override
    public boolean syncData() {
        return true;
    }

    @Override
    public void onAdded(ISoldier<?> soldier, ItemStack stack, ISoldierUpgradeInst upgInstance) {
        if( !soldier.getEntity().world.isRemote ) {
            upgInstance.getNbtData().setByte("uses", MAX_USAGES);
            AttributeModifier modifier = upgInstance.getUpgradeType() == EnumUpgradeType.MAIN_HAND ? SOLDIER_BLADE_DMG_1 : SOLDIER_BLADE_DMG_2;
            soldier.getEntity().getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(modifier);
            soldier.getEntity().playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.2F, ((MiscUtils.RNG.randomFloat() - MiscUtils.RNG.randomFloat()) * 0.7F + 1.0F) * 2.0F);
            stack.stackSize--;
        }
    }

    @Override
    public void onAttackSuccess(ISoldier<?> soldier, ISoldierUpgradeInst upgInstance, Entity target) {
        byte uses = (byte) (upgInstance.getNbtData().getByte("uses") - 1);
        if( uses < 1 ) {
            AttributeModifier modifier = upgInstance.getUpgradeType() == EnumUpgradeType.MAIN_HAND ? SOLDIER_BLADE_DMG_1 : SOLDIER_BLADE_DMG_2;
            soldier.getEntity().getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).removeModifier(modifier);
            soldier.destroyUpgrade(upgInstance.getUpgrade(), upgInstance.getUpgradeType(), false);
            soldier.getEntity().playSound(SoundEvents.ENTITY_ITEM_BREAK, 0.8F, 0.8F + MiscUtils.RNG.randomFloat() * 0.4F);
        } else {
            upgInstance.getNbtData().setByte("uses", uses);
        }
    }

    @Override
    public void onDeath(ISoldier<?> soldier, ISoldierUpgradeInst upgInstance, List<ItemStack> drops) {
        if( upgInstance.getNbtData().getByte("uses") >= MAX_USAGES ) {
            drops.add(upgInstance.getSavedStack());
        }
    }

    public static final AttributeModifier SOLDIER_BLADE_DMG_1 = new AttributeModifier(UUID.fromString("DA52E85C-40A0-4E0C-B719-1629A138B4E3"), CsmConstants.ID + ".blade_upg1", 1.0D, 1) {
        @Override
        public double getAmount() {
            return 1.0D + MiscUtils.RNG.randomFloat() * 1.0D;
        }
    };

    public static final AttributeModifier SOLDIER_BLADE_DMG_2 = new AttributeModifier(UUID.fromString("27D57300-80A0-4AFE-937C-9F5208AADB0D"), CsmConstants.ID + ".blade_upg2", 1.0D, 1) {
        @Override
        public double getAmount() {
            return 1.0D + MiscUtils.RNG.randomFloat() * 1.0D;
        }
    };
}
