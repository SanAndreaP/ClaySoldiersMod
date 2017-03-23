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
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.UUID;

public class DyedSoldierRecipe
        implements IRecipe
{
    private int dyedCount;
    private ItemStack resultItem;

    public static final UUID[] TEAMS = {
            TeamRegistry.SOLDIER_BLACK,
            TeamRegistry.SOLDIER_RED,
            TeamRegistry.SOLDIER_GREEN,
            TeamRegistry.SOLDIER_BROWN,
            TeamRegistry.SOLDIER_BLUE,
            TeamRegistry.SOLDIER_PURPLE,
            TeamRegistry.SOLDIER_CYAN,
            TeamRegistry.SOLDIER_CLAY,
            TeamRegistry.SOLDIER_GRAY,
            TeamRegistry.SOLDIER_PINK,
            TeamRegistry.SOLDIER_LIME,
            TeamRegistry.SOLDIER_YELLOW,
            TeamRegistry.SOLDIER_LIGHTBLUE,
            TeamRegistry.SOLDIER_MAGENTA,
            TeamRegistry.SOLDIER_ORANGE,
            TeamRegistry.SOLDIER_WHITE,
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
        this.resultItem = null;

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

            if( ItemStackUtils.isItem(invStack, ItemRegistry.doll_soldier) ) {
                this.dyedCount++;
            } else if( ItemStackUtils.isValid(invStack) ) {
                return false;
            }
        }

        if( dyeId < 0 || this.dyedCount < 1 ) {
            return false;
        }

        this.resultItem = TeamRegistry.INSTANCE.setTeam(new ItemStack(ItemRegistry.doll_soldier, dyedCount), TEAMS[dyeId]);
        return true;
    }

    @Nullable
    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        return this.resultItem.copy();
    }

    @Override
    public int getRecipeSize() {
        return Math.max(this.dyedCount, 2);
    }

    @Nullable
    @Override
    public ItemStack getRecipeOutput() {
        return this.resultItem;
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
