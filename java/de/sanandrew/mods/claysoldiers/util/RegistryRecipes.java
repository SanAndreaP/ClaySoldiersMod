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
import de.sanandrew.mods.claysoldiers.item.ItemHorseDoll;
import de.sanandrew.mods.claysoldiers.util.mount.EnumHorseType;
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
        // basic soldier dolls
        CraftingManager.getInstance().addShapelessRecipe(ItemClayManDoll.setTeamForItem("clay", new ItemStack(RegistryItems.dollSoldier, 4)),
                                                         new ItemStack(Blocks.soul_sand, 1), new ItemStack(Blocks.clay));

        // shear blades
        CraftingManager.getInstance().addShapelessRecipe(new ItemStack(RegistryItems.shearBlade, 2),
                                                         new ItemStack(Items.shears));
        CraftingManager.getInstance().addShapelessRecipe(new ItemStack(Items.shears),
                                                         new ItemStack(RegistryItems.shearBlade), new ItemStack(RegistryItems.shearBlade));

        // disruptors
        CraftingManager.getInstance().addRecipe(new ItemStack(RegistryItems.disruptor),
                                                "#|#", "#R#",
                                                '#', new ItemStack(Blocks.clay),
                                                '|', new ItemStack(Items.stick),
                                                'R', new ItemStack(Items.redstone));
        CraftingManager.getInstance().addRecipe(new ItemStack(RegistryItems.disruptorHardened),
                                                "#|#", "#R#",
                                                '#', new ItemStack(Blocks.hardened_clay, 1, OreDictionary.WILDCARD_VALUE),
                                                '|', new ItemStack(Items.stick),
                                                'R', new ItemStack(Items.redstone));

        // stat displays
        CraftingManager.getInstance().addRecipe(new ItemStack(RegistryItems.statDisplay),
                                                "#G#", "#R#",
                                                '#', new ItemStack(Blocks.clay),
                                                'G', new ItemStack(Blocks.glass),
                                                'R', new ItemStack(Items.redstone));
        CraftingManager.getInstance().addRecipe(new ItemStack(RegistryItems.statDisplay),
                                                "#G#", "#R#",
                                                '#', new ItemStack(Blocks.clay),
                                                'G', new ItemStack(Blocks.stained_glass, 1, OreDictionary.WILDCARD_VALUE),
                                                'R', new ItemStack(Items.redstone));

        // nexus
        CraftingManager.getInstance().addRecipe(new ItemStack(RegistryBlocks.clayNexus),
                                                "CDC", "SOS", "OOO",
                                                'C', new ItemStack(Items.clay_ball),
                                                'D', new ItemStack(Items.diamond),
                                                'S', new ItemStack(Blocks.soul_sand),
                                                'O', new ItemStack(Blocks.obsidian));

        recSoldiersInst = new RecipeSoldiers();

        recSoldiersInst.addDollMaterial(new ItemStack(Items.dye, 1, OreDictionary.WILDCARD_VALUE));
        recSoldiersInst.addDollMaterial(new ItemStack(Blocks.melon_block, 1, OreDictionary.WILDCARD_VALUE));
        recSoldiersInst.addDollMaterial(new ItemStack(Blocks.pumpkin, 1, OreDictionary.WILDCARD_VALUE));
        recSoldiersInst.addDollMaterial(new ItemStack(Blocks.torch, 1, OreDictionary.WILDCARD_VALUE));
        recSoldiersInst.addDollMaterial(new ItemStack(Blocks.redstone_torch, 1, OreDictionary.WILDCARD_VALUE));

        CraftingManager.getInstance().getRecipeList().add(recSoldiersInst);
        CraftingManager.getInstance().getRecipeList().add(new RecipeHorses());
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

    private static class RecipeHorses implements IRecipe {
        private final ItemStack feather = new ItemStack(Items.feather);
        private final ItemStack soulSand = new ItemStack(Blocks.soul_sand);

        @Override
        public boolean matches(InventoryCrafting invCrafting, World world) {
            boolean hasFeather = false;
            if( SAPUtils.areStacksEqualWithWCV(invCrafting.getStackInSlot(1), this.feather) ) {
                if( invCrafting.getStackInSlot(0) != null || invCrafting.getStackInSlot(2) != null ) {
                    return false;
                }
                hasFeather = true;
            }

            int checkIndex = 0;

            if( !hasFeather ) {
                for( int i = 0; i < 3; i++ ) {
                    ItemStack topItem = invCrafting.getStackInSlot(i);
                    ItemStack bottomItem = invCrafting.getStackInSlot(6 + i);

                    if( topItem == null && bottomItem != null ) {
                        checkIndex = 3;
                    } else if( topItem != null && bottomItem != null ) {
                       return false;
                    }
                }
            } else {
                checkIndex = 3;
            }

            horseLoop: for( EnumHorseType horseType : EnumHorseType.values ) {
                if( horseType.item == null ) {
                    continue;
                }

                ItemStack[] pattern = new ItemStack[] {
                    horseType.item, this.soulSand, horseType.item,
                    horseType.item,     null,      horseType.item
                };

                for( int i = 0; i < pattern.length; i++ ) {
                    if( !SAPUtils.areStacksEqualWithWCV(pattern[i], invCrafting.getStackInSlot(checkIndex + i)) ) {
                        continue horseLoop;
                    }
                }

                return true;
            }

            return false;
        }

        @Override
        public ItemStack getCraftingResult(InventoryCrafting invCrafting) {
            boolean isPegasus = false;
            if( SAPUtils.areStacksEqualWithWCV(invCrafting.getStackInSlot(1), this.feather) ) {
                isPegasus = true;
            }

            for( EnumHorseType horseType : EnumHorseType.values ) {
                if( SAPUtils.areStacksEqualWithWCV(horseType.item, invCrafting.getStackInSlot(3)) ) {
                    ItemStack stack = new ItemStack(RegistryItems.dollHorseMount, 2);
                    ItemHorseDoll.setType(stack, horseType, isPegasus);
                    return stack;
                }
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
