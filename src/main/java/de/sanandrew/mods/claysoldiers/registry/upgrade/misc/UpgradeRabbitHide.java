/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry.upgrade.misc;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.entity.IDisruptable;
import de.sanandrew.mods.claysoldiers.api.attribute.AttributeHelper;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.IHandedUpgradeable;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.EnumUpgFunctions;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.ISoldierUpgrade;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.ISoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.registry.upgrade.Upgrades;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import net.minecraft.entity.Entity;
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

import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.UpgradeFunctions;

@UpgradeFunctions({EnumUpgFunctions.ON_DEATH, EnumUpgFunctions.ON_DAMAGED})
public class UpgradeRabbitHide
        implements ISoldierUpgrade
{
    private static final ItemStack[] UPG_ITEMS = { new ItemStack(Items.RABBIT_HIDE, 1) };
    private static final short MAX_USES = 20;

    @Override
    public String getModId() {
        return CsmConstants.ID;
    }

    @Override
    public String getShortName() {
        return "rabbithide";
    }

    @Nonnull
    @Override
    public ItemStack[] getStacks() {
        return UPG_ITEMS;
    }

    @Nonnull
    @Override
    public EnumUpgradeType getType(IHandedUpgradeable checker) {
        return EnumUpgradeType.MISC;
    }

    @Override
    public boolean syncData() {
        return true;
    }

    @Override
    public boolean isApplicable(ISoldier<?> soldier, ItemStack stack) {
        return !soldier.hasUpgrade(Upgrades.MC_LEATHER, EnumUpgradeType.MISC);
    }

    @Override
    public void onAdded(ISoldier<?> soldier, ItemStack stack, ISoldierUpgradeInst upgradeInst) {
        if( !soldier.getEntity().world.isRemote ) {
            upgradeInst.getNbtData().setShort("uses", MAX_USES);
            AttributeHelper.tryApplyMoveSpeedModifier(soldier.getEntity(), SPEED_BOOST);
            soldier.getEntity().playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.2F, ((MiscUtils.RNG.randomFloat() - MiscUtils.RNG.randomFloat()) * 0.7F + 1.0F) * 2.0F);
            stack.shrink(1);
        }
    }

    @Override
    public void onDamaged(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst, Entity attacker, DamageSource dmgSource, MutableFloat damage) {
        damage.setValue(damage.getValue() * 0.875F);
        if( !soldier.getEntity().world.isRemote ) {
            short uses = (short) (upgradeInst.getNbtData().getShort("uses") - 1);
            if( uses < 1 ) {
                soldier.destroyUpgrade(upgradeInst.getUpgrade(), upgradeInst.getUpgradeType(), false);
                AttributeHelper.tryRemoveMoveSpeedModifier(soldier.getEntity(), SPEED_BOOST);
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

    private static final AttributeModifier SPEED_BOOST = new AttributeModifier(UUID.fromString("0DA68028-B25B-47AA-A0FF-2D45FA8BCF1E"), CsmConstants.ID + ".rhide_speed", 0.075D, 0);
}
