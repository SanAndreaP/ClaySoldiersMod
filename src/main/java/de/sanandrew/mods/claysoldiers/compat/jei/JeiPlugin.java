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
import de.sanandrew.mods.claysoldiers.registry.team.TeamRegistry;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;

@JEIPlugin
@SideOnly(Side.CLIENT)
public class JeiPlugin
    implements IModPlugin
{
    public JeiPlugin() { }

    @Override
    public void register(IModRegistry registry) {
        registry.handleRecipes(ItemDisruptor.DisruptorType.class, new DisruptorRecipeWrapper.Factory(), VanillaRecipeCategoryUid.CRAFTING);
        registry.handleRecipes(JeiDyedSoldierRecipe.class, new DyedSoldierRecipeWrapper.Factory(), VanillaRecipeCategoryUid.CRAFTING);
        registry.handleRecipes(JeiClearSoldierRecipe.class, new ClearSoldierRecipeWrapper.Factory(), VanillaRecipeCategoryUid.CRAFTING);
        registry.handleRecipes(JeiOtherSoldierRecipe.class, new OtherSoldierRecipeWrapper.Factory(), VanillaRecipeCategoryUid.CRAFTING);
        registry.handleRecipes(JeiBrickSoldierRecipe.class, new BrickSoldierRecipeWrapper.Factory(), VanillaRecipeCategoryUid.CRAFTING);
        registry.handleRecipes(JeiDyedGlassSoldierRecipe.class, new DyedGlassSoldierRecipeWrapper.Factory(), VanillaRecipeCategoryUid.CRAFTING);

        registry.addRecipes(Arrays.asList(ItemDisruptor.DisruptorType.VALUES), VanillaRecipeCategoryUid.CRAFTING);
        registry.addRecipes(JeiDyedSoldierRecipe.getRecipes(), VanillaRecipeCategoryUid.CRAFTING);
        registry.addRecipes(JeiClearSoldierRecipe.getRecipes(), VanillaRecipeCategoryUid.CRAFTING);
        registry.addRecipes(JeiOtherSoldierRecipe.getRecipes(), VanillaRecipeCategoryUid.CRAFTING);
        registry.addRecipes(JeiBrickSoldierRecipe.getRecipes(), VanillaRecipeCategoryUid.CRAFTING);
        registry.addRecipes(JeiDyedGlassSoldierRecipe.getRecipes(), VanillaRecipeCategoryUid.CRAFTING);
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
        subtypeRegistry.registerSubtypeInterpreter(ItemRegistry.DISRUPTOR, itemStack -> ItemDisruptor.getType(itemStack).name());
        subtypeRegistry.registerSubtypeInterpreter(ItemRegistry.DOLL_SOLDIER, itemStack -> TeamRegistry.INSTANCE.getTeam(itemStack).getName());
        subtypeRegistry.registerSubtypeInterpreter(ItemRegistry.DOLL_HORSE, itemStack -> ItemRegistry.DOLL_HORSE.getType(itemStack).getName());
        subtypeRegistry.registerSubtypeInterpreter(ItemRegistry.DOLL_PEGASUS, itemStack -> ItemRegistry.DOLL_PEGASUS.getType(itemStack).getName());
        subtypeRegistry.registerSubtypeInterpreter(ItemRegistry.DOLL_TURTLE, itemStack -> ItemRegistry.DOLL_TURTLE.getType(itemStack).getName());
    }
}
