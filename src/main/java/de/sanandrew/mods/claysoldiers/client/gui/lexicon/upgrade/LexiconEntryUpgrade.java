/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.gui.lexicon.upgrade;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconEntry;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgrade;
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

@SideOnly(Side.CLIENT)
public class LexiconEntryUpgrade
        implements ILexiconEntry
{
    private final String id;
    private final String groupId;
    private final String renderId;
    private final ItemStack[] icons;
    private final ResourceLocation prevPic;
    final ISoldierUpgrade upgrade;

    public LexiconEntryUpgrade(ISoldierUpgrade upgrade) {
        this.id = upgrade.getShortName();
        this.groupId = LexiconGroupUpgrades.GRP_NAME;
        this.renderId = LexiconRenderUpgrades.ID;
        this.upgrade = upgrade;
        this.icons = Arrays.stream(upgrade.getStacks()).map(item -> {
            NonNullList<ItemStack> newItems = NonNullList.create();
            if( item.getItemDamage() == OreDictionary.WILDCARD_VALUE ) {
                item.getItem().getSubItems(CreativeTabs.SEARCH, newItems);
            } else {
                newItems.add(item);
            }
            return newItems;
        }).collect(ArrayList<ItemStack>::new, ArrayList::addAll, ArrayList::addAll).toArray(new ItemStack[0]);
        this.prevPic = new ResourceLocation(CsmConstants.ID, "textures/gui/lexicon/page_pics/upgrades/" + upgrade.getModId() + '_' + upgrade.getShortName() + ".png");
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
        return this.icons[(int) ((System.nanoTime() / 1_000_000_000) % this.icons.length)];
    }

    @Override
    public ResourceLocation getPicture() {
        return this.prevPic;
    }

    @Override
    public boolean divideAfter() {
        return false;
    }
}
