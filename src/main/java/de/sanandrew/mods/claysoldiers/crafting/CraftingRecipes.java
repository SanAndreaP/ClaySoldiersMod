/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.crafting;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.item.ItemDisruptor;
import de.sanandrew.mods.claysoldiers.registry.ItemRegistry;
import de.sanandrew.mods.claysoldiers.registry.TeamRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.UUID;

@Mod.EventBusSubscriber
public class CraftingRecipes
{
    public static void initialize() {
//        CraftingManager.getRecipeList().add(new DisruptorRecipe(ItemDisruptor.DisruptorType.CLAY));
//        CraftingManager.getInstance().getRecipeList().add(new DisruptorRecipe(ItemDisruptor.DisruptorType.HARDENED));
//        CraftingManager.getInstance().getRecipeList().add(new DisruptorRecipe(ItemDisruptor.DisruptorType.OBSIDIAN));
//
//        CraftingManager.getInstance().getRecipeList().add(new DyedSoldierRecipe());
//        CraftingManager.getInstance().getRecipeList().add(new ClearSoldierRecipe());
//        CraftingManager.getInstance().getRecipeList().add(new OtherSoldierRecipe());
//        CraftingManager.getInstance().getRecipeList().add(new BrickSoldierConvRecipe());
//
//        CraftingManager.getInstance().addRecipe(TeamRegistry.INSTANCE.setTeam(new ItemStack(ItemRegistry.doll_soldier, 4), TeamRegistry.SOLDIER_CLAY),
//                "C", "S",
//                'C', Blocks.CLAY,
//                'S', Blocks.SOUL_SAND);
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        event.getRegistry()
             .registerAll(new DisruptorRecipe(ItemDisruptor.DisruptorType.CLAY).setRegistryName(new ResourceLocation(CsmConstants.ID, "disruptorClay")),
                          new DisruptorRecipe(ItemDisruptor.DisruptorType.HARDENED).setRegistryName(new ResourceLocation(CsmConstants.ID, "disruptorHardened")),
                          new DisruptorRecipe(ItemDisruptor.DisruptorType.OBSIDIAN).setRegistryName(new ResourceLocation(CsmConstants.ID, "disruptorObsidian")),
                          new DyedSoldierRecipe().setRegistryName(new ResourceLocation(CsmConstants.ID, "dyedSoldier")),
                          new ClearSoldierRecipe().setRegistryName(new ResourceLocation(CsmConstants.ID, "clearSoldier")),
                          new OtherSoldierRecipe().setRegistryName(new ResourceLocation(CsmConstants.ID, "othrSoldier")),
                          new BrickSoldierConvRecipe().setRegistryName(new ResourceLocation(CsmConstants.ID, "brickSoldierConv")));
    }
}
