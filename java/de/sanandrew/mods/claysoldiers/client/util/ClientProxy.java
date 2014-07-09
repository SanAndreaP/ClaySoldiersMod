package de.sanandrew.mods.claysoldiers.client.util;

import cpw.mods.fml.client.registry.RenderingRegistry;
import de.sanandrew.core.manpack.util.SAPUtils;
import de.sanandrew.core.manpack.util.javatuples.Quartet;
import de.sanandrew.core.manpack.util.javatuples.Triplet;
import de.sanandrew.core.manpack.util.javatuples.Tuple;
import de.sanandrew.mods.claysoldiers.client.render.entity.RenderClayMan;
import de.sanandrew.mods.claysoldiers.client.util.event.RenderModelUpgradeEvent;
import de.sanandrew.mods.claysoldiers.client.util.event.RenderBodyUpgradesEvent;
import de.sanandrew.mods.claysoldiers.client.util.event.RenderLeftHandUpgradesEvent;
import de.sanandrew.mods.claysoldiers.client.util.event.RenderRightHandUpgradesEvent;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.network.ClientPacketHandler;
import de.sanandrew.mods.claysoldiers.network.packet.PacketParticleFX;
import de.sanandrew.mods.claysoldiers.util.CSM_Main;
import de.sanandrew.mods.claysoldiers.util.CommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityBreakingFX;
import net.minecraft.client.particle.EntityCritFX;
import net.minecraft.item.Item;

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
        CSM_Main.EVENT_BUS.register(new RenderModelUpgradeEvent());
        CSM_Main.EVENT_BUS.register(new RenderBodyUpgradesEvent());
    }

    @Override
    public void spawnParticles(byte particleId, Tuple particleData) {
        Minecraft mc = Minecraft.getMinecraft();
        switch( particleId ) {
            case PacketParticleFX.FX_BREAK: {
                @SuppressWarnings("unchecked")
                Quartet<Double, Double, Double, String> data = (Quartet) particleData;
                Item item = (Item) Item.itemRegistry.getObject(data.getValue3());

                for (int i = 0; i < 5; i++) {
                    EntityBreakingFX fx = new EntityBreakingFX(mc.theWorld, data.getValue0(), data.getValue1(), data.getValue2(), item);
                    mc.effectRenderer.addEffect(fx);
                }

                break;
            }
            case PacketParticleFX.FX_CRIT: {
                @SuppressWarnings("unchecked")
                Triplet<Double, Double, Double> data = (Triplet) particleData;
                for (int i = 0; i < 10; i++) {
                    double motX = SAPUtils.RANDOM.nextDouble() - 0.5D;
                    double motY = SAPUtils.RANDOM.nextDouble() * 0.5D;
                    double motZ = SAPUtils.RANDOM.nextDouble() - 0.5D;
                    EntityCritFX fx = new EntityCritFX(mc.theWorld, data.getValue0(), data.getValue1(), data.getValue2(), motX, motY, motZ);
                    mc.effectRenderer.addEffect(fx);
                }
                break;
            }
        }

    }
}
