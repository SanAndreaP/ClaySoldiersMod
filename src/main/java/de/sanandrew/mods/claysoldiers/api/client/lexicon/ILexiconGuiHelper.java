/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.api.client.lexicon;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

public interface ILexiconGuiHelper
{
    void doEntryScissoring(int x, int y, int width, int height);

    void doEntryScissoring();

    GuiScreen getGui();

    void changePage(ILexiconGroup group, ILexiconEntry entry);

    int getEntryX();

    int getEntryY();

    void drawItemGrid(int x, int y, int mouseX, int mouseY, int scrollY, @Nonnull ItemStack stack, float scale, boolean drawTooltip);

    void drawContentString(String str, int x, int y, int wrapWidth, int textColor, @Nonnull List<GuiButton> newButtons);

    void drawItem(@Nonnull ItemStack stack, int x, int y, double scale);

    void drawTextureRect(int x, int y, int u, int v, int w, int h);
}
