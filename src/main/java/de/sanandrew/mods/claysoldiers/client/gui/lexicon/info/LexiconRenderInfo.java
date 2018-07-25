/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.gui.lexicon.info;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.util.Lang;
import de.sanandrew.mods.sanlib.api.client.lexicon.ILexiconEntry;
import de.sanandrew.mods.sanlib.api.client.lexicon.ILexiconGuiHelper;
import de.sanandrew.mods.sanlib.api.client.lexicon.ILexiconPageRender;
import de.sanandrew.mods.sanlib.lib.util.LangUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@SideOnly(Side.CLIENT)
public class LexiconRenderInfo
        implements ILexiconPageRender
{
    public static final String RENDER_INFO_ID = CsmConstants.ID + ":info";

    private static final int ITEM_TXT_COLOR = 0xFF808080;
    private static final int TXT_COLOR = 0xFF000000;

    private int drawHeight;

    @Override
    public String getId() {
        return RENDER_INFO_ID;
    }

    @Override
    public void initPage(ILexiconEntry entry, ILexiconGuiHelper helper, List<GuiButton> globalButtons, List<GuiButton> entryButtons) {
        entryButtons.add(helper.getNewLinkButton(entryButtons.size(), 6, 42, "SanAndreasP", "https://minecraft.curseforge.com/members/SanAndreasP", helper.getFontRenderer(), true).get());
        entryButtons.add(helper.getNewLinkButton(entryButtons.size(), 6, 51, "SilverChiren", "https://minecraft.curseforge.com/members/SilverChiren", helper.getFontRenderer(), true).get());
        entryButtons.add(helper.getNewLinkButton(entryButtons.size(), 6, 60, "CliffracerX", "https://minecraft.curseforge.com/members/CliffracerX", helper.getFontRenderer(), true).get());

        entryButtons.add(helper.getNewLinkButton(entryButtons.size(), 6, 90, "Vazkii (some rendering code)", "https://minecraft.curseforge.com/members/Vazkii", helper.getFontRenderer(), true).get());

        entryButtons.add(helper.getNewLinkButton(entryButtons.size(), 6, 111, LangUtils.translate(Lang.LEXICON_INFO_ITEM.get("github")), "https://github.com/SanAndreasP/ClaySoldiersMod", helper.getFontRenderer(), true).get());
        entryButtons.add(helper.getNewLinkButton(entryButtons.size(), 6, 120, LangUtils.translate(Lang.LEXICON_INFO_ITEM.get("issues")), "https://github.com/SanAndreasP/ClaySoldiersMod/issues/", helper.getFontRenderer(), true).get());
        entryButtons.add(helper.getNewLinkButton(entryButtons.size(), 6, 129, LangUtils.translate(Lang.LEXICON_INFO_ITEM.get("curse")), "https://minecraft.curseforge.com/projects/clay-soldiers-mod", helper.getFontRenderer(), true).get());
    }

    @Override
    public void updateScreen(ILexiconGuiHelper helper) {

    }

    @Override
    public void renderPageEntry(ILexiconEntry entry, ILexiconGuiHelper helper, int mouseX, int mouseY, int scrollY, float partTicks) {
        helper.drawTitleCenter(0, entry);

        this.drawHeight = 12;
        helper.getFontRenderer().drawString(LangUtils.translate(Lang.LEXICON_INFO_ITEM.get("version")), 0, this.drawHeight, ITEM_TXT_COLOR);
        this.drawHeight += 9;
        helper.getFontRenderer().drawString(CsmConstants.VERSION, 6, this.drawHeight, TXT_COLOR);

        this.drawHeight += 12;
        helper.getFontRenderer().drawString(LangUtils.translate(Lang.LEXICON_INFO_ITEM.get("author")), 0, this.drawHeight, ITEM_TXT_COLOR);
        this.drawHeight += 27;

        this.drawHeight += 12;
        helper.getFontRenderer().drawString(LangUtils.translate(Lang.LEXICON_INFO_ITEM.get("credits")), 0, this.drawHeight, ITEM_TXT_COLOR);
        this.drawHeight += 9;
        helper.getFontRenderer().drawString("KodaichiZero (Creator)", 6, this.drawHeight, TXT_COLOR);
        this.drawHeight += 9;

        this.drawHeight += 12;
        helper.getFontRenderer().drawString(LangUtils.translate(Lang.LEXICON_INFO_ITEM.get("links")), 0, this.drawHeight, ITEM_TXT_COLOR);
        this.drawHeight += 27;
    }

    @Override
    public int getEntryHeight(ILexiconEntry entry, ILexiconGuiHelper helper) {
        return this.drawHeight;
    }
}
