/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import de.sanandrew.mods.claysoldiers.client.render.entity.RenderClayMan;
import de.sanandrew.mods.claysoldiers.client.util.Textures;
import de.sanandrew.mods.claysoldiers.util.soldier.effect.SoldierEffects;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgrades;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
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

    public boolean isInitialized = false;

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
//        this.lilypantsRightLeg = new ModelRenderer(renderClayMan.modelBipedMain, 0, 24);
//        this.lilypantsRightLeg.addBox(-2.0F, 8.0F, -2.0F, 4, 4, 4, 0.25F);
//        this.lilypantsRightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
//        this.lilypantsLeftLeg = new ModelRenderer(renderClayMan.modelBipedMain, 0, 24);
//        this.lilypantsLeftLeg.mirror = true;
//        this.lilypantsLeftLeg.addBox(-2.0F, 8.0F, -2.0F, 4, 4, 4, 0.25F);
//        this.lilypantsLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
    }

    @SubscribeEvent
    public void onSoldierRenderArmor(SoldierRenderEvent.RenderModelEvent event) {
        if( !this.isInitialized ) {
            this.isInitialized = true;
            this.initRenderer(event.clayManRender);
        }

        if( event.clayMan.hasUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_GUNPOWDER)) ) {
            event.clayManRender.bindTexture(Textures.CLAYMAN_GUNPOWDER);
            GL11.glPushMatrix();
            event.clayManRender.modelBipedMain.render(event.clayMan, event.limbSwing, event.limbSwingAmount, event.rotFloat, event.yaw, event.pitch, event.partTicks);
            GL11.glPopMatrix();
        }

        if( event.clayMan.hasUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_GOLD_NUGGET)) ) {
            ModelBiped model = event.clayManRender.modelBipedMain;

            this.crown.rotateAngleX = model.bipedHead.rotateAngleX;
            this.crown.rotateAngleY = model.bipedHead.rotateAngleY;
            this.crown.rotateAngleZ = model.bipedHead.rotateAngleZ;

            event.clayManRender.bindTexture(Textures.CLAYMAN_CROWN);
            GL11.glColor3f(1.0F, 0.9F, 0.0F);
            this.crown.render(event.partTicks);
            GL11.glColor3f(1.0F, 1.0F, 1.0F);
        }

        if( event.clayMan.hasUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_IRON_INGOT)) ) {
            ModelBiped model = event.clayManRender.modelBipedMain;

            this.buffedBody.rotateAngleX = model.bipedBody.rotateAngleX;
            this.buffedBody.rotateAngleY = model.bipedBody.rotateAngleY;
            this.buffedBody.rotateAngleZ = model.bipedBody.rotateAngleZ;

            event.clayManRender.bindTexture(event.clayMan.getTexture());
            GL11.glPushMatrix();
            GL11.glScalef(1.5F, 1.5F, 1.5F);
            this.buffedBody.render(event.partTicks);
            GL11.glPopMatrix();
        }

        if( event.clayMan.hasUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_LILYPADS)) ) {
            ModelBiped model = event.clayManRender.modelBipedMain;

            this.lilypantsBody.rotateAngleX = model.bipedBody.rotateAngleX;
            this.lilypantsBody.rotateAngleY = model.bipedBody.rotateAngleY;
            this.lilypantsBody.rotateAngleZ = model.bipedBody.rotateAngleZ;
            this.lilypantsLeftLeg.rotateAngleX = model.bipedLeftLeg.rotateAngleX;
            this.lilypantsLeftLeg.rotateAngleY = model.bipedLeftLeg.rotateAngleY;
            this.lilypantsLeftLeg.rotateAngleZ = model.bipedLeftLeg.rotateAngleZ;
            this.lilypantsRightLeg.rotateAngleX = model.bipedRightLeg.rotateAngleX;
            this.lilypantsRightLeg.rotateAngleY = model.bipedRightLeg.rotateAngleY;
            this.lilypantsRightLeg.rotateAngleZ = model.bipedRightLeg.rotateAngleZ;

            event.clayManRender.bindTexture(Textures.CLAYMAN_LILYPANTS);
            this.lilypantsBody.render(event.partTicks);
            this.lilypantsLeftLeg.render(event.partTicks);
            this.lilypantsRightLeg.render(event.partTicks);
        }

        if( event.clayMan.hasUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_LEATHER)) ) {
            ModelBiped model = event.clayManRender.modelBipedMain;

            this.armorBody.rotateAngleX = model.bipedBody.rotateAngleX;
            this.armorBody.rotateAngleY = model.bipedBody.rotateAngleY;
            this.armorBody.rotateAngleZ = model.bipedBody.rotateAngleZ;
            this.armorLeftArm.rotateAngleX = model.bipedLeftArm.rotateAngleX;
            this.armorLeftArm.rotateAngleY = model.bipedLeftArm.rotateAngleY;
            this.armorLeftArm.rotateAngleZ = model.bipedLeftArm.rotateAngleZ;
            this.armorRightArm.rotateAngleX = model.bipedRightArm.rotateAngleX;
            this.armorRightArm.rotateAngleY = model.bipedRightArm.rotateAngleY;
            this.armorRightArm.rotateAngleZ = model.bipedRightArm.rotateAngleZ;

            event.clayManRender.bindTexture(Textures.CLAYMAN_LEATHER_ARMOR);
            GL11.glPushMatrix();
            GL11.glScalef(1.2F, 1.2F, 1.2F);
            this.armorBody.render(event.partTicks);
            this.armorLeftArm.render(event.partTicks);
            this.armorRightArm.render(event.partTicks);
            GL11.glPopMatrix();

            if( event.clayMan.hasUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_WOOL)) ) {
                float[] color = this.getSplittedColor(event.clayMan.getMiscColor());

                event.clayManRender.bindTexture(Textures.CLAYMAN_PADDING);
                GL11.glPushMatrix();
                GL11.glScalef(1.1F, 1.1F, 1.1F);
                GL11.glColor3f(color[0], color[1], color[2]);
                this.armorBody.render(event.partTicks);
                this.armorLeftArm.render(event.partTicks);
                this.armorRightArm.render(event.partTicks);
                GL11.glPopMatrix();
            }
        }

        if( event.clayMan.hasEffect(SoldierEffects.getEffect(SoldierEffects.EFF_SLIMEFEET)) ) {
            ModelBiped model = event.clayManRender.modelBipedMain;

            this.slimeLeftLeg.rotateAngleX = model.bipedLeftLeg.rotateAngleX;
            this.slimeLeftLeg.rotateAngleY = model.bipedLeftLeg.rotateAngleY;
            this.slimeLeftLeg.rotateAngleZ = model.bipedLeftLeg.rotateAngleZ;
            this.slimeRightLeg.rotateAngleX = model.bipedRightLeg.rotateAngleX;
            this.slimeRightLeg.rotateAngleY = model.bipedRightLeg.rotateAngleY;
            this.slimeRightLeg.rotateAngleZ = model.bipedRightLeg.rotateAngleZ;

            event.clayManRender.bindTexture(Textures.CLAYMAN_SLIMEFEET);
            GL11.glPushMatrix();
            GL11.glScalef(1.2F, 1.2F, 1.2F);
            GL11.glTranslatef(0.0F, -0.2F, 0.0F);
            this.slimeLeftLeg.render(event.partTicks);
            this.slimeRightLeg.render(event.partTicks);
            GL11.glPopMatrix();
        }
    }

    public float[] getSplittedColor(int color) {
        float[] splitColor = new float[3];

        splitColor[0] = (float)((color >> 16) & 0xFF) / 255.0F;
        splitColor[1] = (float)((color >> 8) & 0xFF) / 255.0F;
        splitColor[2] = (float)(color & 0xFF) / 255.0F;

        return splitColor;
    }
}
