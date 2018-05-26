/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.gui.lexicon.soldier;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconEntry;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconEntryCraftingGrid;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconGuiHelper;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconPageRender;
import de.sanandrew.mods.claysoldiers.registry.team.TeamRegistry;
import de.sanandrew.mods.claysoldiers.registry.team.Teams;
import de.sanandrew.mods.claysoldiers.util.Resources;
import de.sanandrew.mods.sanlib.lib.util.ItemStackUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.List;
import java.util.Locale;
import java.util.stream.StreamSupport;

@SideOnly(Side.CLIENT)
public class LexiconRenderCraftingGrid
        implements ILexiconPageRender
{
    public static final String ID = CsmConstants.ID + ":craftingGrid";

    private int drawHeight;
    private List<GuiButton> entryButtons;
    private IRecipe recipe;
    private ItemStack[][][] crfGrid;
    private boolean isShapeless;

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public void initPage(ILexiconEntry entry, ILexiconGuiHelper helper, List<GuiButton> globalButtons, List<GuiButton> entryButtons) {
        if( !(entry instanceof ILexiconEntryCraftingGrid) ) {
            return;
        }

        this.entryButtons = entryButtons;
//        if( this.recipe == null ) {
            recipe = ((ILexiconEntryCraftingGrid) entry).getRecipe();

            if( recipe == null ) {
                final ItemStack result = ((ILexiconEntryCraftingGrid) entry).getRecipeResult();
                recipe = StreamSupport.stream(CraftingManager.REGISTRY.spliterator(), false)
                                      .filter(r -> !r.isDynamic() && ItemStackUtils.areEqual(r.getRecipeOutput(), result) && r.canFit(3, 3))
                                      .findFirst().orElse(null);
            }
//        }

        if( recipe != null ) {
            int w, h;
            NonNullList<Ingredient> ingredients = recipe.getIngredients();
            this.isShapeless = recipe.getClass().getName().toLowerCase(Locale.ROOT).contains("shapeless");
            if( recipe instanceof ShapedRecipes ) {
                w = ((ShapedRecipes) recipe).recipeWidth;
                h = ((ShapedRecipes) recipe).recipeHeight;
            } else if( recipe instanceof ShapedOreRecipe ) {
                w = ((ShapedOreRecipe) recipe).getRecipeWidth();
                h = ((ShapedOreRecipe) recipe).getRecipeHeight();
            } else {
                w = MathHelper.ceil(MathHelper.sqrt(ingredients.size()));
                h = MathHelper.ceil(ingredients.size() / (float) w);
            }

            this.crfGrid = new ItemStack[w][h][];
            for( int y = 0; y < h; y++ ) {
                for( int x = 0; x < w; x++ ) {
                    int ind = x + y * w;
                    if( ind < ingredients.size() ) {
                        this.crfGrid[x][y] = ingredients.get(ind).getMatchingStacks();
                    }
                }
            }
        }
    }

    @Override
    public void renderPageEntry(ILexiconEntry entry, ILexiconGuiHelper helper, int mouseX, int mouseY, int scrollY, float partTicks) {
        String s = TextFormatting.ITALIC.toString() + TextFormatting.BOLD + entry.getEntryName();
        helper.getFontRenderer().drawString(s, (MAX_ENTRY_WIDTH - helper.getFontRenderer().getStringWidth(s)) / 2, 0, 0xFF8A4500);

        if( recipe != null ) {
            int mX = this.crfGrid.length;
            int mY = this.crfGrid[0].length;
            int sumWidth = 36 + 18 * mX + 22;
            helper.drawItemGrid((MAX_ENTRY_WIDTH - sumWidth) / 2 + 18 * mX + 4 + 18, 12 + Math.max(mY * 9 - 18, 0), mouseX, mouseY, scrollY, recipe.getRecipeOutput(), 2.0F, false);
            helper.tryLoadTexture(Resources.GUI_LEXICON.resource);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                                                GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            helper.drawTextureRect((MAX_ENTRY_WIDTH - sumWidth) / 2 + 18 * mX + 2, 24 + Math.max(mY * 9 - 18, 0), 238, this.isShapeless ? 30 : 18, 18, 12);

            for( int x = 0; x < mX; x++ ) {
                for( int y = 0; y < mY; y++ ) {
                    ItemStack[] drawnStacks = this.crfGrid[x][y];
                    ItemStack drawnStack = drawnStacks != null && drawnStacks.length > 0
                                                   ? drawnStacks[(int) ((System.nanoTime() / 1_000_000_000) % drawnStacks.length)]
                                                   : ItemStack.EMPTY;
                    helper.drawItemGrid((MAX_ENTRY_WIDTH - sumWidth) / 2 + x * 18, 12 + y * 18 + Math.max(27 - mY * 18, 0), mouseX, mouseY, scrollY, drawnStack, 1.0F, true);
                }
            }

            this.drawHeight = Math.max(17 + mY * 18, 55);
        } else {
            helper.drawItemGrid((MAX_ENTRY_WIDTH - 36) / 2, 12, mouseX, mouseY, scrollY, entry.getEntryIcon(), 2.0F, false);
            this.drawHeight = 55;
        }

        s = entry.getEntryText().replace("\\n", "\n");
        helper.drawContentString(s, 2, this.drawHeight, MAX_ENTRY_WIDTH - 2, 0xFF000000, this.entryButtons);
        this.drawHeight += helper.getWordWrappedHeight(s, MAX_ENTRY_WIDTH - 2);

        if( helper.tryLoadTexture(entry.getPicture()) ) {
            int height = MAX_ENTRY_WIDTH / 2;
            helper.drawRect(0, this.drawHeight + 8, MAX_ENTRY_WIDTH, this.drawHeight + 8 + height, 0xFF000000);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            helper.drawTextureRect(2, this.drawHeight + 10, MAX_ENTRY_WIDTH - 4, height - 4, 0.0F, 0.0F, 1.0F, 1.0F);
            this.drawHeight += height + 12;
        }

        this.drawHeight += 2;
    }

    @Override
    public int getEntryHeight(ILexiconEntry entry, ILexiconGuiHelper helper) {
        return this.drawHeight;
    }

    @Override
    public boolean actionPerformed(GuiButton button, ILexiconGuiHelper helper) {
        return helper.linkActionPerformed(button);
    }
}
