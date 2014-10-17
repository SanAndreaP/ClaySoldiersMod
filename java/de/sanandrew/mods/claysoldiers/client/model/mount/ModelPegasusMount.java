/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.model.mount;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.mods.claysoldiers.entity.mount.EntityPegasusMount;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

@SideOnly(Side.CLIENT)
public class ModelPegasusMount
        extends ModelHorseMount
{
    public float wingSwingAmount;
    public boolean isOnGround;
    public ModelRenderer wingLeft;
    public ModelRenderer wingRight;

    public ModelPegasusMount() {
        super();

        this.wingLeft = new ModelRenderer(this, 0, 22);
        this.wingLeft.addBox(-12.5F, 0.25F, -2.25F, 13, 1, 5, 0.0F);
        this.wingLeft.setRotationPoint(-1.5F, -0.5F, 0.0F);

        this.wingRight = new ModelRenderer(this, 0, 22);
        this.wingRight.mirror = true;
        this.wingRight.addBox(-0.5F, 0.25F, -2.25F, 13, 1, 5, 0.0F);
        this.wingRight.setRotationPoint(1.5F, -0.5F, 0.0F);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float rotFloat, float rotYaw, float rotPitch, float partTicks) {
        EntityPegasusMount pegasus = (EntityPegasusMount) entity;

        this.isOnGround = pegasus.onGround;
        this.wingSwingAmount = (pegasus.wingSwingStep + (pegasus.wingSwing - pegasus.prevWingSwing) * partTicks);
        pegasus.wingSwingStep = this.wingSwingAmount;

        super.render(entity, limbSwing, limbSwingAmount, rotFloat, rotYaw, rotPitch, partTicks);

        this.wingLeft.render(partTicks);
        this.wingRight.render(partTicks);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float rotFloat, float rotYaw, float rotPitch, float partTicks, Entity entity) {
        super.setRotationAngles(limbSwing, limbSwingAmount, rotFloat, rotYaw, rotPitch, partTicks, entity);

        this.wingLeft.rotateAngleY = 0.2F;
        this.wingRight.rotateAngleY = -0.2F;
        this.wingLeft.rotateAngleZ = -0.125F;
        this.wingRight.rotateAngleZ = 0.125F;

        this.wingLeft.rotateAngleY += Math.sin(this.wingSwingAmount) / 6.0F;
        this.wingRight.rotateAngleY -= Math.sin(this.wingSwingAmount) / 6.0F;
        this.wingLeft.rotateAngleZ -= Math.cos(this.wingSwingAmount) / (this.isOnGround ? 10.0F : 1.5F);
        this.wingRight.rotateAngleZ += Math.cos(this.wingSwingAmount) / (this.isOnGround ? 10.0F : 1.5F);
    }
}
