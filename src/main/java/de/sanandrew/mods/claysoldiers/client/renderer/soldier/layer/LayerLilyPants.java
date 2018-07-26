/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.renderer.soldier.layer;

import de.sanandrew.mods.claysoldiers.api.client.soldier.ISoldierRender;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.client.model.accessory.ModelLilyPants;
import de.sanandrew.mods.claysoldiers.registry.upgrade.Upgrades;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityCreature;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerLilyPants
        implements LayerRenderer<EntityCreature>
{
    private final ModelLilyPants modelLilyPants;
    private final ISoldierRender<?, ?> renderer;

    public LayerLilyPants(ISoldierRender<?, ?> renderer) {
        this.renderer = renderer;
        this.modelLilyPants = new ModelLilyPants();
    }

    @Override
    public void doRenderLayer(EntityCreature creature, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if( !(creature instanceof ISoldier) ) { return; }
        ISoldier soldier = (ISoldier) creature;

        if( soldier.hasUpgrade(Upgrades.MC_LILYPAD, EnumUpgradeType.MISC) ) {
            if( this.modelLilyPants.texture != null ) {
                this.renderer.bindSoldierTexture(this.modelLilyPants.texture);
            }

            GlStateManager.pushMatrix();
            this.renderer.getSoldierModel().bipedBody.postRender(scale);
            this.modelLilyPants.renderBody(scale);
            GlStateManager.popMatrix();

            GlStateManager.pushMatrix();
            this.renderer.getSoldierModel().bipedLeftLeg.postRender(scale);
            this.modelLilyPants.renderLeftLeg(scale);
            GlStateManager.popMatrix();

            GlStateManager.pushMatrix();
            this.renderer.getSoldierModel().bipedRightLeg.postRender(scale);
            this.modelLilyPants.renderRightLeg(scale);
            GlStateManager.popMatrix();
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
