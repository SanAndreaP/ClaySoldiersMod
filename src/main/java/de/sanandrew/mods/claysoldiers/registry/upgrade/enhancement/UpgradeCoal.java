/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry.upgrade.enhancement;

import de.sanandrew.mods.claysoldiers.api.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgFunctions;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgrade;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.UpgradeFunctions;
import de.sanandrew.mods.claysoldiers.registry.upgrade.UpgradeRegistry;
import de.sanandrew.mods.claysoldiers.registry.upgrade.Upgrades;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeBlazePowder;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import de.sanandrew.mods.sanlib.lib.util.UuidUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.mutable.MutableFloat;

import javax.annotation.Nonnull;
import java.util.UUID;

@UpgradeFunctions({EnumUpgFunctions.ON_UPGRADE_ADDED, EnumUpgFunctions.ON_DEATH})
public class UpgradeCoal
        implements ISoldierUpgrade
{
    private static final ItemStack[] UPG_ITEMS = { new ItemStack(Items.COAL, 1, 0), new ItemStack(Items.COAL, 1, 1) };

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
        return soldier.hasUpgrade(Upgrades.MH_BLAZEROD, EnumUpgradeType.MAIN_HAND) || soldier.hasUpgrade(Upgrades.MC_BLAZEPOWDER, EnumUpgradeType.MISC)
               || soldier.hasUpgrade(Upgrades.OH_FIRECHARGE, EnumUpgradeType.OFF_HAND);
    }

    @Override
    public boolean syncData() {
        return true;
    }

    @Override
    public void onAdded(ISoldier<?> soldier, ItemStack stack, ISoldierUpgradeInst upgradeInst) {
        if( !soldier.getEntity().world.isRemote ) {
            soldier.getEntity().playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.2F, ((MiscUtils.RNG.randomFloat() - MiscUtils.RNG.randomFloat()) * 0.7F + 1.0F) * 2.0F);
            stack.shrink(1);
        }

        ISoldierUpgradeInst bPowderInst = soldier.getUpgradeInstance(Upgrades.MC_BLAZEPOWDER, EnumUpgradeType.MISC);
        if( bPowderInst != null ) {
            bPowderInst.getNbtData().setShort("uses", (short) (bPowderInst.getNbtData().getShort("uses") + 1));
        }
    }

    @Override
    public void onUpgradeAdded(ISoldier soldier, ISoldierUpgradeInst upgradeInst, ISoldierUpgradeInst addedUpgInst) {
        if( addedUpgInst.getUpgrade() instanceof UpgradeBlazePowder ) {
            upgradeInst.getNbtData().setShort("uses", (short) (upgradeInst.getNbtData().getShort("uses") + 1));
        }
    }

    @Override
    public void onDeath(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst, DamageSource dmgSource, NonNullList<ItemStack> drops) {
        drops.add(upgradeInst.getSavedStack());
    }
}
