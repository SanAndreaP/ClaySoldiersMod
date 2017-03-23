/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.crafting;

import de.sanandrew.mods.claysoldiers.api.soldier.Team;
import de.sanandrew.mods.claysoldiers.registry.ItemRegistry;
import de.sanandrew.mods.claysoldiers.registry.TeamRegistry;
import de.sanandrew.mods.sanlib.lib.util.ItemStackUtils;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BrickSoldierConvRecipe
        implements IRecipe
{
    private int count;
    private ItemStack resultItem;
    private Team resultTeam;

    //TODO: finish this up
    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        this.count = 0;
        this.resultItem = null;
        this.resultTeam = null;

        boolean hasGhastTear = false;
        for( int i = 0, max = inv.getSizeInventory(); i < max; i++ ) {
            ItemStack stack = inv.getStackInSlot(i);

            if( !ItemStackUtils.isValid(stack) ) {
                continue;
            }

            if( ItemStackUtils.isItem(stack, ItemRegistry.doll_brick_soldier) ) {
                this.count += stack.stackSize;
            } else if( ItemStackUtils.isItem(stack, ItemRegistry.doll_soldier) ) {
                this.resultTeam = TeamRegistry.INSTANCE.getTeam(stack);
                if( this.resultTeam == TeamRegistry.NULL_TEAM ) {
                    return false;
                }
            } else if( ItemStackUtils.isItem(stack, Items.GHAST_TEAR) ) {
                if( !hasGhastTear ) {
                    hasGhastTear = true;
                } else {
                    return false;
                }
            }
        }

        if( hasGhastTear && this.count > 0 ) {
            return true;
        } else {
            return false;
        }
    }

    @Nullable
    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        return null;
    }

    @Override
    public int getRecipeSize() {
        return 0;
    }

    @Nullable
    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }

    @Override
    public ItemStack[] getRemainingItems(InventoryCrafting inv) {
        return new ItemStack[0];
    }
}
