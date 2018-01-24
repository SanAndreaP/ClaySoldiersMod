/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.crafting;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.item.ItemDisruptor;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = CsmConstants.ID)
public class CraftingRecipes
{
    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        event.getRegistry()
             .registerAll(new DisruptorRecipe(ItemDisruptor.DisruptorType.CLAY).setRegistryName(new ResourceLocation(CsmConstants.ID, "disruptorClay")),
                          new DisruptorRecipe(ItemDisruptor.DisruptorType.HARDENED).setRegistryName(new ResourceLocation(CsmConstants.ID, "disruptorHardened")),
                          new DisruptorRecipe(ItemDisruptor.DisruptorType.OBSIDIAN).setRegistryName(new ResourceLocation(CsmConstants.ID, "disruptorObsidian")),
                          new DyedSoldierRecipe().setRegistryName(new ResourceLocation(CsmConstants.ID, "dyedSoldier")),
                          new ClearSoldierRecipe().setRegistryName(new ResourceLocation(CsmConstants.ID, "clearSoldier")),
                          new OtherSoldierRecipe().setRegistryName(new ResourceLocation(CsmConstants.ID, "othrSoldier")),
                          new BrickSoldierConvRecipe().setRegistryName(new ResourceLocation(CsmConstants.ID, "brickSoldierConv")),
                          new DyedGlassSoldierRecipe().setRegistryName(new ResourceLocation(CsmConstants.ID, "dyedGlassSoldier")));
    }
}
