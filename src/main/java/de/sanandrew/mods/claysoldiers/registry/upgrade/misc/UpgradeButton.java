/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry.upgrade.misc;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.IHandedUpgradeable;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.EnumUpgFunctions;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.ISoldierUpgrade;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.ISoldierUpgradeInst;
import de.sanandrew.mods.sanlib.lib.util.ItemStackUtils;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import org.apache.commons.lang3.mutable.MutableFloat;

import javax.annotation.Nonnull;

import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.UpgradeFunctions;

@UpgradeFunctions({EnumUpgFunctions.ON_DEATH, EnumUpgFunctions.ON_ATTACK})
public class UpgradeButton
        implements ISoldierUpgrade
{
    private static final ItemStack[] UPG_ITEMS = { new ItemStack(Blocks.WOODEN_BUTTON, 1), new ItemStack(Blocks.STONE_BUTTON, 1) };

    @Override
    public String getModId() {
        return CsmConstants.ID;
    }

    @Override
    public String getShortName() {
        return "button";
    }

    @Override
    @Nonnull
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
    public boolean syncNbtData() {
        return true;
    }

    @Override
    public void writeSyncData(ByteBuf buf, NBTTagCompound nbt) {
        buf.writeBoolean(nbt.getBoolean("isStone"));
    }

    @Override
    public void readSyncData(ByteBuf buf, NBTTagCompound nbt) {
        nbt.setBoolean("isStone", buf.readBoolean());
    }

    @Override
    public void onAttack(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst, Entity target, DamageSource dmgSource, MutableFloat damage) {
        if( damage != null && dmgSource != null && !dmgSource.isProjectile() && !soldier.hasMainHandUpgrade() ) {
            damage.add(upgradeInst.getNbtData().getBoolean("isStone") ? 2.0F : 1.0F);
        }
    }

    @Override
    public void onAdded(ISoldier<?> soldier, ItemStack stack, ISoldierUpgradeInst upgradeInst) {
        if( !soldier.getEntity().world.isRemote ) {
            upgradeInst.getNbtData().setBoolean("isStone", ItemStackUtils.isBlock(stack, Blocks.STONE_BUTTON));
            soldier.getEntity().playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.2F, ((MiscUtils.RNG.randomFloat() - MiscUtils.RNG.randomFloat()) * 0.7F + 1.0F) * 2.0F);
            stack.shrink(1);
        }
    }

    @Override
    public void onDeath(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst, DamageSource dmgSource, NonNullList<ItemStack> drops) {
        drops.add(upgradeInst.getSavedStack());
    }
}
