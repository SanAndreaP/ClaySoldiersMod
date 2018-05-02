/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.gui.lexicon.soldier;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconEntry;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconGuiHelper;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconPageRender;
import de.sanandrew.mods.claysoldiers.crafting.CraftingRecipes;
import de.sanandrew.mods.claysoldiers.registry.team.TeamRegistry;
import de.sanandrew.mods.claysoldiers.registry.team.Teams;
import de.sanandrew.mods.sanlib.lib.util.ItemStackUtils;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.List;
import java.util.stream.StreamSupport;

@SideOnly(Side.CLIENT)
public class LexiconRenderBaseSoldier
        implements ILexiconPageRender
{
    public static final String ID = CsmConstants.ID + ":baseSoldier";

    private int drawHeight;
    private List<GuiButton> entryButtons;
    private static IRecipe soldierRecipe;

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public void initPage(ILexiconEntry entry, ILexiconGuiHelper helper, List<GuiButton> globalButtons, List<GuiButton> entryButtons) {
        this.entryButtons = entryButtons;
        if( soldierRecipe == null ) {
            final ItemStack greySoldier = TeamRegistry.INSTANCE.getNewTeamStack(1, Teams.SOLDIER_CLAY);
            soldierRecipe = StreamSupport.stream(CraftingManager.REGISTRY.spliterator(), false)
                                         .filter(r -> !r.isDynamic() && ItemStackUtils.areEqual(r.getRecipeOutput(), greySoldier) && r.canFit(3, 3))
                                         .findFirst().orElse(null);
        }
    }

    @Override
    public void renderPageEntry(ILexiconEntry entry, ILexiconGuiHelper helper, int mouseX, int mouseY, int scrollY, float partTicks) {
        String s = TextFormatting.ITALIC.toString() + TextFormatting.BOLD + entry.getEntryName();
        helper.getFontRenderer().drawString(s, (MAX_ENTRY_WIDTH - helper.getFontRenderer().getStringWidth(s)) / 2, 0, 0xFF8A4500);

        s = entry.getEntryText().replace("\\n", "\n");
        this.drawHeight = helper.getWordWrappedHeight(s, MAX_ENTRY_WIDTH - 2) + 58;
        helper.drawContentString(s, 2, 55, MAX_ENTRY_WIDTH - 2, 0xFF000000, this.entryButtons);

        if( helper.tryLoadTexture(entry.getPicture()) ) {
            int height = MAX_ENTRY_WIDTH / 2;
            helper.drawRect(0, this.drawHeight + 8, MAX_ENTRY_WIDTH, this.drawHeight + 8 + height, 0xFF000000);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            helper.drawTextureRect(2, this.drawHeight + 10, MAX_ENTRY_WIDTH - 4, height - 4, 0.0F, 0.0F, 1.0F, 1.0F);
            this.drawHeight += height + 12;
        }

        helper.drawItemGrid((MAX_ENTRY_WIDTH - 36) / 2, 12, mouseX, mouseY, scrollY, entry.getEntryIcon(), 2.0F, false);

        if( soldierRecipe != null ) {
            int w, h;
            if( soldierRecipe instanceof ShapedRecipes ) {
                w = ((ShapedRecipes) soldierRecipe).recipeWidth;
                h = ((ShapedRecipes) soldierRecipe).recipeHeight;
            } else if( soldierRecipe instanceof ShapedOreRecipe ) {
                w = ((ShapedOreRecipe) soldierRecipe).getRecipeWidth();
                h = ((ShapedOreRecipe) soldierRecipe).getRecipeHeight();
            }
            soldierRecipe.getIngredients()
            helper.drawItemGrid((MAX_ENTRY_WIDTH - 36) / 2, 12, mouseX, mouseY, scrollY, entry.getEntryIcon(), 2.0F, false);
        }
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
