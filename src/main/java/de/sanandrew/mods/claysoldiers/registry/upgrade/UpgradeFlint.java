/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry.upgrade;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.soldier.ISoldierUpgrade;
import de.sanandrew.mods.claysoldiers.api.soldier.ISoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.registry.UpgradeRegistry;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import de.sanandrew.mods.sanlib.lib.util.UuidUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.UUID;

public class UpgradeFlint
        implements ISoldierUpgrade
{
    private static final ItemStack UPG_ITEM = new ItemStack(Items.FLINT, 1);
    private static final EnumFunctionCalls[] FUNC_CALLS = new EnumFunctionCalls[] { EnumFunctionCalls.ON_OTHR_DESTROYED };

    @Override
    public ItemStack getStack() {
        return UPG_ITEM;
    }

    @Override
    public boolean checkPickupable(ISoldier<?> soldier, ItemStack stack) {
        return soldier.hasUpgrade(UpgradeRegistry.MH_STICK);
    }

    @Override
    public EnumFunctionCalls[] getFunctionCalls() {
        return FUNC_CALLS;
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public boolean syncData() {
        return true;
    }

    @Override
    public void onAdded(ISoldier<?> soldier, ItemStack stack, ISoldierUpgradeInst upgInstance) {
        if( !soldier.getEntity().world.isRemote ) {
            soldier.getEntity().getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(SOLDIER_FLINT_DMG);
            soldier.getEntity().playSound(SoundEvents.BLOCK_WOOD_BUTTON_CLICK_ON, 0.2F, ((MiscUtils.RNG.randomFloat() - MiscUtils.RNG.randomFloat()) * 0.7F + 1.0F) * 2.0F);
        }
    }

    @Override
    public void onUpgradeDestroyed(ISoldier soldier, ISoldierUpgradeInst upgradeInst, ISoldierUpgrade destroyed) {
        if( !soldier.getEntity().world.isRemote && UuidUtils.areUuidsEqual(UpgradeRegistry.INSTANCE.getId(destroyed), UpgradeRegistry.MH_STICK) ) {
            soldier.getEntity().getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).removeModifier(SOLDIER_FLINT_DMG);
            soldier.destroyUpgrade(upgradeInst.getUpgrade());
        }
    }

    public static final AttributeModifier SOLDIER_FLINT_DMG = new AttributeModifier(UUID.fromString("E9BBBE04-75CC-48E5-9F5D-528D116CF1B1"), CsmConstants.ID + ".flint_upg", 0.5D, 1) {
        @Override
        public double getAmount() {
            return 0.5D + MiscUtils.RNG.randomFloat() * 0.5D;
        }
    };
}
