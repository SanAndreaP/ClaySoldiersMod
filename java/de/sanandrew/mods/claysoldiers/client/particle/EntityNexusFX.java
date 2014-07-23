/*******************************************************************************************************************
 * Authors:   Azanor, Vazkii, SanAndreasP
 * Copyright: SanAndreasP, SilverChiren, CliffracerX, Vazkii and Azanor
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 * Notes:     This class was created by <Azanor>. It was distributed as
 *            part of the Botania Mod by <Vazkii>, now it's part of the CLay Soldiers Mod.
 *            Botania github: https://github.com/Vazkii/Botania
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.particle;

import cpw.mods.fml.client.FMLClientHandler;
import de.sanandrew.mods.claysoldiers.client.util.Textures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import java.util.ArrayDeque;
import java.util.Queue;

public class EntityNexusFX extends EntityFX
{
    public static Queue<EntityNexusFX> queuedRenders = new ArrayDeque<>();

    // Queue values
    float f;
    float f1;
    float f2;
    float f3;
    float f4;
    float f5;

    public EntityNexusFX(World world, double x, double y, double z, float size, float red, float green, float blue, boolean distanceLimit, float maxAgeMul) {
        super(world, x, y, z, 0.0D, 0.0D, 0.0D);
        particleRed = red;
        particleGreen = green;
        particleBlue = blue;
        particleGravity = 0;
        motionX = motionY = motionZ = 0;
        particleScale *= size;
        moteParticleScale = particleScale;
        particleMaxAge = (int)(28D / (Math.random() * 0.3D + 0.7D) * maxAgeMul);

        moteHalfLife = particleMaxAge / 2;
        noClip = true;
        setSize(0.01F, 0.01F);
        EntityLivingBase renderentity = FMLClientHandler.instance().getClient().renderViewEntity;

        if(distanceLimit) {
            int visibleDistance = 50;
            if (!FMLClientHandler.instance().getClient().gameSettings.fancyGraphics)
                visibleDistance = 25;

            if (renderentity == null || renderentity.getDistance(posX, posY, posZ) > visibleDistance)
                particleMaxAge = 0;
        }

        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
    }

    public static void dispatchQueuedRenders(Tessellator tessellator) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
        Minecraft.getMinecraft().renderEngine.bindTexture(Textures.NEXUS_PARTICLE);

        tessellator.startDrawingQuads();
        for(EntityNexusFX wisp : queuedRenders)
            wisp.renderQueued(tessellator);
        tessellator.draw();

        queuedRenders.clear();
    }

    private void renderQueued(Tessellator tessellator) {
        float agescale = 0;
        agescale = (float)particleAge / (float) moteHalfLife;
        if (agescale > 1F)
            agescale = 2 - agescale;

        particleScale = moteParticleScale * agescale;

        float f10 = 0.5F * particleScale;
        float f11 = (float)(prevPosX + (posX - prevPosX) * f - interpPosX);
        float f12 = (float)(prevPosY + (posY - prevPosY) * f - interpPosY);
        float f13 = (float)(prevPosZ + (posZ - prevPosZ) * f - interpPosZ);

        tessellator.setBrightness(240);
        tessellator.setColorRGBA_F(particleRed, particleGreen, particleBlue, 0.5F);
        tessellator.addVertexWithUV(f11 - f1 * f10 - f4 * f10, f12 - f2 * f10, f13 - f3 * f10 - f5 * f10, 0, 1);
        tessellator.addVertexWithUV(f11 - f1 * f10 + f4 * f10, f12 + f2 * f10, f13 - f3 * f10 + f5 * f10, 1, 1);
        tessellator.addVertexWithUV(f11 + f1 * f10 + f4 * f10, f12 + f2 * f10, f13 + f3 * f10 + f5 * f10, 1, 0);
        tessellator.addVertexWithUV(f11 + f1 * f10 - f4 * f10, f12 - f2 * f10, f13 + f3 * f10 - f5 * f10, 0, 0);
    }

    @Override
    public void renderParticle(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5) {
        this.f = f;
        this.f1 = f1;
        this.f2 = f2;
        this.f3 = f3;
        this.f4 = f4;
        this.f5 = f5;

        queuedRenders.add(this);
    }

    @Override
    public void onUpdate() {
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;

        if (particleAge++ >= particleMaxAge)
            setDead();

        motionY -= 0.04D * particleGravity;
        posX += motionX;
        posY += motionY;
        posZ += motionZ;
        motionX *= 0.98000001907348633D;
        motionY *= 0.98000001907348633D;
        motionZ *= 0.98000001907348633D;
    }

    public void setGravity(float value) {
        particleGravity = value;
    }

    public boolean distanceLimit = true;
    float moteParticleScale;
    int moteHalfLife;
    public boolean tinkle = false;
    public int blendmode = 1;
}
