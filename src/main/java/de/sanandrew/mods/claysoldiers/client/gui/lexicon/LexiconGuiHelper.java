/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.gui.lexicon;

import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconEntry;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconGroup;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconPageRender;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconGuiHelper;
import de.sanandrew.mods.claysoldiers.util.CsmConfiguration;
import de.sanandrew.mods.claysoldiers.util.Resources;
import de.sanandrew.mods.sanlib.lib.client.util.GuiUtils;
import de.sanandrew.mods.sanlib.lib.client.util.RenderUtils;
import de.sanandrew.mods.sanlib.lib.util.ItemStackUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LexiconGuiHelper
        implements ILexiconGuiHelper
{
    private static final Pattern PATTERN_LINKSTRING = Pattern.compile("\\{link:(.+?)\\|(.+?):(.+?)}");

    private static FontRenderer unicodeFr;

    public final GuiLexicon gui;
    private final Map<ResourceLocation, Boolean> checkedResources;

    LexiconGuiHelper(GuiLexicon gui) {
        this.gui = gui;
        this.checkedResources = new HashMap<>();
    }

    @Override
    public void doEntryScissoring(int x, int y, int width, int height) {
        int prevX = x;
        int yShifted = y - Math.round(GuiLexicon.scroll * this.gui.dHeight);

        int maxWidth = Math.min(width, width - (x + width - ILexiconPageRender.MAX_ENTRY_WIDTH));
        int maxHeight = Math.min(height, height - (y + height - ILexiconPageRender.MAX_ENTRY_HEIGHT) + Math.round(GuiLexicon.scroll * this.gui.dHeight));

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
        GlStateManager.enableBlend();
        this.drawTextureRect(0, 0, 238, 0, 18, 18);
        GlStateManager.popMatrix();

        x += (1.0F * scale);
        y += (1.0F * scale);

        boolean mouseOver = mouseY >= 0 && mouseY < ILexiconPageRender.MAX_ENTRY_HEIGHT && mouseX >= x && mouseX < x + 16 * scale && mouseY >= y - scrollY && mouseY < y + 16 * scale - scrollY;
        if( mouseOver && ItemStackUtils.isValid(stack) ) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, ILexiconPageRender.MAX_ENTRY_HEIGHT - 20 + scrollY, 32.0F);
            Gui.drawRect(0, 0, ILexiconPageRender.MAX_ENTRY_WIDTH, 20, 0xD0000000);

            List tooltip = GuiUtils.getTooltipWithoutShift(stack);
            this.getFontRenderer().drawString(tooltip.get(0).toString(), 22, 2, 0xFFFFFFFF, false);
            if( drawTooltip && tooltip.size() > 1 ) {
                this.getFontRenderer().drawString(tooltip.get(1).toString(), 22, 11, 0xFF808080, false);
            }

            RenderUtils.renderStackInGui(stack, 2, 2, 1.0F, this.getFontRenderer());

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

    @Override
    public void drawContentString(String str, int x, int y, int wrapWidth, int textColor, @Nonnull List<GuiButton> newButtons) {
        FontRenderer fontRenderer = this.getFontRenderer();
        Map<Integer, String> links = new HashMap<>();
        str = replaceLinkedText(str, links);

        int drawnCharacters = 0;
        List<String> lines = getSplitString(str, wrapWidth);
        for( String line : lines ) {
            final int currLength = str.indexOf(line, drawnCharacters);
            final int newLength = currLength + line.length();
            List<Map.Entry<Integer, String>> entries = links.entrySet().stream().filter(entry -> entry.getKey() >= currLength && entry.getKey() < newLength)
                                                            .sorted(Comparator.comparingInt(Map.Entry::getKey)).collect(Collectors.toCollection(ArrayList::new));
            if( entries.size() > 0 ) {
                int lastUsedId = 0;
                int lineX = x;
                for( Map.Entry<Integer, String> entry : entries ) {
                    String s = line.substring(lastUsedId, entry.getKey() - currLength);
                    int sl = fontRenderer.getStringWidth(s);
                    fontRenderer.drawString(s, lineX, y, textColor, false);
                    lineX += sl;

                    int entrySplitId = entry.getValue().indexOf('|');
                    String t = entry.getValue().substring(0, entrySplitId);
                    int tl = fontRenderer.getStringWidth(t);
                    GuiButton lnk = newButtons.stream().filter(button -> button.id == entry.getKey()).findFirst().orElse(null);
                    if( lnk == null ) {
                        lnk = new GuiButtonLink(entry.getKey(), lineX, y, t, entry.getValue().substring(entrySplitId + 1), this.getFontRenderer());
                        newButtons.add(lnk);
                    } else {
                        lnk.x = lineX;
                        lnk.y = y;
                    }
                    lineX += tl;

                    lastUsedId += s.length() + t.length();
                }
                if( lastUsedId < line.length() ) {
                    fontRenderer.drawString(line.substring(lastUsedId), lineX, y, textColor, false);
                }
            } else {
                fontRenderer.drawString(line, x, y, textColor, false);
            }
            y += fontRenderer.FONT_HEIGHT;
            drawnCharacters = newLength;
        }
    }

    @Override
    public int getWordWrappedHeight(String str, final int wrapWidth) {
        return this.getFontRenderer().FONT_HEIGHT * getSplitString(str, wrapWidth).size();
    }

    private List<String> getSplitString(String str, final int wrapWidth) {
        final FontRenderer fr = this.getFontRenderer();

        return Arrays.stream(replaceLinkedText(str, null).split("\n"))
                     .collect(ArrayList::new, (list, elem) -> list.addAll(fr.listFormattedStringToWidth(elem, wrapWidth)), ArrayList::addAll);
    }

    private String replaceLinkedText(String str, Map<Integer, String> links) {
        while(true) {
            Matcher matcher = PATTERN_LINKSTRING.matcher(str);
            if( matcher.find() ) {
                if( links != null ) {
                    String[] linkSplit = matcher.group(1).split(" ");
                    int currInd = matcher.start();
                    for( int i = 0; i < linkSplit.length; i++ ) {
                        StringBuilder txt = new StringBuilder(linkSplit[i]);
                        if( i < linkSplit.length - 1 ) {
                            txt.append(' ');
                        }
                        links.put(currInd, String.format("%s|%s:%s", txt, matcher.group(2), matcher.group(3)));
                        currInd += txt.length();
                    }
                }
                str = matcher.replaceFirst("$1");
            } else {
                break;
            }
        }

        return str;
    }

    @Override
    public FontRenderer getFontRenderer() {
        if( CsmConfiguration.lexiconForceUnicode ) {
            if( unicodeFr == null ) {
                unicodeFr = new FontRenderer(this.gui.mc.gameSettings, new ResourceLocation("textures/font/ascii.png"), this.gui.mc.renderEngine, true);
            }

            return unicodeFr;
        } else {
            return this.gui.mc.fontRenderer;
        }
    }

    @Override
    public void drawItem(@Nonnull ItemStack stack, int x, int y, double scale) {
        RenderUtils.renderStackInGui(stack, x, y, scale);
    }

    @Override
    public void drawTextureRect(int x, int y, int u, int v, int w, int h) {
        this.gui.drawTexturedModalRect(x, y, u, v, w, h);
    }

    @Override
    public void drawTextureRect(int x, int y, int w, int h, float uMin, float vMin, float uMax, float vMax) {
        float z = this.gui.getZLevel();
        Tessellator tess = Tessellator.getInstance();
        BufferBuilder buf = tess.getBuffer();

        buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buf.pos(x, y + h, z).tex(uMin, vMax).endVertex();
        buf.pos(x + w, y + h, z).tex(uMax, vMax).endVertex();
        buf.pos(x + w, y, z).tex(uMax, vMin).endVertex();
        buf.pos(x, y, z).tex(uMin, vMin).endVertex();

        tess.draw();
    }

    @Override
    public void drawRect(int x, int y, int w, int h, int color) {
        Gui.drawRect(x, y, w, h, color);
    }

    @Override
    public void changePage(ILexiconGroup group, ILexiconEntry entry) {
        this.gui.changePage(group, entry, 0.0F, true);
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public boolean tryLoadTexture(ResourceLocation location) {
        if( location == null ) {
            return false;
        }

        if( this.checkedResources.containsKey(location) ) {
            if( this.checkedResources.get(location) ) {
                this.gui.mc.renderEngine.bindTexture(location);
                return true;
            } else {
                return false;
            }
        }

        try {
            if( this.gui.mc.renderEngine.getTexture(location) == null ) {
                new SimpleTexture(location).loadTexture(this.gui.mc.getResourceManager());
            }
        } catch( IOException ex ) {
            this.checkedResources.put(location, false);
            return false;
        }

        this.gui.mc.renderEngine.bindTexture(location);
        this.checkedResources.put(location, true);
        return true;
    }

    @Override
    public boolean linkActionPerformed(GuiButton button) {
        if( button instanceof GuiButtonLink ) {
            GuiButtonLink btnLink = (GuiButtonLink) button;
            int groupCharId = btnLink.link.indexOf(':');
            String groupId = btnLink.link.substring(0, groupCharId);
            String entryId = btnLink.link.substring(groupCharId + 1);

            ILexiconGroup group = LexiconRegistry.INSTANCE.getGroup(groupId);
            if( group != null ) {
                ILexiconEntry entry = group.getEntry(entryId);
                if( entry != null ) {
                    this.changePage(group, entry);
                }
            }

            return true;
        }

        return false;
    }
}
