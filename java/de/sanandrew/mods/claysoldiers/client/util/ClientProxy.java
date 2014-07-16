package de.sanandrew.mods.claysoldiers.client.util;

import cpw.mods.fml.client.registry.RenderingRegistry;
import de.sanandrew.core.manpack.util.javatuples.Quartet;
import de.sanandrew.core.manpack.util.javatuples.Triplet;
import de.sanandrew.core.manpack.util.javatuples.Tuple;
import de.sanandrew.mods.claysoldiers.client.event.*;
import de.sanandrew.mods.claysoldiers.client.particle.ParticleHelper;
import de.sanandrew.mods.claysoldiers.client.render.entity.RenderClayMan;
import de.sanandrew.mods.claysoldiers.client.render.entity.RenderHorseMount;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.entity.mounts.EntityHorseMount;
import de.sanandrew.mods.claysoldiers.network.ClientPacketHandler;
import de.sanandrew.mods.claysoldiers.network.packet.PacketParticleFX;
import de.sanandrew.mods.claysoldiers.util.CSM_Main;
import de.sanandrew.mods.claysoldiers.util.CommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

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
        RenderingRegistry.registerEntityRenderingHandler(EntityHorseMount.class, new RenderHorseMount());

        CSM_Main.EVENT_BUS.register(new RenderSoldierRightHandEvent());
        CSM_Main.EVENT_BUS.register(new RenderSoldierLeftHandEvent());
        CSM_Main.EVENT_BUS.register(new RenderSoldierModelEvent());
        CSM_Main.EVENT_BUS.register(new RenderSoldierBodyEvent());

        MinecraftForge.EVENT_BUS.register(new RenderHudOverlayEvent());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void spawnParticles(byte particleId, Tuple particleData) {
        Minecraft mc = Minecraft.getMinecraft();
        switch( particleId ) {
            case PacketParticleFX.FX_BREAK:
                ParticleHelper.spawnBreakFx((Quartet) particleData, mc);
                break;
            case PacketParticleFX.FX_CRIT:
                ParticleHelper.spawnCritFx((Triplet) particleData, mc);
                break;
            case PacketParticleFX.FX_SOLDIER_DEATH:
                ParticleHelper.spawnSoldierDeathFx((Quartet) particleData, mc);
                break;
            case PacketParticleFX.FX_HORSE_DEATH:
                ParticleHelper.spawnHorseDeathFx((Quartet) particleData, mc);
                break;

        }
    }

    @Override
    public void applySoldierRenderFlags(int entityId, long upgFlags1, long upgFlags2, long effFlags1, long effFlags2) {
        World world = Minecraft.getMinecraft().theWorld;
        Entity entity = world.getEntityByID(entityId);

        if( entity instanceof EntityClayMan ) {
            EntityClayMan clayman = (EntityClayMan) entity;
            clayman.applyRenderFlags(upgFlags1, upgFlags2, effFlags1, effFlags2);
        }
    }
}
