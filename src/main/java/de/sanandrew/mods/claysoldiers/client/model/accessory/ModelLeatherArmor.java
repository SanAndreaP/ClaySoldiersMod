/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.model.accessory;

import de.sanandrew.mods.sanlib.lib.client.ModelJsonHandler;
import de.sanandrew.mods.sanlib.lib.client.ModelJsonLoader;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelLeatherArmor
        extends ModelBase
        implements ModelJsonHandler<ModelLeatherArmor, ModelJsonLoader.ModelJson>
{
    public ModelRenderer body;
    public ModelRenderer leftArm;
    public ModelRenderer rightArm;
    public ResourceLocation texture;

    public ModelLeatherArmor(ResourceLocation modelLoc) {
        ModelJsonLoader.create(this, modelLoc, "body", "leftArm", "rightArm").load();
    }

    @Override
    public void onReload(IResourceManager iResourceManager, ModelJsonLoader modelJsonLoader) {
        modelJsonLoader.load();
        this.body = modelJsonLoader.getBox("body");
        this.leftArm = modelJsonLoader.getBox("leftArm");
        this.rightArm = modelJsonLoader.getBox("rightArm");
    }

    public void renderBody(float scale) {
        if( this.body != null ) {
            this.body.render(scale);
        }
    }

    public void renderLeftArm(float scale) {
        if( this.leftArm != null ) {
            this.leftArm.render(scale);
        }
    }

    public void renderRightArm(float scale) {
        if( this.rightArm != null ) {
            this.rightArm.render(scale);
        }
    }

    @Override
    public void setTexture(String s) {
        this.texture = new ResourceLocation(s);
    }
}
