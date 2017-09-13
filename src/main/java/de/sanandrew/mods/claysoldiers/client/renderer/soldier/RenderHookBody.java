/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.renderer.soldier;

import de.sanandrew.mods.claysoldiers.api.client.ISoldierRenderHook;
import de.sanandrew.mods.claysoldiers.api.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.registry.upgrade.Upgrades;
import de.sanandrew.mods.sanlib.lib.client.util.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderHookBody
        implements ISoldierRenderHook
{
    private static final ItemStack FEATHER = new ItemStack(Items.FEATHER);

    public RenderHookBody() { }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public void renderModelPre(ISoldier<?> soldier, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        if( soldier.hasUpgrade(Upgrades.MC_EGG, EnumUpgradeType.MISC) ) {
            GlStateManager.enableBlend();
            GlStateManager.disableAlpha();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.DST_COLOR);
        }

        if( soldier.hasUpgrade(Upgrades.MC_GLOWSTONE, EnumUpgradeType.MISC) ) {
            int brightness = 0xF0;
            int brightX = brightness % 65536;
            int brightY = brightness / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightX, brightY);
        }
    }

    @Override
    public void renderModelPost(ISoldier<?> soldier, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        if( soldier.hasUpgrade(Upgrades.MC_EGG, EnumUpgradeType.MISC) ) {
            GlStateManager.enableAlpha();
            GlStateManager.disableBlend();
        }

        if( soldier.hasUpgrade(Upgrades.MC_GLOWSTONE, EnumUpgradeType.MISC) ) {
            int brightness = soldier.getEntity().getBrightnessForRender();
            int brightX = brightness % 65536;
            int brightY = brightness / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightX, brightY);
        }

        if( soldier.hasUpgrade(Upgrades.MC_FEATHER, EnumUpgradeType.MISC) && !soldier.getEntity().onGround ) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, -0.55F, 0.1F);
            RenderUtils.renderStackInWorld(FEATHER, 0.0D, 0.0D, 0.0D, 90.0F, 0.0F, 125.0F, 1.75D);
            GlStateManager.popMatrix();
        }
    }
}
