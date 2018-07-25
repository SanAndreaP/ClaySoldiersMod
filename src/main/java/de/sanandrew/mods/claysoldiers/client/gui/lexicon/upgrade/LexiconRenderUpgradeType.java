/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.gui.lexicon.upgrade;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.DummyHander;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.client.util.ClientProxy;
import de.sanandrew.mods.sanlib.api.client.lexicon.ILexiconEntry;
import de.sanandrew.mods.sanlib.api.client.lexicon.ILexiconGuiHelper;
import de.sanandrew.mods.sanlib.api.client.lexicon.ILexiconPageRender;
import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class LexiconRenderUpgradeType
        implements ILexiconPageRender
{
    private int drawHeight;
    private List<GuiButton> entryButtons;
    private List<GuiButton> subEntryButtons;

    public static final String ID = CsmConstants.ID + ":upgradetype";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public void initPage(ILexiconEntry entry, ILexiconGuiHelper helper, List<GuiButton> globalButtons, List<GuiButton> entryButtons) {
        this.entryButtons = entryButtons;
        this.subEntryButtons = new ArrayList<>();

        if( entry instanceof LexiconEntryUpgradeType ) {
            final EnumUpgradeType type = ((LexiconEntryUpgradeType) entry).type;
            final int btnX = helper.getEntryX() + 2;
            ClientProxy.lexiconInstance.getGroup(entry.getGroupId()).getEntries().forEach(subEntry -> {
                if( subEntry instanceof LexiconEntryUpgrade ) {
                    LexiconEntryUpgrade subEntryUpg = (LexiconEntryUpgrade) subEntry;
                    if( type == subEntryUpg.upgrade.getType(DummyHander.MAIN) || type == subEntryUpg.upgrade.getType(DummyHander.OFF) ) {
                        GuiButton btn = helper.getNewEntryButton(this.entryButtons.size(), btnX, 0, subEntry, helper.getFontRenderer()).get();
                        this.subEntryButtons.add(btn);
                        this.entryButtons.add(btn);
                    }
                }
            });
        }
    }

    @Override
    public void renderPageEntry(ILexiconEntry entry, ILexiconGuiHelper helper, int mouseX, int mouseY, int scrollY, float partTicks) {
//        String s = TextFormatting.ITALIC.toString() + TextFormatting.BOLD + Lang.translate(Lang.LEXICON_ENTRY_NAME.get(entry.getGroupId(), entry.getId()));
//        helper.getFontRenderer().drawString(s, (MAX_ENTRY_WIDTH - helper.getFontRenderer().getStringWidth(s)) / 2, 0, TITLE_COLOR);
        helper.drawTitleCenter(0, entry);

//        s = Lang.translate(Lang.LEXICON_ENTRY_TEXT.get(entry.getGroupId(), entry.getId())).replace("\\n", "\n");
//        this.drawHeight = helper.getWordWrappedHeight(s, MAX_ENTRY_WIDTH - 4) + 15;
//        helper.drawContentString(s, 2, 12, MAX_ENTRY_WIDTH - 4, 0xFF000000, this.entryButtons);
        this.drawHeight += helper.drawContentString(2, 12, entry, this.entryButtons);

        for( GuiButton btn : this.subEntryButtons ) {
            btn.y = this.drawHeight;
            this.drawHeight += 14;
        }
        this.drawHeight += 2;
    }

    @Override
    public int getEntryHeight(ILexiconEntry entry, ILexiconGuiHelper helper) {
        return this.drawHeight;
    }
}
