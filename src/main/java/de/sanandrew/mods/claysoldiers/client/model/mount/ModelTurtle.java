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
public class ModelTurtle
        extends ModelBase
        implements ModelJsonHandler<ModelTurtle, ModelJsonLoader.ModelJson>
{
    public ModelRenderer head;
    public ModelRenderer leg1;
    public ModelRenderer leg2;
    public ModelRenderer leg3;
    public ModelRenderer leg4;

    protected ModelJsonLoader<ModelTurtle, ModelJsonLoader.ModelJson> jsonLoader;

    public ModelTurtle() {
        this.jsonLoader = ModelJsonLoader.create(this, Resources.MODEL_TURTLE.resource, "head", "leg1", "leg2", "leg3", "leg4");
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
        this.head.rotateAngleX = (headPitch / 57.29578F);

        this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 1.6662F + (float) Math.PI) * 1.5F * limbSwingAmount;
        this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 1.6662F) * 1.5F * limbSwingAmount;
        this.leg3.rotateAngleX = this.leg2.rotateAngleX;
        this.leg4.rotateAngleX = this.leg1.rotateAngleX;
    }

    @Override
    public void onReload(IResourceManager iResourceManager, ModelJsonLoader<ModelTurtle, ModelJsonLoader.ModelJson> modelJsonLoader) {
        modelJsonLoader.load();

        this.head = modelJsonLoader.getBox("head");
        this.leg1 = modelJsonLoader.getBox("leg1");
        this.leg2 = modelJsonLoader.getBox("leg2");
        this.leg3 = modelJsonLoader.getBox("leg3");
        this.leg4 = modelJsonLoader.getBox("leg4");
    }

    @Override
    public void setTexture(String s) { }
}
