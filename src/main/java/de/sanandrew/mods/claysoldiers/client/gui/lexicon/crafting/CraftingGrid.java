/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.gui.lexicon.crafting;

import net.minecraft.item.ItemStack;

final class CraftingGrid
{
    ItemStack[][][] items;
    ItemStack result;

    CraftingGrid(int width, int height, ItemStack result) {
        this.items = new ItemStack[width][height][];
        this.result = result;
    }
}
