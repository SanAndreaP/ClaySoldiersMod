/*******************************************************************************************************************
 * Name:      ModelGecko.java
 * Author:    SanAndreasP
 * Copyright: SanAndreasP and SilverChiren
 * License:   Attribution-NonCommercial-ShareAlike 3.0 Unported (http://creativecommons.org/licenses/by-nc-sa/3.0/)
 *******************************************************************************************************************/

package de.sanandrew.mods.claysoldiers.client.model.mount;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelGeckoMount
        extends ModelBase
{
    public ModelRenderer head;
    public ModelRenderer body;
    public ModelRenderer rightarm;
    public ModelRenderer leftarm;
    public ModelRenderer rightleg;
    public ModelRenderer leftleg;
    public ModelRenderer nose;
    public ModelRenderer tail;

    public ModelGeckoMount() {
        this.head = new ModelRenderer(this, 0, 0);
        this.head.addBox(-1.0F, 0.0F, -3.0F, 2, 1, 2);
        this.head.setRotationPoint(0.0F, 21.0F, -4.0F);
        setRotation(this.head, 0.0F, 0.0F, 0.0F);

        this.body = new ModelRenderer(this, 8, 0);
        this.body.addBox(-1.5F, -1.0F, -1.5F, 3, 1, 7);
        this.body.setRotationPoint(0.0F, 22.0F, -2.0F);
        setRotation(this.body, 0.0F, 0.0F, 0.0F);

        this.rightarm = new ModelRenderer(this, 0, 3);
        this.rightarm.addBox(-1.0F, 0.0F, -1.0F, 1, 4, 1);
        this.rightarm.setRotationPoint(-1.0F, 22.0F, -2.0F);
        this.rightarm.mirror = true;
        setRotation(this.rightarm, 0.0F, -0.2602503F, 1.226894F);

        this.leftarm = new ModelRenderer(this, 0, 3);
        this.leftarm.addBox(0.0F, 0.0F, -1.0F, 1, 4, 1);
        this.leftarm.setRotationPoint(1.0F, 22.0F, -2.0F);
        this.leftarm.mirror = true;
        setRotation(this.leftarm, 0.0F, 0.2602503F, -1.226894F);

        this.rightleg = new ModelRenderer(this, 4, 3);
        this.rightleg.addBox(-1.0F, 0.0F, 0.0F, 1, 4, 1);
        this.rightleg.setRotationPoint(-1.0F, 22.0F, 2.0F);
        this.rightleg.mirror = true;
        setRotation(this.rightleg, 0.0F, 0.2602503F, 1.226894F);

        this.leftleg = new ModelRenderer(this, 4, 3);
        this.leftleg.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1);
        this.leftleg.setRotationPoint(1.0F, 22.0F, 2.0F);
        setRotation(this.leftleg, 0.0F, -0.2602503F, -1.226894F);

        this.nose = new ModelRenderer(this, 0, 8);
        this.nose.addBox(-1.5F, -1.0F, -1.5F, 3, 2, 2);
        this.nose.setRotationPoint(0.0F, 21.0F, -4.0F);
        setRotation(this.nose, 0.0F, 0.0F, 0.0F);

        this.tail = new ModelRenderer(this, 0, 12);
        this.tail.addBox(-0.5F, 0.0F, -0.5F, 1, 8, 1);
        this.tail.setRotationPoint(0.0F, 21.5F, 3.0F);
        setRotation(this.tail, 1.487144F, 0.0F, 0.0F);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float rotFloat, float rotYaw, float rotPitch, float partTicks) {
        this.setRotationAngles(limbSwing, limbSwingAmount, rotFloat, rotYaw, rotPitch, partTicks, entity);
        head.render(partTicks);
        body.render(partTicks);
        rightarm.render(partTicks);
        leftarm.render(partTicks);
        rightleg.render(partTicks);
        leftleg.render(partTicks);
        nose.render(partTicks);
        tail.render(partTicks);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float rotFloat, float rotYaw, float rotPitch, float partTicks, Entity entity) {
        this.rightarm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 0.5F * limbSwingAmount;
        this.leftarm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 0.5F * limbSwingAmount;
        this.rightleg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 0.5F * limbSwingAmount;
        this.leftleg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 0.5F * limbSwingAmount;
        this.tail.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 0.5F * limbSwingAmount;
    }

    private static void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
