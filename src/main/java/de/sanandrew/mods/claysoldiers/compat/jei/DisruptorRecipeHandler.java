/*
 * ****************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 * *****************************************************************************************************************
 */
package de.sanandrew.mods.claysoldiers.compat.jei;

import com.google.common.collect.ImmutableList;
import de.sanandrew.mods.claysoldiers.item.ItemDisruptor;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;

import java.util.List;

public class DisruptorRecipeHandler
        implements IRecipeHandler<DisruptorRecipeHandler.JeiDisruptorRecipe>
{
    @Override
    public Class<JeiDisruptorRecipe> getRecipeClass() {
        return JeiDisruptorRecipe.class;
    }

    @Override
    public String getRecipeCategoryUid() {
        return VanillaRecipeCategoryUid.CRAFTING;
    }

    @Override
    public String getRecipeCategoryUid(JeiDisruptorRecipe recipe) {
        return VanillaRecipeCategoryUid.CRAFTING;
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(JeiDisruptorRecipe recipe) {
        return new DisruptorRecipeWrapper(recipe.type);
    }

    @Override
    public boolean isRecipeValid(JeiDisruptorRecipe recipe) {
        return true;
    }

    public static List<JeiDisruptorRecipe> getRecipeList() {
        return ImmutableList.of(new JeiDisruptorRecipe(ItemDisruptor.DisruptorType.CLAY),
                                new JeiDisruptorRecipe(ItemDisruptor.DisruptorType.HARDENED),
                                new JeiDisruptorRecipe(ItemDisruptor.DisruptorType.OBSIDIAN));
    }

    static class JeiDisruptorRecipe {
        public final ItemDisruptor.DisruptorType type;

        public JeiDisruptorRecipe(ItemDisruptor.DisruptorType type) {
            this.type = type;
        }
    }
}
