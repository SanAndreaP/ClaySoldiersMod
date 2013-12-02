/*******************************************************************************************************************
 * Name:      RenderTurtle.java
 * Author:    SanAndreasP
 * Copyright: SanAndreasP and SilverChiren
 * License:   Attribution-NonCommercial-ShareAlike 3.0 Unported (http://creativecommons.org/licenses/by-nc-sa/3.0/)
 *******************************************************************************************************************/

package sanandreasp.mods.ClaySoldiersMod.client.render;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.src.*;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import sanandreasp.mods.ClaySoldiersMod.entity.mount.EntityGecko;
import sanandreasp.mods.ClaySoldiersMod.entity.mount.EntityTurtle;
import sanandreasp.mods.ClaySoldiersMod.registry.Textures;

public class RenderTurtle extends RenderBiped implements Textures {

    public RenderTurtle(ModelBiped model, float f) {
        super(model, f);
    }
	
	@Override
	protected void preRenderCallback(EntityLivingBase entityliving, float f) {
		GL11.glScalef(1F, 1F, 1F);
    }
	
	@Override
	public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2, float f, float f1) {
		f1 *= 2F;
		super.doRenderLiving(entityliving, d, d1, d2, f, f1);
    }
	
    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        if( entity instanceof EntityTurtle ) {
            EntityTurtle turtle = (EntityTurtle)entity;
            if( turtle.isKawako() )
                return TURTLE_KAWAKO;
            
            return TURTLE[turtle.getType()][turtle.getAltTex()];
        }
        return TURTLE[0][0];
    }
	
}