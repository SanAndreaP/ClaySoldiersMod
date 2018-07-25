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
import de.sanandrew.mods.claysoldiers.registry.team.TeamRegistry;
import de.sanandrew.mods.claysoldiers.registry.team.Teams;
import de.sanandrew.mods.claysoldiers.util.CsmConfig;
import de.sanandrew.mods.sanlib.api.client.lexicon.ILexiconEntryCraftingGrid;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class LexiconEntryWashSoldier
        implements ILexiconEntryCraftingGrid
{
    private static final String ID = "washSoldier";
    private final ItemStack icon;
    private final ResourceLocation prevPic;
    private final NonNullList<IRecipe> recipes;

    public LexiconEntryWashSoldier() {
        this.icon = new ItemStack(Items.WATER_BUCKET);
        this.prevPic = new ResourceLocation(CsmConstants.ID, "textures/gui/lexicon/page_pics/soldiers/" + CsmConstants.ID + "_washsoldier.png");
        this.recipes = NonNullList.create();

        List<ItemStack> allTeams = new ArrayList<>();
        for( ITeam team : TeamRegistry.INSTANCE.getTeams() ) {
            allTeams.add(TeamRegistry.INSTANCE.getNewTeamStack(1, team));
        }
        Ingredient allTeamsIngredient = Ingredient.fromStacks(allTeams.toArray(new ItemStack[0]));

        for( int i = 1, max = 8; i <= max; i++ ) {
            NonNullList<Ingredient> ingredients = NonNullList.create();

            ingredients.add(Ingredient.fromItem(Items.WATER_BUCKET));
            ingredients.add(allTeamsIngredient);
            switch( i ) { // no breaks!
                case 8: ingredients.add(allTeamsIngredient);
                case 7: ingredients.add(allTeamsIngredient);
                case 6: ingredients.add(allTeamsIngredient);
                case 5: ingredients.add(allTeamsIngredient);
                case 4: ingredients.add(allTeamsIngredient);
                case 3: ingredients.add(allTeamsIngredient);
                case 2: ingredients.add(allTeamsIngredient);
            }

            this.recipes.add(new ShapelessRecipes("", TeamRegistry.INSTANCE.getNewTeamStack(i, Teams.SOLDIER_CLAY), ingredients));
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
        return this.icon;
    }

    @Override
    public ResourceLocation getPicture() {
        return this.prevPic;
    }

    @Override
    public NonNullList<IRecipe> getRecipes() {
        return CsmConfig.Recipes.enableSoldierWashRecipe ? this.recipes : NonNullList.create();
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
