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
import de.sanandrew.mods.sanlib.lib.util.ItemStackUtils;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UpgradeGoggles
        implements ISoldierUpgrade
{
    private static final ItemStack[] UPG_ITEMS;
    private static final EnumFunctionCalls[] FUNC_CALLS = new EnumFunctionCalls[] { EnumFunctionCalls.ON_DEATH};

    static {
        List<ItemStack> itms = new ArrayList<>();
        itms.add(new ItemStack(Blocks.GLASS, 1));
        itms.add(new ItemStack(Items.GLASS_BOTTLE, 1));
        itms.add(new ItemStack(Blocks.GLASS_PANE, 1));
        for( EnumDyeColor clr : EnumDyeColor.values() ) {
            itms.add(new ItemStack(Blocks.STAINED_GLASS, 1, clr.getMetadata()));
            itms.add(new ItemStack(Blocks.STAINED_GLASS_PANE, 1, clr.getMetadata()));
        }

        UPG_ITEMS = itms.toArray(new ItemStack[itms.size()]);
    }

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
    public boolean syncData() {
        return true;
    }

    @Override
    public boolean syncNbtData() {
        return true;
    }

    @Override
    public void writeSyncData(ByteBuf buf, NBTTagCompound nbt) {
        buf.writeByte(nbt.hasKey("color") ? nbt.getByte("color") : -1);
    }

    @Override
    public void readSyncData(ByteBuf buf, NBTTagCompound nbt) {
        byte color = buf.readByte();
        if( color >= 0 ) {
            nbt.setByte("color", color);
        }
    }

    @Override
    public void onAdded(ISoldier<?> soldier, ItemStack stack, ISoldierUpgradeInst upgradeInst) {
        if( !soldier.getEntity().world.isRemote ) {
            if( ItemStackUtils.isBlock(stack, Blocks.GLASS_PANE) || ItemStackUtils.isBlock(stack, Blocks.STAINED_GLASS_PANE) || ItemStackUtils.isItem(stack, Items.GLASS_BOTTLE) ) {
                soldier.getEntity().playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.2F, ((MiscUtils.RNG.randomFloat() - MiscUtils.RNG.randomFloat()) * 0.7F + 1.0F) * 2.0F);
                stack.shrink(1);
            } else {
                soldier.getEntity().playSound(SoundEvents.BLOCK_GLASS_BREAK, 0.2F, ((MiscUtils.RNG.randomFloat() - MiscUtils.RNG.randomFloat()) * 0.7F + 1.0F) * 2.0F);
            }

            if( ItemStackUtils.isBlock(stack, Blocks.STAINED_GLASS) || ItemStackUtils.isBlock(stack, Blocks.STAINED_GLASS_PANE) ) {
                upgradeInst.getNbtData().setByte("color", (byte) stack.getItemDamage());
            }

            soldier.getEntity().getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).applyModifier(FOLLOW_RNG);
        }
    }

    @Override
    public void onDeath(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst, DamageSource dmgSource, NonNullList<ItemStack> drops) {
        ItemStack saved = upgradeInst.getSavedStack();
        if( ItemStackUtils.isBlock(saved, Blocks.GLASS_PANE) || ItemStackUtils.isBlock(saved, Blocks.STAINED_GLASS_PANE) || ItemStackUtils.isItem(saved, Items.GLASS_BOTTLE) ) {
            drops.add(saved);
        }
    }

    private static final AttributeModifier FOLLOW_RNG = new AttributeModifier(UUID.fromString("B58968E4-6273-4BEA-93FC-B03120CE5C51"), CsmConstants.ID + ".goggle_upg", 16.0D, 0);
}
