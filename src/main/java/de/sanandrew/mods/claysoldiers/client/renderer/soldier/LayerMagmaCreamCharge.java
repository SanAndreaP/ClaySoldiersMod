/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.renderer.soldier;

import de.sanandrew.mods.claysoldiers.client.model.ModelClaySoldier;
import de.sanandrew.mods.claysoldiers.client.renderer.RenderClaySoldier;
import de.sanandrew.mods.claysoldiers.entity.EntityClaySoldier;
import de.sanandrew.mods.claysoldiers.registry.effect.EffectRegistry;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeMagmaCream;
import de.sanandrew.mods.claysoldiers.util.Resources;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class LayerMagmaCreamCharge
        implements LayerRenderer<EntityClaySoldier>
{
    private final RenderClaySoldier renderer;
    private final ModelClaySoldier chargedModel = new ModelClaySoldier(0.5F);

    public LayerMagmaCreamCharge(RenderClaySoldier renderer) {
        this.renderer = renderer;
    }

    @Override
    public void doRenderLayer(EntityClaySoldier soldier, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if( soldier.hasEffect(EffectRegistry.TIME_BOMB) ) {
            float durationPerc = soldier.getEffectDurationLeft(EffectRegistry.TIME_BOMB) / (float) UpgradeMagmaCream.MAX_TIME_DETONATION;
            boolean isInvisible = soldier.isInvisible();
            float ticks = soldier.ticksExisted + partialTicks;

            GlStateManager.depthMask(!isInvisible);
            this.renderer.bindTexture(Resources.LIGHTNING_OVERLAY.resource);
            GlStateManager.matrixMode(GL11.GL_TEXTURE);
            GlStateManager.loadIdentity();
            GlStateManager.translate(ticks * 0.01F, ticks * 0.01F, 0.0F);
            GlStateManager.matrixMode(GL11.GL_MODELVIEW);
            GlStateManager.enableBlend();
            GlStateManager.color(0.5F * (1.0F - durationPerc), 0.5F * durationPerc, 0.0F, 1.0F);
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
            this.chargedModel.setModelAttributes(this.renderer.getMainModel());
            this.chargedModel.render(soldier, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            GlStateManager.matrixMode(GL11.GL_TEXTURE);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(GL11.GL_MODELVIEW);
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
            GlStateManager.depthMask(isInvisible);
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
