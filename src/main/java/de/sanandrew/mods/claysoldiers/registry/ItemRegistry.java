/*
 * ****************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 * *****************************************************************************************************************
 */
package de.sanandrew.mods.claysoldiers.registry;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.item.ItemDisruptor;
import de.sanandrew.mods.claysoldiers.item.ItemSoldier;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@SuppressWarnings("ConstantNamingConvention")
@Mod.EventBusSubscriber
@GameRegistry.ObjectHolder(CsmConstants.ID)
public class ItemRegistry
{
    public static final ItemSoldier doll_soldier = nilItem();
    public static final ItemDisruptor disruptor = nilItem();

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
            new ItemSoldier().setRegistryName(CsmConstants.ID, "doll_soldier"),
            new ItemDisruptor().setRegistryName(CsmConstants.ID, "disruptor")
        );
    }

    /** prevents IDE from thinking the item fields are null */
    private static <T> T nilItem() {
        return null;
    }
}
