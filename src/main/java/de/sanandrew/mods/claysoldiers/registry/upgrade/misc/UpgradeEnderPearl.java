/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry.upgrade.misc;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgrade;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgradeInst;
import de.sanandrew.mods.sanlib.lib.util.EntityUtils;
import de.sanandrew.mods.sanlib.lib.util.ItemStackUtils;
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

public class UpgradeEnderPearl
        implements ISoldierUpgrade
{
    private static final ItemStack[] UPG_ITEMS = { new ItemStack(Items.ENDER_PEARL, 1) };
    private static final EnumFunctionCalls[] FUNC_CALLS = new EnumFunctionCalls[] { EnumFunctionCalls.ON_DEATH,
                                                                                    EnumFunctionCalls.ON_ATTACK_SUCCESS,
                                                                                    EnumFunctionCalls.ON_TICK };

    @Nonnull
    @Override
    public ItemStack[] getStacks() {
        return UPG_ITEMS;
    }

    @Nonnull
    @Override
    public EnumFunctionCalls[] getFunctionCalls() {
        return FUNC_CALLS;
    }

    @Nonnull
    @Override
    public EnumUpgradeType getType(ISoldier<?> checker) {
        return EnumUpgradeType.MISC;
    }

    @Override
    public boolean isApplicable(ISoldier<?> soldier, ItemStack stack) {
        return true;
    }

    @Override
    public void onAdded(ISoldier<?> soldier, ItemStack stack, ISoldierUpgradeInst upgradeInst) {
        if( !soldier.getEntity().world.isRemote ) {
            upgradeInst.getNbtData().setInteger("tickAdded", soldier.getEntity().ticksExisted);
            if( !soldier.getEntity().getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).hasModifier(HEALTH_BOOST) ) {
                soldier.getEntity().getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(HEALTH_BOOST);
            }
            soldier.getEntity().heal(5.0F);
            soldier.getEntity().playSound(SoundEvents.ENTITY_GHAST_AMBIENT, 0.2F, ((MiscUtils.RNG.randomFloat() - MiscUtils.RNG.randomFloat()) * 0.7F + 1.0F) * 2.0F);
            stack.shrink(1);
        }
    }

    @Override
    public void onAttackSuccess(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst, Entity target) {
        upgradeInst.getNbtData().setInteger("tickAdded", soldier.getEntity().ticksExisted);

        if( target instanceof ISoldier ) {
            ISoldier tgtSoldier = (ISoldier) target;
            if( !tgtSoldier.getEntity().isEntityAlive() ) {
                soldier.getEntity().heal(Float.MAX_VALUE);
                tgtSoldier.getEntity().setHealth(Float.MAX_VALUE);
                tgtSoldier.addUpgrade(this, EnumUpgradeType.MISC, ItemStack.EMPTY);
                soldier.getEntity().setAttackTarget(null);
                tgtSoldier.getEntity().setAttackTarget(null);
            }
        }
    }

    @Override
    public void onTick(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst) {
        if( soldier.getEntity().ticksExisted - upgradeInst.getNbtData().getInteger("tickAdded") > 12_000 ) {
            soldier.getEntity().onKillCommand();
        }
    }

    @Override
    public void onDeath(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst, DamageSource dmgSource, NonNullList<ItemStack> drops) {
        if( ItemStackUtils.isValid(upgradeInst.getSavedStack()) ) {
            drops.add(upgradeInst.getSavedStack());
        }
    }

    private static final AttributeModifier HEALTH_BOOST = new AttributeModifier(UUID.fromString("12c74323-3578-4719-99f6-56086da3dd75"),
                                                                                CsmConstants.ID + ".enderpearl_health", 5.0D, EntityUtils.ATTR_ADD_VAL_TO_BASE);
}
