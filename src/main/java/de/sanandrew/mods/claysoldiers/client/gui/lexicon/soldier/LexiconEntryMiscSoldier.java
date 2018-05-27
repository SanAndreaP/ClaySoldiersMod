/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.gui.lexicon.soldier;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconEntryCraftingGrid;
import de.sanandrew.mods.claysoldiers.api.misc.IDummyMultiRecipe;
import de.sanandrew.mods.claysoldiers.api.soldier.ITeam;
import de.sanandrew.mods.claysoldiers.client.gui.lexicon.crafting.LexiconRenderCraftingGrid;
import de.sanandrew.mods.claysoldiers.crafting.DyedSoldierRecipe;
import de.sanandrew.mods.claysoldiers.crafting.OtherSoldierRecipe;
import de.sanandrew.mods.claysoldiers.registry.team.TeamRegistry;
import de.sanandrew.mods.claysoldiers.util.CsmConfiguration;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreIngredient;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LexiconEntryMiscSoldier
        implements ILexiconEntryCraftingGrid
{
    private static final String ID = CsmConstants.ID + ":miscSoldier";
    private final ItemStack[] icons;
    private final ResourceLocation prevPic;
    private final IRecipe recipe;

    public LexiconEntryMiscSoldier() {
        this.icons = Arrays.stream(OtherSoldierRecipe.TEAMS).map(uuid -> TeamRegistry.INSTANCE.getNewTeamStack(1, uuid)).toArray(ItemStack[]::new);
        this.prevPic = new ResourceLocation(CsmConstants.ID, "textures/gui/lexicon/page_pics/soldiers/" + CsmConstants.ID + "_miscsoldier.png");
        this.recipe = new DummyRecipeMiscSoldiers();
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
        return LexiconRenderCraftingGrid.ID;
    }

    @Override
    public String getEntryName() {
        return I18n.format(String.format("%s.lexicon.%s.%s.name", CsmConstants.ID, this.getGroupId(), this.getId()));
    }

    @Override
    public String getEntryText() {
        return I18n.format(String.format("%s.lexicon.%s.%s.text", CsmConstants.ID, this.getGroupId(), this.getId()));
    }

    @Nonnull
    @Override
    public ItemStack getEntryIcon() {
        return this.icons[MiscUtils.RNG.randomInt(this.icons.length)];
    }

    @Override
    public ResourceLocation getPicture() {
        return this.prevPic;
    }

    @Override
    public boolean divideAfter() {
        return false;
    }

    @Override
    public IRecipe getRecipe() {
        return CsmConfiguration.enableResourceSoldierRecipe ? this.recipe : null;
    }

    private static final class DummyRecipeMiscSoldiers
            implements IDummyMultiRecipe
    {
        List<IRecipe> recipes;

        DummyRecipeMiscSoldiers() {
            this.recipes = new ArrayList<>();

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
        public int getRecipeWidth() {
            return 3;
        }

        @Override
        public int getRecipeHeight() {
            return 3;
        }

        @Override
        public List<IRecipe> getRecipes() {
            return this.recipes;
        }
    }
}
