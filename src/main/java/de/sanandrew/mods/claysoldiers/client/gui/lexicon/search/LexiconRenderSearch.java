/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.gui.lexicon.search;

import com.google.common.collect.ArrayTable;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconEntry;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconGroup;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconGuiHelper;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconPageRender;
import de.sanandrew.mods.claysoldiers.client.gui.lexicon.LexiconRegistry;
import de.sanandrew.mods.claysoldiers.client.gui.lexicon.button.GuiButtonEntry;
import de.sanandrew.mods.claysoldiers.util.Lang;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

@SideOnly(Side.CLIENT)
public class LexiconRenderSearch
        implements ILexiconPageRender
{
    public static final String RENDER_SEARCH_ID = CsmConstants.ID + ":search";

    public static final int TYPE_TXT_HEIGHT = 12;
    public static final int GROUP_TXT_HEIGHT = 12;
    public static final int ENTRY_BTN_HEIGHT = 14;

    private int drawHeight;
    private List<GuiButton> entryButtons;
    private GuiTextField search;
    private String lastFilterText;

    private final Table<String, String, GuiButtonEntry> allEntries = HashBasedTable.create();
    private final Table<ILexiconGroup, Boolean, List<GuiButtonEntry>> visibleEntries = HashBasedTable.create();

    @Override
    public String getId() {
        return RENDER_SEARCH_ID;
    }

    @Override
    public int shiftEntryPosY() {
        return 16;
    }

    @Override
    public void initPage(ILexiconEntry entry, ILexiconGuiHelper helper, List<GuiButton> globalButtons, List<GuiButton> entryButtons) {
        this.entryButtons = entryButtons;
        this.lastFilterText = "\u00A0";

        this.search = new GuiTextField(0, helper.getFontRenderer(), 14, 17, MAX_ENTRY_WIDTH + 3, 12);
        this.search.setFocused(true);
        this.search.setCanLoseFocus(true);

        LexiconRegistry.INSTANCE.getGroups().forEach(grp -> {
            grp.getEntries().forEach(ntry -> {
                if( !ntry.getSrcText().isEmpty() && !ntry.getSrcTitle().isEmpty() ) {
                    GuiButtonEntry btn = new GuiButtonEntry(this.entryButtons.size(), 12, 0, ntry, helper.getFontRenderer());
                    btn.visible = false;
                    this.entryButtons.add(btn);
                    this.allEntries.put(ntry.getSrcTitle(), ntry.getSrcText(), btn);
                }
            });
        });
    }

    @Override
    public void updateScreen(ILexiconGuiHelper helper) {
        this.search.updateCursorCounter();

        if( !this.search.getText().equals(this.lastFilterText) ) {
            this.drawHeight = 0;
            this.lastFilterText = this.search.getText();
            this.visibleEntries.clear();
            helper.setScroll(0.0F);

            Pattern filter = Pattern.compile(".*?" + this.lastFilterText + ".*", Pattern.CASE_INSENSITIVE);

            this.allEntries.cellSet().forEach(cell -> {
                GuiButtonEntry btn = Objects.requireNonNull(cell.getValue());
                boolean matchedByTitle = filter.matcher(Objects.requireNonNull(cell.getRowKey())).matches();

                btn.visible = matchedByTitle || filter.matcher(Objects.requireNonNull(cell.getColumnKey())).matches();
                if( btn.visible ) {
                    ILexiconGroup group = LexiconRegistry.INSTANCE.getGroup(btn.entry.getGroupId());
                    List<GuiButtonEntry> btnList = this.visibleEntries.get(group, matchedByTitle);
                    if( btnList == null ) {
                        btnList = new ArrayList<>();
                        this.visibleEntries.put(group, matchedByTitle, btnList);
                    }

                    btnList.add(btn);
                }
            });

            Arrays.stream(new Boolean[] {true, false}).forEach(b -> {
                this.drawHeight += this.visibleEntries.column(b).size() > 0 ? TYPE_TXT_HEIGHT : 0;
                this.visibleEntries.column(b).forEach((group, entries) -> {
                    this.drawHeight += GROUP_TXT_HEIGHT;
                    entries.forEach(btn -> {
                        btn.y = this.drawHeight;
                        this.drawHeight += ENTRY_BTN_HEIGHT;
                    });
                    this.drawHeight += 2;
                });
            });
        }
    }

    @Override
    public void renderPageOverlay(ILexiconEntry entry, ILexiconGuiHelper helper, int mouseX, int mouseY, float partTicks) {
        this.search.drawTextBox();
    }

    @Override
    public void renderPageEntry(ILexiconEntry entry, ILexiconGuiHelper helper, int mouseX, int mouseY, int scrollY, float partTicks) {
        this.drawHeight = 0;
        Arrays.stream(new Boolean[] {true, false}).forEach(b -> {
            if( this.visibleEntries.column(b).size() > 0 ) {
                helper.getFontRenderer().drawString(Lang.translate(b ? Lang.LEXICON_SRC_ENTRY_TITLE : Lang.LEXICON_SRC_ENTRY_TEXT), 0, this.drawHeight, 0xFF808080);
                this.drawHeight += TYPE_TXT_HEIGHT;
                this.visibleEntries.column(b).forEach((group, entries) -> {
                    helper.getFontRenderer().drawString(Lang.translate(Lang.LEXICON_GROUP_NAME.get(group.getId())), 6, this.drawHeight, TITLE_COLOR);
                    this.drawHeight += GROUP_TXT_HEIGHT + entries.size() * ENTRY_BTN_HEIGHT + 2;
                });
            }
        });
    }

    @Override
    public int getEntryHeight(ILexiconEntry entry, ILexiconGuiHelper helper) {
        return this.drawHeight;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseBtn, ILexiconGuiHelper helper) {
        mouseX -= helper.getGuiX();
        mouseY -= helper.getGuiY();

        this.search.mouseClicked(mouseX, mouseY, mouseBtn);
        if( mouseBtn == 1 && mouseX >= this.search.x && mouseX < this.search.x + this.search.width && mouseY >= this.search.y
            && mouseY < this.search.y + this.search.height )
        {
            this.search.setText("");
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode, ILexiconGuiHelper helper) {
        this.search.textboxKeyTyped(typedChar, keyCode);
    }

    @Override
    public void savePageState(NBTTagCompound nbt) {
        nbt.setString("searchString", this.search.getText());
    }

    @Override
    public void loadPageState(NBTTagCompound nbt) {
        if( nbt.hasKey("searchString") ) {
            this.search.setText(nbt.getString("searchString"));
        }
    }
}
