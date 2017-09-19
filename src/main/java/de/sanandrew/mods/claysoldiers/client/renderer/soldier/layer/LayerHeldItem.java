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
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.registry.ItemRegistry;
import de.sanandrew.mods.claysoldiers.registry.effect.Effects;
import de.sanandrew.mods.claysoldiers.registry.upgrade.Upgrades;
import de.sanandrew.mods.sanlib.lib.client.util.RenderUtils;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityCreature;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;
import java.util.Queue;

@SideOnly(Side.CLIENT)
public class LayerHeldItem
        implements LayerRenderer<EntityCreature>
{
    private static final boolean H_AS_EG_G = MiscUtils.RNG.randomInt(1_000_000) == 0;

    private static final ItemStack SHEARBLADE = new ItemStack(ItemRegistry.SHEAR_BLADE);
    private static final ItemStack GRAVEL = new ItemStack(Blocks.GRAVEL);
    private static final ItemStack SNOW = new ItemStack(Blocks.SNOW);
    private static final ItemStack MAGMA = new ItemStack(Blocks.MAGMA);
    private static final ItemStack QUARTZ = new ItemStack(Blocks.QUARTZ_ORE);
    private static final ItemStack SHIELD_NRM = new ItemStack(ItemRegistry.SOLDIER_SHIELD, 1, H_AS_EG_G ? 2 : 0);
    private static final ItemStack SHIELD_STD = new ItemStack(ItemRegistry.SOLDIER_SHIELD, 1, H_AS_EG_G ? 3 : 1);
    private static final ItemStack STICK = new ItemStack(Items.STICK);
    private static final ItemStack ARROW = new ItemStack(Items.ARROW);
    private static final ItemStack BLAZEROD = new ItemStack(Items.BLAZE_ROD);
    private static final ItemStack SPECLEDMELON = new ItemStack(Items.SPECKLED_MELON);
    private static final ItemStack BONE = new ItemStack(Items.BONE);
    private static final ItemStack REDMUSHROOM = new ItemStack(Blocks.RED_MUSHROOM_BLOCK);
    private static final ItemStack STONE = new ItemStack(Blocks.STONE);
    private static final ItemStack WOOD = new ItemStack(Blocks.PLANKS);
    private static final ItemStack REDSTONEBLOCK = new ItemStack(Blocks.REDSTONE_BLOCK);
    private static final ItemStack SLIMEBLOCK = new ItemStack(Blocks.SLIME_BLOCK);
    private static final ItemStack RABBITFOOT = new ItemStack(Items.RABBIT_FOOT);
    private static final ItemStack BRICKS = new ItemStack(Blocks.BRICK_BLOCK);

    private ISoldierRender<?, ?> renderer;

    public LayerHeldItem(ISoldierRender<?, ?> renderer) {
        this.renderer = renderer;
    }

    public void doRenderLayer(EntityCreature creature, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if( !(creature instanceof ISoldier) ) { return; }
        ISoldier soldier = (ISoldier) creature;

        renderHeldItem(soldier, EnumHandSide.RIGHT);
        renderHeldItem(soldier, EnumHandSide.LEFT);
        renderWornItem(soldier, EnumHandSide.RIGHT);
        renderWornItem(soldier, EnumHandSide.LEFT);
    }

    private void renderWornItem(ISoldier soldier, EnumHandSide handSide) {
        GlStateManager.pushMatrix();

        if (soldier.getEntity().isSneaking()) {
            GlStateManager.translate(0.0F, 0.2F, 0.0F);
        }

        if( handSide == EnumHandSide.LEFT ) {
            this.renderer.getSoldierModel().bipedLeftLeg.postRender(0.0625F);
        } else if( handSide == EnumHandSide.RIGHT ) {
            this.renderer.getSoldierModel().bipedRightLeg.postRender(0.0625F);
        }
        GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        boolean flag = handSide == EnumHandSide.LEFT;
        GlStateManager.translate((flag ? -1 : 1) / 16.0F, 0.125F, -0.5F);

        renderFootItem(soldier, handSide == EnumHandSide.RIGHT ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);
        GlStateManager.popMatrix();
    }

    private void renderFootItem(ISoldier soldier, EnumHand hand) {
        if( soldier.hasUpgrade(Upgrades.MC_RABBITFOOT, EnumUpgradeType.MISC) ) {
            RenderUtils.renderStackInWorld(RABBITFOOT, 0.0D, -0.05D, 0.0D, 0.0F, 90.0F, -120.0F, 0.75D);
        }
        switch( hand ) {
            case MAIN_HAND:
                if( soldier.hasEffect(Effects.STICKING_SLIMEBALL) ) {
                    RenderUtils.renderStackInWorld(SLIMEBLOCK, -0.05D, -0.12D, -0.15D, 0.0F, 0.0F, 0.0F, 0.55D);
                }
                if( soldier.hasUpgrade(Upgrades.CR_BRICK, EnumUpgradeType.CORE) ) {
                    RenderUtils.renderStackInWorld(BRICKS, -0.05D, -0.12D, -0.15D, 0.0F, 0.0F, 0.0F, 0.55D);
                }
                break;
            case OFF_HAND:
                if( soldier.hasEffect(Effects.STICKING_SLIMEBALL) ) {
                    RenderUtils.renderStackInWorld(SLIMEBLOCK, 0.05D, -0.12D, -0.15D, 0.0F, 0.0F, 0.0F, 0.55D);
                }
                if( soldier.hasUpgrade(Upgrades.CR_BRICK, EnumUpgradeType.CORE) ) {
                    RenderUtils.renderStackInWorld(BRICKS, 0.05D, -0.12D, -0.15D, 0.0F, 0.0F, 0.0F, 0.55D);
                }
                break;
        }
    }

    private void renderHeldItem(ISoldier soldier, EnumHandSide handSide) {
        GlStateManager.pushMatrix();

        if (soldier.getEntity().isSneaking()) {
            GlStateManager.translate(0.0F, 0.2F, 0.0F);
        }

        this.renderer.getSoldierModel().postRenderArm(0.0625F, handSide);
        GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        boolean flag = handSide == EnumHandSide.LEFT;
        GlStateManager.translate((flag ? -1 : 1) / 16.0F, 0.125F, -0.5F);

        renderHandItem(soldier, handSide == soldier.getEntity().getPrimaryHand() ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);
        GlStateManager.popMatrix();
    }

    private void renderHandItem(ISoldier soldier, EnumHand hand) {
        ISoldierUpgradeInst upgInst;
        switch( hand ) {
            case MAIN_HAND:
                if( soldier.hasUpgrade(Upgrades.MC_REDMUSHROOM, EnumUpgradeType.MISC) ) {
                    RenderUtils.renderStackInWorld(REDMUSHROOM, 0.0D, -0.12D, 0.2D, 0.0F, 0.0F, 0.0F, 0.55D);
                }
                if( soldier.hasUpgrade(Upgrades.MC_REDSTONE, EnumUpgradeType.MISC) ) {
                    RenderUtils.renderStackInWorld(REDSTONEBLOCK, 0.0D, -0.12D, 0.15D, 0.0F, 0.0F, 0.0F, 0.551D);
                }
                if( soldier.hasUpgrade(Upgrades.MC_SLIMEBALL, EnumUpgradeType.MISC) ) {
                    RenderUtils.renderStackInWorld(SLIMEBLOCK, 0.0D, -0.12D, 0.1D, 0.0F, 0.0F, 0.0F, 0.552D);
                }
                if( (upgInst = soldier.getUpgradeInstance(Upgrades.MC_BUTTON, EnumUpgradeType.MISC)) != null ) {
                    if( upgInst.getNbtData().getBoolean("isStone") ) {
                        RenderUtils.renderStackInWorld(STONE, 0.0D, -0.12D, 0.0D, 0.0F, 0.0F, 0.0F, 0.553D);
                    } else {
                        RenderUtils.renderStackInWorld(WOOD, 0.0D, -0.12D, 0.0D, 0.0F, 0.0F, 0.0F, 0.553D);
                    }
                }
                if( soldier.hasUpgrade(Upgrades.EC_FLINT, EnumUpgradeType.ENHANCEMENT) ) {
                    RenderUtils.renderStackInWorld(ARROW, 0.0D, 0.2D, 0.1D, 0.0F, 90.0F, 135.0F, 0.75D);
                } else if( soldier.hasUpgrade(Upgrades.MH_STICK, EnumUpgradeType.MAIN_HAND) ) {
                    RenderUtils.renderStackInWorld(STICK, 0.0D, 0.2D, 0.1D, 0.0F, 90.0F, -45.0F, 0.75D);
                } else if( soldier.hasUpgrade(Upgrades.MH_BLAZEROD, EnumUpgradeType.MAIN_HAND) ) {
                    RenderUtils.renderStackInWorld(BLAZEROD, 0.0D, 0.2D, 0.1D, 0.0F, 90.0F, -45.0F, 0.75D);
                } else if( soldier.hasUpgrade(Upgrades.MOH_SHEARBLADE, EnumUpgradeType.MAIN_HAND) ) {
                    RenderUtils.renderStackInWorld(SHEARBLADE, 0.0D, 0.2D, 0.1D, 0.0F, 90.0F, 135.0F, 0.75D);
                } else if( soldier.hasUpgrade(Upgrades.MH_SPECKLEDMELON, EnumUpgradeType.MAIN_HAND) ) {
                    RenderUtils.renderStackInWorld(SPECLEDMELON, 0.0D, 0.2D, 0.1D, 0.0F, 90.0F, -90.0F, 0.75D);
                } else if( soldier.hasUpgrade(Upgrades.MH_BONE, EnumUpgradeType.MAIN_HAND) ) {
                    RenderUtils.renderStackInWorld(BONE, 0.0D, 0.2D, 0.1D, 0.0F, 90.0F, -45.0F, 0.75D);
                }
                break;
            case OFF_HAND:
                if( soldier.hasUpgrade(Upgrades.EC_IRONBLOCK, EnumUpgradeType.ENHANCEMENT) ) {
                    RenderUtils.renderStackInWorld(SHIELD_STD, 0.0D, 0.2D, 0.0D, 90.0F, 0.0F, 0.0F, 0.75D);
                } else if( soldier.hasUpgrade(Upgrades.MOH_SHEARBLADE, EnumUpgradeType.OFF_HAND) ) {
                    RenderUtils.renderStackInWorld(SHEARBLADE, 0.0D, 0.2D, 0.0D, 0.0F, 90.0F, 135.0F, 0.75D);
                } else if( soldier.hasUpgrade(Upgrades.OH_GRAVEL, EnumUpgradeType.OFF_HAND) ) {
                    RenderUtils.renderStackInWorld(GRAVEL, 0.0D, -0.125D, -0.05D, 0.0F, 0.0F, 0.0F, 0.6D);
                } else if( soldier.hasUpgrade(Upgrades.OH_SNOW, EnumUpgradeType.OFF_HAND) ) {
                    RenderUtils.renderStackInWorld(SNOW, 0.0D, -0.125D, -0.05D, 0.0F, 0.0F, 0.0F, 0.6D);
                } else if( soldier.hasUpgrade(Upgrades.OH_FIRECHARGE, EnumUpgradeType.OFF_HAND) ) {
                    RenderUtils.renderStackInWorld(MAGMA, 0.0D, -0.125D, -0.05D, 0.0F, 0.0F, 0.0F, 0.6D);
                } else if( soldier.hasUpgrade(Upgrades.OH_QUARTZ, EnumUpgradeType.OFF_HAND) ) {
                    RenderUtils.renderStackInWorld(QUARTZ, 0.0D, -0.125D, -0.05D, 0.0F, 0.0F, 0.0F, 0.6D);
                } else if( soldier.hasUpgrade(Upgrades.OH_BOWL, EnumUpgradeType.OFF_HAND) ) {
                    RenderUtils.renderStackInWorld(SHIELD_NRM, 0.0D, 0.2D, 0.0D, 90.0F, 0.0F, 0.0F, 0.75D);
                }
                break;
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
