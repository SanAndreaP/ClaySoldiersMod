/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.crafting;

import de.sanandrew.mods.claysoldiers.item.ItemDisruptor;
import de.sanandrew.mods.claysoldiers.registry.ItemRegistry;
import de.sanandrew.mods.claysoldiers.registry.TeamRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;

import java.util.UUID;

public class CraftingRecipes
{
    public static void initialize() {
        CraftingManager.getInstance().getRecipeList().add(new DisruptorRecipe(ItemDisruptor.DisruptorType.CLAY));
        CraftingManager.getInstance().getRecipeList().add(new DisruptorRecipe(ItemDisruptor.DisruptorType.HARDENED));
        CraftingManager.getInstance().getRecipeList().add(new DisruptorRecipe(ItemDisruptor.DisruptorType.OBSIDIAN));

        CraftingManager.getInstance().getRecipeList().add(new DyedSoldierRecipe());
        CraftingManager.getInstance().getRecipeList().add(new ClearSoldierRecipe());
        CraftingManager.getInstance().getRecipeList().add(new OtherSoldierRecipe());
        CraftingManager.getInstance().getRecipeList().add(new BrickSoldierConvRecipe());

        CraftingManager.getInstance().addRecipe(TeamRegistry.INSTANCE.setTeam(new ItemStack(ItemRegistry.doll_soldier, 4), TeamRegistry.SOLDIER_CLAY),
                "C", "S",
                'C', Blocks.CLAY,
                'S', Blocks.SOUL_SAND);
    }
}
