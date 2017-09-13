/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.renderer.soldier.layer;

import de.sanandrew.mods.claysoldiers.api.client.soldier.ISoldierRender;
import de.sanandrew.mods.claysoldiers.api.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.client.model.ModelCrown;
import de.sanandrew.mods.claysoldiers.client.model.ModelGoggleBand;
import de.sanandrew.mods.claysoldiers.registry.upgrade.Upgrades;
import de.sanandrew.mods.sanlib.lib.client.util.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityCreature;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.EnumMap;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class LayerCrown
        implements LayerRenderer<EntityCreature>
{
    private final ModelCrown model;
    private final ISoldierRender<?, ?> renderer;

    public LayerCrown(ISoldierRender<?, ?> renderer) {
        this.renderer = renderer;
        this.model = new ModelCrown();
    }

    @Override
    public void doRenderLayer(EntityCreature creature, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if( !(creature instanceof ISoldier) ) {
            return;
        }

        ISoldier soldier = (ISoldier) creature;

        if( soldier.hasUpgrade(Upgrades.MC_GOLDNUGGET, EnumUpgradeType.MISC) ) {
            GlStateManager.pushMatrix();
            this.renderer.getSoldierModel().bipedHead.postRender(scale);

            if( this.model.texture != null ) {
                this.renderer.bindSoldierTexture(this.model.texture);
                this.model.render(scale);
            }

            GlStateManager.popMatrix();
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
