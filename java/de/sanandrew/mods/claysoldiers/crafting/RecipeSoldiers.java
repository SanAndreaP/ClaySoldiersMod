/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.crafting;

import de.sanandrew.core.manpack.util.helpers.ItemUtils;
import de.sanandrew.core.manpack.util.javatuples.Pair;
import de.sanandrew.mods.claysoldiers.item.ItemClayManDoll;
import de.sanandrew.mods.claysoldiers.util.RegistryItems;
import de.sanandrew.mods.claysoldiers.util.soldier.ClaymanTeam;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.ArrayList;
import java.util.List;

public class RecipeSoldiers
        implements IRecipe
{
    private final List<ItemStack> p_dollMaterials;

    public RecipeSoldiers() {
        this.p_dollMaterials = new ArrayList<>();
    }

    public void addDollMaterial(ItemStack stack) {
        this.p_dollMaterials.add(stack);
    }

    @Override
    public boolean matches(InventoryCrafting invCrafting, World world) {
        if( invCrafting.getSizeInventory() < 9 ) {
            return false;
        }

        boolean hasMaterial = false;
        boolean hasDoll = false;
        for( int i = 0; i < 9; i++ ) {
            ItemStack stack = invCrafting.getStackInSlot(i);
            if( stack != null ) {
                if( ItemUtils.isItemStackInArray(stack, true, this.p_dollMaterials.toArray(new ItemStack[this.p_dollMaterials.size()])) ) {
                    if( !hasMaterial ) {
                        hasMaterial = true;
                    } else {
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
    public ItemStack getCraftingResult(InventoryCrafting invCrafting) {
        ItemStack material = null;
        Pair<ItemStack, MutableInt> doll = null;
        for( int i = 0; i < 9; i++ ) {
            ItemStack stack = invCrafting.getStackInSlot(i);
            if( stack != null ) {
                if( ItemUtils.isItemStackInArray(stack, true, this.p_dollMaterials.toArray(new ItemStack[this.p_dollMaterials.size()])) ) {
                    material = stack;
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
