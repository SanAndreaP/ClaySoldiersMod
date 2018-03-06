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
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

//TODO: make it a proper 3x3 recipe
class DisruptorRecipe
        extends IForgeRegistryEntry.Impl<IRecipe>
        implements IRecipe
{
    @Nonnull
    private final ItemStack result;
    private ItemStack[] coreItems;

    private DisruptorRecipe(ItemDisruptor.DisruptorType type) {
        this.result = ItemDisruptor.setType(new ItemStack(ItemRegistry.DISRUPTOR, 1), type);

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

        return OreDictionary.containsMatch(false, NonNullList.from(ItemStack.EMPTY, this.coreItems), inv.getStackInSlot(4));
    }

    @Nullable
    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        return this.result.copy();
    }

    @Override
    public boolean canFit(int width, int height) {
        return width >= 3 && height >= 3;
    }

    @Nullable
    @Override
    public ItemStack getRecipeOutput() {
        return this.result;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        NonNullList<ItemStack> invStacks = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);

        for( int i = 0, max = invStacks.size(); i < max; i++ ) {
            ItemStack itemstack = inv.getStackInSlot(i);
            invStacks.set(i, ForgeHooks.getContainerItem(itemstack));
        }

        return invStacks;
    }

    @Override
    public boolean isDynamic() {
        return true;
    }
}
