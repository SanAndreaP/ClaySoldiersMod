/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.gui.lexicon.soldier;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconEntryCraftingGrid;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconPageRender;
import de.sanandrew.mods.claysoldiers.registry.team.TeamRegistry;
import de.sanandrew.mods.claysoldiers.registry.team.Teams;
import de.sanandrew.mods.claysoldiers.util.Lang;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class LexiconEntryBaseSoldier
        implements ILexiconEntryCraftingGrid
{
    private static final String ID = "baseSoldier";
    private final ItemStack icon;
    private final ResourceLocation prevPic;

    public LexiconEntryBaseSoldier() {
        this.icon = TeamRegistry.INSTANCE.getNewTeamStack(1, Teams.SOLDIER_CLAY);
        this.prevPic = new ResourceLocation(CsmConstants.ID, "textures/gui/lexicon/page_pics/soldiers/" + CsmConstants.ID + "_basesoldier.png");
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getGroupId() {
        return LexiconGroupSoldiers.GRP_NAME;
    }

    @Override
    public String getPageRenderId() {
        return ILexiconPageRender.RENDER_CRAFTING_ID;
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

    @Override
    public NonNullList<ItemStack> getRecipeResults() {
        return NonNullList.withSize(1, this.icon);
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
