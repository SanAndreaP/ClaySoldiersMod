/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry.upgrade;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.soldier.IUpgrade;
import de.sanandrew.mods.claysoldiers.api.soldier.IUpgradeInst;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import org.apache.commons.lang3.mutable.MutableFloat;

import java.util.UUID;

public class UpgradeStick
        implements IUpgrade
{
    private static final ItemStack UPG_ITEM = new ItemStack(Items.STICK, 1);
    private static final EnumFunctionCalls[] FUNC_CALLS = new EnumFunctionCalls[] { EnumFunctionCalls.ON_PICKUP,
                                                                                    EnumFunctionCalls.ON_ATTACK_SUCCESS,
                                                                                    EnumFunctionCalls.ON_DEATH};
    private static final byte MAX_USAGES = 20;

    @Override
    public ItemStack getItem() {
        return UPG_ITEM;
    }

    @Override
    public EnumFunctionCalls[] getFunctionCalls() {
        return FUNC_CALLS;
    }

    @Override
    public boolean syncData() {
        return true;
    }

    @Override
    public ItemStack onPickup(ISoldier<?> soldier, ItemStack stack, IUpgradeInst upgInstance) {
        upgInstance.getNbtData().setByte("uses", MAX_USAGES);
        soldier.getEntity().getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(SOLDIER_STICK_DMG);
        return stack.splitStack(1);
    }

    @Override
    public void onAttackSuccess(ISoldier<?> soldier, IUpgradeInst upgInstance, Entity target) {
        byte uses = (byte) (upgInstance.getNbtData().getByte("uses") - 1);
        if( uses < 1 ) {
            soldier.getEntity().getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).removeModifier(SOLDIER_STICK_DMG);
            soldier.destroyUpgrade(upgInstance.getUpgrade());
        } else {
            upgInstance.getNbtData().setByte("uses", uses);
        }
    }

    @Override
    public void onDeath(ISoldier<?> soldier, IUpgradeInst upgInstance, ItemStack stack) {
        stack.stackSize = upgInstance.getNbtData().getByte("uses") < MAX_USAGES ? 0 : 1;
    }

    public static final AttributeModifier SOLDIER_STICK_DMG = new AttributeModifier(UUID.fromString("7455374C-2247-4EE5-B163-19728E3C7B17"), CsmConstants.ID + ".stick_upg", 1.0D, 1) {
        @Override
        public double getAmount() {
            return 1.0D + MiscUtils.RNG.randomFloat() * 0.5D;
        }
    };
}
