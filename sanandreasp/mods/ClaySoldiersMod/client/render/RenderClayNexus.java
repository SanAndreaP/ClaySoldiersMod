/*******************************************************************************************************************
 * Name:      RenderClayNexus.java
 * Author:    SanAndreasP
 * Copyright: SanAndreasP and SilverChiren
 * License:   Attribution-NonCommercial-ShareAlike 3.0 Unported (http://creativecommons.org/licenses/by-nc-sa/3.0/)
 *******************************************************************************************************************/

package sanandreasp.mods.ClaySoldiersMod.client.render;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.src.*;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import sanandreasp.mods.ClaySoldiersMod.client.model.ModelClayNexus;
import sanandreasp.mods.ClaySoldiersMod.entity.EntityClayNexus;
import sanandreasp.mods.ClaySoldiersMod.registry.Textures;

public class RenderClayNexus extends RenderLiving implements Textures {

	ModelClayNexus mc1;
	
	public RenderClayNexus(ModelBase par1ModelBase, float par2) {
		super(par1ModelBase, par2);
		mc1 = (ModelClayNexus)par1ModelBase;
		setRenderPassModel(mc1);
	}

	@Override
	public void doRender(Entity par1Entity, double par2, double par4,
			double par6, float par8, float par9) {
		mc1.isActive = ((EntityClayNexus)par1Entity).isActive();
		mc1.isSpawnable = ((EntityClayNexus)par1Entity).getColor() >= 0;
		
		super.doRender(par1Entity, par2, par4, par6, par8, par9);
	}
	

    protected int setCubeBrightness(EntityClayNexus par1EntityClayNexus, int par2, float par3)
    {
        if (par2 != 0)
        {
            return -1;
        }
        else
        {
			this.bindTexture(NEXUS_LGHT);
            float f = 1.0F;
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
            int i = 61680;
            int j = i % 0x10000;
            int k = i / 0x10000;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, f);
            return 1;
        }
    }
    
    /**
     * Queries whether should render the specified pass or not.
     */
    @Override
	protected int shouldRenderPass(EntityLivingBase par1EntityLiving, int par2, float par3)
    {
        return setCubeBrightness((EntityClayNexus)par1EntityLiving, par2, par3);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return NEXUS_NORM;
    }
    
    
}
