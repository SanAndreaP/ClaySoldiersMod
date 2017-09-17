/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry.upgrade.misc;

import de.sanandrew.mods.claysoldiers.api.IDisruptable;
import de.sanandrew.mods.claysoldiers.api.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgrade;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.registry.upgrade.Upgrades;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import de.sanandrew.mods.claysoldiers.util.EnumParticle;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

public class UpgradeFireworkStar
        implements ISoldierUpgrade
{
    private static final ItemStack[] UPG_ITEMS = { new ItemStack(Items.FIREWORK_CHARGE, 1) };
    private static final EnumFunctionCalls[] FUNC_CALLS = new EnumFunctionCalls[] { EnumFunctionCalls.ON_DEATH };

    @Override
    @Nonnull
    public ItemStack[] getStacks() {
        return UPG_ITEMS;
    }

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
        return !soldier.hasUpgrade(Upgrades.MC_GUNPOWDER, EnumUpgradeType.MISC) && !soldier.hasUpgrade(Upgrades.MC_MAGMACREAM, EnumUpgradeType.MISC);
    }

    @Override
    public void onAdded(ISoldier<?> soldier, ItemStack stack, ISoldierUpgradeInst upgradeInst) {
        if( !soldier.getEntity().world.isRemote ) {
            soldier.getEntity().playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.2F, ((MiscUtils.RNG.randomFloat() - MiscUtils.RNG.randomFloat()) * 0.7F + 1.0F) * 2.0F);
            stack.shrink(1);
        }
    }

    @Override
    public void onDeath(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst, DamageSource dmgSource, NonNullList<ItemStack> drops) {
        if( dmgSource != IDisruptable.DISRUPT_DAMAGE ) {
            NBTTagCompound explosionNbt = upgradeInst.getSavedStack().getSubCompound("Explosion");
            if( explosionNbt != null ) {
                NBTTagCompound rocketNbt = new NBTTagCompound();
                NBTTagList explosions = new NBTTagList();
                EntityLivingBase soldierL = soldier.getEntity();

                explosions.appendTag(explosionNbt);
                rocketNbt.setTag("Explosions", explosions);

                ClaySoldiersMod.proxy.spawnParticle(EnumParticle.FIREWORK, soldierL.world.provider.getDimension(), soldierL.posX, soldierL.posY, soldierL.posZ, rocketNbt);
            }
        } else {
            drops.add(upgradeInst.getSavedStack());
        }
    }
}
