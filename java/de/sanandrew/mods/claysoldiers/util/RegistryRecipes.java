/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util;

import de.sanandrew.core.manpack.util.javatuples.Pair;
import de.sanandrew.mods.claysoldiers.item.ItemClayManDoll;
import de.sanandrew.mods.claysoldiers.util.soldier.ClaymanTeam;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import org.apache.commons.lang3.mutable.MutableInt;

public final class RegistryRecipes
{
    @SuppressWarnings("unchecked")
    public static void initialize() {
        CraftingManager.getInstance().getRecipeList().add(new RecipeDyeSoldiers(4));
        CraftingManager.getInstance().getRecipeList().add(new RecipeDyeSoldiers(9));
    }

    private static class RecipeDyeSoldiers implements IRecipe {
        private final int size_;

        public RecipeDyeSoldiers(int size) {
            this.size_ = size;
        }

        @Override
        public boolean matches(InventoryCrafting inventory, World world) {
            boolean hasDye = false;
            boolean hasDoll = false;
            for( int i = 0; i < this.size_; i++ ) {
                ItemStack stack = inventory.getStackInSlot(i);
                if( stack != null ) {
                    if( stack.getItem() instanceof ItemDye ) {
                        if( !hasDye ) {
                            hasDye = true;
                        } else  {
                            return false;
                        }
                    } else if( stack.getItem() instanceof ItemClayManDoll ) {
                        hasDoll = true;
                    }
                }
            }

            return hasDoll && hasDye;
        }

        @Override
        public ItemStack getCraftingResult(InventoryCrafting inventory) {
            ItemStack dye = null;
            Pair<ItemStack, MutableInt> doll = null;
            for( int i = 0; i < this.size_; i++ ) {
                ItemStack stack = inventory.getStackInSlot(i);
                if( stack != null ) {
                    if( stack.getItem() instanceof ItemDye ) {
                        if( dye == null ) {
                            dye = stack;
                        } else {
                            return null;
                        }
                    } else if( stack.getItem() instanceof ItemClayManDoll ) {
                        if( doll == null ) {
                            doll = Pair.with(stack, new MutableInt(1));
                        } else {
                            doll.getValue1().increment();
                        }
                    }
                }
            }

            if( doll != null ) {
                ItemStack result = new ItemStack(RegistryItems.dollSoldier, doll.getValue1().getValue());
                if( doll.getValue1().getValue() == 1 && doll.getValue0().hasTagCompound() ) {
                    result.setTagCompound((NBTTagCompound) doll.getValue0().getTagCompound().copy());
                }
                ItemClayManDoll.setTeamForItem(ClaymanTeam.getTeam(dye).getTeamName(), result);

                return result;
            }

            return null;
        }

        @Override
        public int getRecipeSize() {
            return this.size_;
        }

        @Override
        public ItemStack getRecipeOutput() {
            return null;
        }
    }
}
