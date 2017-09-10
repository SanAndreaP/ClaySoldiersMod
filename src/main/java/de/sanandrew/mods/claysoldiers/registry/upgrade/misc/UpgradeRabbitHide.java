/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry.upgrade.misc;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.IDisruptable;
import de.sanandrew.mods.claysoldiers.api.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgrade;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.registry.upgrade.UpgradeRegistry;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import org.apache.commons.lang3.mutable.MutableFloat;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

public class UpgradeRabbitHide
        implements ISoldierUpgrade
{
    private static final ItemStack[] UPG_ITEMS = { new ItemStack(Items.RABBIT_HIDE, 1) };
    private static final EnumFunctionCalls[] FUNC_CALLS = new EnumFunctionCalls[] {EnumFunctionCalls.ON_DEATH,
                                                                                   EnumFunctionCalls.ON_DAMAGED};
    private static final byte MAX_USES = 20;

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
    public boolean syncData() {
        return true;
    }

    @Override
    public boolean checkPickupable(ISoldier<?> soldier, ItemStack stack) {
        return !soldier.hasUpgrade(UpgradeRegistry.MC_LEATHER, EnumUpgradeType.MISC);
    }

    @Override
    public void onAdded(ISoldier<?> soldier, ItemStack stack, ISoldierUpgradeInst upgradeInst) {
        if( !soldier.getEntity().world.isRemote ) {
            upgradeInst.getNbtData().setByte("uses", MAX_USES);
            soldier.getEntity().getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(SPEED_BOOST);
            soldier.getEntity().getEntityAttribute(SharedMonsterAttributes.ARMOR).applyModifier(ARMOR_VALUE);
            soldier.getEntity().playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.2F, ((MiscUtils.RNG.randomFloat() - MiscUtils.RNG.randomFloat()) * 0.7F + 1.0F) * 2.0F);
            stack.setCount(stack.getCount() - 1);
        }
    }

    @Override
    public void onDamaged(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst, Entity attacker, DamageSource dmgSource, MutableFloat damage) {
        if( !soldier.getEntity().world.isRemote ) {
            byte uses = (byte) (upgradeInst.getNbtData().getByte("uses") - 1);
            if( uses < 1 ) {
                soldier.destroyUpgrade(upgradeInst.getUpgrade(), upgradeInst.getUpgradeType(), false);
                soldier.getEntity().getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(SPEED_BOOST);
                soldier.getEntity().getEntityAttribute(SharedMonsterAttributes.ARMOR).removeModifier(ARMOR_VALUE);
                soldier.getEntity().playSound(SoundEvents.ENTITY_ITEM_BREAK, 0.8F, 0.8F + MiscUtils.RNG.randomFloat() * 0.4F);
            } else if( !(dmgSource.getTrueSource() instanceof EntityPlayer) && dmgSource != IDisruptable.DISRUPT_DAMAGE ) {
                upgradeInst.getNbtData().setByte("uses", uses);
            }
        }
    }

    @Override
    public void onDeath(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst, DamageSource dmgSource, List<ItemStack> drops) {
        if( upgradeInst.getNbtData().getByte("uses") >= MAX_USES ) {
            drops.add(upgradeInst.getSavedStack());
        }
    }

    private static final AttributeModifier SPEED_BOOST = new AttributeModifier(UUID.fromString("0DA68028-B25B-47AA-A0FF-2D45FA8BCF1E"), CsmConstants.ID + ".rhide_speed", 0.075D, 0);
    private static final AttributeModifier ARMOR_VALUE = new AttributeModifier(UUID.fromString("A801BBD1-4C82-45E4-96D0-ECC309D2837F"), CsmConstants.ID + ".rhide_armor", 6.25D, 0);
}
