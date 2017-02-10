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
public class ModelBunnyMount
        extends ModelBase
{
    public ModelRenderer head;
    public ModelRenderer body;
    public ModelRenderer leftLegFront;
    public ModelRenderer rightLegFront;
    public ModelRenderer leftLegBack;
    public ModelRenderer rightLegBack;
    public ModelRenderer earRight;
    public ModelRenderer earLeft;
    public ModelRenderer tail;

    public ModelBunnyMount() {
        this.head = new ModelRenderer(this, 0, 0);
        this.head.addBox(-1.0F, -1.0F, -2.0F, 2, 2, 2);
        this.head.setRotationPoint(0.0F, 21.5F, -1.0F);

        this.body = new ModelRenderer(this, 0, 4);
        this.body.addBox(-1.5F, -2.0F, -1.5F, 3, 3, 2);
        this.body.setRotationPoint(0.0F, 21.5F, 1.0F);
        setRotation(this.body, 1.570796F, 0.0F, 0.0F);

        this.leftLegFront = new ModelRenderer(this, 0, 9);
        this.leftLegFront.addBox(-1.0F, 0.0F, 0.0F, 1, 1, 1);
        this.leftLegFront.setRotationPoint(-0.5F, 23.0F, 1.0F);

        this.rightLegFront = new ModelRenderer(this, 0, 9);
        this.rightLegFront.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
        this.rightLegFront.setRotationPoint(0.5F, 23.0F, 1.0F);
        this.rightLegFront.mirror = true;

        this.leftLegBack = new ModelRenderer(this, 0, 9);
        this.leftLegBack.addBox(-1.0F, 0.0F, -1.0F, 1, 1, 1);
        this.leftLegBack.setRotationPoint(-0.5F, 23.0F, 0.0F);

        this.rightLegBack = new ModelRenderer(this, 0, 9);
        this.rightLegBack.addBox(0.0F, 0.0F, -1.0F, 1, 1, 1);
        this.rightLegBack.setRotationPoint(0.5F, 23.0F, 0.0F);
        this.rightLegBack.mirror = true;

        this.earRight = new ModelRenderer(this, 8, 0);
        this.earRight.addBox(0.0F, -3.1F, -1.0F, 1, 3, 1);
        this.earRight.setRotationPoint(0.0F, 22.0F, -1.0F);
        setRotation(this.earRight, 0.0F, 0.0F, -0.6981317F);

        this.earLeft = new ModelRenderer(this, 8, 0);
        this.earLeft.addBox(-1.0F, -3.1F, -1.0F, 1, 3, 1);
        this.earLeft.setRotationPoint(0.0F, 22.0F, -1.0F);
        this.earLeft.mirror = true;
        setRotation(this.earLeft, 0.0F, 0.0F, 0.6981317F);

        this.tail = new ModelRenderer(this, 4, 9);
        this.tail.addBox(-0.5F, 0.0F, -0.5F, 1, 1, 1);
        this.tail.setRotationPoint(0.0F, 21.5F, 2.0F);
        setRotation(this.tail, 1.747395F, 0.0F, 0.0F);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float rotFloat, float rotYaw, float rotPitch, float partTicks) {
        this.setRotationAngles(limbSwing, limbSwingAmount, rotFloat, rotYaw, rotPitch, partTicks, entity);
        this.head.render(partTicks);
        this.body.render(partTicks);
        this.rightLegFront.render(partTicks);
        this.leftLegFront.render(partTicks);
        this.rightLegBack.render(partTicks);
        this.leftLegBack.render(partTicks);
        this.earLeft.render(partTicks);
        this.earRight.render(partTicks);
        this.tail.render(partTicks);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float rotFloat, float rotYaw, float rotPitch, float partTicks, Entity entity) {
        this.rightLegFront.rotateAngleX = this.leftLegFront.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 0.5F * limbSwingAmount;
        this.rightLegBack.rotateAngleX = this.leftLegBack.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 0.5F * limbSwingAmount;
        this.rightLegFront.rotateAngleY = this.leftLegFront.rotateAngleY = 0.0F;
        this.rightLegBack.rotateAngleY = this.leftLegBack.rotateAngleY = 0.0F;
    }

    private static void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
