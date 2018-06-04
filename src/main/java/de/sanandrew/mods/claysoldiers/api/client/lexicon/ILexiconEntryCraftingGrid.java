/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.api.client.lexicon;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;
import java.util.List;

public interface ILexiconEntryCraftingGrid
        extends ILexiconEntry
{
    @Nonnull
    default NonNullList<IRecipe> getRecipes() {
        return NonNullList.create();
    }

    @Nonnull
    default NonNullList<ItemStack> getRecipeResults() {
        return NonNullList.create();
    }
}
