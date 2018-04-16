/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.gui.lexicon.upgrades;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconEntry;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Arrays;
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
        this.id = CsmConstants.ID + ":cat_" + type.name().toLowerCase(Locale.ROOT);
        this.groupId = LexiconGroupUpgrades.GRP_NAME;
        this.renderId = CsmConstants.ID + ":upgradetype";
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

    @Override
    public ResourceLocation getPicture() {
        return null;
    }

    @Override
    public boolean divideAfter() {
        return this.type == EnumUpgradeType.VALUES[EnumUpgradeType.VALUES.length - 1];
    }
}
