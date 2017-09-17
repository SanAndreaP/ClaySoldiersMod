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
import de.sanandrew.mods.claysoldiers.registry.upgrade.Upgrades;
import de.sanandrew.mods.claysoldiers.util.Resources;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityCreature;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerCape
        implements LayerRenderer<EntityCreature>
{
    private final ISoldierRender<?, ?> renderer;

    public LayerCape(ISoldierRender<?, ?> renderer) {
        this.renderer = renderer;
    }

    @Override
    public void doRenderLayer(EntityCreature creature, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if( creature instanceof ISoldier ) {
            ISoldier soldier = (ISoldier) creature;
            boolean hasDiamond = soldier.hasUpgrade(Upgrades.MC_DIAMOND, EnumUpgradeType.MISC) || soldier.hasUpgrade(Upgrades.MC_DIAMONDBLOCK, EnumUpgradeType.MISC);
            if( hasDiamond || soldier.hasUpgrade(Upgrades.MC_PAPER, EnumUpgradeType.MISC) ) {
                if( hasDiamond ) {
                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                    this.renderer.bindSoldierTexture(Resources.ENTITY_WEARABLE_CAPE_DIAMOND.resource);
                } else {
                    ISoldierUpgradeInst clrUpg = soldier.getUpgradeInstance(Upgrades.MC_CONCRETEPOWDER, EnumUpgradeType.MISC);
                    if( clrUpg != null ) {
                        float[] clr = EnumDyeColor.byMetadata(clrUpg.getNbtData().getInteger("color")).getColorComponentValues();
                        GlStateManager.color(clr[0], clr[1], clr[2], 1.0F);
                    } else {
                        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                    }
                    this.renderer.bindSoldierTexture(Resources.ENTITY_WEARABLE_CAPE_PAPER.resource);
                }
                GlStateManager.pushMatrix();
                GlStateManager.translate(0.0F, 0.0F, 0.125F);
                double dX = soldier.getChasingPosX(partialTicks) - (creature.prevPosX + (creature.posX - creature.prevPosX) * partialTicks);
                double dY = soldier.getChasingPosY(partialTicks) - (creature.prevPosY + (creature.posY - creature.prevPosY) * partialTicks);
                double dZ = soldier.getChasingPosZ(partialTicks) - (creature.prevPosZ + (creature.posZ - creature.prevPosZ) * partialTicks);
                float yaw = creature.prevRenderYawOffset + (creature.renderYawOffset - creature.prevRenderYawOffset) * partialTicks;
                double sinYaw = MathHelper.sin(yaw * 0.017453292F);
                double cosYaw = (-MathHelper.cos(yaw * 0.017453292F));
                float rotY = (float) dY * 10.0F;
                rotY = MathHelper.clamp(rotY, -6.0F, 32.0F);
                float rotX = (float) (dX * sinYaw + dZ * cosYaw) * 100.0F;
                float rotZ = (float) (dX * cosYaw - dZ * sinYaw) * 100.0F;

                if (rotX < 0.0F) {
                    rotX = 0.0F;
                }

                float f4 = creature.prevRotationYaw + (creature.rotationYaw - creature.prevRotationYaw) * partialTicks;
                rotY = rotY + MathHelper.sin((creature.prevDistanceWalkedModified + (creature.distanceWalkedModified - creature.prevDistanceWalkedModified) * partialTicks) * 6.0F) * 32.0F * f4;

                if (creature.isSneaking()) {
                    rotY += 25.0F;
                }

                GlStateManager.rotate(6.0F + rotX / 2.0F + rotY, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(rotZ / 2.0F, 0.0F, 0.0F, 1.0F);
                GlStateManager.rotate(-rotZ / 2.0F, 0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                ModelCape.INSTANCE.renderCape();
                GlStateManager.popMatrix();
            }
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }

    private static final class ModelCape
            extends ModelBase
    {
        static final ModelCape INSTANCE = new ModelCape();

        private final ModelRenderer cape;

        private ModelCape() {
            this.cape = new ModelRenderer(this, 0, 0);
            this.cape.setTextureSize(64, 64);
            this.cape.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, 0.0F);
        }

        void renderCape() {
            this.cape.render(0.0625F);
        }
    }
}
