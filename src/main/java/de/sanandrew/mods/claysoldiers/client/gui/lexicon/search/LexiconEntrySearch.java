/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.gui.lexicon.search;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconEntryCraftingGrid;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconPageRender;
import de.sanandrew.mods.claysoldiers.client.gui.lexicon.misc.LexiconGroupMisc;
import de.sanandrew.mods.claysoldiers.util.Lang;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public final class LexiconEntrySearch
        implements ILexiconEntryCraftingGrid
{
    public static final String ID = "search";
    private final ItemStack icon;

    public LexiconEntrySearch() {
        this.icon = new ItemStack(Blocks.BARRIER, 1);
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getGroupId() {
        return LexiconGroupSearch.GRP_NAME;
    }

    @Override
    public String getPageRenderId() {
        return LexiconRenderSearch.RENDER_SEARCH_ID;
    }

    @Nonnull
    @Override
    public ItemStack getEntryIcon() {
        return this.icon;
    }

    @Nonnull
    @Override
    public String getSrcTitle() {
        return "";
    }

    @Nonnull
    @Override
    public String getSrcText() {
        return "";
    }
}
