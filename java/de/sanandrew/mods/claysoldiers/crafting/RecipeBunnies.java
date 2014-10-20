/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.crafting;

import de.sanandrew.mods.claysoldiers.item.ItemBunnyDoll;
import de.sanandrew.mods.claysoldiers.util.RegistryItems;
import de.sanandrew.mods.claysoldiers.util.mount.EnumBunnyType;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class RecipeBunnies
        implements IRecipe
{
    private final Item p_soulSand = Item.getItemFromBlock(Blocks.soul_sand);
    private final Item p_wool = Item.getItemFromBlock(Blocks.wool);

    @Override
    public boolean matches(InventoryCrafting invCrafting, World world) {
        if( invCrafting.getSizeInventory() < 9 ) {
            return false;
        }

        boolean hasRightPattern = false;

        for( int i = 0; i < 9; i += 3 ) {
            ItemStack[] row = new ItemStack[] {invCrafting.getStackInSlot(i), invCrafting.getStackInSlot(i+1), invCrafting.getStackInSlot(i+2)};

            if( row[0] == null && row[1] == null && row[2] == null) {
                continue;
            }

            if( !hasRightPattern ) {
                if( row[0].getItem() == this.p_wool && row[1].getItem() == this.p_soulSand && row[2].getItem() == this.p_wool
                    && row[0].getItemDamage() == row[2].getItemDamage() )
                {
                    hasRightPattern = true;
                } else {
                    return false;
                }
            }
        }

        return hasRightPattern;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting invCrafting) {
        ItemStack wool = null;
        for( int i = 0; i < 9; i += 3 ) {
            if( (wool = invCrafting.getStackInSlot(i)).getItem() != this.p_wool ) {
                wool = null;
            }
        }

        if( wool == null ) {
            return null;
        }

        for( EnumBunnyType type : EnumBunnyType.VALUES ) {
            if( type.woolMeta == wool.getItemDamage() ) {
                ItemStack stack = new ItemStack(RegistryItems.dollBunnyMount, 2);
                ItemBunnyDoll.setType(stack, type);
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
