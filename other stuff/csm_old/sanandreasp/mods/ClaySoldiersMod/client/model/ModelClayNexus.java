/*******************************************************************************************************************
 * Name:      ModelClayNexus.java
 * Author:    SanAndreasP
 * Copyright: SanAndreasP and SilverChiren
 * License:   Attribution-NonCommercial-ShareAlike 3.0 Unported (http://creativecommons.org/licenses/by-nc-sa/3.0/)
 *******************************************************************************************************************/

package sanandreasp.mods.ClaySoldiersMod.client.model;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.src.*;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import sanandreasp.mods.ClaySoldiersMod.entity.EntityClayNexus;

public class ModelClayNexus extends ModelBase
{
    ModelRenderer base;
    ModelRenderer orb;
    ModelRenderer arm1;
    ModelRenderer arm2;
    ModelRenderer arm3;
    ModelRenderer arm4;
    Random rand = new Random();
    
    public boolean isActive = false;
    public boolean isSpawnable = false;
  
	public ModelClayNexus() {
		textureWidth = 64;
		textureHeight = 32;

		base = new ModelRenderer(this, 0, 0);
		base.addBox(-3F, -1F, -3F, 6, 2, 6);
		base.setRotationPoint(0F, 23F, 0F);
		base.setTextureSize(64, 32);
		base.mirror = true;
		setRotation(base, 0F, 0F, 0F);
		orb = new ModelRenderer(this, 18, 8);
		orb.addBox(-1F, -1F, -1F, 2, 2, 2);
		orb.setRotationPoint(0F, 19F, 0F);
		orb.setTextureSize(64, 32);
		orb.mirror = true;
		setRotation(orb, 0.7853982F, -0.7853982F, 1.570796F);
		arm1 = new ModelRenderer(this, 0, 8);
		arm1.addBox(-2F, 0F, 0F, 5, 0, 4);
		arm1.setRotationPoint(0F, 19F, 0F);
		arm1.setTextureSize(64, 32);
		arm1.mirror = true;
		setRotation(arm1, 0F, 0F, 1.570796F);
		arm2 = new ModelRenderer(this, 0, 8);
		arm2.addBox(-2F, 0F, 0F, 5, 0, 4);
		arm2.setRotationPoint(0F, 19F, 0F);
		arm2.setTextureSize(64, 32);
		arm2.mirror = true;
		setRotation(arm2, 1.570796F, 0.0F, 1.570796F);
		arm3 = new ModelRenderer(this, 0, 8);
		arm3.addBox(-2F, 0F, 0F, 5, 0, 4);
		arm3.setRotationPoint(0F, 19F, 0F);
		arm3.setTextureSize(64, 32);
		arm3.mirror = true;
		setRotation(arm3, 3.141592F, 0F, 1.570796F);
		arm4 = new ModelRenderer(this, 0, 8);
		arm4.addBox(-2F, 0F, 0F, 5, 0, 4);
		arm4.setRotationPoint(0F, 19F, 0F);
		arm4.setTextureSize(64, 32);
		arm4.mirror = true;
		setRotation(arm4, -1.570796F, 0F, 1.570796F);
  	}
  
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
	    super.render(entity, f, f1, f2, f3, f4, f5);
	    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	    base.render(f5);
	    arm1.render(f5);
	    arm2.render(f5);
	    arm3.render(f5);
	    arm4.render(f5);
    
    	if (isSpawnable) {
	    	if (isActive) {
			    orb.rotateAngleX = ((EntityClayNexus)entity).innerRotation / 12F;
			    orb.rotateAngleZ = ((EntityClayNexus)entity).innerRotation / 7F;
			    orb.rotateAngleY = ((EntityClayNexus)entity).innerRotation / 10.5F;
			    
			    orb.rotationPointY = (float) (19F + Math.sin(((EntityClayNexus)entity).innerRotation / 15F));
	    	}
	    
	    	float[] color = ((EntityClayNexus)entity).getTeamColor();
	    	GL11.glColor3f(color[0], color[1], color[2]);
	    	orb.render(f5);
	    	GL11.glColor3f(1F, 1F, 1F);
		}
	}
  
	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
  
	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e) { }

}
