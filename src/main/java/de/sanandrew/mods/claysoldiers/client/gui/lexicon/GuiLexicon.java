/*
 * ****************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 * *****************************************************************************************************************
 */
package de.sanandrew.mods.claysoldiers.client.gui.lexicon;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconEntry;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconGroup;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconPageRender;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconRenderHelper;
import de.sanandrew.mods.claysoldiers.util.Resources;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Level;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiLexicon
        extends GuiScreen
        implements GuiYesNoCallback
{

    private int guiLeft;
    private int guiTop;

    public ILexiconGroup group;
    public ILexiconEntry entry;
    public ILexiconPageRender render;

    public float scroll = 0.0F;
    int dHeight;
    boolean isScrolling;
    public int entryX;
    public int entryY;
    private URI clickedURI;
    private boolean updateGUI;

    public final List<GuiButton> entryButtons;
    public final ILexiconRenderHelper renderHelper;

    public GuiLexicon() {
        this.entryButtons = new ArrayList<>();
        this.renderHelper = new LexiconRenderHelper(this);
    }

    @SuppressWarnings("unchecked")
    public void initGui() {
        super.initGui();

        this.scroll = 0.0F;

        this.guiLeft = (this.width - ILexiconRenderHelper.GUI_SIZE_X) / 2;
        this.guiTop = (this.height - ILexiconRenderHelper.GUI_SIZE_Y) / 2;

        this.entryX = this.guiLeft + 9;
        this.entryY = this.guiTop + 19;

        this.buttonList.clear();
        this.entryButtons.clear();

        this.buttonList.add(new GuiButtonNav(this.buttonList.size(), this.guiLeft + 53, this.guiTop + 206, 0));
        this.buttonList.add(new GuiButtonNav(this.buttonList.size(), this.guiLeft + 83, this.guiTop + 206, 1));
        this.buttonList.add(new GuiButtonNav(this.buttonList.size(), this.guiLeft + 114, this.guiTop + 206, 2));

        if( this.group == null ) {
            int posX = 0;
            int posY = 0;
            for( ILexiconGroup group : LexiconRegistry.INSTANCE.getGroups() ) {
                this.buttonList.add(new GuiButtonGroup(this.buttonList.size(), this.guiLeft + 12 + posX, this.guiTop + 24 + posY, group, this::groupBtnMouseOver));
                if( (posX += 32) > this.guiLeft + 12 + ILexiconPageRender.MAX_ENTRY_WIDTH ) {
                    posX = 0;
                    posY += 32;
                }
            }
        } else if( this.entry == null ) {
            int posY = 0;
            for( ILexiconEntry entry : this.group.getEntries() ) {
                this.entryButtons.add(new GuiButtonEntry(this.entryButtons.size(), 5, 19 + posY, entry));
                posY += 14;
            }
        } else {
            this.render = LexiconRegistry.INSTANCE.getPageRender(this.entry.getPageRenderId());
            this.render.initPage(this.entry, this.renderHelper, this.buttonList, this.entryButtons);
        }
    }

    @Override
    public void updateScreen() {
        if( this.updateGUI ) {
            this.updateGUI = false;
            this.initGui();
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partTicks) {
        boolean mouseDown = Mouse.isButtonDown(0);

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.drawDefaultBackground();

        this.mc.renderEngine.bindTexture(Resources.GUI_LEXICON.resource);

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, ILexiconRenderHelper.GUI_SIZE_X, ILexiconRenderHelper.GUI_SIZE_Y);

        GlStateManager.pushMatrix();
        GlStateManager.translate(this.entryX + ILexiconPageRender.MAX_ENTRY_WIDTH, this.entryY, 0.0F);
        drawRect(0, 0, 6, ILexiconPageRender.MAX_ENTRY_HEIGHT, 0x30000000);
        if( this.dHeight > 0 ) {
            drawRect(0, Math.round((ILexiconPageRender.MAX_ENTRY_HEIGHT - 16) * this.scroll), 6, Math.round((ILexiconPageRender.MAX_ENTRY_HEIGHT - 16) * this.scroll + 16), 0x800000FF);
        }
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        this.renderHelper.doEntryScissoring();
        GlStateManager.translate(this.entryX, this.entryY, 0.0F);

        if( this.entry != null ) {
            this.render.renderPageGlobal(this.entry, this.renderHelper, mouseX - this.entryX, mouseY - entryY, partTicks);
        }

        GlStateManager.translate(0.0F, Math.round(-this.scroll * this.dHeight), 0.0F);

        if( this.entry != null ) {
            this.dHeight = this.render.getEntryHeight(this.entry, this.renderHelper) - ILexiconPageRender.MAX_ENTRY_HEIGHT;
            this.render.renderPageEntry(this.entry, this.renderHelper, mouseX - this.entryX, mouseY - entryY, Math.round(this.scroll * this.dHeight), partTicks);
        } else if( this.group != null ) {
            this.dHeight = this.entryButtons.size() * 14 + 20 - ILexiconPageRender.MAX_ENTRY_HEIGHT;
            this.fontRenderer.drawString(TextFormatting.ITALIC + this.group.getGroupName(), 2, 2, 0xFF33AA33, false);
            Gui.drawRect(2, 12, ILexiconPageRender.MAX_ENTRY_WIDTH - 2, 13, 0xFF33AA33);
        }

        for( GuiButton btn : this.entryButtons ) {
            btn.enabled = btn.y - Math.round(this.scroll * this.dHeight) > 0 && btn.y - Math.round(this.scroll * this.dHeight) + btn.height < ILexiconPageRender.MAX_ENTRY_HEIGHT;
            btn.drawButton(this.mc, mouseX - this.entryX, mouseY - this.entryY + Math.round(this.scroll * this.dHeight), partTicks);
        }

        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GlStateManager.popMatrix();

        if( !mouseDown && this.isScrolling ) {
            this.isScrolling = false;
        } else if( mouseDown && !this.isScrolling ) {
            if( mouseY >= this.entryY && mouseY < this.entryY + ILexiconPageRender.MAX_ENTRY_HEIGHT ) {
                if( mouseX >= this.entryX + ILexiconPageRender.MAX_ENTRY_WIDTH && mouseX < this.entryX + ILexiconPageRender.MAX_ENTRY_WIDTH + 6 ) {
                    this.isScrolling = this.dHeight > 0;
                }
            }
        }

        if( this.isScrolling ) {
            int mouseDelta = Math.min(ILexiconPageRender.MAX_ENTRY_HEIGHT - 16, Math.max(0, mouseY - (this.entryY + 8)));
            this.scroll = mouseDelta / (ILexiconPageRender.MAX_ENTRY_HEIGHT - 16.0F);
        }

        super.drawScreen(mouseX, mouseY, partTicks);
    }

    private void groupBtnMouseOver(ILexiconGroup group, int mouseX, int mouseY) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(mouseX + 12, mouseY - 12, 32.0F);

        String title = group.getGroupName();
        int bkgColor = 0xF0101010;
        int lightBg = 0x50A0A0A0;
        int darkBg = (lightBg & 0xFEFEFE) >> 1 | lightBg & 0xFF000000;
        int textWidth = this.fontRenderer.getStringWidth(title);
        int tHeight = 8;

        this.drawGradientRect(-3,            -4,          textWidth + 3, -3,          bkgColor, bkgColor);
        this.drawGradientRect(-3,            tHeight + 3, textWidth + 3, tHeight + 4, bkgColor, bkgColor);
        this.drawGradientRect(-3,            -3,          textWidth + 3, tHeight + 3, bkgColor, bkgColor);
        this.drawGradientRect(-4,            -3,          -3,            tHeight + 3, bkgColor, bkgColor);
        this.drawGradientRect(textWidth + 3, -3,          textWidth + 4, tHeight + 3, bkgColor, bkgColor);

        this.drawGradientRect(-3,            -3 + 1,      -3 + 1,        tHeight + 3 - 1, lightBg, darkBg);
        this.drawGradientRect(textWidth + 2, -3 + 1,      textWidth + 3, tHeight + 3 - 1, lightBg, darkBg);
        this.drawGradientRect(-3,            -3,          textWidth + 3, -3 + 1,          lightBg, lightBg);
        this.drawGradientRect(-3,            tHeight + 2, textWidth + 3, tHeight + 3,     darkBg,  darkBg);

        this.fontRenderer.drawString(title, 0, 0, 0xFFFFFFFF, true);
        GlStateManager.popMatrix();
    }

    @Override
    public void handleMouseInput() throws IOException {
        if( this.dHeight > 0 ) {
            int dwheel = Mouse.getEventDWheel() / 120;
            if( dwheel != 0 ) {
                this.scroll = Math.min(1.0F, Math.max(0.0F, (this.scroll * this.dHeight - dwheel * 16.0F) / this.dHeight));
            }
        }

        super.handleMouseInput();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if( button instanceof GuiButtonNav ) {
            switch( ((GuiButtonNav) button).buttonType ) {
                case 0:
                    if( this.entry != null && this.group != null ) {
                        this.entry = null;
                        if( this.group.getEntries().size() == 1 ) {
                            this.group = null;
                        }
                        this.updateGUI = true;
                    } else if( this.entry == null && this.group != null ) {
                        this.group = null;
                        this.updateGUI = true;
                    }
                    break;
                case 1:
                    this.entry = null;
                    this.group = null;
                    this.updateGUI = true;
                    break;
                case 2:
                    this.mc.player.closeScreen();
                    break;
            }
        } else if( button instanceof GuiButtonGroup ) {
            GuiButtonGroup grpButton = (GuiButtonGroup) button;
            List<ILexiconEntry> entries = grpButton.group.getEntries();
            this.group = grpButton.group;
            if( entries.size() == 1 ) {
                this.entry = entries.get(0);
            }
            this.updateGUI = true;
        } else if( button instanceof GuiButtonEntry ) {
            this.entry = ((GuiButtonEntry) button).entry;
            this.updateGUI = true;
        } else if( button instanceof GuiButtonLink ) {
            try {
                this.clickedURI = new URI(((GuiButtonLink) button).link);
                if (this.mc.gameSettings.chatLinksPrompt) {
                    this.mc.displayGuiScreen(new GuiConfirmOpenLink(this, this.clickedURI.toString(), 0, false));
                } else {
                    this.openLink(this.clickedURI);
                }
            } catch( URISyntaxException e ) {
                CsmConstants.LOG.log(Level.ERROR, "Cannot create invalid URI", e);
                this.clickedURI = null;
            }
        } else if( this.render == null || !this.render.actionPerformed(button, this.renderHelper) ) {
            super.actionPerformed(button);
        }
    }

    @Override
    public void confirmClicked(boolean isYes, int id) {
        if( id == 0 ) {
            if( isYes ) {
                this.openLink(this.clickedURI);
            }

            this.clickedURI = null;
            this.mc.displayGuiScreen(this);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseBtn) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseBtn);

        if( mouseBtn == 0 ) {
            for( GuiButton btn : this.entryButtons ) {
                if( btn.mousePressed(this.mc, mouseX - this.entryX, mouseY - this.entryY + Math.round(this.scroll * this.dHeight)) ) {
                    btn.playPressSound(this.mc.getSoundHandler());
                    this.actionPerformed(btn);
                }
            }
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public void openLink(URI uri) {
        try {
            java.awt.Desktop.getDesktop().browse(uri);
        } catch( Throwable throwable ) {
            CsmConstants.LOG.log(Level.ERROR, "Couldn\'t open link", throwable);
        }
    }

    public static class GuiButtonLink
            extends GuiButton
    {
        public final String link;

        public GuiButtonLink(int id, int x, int y, String text, String link) {
            super(id, x, y, Minecraft.getMinecraft().fontRenderer.getStringWidth(text), Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT, text);
            this.link = link;
        }

        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY, float partTicks) {
            if( this.visible ) {
                String clrCode = (this.enabled ? TextFormatting.BLUE : TextFormatting.GRAY).toString();
                mc.fontRenderer.drawString(clrCode + TextFormatting.UNDERLINE + this.displayString, this.x, this.y, 0xFF000000, false);
            }
        }
    }

    private static final class GuiButtonNav
            extends GuiButton
    {
        public final int buttonType;

        public GuiButtonNav(int id, int x, int y, int type) {
            super(id, x, y, 25 + (type == 1 ? 1 : 0), 25, "");
            this.buttonType = type;
        }

        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY, float partTicks) {
            if( this.visible ) {
                boolean over = mouseX >= this.x && mouseX < this.x + this.width && mouseY >= this.y && mouseY < this.y + this.height;

                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                mc.getTextureManager().bindTexture(Resources.GUI_LEXICON.resource);
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
                switch( this.buttonType ) {
                    case 0:
                        this.drawTexturedModalRect(this.x + 5, this.y + 8, 0, 236 + (over ? 0 : 10), 15, 9);
                        break;
                    case 1:
                        this.drawTexturedModalRect(this.x + 8, this.y + 8, 16, 236 + (over ? 0 : 10), 10, 9);
                        break;
                    case 2:
                        this.drawTexturedModalRect(this.x + 8, this.y + 8, 27, 236 + (over ? 0 : 10), 9, 9);
                        break;
                }
                GlStateManager.disableBlend();
            }
        }
    }
}
