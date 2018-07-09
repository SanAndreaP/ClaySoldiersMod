/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.gui.lexicon.soldier;

import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconEntryFurnace;
import de.sanandrew.mods.claysoldiers.api.soldier.ITeam;
import de.sanandrew.mods.claysoldiers.client.gui.lexicon.crafting.LexiconRenderBrickDoll;
import de.sanandrew.mods.claysoldiers.registry.ItemRegistry;
import de.sanandrew.mods.claysoldiers.registry.team.TeamRegistry;
import de.sanandrew.mods.claysoldiers.registry.team.Teams;
import de.sanandrew.mods.claysoldiers.util.CsmConfig;
import de.sanandrew.mods.claysoldiers.util.Lang;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Map;

public class LexiconEntryBrickDoll
        implements ILexiconEntryFurnace
{
    private static final String ID = "brickDoll";
    private final ItemStack icon;
    private final IRecipe recipeNormal;
    private final NonNullList<IRecipe> recipesTeamed;
    private final Map<Ingredient, ItemStack> furnaceRecipe;

    public LexiconEntryBrickDoll() {
        this.icon = new ItemStack(ItemRegistry.DOLL_BRICK_SOLDIER);
        this.furnaceRecipe = Collections.singletonMap(Ingredient.fromStacks(TeamRegistry.INSTANCE.getTeams().stream()
                                                                                                 .map(team -> TeamRegistry.INSTANCE.getNewTeamStack(1, team))
                                                                                                 .toArray(ItemStack[]::new)),
                                                      new ItemStack(ItemRegistry.DOLL_BRICK_SOLDIER));

        NonNullList<Ingredient> ingredients = NonNullList.create();
        ingredients.add(Ingredient.fromItem(ItemRegistry.DOLL_BRICK_SOLDIER));
        ingredients.add(Ingredient.fromItem(Items.GHAST_TEAR));

        this.recipeNormal = new ShapelessRecipes("", TeamRegistry.INSTANCE.getNewTeamStack(1, Teams.SOLDIER_CLAY), ingredients);
        this.recipesTeamed = NonNullList.create();
        for( ITeam team : TeamRegistry.INSTANCE.getTeams() ) {
            ingredients = NonNullList.create();
            ingredients.add(Ingredient.fromItem(ItemRegistry.DOLL_BRICK_SOLDIER));
            ingredients.add(Ingredient.fromItem(Items.GHAST_TEAR));
            ingredients.add(Ingredient.fromStacks(TeamRegistry.INSTANCE.getNewTeamStack(1, team)));

            this.recipesTeamed.add(new ShapelessRecipes("", TeamRegistry.INSTANCE.getNewTeamStack(1, team), ingredients));
        }
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

    public IRecipe getNormalRecipe() {
        return CsmConfig.Recipes.enableBrickSoldierReverseRecipe ? this.recipeNormal : null;
    }

    @Nonnull
    public NonNullList<IRecipe> getTeamedRecipes() {
        return CsmConfig.Recipes.enableBrickSoldierReverseRecipe ? this.recipesTeamed : NonNullList.create();
    }

    @Override
    public Map<Ingredient, ItemStack> getRecipes() {
        return furnaceRecipe;
    }

    @Nonnull
    @Override
    public String getSrcTitle() {
        return Lang.translate(Lang.LEXICON_ENTRY_NAME.get(this.getGroupId(), this.getId()));
    }

    @Nonnull
    @Override
    public String getSrcText() {
        return Lang.translate(Lang.LEXICON_ENTRY_TEXT.get(this.getGroupId(), this.getId()));
    }
}
