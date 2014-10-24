/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util.mount;

import de.sanandrew.core.manpack.util.SAPUtils;
import de.sanandrew.core.manpack.util.javatuples.Pair;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public enum EnumGeckoType
{
    //TODO: add acacia and dark oak variants
    OAK_OAK(0x919191,    0, 0, 0x684923, 0x57AD3F, new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/gecko/oak_oak.png")),
    OAK_PINE(0x919191,   0, 1, 0x684923, 0x395A39, new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/gecko/oak_pine.png")),
    OAK_BIRCH(0x919191,  0, 2, 0x684923, 0x8FC64F, new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/gecko/oak_birch.png")),
    OAK_JUNGLE(0x919191, 0, 3, 0x684923, 0x378020, new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/gecko/oak_jungle.png")),
    PINE_OAK(0x919191,    1, 0, 0x130803, 0x57AD3F, new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/gecko/pine_oak.png")),
    PINE_PINE(0x919191,   1, 1, 0x130803, 0x395A39, new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/gecko/pine_pine.png")),
    PINE_BIRCH(0x919191,  1, 2, 0x130803, 0x8FC64F, new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/gecko/pine_birch.png")),
    PINE_JUNGLE(0x919191, 1, 3, 0x130803, 0x378020, new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/gecko/pine_jungle.png")),
    BIRCH_OAK(0x919191,    2, 0, 0xBCCEA9, 0x57AD3F, new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/gecko/birch_oak.png")),
    BIRCH_PINE(0x919191,   2, 1, 0xBCCEA9, 0x395A39, new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/gecko/birch_pine.png")),
    BIRCH_BIRCH(0x919191,  2, 2, 0xBCCEA9, 0x8FC64F, new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/gecko/birch_birch.png")),
    BIRCH_JUNGLE(0x919191, 2, 3, 0xBCCEA9, 0x378020, new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/gecko/birch_jungle.png")),
    JUNGLE_OAK(0x919191,    3, 0, 0x181F05, 0x57AD3F, new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/gecko/jungle_oak.png")),
    JUNGLE_PINE(0x919191,   3, 1, 0x181F05, 0x395A39, new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/gecko/jungle_pine.png")),
    JUNGLE_BIRCH(0x919191,  3, 2, 0x181F05, 0x8FC64F, new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/gecko/jungle_birch.png")),
    JUNGLE_JUNGLE(0x919191, 3, 3, 0x181F05, 0x378020, new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/mount/gecko/jungle_jungle.png"));

    public static final EnumGeckoType[] VALUES = values();

    public final int typeColor;
    public final Pair<Integer, Integer> saplingTypes;
    public final Pair<Integer, Integer> colors;
    public final ResourceLocation texture;

    private EnumGeckoType(int typeColor, int saplingOne, int saplingTwo, int itemColorLimbs, int itemColorBody, ResourceLocation texture) {
        this.typeColor = typeColor;
        this.saplingTypes = Pair.with(saplingOne, saplingTwo);
        this.colors = Pair.with(itemColorLimbs, itemColorBody);
        this.texture = texture;
    }

    public static EnumGeckoType getTypeFromItem(ItemStack stack1, ItemStack stack2) {
        if( stack1 == null || stack2 == null ) {
            return null;
        }

        ItemStack sapling1 = new ItemStack(Blocks.sapling);
        ItemStack sapling2 = new ItemStack(Blocks.sapling);

        for( EnumGeckoType type : VALUES ) {
            sapling1.setItemDamage(type.saplingTypes.getValue0());
            sapling2.setItemDamage(type.saplingTypes.getValue1());

            if( SAPUtils.areStacksEqualWithWCV(sapling1, stack1) && SAPUtils.areStacksEqualWithWCV(sapling2, stack2) ) {
                return type;
            }
        }

        return null;
    }
}
