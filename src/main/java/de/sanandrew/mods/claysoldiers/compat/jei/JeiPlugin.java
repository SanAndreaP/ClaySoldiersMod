/*
 * ****************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 * *****************************************************************************************************************
 */
package de.sanandrew.mods.claysoldiers.compat.jei;

import de.sanandrew.mods.claysoldiers.item.ItemDisruptor;
import de.sanandrew.mods.claysoldiers.registry.ItemRegistry;
import de.sanandrew.mods.claysoldiers.registry.TeamRegistry;
import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.JEIPlugin;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@JEIPlugin
@SideOnly(Side.CLIENT)
public class JeiPlugin extends BlankModPlugin {
    public JeiPlugin() {}

    @Override
    public void register(IModRegistry registry) {
        registry.addRecipeHandlers(new DisruptorRecipeHandler());
        registry.addRecipes(DisruptorRecipeHandler.getRecipeList());

        registry.addRecipeHandlers(new DyedSoldierRecipeHandler());
        registry.addRecipes(DyedSoldierRecipeHandler.getRecipes());
        registry.addRecipeHandlers(new ClearSoldierRecipeHandler());
        registry.addRecipes(ClearSoldierRecipeHandler.getRecipes());
        registry.addRecipeHandlers(new OtherSoldierRecipeHandler());
        registry.addRecipes(OtherSoldierRecipeHandler.getRecipes());
        registry.addRecipeHandlers(new BrickSoldierRecipeHandler());
        registry.addRecipes(BrickSoldierRecipeHandler.getRecipes());
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
        subtypeRegistry.registerSubtypeInterpreter(ItemRegistry.disruptor, itemStack -> ItemDisruptor.getType(itemStack).name());
        subtypeRegistry.registerSubtypeInterpreter(ItemRegistry.doll_soldier, itemStack -> TeamRegistry.INSTANCE.getTeam(itemStack).toString());
    }
}
