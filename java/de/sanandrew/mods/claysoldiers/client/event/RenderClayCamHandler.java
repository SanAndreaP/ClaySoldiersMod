/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.mods.claysoldiers.client.render.EntityRendererClayCam;
import de.sanandrew.mods.claysoldiers.client.util.ClientProxy;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;

@SideOnly(Side.CLIENT)
public class RenderClayCamHandler
{
    private EntityRendererClayCam p_clayCamRenderer;
    private EntityRenderer p_prevEntityRenderer;
    private int viewMode = -1;

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        Minecraft mc = Minecraft.getMinecraft();

        if( event.phase == Phase.START ) {
            if( mc.thePlayer != null && ClientProxy.s_clayCamEntity != null ) {
                if( this.viewMode == -1 ) {
                    this.viewMode = mc.gameSettings.thirdPersonView;
                }
                mc.gameSettings.thirdPersonView = 1;
                if( this.p_clayCamRenderer == null ) {
                    this.p_clayCamRenderer = new EntityRendererClayCam(mc, mc.getResourceManager());
                }

                if( mc.entityRenderer != this.p_clayCamRenderer ) {
                    // be sure to store the previous renderer
                    this.p_prevEntityRenderer = mc.entityRenderer;
                    mc.entityRenderer = this.p_clayCamRenderer;
                }

                if( mc.thePlayer.isSneaking() || ClientProxy.s_clayCamEntity.isDead ) {
                    mc.gameSettings.thirdPersonView = this.viewMode;
                    this.viewMode = -1;
                    ClaySoldiersMod.proxy.switchClayCam(false, null);
                }
            } else if( this.p_prevEntityRenderer != null && mc.entityRenderer != this.p_prevEntityRenderer ) {
                // reset the renderer
                mc.entityRenderer = this.p_prevEntityRenderer;
            }
        } else if( ClientProxy.s_clayCamEntity != null ) {
            mc.gameSettings.thirdPersonView = this.viewMode;
        }
    }

    @SubscribeEvent
    public void getFovMulti(FOVUpdateEvent event) {
//        System.out.println();
        if( Minecraft.getMinecraft().entityRenderer == this.p_clayCamRenderer ) {
            event.newfov = 0.5F;
        }
    }

    @SubscribeEvent
    public void renderPlayerPre(RenderPlayerEvent.Pre event) {
        if( ClientProxy.s_clayCamEntity != null ) {
            event.setCanceled(true);
        }
    }
}
