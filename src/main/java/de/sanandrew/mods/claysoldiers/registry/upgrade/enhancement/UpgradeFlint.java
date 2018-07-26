/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry.upgrade.enhancement;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.attribute.AttributeHelper;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.IHandedUpgradeable;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.EnumUpgFunctions;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.ISoldierUpgrade;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.ISoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.UpgradeFunctions;
import de.sanandrew.mods.claysoldiers.registry.upgrade.UpgradeRegistry;
import de.sanandrew.mods.claysoldiers.registry.upgrade.Upgrades;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import de.sanandrew.mods.sanlib.lib.util.UuidUtils;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.UUID;

@UpgradeFunctions({EnumUpgFunctions.ON_OTHR_DESTROYED})
public class UpgradeFlint
        implements ISoldierUpgrade
{
    private static final ItemStack[] UPG_ITEMS = { new ItemStack(Items.FLINT, 1) };

    @Override
    public String getModId() {
        return CsmConstants.ID;
    }

    @Override
    public String getShortName() {
        return "flint";
    }

    @Override
    @Nonnull
    public EnumUpgradeType getType(IHandedUpgradeable checker) {
        return EnumUpgradeType.ENHANCEMENT;
    }

    @Override
    @Nonnull
    public ItemStack[] getStacks() {
        return UPG_ITEMS;
    }

    @Override
    public boolean isApplicable(ISoldier<?> soldier, ItemStack stack) {
        return soldier.hasUpgrade(Upgrades.MH_STICK, EnumUpgradeType.MAIN_HAND);
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
    public void onAdded(ISoldier<?> soldier, ItemStack stack, ISoldierUpgradeInst upgradeInst) {
        if( !soldier.getEntity().world.isRemote ) {
            AttributeHelper.tryApplyAttackDmgModifier(soldier.getEntity(), SOLDIER_FLINT_DMG);
            soldier.getEntity().playSound(SoundEvents.BLOCK_WOOD_BUTTON_CLICK_ON, 0.2F, ((MiscUtils.RNG.randomFloat() - MiscUtils.RNG.randomFloat()) * 0.7F + 1.0F) * 2.0F);
        }
    }

    @Override
    public void onUpgradeDestroyed(ISoldier soldier, ISoldierUpgradeInst upgradeInst, ISoldierUpgradeInst destroyedUpgInst) {
        if( !soldier.getEntity().world.isRemote && UuidUtils.areUuidsEqual(UpgradeRegistry.INSTANCE.getId(destroyedUpgInst.getUpgrade()), Upgrades.MH_STICK) ) {
            AttributeHelper.tryRemoveAttackDmgModifier(soldier.getEntity(), SOLDIER_FLINT_DMG);
            soldier.destroyUpgrade(this, this.getType(soldier), true);
        }
    }

    public static final AttributeModifier SOLDIER_FLINT_DMG = new AttributeModifier(UUID.fromString("E9BBBE04-75CC-48E5-9F5D-528D116CF1B1"), CsmConstants.ID + ".flint_upg", 2.0D, 1);
}
