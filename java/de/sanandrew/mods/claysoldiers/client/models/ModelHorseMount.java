/*******************************************************************************************************************
 * Author: SanAndreasP
 * Copyright: SanAndreasP and SilverChiren
 * License: Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/)
 *******************************************************************************************************************/

package de.sanandrew.mods.claysoldiers.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelHorseMount
    extends ModelBase
{
    public ModelRenderer head;
    public ModelRenderer ear1;
    public ModelRenderer ear2;
    public ModelRenderer body;
    public ModelRenderer neck;
    public ModelRenderer mane;
    public ModelRenderer leg1;
    public ModelRenderer leg2;
    public ModelRenderer leg3;
    public ModelRenderer leg4;
    public ModelRenderer tail;

    public ModelHorseMount() {
        head = new ModelRenderer(this, 0, 0);
        head.addBox(-1.0F, 0.0F, -4.0F, 2, 2, 4,0.2F);
        head.setRotationPoint(0.0F, -3.75F, -7.75F);

        ear1 = new ModelRenderer(this, 0, 0);
        ear1.addBox(-1.25F, -0.8F, -1.0F, 1, 1, 1, 0.1F);
        ear1.setRotationPoint(0.0F, -3.75F, -7.75F);

        ear2 = new ModelRenderer(this, 0, 0);
        ear2.addBox(0.25F, -0.8F, -1.0F, 1, 1, 1, 0.1F);
        ear2.setRotationPoint(0.0F, -3.75F, -7.75F);

        body = new ModelRenderer(this, 0, 8);
        body.addBox(-2.0F, 0.0F, -4.0F, 4, 4, 8, 0.0F);
        body.setRotationPoint(0.0F, 0.0F, 0.0F);

        neck = new ModelRenderer(this, 12, 0);
        neck.addBox(-1.0F, 0.0F, -6.0F, 2, 2, 6, 0.4F);
        neck.setRotationPoint(0.0F, 0.0F, -2.0F);

        mane = new ModelRenderer(this, 28, 0);
        mane.addBox(-1.0F, -1.1F, -6.0F, 2, 1, 6, 0.0F);
        mane.setRotationPoint(0.0F, 0.0F, -2.0F);

        leg1 = new ModelRenderer(this, 24, 10);
        leg1.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.25F);
        leg1.setRotationPoint(-1.0F, 3.75F, -2.75F);

        leg2 = new ModelRenderer(this, 24, 10);
        leg2.mirror = true;
        leg2.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.25F);
        leg2.setRotationPoint(1.0F, 3.75F, -2.75F);

        leg3 = new ModelRenderer(this, 24, 10);
        leg3.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.25F);
        leg3.setRotationPoint(-.0F, 3.75F, 2.75F);

        leg4 = new ModelRenderer(this, 24, 10);
        leg4.mirror = true;
        leg4.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.25F);
        leg4.setRotationPoint(1.0F, 3.75F, 2.75F);

        tail = new ModelRenderer(this, 36, 11);
        tail.addBox(-0.5F, 0.0F, -0.5F, 1, 5, 1, 0.15F);
        tail.setRotationPoint(0.0F, 0.0F, 3.75F);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float rotFloat, float rotYaw, float rotPitch, float partTicks) {
        setRotationAngles(limbSwing, limbSwingAmount, rotFloat, rotYaw, rotPitch, partTicks, entity);
        head.render(partTicks);
        body.render(partTicks);
        leg1.render(partTicks);
        leg2.render(partTicks);
        leg3.render(partTicks);
        leg4.render(partTicks);
        neck.render(partTicks);
        mane.render(partTicks);
        tail.render(partTicks);
        ear1.render(partTicks);
        ear2.render(partTicks);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float rotFloat, float rotYaw, float rotPitch, float partTicks, Entity entity) {
        head.rotateAngleY = rotYaw / 57.29578F;
        head.rotateAngleX = (rotPitch / 57.29578F) + 0.79F;

        leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.25F;
        leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.25F;
        leg3.rotateAngleX = leg1.rotateAngleX;
        leg4.rotateAngleX = leg2.rotateAngleX;

        leg1.rotateAngleZ = 0.0F;
        leg2.rotateAngleZ = 0.0F;
        leg1.rotateAngleY = 0.0F;
        leg2.rotateAngleY = 0.0F;
        leg3.rotateAngleY = 0.0F;
        leg4.rotateAngleY = 0.0F;

        tail.rotateAngleX = 0.3F + (leg1.rotateAngleX * leg1.rotateAngleX);
        mane.rotateAngleX = neck.rotateAngleX = -0.6F;
        ear1.rotateAngleX = ear2.rotateAngleX = head.rotateAngleX;
        ear1.rotateAngleY = ear2.rotateAngleY = head.rotateAngleY;
    }
}
