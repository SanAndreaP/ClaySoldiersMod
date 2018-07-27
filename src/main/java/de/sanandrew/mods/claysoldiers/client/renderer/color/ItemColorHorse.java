/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.renderer.color;

import de.sanandrew.mods.claysoldiers.item.ItemRegistry;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

public class ItemColorHorse
        implements IItemColor
{
    @Override
    public int colorMultiplier(ItemStack stack, int tintIndex) {
        return tintIndex > 0 ? -1 : ItemRegistry.DOLL_HORSE.getType(stack).itemColor;
    }
}
