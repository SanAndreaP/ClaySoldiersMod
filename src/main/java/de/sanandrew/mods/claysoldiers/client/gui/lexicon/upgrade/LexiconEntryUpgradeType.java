/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.gui.lexicon.upgrade;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconEntry;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.util.Lang;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Locale;

@SideOnly(Side.CLIENT)
public class LexiconEntryUpgradeType
        implements ILexiconEntry
{
    private final String id;
    private final String groupId;
    private final String renderId;
    private final ItemStack icon;
    final EnumUpgradeType type;

    public LexiconEntryUpgradeType(EnumUpgradeType type, ItemStack icon) {
        this.id = "cat_" + type.name().toLowerCase(Locale.ROOT);
        this.groupId = LexiconGroupUpgrades.GRP_NAME;
        this.renderId = LexiconRenderUpgradeType.ID;
        this.type = type;
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
    public ItemStack getEntryIcon() {
        return this.icon;
    }

    @Override
    public ResourceLocation getPicture() {
        return null;
    }

    @Override
    public boolean divideAfter() {
        return this.type == EnumUpgradeType.VALUES[EnumUpgradeType.VALUES.length - 1];
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
