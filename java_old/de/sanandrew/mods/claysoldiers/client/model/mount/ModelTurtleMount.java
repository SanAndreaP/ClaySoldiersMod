/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.model.mount;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

@SideOnly(Side.CLIENT)
public class ModelTurtleMount
        extends ModelBase
{
    public ModelRenderer head;
    public ModelRenderer shellMain;
    public ModelRenderer shellTop;
    public ModelRenderer rightLegFront;
    public ModelRenderer leftLegFront;
    public ModelRenderer rightLegBack;
    public ModelRenderer leftLegBack;

    public ModelTurtleMount() {
        this.head = new ModelRenderer(this, 0, 0);
        this.head.addBox(-1.0F, -1.0F, -2.0F, 2, 2, 2);
        this.head.setRotationPoint(0.0F, 21.0F, -5.0F);

        this.shellMain = new ModelRenderer(this, 8, 0);
        this.shellMain.addBox(-3.0F, 0.0F, -2.0F, 6, 2, 7);
        this.shellMain.setRotationPoint(0.0F, 20.0F, -3.0F);

        this.shellTop = new ModelRenderer(this, 8, 9);
        this.shellTop.addBox(-2.0F, -1.0F, -1.0F, 4, 1, 5);
        this.shellTop.setRotationPoint(0.0F, 20.0F, -3.0F);

        this.rightLegFront = new ModelRenderer(this, 0, 4);
        this.rightLegFront.addBox(-1.0F, 0.0F, -1.0F, 1, 3, 1);
        this.rightLegFront.setRotationPoint(-2.0F, 22.0F, -3.0F);
        setRotation(this.rightLegFront, 0.0F, 0.0F, 1.003822F);

        this.leftLegFront = new ModelRenderer(this, 0, 4);
        this.leftLegFront.addBox(0.0F, 0.0F, -1.0F, 1, 3, 1);
        this.leftLegFront.setRotationPoint(2.0F, 22.0F, -3.0F);
        this.leftLegFront.mirror = true;
        setRotation(this.leftLegFront, 0.0F, 0.0F, -1.003822F);

        this.rightLegBack = new ModelRenderer(this, 4, 4);
        this.rightLegBack.addBox(-1.0F, 0.0F, 0.0F, 1, 3, 1);
        this.rightLegBack.setRotationPoint(-2.0F, 22.0F, 0.0F);
        setRotation(this.rightLegBack, 0.0F, 0.0F, 1.003822F);

        this.leftLegBack = new ModelRenderer(this, 4, 4);
        this.leftLegBack.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1);
        this.leftLegBack.setRotationPoint(2.0F, 22.0F, 0.0F);
        this.leftLegBack.mirror = true;
        setRotation(this.leftLegBack, 0.0F, 0.0F, -1.003822F);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float rotFloat, float rotYaw, float rotPitch, float partTicks) {
        this.setRotationAngles(limbSwing, limbSwingAmount, rotFloat, rotYaw, rotPitch, partTicks, entity);
        this.head.render(partTicks);
        this.shellMain.render(partTicks);
        this.rightLegFront.render(partTicks);
        this.leftLegFront.render(partTicks);
        this.rightLegBack.render(partTicks);
        this.leftLegBack.render(partTicks);
        this.shellTop.render(partTicks);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float rotFloat, float rotYaw, float rotPitch, float partTicks, Entity entity) {
        this.rightLegFront.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 0.5F * limbSwingAmount;
        this.leftLegFront.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 0.5F * limbSwingAmount;
        this.rightLegFront.rotateAngleY = 0.0F;
        this.leftLegFront.rotateAngleY = 0.0F;
        this.rightLegBack.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 0.5F * limbSwingAmount;
        this.leftLegBack.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 0.5F * limbSwingAmount;
        this.rightLegBack.rotateAngleY = 0.0F;
        this.leftLegBack.rotateAngleY = 0.0F;
    }

    private static void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
