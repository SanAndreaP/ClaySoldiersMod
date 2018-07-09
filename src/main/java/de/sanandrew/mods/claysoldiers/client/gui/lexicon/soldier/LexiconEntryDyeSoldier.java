/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.gui.lexicon.soldier;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconEntryCraftingGrid;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconPageRender;
import de.sanandrew.mods.claysoldiers.api.soldier.ITeam;
import de.sanandrew.mods.claysoldiers.crafting.DyedSoldierRecipe;
import de.sanandrew.mods.claysoldiers.registry.team.TeamRegistry;
import de.sanandrew.mods.claysoldiers.util.CsmConfig;
import de.sanandrew.mods.claysoldiers.util.Lang;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreIngredient;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LexiconEntryDyeSoldier
        implements ILexiconEntryCraftingGrid
{
    private static final String ID = "dyeSoldier";
    private final ItemStack[] icons;
    private final ResourceLocation prevPic;
    private final NonNullList<IRecipe> recipes;

    public LexiconEntryDyeSoldier() {
        this.icons = Arrays.stream(DyedSoldierRecipe.TEAMS).map(uuid -> TeamRegistry.INSTANCE.getNewTeamStack(1, uuid)).toArray(ItemStack[]::new);
        this.prevPic = new ResourceLocation(CsmConstants.ID, "textures/gui/lexicon/page_pics/soldiers/" + CsmConstants.ID + "_dyesoldier.png");
        this.recipes = NonNullList.create();

        List<ItemStack> allTeams = new ArrayList<>();
        for( ITeam team : TeamRegistry.INSTANCE.getTeams() ) {
            allTeams.add(TeamRegistry.INSTANCE.getNewTeamStack(1, team));
        }
        Ingredient allTeamsIngredient = Ingredient.fromStacks(allTeams.toArray(new ItemStack[0]));

        for( int cnt = 1; cnt <= 8; cnt++ ) {
            for( int i = 0, max = DyedSoldierRecipe.TEAMS.length; i < max; i++ ) {
                NonNullList<Ingredient> ingredients = NonNullList.create();

                Ingredient dye = new OreIngredient(DyedSoldierRecipe.DYES[i]);
                ingredients.add(dye);

                ingredients.add(allTeamsIngredient);
                switch( cnt ) { // no breaks!
                    case 8: ingredients.add(allTeamsIngredient);
                    case 7: ingredients.add(allTeamsIngredient);
                    case 6: ingredients.add(allTeamsIngredient);
                    case 5: ingredients.add(allTeamsIngredient);
                    case 4: ingredients.add(allTeamsIngredient);
                    case 3: ingredients.add(allTeamsIngredient);
                    case 2: ingredients.add(allTeamsIngredient);
                }

                this.recipes.add(new ShapelessRecipes("", TeamRegistry.INSTANCE.getNewTeamStack(cnt, DyedSoldierRecipe.TEAMS[i]), ingredients));
            }
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
        return ILexiconPageRender.RENDER_CRAFTING_ID;
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

    @Nonnull
    @Override
    public NonNullList<IRecipe> getRecipes() {
        return CsmConfig.Recipes.enableDyedSoldierRecipe ? this.recipes : NonNullList.create();
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
