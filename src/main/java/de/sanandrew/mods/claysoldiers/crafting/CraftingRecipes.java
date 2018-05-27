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
import de.sanandrew.mods.claysoldiers.util.CsmConfiguration;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = CsmConstants.ID)
public class CraftingRecipes
{
    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        List<IRecipe> recipeList = new ArrayList<>();
        if( CsmConfiguration.enableDyedSoldierRecipe ) recipeList.add(new DyedSoldierRecipe().setRegistryName(new ResourceLocation(CsmConstants.ID, "dyedSoldier")));
        if( CsmConfiguration.enableSoldierWashRecipe ) recipeList.add(new ClearSoldierRecipe().setRegistryName(new ResourceLocation(CsmConstants.ID, "clearSoldier")));
        if( CsmConfiguration.enableResourceSoldierRecipe ) recipeList.add(new OtherSoldierRecipe().setRegistryName(new ResourceLocation(CsmConstants.ID, "othrSoldier")));
        if( CsmConfiguration.enableBrickSoldierReverseRecipe ) recipeList.add(new BrickSoldierConvRecipe().setRegistryName(new ResourceLocation(CsmConstants.ID, "brickSoldierConv")));
        if( CsmConfiguration.enableDyedGlassSoldierRecipe ) recipeList.add(new DyedGlassSoldierRecipe().setRegistryName(new ResourceLocation(CsmConstants.ID, "dyedGlassSoldier")));

        recipeList.forEach(event.getRegistry()::register);
    }

    public static void registerSmelting() {
        FurnaceRecipes.instance().addSmelting(ItemRegistry.DOLL_SOLDIER, new ItemStack(ItemRegistry.DOLL_BRICK_SOLDIER, 1), 0);
    }
}
