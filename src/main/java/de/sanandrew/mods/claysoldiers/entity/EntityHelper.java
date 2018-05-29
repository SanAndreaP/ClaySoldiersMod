/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity;

import de.sanandrew.mods.sanlib.lib.util.ItemStackUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public final class EntityHelper
{
    public static void dropItems(Entity e, NonNullList<ItemStack> drops) {
        drops.removeIf(stack -> !ItemStackUtils.isValid(stack));
        for( ItemStack drop : drops ) {
            EntityItem item = e.entityDropItem(drop, 0.5F);
            if( item != null ) {
                item.motionX = 0.0F;
                item.motionY = 0.0F;
                item.motionZ = 0.0F;
                item.velocityChanged = true;
            }
        }
    }
}
