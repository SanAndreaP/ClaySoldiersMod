/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.crafting;

import de.sanandrew.core.manpack.util.SAPUtils;
import de.sanandrew.mods.claysoldiers.item.ItemHorseDoll;
import de.sanandrew.mods.claysoldiers.util.RegistryItems;
import de.sanandrew.mods.claysoldiers.util.mount.EnumHorseType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class RecipeHorses
        implements IRecipe
{
    private final ItemStack p_feather = new ItemStack(Items.feather);
    private final ItemStack p_soulSand = new ItemStack(Blocks.soul_sand);

    @Override
    public boolean matches(InventoryCrafting invCrafting, World world) {
        ItemStack typeItem = invCrafting.getStackInSlot(3);
        int startIndex;
        ItemStack[] pattern;

        if( invCrafting.getStackInSlot(0) == null
                && (invCrafting.getStackInSlot(1) == null || SAPUtils.areStacksEqualWithWCV(this.p_feather, invCrafting.getStackInSlot(1)))
                && invCrafting.getStackInSlot(2) == null ) {
            startIndex = 3;
        } else if( invCrafting.getStackInSlot(6) == null && invCrafting.getStackInSlot(7) == null && invCrafting.getStackInSlot(8) == null ) {
            startIndex = 0;
        } else {
            return false;
        }

        if( EnumHorseType.getTypeFromItem(typeItem) == null ) {
            return false;
        }

        pattern = new ItemStack[] {
                typeItem, this.p_soulSand, typeItem,
                typeItem, null, typeItem
        };

        for( int i = 0, slotIndex = startIndex; i < pattern.length; slotIndex = startIndex + (++i) ) {
            if( !SAPUtils.areStacksEqualWithWCV(pattern[i], invCrafting.getStackInSlot(slotIndex)) ) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting invCrafting) {
        boolean isPegasus = false;
        if( SAPUtils.areStacksEqualWithWCV(invCrafting.getStackInSlot(1), this.p_feather) ) {
            isPegasus = true;
        }

        for( EnumHorseType horseType : EnumHorseType.VALUES ) {
            if( SAPUtils.areStacksEqualWithWCV(horseType.item, invCrafting.getStackInSlot(3)) ) {
                ItemStack stack = new ItemStack(RegistryItems.dollHorseMount, 2);
                ItemHorseDoll.setType(stack, horseType, isPegasus);
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
