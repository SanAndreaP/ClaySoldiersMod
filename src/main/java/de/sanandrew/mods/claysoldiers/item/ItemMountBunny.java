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
import de.sanandrew.mods.claysoldiers.entity.mount.EntityWoolBunny;
import de.sanandrew.mods.claysoldiers.registry.mount.EnumWoolBunnyType;
import de.sanandrew.mods.claysoldiers.util.CsmConfiguration;
import de.sanandrew.mods.claysoldiers.util.CsmCreativeTabs;
import de.sanandrew.mods.sanlib.lib.util.ItemStackUtils;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class ItemMountBunny
        extends ItemDoll<EntityWoolBunny, EnumWoolBunnyType>
{
    public ItemMountBunny() {
        super(CsmConstants.ID, "doll_bunny", CsmCreativeTabs.DOLLS);
        this.maxStackSize = CsmConfiguration.BlocksAndItems.Dolls.bunnyDollStackSize;
    }

    @Override
    public EnumWoolBunnyType getType(ItemStack stack) {
        if( ItemStackUtils.isItem(stack, this) ) {
            NBTTagCompound nbt = stack.getSubCompound(NBTConstants.S_DOLL_BUNNY);
            if( nbt != null && nbt.hasKey(NBTConstants.I_DOLL_TYPE, Constants.NBT.TAG_INT) ) {
                return EnumWoolBunnyType.VALUES[nbt.getInteger(NBTConstants.I_DOLL_TYPE)];
            }
        }

        return EnumWoolBunnyType.UNKNOWN;
    }

    @Override
    public EntityWoolBunny createEntity(World world, EnumWoolBunnyType type, ItemStack newDollStack) {
        return new EntityWoolBunny(world, type, newDollStack);
    }

    @Override
    public EnumWoolBunnyType[] getTypes() {
        return EnumWoolBunnyType.VALUES;
    }

    @Override
    public ItemStack getTypeStack(EnumWoolBunnyType type) {
        ItemStack stack = new ItemStack(this, 1);
        stack.getOrCreateSubCompound(NBTConstants.S_DOLL_BUNNY).setInteger(NBTConstants.I_DOLL_TYPE, type.ordinal());
        return stack;
    }

    @Override
    public SoundEvent getPlacementSound() {
        return SoundEvents.BLOCK_CLOTH_BREAK;
    }
}
