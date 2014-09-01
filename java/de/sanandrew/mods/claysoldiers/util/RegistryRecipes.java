/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util;

import de.sanandrew.core.manpack.util.SAPUtils;
import de.sanandrew.core.manpack.util.javatuples.Pair;
import de.sanandrew.mods.claysoldiers.item.ItemClayManDoll;
import de.sanandrew.mods.claysoldiers.util.soldier.ClaymanTeam;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.ArrayList;
import java.util.List;

public final class RegistryRecipes
{
    public static RecipeSoldiers recSoldiersInst;

    @SuppressWarnings("unchecked")
    public static void initialize() {
        recSoldiersInst = new RecipeSoldiers();

        recSoldiersInst.addDollMaterial(new ItemStack(Items.dye, 1, OreDictionary.WILDCARD_VALUE));
        recSoldiersInst.addDollMaterial(new ItemStack(Blocks.melon_block, 1, OreDictionary.WILDCARD_VALUE));

        CraftingManager.getInstance().getRecipeList().add(recSoldiersInst);
    }

    public static class RecipeSoldiers implements IRecipe {
        private final List<ItemStack> dollMaterials_;

        public RecipeSoldiers() {
            this.dollMaterials_ = new ArrayList<>();
        }

        public void addDollMaterial(ItemStack stack) {
            this.dollMaterials_.add(stack);
        }

        @Override
        public boolean matches(InventoryCrafting inventory, World world) {
            boolean hasMaterial = false;
            boolean hasDoll = false;
            for( int i = 0; i < 9; i++ ) {
                ItemStack stack = inventory.getStackInSlot(i);
                if( stack != null ) {
                    if( SAPUtils.isItemInStackArray(stack, this.dollMaterials_) ) {
                        if( !hasMaterial ) {
                            hasMaterial = true;
                        } else  {
                            return false;
                        }
                    } else if( stack.getItem() instanceof ItemClayManDoll ) {
                        hasDoll = true;
                    } else {
                        return false;
                    }
                }
            }

            return hasDoll && hasMaterial;
        }

        @Override
        public ItemStack getCraftingResult(InventoryCrafting inventory) {
            ItemStack material = null;
            Pair<ItemStack, MutableInt> doll = null;
            for( int i = 0; i < 9; i++ ) {
                ItemStack stack = inventory.getStackInSlot(i);
                if( stack != null ) {
                    if( SAPUtils.isItemInStackArray(stack, this.dollMaterials_) ) {
                        if( material == null ) {
                            material = stack;
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

            if( doll != null && material != null ) {
                ItemStack result = new ItemStack(RegistryItems.dollSoldier, doll.getValue1().getValue());
                if( doll.getValue1().getValue() == 1 && doll.getValue0().hasTagCompound() ) {
                    result.setTagCompound((NBTTagCompound) doll.getValue0().getTagCompound().copy());
                }
                ItemClayManDoll.setTeamForItem(ClaymanTeam.getTeam(material).getTeamName(), result);

                return result;
            }

            return null;
        }

        @Override
        public int getRecipeSize() {
            return 9;
        }

        @Override
        public ItemStack getRecipeOutput() {
            return null;
        }
    }
}
