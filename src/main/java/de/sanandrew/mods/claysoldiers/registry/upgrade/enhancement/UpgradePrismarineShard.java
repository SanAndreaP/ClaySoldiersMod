/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry.upgrade.enhancement;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgFunctions;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgrade;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.UpgradeFunctions;
import de.sanandrew.mods.claysoldiers.registry.upgrade.UpgradeRegistry;
import de.sanandrew.mods.claysoldiers.registry.upgrade.Upgrades;
import de.sanandrew.mods.sanlib.lib.util.EntityUtils;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import de.sanandrew.mods.sanlib.lib.util.UuidUtils;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.UUID;

@UpgradeFunctions({EnumUpgFunctions.ON_OTHR_DESTROYED, EnumUpgFunctions.ON_UPGRADE_ADDED})
public class UpgradePrismarineShard
        implements ISoldierUpgrade
{
    private static final ItemStack[] UPG_ITEMS = { new ItemStack(Items.PRISMARINE_SHARD, 1) };

    @Override
    public String getModId() {
        return CsmConstants.ID;
    }

    @Override
    public String getShortName() {
        return "prismshard";
    }

    @Override
    @Nonnull
    public EnumUpgradeType getType(ISoldier<?> checker) {
        return EnumUpgradeType.ENHANCEMENT;
    }

    @Override
    @Nonnull
    public ItemStack[] getStacks() {
        return UPG_ITEMS;
    }

    @Override
    public boolean isApplicable(ISoldier<?> soldier, ItemStack stack) {
        return soldier.hasUpgrade(Upgrades.MOH_SHEARBLADE, EnumUpgradeType.MAIN_HAND) || soldier.hasUpgrade(Upgrades.MOH_SHEARBLADE, EnumUpgradeType.OFF_HAND);
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
            if( soldier.hasUpgrade(Upgrades.MOH_SHEARBLADE, EnumUpgradeType.MAIN_HAND) ) {
                soldier.getEntity().getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(SHEAR_DMG_M);
            }
            if( soldier.hasUpgrade(Upgrades.MOH_SHEARBLADE, EnumUpgradeType.OFF_HAND) ) {
                soldier.getEntity().getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(SHEAR_DMG_O);
            }
            soldier.getEntity().playSound(SoundEvents.BLOCK_WOOD_BUTTON_CLICK_ON, 0.2F, ((MiscUtils.RNG.randomFloat() - MiscUtils.RNG.randomFloat()) * 0.7F + 1.0F) * 2.0F);
        }
    }

    @Override
    public void onUpgradeAdded(ISoldier soldier, ISoldierUpgradeInst upgradeInst, ISoldierUpgradeInst addedUpgInst) {
        if( !soldier.getEntity().world.isRemote ) {
            if( UuidUtils.areUuidsEqual(UpgradeRegistry.INSTANCE.getId(addedUpgInst.getUpgrade()), Upgrades.MOH_SHEARBLADE) ) {
                if( addedUpgInst.getUpgradeType() == EnumUpgradeType.MAIN_HAND ) {
                    soldier.getEntity().getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(SHEAR_DMG_M);
                }
                if( addedUpgInst.getUpgradeType() == EnumUpgradeType.OFF_HAND ) {
                    soldier.getEntity().getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(SHEAR_DMG_O);
                }
            }
        }
    }

    @Override
    public void onUpgradeDestroyed(ISoldier soldier, ISoldierUpgradeInst upgradeInst, ISoldierUpgradeInst destroyedUpgInst) {
        if( !soldier.getEntity().world.isRemote ) {
            if( UuidUtils.areUuidsEqual(UpgradeRegistry.INSTANCE.getId(destroyedUpgInst.getUpgrade()), Upgrades.MOH_SHEARBLADE) ) {
                if( destroyedUpgInst.getUpgradeType() == EnumUpgradeType.MAIN_HAND ) {
                    soldier.getEntity().getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).removeModifier(SHEAR_DMG_M);
                }
                if( destroyedUpgInst.getUpgradeType() == EnumUpgradeType.OFF_HAND ) {
                    soldier.getEntity().getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).removeModifier(SHEAR_DMG_O);
                }
                if( !soldier.hasUpgrade(Upgrades.MOH_SHEARBLADE, EnumUpgradeType.MAIN_HAND) && !soldier.hasUpgrade(Upgrades.MOH_SHEARBLADE, EnumUpgradeType.OFF_HAND) ) {
                    soldier.destroyUpgrade(upgradeInst.getUpgrade(), upgradeInst.getUpgradeType(), true);
                }
            }
        }
    }

    public static final AttributeModifier SHEAR_DMG_M = new AttributeModifier(UUID.fromString("942F2774-A191-41A2-B76B-0AB8198ADD1E"), CsmConstants.ID + ".shear_prism_upg_m", 1.0D, EntityUtils.ATTR_ADD_VAL_TO_BASE);
    public static final AttributeModifier SHEAR_DMG_O = new AttributeModifier(UUID.fromString("614D0E68-C7F8-467D-AD5F-5BB7AFB039CD"), CsmConstants.ID + ".shear_prism_upg_o", 1.0D, EntityUtils.ATTR_ADD_VAL_TO_BASE);
}
