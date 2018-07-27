/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.gui.lexicon.mount;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.client.util.ClientProxy;
import de.sanandrew.mods.claysoldiers.item.ItemRegistry;
import de.sanandrew.mods.claysoldiers.registry.mount.EnumClayHorseType;
import de.sanandrew.mods.sanlib.api.client.lexicon.ILexiconEntryCraftingGrid;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class LexiconEntryPegasus
        implements ILexiconEntryCraftingGrid
{
    private static final String ID = "pegasus";
    private final ItemStack[] icons;
    private final ResourceLocation prevPic;

    public LexiconEntryPegasus() {
        this.icons = Arrays.stream(EnumClayHorseType.VALUES).filter(EnumClayHorseType::isVisible).map(ItemRegistry.DOLL_PEGASUS::getTypeStack).toArray(ItemStack[]::new);
        this.prevPic = new ResourceLocation(CsmConstants.ID, "textures/gui/lexicon/page_pics/mounts/" + CsmConstants.ID + "_pegasi.png");
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
        return ClientProxy.lexiconInstance.getCraftingRenderID();
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
        return ClientProxy.lexiconInstance.getTranslatedTitle(this);
    }

    @Nonnull
    @Override
    public String getSrcText() {
        return ClientProxy.lexiconInstance.getTranslatedText(this);
    }
}
