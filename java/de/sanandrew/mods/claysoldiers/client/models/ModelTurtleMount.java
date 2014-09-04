/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelTurtleMount
    extends ModelBase
{
    public ModelRenderer bipedHead;
    public ModelRenderer bipedShellMain;
    public ModelRenderer bipedShellTop;
    public ModelRenderer bipedRightArm;
    public ModelRenderer bipedLeftArm;
    public ModelRenderer bipedRightLeg;
    public ModelRenderer bipedLeftLeg;

    public ModelTurtleMount() {
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.addBox(-1.0F, -1.0F, -2.0F, 2, 2, 2);
        this.bipedHead.setRotationPoint(0.0F, 21.0F, -5.0F);

        this.bipedShellMain = new ModelRenderer(this, 8, 0);
        this.bipedShellMain.addBox(-3.0F, 0.0F, -2.0F, 6, 2, 7);
        this.bipedShellMain.setRotationPoint(0.0F, 20.0F, -3.0F);

        this.bipedShellTop = new ModelRenderer(this, 8, 9);
        this.bipedShellTop.addBox(-2.0F, -1.0F, -1.0F, 4, 1, 5);
        this.bipedShellTop.setRotationPoint(0.0F, 20.0F, -3.0F);

        this.bipedRightArm = new ModelRenderer(this, 0, 4);
        this.bipedRightArm.addBox(-1.0F, 0.0F, -1.0F, 1, 3, 1);
        this.bipedRightArm.setRotationPoint(-2.0F, 22.0F, -3.0F);
        this.setRotation(this.bipedRightArm, 0.0F, 0.0F, 1.003822F);

        this.bipedLeftArm = new ModelRenderer(this, 0, 4);
        this.bipedLeftArm.addBox(0.0F, 0.0F, -1.0F, 1, 3, 1);
        this.bipedLeftArm.setRotationPoint(2.0F, 22.0F, -3.0F);
        this.bipedLeftArm.mirror = true;
        this.setRotation(this.bipedLeftArm, 0.0F, 0.0F, -1.003822F);

        this.bipedRightLeg = new ModelRenderer(this, 4, 4);
        this.bipedRightLeg.addBox(-1.0F, 0.0F, 0.0F, 1, 3, 1);
        this.bipedRightLeg.setRotationPoint(-2.0F, 22.0F, 0.0F);
        this.setRotation(this.bipedRightLeg, 0.0F, 0.0F, 1.003822F);

        this.bipedLeftLeg = new ModelRenderer(this, 4, 4);
        this.bipedLeftLeg.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1);
        this.bipedLeftLeg.setRotationPoint(2.0F, 22.0F, 0.0F);
        this.bipedLeftLeg.mirror = true;
        this.setRotation(this.bipedLeftLeg, 0.0F, 0.0F, -1.003822F);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float rotFloat, float rotYaw, float rotPitch, float partTicks) {
        this.setRotationAngles(limbSwing, limbSwingAmount, rotFloat, rotYaw, rotPitch, partTicks, entity);
        this.bipedHead.render(partTicks);
        this.bipedShellMain.render(partTicks);
        this.bipedRightArm.render(partTicks);
        this.bipedLeftArm.render(partTicks);
        this.bipedRightLeg.render(partTicks);
        this.bipedLeftLeg.render(partTicks);
        this.bipedShellTop.render(partTicks);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float rotFloat, float rotYaw, float rotPitch, float partTicks, Entity entity) {
//      bipedHead.rotateAngleY = f3 / 57.29578F;
//      bipedHead.rotateAngleX = (f4 / 57.29578F) + 0.79F;
        this.bipedRightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 0.5F * limbSwingAmount;
        this.bipedLeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 0.5F * limbSwingAmount;
        this.bipedRightArm.rotateAngleY = 0.0F;
        this.bipedLeftArm.rotateAngleY = 0.0F;
        this.bipedRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 0.5F * limbSwingAmount;
        this.bipedLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 0.5F * limbSwingAmount;
        this.bipedRightLeg.rotateAngleY = 0.0F;
        this.bipedLeftLeg.rotateAngleY = 0.0F;
    }
}
