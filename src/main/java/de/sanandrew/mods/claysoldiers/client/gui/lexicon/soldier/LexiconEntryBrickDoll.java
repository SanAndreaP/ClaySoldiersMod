/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.gui.lexicon.soldier;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconEntryFurnace;
import de.sanandrew.mods.claysoldiers.api.misc.IDummyMultiRecipe;
import de.sanandrew.mods.claysoldiers.api.soldier.ITeam;
import de.sanandrew.mods.claysoldiers.client.gui.lexicon.crafting.LexiconRenderBrickDoll;
import de.sanandrew.mods.claysoldiers.registry.ItemRegistry;
import de.sanandrew.mods.claysoldiers.registry.team.TeamRegistry;
import de.sanandrew.mods.claysoldiers.registry.team.Teams;
import de.sanandrew.mods.claysoldiers.util.CsmConfiguration;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class LexiconEntryBrickDoll
        implements ILexiconEntryFurnace
{
    private static final String ID = "brickDoll";
    private final ItemStack icon;
    private final IRecipe recipeNormal;
    private final IRecipe recipeTeamed;
    private final Map<Ingredient, ItemStack> furnaceRecipe;

    public LexiconEntryBrickDoll() {
        this.icon = new ItemStack(ItemRegistry.DOLL_BRICK_SOLDIER);
        this.recipeNormal = new DummyRecipeBrickDollRev(false);
        this.recipeTeamed = new DummyRecipeBrickDollRev(true);
        this.furnaceRecipe = Collections.singletonMap(Ingredient.fromStacks(TeamRegistry.INSTANCE.getTeams().stream()
                                                                                                 .map(team -> TeamRegistry.INSTANCE.getNewTeamStack(1, team))
                                                                                                 .toArray(ItemStack[]::new)),
                                                      new ItemStack(ItemRegistry.DOLL_BRICK_SOLDIER));
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getGroupId() {
        return LexiconGroupSoldiers.GRP_NAME;
    }

    @Override
    public String getPageRenderId() {
        return LexiconRenderBrickDoll.ID;
    }

    @Nonnull
    @Override
    public ItemStack getEntryIcon() {
        return this.icon;
    }

    @Override
    public boolean divideAfter() {
        return false;
    }

    public IRecipe getNormalRecipe() {
        return CsmConfiguration.enableBrickSoldierReverseRecipe ? this.recipeNormal : null;
    }

    public IRecipe getTeamedRecipe() {
        return CsmConfiguration.enableBrickSoldierReverseRecipe ? this.recipeTeamed : null;
    }

    @Override
    public Map<Ingredient, ItemStack> getRecipes() {
        return furnaceRecipe;
    }

    private static final class DummyRecipeBrickDollRev
            implements IDummyMultiRecipe
    {
        final List<IRecipe> recipes;
        final int width;

        DummyRecipeBrickDollRev(boolean isTeamed) {
            this.recipes = new ArrayList<>();
            this.width = isTeamed ? 3 : 2;

            if( isTeamed ) {
                for( ITeam team : TeamRegistry.INSTANCE.getTeams() ) {
                    NonNullList<Ingredient> ingredients = NonNullList.create();
                    ingredients.add(Ingredient.fromItem(ItemRegistry.DOLL_BRICK_SOLDIER));
                    ingredients.add(Ingredient.fromItem(Items.GHAST_TEAR));
                    ingredients.add(Ingredient.fromStacks(TeamRegistry.INSTANCE.getNewTeamStack(1, team)));

                    this.recipes.add(new ShapelessRecipes("", TeamRegistry.INSTANCE.getNewTeamStack(1, team), ingredients));
                }
            } else {
                NonNullList<Ingredient> ingredients = NonNullList.create();
                ingredients.add(Ingredient.fromItem(ItemRegistry.DOLL_BRICK_SOLDIER));
                ingredients.add(Ingredient.fromItem(Items.GHAST_TEAR));

                this.recipes.add(new ShapelessRecipes("", TeamRegistry.INSTANCE.getNewTeamStack(1, Teams.SOLDIER_CLAY), ingredients));
            }
        }

        @Override
        public int getRecipeWidth() {
            return this.width;
        }

        @Override
        public int getRecipeHeight() {
            return 1;
        }

        @Override
        public List<IRecipe> getRecipes() {
            return this.recipes;
        }
    }
}
