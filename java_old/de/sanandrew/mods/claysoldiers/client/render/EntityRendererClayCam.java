/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.mods.claysoldiers.client.util.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.resources.IResourceManager;

@SideOnly(Side.CLIENT)
public class EntityRendererClayCam
        extends EntityRenderer
{
    private final Minecraft p_mc;

    public EntityRendererClayCam(Minecraft minecraft, IResourceManager resManager) {
        super(minecraft, resManager);
        this.p_mc = minecraft;
    }

    @Override
    public void updateCameraAndRender(float partialTick) {
        if( this.p_mc.thePlayer == null || this.p_mc.thePlayer.isPlayerSleeping() || ClientProxy.s_clayCamEntity == null ){
            super.updateCameraAndRender(partialTick);
            return;
        }

        if( ClientProxy.s_clayCamEntity.isDead ) {
            super.updateCameraAndRender(partialTick);
            return;
        }

        // save original offset of the player
        float prevOffset = this.p_mc.thePlayer.yOffset;

        // set the camera yOffset to match soldier's eye height
        this.p_mc.thePlayer.yOffset -= 0.55F;

        // set the player's position clinet-side to the position of the clayman observed
        this.p_mc.thePlayer.lastTickPosX = ClientProxy.s_clayCamEntity.lastTickPosX;
        this.p_mc.thePlayer.prevPosX = ClientProxy.s_clayCamEntity.prevPosX;
        this.p_mc.thePlayer.posX = ClientProxy.s_clayCamEntity.posX;
        this.p_mc.thePlayer.lastTickPosY = ClientProxy.s_clayCamEntity.lastTickPosY;
        this.p_mc.thePlayer.prevPosY = ClientProxy.s_clayCamEntity.prevPosY;
        this.p_mc.thePlayer.posY = ClientProxy.s_clayCamEntity.posY;
        this.p_mc.thePlayer.lastTickPosZ = ClientProxy.s_clayCamEntity.lastTickPosZ;
        this.p_mc.thePlayer.prevPosZ = ClientProxy.s_clayCamEntity.prevPosZ;
        this.p_mc.thePlayer.posZ = ClientProxy.s_clayCamEntity.posZ;

        // set the camera rotation values to the ones of the clayman observed
        this.p_mc.thePlayer.rotationPitch = ClientProxy.s_clayCamEntity.rotationPitch + 20.0F;
        this.p_mc.thePlayer.rotationYaw = ClientProxy.s_clayCamEntity.rotationYaw;
        this.p_mc.thePlayer.prevRotationPitch = ClientProxy.s_clayCamEntity.prevRotationPitch + 20.0F;
        this.p_mc.thePlayer.prevRotationYaw = ClientProxy.s_clayCamEntity.prevRotationYaw;

        // do parent call here, baby...
        super.updateCameraAndRender(partialTick);

        // reset the players yOffset
        this.p_mc.thePlayer.yOffset = prevOffset;
    }

    @Override
    public void getMouseOver(float partialTick) {
        if( this.p_mc.thePlayer == null || this.p_mc.thePlayer.isPlayerSleeping() ){
            super.getMouseOver(partialTick);
        }

        // no mouse-over (thus no block-destruction) while in ClayCam
    }
}
