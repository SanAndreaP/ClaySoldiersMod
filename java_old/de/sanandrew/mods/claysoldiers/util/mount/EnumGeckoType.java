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
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public enum EnumGeckoType
{
    OAK_OAK(0x919191,     0, 0, 0x4E9C39, 0x7F6139, "textures/entity/mount/gecko/spots_oak.png", "textures/entity/mount/gecko/body_oak.png"),
    OAK_PINE(0x919191,    0, 1, 0x4E9C39, 0x2E1D0A, "textures/entity/mount/gecko/spots_oak.png", "textures/entity/mount/gecko/body_pine.png"),
    OAK_BIRCH(0x919191,   0, 2, 0x4E9C39, 0xCFE3BA, "textures/entity/mount/gecko/spots_oak.png", "textures/entity/mount/gecko/body_birch.png"),
    OAK_JUNGLE(0x919191,  0, 3, 0x4E9C39, 0x181F05, "textures/entity/mount/gecko/spots_oak.png", "textures/entity/mount/gecko/body_jungle.png"),
    OAK_ACACIA(0x919191,  0, 4, 0x4E9C39, 0x846412, "textures/entity/mount/gecko/spots_oak.png", "textures/entity/mount/gecko/body_acacia.png"),
    OAK_DARKOAK(0x919191, 0, 5, 0x4E9C39, 0x442D12, "textures/entity/mount/gecko/spots_oak.png", "textures/entity/mount/gecko/body_darkoak.png"),
    PINE_OAK(0x919191,     1, 0, 0x395A39, 0x7F6139, "textures/entity/mount/gecko/spots_pine.png", "textures/entity/mount/gecko/body_oak.png"),
    PINE_PINE(0x919191,    1, 1, 0x395A39, 0x2E1D0A, "textures/entity/mount/gecko/spots_pine.png", "textures/entity/mount/gecko/body_pine.png"),
    PINE_BIRCH(0x919191,   1, 2, 0x395A39, 0xCFE3BA, "textures/entity/mount/gecko/spots_pine.png", "textures/entity/mount/gecko/body_birch.png"),
    PINE_JUNGLE(0x919191,  1, 3, 0x395A39, 0x181F05, "textures/entity/mount/gecko/spots_pine.png", "textures/entity/mount/gecko/body_jungle.png"),
    PINE_ACACIA(0x919191,  1, 4, 0x395A39, 0x846412, "textures/entity/mount/gecko/spots_pine.png", "textures/entity/mount/gecko/body_acacia.png"),
    PINE_DARKOAK(0x919191, 1, 5, 0x395A39, 0x442D12, "textures/entity/mount/gecko/spots_pine.png", "textures/entity/mount/gecko/body_darkoak.png"),
    BIRCH_OAK(0x919191,     2, 0, 0x90C679, 0x7F6139, "textures/entity/mount/gecko/spots_birch.png", "textures/entity/mount/gecko/body_oak.png"),
    BIRCH_PINE(0x919191,    2, 1, 0x90C679, 0x2E1D0A, "textures/entity/mount/gecko/spots_birch.png", "textures/entity/mount/gecko/body_pine.png"),
    BIRCH_BIRCH(0x919191,   2, 2, 0x90C679, 0xCFE3BA, "textures/entity/mount/gecko/spots_birch.png", "textures/entity/mount/gecko/body_birch.png"),
    BIRCH_JUNGLE(0x919191,  2, 3, 0x90C679, 0x181F05, "textures/entity/mount/gecko/spots_birch.png", "textures/entity/mount/gecko/body_jungle.png"),
    BIRCH_ACACIA(0x919191,  2, 4, 0x90C679, 0x846412, "textures/entity/mount/gecko/spots_birch.png", "textures/entity/mount/gecko/body_acacia.png"),
    BIRCH_DARKOAK(0x919191, 2, 5, 0x90C679, 0x442D12, "textures/entity/mount/gecko/spots_birch.png", "textures/entity/mount/gecko/body_darkoak.png"),
    JUNGLE_OAK(0x919191,     3, 0, 0x378020, 0x7F6139, "textures/entity/mount/gecko/spots_jungle.png", "textures/entity/mount/gecko/body_oak.png"),
    JUNGLE_PINE(0x919191,    3, 1, 0x378020, 0x2E1D0A, "textures/entity/mount/gecko/spots_jungle.png", "textures/entity/mount/gecko/body_pine.png"),
    JUNGLE_BIRCH(0x919191,   3, 2, 0x378020, 0xCFE3BA, "textures/entity/mount/gecko/spots_jungle.png", "textures/entity/mount/gecko/body_birch.png"),
    JUNGLE_JUNGLE(0x919191,  3, 3, 0x378020, 0x181F05, "textures/entity/mount/gecko/spots_jungle.png", "textures/entity/mount/gecko/body_jungle.png"),
    JUNGLE_ACACIA(0x919191,  3, 4, 0x378020, 0x846412, "textures/entity/mount/gecko/spots_jungle.png", "textures/entity/mount/gecko/body_acacia.png"),
    JUNGLE_DARKOAK(0x919191, 3, 5, 0x378020, 0x442D12, "textures/entity/mount/gecko/spots_jungle.png", "textures/entity/mount/gecko/body_darkoak.png"),
    ACACIA_OAK(0x919191,     4, 0, 0x72891B, 0x7F6139, "textures/entity/mount/gecko/spots_acacia.png", "textures/entity/mount/gecko/body_oak.png"),
    ACACIA_PINE(0x919191,    4, 1, 0x72891B, 0x2E1D0A, "textures/entity/mount/gecko/spots_acacia.png", "textures/entity/mount/gecko/body_pine.png"),
    ACACIA_BIRCH(0x919191,   4, 2, 0x72891B, 0xCFE3BA, "textures/entity/mount/gecko/spots_acacia.png", "textures/entity/mount/gecko/body_birch.png"),
    ACACIA_JUNGLE(0x919191,  4, 3, 0x72891B, 0x181F05, "textures/entity/mount/gecko/spots_acacia.png", "textures/entity/mount/gecko/body_jungle.png"),
    ACACIA_ACACIA(0x919191,  4, 4, 0x72891B, 0x846412, "textures/entity/mount/gecko/spots_acacia.png", "textures/entity/mount/gecko/body_acacia.png"),
    ACACIA_DARKOAK(0x919191, 4, 5, 0x72891B, 0x442D12, "textures/entity/mount/gecko/spots_acacia.png", "textures/entity/mount/gecko/body_darkoak.png"),
    DARKOAK_OAK(0x919191,     5, 0, 0x459633, 0x7F6139, "textures/entity/mount/gecko/spots_darkoak.png", "textures/entity/mount/gecko/body_oak.png"),
    DARKOAK_PINE(0x919191,    5, 1, 0x459633, 0x2E1D0A, "textures/entity/mount/gecko/spots_darkoak.png", "textures/entity/mount/gecko/body_pine.png"),
    DARKOAK_BIRCH(0x919191,   5, 2, 0x459633, 0xCFE3BA, "textures/entity/mount/gecko/spots_darkoak.png", "textures/entity/mount/gecko/body_birch.png"),
    DARKOAK_JUNGLE(0x919191,  5, 3, 0x459633, 0x181F05, "textures/entity/mount/gecko/spots_darkoak.png", "textures/entity/mount/gecko/body_jungle.png"),
    DARKOAK_ACACIA(0x919191,  5, 4, 0x459633, 0x846412, "textures/entity/mount/gecko/spots_darkoak.png", "textures/entity/mount/gecko/body_acacia.png"),
    DARKOAK_DARKOAK(0x919191, 5, 5, 0x459633, 0x442D12, "textures/entity/mount/gecko/spots_darkoak.png", "textures/entity/mount/gecko/body_darkoak.png");

    public static final EnumGeckoType[] VALUES = values();

    public final int typeColor;
    public final Pair<Integer, Integer> saplingTypes;
    public final Pair<Integer, Integer> colors;
    public final ResourceLocation textures[];

    EnumGeckoType(int typeColor, int saplingOne, int saplingTwo, int itemColorLimbs, int itemColorBody, String textureSpots, String textureBody) {
        this.typeColor = typeColor;
        this.saplingTypes = Pair.with(saplingOne, saplingTwo);
        this.colors = Pair.with(itemColorLimbs, itemColorBody);
        this.textures = new ResourceLocation[] {new ResourceLocation(ClaySoldiersMod.MOD_ID, textureSpots), new ResourceLocation(ClaySoldiersMod.MOD_ID, textureBody)};
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

            if( ItemUtils.areStacksEqual(sapling1, stack1, true) && ItemUtils.areStacksEqual(sapling2, stack2, true) ) {
                return type;
            }
        }

        return null;
    }
}
