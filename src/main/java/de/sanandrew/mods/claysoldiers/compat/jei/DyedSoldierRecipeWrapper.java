/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.compat.jei;

import com.google.common.collect.ImmutableList;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.wrapper.ICraftingRecipeWrapper;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.List;

public class DyedSoldierRecipeWrapper
        implements ICraftingRecipeWrapper
{
    private final List<List<ItemStack>> input;
    private final ItemStack output;


    public DyedSoldierRecipeWrapper(DyedSoldierRecipeHandler.JeiDyedSoldierRecipe type) {
        this.input = type.ingredients;
        this.output = type.result;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(ItemStack.class, this.input);
        ingredients.setOutput(ItemStack.class, this.output);
    }

    @Override
    public List getInputs() {
        return this.input;
    }

    @Override
    public List<ItemStack> getOutputs() {
        return ImmutableList.of(this.output);
    }

    @Override
    public List<FluidStack> getFluidInputs() {
        return ImmutableList.of();
    }

    @Override
    public List<FluidStack> getFluidOutputs() {
        return ImmutableList.of();
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {

    }

    @Override
    public void drawAnimations(Minecraft minecraft, int recipeWidth, int recipeHeight) {

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
}
