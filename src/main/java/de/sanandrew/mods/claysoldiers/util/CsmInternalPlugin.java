/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util;

import de.sanandrew.mods.claysoldiers.api.CsmPlugin;
import de.sanandrew.mods.claysoldiers.api.ICsmPlugin;
import de.sanandrew.mods.claysoldiers.api.IEffectRegistry;
import de.sanandrew.mods.claysoldiers.api.client.IRenderHookRegistry;
import de.sanandrew.mods.claysoldiers.api.soldier.ITeamRegistry;
import de.sanandrew.mods.claysoldiers.api.IUpgradeRegistry;
import de.sanandrew.mods.claysoldiers.client.event.ClayModelRotationEventHandler;
import de.sanandrew.mods.claysoldiers.client.renderer.RenderClaySoldier;
import de.sanandrew.mods.claysoldiers.client.renderer.soldier.LayerGoggles;
import de.sanandrew.mods.claysoldiers.client.renderer.soldier.LayerLeatherArmor;
import de.sanandrew.mods.claysoldiers.client.renderer.soldier.LayerMagmaCreamCharge;
import de.sanandrew.mods.claysoldiers.client.renderer.soldier.LayerSoldierHeldItem;
import de.sanandrew.mods.claysoldiers.client.renderer.soldier.RenderHookBody;
import de.sanandrew.mods.claysoldiers.client.renderer.soldier.RenderHookMainHandItem;
import de.sanandrew.mods.claysoldiers.client.renderer.soldier.RenderHookOffHandItem;
import de.sanandrew.mods.claysoldiers.event.SoldierTargetEnemyEventHandler;
import de.sanandrew.mods.claysoldiers.registry.TeamRegistry;
import de.sanandrew.mods.claysoldiers.registry.effect.EffectRegistry;
import de.sanandrew.mods.claysoldiers.registry.upgrade.UpgradeRegistry;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@CsmPlugin
public class CsmInternalPlugin
        implements ICsmPlugin
{
    @Override
    public void registerTeams(ITeamRegistry registry) {
        TeamRegistry.initialize(registry);
    }

    @Override
    public void registerUpgrades(IUpgradeRegistry registry) {
        UpgradeRegistry.initialize(registry);
    }

    @Override
    public void registerEffects(IEffectRegistry registry) {
        EffectRegistry.initialize(registry);
    }

    @Override
    public void registerCsmEvents(EventBus bus) {
        bus.register(new SoldierTargetEnemyEventHandler());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerCsmClientEvents(EventBus bus) {
        bus.register(new ClayModelRotationEventHandler());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerSoldierRenderLayer(RenderBiped<?> renderer) {
        RenderClaySoldier rSoldier = (RenderClaySoldier) renderer;

        renderer.addLayer(new LayerSoldierHeldItem(rSoldier));
        renderer.addLayer(new LayerGoggles(rSoldier));
        renderer.addLayer(new LayerLeatherArmor(rSoldier));
        renderer.addLayer(new LayerMagmaCreamCharge(rSoldier));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerSoldierRenderHook(IRenderHookRegistry registry) {
        registry.registerSoldierHook(new RenderHookMainHandItem(0));
        registry.registerSoldierHook(new RenderHookMainHandItem(1));
        registry.registerSoldierHook(new RenderHookOffHandItem(0));
        registry.registerSoldierHook(new RenderHookBody());
    }
}
