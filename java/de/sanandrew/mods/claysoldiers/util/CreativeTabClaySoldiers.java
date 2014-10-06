/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util;

import de.sanandrew.mods.claysoldiers.item.ItemClayManDoll;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CreativeTabClaySoldiers
        extends CreativeTabs
{
    private ItemStack tabIcon;

    public CreativeTabClaySoldiers() {
        super(ClaySoldiersMod.MOD_ID + ":csm_tab");
    }

    @Override
    public ItemStack getIconItemStack() {
        if( this.tabIcon == null ) {
            this.tabIcon = super.getIconItemStack();
            ItemClayManDoll.setTeamForItem("clay", this.tabIcon);
        }

        return this.tabIcon;
    }

    @Override
    public Item getTabIconItem() {
        return RegistryItems.dollSoldier;
    }

    @Override
    public String getBackgroundImageName() {
        return "claysoldiers.png";
    }

    @Override
    public boolean hasSearchBar() {
        return true;
    }
}
