/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.item;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.NBTConstants;
import de.sanandrew.mods.claysoldiers.api.doll.ItemDoll;
import de.sanandrew.mods.claysoldiers.entity.mount.EntityGecko;
import de.sanandrew.mods.claysoldiers.entity.mount.EntityGecko;
import de.sanandrew.mods.claysoldiers.registry.mount.EnumGeckoType;
import de.sanandrew.mods.claysoldiers.registry.mount.EnumGeckoType;
import de.sanandrew.mods.claysoldiers.util.CsmConfiguration;
import de.sanandrew.mods.claysoldiers.util.CsmCreativeTabs;
import de.sanandrew.mods.sanlib.lib.util.ItemStackUtils;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class ItemMountGecko
        extends ItemDoll<EntityGecko, EnumGeckoType>
{
    public ItemMountGecko() {
        super(CsmConstants.ID, "doll_gecko", CsmCreativeTabs.DOLLS);
        this.maxStackSize = CsmConfiguration.geckoDollStackSize;
    }

    @Override
    public EnumGeckoType getType(ItemStack stack) {
        if( ItemStackUtils.isItem(stack, this) ) {
            NBTTagCompound nbt = stack.getSubCompound(NBTConstants.S_DOLL_GECKO);
            if( nbt != null && nbt.hasKey(NBTConstants.I_DOLL_TYPE, Constants.NBT.TAG_INT) ) {
                return EnumGeckoType.VALUES[nbt.getInteger(NBTConstants.I_DOLL_TYPE)];
            }
        }

        return EnumGeckoType.UNKNOWN;
    }

    @Override
    public EntityGecko createEntity(World world, EnumGeckoType type, ItemStack newDollStack) {
        return new EntityGecko(world, type, newDollStack);
    }

    @Override
    public EnumGeckoType[] getTypes() {
        return EnumGeckoType.VALUES;
    }

    @Override
    public ItemStack getTypeStack(EnumGeckoType type) {
        ItemStack stack = new ItemStack(this, 1);
        stack.getOrCreateSubCompound(NBTConstants.S_DOLL_GECKO).setInteger(NBTConstants.I_DOLL_TYPE, type.ordinal());
        return stack;
    }

    @Override
    public SoundEvent getPlacementSound() {
        return SoundEvents.BLOCK_CLOTH_BREAK;
    }
}
