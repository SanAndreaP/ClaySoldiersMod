/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.renderer.soldier;

import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.client.model.ModelGoggleBand;
import de.sanandrew.mods.claysoldiers.client.model.ModelLeatherArmor;
import de.sanandrew.mods.claysoldiers.client.renderer.RenderClaySoldier;
import de.sanandrew.mods.claysoldiers.entity.EntityClaySoldier;
import de.sanandrew.mods.claysoldiers.registry.upgrade.UpgradeRegistry;
import de.sanandrew.mods.claysoldiers.util.Resources;
import de.sanandrew.mods.sanlib.lib.client.util.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.EnumMap;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class LayerLeatherArmor
        implements LayerRenderer<EntityClaySoldier>
{
    private final ModelLeatherArmor modelLeatherArmor;
    private final ModelLeatherArmor modelRabbitHide;
    private final RenderClaySoldier renderer;

    public LayerLeatherArmor(RenderClaySoldier renderer) {
        this.renderer = renderer;
        this.modelLeatherArmor = new ModelLeatherArmor(Resources.ENTITY_SOLDIER_LEATHER_ARMOR.resource);
        this.modelRabbitHide = new ModelLeatherArmor(Resources.ENTITY_SOLDIER_RABBIT_HIDE.resource);
    }

    @Override
    public void doRenderLayer(EntityClaySoldier soldier, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        ISoldierUpgradeInst instL = soldier.getUpgradeInstance(UpgradeRegistry.MC_LEATHER, EnumUpgradeType.MISC);
        ISoldierUpgradeInst instR = soldier.getUpgradeInstance(UpgradeRegistry.MC_RABBITHIDE, EnumUpgradeType.MISC);
        ModelLeatherArmor model = instL != null ? this.modelLeatherArmor : instR != null ? this.modelRabbitHide : null;
        if( model != null ) {
            if( model.texture != null ) {
                this.renderer.bindTexture(model.texture);
            }

            GlStateManager.pushMatrix();
            this.renderer.modelBipedMain.bipedBody.postRender(scale);
            model.renderBody(scale);
            GlStateManager.popMatrix();

            GlStateManager.pushMatrix();
            this.renderer.modelBipedMain.bipedLeftArm.postRender(scale);
            model.renderLeftArm(scale);
            GlStateManager.popMatrix();

            GlStateManager.pushMatrix();
            this.renderer.modelBipedMain.bipedRightArm.postRender(scale);
            model.renderRightArm(scale);
            GlStateManager.popMatrix();
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
