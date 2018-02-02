/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.renderer.layers;

import de.sanandrew.mods.claysoldiers.client.renderer.mount.RenderGecko;
import de.sanandrew.mods.claysoldiers.entity.mount.EntityGecko;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;

public class LayerGeckoSpots
        implements LayerRenderer<EntityGecko>
{
    private final RenderGecko renderGecko;

    public LayerGeckoSpots(RenderGecko renderer) {
        this.renderGecko = renderer;
    }

    @Override
    public void doRenderLayer(EntityGecko gecko, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.renderGecko.bindTexture(gecko.getTextureSpots());
        this.renderGecko.getMainModel().render(gecko, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    }

    @Override
    public boolean shouldCombineTextures() {
        return true;
    }
}
