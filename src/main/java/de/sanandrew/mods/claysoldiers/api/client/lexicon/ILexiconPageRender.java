/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.api.client.lexicon;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@SideOnly(Side.CLIENT)
public interface ILexiconPageRender
{
    String RENDER_CRAFTING_ID = CsmConstants.ID + ":craftingGrid";
    String RENDER_STANDARD_ID = CsmConstants.ID + ":standard";

    int GUI_SIZE_X = 200;
    int GUI_SIZE_Y = 213;
    int ENTRY_X = 12;
    int ENTRY_Y = 19;
    int MAX_ENTRY_WIDTH = 165;
    int MAX_ENTRY_HEIGHT = 165;
    int BTN_ENTRY_WIDTH = MAX_ENTRY_WIDTH - 12;

    String getId();

    void initPage(ILexiconEntry entry, ILexiconGuiHelper helper, List<GuiButton> globalButtons, List<GuiButton> entryButtons);

    default void renderPageGlobal(ILexiconEntry entry, ILexiconGuiHelper helper, int mouseX, int mouseY, float partTicks) { }

    void renderPageEntry(ILexiconEntry entry, ILexiconGuiHelper helper, int mouseX, int mouseY, int scrollY, float partTicks);

    int getEntryHeight(ILexiconEntry entry, ILexiconGuiHelper helper);

    default boolean actionPerformed(GuiButton button, ILexiconGuiHelper helper) {
        return helper.linkActionPerformed(button);
    }
}
