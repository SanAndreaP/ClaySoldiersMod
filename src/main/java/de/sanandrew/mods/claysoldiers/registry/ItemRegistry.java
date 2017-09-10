/*
 * ****************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 * *****************************************************************************************************************
 */
package de.sanandrew.mods.claysoldiers.registry;

import de.sanandrew.mods.claysoldiers.item.ItemBrickSoldier;
import de.sanandrew.mods.claysoldiers.item.ItemDisruptor;
import de.sanandrew.mods.claysoldiers.item.ItemShearBlade;
import de.sanandrew.mods.claysoldiers.item.ItemSoldier;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ItemRegistry
{
    public static final ItemSoldier DOLL_SOLDIER = new ItemSoldier();
    public static final ItemDisruptor DISRUPTOR = new ItemDisruptor();
    public static final ItemBrickSoldier DOLL_BRICK_SOLDIER = new ItemBrickSoldier();
    public static final ItemShearBlade SHEAR_BLADE = new ItemShearBlade();

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                DOLL_SOLDIER,
                DISRUPTOR,
                DOLL_BRICK_SOLDIER,
                SHEAR_BLADE
        );
    }
}
