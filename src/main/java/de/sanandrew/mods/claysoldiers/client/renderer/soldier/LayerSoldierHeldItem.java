/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.renderer.soldier;

import de.sanandrew.mods.claysoldiers.api.client.ISoldierRenderer;
import de.sanandrew.mods.claysoldiers.client.renderer.RenderClaySoldier;
import de.sanandrew.mods.claysoldiers.entity.EntityClaySoldier;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SideOnly(Side.CLIENT)
public class LayerSoldierHeldItem
        implements LayerRenderer<EntityClaySoldier>
{
    private RenderClaySoldier renderer;

    public LayerSoldierHeldItem(RenderClaySoldier renderer) {
        this.renderer = renderer;
    }

    public void doRenderLayer(EntityClaySoldier soldier, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        GlStateManager.pushMatrix();

        List<Integer> priorities = new ArrayList<>(this.renderer.renderHooks.keySet());
        priorities.sort(null);
        Collections.reverse(priorities);

        mainHand:
        for( int priority : priorities ) {
            for( ISoldierRenderer renderer : this.renderer.renderHooks.get(priority) ) {
                if( renderHeldItem(soldier, EnumHandSide.RIGHT, renderer) ) {
                    break mainHand;
                }
            }
        }

        offHand:
        for( int priority : priorities ) {
            for( ISoldierRenderer renderer : this.renderer.renderHooks.get(priority) ) {
                if( renderHeldItem(soldier, EnumHandSide.LEFT, renderer) ) {
                    break offHand;
                }
            }
        }

        GlStateManager.popMatrix();
    }

    private boolean renderHeldItem(EntityClaySoldier soldier, EnumHandSide handSide, ISoldierRenderer renderer) {
        GlStateManager.pushMatrix();

        if( renderer.doHandRendererSetup(soldier, handSide) ) {
            if (soldier.isSneaking()) {
                GlStateManager.translate(0.0F, 0.2F, 0.0F);
            }

            ((ModelBiped) this.renderer.getMainModel()).postRenderArm(0.0625F, handSide);
            GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            boolean flag = handSide == EnumHandSide.LEFT;
            GlStateManager.translate((float) (flag ? -1 : 1) / 16.0F, 0.125F, -0.5F);
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
