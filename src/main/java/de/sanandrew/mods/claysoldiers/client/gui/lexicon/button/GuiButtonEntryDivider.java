/*
 * ****************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 * *****************************************************************************************************************
 */
package de.sanandrew.mods.claysoldiers.client.gui.lexicon.button;

import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconPageRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiButtonEntryDivider
        extends GuiButton
{
    public GuiButtonEntryDivider(int id, int x, int y) {
        super(id, x, y, ILexiconPageRender.BTN_ENTRY_WIDTH, 5, "");
    }

    @Override
    public void drawButton(Minecraft mc, int mx, int my, float partTicks) {
        this.enabled = false;
        if( this.visible ) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

            int color1 = 0xC0FFFFFF;
            int color2 = 0x80FFFFFF;

            this.drawGradientRect(this.x, this.y + 2, this.x + this.width, this.y + 3, color1, color2);

            GlStateManager.popMatrix();
        }
    }
}
