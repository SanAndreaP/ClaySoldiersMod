/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util;

import de.sanandrew.core.manpack.util.SAPUtils;
import de.sanandrew.mods.claysoldiers.item.*;
import net.minecraft.item.Item;

public final class ModItems
{
    public static Item dollSoldier = new ItemClayManDoll();
    public static Item disruptor = new ItemDisruptor(false);
    public static Item disruptorHardened = new ItemDisruptor(true);
    public static Item shearBlade = new ItemShearBlade();
    public static Item statDisplay = new ItemClayMonitor();

    public static ItemHorseDoll dollHorseMount = new ItemHorseDoll();

    public static void register() {
        dollSoldier.setCreativeTab(CSM_Main.clayTab);
        dollSoldier.setUnlocalizedName(CSM_Main.MOD_ID + ":clayman_doll");

        disruptor.setCreativeTab(CSM_Main.clayTab);
        disruptor.setUnlocalizedName(CSM_Main.MOD_ID + ":disruptor");

        disruptorHardened.setCreativeTab(CSM_Main.clayTab);
        disruptorHardened.setUnlocalizedName(CSM_Main.MOD_ID + ":disruptor_cooked");

        shearBlade.setCreativeTab(CSM_Main.clayTab);
        shearBlade.setUnlocalizedName(CSM_Main.MOD_ID + ":shear_blade");

        statDisplay.setCreativeTab(CSM_Main.clayTab);
        statDisplay.setUnlocalizedName(CSM_Main.MOD_ID + ":stat_display");

        dollHorseMount.setCreativeTab(CSM_Main.clayTab);
        dollHorseMount.setUnlocalizedName(CSM_Main.MOD_ID + ":horsemount_doll");

        SAPUtils.registerItems(dollSoldier, disruptor, disruptorHardened, shearBlade, statDisplay, dollHorseMount);
    }
}
