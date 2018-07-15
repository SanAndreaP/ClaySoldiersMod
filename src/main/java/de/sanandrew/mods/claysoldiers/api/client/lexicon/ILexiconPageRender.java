/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.api.client.lexicon;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;
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

    int TITLE_COLOR = 0xFF8A4500;

    String getId();

    void initPage(ILexiconEntry entry, ILexiconGuiHelper helper, List<GuiButton> globalButtons, List<GuiButton> entryButtons);

    default void updateScreen(ILexiconGuiHelper helper) { }

    default void renderPageOverlay(ILexiconEntry entry, ILexiconGuiHelper helper, int mouseX, int mouseY, float partTicks) { }

    void renderPageEntry(ILexiconEntry entry, ILexiconGuiHelper helper, int mouseX, int mouseY, int scrollY, float partTicks);

    int getEntryHeight(ILexiconEntry entry, ILexiconGuiHelper helper);

    default void savePageState(NBTTagCompound nbt) { }

    default void loadPageState(NBTTagCompound nbt) { }

    default int shiftEntryPosY() {
        return 0;
    }

    default boolean actionPerformed(GuiButton button, ILexiconGuiHelper helper) {
        return helper.linkActionPerformed(button);
    }

    default void mouseClicked(int mouseX, int mouseY, int mouseBtn, ILexiconGuiHelper helper) throws IOException { }

    default void keyTyped(char typedChar, int keyCode, ILexiconGuiHelper helper) throws IOException { }
}
