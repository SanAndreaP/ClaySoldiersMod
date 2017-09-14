/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.model.mount;

import de.sanandrew.mods.claysoldiers.util.Resources;
import de.sanandrew.mods.sanlib.lib.client.ModelJsonHandler;
import de.sanandrew.mods.sanlib.lib.client.ModelJsonLoader;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;

@SideOnly(Side.CLIENT)
public class ModelClayHorse
        extends ModelBase
        implements ModelJsonHandler<ModelClayHorse, ModelJsonLoader.ModelJson>
{
    public ModelRenderer head;
    public ModelRenderer body;
    public ModelRenderer leg1;
    public ModelRenderer leg2;
    public ModelRenderer leg3;
    public ModelRenderer leg4;
    public ModelRenderer tail;

    private ModelJsonLoader<ModelClayHorse, ModelJsonLoader.ModelJson> jsonLoader;

    public ModelClayHorse() {
        this.jsonLoader = ModelJsonLoader.create(this, Resources.MODEL_CLAY_HORSE.resource, "head", "body", "leg1", "leg2", "leg3", "leg4", "tail");
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if( this.jsonLoader.isLoaded() ) {
            Arrays.stream(this.jsonLoader.getMainBoxes()).forEach(box -> box.render(scale));
        }
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        this.head.rotateAngleY = netHeadYaw / 57.29578F;
        this.head.rotateAngleX = (headPitch / 57.29578F) + 0.79F;

        this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.25F;
        this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.25F;
        this.leg3.rotateAngleX = this.leg1.rotateAngleX;
        this.leg4.rotateAngleX = this.leg2.rotateAngleX;

        this.tail.rotateAngleX = 0.3F + (this.leg1.rotateAngleX * this.leg1.rotateAngleX);
    }

    @Override
    public void onReload(IResourceManager iResourceManager, ModelJsonLoader<ModelClayHorse, ModelJsonLoader.ModelJson> modelJsonLoader) {
        modelJsonLoader.load();

        this.head = modelJsonLoader.getBox("head");
        this.body = modelJsonLoader.getBox("body");
        this.leg1 = modelJsonLoader.getBox("leg1");
        this.leg2 = modelJsonLoader.getBox("leg2");
        this.leg3 = modelJsonLoader.getBox("leg3");
        this.leg4 = modelJsonLoader.getBox("leg4");
        this.tail = modelJsonLoader.getBox("tail");
    }

    @Override
    public void setTexture(String s) { }
}
