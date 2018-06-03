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
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconGuiHelper;
import de.sanandrew.mods.claysoldiers.util.Lang;
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

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiLexicon
        extends GuiScreen
        implements GuiYesNoCallback
{
    int guiLeft;
    int guiTop;
    int entryX;
    int entryY;

    private static ILexiconGroup group;
    private static ILexiconEntry entry;
    @Nonnull
    private ILexiconPageRender render;

    static float scroll;
    int dHeight;
    boolean isScrolling;
    private URI clickedURI;
    private boolean updateGUI;

    public final List<GuiButton> entryButtons;
    public final ILexiconGuiHelper renderHelper;

    private static final Deque<History> NAV_HISTORY = new ArrayDeque<>();
    private static final Deque<History> NAV_FUTURE = new ArrayDeque<>();

    public GuiLexicon() {
        this.entryButtons = new ArrayList<>();
        this.renderHelper = new LexiconGuiHelper(this);
        this.render = EmptyRenderer.INSTANCE;
    }

    @SuppressWarnings("unchecked")
    public void initGui() {
        super.initGui();

        this.guiLeft = (this.width - ILexiconPageRender.GUI_SIZE_X) / 2;
        this.guiTop = (this.height - ILexiconPageRender.GUI_SIZE_Y) / 2;

        this.entryX = this.guiLeft + ILexiconPageRender.ENTRY_X;
        this.entryY = this.guiTop + ILexiconPageRender.ENTRY_Y;

        this.buttonList.clear();
        this.entryButtons.clear();

        this.buttonList.add(new GuiButtonNav(this.buttonList.size(), this.guiLeft + 30, this.guiTop + 190, 0, !NAV_HISTORY.isEmpty()));
        this.buttonList.add(new GuiButtonNav(this.buttonList.size(), this.guiLeft + (ILexiconPageRender.GUI_SIZE_X - 10) / 2, this.guiTop + 190, 1, true));
        this.buttonList.add(new GuiButtonNav(this.buttonList.size(), this.guiLeft + ILexiconPageRender.GUI_SIZE_X - 48, this.guiTop + 190, 2, !NAV_FUTURE.isEmpty()));

        if( group == null ) {
            int posX = 0;
            int posY = 0;
            for( ILexiconGroup group : LexiconRegistry.INSTANCE.getGroups() ) {
                this.buttonList.add(new GuiButtonGroup(this.buttonList.size(), this.guiLeft + 12 + posX, this.guiTop + 24 + posY, group, this::groupBtnMouseOver));
                if( (posX += 32) > this.guiLeft + 12 + ILexiconPageRender.MAX_ENTRY_WIDTH ) {
                    posX = 0;
                    posY += 32;
                }
            }
        } else if( entry == null ) {
            int posY = 0;
            int btnX = (ILexiconPageRender.MAX_ENTRY_WIDTH - ILexiconPageRender.BTN_ENTRY_WIDTH) / 2;
            group.sortEntries();
            for( ILexiconEntry entry : group.getEntries() ) {
                this.entryButtons.add(new GuiButtonEntry(this.entryButtons.size(), btnX, 19 + posY, entry, this.renderHelper.getFontRenderer()));
                posY += 14;
                if( entry.divideAfter() ) {
                    this.entryButtons.add(new GuiButtonEntryDivider(this.entryButtons.size(), btnX, 19 + posY));
                    posY += 5;
                }
            }
        } else {
            this.render = LexiconRegistry.INSTANCE.getPageRender(entry.getPageRenderId());
            if( this.render != null ) {
                this.render.initPage(entry, this.renderHelper, this.buttonList, this.entryButtons);
            } else {
                CsmConstants.LOG.log(Level.ERROR, String.format("cannot render lexicon page entry %s as render ID %s is not registered!", entry.getId(), entry.getPageRenderId()));
                this.render = EmptyRenderer.INSTANCE;
            }
        }

        this.updateScreen();
    }

    @Override
    public void updateScreen() {
        if( this.updateGUI ) {
            this.updateGUI = false;
            this.initGui();
        }

        if( entry != null ) {
            this.dHeight = this.render.getEntryHeight(entry, this.renderHelper) - ILexiconPageRender.MAX_ENTRY_HEIGHT;
        } else if( group != null ) {
            this.dHeight = this.entryButtons.size() * 14 + 20 - ILexiconPageRender.MAX_ENTRY_HEIGHT;
        } else {
            this.dHeight = 0;
        }

        for( GuiButton btn : this.entryButtons ) {
            btn.enabled = btn.y - Math.round(scroll * this.dHeight) > 0 && btn.y - Math.round(scroll * this.dHeight) + btn.height < ILexiconPageRender.MAX_ENTRY_HEIGHT;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partTicks) {
        boolean mouseDown = Mouse.isButtonDown(0);

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.drawDefaultBackground();

        this.mc.renderEngine.bindTexture(Resources.GUI_LEXICON.resource);

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, ILexiconPageRender.GUI_SIZE_X, ILexiconPageRender.GUI_SIZE_Y);

        GlStateManager.pushMatrix();
        GlStateManager.translate(this.entryX + ILexiconPageRender.MAX_ENTRY_WIDTH, this.entryY, 0.0F);
        drawRect(0, 0, 6, ILexiconPageRender.MAX_ENTRY_HEIGHT, 0x30000000);
        if( this.dHeight > 0 ) {
            drawRect(0, Math.round((ILexiconPageRender.MAX_ENTRY_HEIGHT - 16) * scroll), 6, Math.round((ILexiconPageRender.MAX_ENTRY_HEIGHT - 16) * scroll + 16), 0x800000FF);
        }
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        this.renderHelper.doEntryScissoring();
        GlStateManager.translate(this.entryX, this.entryY, 0.0F);

        if( entry != null ) {
            this.render.renderPageGlobal(entry, this.renderHelper, mouseX - this.entryX, mouseY - entryY, partTicks);
        }

        GlStateManager.translate(0.0F, Math.round(-scroll * this.dHeight), 0.0F);

        if( entry != null ) {
            this.render.renderPageEntry(entry, this.renderHelper, mouseX - this.entryX, mouseY - entryY, Math.round(scroll * this.dHeight), partTicks);
        } else if( group != null ) {
            this.renderHelper.getFontRenderer().drawString(TextFormatting.ITALIC + Lang.translate(Lang.LEXICON_GROUP_NAME.get(group.getId())), 2, 2, 0xFF33AA33, false);
            Gui.drawRect(2, 12, ILexiconPageRender.MAX_ENTRY_WIDTH - 2, 13, 0xFF33AA33);
        }

        for( GuiButton btn : this.entryButtons ) {
            btn.drawButton(this.mc, mouseX - this.entryX, mouseY - this.entryY + Math.round(scroll * this.dHeight), partTicks);
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
            scroll = mouseDelta / (ILexiconPageRender.MAX_ENTRY_HEIGHT - 16.0F);
        }

        super.drawScreen(mouseX, mouseY, partTicks);
    }

    public void changePage(ILexiconGroup group, ILexiconEntry entry, float scroll, boolean doHistory) {
        if( doHistory ) {
            NAV_HISTORY.offer(new History(GuiLexicon.group, GuiLexicon.entry, GuiLexicon.scroll));
            NAV_FUTURE.clear();
        }
        GuiLexicon.group = group;
        GuiLexicon.entry = entry;
        GuiLexicon.scroll = scroll;
        this.updateGUI = true;
    }

    private void groupBtnMouseOver(ILexiconGroup group, int mouseX, int mouseY) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(mouseX + 12, mouseY - 12, 32.0F);

        String title = Lang.translate(Lang.LEXICON_GROUP_NAME.get(group.getId()));
        int bkgColor = 0xF0101010;
        int lightBg = 0x50A0A0A0;
        int darkBg = (lightBg & 0xFEFEFE) >> 1 | lightBg & 0xFF000000;
        int textWidth = this.renderHelper.getFontRenderer().getStringWidth(title);
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

        this.renderHelper.getFontRenderer().drawString(title, 0, 0, 0xFFFFFFFF, true);
        GlStateManager.popMatrix();
    }

    @Override
    public void handleMouseInput() throws IOException {
        if( this.dHeight > 0 ) {
            int dwheel = Mouse.getEventDWheel() / 120;
            if( dwheel != 0 ) {
                scroll = Math.min(1.0F, Math.max(0.0F, (scroll * this.dHeight - dwheel * 16.0F) / this.dHeight));
            }
        }

        super.handleMouseInput();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if( !this.render.actionPerformed(button, this.renderHelper) ) {
            if( button instanceof GuiButtonNav ) {
                History h;
                switch( ((GuiButtonNav) button).buttonType ) {
                    case 0:
                        h = NAV_HISTORY.pollLast();
                        if( h != null ) {
                            NAV_FUTURE.offer(new History(group, entry, scroll));
                            this.changePage(h.group, h.entry, h.scroll, false);
                        }
                        break;
                    case 1:
                        this.changePage(null, null, 0.0F, true);
                        break;
                    case 2:
                        h = NAV_FUTURE.pollLast();
                        if( h != null ) {
                            NAV_HISTORY.offer(new History(group, entry, scroll));
                            this.changePage(h.group, h.entry, h.scroll, false);
                        }
                        break;
                }
            } else if( button instanceof GuiButtonGroup ) {
                GuiButtonGroup grpButton = (GuiButtonGroup) button;
                List<ILexiconEntry> entries = grpButton.group.getEntries();
                this.changePage(grpButton.group, entries.size() == 1 ? entries.get(0) : null, 0.0F, true);
            } else if( button instanceof GuiButtonEntry ) {
                this.changePage(group, ((GuiButtonEntry) button).entry, 0.0F, true);
            } else if( button instanceof GuiButtonLink ) {
                try {
                    this.clickedURI = new URI(((GuiButtonLink) button).link);
                    if( this.mc.gameSettings.chatLinksPrompt ) {
                        this.mc.displayGuiScreen(new GuiConfirmOpenLink(this, this.clickedURI.toString(), 0, false));
                    } else {
                        this.openLink(this.clickedURI);
                    }
                } catch( URISyntaxException e ) {
                    CsmConstants.LOG.log(Level.ERROR, "Cannot create invalid URI", e);
                    this.clickedURI = null;
                }
            } else {
                super.actionPerformed(button);
            }
        }
    }

    public float getZLevel() {
        return this.zLevel;
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
                if( btn.mousePressed(this.mc, mouseX - this.entryX, mouseY - this.entryY + Math.round(scroll * this.dHeight)) ) {
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

    private static final class History
    {
        final ILexiconGroup group;
        final ILexiconEntry entry;
        final float scroll;

        History(ILexiconGroup group, ILexiconEntry entry, float scroll) {
            this.group = group;
            this.entry = entry;
            this.scroll = scroll;
        }
    }

    private static final class GuiButtonNav
            extends GuiButton
    {
        public final int buttonType;

        public GuiButtonNav(int id, int x, int y, int type, boolean visible) {
            super(id, x, y, (type == 1 ? 10 : 18), 10, "");
            this.buttonType = type;
            this.visible = visible;
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
                    case 0: this.drawTexturedModalRect(this.x, this.y, 220 + (over ? 18 : 0), 52, 18, 10); break;
                    case 1: this.drawTexturedModalRect(this.x, this.y, 236 + (over ? 10 : 0), 62, 10, 10); break;
                    case 2: this.drawTexturedModalRect(this.x, this.y, 220 + (over ? 18 : 0), 42, 18, 10); break;
                }
                GlStateManager.disableBlend();
            }
        }
    }

    private static final class EmptyRenderer
            implements ILexiconPageRender
    {
        static final ILexiconPageRender INSTANCE = new EmptyRenderer();

        @Override public String getId() { return ""; }
        @Override public void initPage(ILexiconEntry entry, ILexiconGuiHelper helper, List<GuiButton> globalButtons, List<GuiButton> entryButtons) { }
        @Override public void renderPageEntry(ILexiconEntry entry, ILexiconGuiHelper helper, int mouseX, int mouseY, int scrollY, float partTicks) { }
        @Override public int getEntryHeight(ILexiconEntry entry, ILexiconGuiHelper helper) { return 0; }
    }
}
