/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry.upgrade.misc;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgFunctions;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgrade;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.UpgradeFunctions;
import de.sanandrew.mods.claysoldiers.registry.upgrade.Upgrades;
import de.sanandrew.mods.sanlib.lib.util.EntityUtils;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import java.util.UUID;

@UpgradeFunctions({EnumUpgFunctions.ON_DEATH, EnumUpgFunctions.ON_UPGRADE_ADDED})
public class UpgradeDiamond
        implements ISoldierUpgrade
{
    private static final ItemStack[] UPG_ITEMS = { new ItemStack(Items.DIAMOND, 1) };

    @Override
    @Nonnull
    public ItemStack[] getStacks() {
        return UPG_ITEMS;
    }

    @Nonnull
    @Override
    public EnumUpgradeType getType(ISoldier<?> checker) {
        return EnumUpgradeType.MISC;
    }

    @Override
    public boolean isApplicable(ISoldier<?> soldier, ItemStack stack) {
        return !soldier.hasUpgrade(Upgrades.MC_DIAMONDBLOCK, EnumUpgradeType.MISC);
    }

    @Override
    public boolean syncData() {
        return true;
    }

    @Override
    public void onAdded(ISoldier<?> soldier, ItemStack stack, ISoldierUpgradeInst upgradeInst) {
        if( !soldier.getEntity().world.isRemote ) {
            soldier.getEntity().getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(SPEED_BOOST);
            soldier.getEntity().getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(HEALTH_BOOST);
            soldier.getEntity().heal(Float.MAX_VALUE);
            soldier.getUpgradeInstanceList().forEach(inst -> {
                NBTTagCompound nbt = inst.getNbtData();
                if( nbt.hasKey("uses", Constants.NBT.TAG_SHORT) ) {
                    nbt.setShort("uses", (short) (nbt.getShort("uses") * 2));
                }
            });
            soldier.getEntity().playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.2F, ((MiscUtils.RNG.randomFloat() - MiscUtils.RNG.randomFloat()) * 0.7F + 1.0F) * 2.0F);
            stack.shrink(1);
        }
    }

    @Override
    public void onUpgradeAdded(ISoldier soldier, ISoldierUpgradeInst upgradeInst, ISoldierUpgradeInst addedUpgInst) {
        NBTTagCompound nbt = addedUpgInst.getNbtData();
        if( nbt.hasKey("uses", Constants.NBT.TAG_SHORT) ) {
            nbt.setShort("uses", (short) (nbt.getShort("uses") * 2));
        }
    }

    @Override
    public void onDeath(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst, DamageSource dmgSource, NonNullList<ItemStack> drops) {
        drops.add(upgradeInst.getSavedStack());
    }

    private static final AttributeModifier SPEED_BOOST = new AttributeModifier(UUID.fromString("9B883006-F5AD-4342-8313-3FCB0834E3A5"), CsmConstants.ID + ".diamond_speed", 0.15D, EntityUtils.ATTR_ADD_VAL_TO_BASE);
    private static final AttributeModifier HEALTH_BOOST = new AttributeModifier(UUID.fromString("C5778C31-B0BC-485F-89E3-A2441E70885D"), CsmConstants.ID + ".diamond_health", 10.0D, EntityUtils.RISE_SUM_WITH_PERC_VAL);
}
