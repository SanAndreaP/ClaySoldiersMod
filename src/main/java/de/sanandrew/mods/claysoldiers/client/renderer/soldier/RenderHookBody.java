/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.renderer.soldier;

import de.sanandrew.mods.claysoldiers.api.client.ISoldierRenderer;
import de.sanandrew.mods.claysoldiers.api.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.client.RenderWorldEventHandler;
import de.sanandrew.mods.claysoldiers.registry.upgrade.UpgradeRegistry;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderHookBody
        implements ISoldierRenderer
{
    public RenderHookBody() { }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public void renderModelPre(ISoldier<?> entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        if( entity.hasUpgrade(UpgradeRegistry.MC_EGG, EnumUpgradeType.MISC) ) {
            GlStateManager.enableBlend();
            GlStateManager.disableAlpha();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.DST_COLOR);
        }

        if( entity.hasUpgrade(UpgradeRegistry.MC_GLOWSTONE, EnumUpgradeType.MISC) ) {
            int brightness = 0xF0;
            int brightX = brightness % 65536;
            int brightY = brightness / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightX, brightY);
        }
    }

    @Override
    public void renderModelPost(ISoldier<?> entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        if( entity.hasUpgrade(UpgradeRegistry.MC_EGG, EnumUpgradeType.MISC) ) {
            GlStateManager.enableAlpha();
            GlStateManager.disableBlend();
        }

        if( entity.hasUpgrade(UpgradeRegistry.MC_GLOWSTONE, EnumUpgradeType.MISC) ) {
            int brightness = entity.getEntity().getBrightnessForRender(RenderWorldEventHandler.partTicks);
            int brightX = brightness % 65536;
            int brightY = brightness / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightX, brightY);
        }
    }
}
