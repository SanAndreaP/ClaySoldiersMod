/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry.upgrade.core;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.attribute.AttributeHelper;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.IHandedUpgradeable;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.EnumUpgFunctions;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.ISoldierUpgrade;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.ISoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.UpgradeFunctions;
import de.sanandrew.mods.claysoldiers.registry.upgrade.Upgrades;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;
import java.util.UUID;

@UpgradeFunctions({EnumUpgFunctions.ON_DEATH, EnumUpgFunctions.ON_ATTACK_SUCCESS})
public class UpgradeIronIngot
        implements ISoldierUpgrade
{
    private static final ItemStack[] UPG_ITEMS = { new ItemStack(Items.IRON_INGOT, 1) };

    @Override
    public String getModId() {
        return CsmConstants.ID;
    }

    @Override
    public String getShortName() {
        return "ironingot";
    }

    @Nonnull
    @Override
    public ItemStack[] getStacks() {
        return UPG_ITEMS;
    }

    @Nonnull
    @Override
    public EnumUpgradeType getType(IHandedUpgradeable checker) {
        return EnumUpgradeType.CORE;
    }

    @Override
    public boolean syncData() {
        return true;
    }

    @Override
    public void onAdded(ISoldier<?> soldier, ItemStack stack, ISoldierUpgradeInst upgradeInst) {
        if( !soldier.getEntity().world.isRemote ) {
            AttributeHelper.tryApplyKnockbackResModifier(soldier.getEntity(), KB_RESISTANCE);
            soldier.getEntity().playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.2F, ((MiscUtils.RNG.randomFloat() - MiscUtils.RNG.randomFloat()) * 0.7F + 1.0F) * 2.0F);
            stack.shrink(1);
        }
    }

    @Override
    public void onAttackSuccess(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst, Entity target) {
        if( target instanceof ISoldier ) {
            ISoldier mark = (ISoldier) target;
            double xRatio = soldier.getEntity().posX - target.posX;
            double zRatio = soldier.getEntity().posZ - target.posZ;

            double prevMotionX = mark.getEntity().motionX;
            if( mark.hasUpgrade(Upgrades.CR_IRONINGOT, EnumUpgradeType.CORE) ) {
                mark.getEntity().knockBack(soldier.getEntity(), 1.6F, xRatio, zRatio);
            } else {
                mark.getEntity().knockBack(soldier.getEntity(), 0.8F, xRatio, zRatio);
            }
            mark.getEntity().motionX = prevMotionX;
        }
    }

    @Override
    public void onDeath(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst, DamageSource dmgSource, NonNullList<ItemStack> drops) {
        drops.add(upgradeInst.getSavedStack());
    }

    private static final AttributeModifier KB_RESISTANCE = new AttributeModifier(UUID.fromString("5983FC6D-F8B1-4217-8555-B5EB792281EC"), CsmConstants.ID + ".iron_ingot", 0.75D, 0);
}
