/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.gui.lexicon;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconEntry;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

@SideOnly(Side.CLIENT)
public final class LexiconEntry
        implements ILexiconEntry
{
    private final String id;
    private final String groupId;
    private final String renderId;
    private final @Nonnull ItemStack icon;

    public LexiconEntry(String id, String groupId, String renderId, ItemStack icon) {
        this.id = id;
        this.groupId = groupId;
        this.renderId = renderId;
        this.icon = icon;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getGroupId() {
        return this.groupId;
    }

    @Override
    public String getPageRenderId() {
        return this.renderId;
    }

    @Override
    public String getEntryName() {
        return I18n.format(String.format("%s.lexicon.%s.%s.name", CsmConstants.ID, this.groupId, this.id));
    }

    @Override
    public String getEntryText() {
        return I18n.format(String.format("%s.lexicon.%s.%s.text", CsmConstants.ID, this.groupId, this.id));
    }

    @Override
    public ItemStack getEntryIcon() {
        return this.icon;
    }
}
