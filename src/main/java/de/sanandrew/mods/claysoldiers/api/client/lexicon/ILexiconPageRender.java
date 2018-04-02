/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.api.client.lexicon;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@SideOnly(Side.CLIENT)
public interface ILexiconPageRender
{
    int MAX_ENTRY_WIDTH = 168;
    int MAX_ENTRY_HEIGHT = 183;

    String getId();

    void initPage(ILexiconEntry entry, ILexiconRenderHelper helper, List<GuiButton> globalButtons, List<GuiButton> entryButtons);

    default void renderPageGlobal(ILexiconEntry entry, ILexiconRenderHelper helper, int mouseX, int mouseY, float partTicks) { }

    void renderPageEntry(ILexiconEntry entry, ILexiconRenderHelper helper, int mouseX, int mouseY, int scrollY, float partTicks);

    int getEntryHeight(ILexiconEntry entry, ILexiconRenderHelper helper);

    default boolean actionPerformed(GuiButton button, ILexiconRenderHelper helper) {
        return false;
    }
}
