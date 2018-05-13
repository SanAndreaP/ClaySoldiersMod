/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.gui.lexicon.soldier;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconEntry;
import de.sanandrew.mods.claysoldiers.registry.team.TeamRegistry;
import de.sanandrew.mods.claysoldiers.registry.team.Teams;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class LexiconEntryBaseSoldier
        implements ILexiconEntry
{
    private static final String ID = CsmConstants.ID + ":baseSoldier";
    private final ItemStack icon;

    public LexiconEntryBaseSoldier() {
        this.icon = TeamRegistry.INSTANCE.getNewTeamStack(1, Teams.SOLDIER_CLAY);
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
        return LexiconRenderBaseSoldier.ID;
    }

    @Override
    public String getEntryName() {
        return I18n.format(String.format("%s.lexicon.%s.%s.name", CsmConstants.ID, this.getGroupId(), this.getId()));
    }

    @Override
    public String getEntryText() {
        return I18n.format(String.format("%s.lexicon.%s.%s.text", CsmConstants.ID, this.getGroupId(), this.getId()));
    }

    @Nonnull
    @Override
    public ItemStack getEntryIcon() {
        return this.icon;
    }

    @Override
    public ResourceLocation getPicture() {
        return null;
    }

    @Override
    public boolean divideAfter() {
        return false;
    }
}
