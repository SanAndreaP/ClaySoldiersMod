/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry.upgrade.hand;

import de.sanandrew.mods.claysoldiers.api.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgrade;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.registry.upgrade.UpgradeRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class UpgradeArrow
        implements ISoldierUpgrade
{
    private static final ItemStack[] UPG_ITEMS = { new ItemStack(Items.ARROW, 1) };
    private static final EnumFunctionCalls[] FUNC_CALLS = new EnumFunctionCalls[0];

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
    public boolean checkPickupable(ISoldier<?> soldier, ItemStack stack) {
        return !soldier.hasUpgrade(UpgradeRegistry.MH_STICK, EnumUpgradeType.MAIN_HAND) && !soldier.hasUpgrade(UpgradeRegistry.MC_FLINT, EnumUpgradeType.MISC);
    }

    @Override
    public void onAdded(ISoldier<?> soldier, ItemStack stack, ISoldierUpgradeInst upgradeInst) {
        if( !soldier.getEntity().world.isRemote ) {
            soldier.addUpgrade(UpgradeRegistry.INSTANCE.getUpgrade(UpgradeRegistry.MH_STICK), EnumUpgradeType.MAIN_HAND, new ItemStack(Items.STICK, 1));
            soldier.addUpgrade(UpgradeRegistry.INSTANCE.getUpgrade(UpgradeRegistry.MC_FLINT), EnumUpgradeType.ENHANCEMENT, new ItemStack(Items.FLINT, 1));
            soldier.destroyUpgrade(upgradeInst.getUpgrade(), upgradeInst.getUpgradeType(), true);
            soldier.getEntity().entityDropItem(new ItemStack(Items.FEATHER, 1), 0.0F);
            soldier.getEntity().entityDropItem(new ItemStack(Items.FLINT, 1), 0.0F);
            stack.stackSize--;
        }
    }
}
