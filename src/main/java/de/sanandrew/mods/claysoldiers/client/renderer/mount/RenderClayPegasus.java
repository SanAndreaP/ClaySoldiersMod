/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.renderer.mount;

import de.sanandrew.mods.claysoldiers.client.model.mount.ModelClayPegasus;
import de.sanandrew.mods.claysoldiers.entity.mount.EntityPegasus;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderClayPegasus
        extends RenderLiving<EntityPegasus>
{
    public RenderClayPegasus(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelClayPegasus(), 0.2F);
    }

    @Override
    protected void renderLivingAt(EntityPegasus entityLivingBaseIn, double x, double y, double z) {
        super.renderLivingAt(entityLivingBaseIn, x, y, z);
        GlStateManager.scale(0.6F, 0.6F, 0.6F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityPegasus entity) {
        return entity.getTexture();
    }
}
