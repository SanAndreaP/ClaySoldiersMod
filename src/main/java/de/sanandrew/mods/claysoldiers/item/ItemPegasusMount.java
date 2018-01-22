/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.item;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.doll.ItemDoll;
import de.sanandrew.mods.claysoldiers.entity.mount.EntityClayPegasus;
import de.sanandrew.mods.claysoldiers.registry.ItemRegistry;
import de.sanandrew.mods.claysoldiers.registry.mount.EnumClayHorseType;
import de.sanandrew.mods.claysoldiers.util.CsmCreativeTabs;
import de.sanandrew.mods.sanlib.lib.util.ItemStackUtils;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class ItemPegasusMount
        extends ItemDoll<EntityClayPegasus, EnumClayHorseType>
{
    public ItemPegasusMount() {
        super(CsmConstants.ID, "doll_pegasus", CsmCreativeTabs.DOLLS);
    }

    @Override
    public EnumClayHorseType getType(ItemStack stack) {
        if( ItemStackUtils.isItem(stack, ItemRegistry.DOLL_PEGASUS) ) {
            NBTTagCompound nbt = stack.getSubCompound("dollPegasus");
            if( nbt != null && nbt.hasKey("type", Constants.NBT.TAG_INT) ) {
                return EnumClayHorseType.VALUES[nbt.getInteger("type")];
            }
        }

        return EnumClayHorseType.UNKNOWN;
    }

    @Override
    public EntityClayPegasus createEntity(World world, EnumClayHorseType type, ItemStack newDollStack) {
        return new EntityClayPegasus(world, type, newDollStack);
    }

    @Override
    public EnumClayHorseType[] getTypes() {
        return EnumClayHorseType.VALUES;
    }

    @Override
    public ItemStack getTypeStack(EnumClayHorseType type) {
        ItemStack stack = new ItemStack(ItemRegistry.DOLL_PEGASUS, 1);
        stack.getOrCreateSubCompound("dollPegasus").setInteger("type", type.ordinal());
        return stack;
    }

    @Override
    public SoundEvent getPlacementSound() {
        return SoundEvents.BLOCK_GRAVEL_BREAK;
    }
}
