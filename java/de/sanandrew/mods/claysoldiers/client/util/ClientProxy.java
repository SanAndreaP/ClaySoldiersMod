package de.sanandrew.mods.claysoldiers.client.util;

import cpw.mods.fml.client.registry.RenderingRegistry;
import de.sanandrew.mods.claysoldiers.client.render.entity.RenderClayMan;
import de.sanandrew.mods.claysoldiers.client.util.event.RenderArmorUpgradeEvent;
import de.sanandrew.mods.claysoldiers.client.util.event.RenderBodyUpgradesEvent;
import de.sanandrew.mods.claysoldiers.client.util.event.RenderLeftHandUpgradesEvent;
import de.sanandrew.mods.claysoldiers.client.util.event.RenderRightHandUpgradesEvent;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.network.ClientPacketHandler;
import de.sanandrew.mods.claysoldiers.util.CSM_Main;
import de.sanandrew.mods.claysoldiers.util.CommonProxy;

/**
 * @author SanAndreasP
 * @version 1.0
 */
public class ClientProxy extends CommonProxy
{
    @Override
    public void modInit() {
        super.modInit();

        CSM_Main.channel.register(new ClientPacketHandler());

        RenderingRegistry.registerEntityRenderingHandler(EntityClayMan.class, new RenderClayMan());

        CSM_Main.EVENT_BUS.register(new RenderRightHandUpgradesEvent());
        CSM_Main.EVENT_BUS.register(new RenderLeftHandUpgradesEvent());
        CSM_Main.EVENT_BUS.register(new RenderArmorUpgradeEvent());
        CSM_Main.EVENT_BUS.register(new RenderBodyUpgradesEvent());
    }
}
