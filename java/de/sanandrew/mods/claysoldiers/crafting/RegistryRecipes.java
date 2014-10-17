/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.crafting;

import de.sanandrew.mods.claysoldiers.item.ItemClayManDoll;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import de.sanandrew.mods.claysoldiers.util.RegistryBlocks;
import de.sanandrew.mods.claysoldiers.util.RegistryItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.RecipeSorter.Category;

public final class RegistryRecipes
{
    public static RecipeSoldiers s_recSoldiersInst;

    @SuppressWarnings("unchecked")
    public static void initialize() {
        // basic soldier dolls
        CraftingManager.getInstance().addShapelessRecipe(ItemClayManDoll.setTeamForItem("clay", new ItemStack(RegistryItems.dollSoldier, 4)),
                                                         new ItemStack(Blocks.soul_sand, 1), new ItemStack(Blocks.clay)
        );

        // shear blades
        CraftingManager.getInstance().addShapelessRecipe(new ItemStack(RegistryItems.shearBlade, 2),
                                                         new ItemStack(Items.shears)
        );
        CraftingManager.getInstance().addShapelessRecipe(new ItemStack(Items.shears),
                                                         new ItemStack(RegistryItems.shearBlade), new ItemStack(RegistryItems.shearBlade)
        );

        // disruptors
        CraftingManager.getInstance().addRecipe(new ItemStack(RegistryItems.disruptor),
                                                "#|#", "#R#",
                                                '#', new ItemStack(Blocks.clay),
                                                '|', new ItemStack(Items.stick),
                                                'R', new ItemStack(Items.redstone)
        );
        CraftingManager.getInstance().addRecipe(new ItemStack(RegistryItems.disruptorHardened),
                                                "#|#", "#R#",
                                                '#', new ItemStack(Blocks.hardened_clay, 1, OreDictionary.WILDCARD_VALUE),
                                                '|', new ItemStack(Items.stick),
                                                'R', new ItemStack(Items.redstone)
        );

        // stat displays
        CraftingManager.getInstance().addRecipe(new ItemStack(RegistryItems.statDisplay),
                                                "#G#", "#R#",
                                                '#', new ItemStack(Blocks.clay),
                                                'G', new ItemStack(Blocks.glass),
                                                'R', new ItemStack(Items.redstone)
        );
        CraftingManager.getInstance().addRecipe(new ItemStack(RegistryItems.statDisplay),
                                                "#G#", "#R#",
                                                '#', new ItemStack(Blocks.clay),
                                                'G', new ItemStack(Blocks.stained_glass, 1, OreDictionary.WILDCARD_VALUE),
                                                'R', new ItemStack(Items.redstone)
        );

        // nexus
        CraftingManager.getInstance().addRecipe(new ItemStack(RegistryBlocks.clayNexus),
                                                "CDC", "SOS", "OOO",
                                                'C', new ItemStack(Items.clay_ball),
                                                'D', new ItemStack(Items.diamond),
                                                'S', new ItemStack(Blocks.soul_sand),
                                                'O', new ItemStack(Blocks.obsidian)
        );

        s_recSoldiersInst = new RecipeSoldiers();

        s_recSoldiersInst.addDollMaterial(new ItemStack(Items.dye, 1, OreDictionary.WILDCARD_VALUE));
        s_recSoldiersInst.addDollMaterial(new ItemStack(Blocks.melon_block, 1, OreDictionary.WILDCARD_VALUE));
        s_recSoldiersInst.addDollMaterial(new ItemStack(Blocks.pumpkin, 1, OreDictionary.WILDCARD_VALUE));
        s_recSoldiersInst.addDollMaterial(new ItemStack(Blocks.torch, 1, OreDictionary.WILDCARD_VALUE));
        s_recSoldiersInst.addDollMaterial(new ItemStack(Blocks.redstone_torch, 1, OreDictionary.WILDCARD_VALUE));

        RecipeSorter.register(ClaySoldiersMod.MOD_ID + ":recipe_soldiers", RecipeSoldiers.class, Category.SHAPED, "after:minecraft:shaped");
        RecipeSorter.register(ClaySoldiersMod.MOD_ID + ":recipe_horses", RecipeHorses.class, Category.SHAPED, "after:minecraft:shaped");
        RecipeSorter.register(ClaySoldiersMod.MOD_ID + ":recipe_turtles", RecipeTurtles.class, Category.SHAPED, "after:minecraft:shaped");

        CraftingManager.getInstance().getRecipeList().add(s_recSoldiersInst);
        CraftingManager.getInstance().getRecipeList().add(new RecipeHorses());
        CraftingManager.getInstance().getRecipeList().add(new RecipeTurtles());
    }
}
