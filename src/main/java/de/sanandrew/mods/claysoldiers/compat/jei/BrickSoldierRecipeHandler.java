/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.compat.jei;

import com.google.common.collect.ImmutableList;
import de.sanandrew.mods.claysoldiers.api.soldier.Team;
import de.sanandrew.mods.claysoldiers.crafting.OtherSoldierRecipe;
import de.sanandrew.mods.claysoldiers.registry.ItemRegistry;
import de.sanandrew.mods.claysoldiers.registry.TeamRegistry;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BrickSoldierRecipeHandler
        implements IRecipeHandler<BrickSoldierRecipeHandler.JeiBrickSoldierRecipe>
{
    @Override
    public Class<JeiBrickSoldierRecipe> getRecipeClass() {
        return JeiBrickSoldierRecipe.class;
    }

    @Override
    @Deprecated
    public String getRecipeCategoryUid() {
        return VanillaRecipeCategoryUid.CRAFTING;
    }

    @Override
    public String getRecipeCategoryUid(JeiBrickSoldierRecipe recipe) {
        return VanillaRecipeCategoryUid.CRAFTING;
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(JeiBrickSoldierRecipe recipe) {
        return new BrickSoldierRecipeWrapper(recipe);
    }

    @Override
    public boolean isRecipeValid(JeiBrickSoldierRecipe recipe) {
        return true;
    }

    static List<JeiBrickSoldierRecipe> getRecipes() {
        List<JeiBrickSoldierRecipe> recipes = new ArrayList<>();

        recipes.add(new JeiBrickSoldierRecipe(TeamRegistry.NULL_TEAM));
        TeamRegistry.INSTANCE.getTeams().forEach(team -> recipes.add(new JeiBrickSoldierRecipe(team)));

        return recipes;
    }

    static class JeiBrickSoldierRecipe
    {
        final ItemStack result;
        final List<List<ItemStack>> ingredients;

        JeiBrickSoldierRecipe(Team team) {
            this.ingredients = new ArrayList<>();

            this.ingredients.add(ImmutableList.of(new ItemStack(Items.GHAST_TEAR, 1)));
            this.ingredients.add(ImmutableList.of(new ItemStack(ItemRegistry.doll_brick_soldier, 1)));

            if( team != TeamRegistry.NULL_TEAM ) {
                this.result = TeamRegistry.INSTANCE.getNewTeamStack(1, team);
                this.ingredients.add(ImmutableList.of(TeamRegistry.INSTANCE.getNewTeamStack(1, team)));
            } else {
                this.result = TeamRegistry.INSTANCE.getNewTeamStack(1, TeamRegistry.SOLDIER_CLAY);
            }
        }
    }
}
