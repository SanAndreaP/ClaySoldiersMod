/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util.mount;

import de.sanandrew.core.manpack.util.helpers.ItemUtils;
import de.sanandrew.core.manpack.util.javatuples.Pair;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

public enum EnumHorseType
{
    DIRT(35.0F, 0.6F, 0x9C5300, new ItemStack(Blocks.dirt), ClaySoldiersMod.MOD_ID + ":doll_horse", 0x9C5300,
         new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/horses/dirt1.png"),
         new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/horses/dirt2.png"),
         new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/horses/dirt3.png"),
         new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/horses/dirt4.png")
    ),
    SAND(30.0F, 0.7F, 0xF9FF80, new ItemStack(Blocks.sand), ClaySoldiersMod.MOD_ID + ":doll_horse", 0xF9FF80,
         new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/horses/sand.png")
    ),
    GRAVEL(45.0F, 0.4F, 0xD1BABA, new ItemStack(Blocks.gravel), ClaySoldiersMod.MOD_ID + ":doll_horse", 0xD1BABA,
           new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/horses/gravel1.png"),
           new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/horses/gravel2.png")
    ),
    SNOW(40.0F, 0.5F, 0xFFFFFF, new ItemStack(Blocks.snow), ClaySoldiersMod.MOD_ID + ":doll_horse", 0xFFFFFF,
         new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/horses/snow.png")
    ),
    GRASS(20.0F, 0.9F, 0x2ABA1A, new ItemStack(Blocks.tallgrass, 1, OreDictionary.WILDCARD_VALUE), ClaySoldiersMod.MOD_ID + ":doll_horse", 0x2ABA1A,
          new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/horses/grass1.png"),
          new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/horses/grass2.png")
    ),
    LAPIS(35.0F, 0.9F, 0x4430C2, new ItemStack(Items.dye, 1, 4), ClaySoldiersMod.MOD_ID + ":doll_horse", 0x4430C2,
          new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/horses/lapis.png")
    ),
    CLAY(35.0F, 0.6F, 0xA3A3A3, new ItemStack(Blocks.clay), ClaySoldiersMod.MOD_ID + ":doll_horse",
         0xA3A3A3, new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/horses/clay.png")
    ),
    CARROT(35.0F, 0.9F, 0xF0A800, new ItemStack(Items.carrot), ClaySoldiersMod.MOD_ID + ":doll_horse", 0xF0A800,
           new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/horses/carrot1.png"),
           new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/horses/carrot2.png")
    ),
    SOULSAND(35.0F, 0.8F, 0x5C3100, new ItemStack(Blocks.soul_sand), ClaySoldiersMod.MOD_ID + ":doll_horse", 0x5C3100,
             new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/horses/soulsand.png")
    ),
    CAKE(30.0F, 1.1F, 0xFFFFFF, new ItemStack(Items.cake), ClaySoldiersMod.MOD_ID + ":doll_horse_cake", 0xFFFFFF,
         new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/horses/cake.png")
    ),
    NIGHTMARE(50.0F, 1.2F, 0x000000, null,
              new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/horses/spec_nightmare1.png"),       // special!
              new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/horses/spec_nightmare2.png")
    );

    public static final EnumHorseType[] VALUES = values();

    public final float health;
    public final float moveSpeed;
    public final ResourceLocation[] textures;
    public final Pair<String, Integer> itemData;
    public final int typeColor;
    public final ItemStack item;

    EnumHorseType(float health, float speed, int typeColor, ItemStack materialItem, ResourceLocation... textures) {
        this.health = health;
        this.moveSpeed = speed;
        this.textures = textures;
        this.itemData = null;
        this.typeColor = typeColor;
        this.item = materialItem;
    }

    EnumHorseType(float health, float speed, int typeColor, ItemStack materialItem, String itemTexture, int itemColor, ResourceLocation... textures) {
        this.health = health;
        this.moveSpeed = speed;
        this.textures = textures;
        this.itemData = Pair.with(itemTexture, itemColor);
        this.typeColor = typeColor;
        this.item = materialItem;
    }

    public static EnumHorseType getTypeFromItem(ItemStack stack) {
        if( stack == null ) {
            return null;
        }

        if( stack.getItem() == Item.getItemFromBlock(Blocks.tallgrass) && stack.getItemDamage() == 0 ) {
            return null;
        }

        for( EnumHorseType type : VALUES ) {
            if( type.item == null ) {
                return null;
            }

            if( ItemUtils.areStacksEqual(type.item, stack, true) ) {
                return type;
            }
        }

        return null;
    }
}
