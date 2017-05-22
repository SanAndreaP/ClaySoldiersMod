/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.renderer.soldier;

import de.sanandrew.mods.claysoldiers.api.client.ISoldierRenderer;
import de.sanandrew.mods.claysoldiers.api.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgrade;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.registry.ItemRegistry;
import de.sanandrew.mods.claysoldiers.registry.upgrade.UpgradeRegistry;
import de.sanandrew.mods.sanlib.lib.client.util.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.EntityCreature;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderHookMainHandItem
        implements ISoldierRenderer
{
    private static final ItemStack STICK = new ItemStack(Items.STICK);
    private static final ItemStack ARROW = new ItemStack(Items.ARROW);
    private static final ItemStack BLAZEROD = new ItemStack(Items.BLAZE_ROD);
    private static final ItemStack SHEARBLADE = new ItemStack(ItemRegistry.shear_blade);
    private static final ItemStack SPECLEDMELON = new ItemStack(Items.SPECKLED_MELON);

    private final int priority;

    public RenderHookMainHandItem(int priority) {
        this.priority = priority;
    }

    @Override
    public int getPriority() {
        return this.priority;
    }

    @Override
    public boolean doHandRendererSetup(ISoldier<?> soldier, EnumHandSide handSide) {
        return handSide == soldier.getEntity().getPrimaryHand();
    }

    @Override
    public boolean onHandRender(ISoldier<?> soldier, RenderBiped<? extends EntityCreature> renderer, EnumHandSide handSide) {
        if( !this.doHandRendererSetup(soldier, handSide) ) {
            return false;
        }

        switch( this.priority ) {
            case 0: {
                if( soldier.hasUpgrade(UpgradeRegistry.MH_STICK, EnumUpgradeType.MAIN_HAND) ) {
                    this.renderItem(STICK, -125.0F);
                    return true;
                } else if( soldier.hasUpgrade(UpgradeRegistry.MH_BLAZEROD, EnumUpgradeType.MAIN_HAND) ) {
                    this.renderItem(BLAZEROD, -125.0F);
                    return true;
                } else if( soldier.hasUpgrade(UpgradeRegistry.MOH_SHEARBLADE, EnumUpgradeType.MAIN_HAND) ) {
                    this.renderItem(SHEARBLADE, -135.0F);
                    return true;
                } else if( soldier.hasUpgrade(UpgradeRegistry.MH_SPECKLEDMELON, EnumUpgradeType.MAIN_HAND) ) {
                    this.renderItem(SPECLEDMELON, -90.0F);
                    return true;
                }
            } break;
            case 1: {
                ISoldierUpgrade upg = UpgradeRegistry.INSTANCE.getUpgrade(UpgradeRegistry.MC_FLINT);
                if( upg != null && soldier.hasUpgrade(upg, EnumUpgradeType.ENHANCEMENT) ) {
                    GlStateManager.pushMatrix();
                    GlStateManager.translate(0.0F, 0.2F, 0.1F);
                    RenderUtils.renderStackInWorld(ARROW, 0.0D, 0.0D, 0.0D, 0.0F, 90.0F, -125.0F, 0.75D);
                    GlStateManager.popMatrix();
                    return true;
                }
            } break;
        }
        return false;
    }

    private void renderItem(ItemStack stack, float rotation) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, 0.2F, 0.1F);
        RenderUtils.renderStackInWorld(stack, 0.0D, 0.0D, 0.0D, 0.0F, 90.0F, rotation, 0.75D);
        GlStateManager.popMatrix();
    }
}
