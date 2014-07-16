package de.sanandrew.mods.claysoldiers.util;

import cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.EventBus;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import de.sanandrew.mods.claysoldiers.dispenser.BehaviorDisruptorDispenseItem;
import de.sanandrew.mods.claysoldiers.dispenser.BehaviorSoldierDispenseItem;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.entity.mounts.EntityHorseMount;
import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;

/**
 * @author SanAndreasP
 * @version 1.0
 */

@Mod(modid = CSM_Main.MOD_ID, version = CSM_Main.VERSION, name = "Clay Soldiers Mod", guiFactory = CSM_Main.MOD_GUI_FACTORY, dependencies = "required-after:sapmanpack@[2.0.0,)")
public final class CSM_Main
{
    public static final String MOD_ID = "claysoldiers";
    public static final String VERSION = "2.0";
    public static final String MOD_LOG = "ClaySoldiers";
    public static final String MOD_CHANNEL = "ClaySoldiersNWCH";
    public static final String MOD_GUI_FACTORY = "de.sanandrew.mods.claysoldiers.client.gui.ModGuiFactory";

    private static final String MOD_PROXY_CLIENT = "de.sanandrew.mods.claysoldiers.client.util.ClientProxy";
    private static final String MOD_PROXY_COMMON = "de.sanandrew.mods.claysoldiers.util.CommonProxy";

    @Mod.Instance(CSM_Main.MOD_ID)
    public static CSM_Main instance;
    @SidedProxy(modId = CSM_Main.MOD_ID, clientSide = CSM_Main.MOD_PROXY_CLIENT, serverSide = CSM_Main.MOD_PROXY_COMMON)
    public static CommonProxy proxy;
    public static FMLEventChannel channel;

    public static final EventBus EVENT_BUS = new EventBus();

    public static CreativeTabs clayTab = new CreativeTabs(CSM_Main.MOD_ID + ":clay_tab") {
        @Override
        public Item getTabIconItem() {
            return ModItems.dollSoldier;
        }
    };

    @Mod.EventHandler
    @SuppressWarnings("unused")
    public void modPreInit(FMLPreInitializationEvent event) {
        ModConfig.config = new Configuration(event.getSuggestedConfigurationFile());
        ModConfig.syncConfig();

        ModItems.registerItems();
    }

    @Mod.EventHandler
    @SuppressWarnings("unused")
    public void modInit(FMLInitializationEvent event) {
        FMLCommonHandler.instance().bus().register(this.instance);

        channel = NetworkRegistry.INSTANCE.newEventDrivenChannel(CSM_Main.MOD_CHANNEL);

        proxy.modInit();

        int entityId = 0;
        EntityRegistry.registerModEntity(EntityClayMan.class, CSM_Main.MOD_ID + ":clayman", entityId++, this, 64, 1, true);
        EntityRegistry.registerModEntity(EntityHorseMount.class, CSM_Main.MOD_ID + ":horsemount", entityId++, this, 64, 1, true);

        BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.dollSoldier, new BehaviorSoldierDispenseItem());
        BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.disruptor, new BehaviorDisruptorDispenseItem());
        BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.disruptorHardened, new BehaviorDisruptorDispenseItem());
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public void onConfigChanged(OnConfigChangedEvent eventArgs) {
        if( eventArgs.modID.equals(MOD_ID) ) {
            ModConfig.syncConfig();
        }
    }
}
