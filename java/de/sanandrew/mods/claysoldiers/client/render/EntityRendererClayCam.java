/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.render;

import de.sanandrew.mods.claysoldiers.client.util.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.resources.IResourceManager;

public class EntityRendererClayCam
        extends EntityRenderer
{
    private final Minecraft mc;

    public EntityRendererClayCam(Minecraft minecraft, IResourceManager resManager) {
        super(minecraft, resManager);
        this.mc = minecraft;
    }

    @Override
    public void updateCameraAndRender(float partialTick) {
        if( this.mc.thePlayer == null || this.mc.thePlayer.isPlayerSleeping() || ClientProxy.clayCamEntity == null ){
            super.updateCameraAndRender(partialTick);
            return;
        }

        // save original offset of the player
        float prevOffset = this.mc.thePlayer.yOffset;

        // set the camera yOffset to match soldier's eye height
        this.mc.thePlayer.yOffset -= 0.55F;

        // set the player's position clinet-side to the position of the clayman observed
        this.mc.thePlayer.lastTickPosX = ClientProxy.clayCamEntity.lastTickPosX;
        this.mc.thePlayer.prevPosX = ClientProxy.clayCamEntity.prevPosX;
        this.mc.thePlayer.posX = ClientProxy.clayCamEntity.posX;
        this.mc.thePlayer.lastTickPosY = ClientProxy.clayCamEntity.lastTickPosY;
        this.mc.thePlayer.prevPosY = ClientProxy.clayCamEntity.prevPosY;
        this.mc.thePlayer.posY = ClientProxy.clayCamEntity.posY;
        this.mc.thePlayer.lastTickPosZ = ClientProxy.clayCamEntity.lastTickPosZ;
        this.mc.thePlayer.prevPosZ = ClientProxy.clayCamEntity.prevPosZ;
        this.mc.thePlayer.posZ = ClientProxy.clayCamEntity.posZ;

        // set the camera rotation values to the ones of the clayman observed
        this.mc.thePlayer.rotationPitch = ClientProxy.clayCamEntity.rotationPitch;
        this.mc.thePlayer.rotationYaw = ClientProxy.clayCamEntity.rotationYaw;

        // do parent call here, baby...
        super.updateCameraAndRender(partialTick);

        // reset the players yOffset
        this.mc.thePlayer.yOffset = prevOffset;
    }

    @Override
    public void getMouseOver(float partialTick) {
        if( this.mc.thePlayer == null || this.mc.thePlayer.isPlayerSleeping() ){
            super.getMouseOver(partialTick);
            return;
        }

        // no mouse-over (thus no block-destruction) while in ClayCam
    }
}
