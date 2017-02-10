/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.model.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.core.manpack.util.javatuples.Triplet;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class ModelClayNexus
        extends ModelBase
{
    public ModelRenderer base1;
    public ModelRenderer base2;
    public ModelRenderer base3;
    public ModelRenderer armBR1;
    public ModelRenderer armTR1;
    public ModelRenderer armBL1;
    public ModelRenderer armTL1;
    public ModelRenderer armBR2;
    public ModelRenderer armTR2;
    public ModelRenderer armBL2;
    public ModelRenderer armTL2;
    public ModelRenderer armBR3;
    public ModelRenderer armTR3;
    public ModelRenderer armBL3;
    public ModelRenderer armTL3;

    public ModelClayNexus() {
        this.textureWidth = 32;
        this.textureHeight = 32;

        float degree45 = 45.0F / 180.0F * (float) Math.PI;

        this.base1 = new ModelRenderer(this, 0, 0);
        this.base1.addBox(-4.0F, 0.0F, -4.0F, 8, 1, 8);
        this.base1.setRotationPoint(0.0F, 23.0F, 0.0F);
        this.base1.setTextureSize(32, 32);
        setRotation(base1, 0.0F, 0.0F, 0.0F);

        this.base2 = new ModelRenderer(this, 0, 9);
        this.base2.addBox(-2.0F, -1.0F, -2.0F, 4, 1, 4);
        this.base2.setRotationPoint(0.0F, 23.0F, 0.0F);
        this.base2.setTextureSize(32, 32);
        setRotation(this.base2, 0.0F, 0.0F, 0.0F);

        this.base3 = new ModelRenderer(this, 0, 21);
        this.base3.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1);
        this.base3.setRotationPoint(0.0F, 22.0F, 0.0F);
        this.base3.setTextureSize(32, 32);
        setRotation(this.base3, 0.0F, 0.0F, 0.0F);

        this.armBR1 = new ModelRenderer(this, 0, 14);
        this.armBR1.addBox(-0.5F, -5.0F, -0.5F, 1, 6, 1);
        this.armBR1.setRotationPoint(3.0F, 23.0F, -3.0F);
        this.armBR1.setTextureSize(32, 32);
        setRotation(this.armBR1, degree45, -degree45, 0.0F);

        this.armTR1 = new ModelRenderer(this, 4, 14);
        this.armTR1.addBox(-0.5F, -5.0F, -0.5F, 1, 6, 1);
        this.armTR1.setRotationPoint(3.0F, 23.0F, 3.0F);
        this.armTR1.setTextureSize(32, 32);
        setRotation(this.armTR1, -degree45, degree45, 0.0F);

        this.armBL1 = new ModelRenderer(this, 8, 14);
        this.armBL1.addBox(-0.5F, -5.0F, -0.5F, 1, 6, 1);
        this.armBL1.setRotationPoint(-3.0F, 23.0F, -3.0F);
        this.armBL1.setTextureSize(32, 32);
        setRotation(armBL1, degree45, degree45, 0.0F);

        this.armTL1 = new ModelRenderer(this, 12, 14);
        this.armTL1.addBox(-0.5F, -5.0F, -0.5F, 1, 6, 1);
        this.armTL1.setRotationPoint(-3.0F, 23.0F, 3.0F);
        this.armTL1.setTextureSize(32, 32);
        setRotation(this.armTL1, -degree45, -degree45, 0.0F);

        this.armBR2 = new ModelRenderer(this, 16, 9);
        this.armBR2.addBox(-0.5F, -5.0F, -0.45F, 1, 7, 1);
        this.armBR2.setRotationPoint(5.0F, 18.0F, -5.0F);
        this.armBR2.setTextureSize(32, 32);
        setRotation(this.armBR2, -degree45, -degree45, 0.0F);

        this.armTR2 = new ModelRenderer(this, 20, 9);
        this.armTR2.addBox(-0.5F, -5.0F, -0.55F, 1, 7, 1);
        this.armTR2.setRotationPoint(5.0F, 18.0F, 5.0F);
        this.armTR2.setTextureSize(32, 32);
        setRotation(this.armTR2, degree45, degree45, 0.0F);

        this.armBL2 = new ModelRenderer(this, 24, 9);
        this.armBL2.addBox(-0.5F, -5.0F, -0.45F, 1, 7, 1);
        this.armBL2.setRotationPoint(-5.0F, 18.0F, -5.0F);
        this.armBL2.setTextureSize(32, 32);
        setRotation(this.armBL2, -degree45, degree45, 0.0F);

        this.armTL2 = new ModelRenderer(this, 28, 9);
        this.armTL2.addBox(-0.5F, -5.0F, -0.55F, 1, 7, 1);
        this.armTL2.setRotationPoint(-5.0F, 18.0F, 5.0F);
        this.armTL2.setTextureSize(32, 32);
        setRotation(this.armTL2, degree45, -degree45, 0.0F);

        this.armBR3 = new ModelRenderer(this, 16, 17);
        this.armBR3.addBox(-0.5F, 1.05F, -0.55F, 1, 2, 1);
        this.armBR3.setRotationPoint(3.0F, 13.0F, -3.0F);
        this.armBR3.setTextureSize(32, 32);
        setRotation(this.armBR3, degree45, -degree45, 0.0F);

        this.armTR3 = new ModelRenderer(this, 20, 17);
        this.armTR3.addBox(-0.5F, 1.05F, -0.45F, 1, 2, 1);
        this.armTR3.setRotationPoint(3.0F, 13.0F, 3.0F);
        this.armTR3.setTextureSize(32, 32);
        setRotation(this.armTR3, -degree45, degree45, 0.0F);

        this.armBL3 = new ModelRenderer(this, 24, 17);
        this.armBL3.addBox(-0.5F, 1.05F, -0.55F, 1, 2, 1);
        this.armBL3.setRotationPoint(-3.0F, 13.0F, -3.0F);
        this.armBL3.setTextureSize(32, 32);
        setRotation(this.armBL3, degree45, degree45, 0.0F);

        this.armTL3 = new ModelRenderer(this, 28, 17);
        this.armTL3.addBox(-0.5F, 1.05F, -0.45F, 1, 2, 1);
        this.armTL3.setRotationPoint(-3.0F, 13.0F, 3.0F);
        this.armTL3.setTextureSize(32, 32);
        setRotation(this.armTL3, -degree45, -degree45, 0.0F);
    }

    public void renderTileEntity() {
        this.base1.render(0.0625F);
        this.base2.render(0.0625F);
        this.base3.render(0.0625F);
        this.armBR1.render(0.0625F);
        this.armTR1.render(0.0625F);
        this.armBL1.render(0.0625F);
        this.armTL1.render(0.0625F);
        this.armBR2.render(0.0625F);
        this.armTR2.render(0.0625F);
        this.armBL2.render(0.0625F);
        this.armTL2.render(0.0625F);
        this.armBR3.render(0.0625F);
        this.armTR3.render(0.0625F);
        this.armBL3.render(0.0625F);
        this.armTL3.render(0.0625F);
    }

    public void renderTileEntityGlowmap() {
        renderScaledBox(this.base1, 0.0625F);
        renderScaledBox(this.base2, 0.0625F);
        renderScaledBox(this.base3, 0.0625F);
        renderScaledBox(this.armBR1, 0.0625F);
        renderScaledBox(this.armTR1, 0.0625F);
        renderScaledBox(this.armBL1, 0.0625F);
        renderScaledBox(this.armTL1, 0.0625F);
        renderScaledBox(this.armBR2, 0.0625F);
        renderScaledBox(this.armTR2, 0.0625F);
        renderScaledBox(this.armBL2, 0.0625F);
        renderScaledBox(this.armTL2, 0.0625F);
        renderScaledBox(this.armBR3, 0.0625F);
        renderScaledBox(this.armTR3, 0.0625F);
        renderScaledBox(this.armBL3, 0.0625F);
        renderScaledBox(this.armTL3, 0.0625F);
    }

    private static void renderScaledBox(ModelRenderer box, float scaleFactor) {
        Triplet<Float, Float, Float> rot = Triplet.with(box.rotateAngleX, box.rotateAngleY, box.rotateAngleZ);
        Triplet<Float, Float, Float> point = Triplet.with(box.rotationPointX, box.rotationPointY, box.rotationPointZ);
        setRotation(box, 0.0F, 0.0F, 0.0F);
        box.rotationPointX = 0.0F;
        box.rotationPointY = 0.0F;
        box.rotationPointZ = 0.0F;

        GL11.glPushMatrix();
        GL11.glTranslatef(point.getValue0() * scaleFactor, point.getValue1() * scaleFactor, point.getValue2() * scaleFactor);
        GL11.glRotatef(rot.getValue2() * (180.0F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
        GL11.glRotatef(rot.getValue1() * (180.0F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(rot.getValue0() * (180.0F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
        GL11.glScalef(1.01F, 1.01F, 1.01F);
        GL11.glTranslatef(0.0F, -0.00075F, 0.0F);
        box.render(scaleFactor);
        GL11.glPopMatrix();

        setRotation(box, rot.getValue0(), rot.getValue1(), rot.getValue2());
        box.rotationPointX = point.getValue0();
        box.rotationPointY = point.getValue1();
        box.rotationPointZ = point.getValue2();
    }

    private static void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
