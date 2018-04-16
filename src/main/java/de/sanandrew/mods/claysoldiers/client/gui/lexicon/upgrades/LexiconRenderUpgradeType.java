/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.gui.lexicon.upgrades;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconEntry;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconGroup;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconGuiHelper;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconPageRender;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.client.gui.lexicon.GuiButtonLink;
import de.sanandrew.mods.claysoldiers.client.gui.lexicon.LexiconRegistry;
import de.sanandrew.mods.claysoldiers.registry.upgrade.UpgradeRegistry;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.text.TextFormatting;
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
    private static List<ILexiconEntry> subEntryCache;

    @Override
    public String getId() {
        return CsmConstants.ID + ":upgradetype";
    }

    @Override
    public void initPage(ILexiconEntry entry, ILexiconGuiHelper helper, List<GuiButton> globalButtons, List<GuiButton> entryButtons) {
        this.entryButtons = entryButtons;
    }

    @Override
    public void renderPageEntry(ILexiconEntry entry, ILexiconGuiHelper helper, int mouseX, int mouseY, int scrollY, float partTicks) {
        String s = TextFormatting.ITALIC.toString() + TextFormatting.BOLD + entry.getEntryName();
        helper.getFontRenderer().drawString(s, (MAX_ENTRY_WIDTH - helper.getFontRenderer().getStringWidth(s)) / 2, 0, 0xFF8A4500);

        s = entry.getEntryText().replace("\\n", "\n");
        this.drawHeight = helper.getWordWrappedHeight(s, MAX_ENTRY_WIDTH - 2) + 58;
        helper.drawContentString(s, 2, 55, MAX_ENTRY_WIDTH - 2, 0xFF000000, this.entryButtons);
    }

    @Override
    public int getEntryHeight(ILexiconEntry entry, ILexiconGuiHelper helper) {
        return this.drawHeight;
    }

    @Override
    public List<ILexiconEntry> getSubEntries(ILexiconEntry entry) {
        if( subEntryCache == null ) {
            subEntryCache = new ArrayList<>();

            if( entry instanceof LexiconEntryUpgradeType ) {
                final EnumUpgradeType type = ((LexiconEntryUpgradeType) entry).type;
                LexiconRegistry.INSTANCE.getGroup(entry.getGroupId()).getEntries().forEach(subEntry -> {
                    if( subEntry instanceof LexiconEntryUpgrade ) {
                        LexiconEntryUpgrade subEntryUpg = (LexiconEntryUpgrade) subEntry;
                        if( type == subEntryUpg.upgrade.getType(LexiconGroupUpgrades.Hander.MAIN) || type == subEntryUpg.upgrade.getType(LexiconGroupUpgrades.Hander.OFF) ) {
                            subEntryCache.add(subEntry);
                        }
                    }
                });
            }
        }

        return subEntryCache;
    }

    @Override
    public boolean actionPerformed(GuiButton button, ILexiconGuiHelper helper) {
        if( !helper.linkActionPerformed(button) ) {

        }

        return false;
    }
}
