/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.crafting;

import de.sanandrew.mods.claysoldiers.item.ItemDisruptor;
import de.sanandrew.mods.claysoldiers.registry.ItemRegistry;
import de.sanandrew.mods.sanlib.lib.util.ItemStackUtils;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.Arrays;

class DisruptorRecipe
        implements IRecipe
{
    private final ItemStack result;
    private ItemStack[] coreItems;

    public DisruptorRecipe(ItemDisruptor.DisruptorType type) {
        this.result = ItemDisruptor.setType(new ItemStack(ItemRegistry.disruptor, 1), type);

        switch (type) {
            case CLAY:
                this.coreItems = new ItemStack[]{new ItemStack(Blocks.CLAY, 1)};
                break;
            case HARDENED:
                this.coreItems = new ItemStack[17];
                this.coreItems[16] = new ItemStack(Blocks.HARDENED_CLAY, 1);
                for (int i = 0; i < 16; i++) {
                    this.coreItems[i] = new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, i);
                }
                break;
            case OBSIDIAN:
                this.coreItems = OreDictionary.getOres("obsidian").stream().toArray(ItemStack[]::new);
                break;
        }
    }

    @Override
    @SuppressWarnings("SimplifiableIfStatement")
    public boolean matches(InventoryCrafting inv, World worldIn) {
        if (this.coreItems == null || this.coreItems.length < 1) {
            return false;
        }

        if (ItemStackUtils.isValid(inv.getStackInSlot(0)) || ItemStackUtils.isValid(inv.getStackInSlot(2))) {
            return false;
        }

        if (!OreDictionary.containsMatch(false, OreDictionary.getOres("stickWood"), inv.getStackInSlot(1))) {
            return false;
        }

        if (!OreDictionary.containsMatch(false, OreDictionary.getOres("dustRedstone"), inv.getStackInSlot(7))) {
            return false;
        }

        if (!ItemStackUtils.isItem(inv.getStackInSlot(3), Items.CLAY_BALL) || !ItemStackUtils.isItem(inv.getStackInSlot(5), Items.CLAY_BALL)
                || !ItemStackUtils.isItem(inv.getStackInSlot(6), Items.CLAY_BALL) || !ItemStackUtils.isItem(inv.getStackInSlot(8), Items.CLAY_BALL)
                ) {
            return false;
        }

        return OreDictionary.containsMatch(false, Arrays.asList(this.coreItems), inv.getStackInSlot(4));
    }

    @Nullable
    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        return this.result.copy();
    }

    @Override
    public int getRecipeSize() {
        return 8;
    }

    @Nullable
    @Override
    public ItemStack getRecipeOutput() {
        return this.result;
    }

    @Override
    public ItemStack[] getRemainingItems(InventoryCrafting inv) {
        ItemStack[] invStacks = new ItemStack[inv.getSizeInventory()];

        for( int i = 0; i < invStacks.length; i++ ) {
            ItemStack itemstack = inv.getStackInSlot(i);
            invStacks[i] = net.minecraftforge.common.ForgeHooks.getContainerItem(itemstack);
        }

        return invStacks;
    }
}
