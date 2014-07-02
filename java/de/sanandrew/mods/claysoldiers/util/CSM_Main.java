package de.sanandrew.mods.claysoldiers.util;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
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

    @Mod.Instance(CSM_Main.MOD_ID)
    public static CSM_Main instance;
    @SidedProxy(modId = CSM_Main.MOD_ID, clientSide = "de.sanandrew.mods.claysoldiers.client.util.ClientProxy", serverSide = "de.sanandrew.mods.claysoldiers.util.CommonProxy")
    public static CommonProxy proxy;

    public static CreativeTabs clayTab = new CreativeTabs("sap.csm.clay_tab") {
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
        proxy.registerRenderers();

        int entityId = 0;
        EntityRegistry.registerGlobalEntityID(EntityClayMan.class, "sap.csm.clayman", EntityRegistry.findGlobalUniqueEntityId(), 0xFFFFFF, 0x000000);
        EntityRegistry.registerModEntity(EntityClayMan.class, "sap.csm.clayman", entityId++, this, 64, 1, true);

    }
}
