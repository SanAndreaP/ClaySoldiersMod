/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.compat.jei;

import com.google.common.collect.ImmutableList;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.ITeam;
import de.sanandrew.mods.claysoldiers.registry.ItemRegistry;
import de.sanandrew.mods.claysoldiers.registry.team.TeamRegistry;
import de.sanandrew.mods.claysoldiers.registry.team.Teams;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

class JeiBrickSoldierRecipe
{
    final ItemStack result;
    final List<List<ItemStack>> ingredients;

    JeiBrickSoldierRecipe(ITeam team) {
        this.ingredients = new ArrayList<>();

        this.ingredients.add(ImmutableList.of(new ItemStack(Items.GHAST_TEAR, 1)));
        this.ingredients.add(ImmutableList.of(new ItemStack(ItemRegistry.DOLL_BRICK_SOLDIER, 1)));

        if( team != TeamRegistry.NULL_TEAM ) {
            this.result = TeamRegistry.INSTANCE.getNewTeamStack(1, team);
            this.ingredients.add(ImmutableList.of(TeamRegistry.INSTANCE.getNewTeamStack(1, team)));
        } else {
            this.result = TeamRegistry.INSTANCE.getNewTeamStack(1, Teams.SOLDIER_CLAY);
        }
    }

    static List<JeiBrickSoldierRecipe> getRecipes() {
        List<JeiBrickSoldierRecipe> recipes = new ArrayList<>();

        recipes.add(new JeiBrickSoldierRecipe(TeamRegistry.NULL_TEAM));
        TeamRegistry.INSTANCE.getTeams().forEach(team -> recipes.add(new JeiBrickSoldierRecipe(team)));

        return recipes;
    }
}
