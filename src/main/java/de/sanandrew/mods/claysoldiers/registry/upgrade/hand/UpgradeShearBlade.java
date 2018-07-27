/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry.upgrade.hand;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.IHandedUpgradeable;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.EnumUpgFunctions;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.ISoldierUpgrade;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.ISoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.entity.attributes.AttributeModifierRnd;
import de.sanandrew.mods.claysoldiers.api.attribute.AttributeHelper;
import de.sanandrew.mods.claysoldiers.item.ItemRegistry;
import de.sanandrew.mods.claysoldiers.registry.upgrade.Upgrades;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import de.sanandrew.mods.claysoldiers.util.EnumParticle;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;
import java.util.UUID;

import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.UpgradeFunctions;

@UpgradeFunctions({EnumUpgFunctions.ON_ATTACK_SUCCESS, EnumUpgFunctions.ON_TICK, EnumUpgFunctions.ON_DEATH})
public class UpgradeShearBlade
        implements ISoldierUpgrade
{
    private static final ItemStack[] UPG_ITEMS = { new ItemStack(ItemRegistry.SHEAR_BLADE, 1) };
    private static final byte MAX_USAGES = 25;

    @Override
    public String getModId() {
        return CsmConstants.ID;
    }

    @Override
    public String getShortName() {
        return "shearblade";
    }

    @Override
    @Nonnull
    public ItemStack[] getStacks() {
        return UPG_ITEMS;
    }

    @Nonnull
    @Override
    public EnumUpgradeType getType(IHandedUpgradeable checker) {
        return checker.hasMainHandUpgrade() ? EnumUpgradeType.OFF_HAND : EnumUpgradeType.MAIN_HAND;
    }

    @Override
    public boolean syncData() {
        return true;
    }

    @Override
    public void onAdded(ISoldier<?> soldier, ItemStack stack, ISoldierUpgradeInst upgradeInst) {
        if( !soldier.getEntity().world.isRemote ) {
            upgradeInst.getNbtData().setByte("uses", MAX_USAGES);
            AttributeHelper.tryApplyAttackDmgModifier(soldier.getEntity(), upgradeInst.getUpgradeType() == EnumUpgradeType.MAIN_HAND ? BLADE_DMG_1 : BLADE_DMG_2);
            soldier.getEntity().playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.2F, ((MiscUtils.RNG.randomFloat() - MiscUtils.RNG.randomFloat()) * 0.7F + 1.0F) * 2.0F);
            stack.shrink(1);
        }
    }

    @Override
    public void onTick(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst) {
        EntityLivingBase attackTgt = soldier.getEntity().getAttackTarget();
        if( !upgradeInst.getNbtData().getBoolean("firstHit") ) {
            if( attackTgt == null && soldier.hasUpgrade(Upgrades.MC_EGG, EnumUpgradeType.MISC) ) {
                upgradeInst.getNbtData().setBoolean("firstHit", true);
                AttributeHelper.tryApplyAttackDmgModifier(soldier.getEntity(), upgradeInst.getUpgradeType() == EnumUpgradeType.MAIN_HAND ? BLADE_FH_DMG_1 : BLADE_FH_DMG_2);
            }
        } else {
            if( attackTgt instanceof EntityLiving && ((EntityLiving) attackTgt).getAttackTarget() == soldier ) {
                this.resetFirstHit(soldier, upgradeInst);
            }
        }
    }

    @Override
    public void onAttackSuccess(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst, Entity target) {
        NBTTagCompound nbt = upgradeInst.getNbtData();
        if( nbt.getBoolean("firstHit") ) {
            this.resetFirstHit(soldier, upgradeInst);
            ClaySoldiersMod.proxy.spawnParticle(EnumParticle.CRITICAL, target.world.provider.getDimension(), target.posX, target.posY + target.getEyeHeight(), target.posZ);
        }

        byte uses = (byte) (nbt.getByte("uses") - 1);
        if( uses < 1 ) {
            AttributeHelper.tryRemoveAttackDmgModifier(soldier.getEntity(), upgradeInst.getUpgradeType() == EnumUpgradeType.MAIN_HAND ? BLADE_DMG_1 : BLADE_DMG_2);
            soldier.destroyUpgrade(upgradeInst.getUpgrade(), upgradeInst.getUpgradeType(), false);
            soldier.getEntity().playSound(SoundEvents.ENTITY_ITEM_BREAK, 0.8F, 0.8F + MiscUtils.RNG.randomFloat() * 0.4F);
        } else {
            nbt.setByte("uses", uses);
        }
    }

    @Override
    public void onDeath(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst, DamageSource dmgSource, NonNullList<ItemStack> drops) {
        if( upgradeInst.getNbtData().getByte("uses") >= MAX_USAGES ) {
            drops.add(upgradeInst.getSavedStack());
        }
    }

    private void resetFirstHit(ISoldier<?> soldier, ISoldierUpgradeInst upgInstance) {
        upgInstance.getNbtData().setBoolean("firstHit", false);
        AttributeHelper.tryRemoveAttackDmgModifier(soldier.getEntity(), upgInstance.getUpgradeType() == EnumUpgradeType.MAIN_HAND ? BLADE_FH_DMG_1 : BLADE_FH_DMG_2);
    }

    public static final AttributeModifier BLADE_DMG_1 = new AttributeModifierRnd(UUID.fromString("DA52E85C-40A0-4E0C-B719-1629A138B4E3"), CsmConstants.ID + ".blade_upg1", 1.0D, 1.0D, 0);
    public static final AttributeModifier BLADE_DMG_2 = new AttributeModifierRnd(UUID.fromString("27D57300-80A0-4AFE-937C-9F5208AADB0D"), CsmConstants.ID + ".blade_upg2", 1.0D, 1.0D, 0);
    public static final AttributeModifier BLADE_FH_DMG_1 = new AttributeModifier(UUID.fromString("077D626C-FD32-4237-86ED-F49FB3B59F48"), CsmConstants.ID + ".blade_fh_upg1", 2.0D, 0);
    public static final AttributeModifier BLADE_FH_DMG_2 = new AttributeModifier(UUID.fromString("560E9ABA-968A-42A9-8EE2-6980E0574B36"), CsmConstants.ID + ".blade_fh_upg2", 2.0D, 0);
}
