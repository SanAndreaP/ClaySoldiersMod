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
import de.sanandrew.mods.claysoldiers.registry.ItemRegistry;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class DisruptorRecipeWrapper
        implements IShapedCraftingRecipeWrapper
{
    private final List<List<ItemStack>> input;
    private final ItemStack output;

    public DisruptorRecipeWrapper(ItemDisruptor.DisruptorType type) {
        this.output = ItemDisruptor.setType(new ItemStack(ItemRegistry.disruptor, 1), type);
        this.input = new ArrayList<>();
        this.input.add(ImmutableList.of());
        this.input.add(OreDictionary.getOres("stickWood"));
        this.input.add(ImmutableList.of());
        this.input.add(ImmutableList.of(new ItemStack(Items.CLAY_BALL, 1)));
        switch( type ) {
            case CLAY:
                this.input.add(ImmutableList.of(new ItemStack(Blocks.CLAY, 1)));
                break;
            case HARDENED:
                ArrayList<ItemStack> hardenedClayBlocks = new ArrayList<>();
                hardenedClayBlocks.add(new ItemStack(Blocks.HARDENED_CLAY, 1));
                for( int i = 0; i < 16; i++ ) {
                    hardenedClayBlocks.add(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, i));
                }
                this.input.add(hardenedClayBlocks);
                break;
            case OBSIDIAN:
                this.input.add(OreDictionary.getOres("obsidian"));
                break;
        }
        this.input.add(ImmutableList.of(new ItemStack(Items.CLAY_BALL, 1)));
        this.input.add(ImmutableList.of(new ItemStack(Items.CLAY_BALL, 1)));
        this.input.add(OreDictionary.getOres("dustRedstone"));
        this.input.add(ImmutableList.of(new ItemStack(Items.CLAY_BALL, 1)));
    }

    @Override
    public int getWidth() {
        return 3;
    }

    @Override
    public int getHeight() {
        return 3;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(ItemStack.class, this.input);
//        ingredients.setOutputs(ItemStack.class, this.output);
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
