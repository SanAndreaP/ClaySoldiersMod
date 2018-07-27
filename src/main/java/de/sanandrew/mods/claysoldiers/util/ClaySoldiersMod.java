/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP, SilverChiren and CliffracerX
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.CsmPlugin;
import de.sanandrew.mods.claysoldiers.api.ICsmPlugin;
import de.sanandrew.mods.claysoldiers.compat.IMCHandler;
import de.sanandrew.mods.claysoldiers.crafting.CraftingRecipes;
import de.sanandrew.mods.claysoldiers.crafting.FuelHelper;
import de.sanandrew.mods.claysoldiers.eventhandler.LivingEventHandler;
import de.sanandrew.mods.claysoldiers.eventhandler.SoldierEventHandler;
import de.sanandrew.mods.claysoldiers.registry.DispenserBehaviorRegistry;
import de.sanandrew.mods.claysoldiers.network.PacketManager;
import de.sanandrew.mods.claysoldiers.network.datasync.DataSerializerUUID;
import de.sanandrew.mods.claysoldiers.entity.EntityRegistry;
import de.sanandrew.mods.claysoldiers.registry.team.TeamRegistry;
import de.sanandrew.mods.claysoldiers.registry.effect.EffectRegistry;
import de.sanandrew.mods.claysoldiers.registry.upgrade.UpgradeRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.util.EntitySelectors;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import org.apache.logging.log4j.Level;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Mod(modid = CsmConstants.ID, version = CsmConstants.VERSION, name = CsmConstants.NAME, guiFactory = CsmConstants.GUI_FACTORY, dependencies = CsmConstants.DEPENDENCIES)
public class ClaySoldiersMod
{
    public static SimpleNetworkWrapper network;

    public static final List<ICsmPlugin> PLUGINS = new ArrayList<>();
    public static final EventBus EVENT_BUS = new EventBus();

    @Mod.Instance(CsmConstants.ID)
    public static ClaySoldiersMod instance;
    @SidedProxy(modId = CsmConstants.ID, clientSide = CsmConstants.MOD_PROXY_CLIENT, serverSide = CsmConstants.MOD_PROXY_SERVER)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        network = NetworkRegistry.INSTANCE.newSimpleChannel(CsmConstants.CHANNEL);

        CsmConfig.initialize(event);

        loadPlugins(event.getAsmData());
        PLUGINS.forEach(plugin -> {
            plugin.registerTeams(TeamRegistry.INSTANCE);
            plugin.registerCsmEvents(EVENT_BUS);
        });

        NetworkRegistry.INSTANCE.registerGuiHandler(this, GuiHandler.INSTANCE);
        PacketManager.initialize();

        DispenserBehaviorRegistry.initialize();
        EntityRegistry.initialize();

        MinecraftForge.EVENT_BUS.register(new LivingEventHandler());
        MinecraftForge.EVENT_BUS.register(new SoldierEventHandler());

        IMCHandler.sendIMC();

        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);

        PLUGINS.forEach(plugin -> plugin.registerUpgrades(UpgradeRegistry.INSTANCE));
        PLUGINS.forEach(plugin -> plugin.registerEffects(EffectRegistry.INSTANCE));

        DataSerializerUUID.initialize();

        CraftingRecipes.registerSmelting();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);

        FuelHelper.initialize();
    }

    private static void loadPlugins(ASMDataTable dataTable) {
        String annotationClassName = CsmPlugin.class.getCanonicalName();
        Set<ASMDataTable.ASMData> asmDatas = dataTable.getAll(annotationClassName);
        for( ASMDataTable.ASMData asmData : asmDatas ) {
            try {
                Class<?> asmClass = Class.forName(asmData.getClassName());
                Class<? extends ICsmPlugin> asmInstanceClass = asmClass.asSubclass(ICsmPlugin.class);
                ICsmPlugin instance = asmInstanceClass.getConstructor().newInstance();
                PLUGINS.add(instance);
            } catch( ClassNotFoundException | IllegalAccessException | ExceptionInInitializerError | InstantiationException | NoSuchMethodException | InvocationTargetException e ) {
                CsmConstants.LOG.log(Level.ERROR, "Failed to load: {}", asmData.getClassName(), e);
            }
        }
    }

    public static void sendSpawnPacket(Entity e) {
        if( !e.world.isRemote ) {
            Packet<?> pkt = FMLNetworkHandler.getEntitySpawningPacket(e);
            if( pkt != null ) {
                List<EntityPlayerMP> playersInRange = e.world.getPlayers(EntityPlayerMP.class, EntitySelectors.NOT_SPECTATING);
                playersInRange.forEach(player -> player.connection.sendPacket(pkt));
            }
        }
    }
}
