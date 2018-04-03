/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.api.client.lexicon;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public interface ILexiconRenderHelper
{
    int GUI_SIZE_X = 200;
    int GUI_SIZE_Y = 213;

    void doEntryScissoring(int x, int y, int width, int height);

    void doEntryScissoring();

    GuiScreen getGui();

    int getEntryX();

    int getEntryY();

    void drawItemGrid(int x, int y, int mouseX, int mouseY, int scrollY, @Nonnull ItemStack stack, float scale, boolean drawTooltip);

    void drawItem(@Nonnull ItemStack stack, int x, int y, double scale);

    void drawTextureRect(int x, int y, int u, int v, int w, int h);
}
