package sanandreasp.mods.ClaySoldiersMod.client.render;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import sanandreasp.mods.ClaySoldiersMod.client.model.ModelClayMan;
import sanandreasp.mods.ClaySoldiersMod.entity.EntityClayMan;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.IUpgradeEntity;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.leftHand.LeftHandUpgrade;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.misc.UpgEggScent;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.misc.UpgGlowstone;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.rightHand.RightHandUpgrade;

@SideOnly(Side.CLIENT)
public class UpgradeRenderHelper {
    
// ------------------------------- RIGHT HAND ITEMS ------------------------------- //
    
    public static void onRightItemRender(RightHandUpgrade upg, RenderManager manager, IUpgradeEntity entity, float partTicks, ModelBase model) {
        if( entity instanceof EntityClayMan && model instanceof ModelClayMan ) {
            EntityClayMan kootra = (EntityClayMan)entity;
            ModelClayMan corpse = (ModelClayMan)model;

            GL11.glPushMatrix();
            corpse.bipedRightArm.postRender(0.0625F);
            GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);
            float scale = 0.175F;
            GL11.glTranslatef(0.05F, -0.15F, -0.08F);
            GL11.glScalef(scale+0.2F, scale, scale);
            GL11.glRotatef(140F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(-90F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(0F, 0.0F, 0.0F, 1.0F);
                  
            manager.itemRenderer.renderItem(kootra, upg.getHeldItem(entity), 0);
            GL11.glPopMatrix();
        }
    }

// ------------------------------- LEFT HAND ITEMS ------------------------------- //
    
    public static void onLeftItemRender(LeftHandUpgrade upg, RenderManager manager, IUpgradeEntity entity, float partTicks, ModelBase model) {
        if( entity instanceof EntityClayMan && model instanceof ModelClayMan ) {
            EntityClayMan kootra = (EntityClayMan)entity;
            ModelClayMan corpse = (ModelClayMan)model;

            GL11.glPushMatrix();
            corpse.bipedLeftArm.postRender(0.0625F);
            GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);
            float scale = 0.175F;
            GL11.glTranslatef(0.05F, -0.15F, -0.08F);
            GL11.glScalef(scale+0.2F, scale, scale);
            GL11.glRotatef(140F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(-90F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(0F, 0.0F, 0.0F, 1.0F);
                  
            manager.itemRenderer.renderItem(kootra, upg.getHeldItem(entity), 0);
            GL11.glPopMatrix();
        }
    }
    
// ------------------------------- SHIELD ------------------------------- //
    
    public static void onShieldRender(LeftHandUpgrade upg, RenderManager manager, IUpgradeEntity entity, float partTicks, ModelBase model) {
        if( entity instanceof EntityClayMan && model instanceof ModelClayMan ) {
            EntityClayMan kootra = (EntityClayMan)entity;
            ModelClayMan corpse = (ModelClayMan)model;
            
            GL11.glPushMatrix();
            corpse.bipedLeftArm.postRender(0.0625F);
            GL11.glTranslatef(-0.0625F, 0.4375F, -0.0525F);
            float scale = 0.175F;
            GL11.glTranslatef(0.05F, -0.15F, -0.08F);
            GL11.glScalef(scale, scale, scale);
            GL11.glRotatef(100F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(40F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(80F, 0.0F, 0.0F, 1.0F);
              
            manager.itemRenderer.renderItem(kootra, upg.getHeldItem(entity), 0);
            GL11.glPopMatrix();
        }
    }
    
 // ------------------------------- EGG SCENT ITEMS ------------------------------- //
    
    public static void onEggPreRender(UpgEggScent upg, RenderManager manager, IUpgradeEntity entity, float partTicks, ModelBase model) {
        GL11.glEnable(GL11.GL_BLEND);
        float transparency = 0.5F;
        GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_SRC_ALPHA);
        GL11.glColor4f(0.5F, 0.5F, 0.5F, transparency);
    }
    
    public static void onEggPostRender(UpgEggScent upg, RenderManager manager, IUpgradeEntity entity, float partTicks, ModelBase model) {
        GL11.glDisable(GL11.GL_BLEND);
    }
    
 // ------------------------------- GLOWSTONE ------------------------------- //

    public static void onGlowPreRender(UpgGlowstone upg, RenderManager manager, IUpgradeEntity entity, float partTicks, ModelBase model) {
//        GL11.glEnable(GL11.GL_BLEND);
//        GL11.glDisable(GL11.GL_ALPHA_TEST);
//        GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
        char var5 = 0x000F0;
        int var6 = var5 % 65536;
        int var7 = var5 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var6 / 1.0F, var7 / 1.0F);
//        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
//        GL11.glDepthMask(false);
//        GL11.glEnable(GL11.GL_BLEND);
//        float transparency = 0.5F;
//        GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_SRC_ALPHA);
//        GL11.glColor4f(0.5F, 0.5F, 0.5F, transparency);
    }
    
    public static void onGlowPostRender(UpgGlowstone upg, RenderManager manager, IUpgradeEntity entity, float partTicks, ModelBase model) {
        int var5 = entity.getEntity().getBrightnessForRender(0x0000F0);
        int var6 = var5 % 65536;
        int var7 = var5 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var6 / 1.0F, var7 / 1.0F);
//        GL11.glDepthMask(true);
//        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
