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
import de.sanandrew.mods.claysoldiers.client.render.EntityRendererClayCam;
import de.sanandrew.mods.claysoldiers.client.util.ClientProxy;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraftforge.client.event.RenderPlayerEvent;

public class RenderTickHandler
{
//    public float eyeHeight;
//    public float ySize = 0.4F;

    private EntityRendererClayCam clayCamRenderer;
    private EntityRenderer prevEntityRenderer;
    double prevPosX;
    double prevPosY;
    double prevPosZ;

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        Minecraft mc = Minecraft.getMinecraft();

        if( event.phase == Phase.START ) {
            if( mc.thePlayer != null && ClientProxy.clayCamEntity != null ) {
                if( this.clayCamRenderer == null ) {
                    this.clayCamRenderer = new EntityRendererClayCam(mc, mc.getResourceManager());
                }

                if( mc.entityRenderer != this.clayCamRenderer ) {
                    // be sure to store the previous renderer
                    this.prevEntityRenderer = mc.entityRenderer;
                    mc.entityRenderer = this.clayCamRenderer;
                }

//                prevPosX = mc.thePlayer.posX;
//                prevPosY = mc.thePlayer.posY;
//                prevPosZ = mc.thePlayer.posZ;

//                double d0 = ClientProxy.clayCamEntity.prevPosX + (ClientProxy.clayCamEntity.posX - ClientProxy.clayCamEntity.prevPosX) * (double)event.renderTickTime;
//                double d1 = ClientProxy.clayCamEntity.prevPosY + (ClientProxy.clayCamEntity.posY - ClientProxy.clayCamEntity.prevPosY) * (double)event.renderTickTime;
//                double d2 = ClientProxy.clayCamEntity.prevPosZ + (ClientProxy.clayCamEntity.posZ - ClientProxy.clayCamEntity.prevPosZ) * (double)event.renderTickTime;
//
//                mc.thePlayer.posX = d0;
//                mc.thePlayer.posY = d1;
//                mc.thePlayer.posZ = d2;

//            mc.thePlayer.prevPosX = ClientProxy.clayCamEntity.prevPosX;
//            mc.thePlayer.lastTickPosX = ClientProxy.clayCamEntity.lastTickPosX;
//                mc.thePlayer.posX = ClientProxy.clayCamEntity.prevPosX;

//            mc.thePlayer.prevPosY = ClientProxy.clayCamEntity.prevPosY;
//            mc.thePlayer.lastTickPosY = ClientProxy.clayCamEntity.lastTickPosY;
//                mc.thePlayer.posY = ClientProxy.clayCamEntity.prevPosY;

//            mc.thePlayer.prevPosZ = ClientProxy.clayCamEntity.prevPosZ;
//            mc.thePlayer.lastTickPosZ = ClientProxy.clayCamEntity.lastTickPosZ;
//                mc.thePlayer.posZ = ClientProxy.clayCamEntity.prevPosZ;

                if( mc.thePlayer.isSneaking() ) {
                    ClaySoldiersMod.proxy.switchClayCam(false, null);
                }
            } else if( this.prevEntityRenderer != null && mc.entityRenderer != this.prevEntityRenderer ) {
                // reset the renderer
                mc.entityRenderer = this.prevEntityRenderer;

//                mc.thePlayer.posX = prevPosX;
//                mc.thePlayer.posY = prevPosY;
//                mc.thePlayer.posZ = prevPosZ;
            }
        }
    }

    @SubscribeEvent
    public void renderPlayerPre(RenderPlayerEvent.Pre event) {
        if( ClientProxy.clayCamEntity != null ) {
            event.setCanceled(true);
        }
    }
}
