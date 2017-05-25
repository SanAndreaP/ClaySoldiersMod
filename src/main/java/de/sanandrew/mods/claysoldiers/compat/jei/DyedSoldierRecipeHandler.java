/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.compat.jei;

import de.sanandrew.mods.claysoldiers.api.soldier.ITeam;
import de.sanandrew.mods.claysoldiers.crafting.DyedSoldierRecipe;
import de.sanandrew.mods.claysoldiers.registry.TeamRegistry;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DyedSoldierRecipeHandler
        implements IRecipeHandler<DyedSoldierRecipeHandler.JeiDyedSoldierRecipe>
{
    @Override
    public Class<JeiDyedSoldierRecipe> getRecipeClass() {
        return JeiDyedSoldierRecipe.class;
    }

    @Override
    @Deprecated
    public String getRecipeCategoryUid() {
        return VanillaRecipeCategoryUid.CRAFTING;
    }

    @Override
    public String getRecipeCategoryUid(JeiDyedSoldierRecipe recipe) {
        return VanillaRecipeCategoryUid.CRAFTING;
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(JeiDyedSoldierRecipe recipe) {
        return new DyedSoldierRecipeWrapper(recipe);
    }

    @Override
    public boolean isRecipeValid(JeiDyedSoldierRecipe recipe) {
        return true;
    }

    static List<JeiDyedSoldierRecipe> getRecipes() {
        List<JeiDyedSoldierRecipe> recipes = new ArrayList<>();

        for( int i = 0; i < DyedSoldierRecipe.TEAMS.length; i++ ) {
            String dye = DyedSoldierRecipe.DYES[i];
            UUID team = DyedSoldierRecipe.TEAMS[i];
            for( int j = 1; j <= 8; j++ ) {
                recipes.add(new JeiDyedSoldierRecipe(dye, team, j));
            }
        }

        return recipes;
    }

    static class JeiDyedSoldierRecipe
    {
        final ItemStack result;
        final List<List<ItemStack>> ingredients;

        JeiDyedSoldierRecipe(String dyeColor, UUID team, int count) {
            this.result = TeamRegistry.INSTANCE.getNewTeamStack(count, team);
            this.ingredients = new ArrayList<>();

            this.ingredients.add(OreDictionary.getOres(dyeColor));

            List<ITeam> teamList = TeamRegistry.INSTANCE.getTeams();
            for( int i = 0; i < count; i++ ) {
                List<ItemStack> ingredList = new ArrayList<>();

                for( ITeam teamInst : teamList ) {
                    ingredList.add(TeamRegistry.INSTANCE.getNewTeamStack(1, teamInst.getId()));
                }

                this.ingredients.add(ingredList);
            }
        }
    }
}
