/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.api.misc;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public interface IDummyMultiRecipe
        extends IRecipe
{
    int getRecipeWidth();

    int getRecipeHeight();

    List<IRecipe> getRecipes();

    @Override default boolean matches(InventoryCrafting inv, World worldIn) { return false; }
    @Override default ItemStack getCraftingResult(InventoryCrafting inv) { return null; }
    @Override default boolean canFit(int width, int height) { return false; }
    @Override default ItemStack getRecipeOutput() { return null; }
    @Override default NonNullList<Ingredient> getIngredients() { return null; }
    @Override default IRecipe setRegistryName(ResourceLocation name) { return null; }
    @Nullable @Override default ResourceLocation getRegistryName() { return null; }
    @Override default Class<IRecipe> getRegistryType() { return null; }
}
