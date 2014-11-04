/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util;

import de.sanandrew.core.manpack.util.helpers.SAPUtils;
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
    public static ItemBunnyDoll dollBunnyMount;
    public static ItemGeckoDoll dollGeckoMount;

    public static void initialize() {
        dollSoldier = new ItemClayManDoll();
        dollBrick = new ItemBrickManDoll();
        disruptor = new ItemDisruptor(false);
        disruptorHardened = new ItemDisruptor(true);
        statDisplay = new ItemClayMonitor();
        shearBlade = new ItemShearBlade();
        dollHorseMount = new ItemHorseDoll();
        dollTurtleMount = new ItemTurtleDoll();
        dollBunnyMount = new ItemBunnyDoll();
        dollGeckoMount = new ItemGeckoDoll();

        dollSoldier.setCreativeTab(ClaySoldiersMod.clayTab);
        dollSoldier.setUnlocalizedName(ClaySoldiersMod.MOD_ID + ":clayman_doll");

        dollBrick.setCreativeTab(ClaySoldiersMod.clayTab);
        dollBrick.setUnlocalizedName(ClaySoldiersMod.MOD_ID + ":clayman_brick_doll");

        disruptor.setCreativeTab(ClaySoldiersMod.clayTab);
        disruptor.setUnlocalizedName(ClaySoldiersMod.MOD_ID + ":disruptor");

        disruptorHardened.setCreativeTab(ClaySoldiersMod.clayTab);
        disruptorHardened.setUnlocalizedName(ClaySoldiersMod.MOD_ID + ":disruptor_cooked");

        statDisplay.setCreativeTab(ClaySoldiersMod.clayTab);
        statDisplay.setUnlocalizedName(ClaySoldiersMod.MOD_ID + ":stat_display");

        shearBlade.setCreativeTab(ClaySoldiersMod.clayTab);
        shearBlade.setUnlocalizedName(ClaySoldiersMod.MOD_ID + ":shear_blade");

        dollHorseMount.setCreativeTab(ClaySoldiersMod.clayTab);
        dollHorseMount.setUnlocalizedName(ClaySoldiersMod.MOD_ID + ":horsemount_doll");

        dollTurtleMount.setCreativeTab(ClaySoldiersMod.clayTab);
        dollTurtleMount.setUnlocalizedName(ClaySoldiersMod.MOD_ID + ":turtlemount_doll");

        dollBunnyMount.setCreativeTab(ClaySoldiersMod.clayTab);
        dollBunnyMount.setUnlocalizedName(ClaySoldiersMod.MOD_ID + ":bunnymount_doll");

        dollGeckoMount.setCreativeTab(ClaySoldiersMod.clayTab);
        dollGeckoMount.setUnlocalizedName(ClaySoldiersMod.MOD_ID + ":geckomount_doll");

        SAPUtils.registerItems(dollSoldier, dollBrick, disruptor, disruptorHardened, statDisplay, shearBlade, dollHorseMount, dollTurtleMount, dollBunnyMount,
                               dollGeckoMount);
    }
}
