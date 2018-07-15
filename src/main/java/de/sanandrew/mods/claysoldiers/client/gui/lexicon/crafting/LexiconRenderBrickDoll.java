/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.gui.lexicon.crafting;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.CraftingGrid;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconEntry;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconEntryFurnace;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconGuiHelper;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconPageRender;
import de.sanandrew.mods.claysoldiers.client.gui.lexicon.LexiconGuiHelper;
import de.sanandrew.mods.claysoldiers.client.gui.lexicon.soldier.LexiconEntryBrickDoll;
import de.sanandrew.mods.claysoldiers.crafting.FuelHelper;
import de.sanandrew.mods.claysoldiers.util.Lang;
import de.sanandrew.mods.claysoldiers.util.Resources;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class LexiconRenderBrickDoll
        implements ILexiconPageRender
{
    public static final String ID = CsmConstants.ID + ":brickDoll";

    private int drawHeight;
    private List<GuiButton> entryButtons;
    private List<Map.Entry<Ingredient, ItemStack>> furnaceRecipes;
    private List<CraftingGrid> crfGridsNoraml;
    private List<CraftingGrid> crfGridsTeamed;

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public void initPage(ILexiconEntry entry, ILexiconGuiHelper helper, List<GuiButton> globalButtons, List<GuiButton> entryButtons) {
        if( !(entry instanceof ILexiconEntryFurnace) ) {
            throw new RuntimeException("Cannot use this renderer if entry does not implement ILexiconEntryFurnace!");
        }

        this.entryButtons = entryButtons;
        Map<Ingredient, ItemStack> recipes = ((ILexiconEntryFurnace) entry).getRecipes();

        if( recipes != null && !recipes.isEmpty() ) {
            this.furnaceRecipes = new ArrayList<>(recipes.entrySet());
        }

        if( entry instanceof LexiconEntryBrickDoll ) {
            IRecipe normalRecipe = ((LexiconEntryBrickDoll) entry).getNormalRecipe();
            if( normalRecipe != null ) {
                this.crfGridsNoraml = new ArrayList<>();
                LexiconGuiHelper.initCraftings(NonNullList.withSize(1, normalRecipe), this.crfGridsNoraml);
            }

            NonNullList<IRecipe> teamedRecipes = ((LexiconEntryBrickDoll) entry).getTeamedRecipes();
            if( !teamedRecipes.isEmpty() ) {
                this.crfGridsTeamed = new ArrayList<>();
                LexiconGuiHelper.initCraftings(teamedRecipes, this.crfGridsTeamed);
            }
        }
    }

    @Override
    public void renderPageEntry(ILexiconEntry entry, ILexiconGuiHelper helper, int mouseX, int mouseY, int scrollY, float partTicks) {
        final long timer = System.nanoTime() / 1_000_000_000L;
        String s = TextFormatting.ITALIC.toString() + TextFormatting.BOLD + Lang.translate(Lang.LEXICON_ENTRY_NAME.get(entry.getGroupId(), entry.getId()));
        helper.getFontRenderer().drawString(s, (MAX_ENTRY_WIDTH - helper.getFontRenderer().getStringWidth(s)) / 2, 0, TITLE_COLOR);

        if( this.furnaceRecipes != null ) {
            Map.Entry<Ingredient, ItemStack> recipe = this.furnaceRecipes.get((int) (timer % this.furnaceRecipes.size()));
            ItemStack[] inputs = recipe.getKey().getMatchingStacks();
            final int width = 76;
            final int height = 54;
            helper.drawItemGrid((MAX_ENTRY_WIDTH - width) / 2 + width - 36, 12 + 9, mouseX, mouseY, scrollY, recipe.getValue(), 2.0F, false);
            helper.drawItemGrid((MAX_ENTRY_WIDTH - width) / 2, 12, mouseX, mouseY, scrollY, inputs[(int) (timer % inputs.length)], 1.0F, false);
            helper.drawItemGrid((MAX_ENTRY_WIDTH - width) / 2, 12 + height - 18, mouseX, mouseY, scrollY, FuelHelper.FUELS.get((int) (timer % FuelHelper.FUELS.size())), 1.0F, false);
            helper.tryLoadTexture(Resources.GUI_LEXICON.resource);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                                                GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            helper.drawTextureRect((MAX_ENTRY_WIDTH - width) / 2 + 20, 12 + (height - 12) / 2, 238, 18, 18, 12);
            helper.drawTextureRect((MAX_ENTRY_WIDTH - width) / 2 + 2, 12 + (height - 14) / 2, 224, 28, 14, 14);

            this.drawHeight = 12 + height + 4;
        } else {
            helper.drawItemGrid((MAX_ENTRY_WIDTH - 36) / 2, 12, mouseX, mouseY, scrollY, entry.getEntryIcon(), 2.0F, false);
            this.drawHeight = 55;
        }

        s = Lang.translate(Lang.LEXICON_ENTRY_TEXT.get(entry.getGroupId(), entry.getId())).replace("\\n", "\n");
        helper.drawContentString(s, 2, this.drawHeight, MAX_ENTRY_WIDTH - 2, 0xFF000000, this.entryButtons);
        this.drawHeight += helper.getWordWrappedHeight(s, MAX_ENTRY_WIDTH - 2);

        if( this.crfGridsNoraml != null && this.crfGridsNoraml.size() > 0 ) {
            CraftingGrid grid = this.crfGridsNoraml.get((int) (timer % this.crfGridsNoraml.size()));
            Vec3i gridSize = helper.getCraftingGridSize(grid);

            helper.drawCraftingGrid(grid, grid.isShapeless(), (MAX_ENTRY_WIDTH - gridSize.getX()) / 2, this.drawHeight, mouseX, mouseY, scrollY);
            this.drawHeight += gridSize.getY() + 2;
        }

        if( this.crfGridsTeamed != null && this.crfGridsTeamed.size() > 0 ) {
            s = Lang.translate(Lang.LEXICON_ENTRY_GRIDTEXT.get(entry.getGroupId(), entry.getId())).replace("\\n", "\n");
            helper.drawContentString(s, 2, this.drawHeight, MAX_ENTRY_WIDTH - 2, 0xFF000000, this.entryButtons);
            this.drawHeight += helper.getWordWrappedHeight(s, MAX_ENTRY_WIDTH - 2);

            CraftingGrid grid = this.crfGridsTeamed.get((int) (timer % this.crfGridsTeamed.size()));
            Vec3i gridSize = helper.getCraftingGridSize(grid);

            helper.drawCraftingGrid(grid, grid.isShapeless(), (MAX_ENTRY_WIDTH - gridSize.getX()) / 2, this.drawHeight, mouseX, mouseY, scrollY);
            this.drawHeight += gridSize.getY() + 2;
        }

        this.drawHeight += 2;
    }

    @Override
    public int getEntryHeight(ILexiconEntry entry, ILexiconGuiHelper helper) {
        return this.drawHeight;
    }
}
