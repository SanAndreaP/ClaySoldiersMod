/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry.upgrade.hand;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.IDisruptable;
import de.sanandrew.mods.claysoldiers.api.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgrade;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgradeInst;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import org.apache.commons.lang3.mutable.MutableFloat;

import javax.annotation.Nonnull;
import java.util.UUID;

public class UpgradeBowl
        implements ISoldierUpgrade
{
    private static final ItemStack[] UPG_ITEMS = { new ItemStack(Items.BOWL, 1) };
    private static final EnumFunctionCalls[] FUNC_CALLS = new EnumFunctionCalls[] {EnumFunctionCalls.ON_DEATH,
                                                                                   EnumFunctionCalls.ON_DAMAGED};
    private static final short MAX_USES = 20;

    @Nonnull
    @Override
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
        return EnumUpgradeType.OFF_HAND;
    }

    @Override
    public boolean syncData() {
        return true;
    }

    @Override
    public void onAdded(ISoldier<?> soldier, ItemStack stack, ISoldierUpgradeInst upgradeInst) {
        if( !soldier.getEntity().world.isRemote ) {
            upgradeInst.getNbtData().setShort("uses", MAX_USES);
            soldier.getEntity().getEntityAttribute(SharedMonsterAttributes.ARMOR).applyModifier(ARMOR_VALUE);
            soldier.getEntity().playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.2F, ((MiscUtils.RNG.randomFloat() - MiscUtils.RNG.randomFloat()) * 0.7F + 1.0F) * 2.0F);
            stack.shrink(1);
        }
    }

    @Override
    public void onDamaged(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst, Entity attacker, DamageSource dmgSource, MutableFloat damage) {
        if( !soldier.getEntity().world.isRemote ) {
            short uses = (short) (upgradeInst.getNbtData().getShort("uses") - 1);
            if( uses < 1 ) {
                soldier.destroyUpgrade(upgradeInst.getUpgrade(), upgradeInst.getUpgradeType(), false);
                soldier.getEntity().getEntityAttribute(SharedMonsterAttributes.ARMOR).removeModifier(ARMOR_VALUE);
                soldier.getEntity().playSound(SoundEvents.ENTITY_ITEM_BREAK, 0.8F, 0.8F + MiscUtils.RNG.randomFloat() * 0.4F);
            } else if( !(dmgSource.getTrueSource() instanceof EntityPlayer) && dmgSource != IDisruptable.DISRUPT_DAMAGE ) {
                upgradeInst.getNbtData().setShort("uses", uses);
            }
        }
    }

    @Override
    public void onDeath(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst, DamageSource dmgSource, NonNullList<ItemStack> drops) {
        if( upgradeInst.getNbtData().getShort("uses") >= MAX_USES ) {
            drops.add(upgradeInst.getSavedStack());
        }
    }

    private static final AttributeModifier ARMOR_VALUE = new AttributeModifier(UUID.fromString("E7DDF42E-C494-40E4-92AA-B4F40636C5BE"), CsmConstants.ID + ".bowl_shield", 12.5D, 0);
}
