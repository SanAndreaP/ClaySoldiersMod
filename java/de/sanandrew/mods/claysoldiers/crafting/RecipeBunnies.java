/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.crafting;

import de.sanandrew.core.manpack.util.helpers.ItemUtils;
import de.sanandrew.mods.claysoldiers.util.RegistryItems;
import de.sanandrew.mods.claysoldiers.util.mount.EnumBunnyType;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class RecipeBunnies
        implements IRecipe
{
    private final ItemStack p_soulSand = new ItemStack(Blocks.soul_sand);
    private final ItemStack p_wool = new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE);

    @Override
    public boolean matches(InventoryCrafting invCrafting, World world) {
        int slotR1 = getSngItemInCol(invCrafting, this.p_wool, 0);
        int slotR2 = getSngItemInCol(invCrafting, this.p_soulSand, 1);
        int slotR3 = getSngItemInCol(invCrafting, this.p_wool, 2);

        return slotR1 >= 0 && slotR1 == slotR2 && slotR1 == slotR3
               && invCrafting.getStackInSlot(slotR1 * 3).getItemDamage() == invCrafting.getStackInSlot(slotR1 * 3 + 2).getItemDamage();
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting invCrafting) {
        ItemStack woolClr = invCrafting.getStackInSlot(getSngItemInCol(invCrafting, this.p_wool, 0) * 3);
        return new ItemStack(RegistryItems.dollBunnyMount, 4, EnumBunnyType.getTypeFromItem(woolClr).woolMeta);
    }

    @Override
    public int getRecipeSize() {
        return 9;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }

    private static int getSngItemInCol(InventoryCrafting invCrafting, ItemStack stack, int col) {
        int slotFound = -1;
        for(int i = 0; i < 3; i++) {
            ItemStack item = invCrafting.getStackInSlot(col + i * 3);
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
