/*
 * ****************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 * *****************************************************************************************************************
 */
package de.sanandrew.mods.claysoldiers.client.gui.lexicon;

import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconEntry;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconPageRender;
import de.sanandrew.mods.claysoldiers.util.Lang;
import de.sanandrew.mods.sanlib.client.ClientTickHandler;
import de.sanandrew.mods.sanlib.lib.client.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

@SideOnly(Side.CLIENT)
public class GuiButtonEntry
        extends GuiButton
{
    private static final float TIME = 6.0F;
    public final ILexiconEntry entry;

    @Nonnull
    private final ItemStack icon;

    private float ticksHovered = 0.0F;
    private float lastTime;
    private final FontRenderer fontRenderer;

    public GuiButtonEntry(int id, int x, int y, ILexiconEntry entry, FontRenderer fontRenderer) {
        super(id, x, y, ILexiconPageRender.BTN_ENTRY_WIDTH, 14, Lang.translate(Lang.LEXICON_ENTRY_NAME.get(entry.getGroupId(), entry.getId())));
        this.entry = entry;
        this.icon = entry.getEntryIcon();
        this.fontRenderer = fontRenderer;
        this.enabled = false;
    }

    @Override
    public void drawButton(Minecraft mc, int mx, int my, float partTicks) {
        float time = ClientTickHandler.ticksInGame + partTicks;
        float timeDelta = time - this.lastTime;
        this.lastTime = time;

        if( this.visible ) {
            boolean inside = this.enabled && mx >= this.x && my >= this.y && mx < this.x + this.width && my < this.y + this.height;
            if( inside ) {
                this.ticksHovered = Math.min(TIME, this.ticksHovered + timeDelta);
            } else {
                this.ticksHovered = Math.max(0.0F, this.ticksHovered - timeDelta);
            }

            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

            float alphaMulti = this.ticksHovered / TIME;
            int color1 = 0x00FFFFFF | ((Math.max(0x00, Math.min(0xC0, StrictMath.round(0xC0 * alphaMulti))) << 24) & 0xFF000000);
            int color2 = 0x00FFFFFF | ((Math.max(0x00, Math.min(0x80, StrictMath.round(0x80 * alphaMulti))) << 24) & 0xFF000000);

            this.drawGradientRect(this.x, this.y, this.x + this.width, this.y + 1, color1, color2);
            this.drawGradientRect(this.x, this.y + this.height - 1, this.x + this.width, this.y + this.height, color1, color2);
            this.drawGradientRect(this.x, this.y + 1, this.x + 1, this.y + this.height - 1, color1, color1);
            this.drawGradientRect(this.x + this.width - 1, this.y + 1, this.x + this.width, this.y + this.height - 1, color2, color2);

            RenderUtils.renderStackInGui(this.icon, this.x + 2, this.y + 3, 0.5D);

            this.fontRenderer.drawString(this.displayString, this.x + 12, this.y + 3, 0xFF000000, false);

            GlStateManager.popMatrix();
        }
    }
}
