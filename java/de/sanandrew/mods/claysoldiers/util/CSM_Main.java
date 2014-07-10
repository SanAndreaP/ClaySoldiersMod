package de.sanandrew.mods.claysoldiers.util;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.EventBus;
import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import de.sanandrew.mods.claysoldiers.dispenser.BehaviorDisruptorDispenseItem;
import de.sanandrew.mods.claysoldiers.dispenser.BehaviorSoldierDispenseItem;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

/**
 * @author SanAndreasP
 * @version 1.0
 */

@Mod(modid = CSM_Main.MOD_ID, version = CSM_Main.VERSION, name = "Clay Soldiers Mod")
public class CSM_Main
{
    public static final String MOD_ID = "claysoldiers";
    public static final String VERSION = "2.0";
    public static final String MOD_LOG = "ClaySoldiers";
    public static final String MOD_CHANNEL = "ClaySoldiersNWCH";

    @Mod.Instance(CSM_Main.MOD_ID)
    public static CSM_Main instance;
    @SidedProxy(modId = CSM_Main.MOD_ID, clientSide = "de.sanandrew.mods.claysoldiers.client.util.ClientProxy", serverSide = "de.sanandrew.mods.claysoldiers.util.CommonProxy")
    public static CommonProxy proxy;
    public static FMLEventChannel channel;

    public static final EventBus EVENT_BUS = new EventBus();

    public static CreativeTabs clayTab = new CreativeTabs(CSM_Main.MOD_ID + ":clay_tab") {
        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(Blocks.hardened_clay);
        }
    };

    @Mod.EventHandler
    @SuppressWarnings("unused")
    public void modPreInit(FMLPreInitializationEvent event) {
        ModItems.registerItems();
    }

    @Mod.EventHandler
    @SuppressWarnings("unused")
    public void modInit(FMLInitializationEvent event) {
        channel = NetworkRegistry.INSTANCE.newEventDrivenChannel(CSM_Main.MOD_CHANNEL);

        proxy.modInit();

        int entityId = 0;
        EntityRegistry.registerGlobalEntityID(EntityClayMan.class, CSM_Main.MOD_ID + ":clayman", EntityRegistry.findGlobalUniqueEntityId(), 0xFFFFFF, 0x000000);
        EntityRegistry.registerModEntity(EntityClayMan.class, CSM_Main.MOD_ID + ":clayman", entityId++, this, 64, 1, true);

        BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.soldierDoll, new BehaviorSoldierDispenseItem());
        BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.disruptor, new BehaviorDisruptorDispenseItem());
        BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.disruptorHardened, new BehaviorDisruptorDispenseItem());
    }
}
