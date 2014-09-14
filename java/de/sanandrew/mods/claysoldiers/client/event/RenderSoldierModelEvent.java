/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import de.sanandrew.mods.claysoldiers.client.event.SoldierRenderEvent.RenderModelEvent;
import de.sanandrew.mods.claysoldiers.client.event.SoldierRenderEvent.SetRotationAnglesEvent;
import de.sanandrew.mods.claysoldiers.client.render.entity.RenderClayMan;
import de.sanandrew.mods.claysoldiers.client.util.Textures;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.soldier.effect.SoldierEffects;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgrades;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class RenderSoldierModelEvent
{
    public ModelRenderer buffedBody;
    public ModelRenderer armorBody;
    public ModelRenderer armorRightArm;
    public ModelRenderer armorLeftArm;
    public ModelRenderer slimeRightLeg;
    public ModelRenderer slimeLeftLeg;
    public ModelRenderer crown;
    public ModelRenderer lilypantsRightLeg;
    public ModelRenderer lilypantsLeftLeg;
    public ModelRenderer lilypantsBody;
    public ModelRenderer glassStripes;

    public boolean isInitialized = false;

    @SubscribeEvent
    public void onSoldierRotationAngles(SetRotationAnglesEvent event) {
        if( event.clayMan.hasUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_FEATHER)) && !event.clayMan.onGround && event.clayMan.motionY < -0.1D
                && event.clayMan.fallDistance >= 1.3F ) {
            event.model.bipedLeftArm.rotateAngleX = (float) Math.PI;
            event.model.bipedRightArm.rotateAngleX = (float) Math.PI;
        }

        if( event.clayMan.hasUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_ENDERPEARL)) ) {
            event.model.bipedLeftArm.rotateAngleX = -(float) Math.PI * 0.5F;
            event.model.bipedRightArm.rotateAngleX = -(float) Math.PI * 0.5F;
        }
    }

    @SubscribeEvent
    public void onSoldierRenderModel(RenderModelEvent event) {
        if( !this.isInitialized ) {
            this.isInitialized = true;
            this.initRenderer(event.clayManRender);
        }

        if( event.clayMan.hasUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_GOLD_INGOT)) ) {
            this.renderGoldHoodie(event.clayMan, event.clayManRender, event.limbSwing, event.limbSwingAmount, event.rotFloat, event.yaw, event.pitch, event.partTicks);
        }

        if( event.clayMan.hasUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_GUNPOWDER)) ) {
            this.renderGunpowder(event.clayMan, event.clayManRender, event.limbSwing, event.limbSwingAmount, event.rotFloat, event.yaw, event.pitch, event.partTicks);
        }

        if( event.clayMan.hasUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_MAGMACREAM)) ) {
            this.renderMagmacream(event.clayMan, event.clayManRender, event.limbSwing, event.limbSwingAmount, event.rotFloat, event.yaw, event.pitch, event.partTicks);
        }

        if( event.clayMan.hasUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_DIAMOND_ITEM))
                || event.clayMan.hasUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_DIAMOND_BLOCK)) )
        {
            this.renderCape(event.clayMan, event.clayManRender, event.partTicks, true);
            this.renderCrown(event.clayManRender, event.partTicks, true);
        } else {
            if( event.clayMan.hasUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_PAPER)) ) {
                this.renderCape(event.clayMan, event.clayManRender, event.partTicks, false);
            }

            if( event.clayMan.hasUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_GOLD_NUGGET)) ) {
                this.renderCrown(event.clayManRender, event.partTicks, false);
            }
        }

        if( event.clayMan.hasUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_IRON_INGOT)) ) {
            this.renderIronCoreBuff(event.clayMan, event.clayManRender, event.partTicks);
        }

        if( event.clayMan.hasUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_LILYPADS)) ) {
            this.renderLilyPants(event.clayManRender, event.partTicks);
        }

        if( event.clayMan.hasUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_LEATHER)) ) {
            this.renderHideArmor(event.clayMan, event.clayManRender, event.partTicks, SoldierUpgrades.UPG_LEATHER);
        }

        if( event.clayMan.hasUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_GLASS)) ) {
            this.renderGoggleStripes(event.clayManRender, event.partTicks);
        }

        if( event.clayMan.hasEffect(SoldierEffects.getEffect(SoldierEffects.EFF_SLIMEFEET)) ) {
            this.renderSlimefeet(event.clayManRender, event.partTicks);
        }
    }

    public void initRenderer(RenderClayMan renderClayMan) {
        this.buffedBody = new ModelRenderer(renderClayMan.modelBipedMain, 16, 16);
        this.buffedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 6, 4, 0.0F);
        this.buffedBody.setRotationPoint(0.0F, -0.4F, 0.0F);

        this.armorBody = new ModelRenderer(renderClayMan.modelBipedMain, 16, 16);
        this.armorBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F);
        this.armorBody.setRotationPoint(0.0F, -0.4F, 0.0F);
        this.armorRightArm = new ModelRenderer(renderClayMan.modelBipedMain, 40, 16);
        this.armorRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.armorRightArm.setRotationPoint(-5.0F, 1.6F, 0.0F);
        this.armorLeftArm = new ModelRenderer(renderClayMan.modelBipedMain, 40, 16);
        this.armorLeftArm.mirror = true;
        this.armorLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.armorLeftArm.setRotationPoint(5.0F, 1.6F, 0.0F);

        this.slimeRightLeg = new ModelRenderer(renderClayMan.modelBipedMain, 0, 24);
        this.slimeRightLeg.addBox(-2.0F, 8.0F, -2.0F, 4, 4, 4, 0.0F);
        this.slimeRightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
        this.slimeLeftLeg = new ModelRenderer(renderClayMan.modelBipedMain, 0, 24);
        this.slimeLeftLeg.mirror = true;
        this.slimeLeftLeg.addBox(-2.0F, 8.0F, -2.0F, 4, 4, 4, 0.0F);
        this.slimeLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);

        this.crown = new ModelRenderer(renderClayMan.modelBipedMain, 0, 0);
        this.crown.addBox(-4.0F, -9.5F, -4.0F, 8, 8, 8, 0.5F);
        this.crown.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.glassStripes = new ModelRenderer(renderClayMan.modelBipedMain, 0, 0);
        this.glassStripes.addBox(-4.0F, -7.35F, -4.0F, 8, 8, 8, 0.01F);
        this.glassStripes.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.lilypantsBody = new ModelRenderer(renderClayMan.modelBipedMain, 16, 16);
        this.lilypantsBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.25F);
        this.lilypantsBody.setRotationPoint(0.0F, -0.4F, 0.0F);
        this.lilypantsRightLeg = new ModelRenderer(renderClayMan.modelBipedMain, 0, 16);
        this.lilypantsRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.25F);
        this.lilypantsRightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
        this.lilypantsLeftLeg = new ModelRenderer(renderClayMan.modelBipedMain, 0, 16);
        this.lilypantsLeftLeg.mirror = true;
        this.lilypantsLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.25F);
        this.lilypantsLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
    }

    private void renderGoldHoodie(EntityClayMan clayMan, RenderClayMan clayManRender, float limbSwing, float limbSwingAmount, float rotFloat, float yaw, float pitch,
                                  float partTicks) {
        clayManRender.bindTexture(Textures.CLAYMAN_GOLD_HOODIE);
        GL11.glPushMatrix();
        clayManRender.modelBipedMain.render(clayMan, limbSwing, limbSwingAmount, rotFloat, yaw, pitch, partTicks);
        GL11.glPopMatrix();
    }

    private void renderGunpowder(EntityClayMan clayMan, RenderClayMan clayManRender, float limbSwing, float limbSwingAmount, float rotFloat, float yaw, float pitch,
                                 float partTicks) {
        clayManRender.bindTexture(Textures.CLAYMAN_GUNPOWDER);
        GL11.glPushMatrix();
        clayManRender.modelBipedMain.render(clayMan, limbSwing, limbSwingAmount, rotFloat, yaw, pitch, partTicks);
        GL11.glPopMatrix();
    }

    private void renderMagmacream(EntityClayMan clayMan, RenderClayMan clayManRender, float limbSwing, float limbSwingAmount, float rotFloat, float yaw, float pitch,
                                  float partTicks) {
        clayManRender.bindTexture(Textures.CLAYMAN_MAGMACREAM);
        GL11.glPushMatrix();
        clayManRender.modelBipedMain.render(clayMan, limbSwing, limbSwingAmount, rotFloat, yaw, pitch, partTicks);
        GL11.glPopMatrix();
    }

    private void renderCape(EntityClayMan clayMan, RenderClayMan clayManRender, float partTicks, boolean isSuper) {
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, 0.0F, 0.175F);

        double d3 = clayMan.cloakHelper.prevSwingPosX + (clayMan.cloakHelper.swingPosX - clayMan.cloakHelper.prevSwingPosX) * (double) partTicks - (clayMan.prevPosX + (clayMan.posX - clayMan.prevPosX) * (double) partTicks);
        double d4 = clayMan.cloakHelper.prevSwingPosY + (clayMan.cloakHelper.swingPosY - clayMan.cloakHelper.prevSwingPosY) * (double) partTicks - (clayMan.prevPosY + (clayMan.posY - clayMan.prevPosY) * (double) partTicks);
        double d0 = clayMan.cloakHelper.prevSwingPosZ + (clayMan.cloakHelper.swingPosZ - clayMan.cloakHelper.prevSwingPosZ) * (double) partTicks - (clayMan.prevPosZ + (clayMan.posZ - clayMan.prevPosZ) * (double) partTicks);
        float f4 = clayMan.prevRenderYawOffset + (clayMan.renderYawOffset - clayMan.prevRenderYawOffset) * partTicks;
        double d1 = (double) MathHelper.sin(f4 * (float) Math.PI / 180.0F);
        double d2 = (double) (-MathHelper.cos(f4 * (float) Math.PI / 180.0F));
        float f5 = (float) d4 * 10.0F;

        if( f5 < -6.0F ) {
            f5 = -6.0F;
        }

        if( f5 > 32.0F ) {
            f5 = 32.0F;
        }

        float f6 = (float) (d3 * d1 + d0 * d2) * 100.0F;
        float f7 = (float) (d3 * d2 - d0 * d1) * 100.0F;

        if( f6 < 0.0F ) {
            f6 = 0.0F;
        }

        GL11.glRotatef(6.0F + f6 / 2.0F + f5, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(f7 / 2.0F, 0.0F, 0.0F, 1.0F);
        GL11.glRotatef(-f7 / 2.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);

        if( isSuper ) {
            clayManRender.bindTexture(Textures.CLAYMAN_CAPE_DIAMOND);
        } else {
            float[] color = this.getSplittedColor(clayMan.getMiscColor());
            GL11.glColor3f(color[0], color[1], color[2]);
            clayManRender.bindTexture(Textures.CLAYMAN_CAPE_BLANK);
        }

        clayManRender.modelBipedMain.bipedCloak.render(0.0625F);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
    }

    private void renderCrown(RenderClayMan clayManRender, float partTicks, boolean isSuper) {
        ModelBiped model = clayManRender.modelBipedMain;

        this.crown.rotateAngleX = model.bipedHead.rotateAngleX;
        this.crown.rotateAngleY = model.bipedHead.rotateAngleY;
        this.crown.rotateAngleZ = model.bipedHead.rotateAngleZ;

        clayManRender.bindTexture(Textures.CLAYMAN_CROWN);
        if( isSuper ) {
            GL11.glColor3f(0.39F, 0.82F, 0.742F);
        } else {
            GL11.glColor3f(1.0F, 0.9F, 0.0F);
        }
        this.crown.render(partTicks);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
    }

    private void renderIronCoreBuff(EntityClayMan clayMan, RenderClayMan clayManRender, float partTicks) {
        ModelBiped model = clayManRender.modelBipedMain;

        this.buffedBody.rotateAngleX = model.bipedBody.rotateAngleX;
        this.buffedBody.rotateAngleY = model.bipedBody.rotateAngleY;
        this.buffedBody.rotateAngleZ = model.bipedBody.rotateAngleZ;

        clayManRender.bindTexture(clayMan.getTexture());
        GL11.glPushMatrix();
        GL11.glScalef(1.5F, 1.5F, 1.5F);
        this.buffedBody.render(partTicks);
        GL11.glPopMatrix();
    }

    private void renderLilyPants(RenderClayMan clayManRender, float partTicks) {
        ModelBiped model = clayManRender.modelBipedMain;

        this.lilypantsBody.rotateAngleX = model.bipedBody.rotateAngleX;
        this.lilypantsBody.rotateAngleY = model.bipedBody.rotateAngleY;
        this.lilypantsBody.rotateAngleZ = model.bipedBody.rotateAngleZ;
        this.lilypantsLeftLeg.rotateAngleX = model.bipedLeftLeg.rotateAngleX;
        this.lilypantsLeftLeg.rotateAngleY = model.bipedLeftLeg.rotateAngleY;
        this.lilypantsLeftLeg.rotateAngleZ = model.bipedLeftLeg.rotateAngleZ;
        this.lilypantsRightLeg.rotateAngleX = model.bipedRightLeg.rotateAngleX;
        this.lilypantsRightLeg.rotateAngleY = model.bipedRightLeg.rotateAngleY;
        this.lilypantsRightLeg.rotateAngleZ = model.bipedRightLeg.rotateAngleZ;

        clayManRender.bindTexture(Textures.CLAYMAN_LILYPANTS);
        this.lilypantsBody.render(partTicks);
        this.lilypantsLeftLeg.render(partTicks);
        this.lilypantsRightLeg.render(partTicks);
    }

    private void renderHideArmor(EntityClayMan clayMan, RenderClayMan clayManRender, float partTicks, String armorUpgrade) {
        ModelBiped model = clayManRender.modelBipedMain;

        this.armorBody.rotateAngleX = model.bipedBody.rotateAngleX;
        this.armorBody.rotateAngleY = model.bipedBody.rotateAngleY;
        this.armorBody.rotateAngleZ = model.bipedBody.rotateAngleZ;
        this.armorLeftArm.rotateAngleX = model.bipedLeftArm.rotateAngleX;
        this.armorLeftArm.rotateAngleY = model.bipedLeftArm.rotateAngleY;
        this.armorLeftArm.rotateAngleZ = model.bipedLeftArm.rotateAngleZ;
        this.armorRightArm.rotateAngleX = model.bipedRightArm.rotateAngleX;
        this.armorRightArm.rotateAngleY = model.bipedRightArm.rotateAngleY;
        this.armorRightArm.rotateAngleZ = model.bipedRightArm.rotateAngleZ;

        switch( armorUpgrade ) {
            case SoldierUpgrades.UPG_LEATHER:
                clayManRender.bindTexture(Textures.CLAYMAN_LEATHER_ARMOR);
                break;
            case "not_implemented_rabbit_hide":                                 //todo: implement rabbit hide texture when 1.8 arrives
                clayManRender.bindTexture(Textures.CLAYMAN_LEATHER_ARMOR);
                break;
            default:
                return;
        }

        GL11.glPushMatrix();
        GL11.glScalef(1.2F, 1.2F, 1.2F);
        this.armorBody.render(partTicks);
        this.armorLeftArm.render(partTicks);
        this.armorRightArm.render(partTicks);

        if( clayMan.hasUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_IRON_INGOT)) ) {
            this.buffedBody.render(partTicks);
        }

        GL11.glPopMatrix();

        if( clayMan.hasUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_WOOL)) ) {
            float[] color = this.getSplittedColor(clayMan.getMiscColor());

            clayManRender.bindTexture(Textures.CLAYMAN_PADDING);
            GL11.glPushMatrix();
            GL11.glScalef(1.1F, 1.1F, 1.1F);
            GL11.glColor3f(color[0], color[1], color[2]);
            this.armorBody.render(partTicks);
            this.armorLeftArm.render(partTicks);
            this.armorRightArm.render(partTicks);
            GL11.glPopMatrix();
        }
    }

    private void renderGoggleStripes(RenderClayMan clayManRender, float partTicks) {
        ModelBiped model = clayManRender.modelBipedMain;

        this.glassStripes.rotateAngleX = model.bipedHead.rotateAngleX;
        this.glassStripes.rotateAngleY = model.bipedHead.rotateAngleY;
        this.glassStripes.rotateAngleZ = model.bipedHead.rotateAngleZ;

        clayManRender.bindTexture(Textures.CLAYMAN_GOGGLES);
        this.glassStripes.render(partTicks);
    }

    private void renderSlimefeet(RenderClayMan clayManRender, float partTicks) {
        ModelBiped model = clayManRender.modelBipedMain;

        this.slimeLeftLeg.rotateAngleX = model.bipedLeftLeg.rotateAngleX;
        this.slimeLeftLeg.rotateAngleY = model.bipedLeftLeg.rotateAngleY;
        this.slimeLeftLeg.rotateAngleZ = model.bipedLeftLeg.rotateAngleZ;
        this.slimeRightLeg.rotateAngleX = model.bipedRightLeg.rotateAngleX;
        this.slimeRightLeg.rotateAngleY = model.bipedRightLeg.rotateAngleY;
        this.slimeRightLeg.rotateAngleZ = model.bipedRightLeg.rotateAngleZ;

        clayManRender.bindTexture(Textures.CLAYMAN_SLIMEFEET);
        GL11.glPushMatrix();
        GL11.glScalef(1.2F, 1.2F, 1.2F);
        GL11.glTranslatef(0.0F, -0.2F, 0.0F);
        this.slimeLeftLeg.render(partTicks);
        this.slimeRightLeg.render(partTicks);
        GL11.glPopMatrix();
    }

    public float[] getSplittedColor(int color) {
        float[] splitColor = new float[3];

        splitColor[0] = (float) ((color >> 16) & 0xFF) / 255.0F;
        splitColor[1] = (float) ((color >> 8) & 0xFF) / 255.0F;
        splitColor[2] = (float) (color & 0xFF) / 255.0F;

        return splitColor;
    }
}
