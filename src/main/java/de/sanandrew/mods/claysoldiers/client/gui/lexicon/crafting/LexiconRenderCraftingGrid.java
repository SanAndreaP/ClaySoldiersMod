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
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconEntryCraftingGrid;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconGuiHelper;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconPageRender;
import de.sanandrew.mods.claysoldiers.client.gui.lexicon.LexiconGuiHelper;
import de.sanandrew.mods.claysoldiers.util.Lang;
import de.sanandrew.mods.sanlib.lib.util.ItemStackUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

@SideOnly(Side.CLIENT)
public class LexiconRenderCraftingGrid
        implements ILexiconPageRender
{
    public static final String ID = CsmConstants.ID + ":craftingGrid";

    private int drawHeight;
    private List<GuiButton> entryButtons;
    private List<CraftingGrid> crfGrids;

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

        NonNullList<IRecipe> recipes = ((ILexiconEntryCraftingGrid) entry).getRecipes();

        if( recipes.isEmpty() ) {
            for( ItemStack result : ((ILexiconEntryCraftingGrid) entry).getRecipeResults() ) {
                StreamSupport.stream(CraftingManager.REGISTRY.spliterator(), false)
                             .filter(r -> !r.isDynamic() && ItemStackUtils.areEqualNbtFit(result, r.getRecipeOutput(), true, true, false) && r.canFit(3, 3))
                             .findFirst().ifPresent(recipes::add);
            }
        }

        if( !recipes.isEmpty() ) {
            this.crfGrids = new ArrayList<>();
            LexiconGuiHelper.initCraftings(recipes, this.crfGrids);
        }
    }

    @Override
    public void renderPageEntry(ILexiconEntry entry, ILexiconGuiHelper helper, int mouseX, int mouseY, int scrollY, float partTicks) {
        String s = TextFormatting.ITALIC.toString() + TextFormatting.BOLD + Lang.translate(Lang.LEXICON_ENTRY_NAME.get(entry.getGroupId(), entry.getId()));
        helper.getFontRenderer().drawString(s, (MAX_ENTRY_WIDTH - helper.getFontRenderer().getStringWidth(s)) / 2, 0, 0xFF8A4500);

        if( this.crfGrids != null && this.crfGrids.size() > 0 ) {
            CraftingGrid grid = this.crfGrids.get((int) ((System.nanoTime() / 1_000_000_000L) % this.crfGrids.size()));
            Vec3i gridSize = helper.getCraftingGridSize(grid);

            helper.drawCraftingGrid(grid, grid.isShapeless(), (MAX_ENTRY_WIDTH - gridSize.getX()) / 2, 12, mouseX, mouseY, scrollY);
            this.drawHeight = gridSize.getY() + 16;
        } else {
            helper.drawItemGrid((MAX_ENTRY_WIDTH - 36) / 2, 12, mouseX, mouseY, scrollY, entry.getEntryIcon(), 2.0F, false);
            this.drawHeight = 55;
        }

        s = Lang.translate(Lang.LEXICON_ENTRY_TEXT.get(entry.getGroupId(), entry.getId())).replace("\\n", "\n");
        helper.drawContentString(s, 2, this.drawHeight, MAX_ENTRY_WIDTH - 2, 0xFF000000, this.entryButtons);
        this.drawHeight += helper.getWordWrappedHeight(s, MAX_ENTRY_WIDTH - 2);

        int height = MAX_ENTRY_WIDTH / 2;
        if( helper.tryDrawPicture(entry.getPicture(), 0, this.drawHeight + 8, MAX_ENTRY_WIDTH, height) ) {
            this.drawHeight += height + 8;
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
