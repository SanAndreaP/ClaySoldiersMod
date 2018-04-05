/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.gui.lexicon.upgrades;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconEntry;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgrade;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Arrays;

public class LexiconEntryUpgrade
        implements ILexiconEntry
{
    private final String id;
    private final String groupId;
    private final String renderId;
    private final ItemStack[] icons;

    public LexiconEntryUpgrade(String groupId, String renderId, ISoldierUpgrade upgrade) {
        this.id = upgrade.getModId() + ':' + upgrade.getShortName();
        this.groupId = groupId;
        this.renderId = renderId;
        this.icons = Arrays.stream(upgrade.getStacks()).map(item -> {
            NonNullList<ItemStack> newItems = NonNullList.create();
            if( item.getItemDamage() == OreDictionary.WILDCARD_VALUE ) {
                item.getItem().getSubItems(CreativeTabs.SEARCH, newItems);
            } else {
                newItems.add(item);
            }
            return newItems;
        }).collect(ArrayList<ItemStack>::new, ArrayList::addAll, ArrayList::addAll).toArray(new ItemStack[0]);
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
        return this.icons[(int) ((System.nanoTime() / 1_000_000_000) % this.icons.length)];
    }
}
