/*******************************************************************************************************************
 * Name:      ModelHorseMount.java
 * Author:    SanAndreasP
 * Copyright: SanAndreasP and SilverChiren
 * License:   Attribution-NonCommercial-ShareAlike 3.0 Unported (http://creativecommons.org/licenses/by-nc-sa/3.0/)
 *******************************************************************************************************************/

package sanandreasp.mods.ClaySoldiersMod.client.model;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.src.*;
import net.minecraft.util.MathHelper;

public class ModelHorse extends ModelBiped
{

    public ModelHorse()
    {
        this(0.0F);
    }

    public ModelHorse(float f)
    {
        this(f, 0.0F);
    }

    public ModelHorse(float f, float f1)
    {
        bipedHead = new ModelRenderer(this, 0, 0);
        bipedHead.addBox(-1F, 0F, -4F, 2, 2, 4, f + 0.2F);
        bipedHead.setRotationPoint(0.0F, -3.75F + f1, -7.75F);

        bipedEar1 = new ModelRenderer(this, 0, 0);
        bipedEar1.addBox(-1.25F, -0.8F, -1F, 1, 1, 1, f - 0.1F);
        bipedEar1.setRotationPoint(0.0F, -3.75F + f1, -7.75F);

		bipedEar2 = new ModelRenderer(this, 0, 0);
        bipedEar2.addBox(0.25F, -0.8F, -1F, 1, 1, 1, f - 0.1F);
        bipedEar2.setRotationPoint(0.0F, -3.75F + f1, -7.75F);

        bipedBody = new ModelRenderer(this, 0, 8);
        bipedBody.addBox(-2F, 0.0F, -4F, 4, 4, 8, f);
        bipedBody.setRotationPoint(0.0F, 0.0F + f1, 0.0F);

		bipedNeck = new ModelRenderer(this, 12, 0);
        bipedNeck.addBox(-1F, 0.0F, -6.0F, 2, 2, 6, f + 0.4F);
        bipedNeck.setRotationPoint(0.0F, 0.0F + f1, -2.0F);

		bipedMane = new ModelRenderer(this, 28, 0);
        bipedMane.addBox(-1F, -1.1F, -6.0F, 2, 1, 6, f);
        bipedMane.setRotationPoint(0.0F, 0.0F + f1, -2.0F);

        bipedRightArm = new ModelRenderer(this, 24, 10);
        bipedRightArm.addBox(-1F, 0.0F, -1F, 2, 8, 2, f - 0.25F);
        bipedRightArm.setRotationPoint(-1F, 3.75F + f1, -2.75F);

        bipedLeftArm = new ModelRenderer(this, 24, 10);
        bipedLeftArm.mirror = true;
        bipedLeftArm.addBox(-1F, 0.0F, -1F, 2, 8, 2, f - 0.25F);
        bipedLeftArm.setRotationPoint(1.0F, 3.75F + f1, -2.75F);

        bipedRightLeg = new ModelRenderer(this, 24, 10);
        bipedRightLeg.addBox(-1F, 0.0F, -1F, 2, 8, 2, f - 0.25F);
        bipedRightLeg.setRotationPoint(-1F, 3.75F + f1, 2.75F);

        bipedLeftLeg = new ModelRenderer(this, 24, 10);
        bipedLeftLeg.mirror = true;
        bipedLeftLeg.addBox(-1F, 0.0F, -1F, 2, 8, 2, f - 0.25F);
        bipedLeftLeg.setRotationPoint(1.0F, 3.75F + f1, 2.75F);

		bipedTail = new ModelRenderer(this, 36, 11);
        bipedTail.addBox(-0.5F, 0.0F, -0.5F, 1, 5, 1, f + 0.15F);
        bipedTail.setRotationPoint(0F, 0F + f1, 3.75F);
    }

    @Override
	public void render(Entity e, float f, float f1, float f2, float f3, float f4, float f5)
    {
        setRotationAngles(f, f1, f2, f3, f4, f5, e);
        bipedHead.render(f5);
        bipedBody.render(f5);
        bipedRightArm.render(f5);
        bipedLeftArm.render(f5);
        bipedRightLeg.render(f5);
        bipedLeftLeg.render(f5);

		bipedNeck.render(f5);
		bipedMane.render(f5);
		bipedTail.render(f5);
		bipedEar1.render(f5);
		bipedEar2.render(f5);
    }

    @Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e)
    {
        bipedHead.rotateAngleY = f3 / 57.29578F;
        bipedHead.rotateAngleX = (f4 / 57.29578F) + 0.79F;
        bipedHeadwear.rotateAngleY = bipedHead.rotateAngleY;
        bipedHeadwear.rotateAngleX = bipedHead.rotateAngleX;
        bipedRightArm.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 2.0F * f1 * 0.25F;
        bipedLeftArm.rotateAngleX = MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.25F;
        bipedRightArm.rotateAngleZ = 0.0F;
        bipedLeftArm.rotateAngleZ = 0.0F;
        bipedRightLeg.rotateAngleX = MathHelper.cos(f * 0.6662F) * 0.5F * f1;
        bipedLeftLeg.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 0.5F * f1;
        bipedRightLeg.rotateAngleY = 0.0F;
        bipedLeftLeg.rotateAngleY = 0.0F;
        if (isRiding)
        {
            bipedRightArm.rotateAngleX += -0.6283185F;
            bipedLeftArm.rotateAngleX += -0.6283185F;
            bipedRightLeg.rotateAngleX = -1.256637F;
            bipedLeftLeg.rotateAngleX = -1.256637F;
            bipedRightLeg.rotateAngleY = 0.3141593F;
            bipedLeftLeg.rotateAngleY = -0.3141593F;
        }
        if (heldItemLeft != 0)
        {
            bipedLeftArm.rotateAngleX = bipedLeftArm.rotateAngleX * 0.5F - 0.3141593F;
        }
        if (heldItemRight != 0)
        {
            bipedRightArm.rotateAngleX = bipedRightArm.rotateAngleX * 0.5F - 0.3141593F;
        }
        bipedRightArm.rotateAngleY = 0.0F;
        bipedLeftArm.rotateAngleY = 0.0F;
        if (onGround > -9990F)
        {
            float f6 = onGround;
            bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt_float(f6) * 3.141593F * 2.0F) * 0.2F;
            bipedRightArm.rotateAngleY += bipedBody.rotateAngleY;
            bipedLeftArm.rotateAngleY += bipedBody.rotateAngleY;
            bipedLeftArm.rotateAngleX += bipedBody.rotateAngleY;
            f6 = 1.0F - onGround;
            f6 *= f6;
            f6 *= f6;
            f6 = 1.0F - f6;
            float f7 = MathHelper.sin(f6 * 3.141593F);
            float f8 = MathHelper.sin(onGround * 3.141593F) * -(bipedHead.rotateAngleX - 0.7F) * 0.75F;
            bipedRightArm.rotateAngleX -= f7 * 1.2D + f8;
            bipedRightArm.rotateAngleY += bipedBody.rotateAngleY * 2.0F;
            bipedRightArm.rotateAngleZ = MathHelper.sin(onGround * 3.141593F) * -0.4F;
        }

		bipedTail.rotateAngleX = 0.3F + (bipedRightArm.rotateAngleX * bipedRightArm.rotateAngleX);
		bipedMane.rotateAngleX = bipedNeck.rotateAngleX = -0.6F;

		bipedEar1.rotateAngleX = bipedEar2.rotateAngleX = bipedHead.rotateAngleX;
		bipedEar1.rotateAngleY = bipedEar2.rotateAngleY = bipedHead.rotateAngleY;
    }

	public ModelRenderer bipedEar1, bipedEar2;
	public ModelRenderer bipedTail, bipedNeck, bipedMane;
}
