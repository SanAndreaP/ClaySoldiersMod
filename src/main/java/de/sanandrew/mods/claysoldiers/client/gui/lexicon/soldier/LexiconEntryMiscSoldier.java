/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.gui.lexicon.soldier;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.soldier.ITeam;
import de.sanandrew.mods.claysoldiers.client.util.ClientProxy;
import de.sanandrew.mods.claysoldiers.crafting.OtherSoldierRecipe;
import de.sanandrew.mods.claysoldiers.registry.team.TeamRegistry;
import de.sanandrew.mods.claysoldiers.util.CsmConfig;
import de.sanandrew.mods.sanlib.api.client.lexicon.ILexiconEntryCraftingGrid;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LexiconEntryMiscSoldier
        implements ILexiconEntryCraftingGrid
{
    private static final String ID = "miscSoldier";
    private final ItemStack[] icons;
    private final ResourceLocation prevPic;
    private final NonNullList<IRecipe> recipes;

    public LexiconEntryMiscSoldier() {
        this.icons = Arrays.stream(OtherSoldierRecipe.TEAMS).map(uuid -> TeamRegistry.INSTANCE.getNewTeamStack(1, uuid)).toArray(ItemStack[]::new);
        this.prevPic = new ResourceLocation(CsmConstants.ID, "textures/gui/lexicon/page_pics/soldiers/" + CsmConstants.ID + "_miscsoldier.png");
        this.recipes = NonNullList.create();

        List<ItemStack> allTeams = new ArrayList<>();
        for( ITeam team : TeamRegistry.INSTANCE.getTeams() ) {
            allTeams.add(TeamRegistry.INSTANCE.getNewTeamStack(1, team));
        }
        Ingredient allTeamsIngredient = Ingredient.fromStacks(allTeams.toArray(new ItemStack[0]));

        for( int i = 0, max = OtherSoldierRecipe.TEAMS.length; i < max; i++ ) {
            NonNullList<Ingredient> ingredients = NonNullList.create();

            Ingredient dye = Ingredient.fromStacks(OtherSoldierRecipe.ITEMS[i]);

            ingredients.add(Ingredient.EMPTY);
            ingredients.add(allTeamsIngredient);
            ingredients.add(Ingredient.EMPTY);
            ingredients.add(allTeamsIngredient);
            ingredients.add(dye);
            ingredients.add(allTeamsIngredient);
            ingredients.add(Ingredient.EMPTY);
            ingredients.add(allTeamsIngredient);
            ingredients.add(Ingredient.EMPTY);

            this.recipes.add(new ShapedRecipes("", 3, 3, ingredients, TeamRegistry.INSTANCE.getNewTeamStack(4, OtherSoldierRecipe.TEAMS[i])));
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
        return ClientProxy.lexiconInstance.getCraftingRenderID();
    }

    @Nonnull
    @Override
    public ItemStack getEntryIcon() {
        return this.icons[(int) ((System.nanoTime() / 1_000_000_000) % this.icons.length)];
    }

    @Override
    public ResourceLocation getPicture() {
        return this.prevPic;
    }

    @Override
    public NonNullList<IRecipe> getRecipes() {
        return CsmConfig.Recipes.enableResourceSoldierRecipe ? this.recipes : NonNullList.create();
    }

    @Nonnull
    @Override
    public String getSrcTitle() {
        return ClientProxy.lexiconInstance.getTranslatedTitle(this);
    }

    @Nonnull
    @Override
    public String getSrcText() {
        return ClientProxy.lexiconInstance.getTranslatedText(this);
    }
}
