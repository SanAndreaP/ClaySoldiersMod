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

public class ItemColorTurtle
        implements IItemColor
{
    @Override
    public int colorMultiplier(ItemStack stack, int tintIndex) {
        switch( tintIndex ) {
            case 0: return ItemRegistry.DOLL_TURTLE.getType(stack).itemColorBody;
            case 1: return ItemRegistry.DOLL_TURTLE.getType(stack).itemColorShell;
            default: return -1;
        }
    }
}
