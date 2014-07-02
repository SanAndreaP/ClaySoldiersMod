/*******************************************************************************************************************
 * Name:      ModelClayMan.java
 * Author:    SanAndreasP
 * Copyright: SanAndreasP and SilverChiren
 * License:   Attribution-NonCommercial-ShareAlike 3.0 Unported (http://creativecommons.org/licenses/by-nc-sa/3.0/)
 *******************************************************************************************************************/

package sanandreasp.mods.ClaySoldiersMod.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.src.*;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

public class ModelClayMan extends ModelBiped
{

    public ModelClayMan()
    {
        this(0.0F);
    }

    public ModelClayMan(float f)
    {
        this(f, 0.0F);
    }

    public ModelClayMan(float f, float f1)
    {
		bipedCape = new ModelRenderer(this, 41, 0);
        bipedCape.addBox(-2.5F, -0.5F, -0.25F, 5, 7, 1, f - 0.15F);
		bipedCape.setRotationPoint(0.0F, 0.0F + f1, 1.65F);
	
        bipedHead = new ModelRenderer(this, 0, 0);
        bipedHead.addBox(-1.5F, -3F, -1.5F, 3, 3, 3, f);
        bipedHead.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
		
        bipedHeadwear = new ModelRenderer(this, 19, 16); //Crown
        bipedHeadwear.addBox(-1.5F, -4F, -1.5F, 3, 2, 3, f + 0.3F);
        bipedHeadwear.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
		
        bipedBody = new ModelRenderer(this, 15, 0);
        bipedBody.addBox(-2F, 0.0F, -1F, 4, 5, 2, f);
        bipedBody.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
		
        bipedRightArm = new ModelRenderer(this, 9, 7);
        bipedRightArm.addBox(-1F, -1F, -1F, 2, 6, 2, f);
        bipedRightArm.setRotationPoint(-3F, 1.0F + f1, 0.0F);
		
        bipedLeftArm = new ModelRenderer(this, 9, 7);
        bipedLeftArm.mirror = true;
        bipedLeftArm.addBox(-1F, -1F, -1F, 2, 6, 2, f);
        bipedLeftArm.setRotationPoint(3F, 1.0F + f1, 0.0F);
		
        bipedRightLeg = new ModelRenderer(this, 0, 7);
        bipedRightLeg.addBox(-1F, 0.0F, -1F, 2, 6, 2, f);
        bipedRightLeg.setRotationPoint(-1F, 5F + f1, 0.0F);
		
        bipedLeftLeg = new ModelRenderer(this, 0, 7);
        bipedLeftLeg.mirror = true;
        bipedLeftLeg.addBox(-1F, 0.0F, -1F, 2, 6, 2, f);
        bipedLeftLeg.setRotationPoint(1.0F, 5F + f1, 0.0F);
		
		//Weapon
		stick = new ModelRenderer(this, 31, 11);
        stick.addBox(-0.5F, 3.5F, -4F, 1, 1, 3, f);
        stick.setRotationPoint(-3F, 1.0F + f1, 0.0F);
		
		stickBlunt = new ModelRenderer(this, 32, 12);
        stickBlunt.addBox(-0.5F, 3.5F, -6F, 1, 1, 2, f);
        stickBlunt.setRotationPoint(-3F, 1.0F + f1, 0.0F);
		
		stickSharp = new ModelRenderer(this, 9, 0);
        stickSharp.addBox(-0.5F, 3.5F, -5.5F, 1, 1, 2, f - 0.2F);
        stickSharp.setRotationPoint(-3F, 1.0F + f1, 0.0F);
		
		//Armor
		bipedChest = new ModelRenderer(this, 0, 21);
        bipedChest.addBox(-2F, 0.0F, -1F, 4, 4, 2, f + 0.3F);
        bipedChest.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
		
        bipedRightArmor = new ModelRenderer(this, 0, 16);
        bipedRightArmor.addBox(-1F, -1F, -1F, 2, 2, 2, f + 0.4F);
        bipedRightArmor.setRotationPoint(-3F, 1.0F + f1, 0.0F);
		
        bipedLeftArmor = new ModelRenderer(this, 0, 16);
        bipedLeftArmor.mirror = true;
        bipedLeftArmor.addBox(-1F, -1F, -1F, 2, 2, 2, f + 0.4F);
        bipedLeftArmor.setRotationPoint(3F, 1.0F + f1, 0.0F);
		
        RightLegArmor = new ModelRenderer(this, 56, 0);
        RightLegArmor.addBox(-1F, 0.0F, -1F, 2, 5, 2, f + 0.1F);
        RightLegArmor.setRotationPoint(-1F, 5F + f1, 0.0F);
		
        LeftLegArmor = new ModelRenderer(this, 56, 0);
        LeftLegArmor.mirror = true;
        LeftLegArmor.addBox(-1F, 0.0F, -1F, 2, 5, 2, f + 0.1F);
        LeftLegArmor.setRotationPoint(1.0F, 5F + f1, 0.0F);
		
        bipedChest2 = new ModelRenderer(this, 52, 7);
        bipedChest2.addBox(-2F, 0.0F, -1F, 4, 5, 2, f+0.1F);
        bipedChest2.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
		
		//Padding
		bipedPadding = new ModelRenderer(this, 12, 21);
        bipedPadding.addBox(-2F, 2.9F, -1F, 4, 2, 2, f + 0.2F);
        bipedPadding.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
		
        bipedRightPadding = new ModelRenderer(this, 9, 16);
        bipedRightPadding.addBox(-1F, -0.1F, -1F, 2, 2, 2, f + 0.3F);
        bipedRightPadding.setRotationPoint(-3F, 1.0F + f1, 0.0F);
		
        bipedLeftPadding = new ModelRenderer(this, 9, 16);
        bipedLeftPadding.mirror = true;
        bipedLeftPadding.addBox(-1F, -0.1F, -1F, 2, 2, 2, f + 0.3F);
        bipedLeftPadding.setRotationPoint(3F, 1.0F + f1, 0.0F);
		
		//Specky
		speckyHead = new ModelRenderer(this, 37, 17);
        speckyHead.addBox(-1.5F, -3F, -1.5F, 3, 3, 3, f + 0.05F);
        speckyHead.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
		
        speckyBody = new ModelRenderer(this, 52, 17);
        speckyBody.addBox(-2F, 0.0F, -1F, 4, 5, 2, f + 0.05F);
        speckyBody.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
		
        speckyRightArm = new ModelRenderer(this, 37, 24);
        speckyRightArm.addBox(-1F, -1F, -1F, 2, 6, 2, f + 0.05F);
        speckyRightArm.setRotationPoint(-3F, 1.0F + f1, 0.0F);
		
        speckyLeftArm = new ModelRenderer(this, 46, 24);
        speckyLeftArm.mirror = true;
        speckyLeftArm.addBox(-1F, -1F, -1F, 2, 6, 2, f + 0.05F);
        speckyLeftArm.setRotationPoint(3F, 1.0F + f1, 0.0F);
		
        speckyRightLeg = new ModelRenderer(this, 46, 24);
        speckyRightLeg.addBox(-1F, 0.0F, -1F, 2, 6, 2, f + 0.05F);
        speckyRightLeg.setRotationPoint(-1F, 5F + f1, 0.0F);
		
        speckyLeftLeg = new ModelRenderer(this, 37, 24);
        speckyLeftLeg.mirror = true;
        speckyLeftLeg.addBox(-1F, 0.0F, -1F, 2, 6, 2, f + 0.05F);
        speckyLeftLeg.setRotationPoint(1.0F, 5F + f1, 0.0F);
		
		//Goo
		gooBase = new ModelRenderer(this, 0, 27);
        gooBase.addBox(-2.5F, 9.0F, -1.5F, 5, 2, 3, f);
        gooBase.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
		
		//Log
		logBase = new ModelRenderer(this, 16, 26);
        logBase.addBox(-2.5F, -6.5F, -1.5F, 5, 3, 3, f);
        logBase.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
		
		//Rocks
		bipedRock = new ModelRenderer(this, 31, 3);
        bipedRock.mirror = true;
        bipedRock.addBox(-1F, 3.5F, -1F, 2, 2, 2, f + 0.375F);
        bipedRock.setRotationPoint(3F, 1.0F + f1, 0.0F);
		
		//goggles
		bipedGoggle = new ModelRenderer(this, 17, 7);
        bipedGoggle.addBox(-1.35F, -2F, -1.75F, 1, 1, 1, f + 0.15F);
        bipedGoggle.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
		
		bipedGoggle2 = new ModelRenderer(this, 17, 9);
        bipedGoggle2.addBox(0.35F, -2F, -1.75F, 1, 1, 1, f + 0.15F);
        bipedGoggle2.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
		
		bipedStrap = new ModelRenderer(this, 17, 11);
        bipedStrap.addBox(-1.5F, -2F, -1.5F, 3, 1, 3, f + 0.07F);
        bipedStrap.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
    }

    @Override
	public void render(Entity e, float f, float f1, float f2, float f3, float f4, float f5)
    {
        setRotationAngles(f, f1, f2, f3, f4, f5, e);
        bipedHead.render(f5);
        bipedBody.render(f5);
        bipedRightArm.render(f5);
        bipedLeftArm.render(f5);
        bipedRightLeg.render(f5);
        bipedLeftLeg.render(f5);
		
		if (hasCrown) {
			GL11.glPushMatrix();
			GL11.glColor3f(1F, 1F, 0F);
			bipedHeadwear.render(f5);
			GL11.glColor3f(1F, 1F, 1F);
			GL11.glPopMatrix();
		}
		
		if (isSuper && !hasCrown) {
			GL11.glPushMatrix();
			GL11.glColor3f(0.4F, 1F, 1F);
			bipedHeadwear.render(f5);
			GL11.glColor3f(1F, 1F, 1F);
			GL11.glPopMatrix();
		}
		
//		if (hasStick) {
//			stick.render(f5);
//			if (isSharpened) {
//				stickSharp.render(f5);
//			} else {
//				stickBlunt.render(f5);
//			}
//		}
		
		if (hasArmor) {
			bipedChest.render(f5);
			bipedRightArmor.render(f5);
			bipedLeftArmor.render(f5);
			if (isPadded) {
				GL11.glPushMatrix();
				GL11.glColor3f(colorPadded[0], colorPadded[1], colorPadded[2]);
				bipedPadding.render(f5);
				bipedRightPadding.render(f5);
				bipedLeftPadding.render(f5);
				GL11.glColor3f(1F, 1F, 1F);
				GL11.glPopMatrix();
			}
		}
		
		if (hasPants) {
			RightLegArmor.render(f5);
			LeftLegArmor.render(f5);
			bipedChest2.render(f5);
		}
		
		if (hasSpecks) {
			speckyHead.render(f5);
			speckyBody.render(f5);
			speckyRightArm.render(f5);
			speckyLeftArm.render(f5);
			speckyRightLeg.render(f5);
			speckyLeftLeg.render(f5);
		}
		
		if (isGooey) {
			gooBase.render(f5);
		}
		
		if (hasLogs) {
			logBase.render(f5);
		}
		
		if (hasRocks) {
			bipedRock.render(f5);
		}
		
		if (hasGoggles) {
			bipedGoggle.render(f5);
			bipedGoggle2.render(f5);
			bipedStrap.render(f5);
		}
		
		if (isSuper || isCaped) {
			bipedCape.rotateAngleX = -capeSwing * 1.25F;
			bipedCape.rotateAngleY = 3.141593F;
			GL11.glPushMatrix();
			if (isSuper) {
				GL11.glColor3f(0.4F, 1F, 1F);
			} else {
				GL11.glColor3f(capePadded[0], capePadded[1], capePadded[2]);
			}
			bipedCape.render(f5);
			GL11.glColor3f(1F, 1F, 1F);
			GL11.glPopMatrix();
		}
    }

    @Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {
        bipedHead.rotateAngleY = f3 / 57.29578F;
        bipedHead.rotateAngleX = f4 / 57.29578F;
        bipedHeadwear.rotateAngleY = bipedHead.rotateAngleY;
        bipedHeadwear.rotateAngleX = bipedHead.rotateAngleX;
		bipedRightArm.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 2.0F * f1 * 0.5F;
		bipedLeftArm.rotateAngleX = MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F;
		bipedRightArm.rotateAngleZ = 0.0F;
		bipedLeftArm.rotateAngleZ = 0.0F;
        bipedRightLeg.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        bipedLeftLeg.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1;
        bipedRightLeg.rotateAngleY = 0.0F;
        bipedLeftLeg.rotateAngleY = 0.0F;
        if (isRiding)
        {
            bipedRightArm.rotateAngleX += -0.6283185F;
            bipedLeftArm.rotateAngleX += -0.6283185F;
            bipedRightLeg.rotateAngleX = -1.256637F;
            bipedLeftLeg.rotateAngleX = -1.256637F;
            bipedRightLeg.rotateAngleY = 0.3141593F;
            bipedLeftLeg.rotateAngleY = -0.3141593F;
        }
        if (heldItemLeft != 0)
        {
            bipedLeftArm.rotateAngleX = bipedLeftArm.rotateAngleX * 0.5F - 0.3141593F;
        }
        if (heldItemRight != 0)
        {
            bipedRightArm.rotateAngleX = bipedRightArm.rotateAngleX * 0.5F - 0.3141593F;
        }
        bipedRightArm.rotateAngleY = 0.0F;
        bipedLeftArm.rotateAngleY = 0.0F;
		
        if (onGround > -9990F)
        {
            float f6 = onGround;
            bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt_float(f6) * 3.141593F * 2.0F) * 0.2F;
            bipedRightArm.rotateAngleY += bipedBody.rotateAngleY;
            bipedLeftArm.rotateAngleY += bipedBody.rotateAngleY;
            bipedLeftArm.rotateAngleX += bipedBody.rotateAngleY;
            f6 = 1.0F - onGround;
            f6 *= f6;
            f6 *= f6;
            f6 = 1.0F - f6;
            float f7 = MathHelper.sin(f6 * 3.141593F);
            float f8 = MathHelper.sin(onGround * 3.141593F) * -(bipedHead.rotateAngleX - 0.7F) * 0.75F;
            bipedRightArm.rotateAngleX -= f7 * 1.2D + f8;
            bipedRightArm.rotateAngleY += bipedBody.rotateAngleY * 2.0F;
            bipedRightArm.rotateAngleZ = MathHelper.sin(onGround * 3.141593F) * -0.4F;
        }

        bipedRightArm.rotateAngleZ += MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
        bipedLeftArm.rotateAngleZ -= MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
        bipedRightArm.rotateAngleX += MathHelper.sin(f2 * 0.067F) * 0.05F;
        bipedLeftArm.rotateAngleX -= MathHelper.sin(f2 * 0.067F) * 0.05F;
		
		if (isClimbing) {
			bipedRightArm.rotateAngleX -= 1.58F;
			bipedLeftArm.rotateAngleX -= 1.58F;
			bipedRightLeg.rotateAngleX -= 0.7F;
			bipedLeftLeg.rotateAngleX -= 0.7F;
			bipedHead.rotateAngleX -= 0.6F;
			bipedHeadwear.rotateAngleX -= 0.6F;
		} else if (hasLogs || holdFeather) {
			bipedRightArm.rotateAngleX = 3.141593F;
			bipedLeftArm.rotateAngleX = 3.141593F;
		} else if (armLeft > 0.0F) {
			float f6 = -4F + (armLeft * 4F);
			float f7 = 1.0F - armLeft;
            bipedLeftArm.rotateAngleX = f6;
            bipedLeftArm.rotateAngleZ = f7;
        }
		
		//Customs
		bipedStrap.rotateAngleX = bipedGoggle2.rotateAngleX = bipedGoggle.rotateAngleX = speckyHead.rotateAngleX = bipedHead.rotateAngleX;
		bipedStrap.rotateAngleY = bipedGoggle2.rotateAngleY = bipedGoggle.rotateAngleY = speckyHead.rotateAngleY = bipedHead.rotateAngleY;
		
		 bipedChest2.rotateAngleY = bipedPadding.rotateAngleY = bipedChest.rotateAngleY = speckyBody.rotateAngleY = bipedBody.rotateAngleY;
		
//		stickBlaze.rotateAngleX = 
		stickBlunt.rotateAngleX = stickSharp.rotateAngleX = stick.rotateAngleX = bipedRightPadding.rotateAngleX = bipedRightArmor.rotateAngleX = speckyRightArm.rotateAngleX = bipedRightArm.rotateAngleX;
//		stickBlaze.rotateAngleY = 
		stickBlunt.rotateAngleY = stickSharp.rotateAngleY = stick.rotateAngleY = bipedRightPadding.rotateAngleY = bipedRightArmor.rotateAngleY = speckyRightArm.rotateAngleY = bipedRightArm.rotateAngleY;
//		stickBlaze.rotateAngleZ = 
		stickBlunt.rotateAngleZ = stickSharp.rotateAngleZ = stick.rotateAngleZ = bipedRightPadding.rotateAngleZ = bipedRightArmor.rotateAngleZ = speckyRightArm.rotateAngleZ = bipedRightArm.rotateAngleZ;
		
		bipedRock.rotateAngleX = bipedLeftPadding.rotateAngleX = bipedLeftArmor.rotateAngleX = speckyLeftArm.rotateAngleX = bipedLeftArm.rotateAngleX;
		bipedRock.rotateAngleY = bipedLeftPadding.rotateAngleY = bipedLeftArmor.rotateAngleY = speckyLeftArm.rotateAngleY = bipedLeftArm.rotateAngleY;
		bipedRock.rotateAngleZ = bipedLeftPadding.rotateAngleZ = bipedLeftArmor.rotateAngleZ = speckyLeftArm.rotateAngleZ = bipedLeftArm.rotateAngleZ;
		
		RightLegArmor.rotateAngleX = speckyRightLeg.rotateAngleX = bipedRightLeg.rotateAngleX;
		RightLegArmor.rotateAngleY = speckyRightLeg.rotateAngleY = bipedRightLeg.rotateAngleY;
		RightLegArmor.rotateAngleZ = speckyRightLeg.rotateAngleZ = bipedRightLeg.rotateAngleZ;
		
		LeftLegArmor.rotateAngleX = speckyLeftLeg.rotateAngleX = bipedLeftLeg.rotateAngleX;
		LeftLegArmor.rotateAngleY = speckyLeftLeg.rotateAngleY = bipedLeftLeg.rotateAngleY;
		LeftLegArmor.rotateAngleZ = speckyLeftLeg.rotateAngleZ = bipedLeftLeg.rotateAngleZ;
				
		switch(sittingPos) {
			case 1: 
				bipedLeftLeg.rotateAngleX = -(float) Math.PI/2.2F;
				bipedRightLeg.rotateAngleX = -(float) Math.PI/2.2F;
				LeftLegArmor.rotateAngleX = -(float) Math.PI/2.2F;
				RightLegArmor.rotateAngleX = -(float) Math.PI/2.2F;
				bipedLeftLeg.rotateAngleY = -0.2F;
				bipedRightLeg.rotateAngleY = 0.2F;
				LeftLegArmor.rotateAngleY = -0.2F;
				RightLegArmor.rotateAngleY = 0.2F;
				
				bipedBody.rotationPointY = 16F;
				bipedHead.rotationPointY = 16F;
				bipedLeftArm.rotationPointY = 17F;
				bipedRightArm.rotationPointY = 17F;
				bipedLeftLeg.rotationPointY = 21F;
				bipedRightLeg.rotationPointY = 21F;
				stick.rotationPointY = 17F;
				stickBlunt.rotationPointY = 17F;
				stickSharp.rotationPointY = 17F;
			    bipedRightArmor.rotationPointY = 17F;
			    bipedLeftArmor.rotationPointY = 17F;
			    bipedChest.rotationPointY = 16F;
			    bipedChest2.rotationPointY = 16F;
				RightLegArmor.rotationPointY = 21F;
				LeftLegArmor.rotationPointY = 21F;
				return;
			case 2: return;
			case 3: return;
			default: 
				bipedBody.rotationPointY = 13F;
				bipedHead.rotationPointY = 13F;
				bipedLeftArm.rotationPointY = 14F;
				bipedRightArm.rotationPointY = 14F;
				bipedLeftLeg.rotationPointY = 17F;
				bipedRightLeg.rotationPointY = 17F;
				stick.rotationPointY = 14F;
				stickBlunt.rotationPointY = 14F;
				stickSharp.rotationPointY = 14F;
			    bipedRightArmor.rotationPointY = 14F;
			    bipedLeftArmor.rotationPointY = 14F;
			    bipedChest.rotationPointY = 13F;
			    bipedChest2.rotationPointY = 13F;
				RightLegArmor.rotationPointY = 18F;
				LeftLegArmor.rotationPointY = 18F;
				return;
		}
    }

    public ModelRenderer bipedRightArmor;
    public ModelRenderer bipedLeftArmor;
    public ModelRenderer bipedChest;
    public ModelRenderer bipedChest2;
	public ModelRenderer RightLegArmor;
	public ModelRenderer LeftLegArmor;
	
	public ModelRenderer bipedRightPadding;
    public ModelRenderer bipedLeftPadding;
    public ModelRenderer bipedPadding;
	
	public ModelRenderer gooBase;
	public ModelRenderer logBase;
	public ModelRenderer bipedRock;
	public ModelRenderer bipedGoggle, bipedGoggle2, bipedStrap;
	public ModelRenderer bipedCape;
	
	public ModelRenderer speckyHead;
	public ModelRenderer speckyBody;
	public ModelRenderer speckyRightArm;
	public ModelRenderer speckyLeftArm;
	public ModelRenderer speckyRightLeg;
	public ModelRenderer speckyLeftLeg;
	
	public ModelRenderer stick;
	public ModelRenderer stickBlunt;
	public ModelRenderer stickSharp;
	
	public float armLeft, capeSwing;
	public boolean hasStick, hasArmor, hasCrown, hasSpecks, isClimbing, isSharpened, isPadded, isGooey, hasLogs, holdFeather, hasRocks, hasGoggles, isSuper, hasBlazeRod, isCaped, hasPants;
	public int sittingPos = 0;
	public float colorPadded[] = new float[] {1.0F, 1.0F, 1.0F};
	public float capePadded[] = new float[] {1.0F, 1.0F, 1.0F};
}
