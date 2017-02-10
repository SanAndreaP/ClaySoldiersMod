/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.crafting;

import de.sanandrew.core.manpack.util.helpers.ItemUtils;
import de.sanandrew.mods.claysoldiers.item.ItemTurtleDoll;
import de.sanandrew.mods.claysoldiers.util.RegistryItems;
import de.sanandrew.mods.claysoldiers.util.mount.EnumTurtleType;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class RecipeTurtles
        implements IRecipe
{
    private final ItemStack p_soulSand = new ItemStack(Blocks.soul_sand);

    @Override
    public boolean matches(InventoryCrafting invCrafting, World world) {
        if( invCrafting.getSizeInventory() < 9 ) {
            return false;
        }

        ItemStack typeItem = invCrafting.getStackInSlot(5);
        int startIndex;
        ItemStack[] pattern = new ItemStack[] {
                null, typeItem, typeItem,
                this.p_soulSand, this.p_soulSand, typeItem
        };

        if( invCrafting.getStackInSlot(0) == null && invCrafting.getStackInSlot(1) == null && invCrafting.getStackInSlot(2) == null ) {
            startIndex = 3;
        } else if( invCrafting.getStackInSlot(6) == null && invCrafting.getStackInSlot(7) == null && invCrafting.getStackInSlot(8) == null ) {
            startIndex = 0;
        } else {
            return false;
        }

        if( EnumTurtleType.getTypeFromItem(typeItem) == null ) {
            return false;
        }

        for( int i = 0, slotIndex = startIndex; i < pattern.length; slotIndex = startIndex + (++i) ) {
            if( !ItemUtils.areStacksEqual(pattern[i], invCrafting.getStackInSlot(slotIndex), true) ) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting invCrafting) {
        for( EnumTurtleType type : EnumTurtleType.VALUES ) {
            if( ItemUtils.areStacksEqual(type.item, invCrafting.getStackInSlot(5), true) ) {
                ItemStack stack = new ItemStack(RegistryItems.dollTurtleMount, 2);
                ItemTurtleDoll.setType(stack, type);
                return stack;
            }
        }

        return null;
    }

    @Override
    public int getRecipeSize() {
        return 9;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }
}
