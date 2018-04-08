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
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconPageRender;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconGuiHelper;
import de.sanandrew.mods.claysoldiers.client.gui.lexicon.GuiButtonLink;
import de.sanandrew.mods.claysoldiers.client.gui.lexicon.LexiconRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@SideOnly(Side.CLIENT)
public class LexiconRenderUpgrades
        implements ILexiconPageRender
{
    private int drawHeight;
    private List<GuiButton> entryButtons;

    @Override
    public String getId() {
        return CsmConstants.ID + ":upgrades";
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
        this.drawHeight = helper.getWordWrappedHeight(s, MAX_ENTRY_WIDTH - 2) + 55;
        helper.drawContentString(s, 2, 55, MAX_ENTRY_WIDTH - 2, 0xFF000000, this.entryButtons);

        if( helper.tryLoadTexture(entry.getPicture()) ) {
            int height = MAX_ENTRY_WIDTH / 2;
            helper.drawRect(0, this.drawHeight + 8, MAX_ENTRY_WIDTH, this.drawHeight + 8 + height, 0xFF000000);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            helper.drawTextureRect(2, this.drawHeight + 10, MAX_ENTRY_WIDTH - 4, height - 4, 0.0F, 0.0F, 1.0F, 1.0F);
            this.drawHeight += height + 12;
        }

        helper.drawItemGrid((MAX_ENTRY_WIDTH - 36) / 2, 12, mouseX, mouseY, scrollY, entry.getEntryIcon(), 2.0F, false);
    }

    @Override
    public int getEntryHeight(ILexiconEntry entry, ILexiconGuiHelper helper) {
        return this.drawHeight;
    }

    @Override
    public boolean actionPerformed(GuiButton button, ILexiconGuiHelper helper) {
        if( button instanceof GuiButtonLink ) {
            GuiButtonLink btnLink = (GuiButtonLink) button;
            int groupCharId = btnLink.link.indexOf(':');
            String groupId = btnLink.link.substring(0, groupCharId);
            String entryId = btnLink.link.substring(groupCharId + 1);

            ILexiconGroup group = LexiconRegistry.INSTANCE.getGroup(groupId);
            if( group != null ) {
                ILexiconEntry entry = group.getEntry(entryId);
                if( entry != null ) {
                    helper.changePage(group, entry);
                }
            }

            return true;
        }

        return false;
    }
}
