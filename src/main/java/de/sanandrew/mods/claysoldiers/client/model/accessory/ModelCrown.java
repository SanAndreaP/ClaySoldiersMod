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

import java.util.Arrays;

@SideOnly(Side.CLIENT)
public class ModelCrown
        extends ModelBase
        implements ModelJsonHandler<ModelCrown, ModelJsonLoader.ModelJson>
{
    public ModelRenderer crown;
    private final ModelJsonLoader<ModelCrown, ModelJsonLoader.ModelJson> jsonLoader;
    public ResourceLocation texture;

    public ModelCrown() {
        this.jsonLoader = ModelJsonLoader.create(this, Resources.MODEL_SOLDIER_CROWN.resource, "crown");
        this.jsonLoader.load();
    }

    @Override
    public void onReload(IResourceManager iResourceManager, ModelJsonLoader modelJsonLoader) {
        modelJsonLoader.load();
        this.crown = modelJsonLoader.getBox("crown");
    }

    public void render(float scale) {
        if( this.jsonLoader.isLoaded() ) {
            Arrays.stream(this.jsonLoader.getMainBoxes()).forEach(box -> box.render(scale));
        }
    }

    @Override
    public void setTexture(String s) {
        this.texture = new ResourceLocation(s);
    }
}
