/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.crafting;

import de.sanandrew.mods.claysoldiers.api.soldier.ITeam;
import de.sanandrew.mods.claysoldiers.item.ItemRegistry;
import de.sanandrew.mods.claysoldiers.registry.team.TeamRegistry;
import de.sanandrew.mods.claysoldiers.registry.team.Teams;
import de.sanandrew.mods.sanlib.lib.util.ItemStackUtils;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class BrickSoldierConvRecipe
        extends IForgeRegistryEntry.Impl<IRecipe>
        implements IRecipe
{
    private final List<Integer> remaining = new ArrayList<>();
    private int itmCount;
    @Nonnull
    private ItemStack resultItem = ItemStack.EMPTY;

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        this.resultItem = ItemStack.EMPTY;
        this.itmCount = 0;
        this.remaining.clear();

        ITeam resultTeam = TeamRegistry.INSTANCE.getTeam(Teams.SOLDIER_CLAY);
        boolean hasGhastTear = false;
        boolean hasBrickDoll = false;

        for( int i = 0, max = inv.getSizeInventory(); i < max; i++ ) {
            ItemStack stack = inv.getStackInSlot(i);

            if( !ItemStackUtils.isValid(stack) ) {
                continue;
            }

            if( ItemStackUtils.isItem(stack, ItemRegistry.DOLL_BRICK_SOLDIER) ) {
                if( !hasBrickDoll ) {
                    hasBrickDoll = true;
                } else {
                    return false;
                }
            } else if( ItemStackUtils.isItem(stack, ItemRegistry.DOLL_SOLDIER) ) {
                resultTeam = TeamRegistry.INSTANCE.getTeam(stack);
                if( resultTeam == TeamRegistry.NULL_TEAM ) {
                    return false;
                }
                this.remaining.add(i);
            } else if( ItemStackUtils.isItem(stack, Items.GHAST_TEAR) ) {
                if( !hasGhastTear ) {
                    hasGhastTear = true;
                    this.remaining.add(i);
                } else {
                    return false;
                }
            }

            this.itmCount++;
        }

        if( hasGhastTear && hasBrickDoll ) {
            this.resultItem = TeamRegistry.INSTANCE.getNewTeamStack(1, resultTeam);
            return true;
        } else {
            return false;
        }
    }

    @Nullable
    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        return this.resultItem.copy();
    }

    @Override
    public boolean canFit(int width, int height) {
        return width + height >= Math.max(this.itmCount, 2);
    }

    @Nullable
    @Override
    public ItemStack getRecipeOutput() {
        return this.resultItem;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        NonNullList<ItemStack> ret = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);

        this.remaining.forEach(id -> {
            ret.set(id, inv.getStackInSlot(id).copy());
            ret.get(id).setCount(1);
        });

        return ret;
    }

    @Override
    public boolean isHidden() {
        return true;
    }
}
