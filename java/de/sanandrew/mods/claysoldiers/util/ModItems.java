package de.sanandrew.mods.claysoldiers.util;

import de.sanandrew.core.manpack.util.SAPUtils;
import de.sanandrew.mods.claysoldiers.item.ItemClayDoll;
import de.sanandrew.mods.claysoldiers.item.ItemDisruptor;
import net.minecraft.item.Item;

/**
 * @author SanAndreas
 * @version 1.0
 */
public class ModItems
{
    public static Item soldierDoll = new ItemClayDoll();
    public static Item disruptor = new ItemDisruptor(false);
    public static Item disruptorHardened = new ItemDisruptor(true);

    public static void registerItems() {
        soldierDoll.setCreativeTab(CSM_Main.clayTab);
        soldierDoll.setUnlocalizedName(CSM_Main.MOD_ID + ":claydoll");

        disruptor.setCreativeTab(CSM_Main.clayTab);
        disruptor.setUnlocalizedName(CSM_Main.MOD_ID + ":disruptor");

        disruptorHardened.setCreativeTab(CSM_Main.clayTab);
        disruptorHardened.setUnlocalizedName(CSM_Main.MOD_ID + ":disruptor_cooked");

        SAPUtils.registerItems("item", soldierDoll, disruptor, disruptorHardened);
    }
}
