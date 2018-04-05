/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.gui.lexicon;

import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconPageRender;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconRenderHelper;
import de.sanandrew.mods.claysoldiers.util.Resources;
import de.sanandrew.mods.sanlib.lib.client.util.GuiUtils;
import de.sanandrew.mods.sanlib.lib.client.util.RenderUtils;
import de.sanandrew.mods.sanlib.lib.util.ItemStackUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexiconRenderHelper
        implements ILexiconRenderHelper
{
    public GuiLexicon gui;

    LexiconRenderHelper(GuiLexicon gui) {
        this.gui = gui;
    }

    @Override
    public void doEntryScissoring(int x, int y, int width, int height) {
        int prevX = x;
        int yShifted = y - Math.round(this.gui.scroll * this.gui.dHeight);

        int maxWidth = Math.min(width, width - (x + width - ILexiconPageRender.MAX_ENTRY_WIDTH));
        int maxHeight = Math.min(height, height - (y + height - ILexiconPageRender.MAX_ENTRY_HEIGHT) + Math.round(this.gui.scroll * this.gui.dHeight));

        x = this.gui.entryX + Math.max(0, prevX);
        y = this.gui.entryY + Math.max(0, yShifted);

        width = Math.max(0, Math.min(maxWidth, width + prevX));
        height = Math.max(0, Math.min(maxHeight, height + yShifted));

        GuiUtils.glScissor(x, y, width, height);
    }

    @Override
    public void doEntryScissoring() {
        GuiUtils.glScissor(this.gui.entryX, this.gui.entryY, ILexiconPageRender.MAX_ENTRY_WIDTH, ILexiconPageRender.MAX_ENTRY_HEIGHT);
    }

    @Override
    public GuiScreen getGui() {
        return this.gui;
    }

    @Override
    public int getEntryX() {
        return this.gui.entryX;
    }

    @Override
    public int getEntryY() {
        return this.gui.entryY;
    }

    @Override
    public void drawItemGrid(int x, int y, int mouseX, int mouseY, int scrollY, @Nonnull ItemStack stack, float scale, boolean drawTooltip) {
        this.gui.mc.getTextureManager().bindTexture(Resources.GUI_LEXICON.resource);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, 0.0F);
        GlStateManager.scale(scale, scale, 1.0F);
        this.drawTextureRect(0, 0, 222, 0, 18, 18);
        GlStateManager.popMatrix();

        x += (1.0F * scale);
        y += (1.0F * scale);

        boolean mouseOver = mouseY >= 0 && mouseY < ILexiconPageRender.MAX_ENTRY_HEIGHT && mouseX >= x && mouseX < x + 16 * scale && mouseY >= y - scrollY && mouseY < y + 16 * scale - scrollY;
        if( mouseOver && ItemStackUtils.isValid(stack) ) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, ILexiconPageRender.MAX_ENTRY_HEIGHT - 20 + scrollY, 32.0F);
            Gui.drawRect(0, 0, ILexiconPageRender.MAX_ENTRY_WIDTH, 20, 0xD0000000);

            List tooltip = GuiUtils.getTooltipWithoutShift(stack);
            this.gui.mc.fontRenderer.drawString(tooltip.get(0).toString(), 22, 2, 0xFFFFFFFF, false);
            if( drawTooltip && tooltip.size() > 1 ) {
                this.gui.mc.fontRenderer.drawString(tooltip.get(1).toString(), 22, 11, 0xFF808080, false);
            }

            RenderUtils.renderStackInGui(stack, 2, 2, 1.0F, this.gui.mc.fontRenderer);

            GlStateManager.popMatrix();
        }

        if( ItemStackUtils.isValid(stack) ) {
            RenderUtils.renderStackInGui(stack, x, y, scale);
        }

        if( mouseOver ) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, 0.0F, 64.0F);
            Gui.drawRect(x, y, x + (int)(16 * scale), y + (int)(16 * scale), 0x80FFFFFF);
            GlStateManager.popMatrix();
        }
    }

    private static final Pattern PATTERN_LINKSTRING = Pattern.compile("\\{link:(.+?)\\|(.+?):(.+?)}");
    public void drawContentString(String str, int x, int y, int wrapWidth, int textColor) {
        Matcher matcher = PATTERN_LINKSTRING.matcher(str);

//        String shortStr = .replaceAll("$1$2$5");
//        Map<String, >
        List<String> lines = this.gui.mc.fontRenderer.listFormattedStringToWidth(str, wrapWidth);
    }

    @Override
    public void drawItem(@Nonnull ItemStack stack, int x, int y, double scale) {
        RenderUtils.renderStackInGui(stack, x, y, scale);
    }

    @Override
    public void drawTextureRect(int x, int y, int u, int v, int w, int h) {
        this.gui.drawTexturedModalRect(x, y, u, v, w, h);
    }
}
