/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.crafting;

import de.sanandrew.core.manpack.util.helpers.ItemUtils;
import de.sanandrew.mods.claysoldiers.util.RegistryItems;
import de.sanandrew.mods.claysoldiers.util.mount.EnumGeckoType;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class RecipeGeckos
        implements IRecipe
{
    private final ItemStack p_sapling = new ItemStack(Blocks.sapling, 1, OreDictionary.WILDCARD_VALUE);
    private final ItemStack p_soulSand = new ItemStack(Blocks.soul_sand);

    @Override
    public boolean matches(InventoryCrafting invCrafting, World world) {
        int slotR1 = getSngItemInRow(invCrafting, this.p_sapling, 0);
        int slotR2 = getSngItemInRow(invCrafting, this.p_soulSand, 1);
        int slotR3 = getSngItemInRow(invCrafting, this.p_sapling, 2);

        return slotR1 >= 0 && slotR1 == slotR2 && slotR1 == slotR3;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting invCrafting) {
        ItemStack sapOne = invCrafting.getStackInSlot(getSngItemInRow(invCrafting, this.p_sapling, 0));
        ItemStack sapTwo = invCrafting.getStackInSlot(getSngItemInRow(invCrafting, this.p_sapling, 2) + 6);

        return new ItemStack(RegistryItems.dollGeckoMount, 2, EnumGeckoType.getTypeFromItem(sapOne, sapTwo).ordinal());
    }

    @Override
    public int getRecipeSize() {
        return 9;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }

    private static int getSngItemInRow(InventoryCrafting invCrafting, ItemStack stack, int row) {
        int slotFound = -1;
        for(int i = 0; i < 3; i++) {
            ItemStack item = invCrafting.getStackInSlot(i + row * 3);
            if( item != null ) {
                if( slotFound >= 0 ) {
                    return -1;
                } else if( ItemUtils.areStacksEqual(item, stack, true) ) {
                    slotFound = i;
                }
            }
        }

        return slotFound;
    }
}
