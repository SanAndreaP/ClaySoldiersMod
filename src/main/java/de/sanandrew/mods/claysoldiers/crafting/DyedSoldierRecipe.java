/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.crafting;

import de.sanandrew.mods.claysoldiers.item.ItemRegistry;
import de.sanandrew.mods.claysoldiers.registry.team.TeamRegistry;
import de.sanandrew.mods.claysoldiers.registry.team.Teams;
import de.sanandrew.mods.sanlib.lib.util.ItemStackUtils;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.UUID;

public class DyedSoldierRecipe
        extends IForgeRegistryEntry.Impl<IRecipe>
        implements IRecipe
{
    private int dyedCount;
    @Nonnull
    private ItemStack resultItem = ItemStack.EMPTY;

    public static final UUID[] TEAMS = {
            Teams.SOLDIER_BLACK,
            Teams.SOLDIER_RED,
            Teams.SOLDIER_GREEN,
            Teams.SOLDIER_BROWN,
            Teams.SOLDIER_BLUE,
            Teams.SOLDIER_PURPLE,
            Teams.SOLDIER_CYAN,
            Teams.SOLDIER_CLAY,
            Teams.SOLDIER_GRAY,
            Teams.SOLDIER_PINK,
            Teams.SOLDIER_LIME,
            Teams.SOLDIER_YELLOW,
            Teams.SOLDIER_LIGHTBLUE,
            Teams.SOLDIER_MAGENTA,
            Teams.SOLDIER_ORANGE,
            Teams.SOLDIER_WHITE,
            };
    public static final String[] DYES = {
            "dyeBlack",
            "dyeRed",
            "dyeGreen",
            "dyeBrown",
            "dyeBlue",
            "dyePurple",
            "dyeCyan",
            "dyeLightGray",
            "dyeGray",
            "dyePink",
            "dyeLime",
            "dyeYellow",
            "dyeLightBlue",
            "dyeMagenta",
            "dyeOrange",
            "dyeWhite"
    };

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        int dyeId = -1;
        this.dyedCount = 0;
        this.resultItem = ItemStack.EMPTY;

        invLoop:
        for( int i = 0, max = inv.getSizeInventory(); i < max; i++ ) {
            ItemStack invStack = inv.getStackInSlot(i);

            if( !ItemStackUtils.isValid(invStack) ) {
                continue;
            }

            if( dyeId < 0 ) {
                int[] oreIds = OreDictionary.getOreIDs(invStack);
                for( int oreId : oreIds ) {
                    String oreName = OreDictionary.getOreName(oreId);
                    if( oreName.startsWith("dye") ) {
                        dyeId = Arrays.asList(DYES).indexOf(oreName);
                        if( dyeId >= 0 ) {
                            continue invLoop;
                        }
                    }
                }
            }

            if( ItemStackUtils.isItem(invStack, ItemRegistry.DOLL_SOLDIER) ) {
                this.dyedCount++;
            } else if( ItemStackUtils.isValid(invStack) ) {
                return false;
            }
        }

        if( dyeId < 0 || this.dyedCount < 1 ) {
            return false;
        }

        this.resultItem = TeamRegistry.INSTANCE.setTeam(new ItemStack(ItemRegistry.DOLL_SOLDIER, dyedCount), TEAMS[dyeId]);
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

    @Override
    public boolean isHidden() {
        return true;
    }
}
