/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.gui.lexicon;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconEntry;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconPageRender;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconRenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@SideOnly(Side.CLIENT)
public class LexiconRenderUpgrades
        implements ILexiconPageRender
{
    private int drawHeight;

    @Override
    public String getId() {
        return CsmConstants.ID + ":upgrades";
    }

    @Override
    public void initPage(ILexiconEntry entry, ILexiconRenderHelper helper, List<GuiButton> globalButtons, List<GuiButton> entryButtons) {

    }

    @Override
    public void renderPageEntry(ILexiconEntry entry, ILexiconRenderHelper helper, int mouseX, int mouseY, int scrollY, float partTicks) {
        Minecraft mc = helper.getGui().mc;
        mc.fontRenderer.drawString(TextFormatting.ITALIC + entry.getEntryName(), 2, 2, 0xFF0080BB);

        String text = entry.getEntryText().replace("\\n", "\n");
        this.drawHeight = mc.fontRenderer.getWordWrappedHeight(text, MAX_ENTRY_WIDTH - 2) + this.drawHeight + 3 + 2;
        mc.fontRenderer.drawSplitString(text, 3, 14, MAX_ENTRY_WIDTH - 2, 0xFFFFFFFF);
        mc.fontRenderer.drawSplitString(text, 2, 13, MAX_ENTRY_WIDTH - 2, 0xFF000000);

        helper.drawItemGrid(3, 17, mouseX, mouseY, scrollY, entry.getEntryIcon(), 2.0F, false);
    }

    @Override
    public int getEntryHeight(ILexiconEntry entry, ILexiconRenderHelper helper) {
        return 0;
    }
}
