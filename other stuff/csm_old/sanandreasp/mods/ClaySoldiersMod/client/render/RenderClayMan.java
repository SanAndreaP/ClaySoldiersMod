/*******************************************************************************************************************
 * Name:      RenderClayMan.java
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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.*;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import sanandreasp.mods.ClaySoldiersMod.client.model.ModelClayMan;
import sanandreasp.mods.ClaySoldiersMod.entity.EntityClayMan;
import sanandreasp.mods.ClaySoldiersMod.registry.CSMModRegistry;
import sanandreasp.mods.ClaySoldiersMod.registry.Textures;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.IUpgradeItem;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.misc.UpgLeather;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.rightHand.RightHandUpgrade;

public class RenderClayMan extends RenderBiped
{
    public ModelClayMan mc1;

    public RenderClayMan(ModelBiped model, float f) {
        super(model, f);
		mc1 = (ModelClayMan)model;
		setRenderPassModel(model);
    }

	@Override
	protected void preRenderCallback(EntityLivingBase entityliving, float f) {
        EntityClayMan c1 = (EntityClayMan)entityliving;
        mc1.hasArmor = c1.hasUpgrade(CSMModRegistry.clayUpgRegistry.getIDByUpgradeClass(UpgLeather.class));
//		mc1.hasStick = c1.hasStick();
//		mc1.hasBlazeRod = c1.hasBlazeRod();
//		mc1.hasSpecks = c1.hasGunPowder();
//		mc1.hasCrown = c1.hasCrown();
//		if (mc1.isPadded = c1.isPadded())
//			mc1.colorPadded = EntityClayMan.padColorTable[c1.getPadColor()];
//
//		if (mc1.isCaped = c1.isCaped()) {
//			mc1.capeSwing = c1.capeSwing();
//			mc1.capePadded = EntityClayMan.padColorTable[c1.getCapeColor()];
//		}
//		mc1.sittingPos = c1.sittingPos;
//		mc1.isSharpened = c1.isStickSharp();
//		mc1.isGooey = c1.isGooey();
//		mc1.hasLogs = c1.hasLogs();
//		mc1.holdFeather = c1.holdFeather();
//		mc1.hasRocks = c1.hasRocks();
//		mc1.hasGoggles = c1.hasGoggles();
//		mc1.armLeft = c1.armLeft();
//		mc1.isSuper = c1.isSuper();
//		mc1.hasPants = c1.hasPants();
//		if (mc1.isSuper) {
//			mc1.capeSwing = c1.capeSwing();
//		}

		boolean flag = false;
		if (c1.isOnLadder()) {
//			c1.climbTime ++;
			flag = true;
		}

		mc1.isClimbing = flag;

		GL11.glScalef(0.6F, 0.6F, 0.6F);
//		if (c1.isCorrupt() && !c1.isGlowing()) {
//			int i, j, k;
//			i = MathHelper.floor_double(c1.posX);
//			j = MathHelper.floor_double(c1.posY);
//			k = MathHelper.floor_double(c1.posZ);
//			float lightness = c1.worldObj.getFullBlockLightValue(i, j, k);
//			if (c1.hurtTime > 0 || c1.deathTime > 0) {
//				GL11.glColor3f((lightness / 20F) + 0.5F, 0.2F, 0.2F);
//			} else {
//				GL11.glColor3f(lightness / 35F, lightness / 35F, lightness / 35F);
//			}
//		}

//		if (c1.holdFeather()) {
//			ItemStack itemstack = new ItemStack(Item.feather.itemID, 1, 0);
//			GL11.glPushMatrix();
//
//                float f5 = 0.625F;
//
//				GL11.glTranslatef(0F, -1.0F, 0F);
//                GL11.glScalef(f5, f5, f5);
//
//				GL11.glTranslatef(0.625F, 0.1F, -0.4F);
//				GL11.glRotatef(90F, 0F, 0F, 1F);
//				GL11.glRotatef(45F, 0F, 1F, 0F);
//
//            renderManager.itemRenderer.renderItem(entityliving, itemstack, 0);
//            GL11.glPopMatrix();
//		}
    }

    @Override
	protected void renderEquippedItems(EntityLivingBase entityliving, float f) {
        renderEquipped((EntityClayMan)entityliving, f);
    }

	protected void renderEquipped(EntityClayMan entitycm, float f) {
    	super.renderEquippedItems(entitycm, f);

//    	ItemStack rightIS = null;
//    	ItemStack leftIS = null;



//        if (entitycm.hasBlazeRod()) {
//	        rightIS = new ItemStack(Item.blazeRod);
//        }
//        if (entitycm.hasLeftShear()) {
//	        leftIS = new ItemStack(CSMModRegistry.shearBlade);
//        }
//        if (entitycm.hasRightShear() && !entitycm.hasBone() && !entitycm.hasBlazeRod() && !entitycm.hasStick()) {
//	        rightIS = new ItemStack(CSMModRegistry.shearBlade);
//        }
//        if (entitycm.hasGlister()) {
//	        rightIS = new ItemStack(Item.speckledMelon);
//        }
//		if (entitycm.hasBone()) {
//			rightIS = new ItemStack(Item.bone);
//		}
//        if (entitycm.hasGoo()) {
//        	rightIS = new ItemStack(Item.slimeBall);
//        }
//        if (entitycm.hasSmoke()) {
//        	rightIS = new ItemStack(Item.redstone);
//        }
//        if (entitycm.hasSnowballs()) {
//	        leftIS = new ItemStack(Item.snowball);
//        }
//        if (entitycm.hasFireballs()) {
//        	leftIS = new ItemStack(Item.fireballCharge);
//        }

//    	ItemStack rightIs = null;
    	for( int id : entitycm.getUpgrades() )
    		CSMModRegistry.clayUpgRegistry.getUpgradeByID(id).onRenderEquipped(entitycm, this.renderManager, f, this.mc1);
//    		if( upg instanceof RightHandUpgrade ) {
//    			rightIs = upg.getHeldItem();
//    			break;
//    		}

//    	if( rightIs != null ) {
//            GL11.glPushMatrix();
//            this.mc1.rightLegFront.postRender(0.0625F);
//            GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);
//
//            float f4 = 0.175F;
//            GL11.glTranslatef(0.05F, -0.15F, -0.08F);
//            GL11.glScalef(f4+0.2F, f4, f4);
//            GL11.glRotatef(140F, 0.0F, 0.0F, 1.0F);
//            GL11.glRotatef(-90F, 1.0F, 0.0F, 0.0F);
//            GL11.glRotatef(0F, 0.0F, 0.0F, 1.0F);
//
//            this.renderManager.itemRenderer.renderItem(entitycm, rightIs, 0);
//            GL11.glPopMatrix();
//        }

//        if (leftIS != null)
//        {
//            GL11.glPushMatrix();
//            mc1.leftLegFront.postRender(0.0625F);
//            GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);
//
//                float f4 = 0.175F;
//                GL11.glTranslatef(0.05F, -0.15F, -0.08F);
//                GL11.glScalef(f4+0.2F, f4, f4);
//                GL11.glRotatef(140F, 0.0F, 0.0F, 1.0F);
//                GL11.glRotatef(-90F, 1.0F, 0.0F, 0.0F);
//                GL11.glRotatef(0F, 0.0F, 0.0F, 1.0F);
//
//            renderManager.itemRenderer.renderItem(entitycm, leftIS, 0);
//            GL11.glPopMatrix();
//        }


//        if (entitycm.hasTimeBomb()) {
//        	ItemStack itemstack = new ItemStack(Item.magmaCream);
//        	if (itemstack != null)
//        	{
//        		GL11.glPushMatrix();
//        		mc1.bipedBody.postRender(0.0625F);
//        		GL11.glTranslatef(-0.0625F, 0.4375F, -0.0525F);
//        		float f4 = 0.175F;
//        		GL11.glTranslatef(0.05F, -0.15F, -0.08F);
//        		GL11.glScalef(f4, f4, f4);
//        		GL11.glRotatef(100F, 0.0F, 0.0F, 1.0F);
//        		GL11.glRotatef(40F, 1.0F, 0.0F, 0.0F);
//        		GL11.glRotatef(80F, 0.0F, 0.0F, 1.0F);
//
//        		renderManager.itemRenderer.renderItem(entitycm, itemstack, 0);
//        		GL11.glPopMatrix();
//        	}
//        }
//        if (entitycm.hasShield()) {
//	        ItemStack itemstack = new ItemStack(CSMModRegistry.shield, 1, entitycm.hasEESkin() ? 2 : 0);
//	        if (entitycm.isShieldStud()) { itemstack = new ItemStack(CSMModRegistry.shield, 1, entitycm.hasEESkin() ? 3 : 1); }
//	        if (itemstack != null)
//	        {
//	            GL11.glPushMatrix();
//	            mc1.leftLegFront.postRender(0.0625F);
//	            GL11.glTranslatef(-0.0625F, 0.4375F, -0.0525F);
//	                float f4 = 0.175F;
//	                GL11.glTranslatef(0.05F, -0.15F, -0.08F);
//	                GL11.glScalef(f4, f4, f4);
//	                GL11.glRotatef(100F, 0.0F, 0.0F, 1.0F);
//	                GL11.glRotatef(40F, 1.0F, 0.0F, 0.0F);
//	                GL11.glRotatef(80F, 0.0F, 0.0F, 1.0F);
//
//	            renderManager.itemRenderer.renderItem(entitycm, itemstack, 0);
//	            GL11.glPopMatrix();
//	        }
//        }
    }

	@Override
	public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2, float f, float f1) {
		f1 *= 2F;
		EntityClayMan entitycm = (EntityClayMan)entityliving;

		GL11.glPushMatrix();
		for( int id : entitycm.getUpgrades() )
            CSMModRegistry.clayUpgRegistry.getUpgradeByID(id).onPreRender(entitycm, this.renderManager, f, this.mc1);

		super.doRenderLiving(entityliving, d, d1, d2, f, f1);

        for( int id : entitycm.getUpgrades() )
            CSMModRegistry.clayUpgRegistry.getUpgradeByID(id).onPostRender(entitycm, this.renderManager, f, this.mc1);
        GL11.glPopMatrix();
    }

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
	    if( par1Entity instanceof EntityClayMan ) {
	        EntityClayMan clayMan = (EntityClayMan)par1Entity;
	        if( clayMan.getUniqTexture() >= 0 ) {
	            return Textures.CLAYMAN[2][clayMan.getClayTeam()][clayMan.getUniqTexture()];
	        } else if( clayMan.getRareTexture() >= 0 ) {
                return Textures.CLAYMAN[1][clayMan.getClayTeam()][clayMan.getRareTexture()];
            } else {
                return Textures.CLAYMAN[0][clayMan.getClayTeam()][clayMan.getClayTexture()];
            }
	    }
	    return super.getEntityTexture(par1Entity);
	}
}
