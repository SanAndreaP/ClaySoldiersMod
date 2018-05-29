/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.compat.jei;

import de.sanandrew.mods.claysoldiers.api.soldier.ITeam;
import de.sanandrew.mods.claysoldiers.crafting.DyedGlassSoldierRecipe;
import de.sanandrew.mods.claysoldiers.registry.team.TeamRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class JeiDyedGlassSoldierRecipe
{
    final ItemStack result;
    final List<List<ItemStack>> ingredients;

    JeiDyedGlassSoldierRecipe(String dyeColor, UUID team, int count) {
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

    static List<JeiDyedGlassSoldierRecipe> getRecipes() {
        List<JeiDyedGlassSoldierRecipe> recipes = new ArrayList<>();

        for( int i = 0; i < DyedGlassSoldierRecipe.TEAMS.length; i++ ) {
            String color = DyedGlassSoldierRecipe.COLORS[i];
            UUID team = DyedGlassSoldierRecipe.TEAMS[i];
            for( int j = 1; j <= 8; j++ ) {
                recipes.add(new JeiDyedGlassSoldierRecipe(color, team, j));
            }
        }

        return recipes;
    }
}
