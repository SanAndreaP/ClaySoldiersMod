/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.compat.jei;

import com.google.common.collect.ImmutableList;
import de.sanandrew.mods.claysoldiers.api.soldier.Team;
import de.sanandrew.mods.claysoldiers.crafting.DyedSoldierRecipe;
import de.sanandrew.mods.claysoldiers.registry.TeamRegistry;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ClearSoldierRecipeHandler
        implements IRecipeHandler<ClearSoldierRecipeHandler.JeiClearSoldierRecipe>
{
    @Override
    public Class<JeiClearSoldierRecipe> getRecipeClass() {
        return JeiClearSoldierRecipe.class;
    }

    @Override
    public String getRecipeCategoryUid() {
        return VanillaRecipeCategoryUid.CRAFTING;
    }

    @Override
    public String getRecipeCategoryUid(JeiClearSoldierRecipe recipe) {
        return VanillaRecipeCategoryUid.CRAFTING;
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(JeiClearSoldierRecipe recipe) {
        return new ClearSoldierRecipeWrapper(recipe);
    }

    @Override
    public boolean isRecipeValid(JeiClearSoldierRecipe recipe) {
        return true;
    }

    static List<JeiClearSoldierRecipe> getRecipes() {
        List<JeiClearSoldierRecipe> recipes = new ArrayList<>();

        for( int j = 1; j <= 8; j++ ) {
            recipes.add(new JeiClearSoldierRecipe(j));
        }

        return recipes;
    }

    static class JeiClearSoldierRecipe
    {
        final ItemStack result;
        final List<List<ItemStack>> ingredients;

        JeiClearSoldierRecipe(int count) {
            this.result = TeamRegistry.INSTANCE.getNewTeamStack(count, TeamRegistry.SOLDIER_CLAY);
            this.ingredients = new ArrayList<>();

            this.ingredients.add(ImmutableList.of(new ItemStack(Items.WATER_BUCKET, 1)));

            List<Team> teamList = TeamRegistry.INSTANCE.getTeams();
            for( int i = 0; i < count; i++ ) {
                List<ItemStack> ingredList = new ArrayList<>();

                for( Team team : teamList ) {
                    ingredList.add(TeamRegistry.INSTANCE.getNewTeamStack(1, team));
                }

                this.ingredients.add(ingredList);
            }
        }
    }
}
