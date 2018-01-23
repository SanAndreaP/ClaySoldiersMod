/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.renderer.mount;

import de.sanandrew.mods.claysoldiers.client.model.mount.ModelTurtle;
import de.sanandrew.mods.claysoldiers.entity.mount.EntityTurtle;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderTurtle
        extends RenderLiving<EntityTurtle>
{
    public RenderTurtle(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelTurtle(), 0.2F);
    }

    @Override
    protected void renderLivingAt(EntityTurtle entityLivingBaseIn, double x, double y, double z) {
        super.renderLivingAt(entityLivingBaseIn, x, y, z);
        GlStateManager.scale(0.6F, 0.6F, 0.6F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityTurtle entity) {
        return entity.getTexture();
    }
}
