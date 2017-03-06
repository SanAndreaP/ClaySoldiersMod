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
import java.util.Map;
import java.util.Queue;

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

        boolean doMain = true;
        boolean doOff = true;
        for( Map.Entry<Integer, Queue<ISoldierRenderer>> entry : this.renderer.renderHooks.descendingMap().entrySet() ) {
            for( ISoldierRenderer hook : entry.getValue() ) {
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
