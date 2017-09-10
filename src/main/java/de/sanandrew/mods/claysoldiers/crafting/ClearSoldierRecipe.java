/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.crafting;

import de.sanandrew.mods.claysoldiers.registry.ItemRegistry;
import de.sanandrew.mods.claysoldiers.registry.TeamRegistry;
import de.sanandrew.mods.sanlib.lib.util.ItemStackUtils;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;

class ClearSoldierRecipe
        extends IForgeRegistryEntry.Impl<IRecipe>
        implements IRecipe
{
    private int dyedCount;
    private ItemStack resultItem;

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        boolean hasBucket = false;
        this.dyedCount = 0;
        this.resultItem = null;

        for( int i = 0, max = inv.getSizeInventory(); i < max; i++ ) {
            ItemStack invStack = inv.getStackInSlot(i);

            if( invStack == null ) {
                continue;
            }

            if( !hasBucket ) {
                if( ItemStackUtils.isItem(invStack, Items.WATER_BUCKET) ) {
                    hasBucket = true;
                    continue;
                }
            }

            if( ItemStackUtils.isItem(invStack, ItemRegistry.doll_soldier) ) {
                this.dyedCount++;
            } else if( ItemStackUtils.isValid(invStack) ) {
                return false;
            }
        }

        if( !hasBucket || this.dyedCount < 1 ) {
            return false;
        }

        this.resultItem = TeamRegistry.INSTANCE.setTeam(new ItemStack(ItemRegistry.doll_soldier, dyedCount), TeamRegistry.SOLDIER_CLAY);
        return true;
    }

    @Nullable
    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        return this.resultItem.copy();
    }

    @Override
    public boolean canFit(int width, int height) {
        return width + height >= Math.max(this.dyedCount, 2);
    }

    @Nullable
    @Override
    public ItemStack getRecipeOutput() {
        return this.resultItem;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        NonNullList<ItemStack> invStacks = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);

        for( int i = 0, max = invStacks.size(); i < max; i++ ) {
            ItemStack itemstack = inv.getStackInSlot(i);
            invStacks.set(i, net.minecraftforge.common.ForgeHooks.getContainerItem(itemstack));
        }

        return invStacks;
    }
}
