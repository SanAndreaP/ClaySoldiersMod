/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.gui.lexicon.misc;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.client.util.ClientProxy;
import de.sanandrew.mods.sanlib.api.client.lexicon.ILexiconEntry;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class LexiconEntryChestAccess
        implements ILexiconEntry
{
    private static final String ID = "chestaccess";
    private final ItemStack icon;
    private final ResourceLocation prevPic;

    public LexiconEntryChestAccess() {
        this.icon = new ItemStack(Blocks.CHEST, 1);
        this.prevPic = new ResourceLocation(CsmConstants.ID, "textures/gui/lexicon/page_pics/misc/" + CsmConstants.ID + '_' + ID + ".png");
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getGroupId() {
        return LexiconGroupMisc.GRP_NAME;
    }

    @Override
    public String getPageRenderId() {
        return ClientProxy.lexiconInstance.getStandardRenderID();
    }

    @Nonnull
    @Override
    public ItemStack getEntryIcon() {
        return this.icon;
    }

    @Override
    public ResourceLocation getPicture() {
        return this.prevPic;
    }

    @Nonnull
    @Override
    public String getSrcTitle() {
        return ClientProxy.lexiconInstance.getTranslatedTitle(this);
    }

    @Nonnull
    @Override
    public String getSrcText() {
        return ClientProxy.lexiconInstance.getTranslatedText(this);
    }
}
