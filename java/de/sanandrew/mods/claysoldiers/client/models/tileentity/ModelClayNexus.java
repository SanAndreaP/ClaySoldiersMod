/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.models.tileentity;

import de.sanandrew.core.manpack.util.javatuples.Triplet;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import org.lwjgl.opengl.GL11;

public class ModelClayNexus
    extends ModelBase
{
    ModelRenderer base1;
    ModelRenderer base2;
    ModelRenderer base3;
    ModelRenderer ball;
    ModelRenderer armBR1;
    ModelRenderer armTR1;
    ModelRenderer armBL1;
    ModelRenderer armTL1;
    ModelRenderer armBR2;
    ModelRenderer armTR2;
    ModelRenderer armBL2;
    ModelRenderer armTL2;
    ModelRenderer armBR3;
    ModelRenderer armTR3;
    ModelRenderer armBL3;
    ModelRenderer armTL3;

    public ModelClayNexus() {
        this.textureWidth = 32;
        this.textureHeight = 32;

        float degree45 = 45.0F / 180.0F * (float) Math.PI;

        this.base1 = new ModelRenderer(this, 0, 0);
        this.base1.addBox(-4.0F, 0.0F, -4.0F, 8, 1, 8);
        this.base1.setRotationPoint(0.0F, 23.0F, 0.0F);
        this.base1.setTextureSize(32, 32);
        this.setRotation(base1, 0.0F, 0.0F, 0.0F);

        this.base2 = new ModelRenderer(this, 0, 9);
        this.base2.addBox(-2.0F, -1.0F, -2.0F, 4, 1, 4);
        this.base2.setRotationPoint(0.0F, 23.0F, 0.0F);
        this.base2.setTextureSize(32, 32);
        this.setRotation(this.base2, 0.0F, 0.0F, 0.0F);

        this.base3 = new ModelRenderer(this, 0, 21);
        this.base3.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1);
        this.base3.setRotationPoint(0.0F, 22.0F, 0.0F);
        this.base3.setTextureSize(32, 32);
        this.setRotation(this.base3, 0.0F, 0.0F, 0.0F);

        this.ball = new ModelRenderer(this, 4, 21);
        this.ball.addBox(-1.5F, -1.5F, -1.5F, 3, 3, 3);
        this.ball.setRotationPoint(0.0F, 17.0F, 0.0F);
        this.ball.setTextureSize(32, 32);
        this.setRotation(this.ball, 2.356194F, -0.7853982F, 0.7853982F);

        this.armBR1 = new ModelRenderer(this, 0, 14);
        this.armBR1.addBox(-0.5F, -5.0F, -0.5F, 1, 6, 1);
        this.armBR1.setRotationPoint(3.0F, 23.0F, -3.0F);
        this.armBR1.setTextureSize(32, 32);
        this.setRotation(this.armBR1, degree45, -degree45, 0.0F);

        this.armTR1 = new ModelRenderer(this, 4, 14);
        this.armTR1.addBox(-0.5F, -5.0F, -0.5F, 1, 6, 1);
        this.armTR1.setRotationPoint(3.0F, 23.0F, 3.0F);
        this.armTR1.setTextureSize(32, 32);
        this.setRotation(this.armTR1, -degree45, degree45, 0.0F);

        this.armBL1 = new ModelRenderer(this, 8, 14);
        this.armBL1.addBox(-0.5F, -5.0F, -0.5F, 1, 6, 1);
        this.armBL1.setRotationPoint(-3.0F, 23.0F, -3.0F);
        this.armBL1.setTextureSize(32, 32);
        this.setRotation(armBL1, degree45, degree45, 0.0F);

        this.armTL1 = new ModelRenderer(this, 12, 14);
        this.armTL1.addBox(-0.5F, -5.0F, -0.5F, 1, 6, 1);
        this.armTL1.setRotationPoint(-3.0F, 23.0F, 3.0F);
        this.armTL1.setTextureSize(32, 32);
        this.setRotation(this.armTL1, -degree45, -degree45, 0.0F);

        this.armBR2 = new ModelRenderer(this, 16, 9);
        this.armBR2.addBox(-0.5F, -5.0F, -0.45F, 1, 7, 1);
        this.armBR2.setRotationPoint(5.0F, 18.0F, -5.0F);
        this.armBR2.setTextureSize(32, 32);
        this.setRotation(this.armBR2, -degree45, -degree45, 0.0F);

        this.armTR2 = new ModelRenderer(this, 20, 9);
        this.armTR2.addBox(-0.5F, -5.0F, -0.55F, 1, 7, 1);
        this.armTR2.setRotationPoint(5.0F, 18.0F, 5.0F);
        this.armTR2.setTextureSize(32, 32);
        this.setRotation(this.armTR2, degree45, degree45, 0.0F);

        this.armBL2 = new ModelRenderer(this, 24, 9);
        this.armBL2.addBox(-0.5F, -5.0F, -0.45F, 1, 7, 1);
        this.armBL2.setRotationPoint(-5.0F, 18.0F, -5.0F);
        this.armBL2.setTextureSize(32, 32);
        this.setRotation(this.armBL2, -degree45, degree45, 0.0F);

        this.armTL2 = new ModelRenderer(this, 28, 9);
        this.armTL2.addBox(-0.5F, -5.0F, -0.55F, 1, 7, 1);
        this.armTL2.setRotationPoint(-5.0F, 18.0F, 5.0F);
        this.armTL2.setTextureSize(32, 32);
        this.setRotation(this.armTL2, degree45, -degree45, 0.0F);

        this.armBR3 = new ModelRenderer(this, 16, 17);
        this.armBR3.addBox(-0.5F, 1.05F, -0.55F, 1, 2, 1);
        this.armBR3.setRotationPoint(3.0F, 13.0F, -3.0F);
        this.armBR3.setTextureSize(32, 32);
        this.setRotation(this.armBR3, degree45, -degree45, 0.0F);

        this.armTR3 = new ModelRenderer(this, 20, 17);
        this.armTR3.addBox(-0.5F, 1.05F, -0.45F, 1, 2, 1);
        this.armTR3.setRotationPoint(3.0F, 13.0F, 3.0F);
        this.armTR3.setTextureSize(32, 32);
        this.setRotation(this.armTR3, -degree45, degree45, 0.0F);

        this.armBL3 = new ModelRenderer(this, 24, 17);
        this.armBL3.addBox(-0.5F, 1.05F, -0.55F, 1, 2, 1);
        this.armBL3.setRotationPoint(-3.0F, 13.0F, -3F);
        this.armBL3.setTextureSize(32, 32);
        this.setRotation(this.armBL3, degree45, degree45, 0.0F);

        this.armTL3 = new ModelRenderer(this, 28, 17);
        this.armTL3.addBox(-0.5F, 1.05F, -0.45F, 1, 2, 1);
        this.armTL3.setRotationPoint(-3.0F, 13.0F, 3.0F);
        this.armTL3.setTextureSize(32, 32);
        this.setRotation(this.armTL3, -degree45, -degree45, 0.0F);
    }

    public void renderTileEntity(float scaleFactor) {
        base1.render(scaleFactor);
        base2.render(scaleFactor);
        base3.render(scaleFactor);
//        ball.render(scaleFactor);
        armBR1.render(scaleFactor);
        armTR1.render(scaleFactor);
        armBL1.render(scaleFactor);
        armTL1.render(scaleFactor);
        armBR2.render(scaleFactor);
        armTR2.render(scaleFactor);
        armBL2.render(scaleFactor);
        armTL2.render(scaleFactor);
        armBR3.render(scaleFactor);
        armTR3.render(scaleFactor);
        armBL3.render(scaleFactor);
        armTL3.render(scaleFactor);
    }

    public void renderTileEntityGlowmap(float scaleFactor) {
        renderScaledBox(base1, scaleFactor);
        renderScaledBox(base2, scaleFactor);
        renderScaledBox(base3, scaleFactor);
//        renderScaledBox(ball, scaleFactor);
        renderScaledBox(armBR1, scaleFactor);
        renderScaledBox(armTR1, scaleFactor);
        renderScaledBox(armBL1, scaleFactor);
        renderScaledBox(armTL1, scaleFactor);
        renderScaledBox(armBR2, scaleFactor);
        renderScaledBox(armTR2, scaleFactor);
        renderScaledBox(armBL2, scaleFactor);
        renderScaledBox(armTL2, scaleFactor);
        renderScaledBox(armBR3, scaleFactor);
        renderScaledBox(armTR3, scaleFactor);
        renderScaledBox(armBL3, scaleFactor);
        renderScaledBox(armTL3, scaleFactor);
    }

    private void renderScaledBox(ModelRenderer box, float scaleFactor) {
        Triplet<Float, Float, Float> rot = Triplet.with(box.rotateAngleX, box.rotateAngleY, box.rotateAngleZ);
        Triplet<Float, Float, Float> point = Triplet.with(box.rotationPointX, box.rotationPointY, box.rotationPointZ);
        this.setRotation(box, 0.0F, 0.0F, 0.0F);
        box.rotationPointX = 0.0F;
        box.rotationPointY = 0.0F;
        box.rotationPointZ = 0.0F;

        GL11.glPushMatrix();
        GL11.glTranslatef(point.getValue0() * scaleFactor, point.getValue1() * scaleFactor, point.getValue2() * scaleFactor);
        GL11.glRotatef(rot.getValue2() * (180F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
        GL11.glRotatef(rot.getValue1() * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(rot.getValue0() * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
        GL11.glScalef(1.01F, 1.01F, 1.01F);
        GL11.glTranslatef(0.0F, -0.00075F, 0.0F);
        box.render(scaleFactor);
        GL11.glPopMatrix();

        this.setRotation(box, rot.getValue0(), rot.getValue1(), rot.getValue2());
        box.rotationPointX = point.getValue0();
        box.rotationPointY = point.getValue1();
        box.rotationPointZ = point.getValue2();
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
