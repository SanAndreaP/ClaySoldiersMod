/*
 * ****************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 * *****************************************************************************************************************
 */
package de.sanandrew.mods.claysoldiers.util;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.item.ItemDisruptor;
import de.sanandrew.mods.claysoldiers.registry.ItemRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CsmCreativeTabs
{
    public static final CreativeTabs DOLLS = new CreativeTabs(CsmConstants.ID + ":dolls") {
        private ItemStack[] tabIcons;

        @Override
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem() {
            if( this.tabIcons == null ) {
                NonNullList<ItemStack> subItms = NonNullList.create();
                ItemRegistry.DOLL_SOLDIER.getSubItems(this, subItms);
                this.tabIcons = subItms.toArray(new ItemStack[0]);
            }

            return this.tabIcons[(int) (System.currentTimeMillis() / 4250) % this.tabIcons.length];
        }

        @Override
        @SideOnly(Side.CLIENT)
        public ItemStack getIconItemStack() {
            return this.getTabIconItem();
        }
    };

    public static final CreativeTabs MISC = new CreativeTabs(CsmConstants.ID + ":misc") {
        private ItemStack clayDisruptor;

        @Override
        public ItemStack getTabIconItem() {
            if( this.clayDisruptor == null ) {
                this.clayDisruptor = ItemDisruptor.setType(new ItemStack(ItemRegistry.DISRUPTOR, 1), ItemDisruptor.DisruptorType.CLAY);
            }

            return this.clayDisruptor;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public ItemStack getIconItemStack() {
            return this.getTabIconItem();
        }
    };
}
