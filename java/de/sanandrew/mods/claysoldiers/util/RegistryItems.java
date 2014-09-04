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

public final class RegistryItems
{
    public static Item dollSoldier;
    public static Item disruptor;
    public static Item disruptorHardened;
    public static Item shearBlade;
    public static Item statDisplay;
    public static Item dollBrick;

    public static ItemHorseDoll dollHorseMount;
    public static ItemTurtleDoll dollTurtleMount;

    public static void initialize() {
        dollSoldier = new ItemClayManDoll();
        dollBrick = new ItemBrickManDoll();
        disruptor = new ItemDisruptor(false);
        disruptorHardened = new ItemDisruptor(true);
        statDisplay = new ItemClayMonitor();
        shearBlade = new ItemShearBlade();
        dollHorseMount = new ItemHorseDoll();
        dollTurtleMount = new ItemTurtleDoll();

        dollSoldier.setCreativeTab(CSM_Main.clayTab);
        dollSoldier.setUnlocalizedName(CSM_Main.MOD_ID + ":clayman_doll");

        dollBrick.setCreativeTab(CSM_Main.clayTab);
        dollBrick.setUnlocalizedName(CSM_Main.MOD_ID + ":clayman_brick_doll");

        disruptor.setCreativeTab(CSM_Main.clayTab);
        disruptor.setUnlocalizedName(CSM_Main.MOD_ID + ":disruptor");

        disruptorHardened.setCreativeTab(CSM_Main.clayTab);
        disruptorHardened.setUnlocalizedName(CSM_Main.MOD_ID + ":disruptor_cooked");

        statDisplay.setCreativeTab(CSM_Main.clayTab);
        statDisplay.setUnlocalizedName(CSM_Main.MOD_ID + ":stat_display");

        shearBlade.setCreativeTab(CSM_Main.clayTab);
        shearBlade.setUnlocalizedName(CSM_Main.MOD_ID + ":shear_blade");

        dollHorseMount.setCreativeTab(CSM_Main.clayTab);
        dollHorseMount.setUnlocalizedName(CSM_Main.MOD_ID + ":horsemount_doll");

        dollTurtleMount.setCreativeTab(CSM_Main.clayTab);
        dollTurtleMount.setUnlocalizedName(CSM_Main.MOD_ID + ":turtlemount_doll");

        SAPUtils.registerItems(dollSoldier, dollBrick, disruptor, disruptorHardened, statDisplay, shearBlade, dollHorseMount, dollTurtleMount);
    }
}
