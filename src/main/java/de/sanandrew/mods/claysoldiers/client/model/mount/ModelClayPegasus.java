/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.model.mount;

import de.sanandrew.mods.claysoldiers.entity.mount.EntityPegasus;
import de.sanandrew.mods.claysoldiers.util.Resources;
import de.sanandrew.mods.sanlib.lib.client.ModelJsonLoader;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class ModelClayPegasus
        extends ModelClayHorse
{
    private float wingSwingAmount;

    public ModelRenderer wingL;
    public ModelRenderer wingR;

    public ModelClayPegasus() {
        this.jsonLoader = ModelJsonLoader.create(this, Resources.MODEL_CLAY_PEGASUS.resource, "head", "body", "leg1", "leg2", "leg3", "leg4", "tail", "wingL", "wingR");
    }

    @Override
    public void onReload(IResourceManager iResourceManager, ModelJsonLoader<ModelClayHorse, ModelJsonLoader.ModelJson> modelJsonLoader) {
        super.onReload(iResourceManager, modelJsonLoader);

        this.wingL = modelJsonLoader.getBox("wingL");
        this.wingR = modelJsonLoader.getBox("wingR");
    }

    @Override
    public void setLivingAnimations(EntityLivingBase livingBase, float limbSwing, float limbSwingAmount, float partialTickTime) {
        super.setLivingAnimations(livingBase, limbSwing, limbSwingAmount, partialTickTime);

        EntityPegasus pegasus = (EntityPegasus) livingBase;
        this.wingSwingAmount = (pegasus.wingSwing + (pegasus.wingSwing - pegasus.prevWingSwing) * partialTickTime);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);

        this.wingL.rotateAngleY = (float) (Math.sin(this.wingSwingAmount) / 6.0F);
        this.wingR.rotateAngleY = (float) -(Math.sin(this.wingSwingAmount) / 6.0F);
        this.wingL.rotateAngleZ = (float) (Math.cos(this.wingSwingAmount) / (entityIn.onGround ? 10.0F : 1.5F));
        this.wingR.rotateAngleZ = (float) -(Math.cos(this.wingSwingAmount) / (entityIn.onGround ? 10.0F : 1.5F));
    }
}
