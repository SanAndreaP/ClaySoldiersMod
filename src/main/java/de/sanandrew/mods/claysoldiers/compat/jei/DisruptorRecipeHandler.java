/*
 * ****************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 * *****************************************************************************************************************
 */
package de.sanandrew.mods.claysoldiers.compat.jei;

import de.sanandrew.mods.claysoldiers.item.ItemDisruptor;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;

public class DisruptorRecipeHandler
        implements IRecipeHandler<DisruptorRecipeHandler.DisruptorRecipe>
{
    @Override
    public Class<DisruptorRecipeHandler.DisruptorRecipe> getRecipeClass() {
        return DisruptorRecipeHandler.DisruptorRecipe.class;
    }

    @Override
    public String getRecipeCategoryUid() {
        return VanillaRecipeCategoryUid.CRAFTING;
    }

    @Override
    public String getRecipeCategoryUid(DisruptorRecipeHandler.DisruptorRecipe recipe) {
        return VanillaRecipeCategoryUid.CRAFTING;
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(DisruptorRecipeHandler.DisruptorRecipe recipe) {
        return new DisruptorRecipeWrapper(recipe.type);
    }

    @Override
    public boolean isRecipeValid(DisruptorRecipeHandler.DisruptorRecipe recipe) {
        return true;
    }

    public static class DisruptorRecipe {
        public final ItemDisruptor.DisruptorType type;

        public DisruptorRecipe(ItemDisruptor.DisruptorType type) {
            this.type = type;
        }
    }
}
