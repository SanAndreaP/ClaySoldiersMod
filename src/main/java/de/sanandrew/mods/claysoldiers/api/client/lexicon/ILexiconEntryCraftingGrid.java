/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.api.client.lexicon;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

public interface ILexiconEntryCraftingGrid
        extends ILexiconEntry
{
    default IRecipe getRecipe() {
        return null;
    }

    default ItemStack getRecipeResult() {
        return ItemStack.EMPTY;
    }
}
