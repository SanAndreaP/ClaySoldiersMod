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
import de.sanandrew.mods.claysoldiers.client.model.accessory.ModelLeatherArmor;
import de.sanandrew.mods.claysoldiers.registry.upgrade.Upgrades;
import de.sanandrew.mods.claysoldiers.util.Resources;
import de.sanandrew.mods.sanlib.lib.ColorObj;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityCreature;
import net.minecraft.item.EnumDyeColor;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerLeatherArmor
        implements LayerRenderer<EntityCreature>
{
    private final ModelLeatherArmor modelLeatherArmor;
    private final ModelLeatherArmor modelRabbitHide;
    private final ModelLeatherArmor modelPadding;
    private final ISoldierRender<?, ?> renderer;

    public LayerLeatherArmor(ISoldierRender<?, ?> renderer) {
        this.renderer = renderer;
        this.modelLeatherArmor = new ModelLeatherArmor(Resources.MODEL_SOLDIER_LEATHER_ARMOR.resource);
        this.modelRabbitHide = new ModelLeatherArmor(Resources.MODEL_SOLDIER_RABBIT_HIDE.resource);
        this.modelPadding = new ModelLeatherArmor(Resources.MODEL_SOLDIER_WOOL_PADDING.resource);
    }

    @Override
    public void doRenderLayer(EntityCreature creature, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if( !(creature instanceof ISoldier) ) {
            return;
        }

        ISoldier soldier = (ISoldier) creature;

        if( soldier.hasUpgrade(Upgrades.MC_LEATHER, EnumUpgradeType.MISC) ) {
            this.renderModel(this.modelLeatherArmor, scale);
        } else if( soldier.hasUpgrade(Upgrades.MC_RABBITHIDE, EnumUpgradeType.MISC) ) {
            this.renderModel(this.modelRabbitHide, scale);
        }

        ISoldierUpgradeInst woolInst = soldier.getUpgradeInstance(Upgrades.EM_WOOL, EnumUpgradeType.ENHANCEMENT);
        if( woolInst != null ) {
            ColorObj cObj = new ColorObj(EnumDyeColor.byMetadata(woolInst.getNbtData().getInteger("color")).getColorValue());
            GlStateManager.color(cObj.fRed(), cObj.fGreen(), cObj.fBlue());
            this.renderModel(this.modelPadding, scale);
            GlStateManager.color(1.0F, 1.0F, 1.0F);
        }
    }

    private void renderModel(ModelLeatherArmor model, float scale) {
        if( model.texture != null ) {
            this.renderer.bindSoldierTexture(model.texture);
        }

        GlStateManager.pushMatrix();
        this.renderer.getSoldierModel().bipedBody.postRender(scale);
        model.renderBody(scale);
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        this.renderer.getSoldierModel().bipedLeftArm.postRender(scale);
        model.renderLeftArm(scale);
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        this.renderer.getSoldierModel().bipedRightArm.postRender(scale);
        model.renderRightArm(scale);
        GlStateManager.popMatrix();
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
