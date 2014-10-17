/*******************************************************************************************************************
 * Author: SanAndreasP
 * Copyright: SanAndreasP and SilverChiren
 * License: Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/)
 *******************************************************************************************************************/

package de.sanandrew.mods.claysoldiers.client.model.mount;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

@SideOnly(Side.CLIENT)
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
        this.head = new ModelRenderer(this, 0, 0);
        this.head.addBox(-1.0F, 0.0F, -4.0F, 2, 2, 4, 0.2F);
        this.head.setRotationPoint(0.0F, -3.75F, -7.75F);

        this.ear1 = new ModelRenderer(this, 0, 0);
        this.ear1.addBox(-1.25F, -0.8F, -1.0F, 1, 1, 1, 0.1F);
        this.ear1.setRotationPoint(0.0F, -3.75F, -7.75F);

        this.ear2 = new ModelRenderer(this, 0, 0);
        this.ear2.addBox(0.25F, -0.8F, -1.0F, 1, 1, 1, 0.1F);
        this.ear2.setRotationPoint(0.0F, -3.75F, -7.75F);

        this.body = new ModelRenderer(this, 0, 8);
        this.body.addBox(-2.0F, 0.0F, -4.0F, 4, 4, 8, 0.0F);
        this.body.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.neck = new ModelRenderer(this, 12, 0);
        this.neck.addBox(-1.0F, 0.0F, -6.0F, 2, 2, 6, 0.4F);
        this.neck.setRotationPoint(0.0F, 0.0F, -2.0F);

        this.mane = new ModelRenderer(this, 28, 0);
        this.mane.addBox(-1.0F, -1.1F, -6.0F, 2, 1, 6, 0.0F);
        this.mane.setRotationPoint(0.0F, 0.0F, -2.0F);

        this.leg1 = new ModelRenderer(this, 24, 10);
        this.leg1.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.25F);
        this.leg1.setRotationPoint(-1.0F, 3.75F, -2.75F);

        this.leg2 = new ModelRenderer(this, 24, 10);
        this.leg2.mirror = true;
        this.leg2.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.25F);
        this.leg2.setRotationPoint(1.0F, 3.75F, -2.75F);

        this.leg3 = new ModelRenderer(this, 24, 10);
        this.leg3.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.25F);
        this.leg3.setRotationPoint(-1.0F, 3.75F, 2.75F);

        this.leg4 = new ModelRenderer(this, 24, 10);
        this.leg4.mirror = true;
        this.leg4.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.25F);
        this.leg4.setRotationPoint(1.0F, 3.75F, 2.75F);

        this.tail = new ModelRenderer(this, 36, 11);
        this.tail.addBox(-0.5F, 0.0F, -0.5F, 1, 5, 1, 0.15F);
        this.tail.setRotationPoint(0.0F, 0.0F, 3.75F);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float rotFloat, float rotYaw, float rotPitch, float partTicks) {
        setRotationAngles(limbSwing, limbSwingAmount, rotFloat, rotYaw, rotPitch, partTicks, entity);
        this.head.render(partTicks);
        this.body.render(partTicks);
        this.leg1.render(partTicks);
        this.leg2.render(partTicks);
        this.leg3.render(partTicks);
        this.leg4.render(partTicks);
        this.neck.render(partTicks);
        this.mane.render(partTicks);
        this.tail.render(partTicks);
        this.ear1.render(partTicks);
        this.ear2.render(partTicks);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float rotFloat, float rotYaw, float rotPitch, float partTicks, Entity entity) {
        this.head.rotateAngleY = rotYaw / 57.29578F;
        this.head.rotateAngleX = (rotPitch / 57.29578F) + 0.79F;

        this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.25F;
        this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.25F;
        this.leg3.rotateAngleX = this.leg1.rotateAngleX;
        this.leg4.rotateAngleX = this.leg2.rotateAngleX;

        this.leg1.rotateAngleZ = 0.0F;
        this.leg2.rotateAngleZ = 0.0F;
        this.leg1.rotateAngleY = 0.0F;
        this.leg2.rotateAngleY = 0.0F;
        this.leg3.rotateAngleY = 0.0F;
        this.leg4.rotateAngleY = 0.0F;

        this.tail.rotateAngleX = 0.3F + (this.leg1.rotateAngleX * this.leg1.rotateAngleX);
        this.mane.rotateAngleX = this.neck.rotateAngleX = -0.6F;
        this.ear1.rotateAngleX = this.ear2.rotateAngleX = this.head.rotateAngleX;
        this.ear1.rotateAngleY = this.ear2.rotateAngleY = this.head.rotateAngleY;
    }
}
