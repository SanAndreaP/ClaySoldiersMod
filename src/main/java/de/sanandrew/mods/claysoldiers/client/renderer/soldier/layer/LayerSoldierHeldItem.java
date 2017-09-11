/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.renderer.soldier.layer;

import de.sanandrew.mods.claysoldiers.api.client.ISoldierRenderHook;
import de.sanandrew.mods.claysoldiers.api.client.soldier.ISoldierRender;
import de.sanandrew.mods.claysoldiers.api.soldier.ISoldier;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityCreature;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;
import java.util.Queue;

@SideOnly(Side.CLIENT)
public class LayerSoldierHeldItem
        implements LayerRenderer<EntityCreature>
{
    private ISoldierRender<?, ?> renderer;

    public LayerSoldierHeldItem(ISoldierRender<?, ?> renderer) {
        this.renderer = renderer;
    }

    public void doRenderLayer(EntityCreature creature, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if( !(creature instanceof ISoldier) ) { return; }
        ISoldier soldier = (ISoldier) creature;

        GlStateManager.pushMatrix();

        boolean doMain = true;
        boolean doOff = true;
        for( Map.Entry<Integer, Queue<ISoldierRenderHook>> entry : this.renderer.getRenderHookDesc().entrySet() ) {
            for( ISoldierRenderHook hook : entry.getValue() ) {
                if( doMain && renderHeldItem(soldier, EnumHandSide.RIGHT, hook) ) {
                    doMain = false;
                }

                if( doOff && renderHeldItem(soldier, EnumHandSide.LEFT, hook) ) {
                    doOff = false;
                }
            }

            if( !doMain && !doOff ) {
                break;
            }
        }

        GlStateManager.popMatrix();
    }

    private boolean renderHeldItem(ISoldier soldier, EnumHandSide handSide, ISoldierRenderHook renderer) {
        GlStateManager.pushMatrix();

        if( renderer.doHandRendererSetup(soldier, handSide) ) {
            if (soldier.getEntity().isSneaking()) {
                GlStateManager.translate(0.0F, 0.2F, 0.0F);
            }

            this.renderer.getSoldierModel().postRenderArm(0.0625F, handSide);
            GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            boolean flag = handSide == EnumHandSide.LEFT;
            GlStateManager.translate((flag ? -1 : 1) / 16.0F, 0.125F, -0.5F);
        }

        boolean ret = renderer.onHandRender(soldier, this.renderer, handSide);
        GlStateManager.popMatrix();

        return ret;
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
