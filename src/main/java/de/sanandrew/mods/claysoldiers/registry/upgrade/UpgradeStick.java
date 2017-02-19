/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry.upgrade;

import de.sanandrew.mods.claysoldiers.api.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.soldier.IUpgrade;
import de.sanandrew.mods.claysoldiers.api.soldier.IUpgradeInst;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import org.apache.commons.lang3.mutable.MutableFloat;

public class UpgradeStick
        implements IUpgrade
{
    private static final ItemStack UPG_ITEM = new ItemStack(Items.STICK, 1);
    private static final EnumFunctionCalls[] FUNC_CALLS = new EnumFunctionCalls[] { EnumFunctionCalls.ON_PICKUP,
                                                                                    EnumFunctionCalls.ON_ATTACK,
                                                                                    EnumFunctionCalls.ON_ATTACK_SUCCESS,
                                                                                    EnumFunctionCalls.ON_DEATH};
    private static final byte MAX_USAGES = 1;

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
        return stack.splitStack(1);
    }

    @Override
    public void onAttack(ISoldier<?> soldier, IUpgradeInst upgInstance, Entity target, DamageSource dmgSource, MutableFloat damage) {
        damage.add(1.0F + MiscUtils.RNG.randomFloat() * 0.5F);
    }

    @Override
    public void onAttackSuccess(ISoldier<?> soldier, IUpgradeInst upgInstance, Entity target) {
        byte uses = (byte) (upgInstance.getNbtData().getByte("uses") - 1);
        if( uses < 1 ) {
            soldier.destroyUpgrade(upgInstance.getUpgrade());
        } else {
            upgInstance.getNbtData().setByte("uses", uses);
        }
    }

    @Override
    public void onDeath(ISoldier<?> soldier, IUpgradeInst upgInstance, ItemStack stack) {
        stack.stackSize = upgInstance.getNbtData().getByte("uses") < MAX_USAGES ? 0 : 1;
    }
}
