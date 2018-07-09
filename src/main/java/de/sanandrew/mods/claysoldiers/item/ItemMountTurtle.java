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
import de.sanandrew.mods.claysoldiers.entity.mount.EntityTurtle;
import de.sanandrew.mods.claysoldiers.registry.ItemRegistry;
import de.sanandrew.mods.claysoldiers.registry.mount.EnumTurtleType;
import de.sanandrew.mods.claysoldiers.util.CsmConfig;
import de.sanandrew.mods.claysoldiers.util.CsmCreativeTabs;
import de.sanandrew.mods.sanlib.lib.util.ItemStackUtils;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class ItemMountTurtle
        extends ItemDoll<EntityTurtle, EnumTurtleType>
{
    public ItemMountTurtle() {
        super(CsmConstants.ID, "doll_turtle", CsmCreativeTabs.DOLLS);
        this.maxStackSize = CsmConfig.BlocksAndItems.Dolls.turtleDollStackSize;
    }

    @Override
    public EnumTurtleType getType(ItemStack stack) {
        if( ItemStackUtils.isItem(stack, ItemRegistry.DOLL_TURTLE) ) {
            NBTTagCompound nbt = stack.getSubCompound(NBTConstants.S_DOLL_TURTLE);
            if( nbt != null && nbt.hasKey(NBTConstants.I_DOLL_TYPE, Constants.NBT.TAG_INT) ) {
                return EnumTurtleType.VALUES[nbt.getInteger(NBTConstants.I_DOLL_TYPE)];
            }
        }

        return EnumTurtleType.UNKNOWN;
    }

    @Override
    public EntityTurtle createEntity(World world, EnumTurtleType type, ItemStack newDollStack) {
        return new EntityTurtle(world, type, newDollStack);
    }

    @Override
    public EnumTurtleType[] getTypes() {
        return EnumTurtleType.VALUES;
    }

    @Override
    public ItemStack getTypeStack(EnumTurtleType type) {
        ItemStack stack = new ItemStack(ItemRegistry.DOLL_TURTLE, 1);
        stack.getOrCreateSubCompound(NBTConstants.S_DOLL_TURTLE).setInteger(NBTConstants.I_DOLL_TYPE, type.ordinal());
        return stack;
    }

    @Override
    public SoundEvent getPlacementSound() {
        return SoundEvents.BLOCK_GRAVEL_BREAK;
    }
}
