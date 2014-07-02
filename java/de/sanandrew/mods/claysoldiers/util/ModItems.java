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
        soldierDoll.setUnlocalizedName("sap.csm.claydoll");

        disruptor.setCreativeTab(CSM_Main.clayTab);
        disruptor.setUnlocalizedName("sap.csm.disruptor");

        disruptorHardened.setCreativeTab(CSM_Main.clayTab);
        disruptorHardened.setUnlocalizedName("sap.csm.disruptor_cooked");

        SAPUtils.registerItems("sap.csm.item", soldierDoll, disruptor, disruptorHardened);
    }
}
