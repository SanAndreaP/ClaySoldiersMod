/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.gui.lexicon.upgrade;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconEntry;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconPageRender;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconGuiHelper;
import de.sanandrew.mods.claysoldiers.util.Lang;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@SideOnly(Side.CLIENT)
public class LexiconRenderUpgrades
        implements ILexiconPageRender
{
    public static final String ID = CsmConstants.ID + ":upgrades";

    private int drawHeight;
    private List<GuiButton> entryButtons;

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public void initPage(ILexiconEntry entry, ILexiconGuiHelper helper, List<GuiButton> globalButtons, List<GuiButton> entryButtons) {
        this.entryButtons = entryButtons;
    }

    @Override
    public void renderPageEntry(ILexiconEntry entry, ILexiconGuiHelper helper, int mouseX, int mouseY, int scrollY, float partTicks) {
        String s = TextFormatting.ITALIC.toString() + TextFormatting.BOLD + Lang.translate(Lang.LEXICON_ENTRY_NAME.get(entry.getGroupId(), entry.getId()));
        helper.getFontRenderer().drawString(s, (MAX_ENTRY_WIDTH - helper.getFontRenderer().getStringWidth(s)) / 2, 0, 0xFF8A4500);

        s = Lang.translate(Lang.LEXICON_ENTRY_TEXT.get(entry.getGroupId(), entry.getId())).replace("\\n", "\n");
        this.drawHeight = helper.getWordWrappedHeight(s, MAX_ENTRY_WIDTH - 2) + 58;
        helper.drawContentString(s, 2, 55, MAX_ENTRY_WIDTH - 2, 0xFF000000, this.entryButtons);

        int height = MAX_ENTRY_WIDTH / 2;
        if( helper.tryDrawPicture(entry.getPicture(), 0, this.drawHeight + 8, MAX_ENTRY_WIDTH, height) ) {
            this.drawHeight += height + 8;
        }

        helper.drawItemGrid((MAX_ENTRY_WIDTH - 36) / 2, 12, mouseX, mouseY, scrollY, entry.getEntryIcon(), 2.0F, false);
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
