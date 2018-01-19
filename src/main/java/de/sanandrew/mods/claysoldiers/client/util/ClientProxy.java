/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.util;

import de.sanandrew.mods.claysoldiers.api.client.IRenderHookRegistry;
import de.sanandrew.mods.claysoldiers.api.client.ISoldierRenderHook;
import de.sanandrew.mods.claysoldiers.api.client.soldier.ISoldierRender;
import de.sanandrew.mods.claysoldiers.client.event.RenderWorldEventHandler;
import de.sanandrew.mods.claysoldiers.client.particle.ParticleHandler;
import de.sanandrew.mods.claysoldiers.client.renderer.color.ItemColorHorse;
import de.sanandrew.mods.claysoldiers.client.renderer.mount.RenderClayHorse;
import de.sanandrew.mods.claysoldiers.client.renderer.soldier.RenderClaySoldier;
import de.sanandrew.mods.claysoldiers.client.renderer.color.ItemColorSoldier;
import de.sanandrew.mods.claysoldiers.client.renderer.projectile.RenderProjectile;
import de.sanandrew.mods.claysoldiers.entity.mount.EntityClayHorse;
import de.sanandrew.mods.claysoldiers.entity.projectile.EntityProjectileEmerald;
import de.sanandrew.mods.claysoldiers.entity.soldier.EntityClaySoldier;
import de.sanandrew.mods.claysoldiers.entity.projectile.EntityProjectileFirecharge;
import de.sanandrew.mods.claysoldiers.entity.projectile.EntityProjectileGravel;
import de.sanandrew.mods.claysoldiers.entity.projectile.EntityProjectileSnow;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import de.sanandrew.mods.claysoldiers.util.CommonProxy;
import de.sanandrew.mods.claysoldiers.item.ItemRegistry;
import de.sanandrew.mods.claysoldiers.util.EnumParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@SuppressWarnings({"unused"})
public class ClientProxy
        extends CommonProxy
        implements IRenderHookRegistry
{
    private ISoldierRender soldierRenderer;

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        RenderingRegistry.registerEntityRenderingHandler(EntityClaySoldier.class, RenderClaySoldier::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityProjectileGravel.class, RenderProjectile.Gravel::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityProjectileSnow.class, RenderProjectile.Snow::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityProjectileFirecharge.class, RenderProjectile.Firecharge::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityProjectileEmerald.class, RenderProjectile.Emerald::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityClayHorse.class, RenderClayHorse::new);

        MinecraftForge.EVENT_BUS.register(RenderWorldEventHandler.INSTANCE);

        ClaySoldiersMod.PLUGINS.forEach(plugin -> plugin.registerCsmClientEvents(ClaySoldiersMod.EVENT_BUS));
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);

        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new ItemColorSoldier(), ItemRegistry.DOLL_SOLDIER);
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new ItemColorHorse(), ItemRegistry.DOLL_HORSE);

        this.soldierRenderer = (ISoldierRender) Minecraft.getMinecraft().getRenderManager().<EntityClaySoldier>getEntityClassRenderObject(EntityClaySoldier.class);

        ClaySoldiersMod.PLUGINS.forEach(plugin -> plugin.registerSoldierRenderHook(this));
    }

    @Override
    public void spawnParticle(EnumParticle particle, int dim, double x, double y, double z, Object... additData) {
        ParticleHandler.spawn(particle, dim, x, y, z, additData);
    }

    @Override
    public boolean registerSoldierHook(ISoldierRenderHook renderer) {
        return this.soldierRenderer.addRenderHook(renderer);
    }

    @Override
    public EntityPlayer getClientPlayer() {
        return Minecraft.getMinecraft().player;
    }

    @Override
    public void setRenderLightningAt(double x, double y, double z, EnumDyeColor color) {
        RenderWorldEventHandler.INSTANCE.setRenderLightningAt(x, y, z, color == null ? 0x33FF33 : color.getColorValue());
    }
}
