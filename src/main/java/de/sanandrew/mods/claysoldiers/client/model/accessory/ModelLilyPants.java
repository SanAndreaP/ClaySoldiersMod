/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.model.accessory;

import de.sanandrew.mods.claysoldiers.util.Resources;
import de.sanandrew.mods.sanlib.lib.client.ModelJsonHandler;
import de.sanandrew.mods.sanlib.lib.client.ModelJsonLoader;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelLilyPants
        extends ModelBase
        implements ModelJsonHandler<ModelLilyPants, ModelJsonLoader.ModelJson>
{
    public ModelRenderer body;
    public ModelRenderer leftLeg;
    public ModelRenderer rightLeg;
    private ModelJsonLoader<ModelLilyPants, ModelJsonLoader.ModelJson> jsonLoader;
    public ResourceLocation texture;

    public ModelLilyPants() {
        this.jsonLoader = ModelJsonLoader.create(this, Resources.MODEL_SOLDIER_LILYPANTS.resource, "body", "leftLeg", "rightLeg");
        this.jsonLoader.load();
    }

    @Override
    public void onReload(IResourceManager iResourceManager, ModelJsonLoader modelJsonLoader) {
        modelJsonLoader.load();
        this.body = modelJsonLoader.getBox("body");
        this.leftLeg = modelJsonLoader.getBox("leftLeg");
        this.rightLeg = modelJsonLoader.getBox("rightLeg");
    }

    public void renderBody(float scale) {
        if( this.body != null ) {
            this.body.render(scale);
        }
    }

    public void renderLeftLeg(float scale) {
        if( this.leftLeg != null ) {
            this.leftLeg.render(scale);
        }
    }

    public void renderRightLeg(float scale) {
        if( this.rightLeg != null ) {
            this.rightLeg.render(scale);
        }
    }

    @Override
    public void setTexture(String s) {
        this.texture = new ResourceLocation(s);
    }
}
