/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.compat.jei;

import com.google.common.collect.ImmutableList;
import de.sanandrew.mods.claysoldiers.api.soldier.ITeam;
import de.sanandrew.mods.claysoldiers.registry.team.TeamRegistry;
import de.sanandrew.mods.claysoldiers.registry.team.Teams;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

class JeiClearSoldierRecipe
{
    final ItemStack result;
    final List<List<ItemStack>> ingredients;

    JeiClearSoldierRecipe(int count) {
        this.result = TeamRegistry.INSTANCE.getNewTeamStack(count, Teams.SOLDIER_CLAY);
        this.ingredients = new ArrayList<>();

        this.ingredients.add(ImmutableList.of(new ItemStack(Items.WATER_BUCKET, 1)));

        List<ITeam> teamList = TeamRegistry.INSTANCE.getTeams();
        for( int i = 0; i < count; i++ ) {
            List<ItemStack> ingredList = new ArrayList<>();

            for( ITeam team : teamList ) {
                ingredList.add(TeamRegistry.INSTANCE.getNewTeamStack(1, team));
            }

            this.ingredients.add(ingredList);
        }
    }

    static List<JeiClearSoldierRecipe> getRecipes() {
        List<JeiClearSoldierRecipe> recipes = new ArrayList<>();

        for( int j = 1; j <= 8; j++ ) {
            recipes.add(new JeiClearSoldierRecipe(j));
        }

        return recipes;
    }
}
