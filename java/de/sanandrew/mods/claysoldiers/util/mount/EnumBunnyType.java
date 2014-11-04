/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util.mount;

import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public enum EnumBunnyType
{
    BLACK(0x191919, 15, new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/bunny/black.png")),
    RED(0xCC4646, 14, new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/bunny/red.png")),
    GREEN(0x667F33, 13, new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/bunny/green.png")),
    BROWN(0x664C33, 12, new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/bunny/brown.png")),
    BLUE(0x334CB2, 11, new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/bunny/blue.png")),
    PURPLE(0x7F3FB2, 10, new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/bunny/purple.png")),
    CYAN(0x4C7F99, 9, new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/bunny/cyan.png")),
    LIGHT_GRAY(0x999999, 8, new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/bunny/light_gray.png")),
    GRAY(0x4C4C4C, 7, new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/bunny/gray.png")),
    PINK(0xF27FA5, 6, new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/bunny/pink.png")),
    LIME(0x7FCC19, 5, new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/bunny/lime.png")),
    YELLOW(0xE5E533, 4, new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/bunny/yellow.png")),
    LIGHT_BLUE(0x6699D8, 3, new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/bunny/light_blue.png")),
    MAGENTA(0xE000FF, 2, new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/bunny/magenta.png")),
    ORANGE(0xD87F33, 1, new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/bunny/orange.png")),
    WHITE(0xFFFFFF, 0, new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/bunny/white.png"));

    public static final EnumBunnyType[] VALUES = values();

    public final ResourceLocation texture;
    public final int typeColor;
    public final int woolMeta;

    private EnumBunnyType(int typeColor, int woolMeta, ResourceLocation texture) {
        this.texture = texture;
        this.typeColor = typeColor;
        this.woolMeta = woolMeta;
    }

    public static EnumBunnyType getTypeFromItem(ItemStack stack) {
        if( stack == null ) {
            return null;
        }

        if( SAPUtils.isIndexInRange(VALUES, stack.getItemDamage()) ) {
            return VALUES[stack.getItemDamage()];
        }

        return null;
    }
}
