/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.gui.lexicon.mount;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconEntryCraftingGrid;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconPageRender;
import de.sanandrew.mods.claysoldiers.registry.ItemRegistry;
import de.sanandrew.mods.claysoldiers.registry.mount.EnumGeckoType;
import de.sanandrew.mods.claysoldiers.util.Lang;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class LexiconEntryGecko
        implements ILexiconEntryCraftingGrid
{
    private static final String ID = "gecko";
    private final ItemStack[] icons;
    private final ResourceLocation prevPic;

    public LexiconEntryGecko() {
        this.icons = Arrays.stream(EnumGeckoType.VALUES).filter(EnumGeckoType::isVisible).map(ItemRegistry.DOLL_GECKO::getTypeStack).toArray(ItemStack[]::new);
        this.prevPic = new ResourceLocation(CsmConstants.ID, "textures/gui/lexicon/page_pics/mounts/" + CsmConstants.ID + "_geckos.png");
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getGroupId() {
        return LexiconGroupMounts.GRP_NAME;
    }

    @Override
    public String getPageRenderId() {
        return ILexiconPageRender.RENDER_CRAFTING_ID;
    }

    @Nonnull
    @Override
    public ItemStack getEntryIcon() {
        return this.icons[(int) ((System.nanoTime() / 1_000_000_000) % this.icons.length)];
    }

    @Override
    public ResourceLocation getPicture() {
        return this.prevPic;
    }

    @Nonnull
    @Override
    public NonNullList<ItemStack> getRecipeResults() {
        return NonNullList.from(ItemStack.EMPTY, this.icons);
    }

    @Nonnull
    @Override
    public String getSrcTitle() {
        return Lang.translate(Lang.LEXICON_ENTRY_NAME.get(this.getGroupId(), this.getId()));
    }

    @Nonnull
    @Override
    public String getSrcText() {
        return Lang.translate(Lang.LEXICON_ENTRY_TEXT.get(this.getGroupId(), this.getId()));
    }
}
