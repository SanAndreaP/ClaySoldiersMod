/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.crafting;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.NonNullList;

import java.util.ArrayList;
import java.util.List;

public class FuelHelper
{
    public static final List<ItemStack> FUELS = new ArrayList<>();

    public static void initialize() {
        Item.REGISTRY.forEach(item -> {
            NonNullList<ItemStack> subItems = NonNullList.create();
            item.getSubItems(CreativeTabs.SEARCH, subItems);
            subItems.forEach(subItm -> {
                if( TileEntityFurnace.getItemBurnTime(subItm) > 0 ) {
                    FUELS.add(subItm.copy());
                }
            });
        });
    }
}
