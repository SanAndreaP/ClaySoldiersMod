/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.crafting;

import com.google.common.collect.ImmutableList;
import de.sanandrew.mods.claysoldiers.registry.ItemRegistry;
import de.sanandrew.mods.claysoldiers.registry.team.TeamRegistry;
import de.sanandrew.mods.claysoldiers.registry.team.Teams;
import de.sanandrew.mods.sanlib.lib.util.ItemStackUtils;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OtherSoldierRecipe
        extends IForgeRegistryEntry.Impl<IRecipe>
        implements IRecipe
{
    @Nonnull
    private ItemStack resultItem = ItemStack.EMPTY;

    public static final UUID[] TEAMS = {
            Teams.SOLDIER_MELON,
            Teams.SOLDIER_PUMPKIN,
            Teams.SOLDIER_PUMPKIN,
            Teams.SOLDIER_REDSTONE,
            Teams.SOLDIER_COAL,
            Teams.SOLDIER_COAL,
            Teams.SOLDIER_CARROT,
            Teams.SOLDIER_POTATO,
            Teams.SOLDIER_POTATO,
            Teams.SOLDIER_BEETROOT
    };
    public static final ItemStack[] ITEMS = {
            new ItemStack(Blocks.MELON_BLOCK, 1),
            new ItemStack(Blocks.PUMPKIN, 1),
            new ItemStack(Blocks.LIT_PUMPKIN, 1),
            new ItemStack(Items.REDSTONE, 1),
            new ItemStack(Items.COAL, 1, 0),
            new ItemStack(Items.COAL, 1, 1),
            new ItemStack(Items.CARROT, 1),
            new ItemStack(Items.POTATO, 1),
            new ItemStack(Items.POISONOUS_POTATO, 1),
            new ItemStack(Items.BEETROOT)
    };

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        int itmId = -1;
        int coreIndex = -1;

        List<Integer> dollIndices = new ArrayList<>();

        this.resultItem = ItemStack.EMPTY;

        invLoop:
        for( int i = 0, max = inv.getSizeInventory(); i < max; i++ ) {
            ItemStack invStack = inv.getStackInSlot(i);

            if( !ItemStackUtils.isValid(invStack) ) {
                continue;
            }

            if( itmId < 0 ) {
                for( int j = 0; j < ITEMS.length; j++ ) {
                    if( ItemStackUtils.areEqual(invStack, ITEMS[j], false, false) ) {
                        itmId = j;
                        coreIndex = i;
                        continue invLoop;
                    }
                }
            }

            if( ItemStackUtils.isItem(invStack, ItemRegistry.DOLL_SOLDIER) && dollIndices.size() < 4 ) {
                dollIndices.add(i);
            } else if( ItemStackUtils.isValid(invStack) ) {
                return false;
            }
        }

        int invWidth = inv.getWidth();
        if( dollIndices.size() < 4 || !dollIndices.containsAll(ImmutableList.of(coreIndex - 1, coreIndex + 1, coreIndex - invWidth, coreIndex + invWidth)) ) {
            return false;
        }

        if( itmId < 0 ) {
            return false;
        }

        this.resultItem = TeamRegistry.INSTANCE.setTeam(new ItemStack(ItemRegistry.DOLL_SOLDIER, 4), TEAMS[itmId]);
        return true;
    }

    @Nullable
    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        return this.resultItem.copy();
    }

    @Override
    public boolean canFit(int width, int height) {
        return width + height >= 9;
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
            invStacks.set(i, ForgeHooks.getContainerItem(itemstack));
        }

        return invStacks;
    }

    @Override
    public boolean isHidden() {
        return true;
    }
}
