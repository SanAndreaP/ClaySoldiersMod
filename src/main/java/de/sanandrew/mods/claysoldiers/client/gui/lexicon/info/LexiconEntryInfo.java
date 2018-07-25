/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.gui.lexicon.info;

import de.sanandrew.mods.sanlib.api.client.lexicon.ILexiconEntry;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public final class LexiconEntryInfo
        implements ILexiconEntry
{
    public static final String ID = "info";
    private final ItemStack icon;

    public LexiconEntryInfo() {
        this.icon = new ItemStack(Blocks.BARRIER, 1);
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getGroupId() {
        return LexiconGroupInfo.GRP_NAME;
    }

    @Override
    public String getPageRenderId() {
        return LexiconRenderInfo.RENDER_INFO_ID;
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
