/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.compat.jei;

import com.google.common.collect.ImmutableList;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.List;

public class DyedSoldierRecipeWrapper
        implements IRecipeWrapper
{
    private final List<List<ItemStack>> input;
    private final ItemStack output;


    public DyedSoldierRecipeWrapper(JeiDyedSoldierRecipe type) {
        this.input = type.ingredients;
        this.output = type.result;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(ItemStack.class, this.input);
        ingredients.setOutput(ItemStack.class, this.output);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {

    }

    @Nullable
    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        return ImmutableList.of();
    }

    @Override
    public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
        return false;
    }

    public static class Factory
            implements IRecipeWrapperFactory<JeiDyedSoldierRecipe>
    {
        @Override
        public IRecipeWrapper getRecipeWrapper(JeiDyedSoldierRecipe recipe) {
            return new DyedSoldierRecipeWrapper(recipe);
        }
    }
}
