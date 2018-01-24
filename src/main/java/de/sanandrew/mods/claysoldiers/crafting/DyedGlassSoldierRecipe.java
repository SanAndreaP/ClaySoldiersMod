/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.crafting;

import de.sanandrew.mods.claysoldiers.registry.ItemRegistry;
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

public class DyedGlassSoldierRecipe
        extends IForgeRegistryEntry.Impl<IRecipe>
        implements IRecipe
{
    private int dyedCount;
    @Nonnull
    private ItemStack resultItem = ItemStack.EMPTY;

    public static final UUID[] TEAMS = {
            Teams.SOLDIER_GLASSBLACK,
            Teams.SOLDIER_GLASSRED,
            Teams.SOLDIER_GLASSGREEN,
            Teams.SOLDIER_GLASSBROWN,
            Teams.SOLDIER_GLASSBLUE,
            Teams.SOLDIER_GLASSPURPLE,
            Teams.SOLDIER_GLASSCYAN,
            Teams.SOLDIER_GLASSLIGHTGRAY,
            Teams.SOLDIER_GLASSGRAY,
            Teams.SOLDIER_GLASSPINK,
            Teams.SOLDIER_GLASSLIME,
            Teams.SOLDIER_GLASSYELLOW,
            Teams.SOLDIER_GLASSLIGHTBLUE,
            Teams.SOLDIER_GLASSMAGENTA,
            Teams.SOLDIER_GLASSORANGE,
            Teams.SOLDIER_GLASSWHITE,
            };

    public static final String[] COLORS = {
            "blockGlassBlack",
            "blockGlassRed",
            "blockGlassGreen",
            "blockGlassBrown",
            "blockGlassBlue",
            "blockGlassPurple",
            "blockGlassCyan",
            "blockGlassLightGray",
            "blockGlassGray",
            "blockGlassPink",
            "blockGlassLime",
            "blockGlassYellow",
            "blockGlassLightBlue",
            "blockGlassMagenta",
            "blockGlassOrange",
            "blockGlassWhite"
    };

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        int colorId = -1;
        this.dyedCount = 0;
        this.resultItem = ItemStack.EMPTY;

        invLoop:
        for( int i = 0, max = inv.getSizeInventory(); i < max; i++ ) {
            ItemStack invStack = inv.getStackInSlot(i);

            if( !ItemStackUtils.isValid(invStack) ) {
                continue;
            }

            if( colorId < 0 ) {
                int[] oreIds = OreDictionary.getOreIDs(invStack);
                for( int oreId : oreIds ) {
                    String oreName = OreDictionary.getOreName(oreId);
                    if( oreName.startsWith("blockGlass") ) {
                        colorId = Arrays.asList(COLORS).indexOf(oreName);
                        if( colorId >= 0 ) {
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

        if( colorId < 0 || this.dyedCount < 1 ) {
            return false;
        }

        this.resultItem = TeamRegistry.INSTANCE.setTeam(new ItemStack(ItemRegistry.DOLL_SOLDIER, dyedCount), TEAMS[colorId]);
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
