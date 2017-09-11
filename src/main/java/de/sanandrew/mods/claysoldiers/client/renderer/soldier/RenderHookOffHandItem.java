/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.renderer.soldier;

import de.sanandrew.mods.claysoldiers.api.client.ISoldierRenderHook;
import de.sanandrew.mods.claysoldiers.api.client.soldier.ISoldierRender;
import de.sanandrew.mods.claysoldiers.api.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.registry.ItemRegistry;
import de.sanandrew.mods.claysoldiers.registry.upgrade.UpgradeRegistry;
import de.sanandrew.mods.sanlib.lib.client.util.RenderUtils;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderHookOffHandItem
        implements ISoldierRenderHook
{
    private static final boolean H_AS_EG_G = MiscUtils.RNG.randomInt(1_000_000) == 0;

    private static final ItemStack SHEARBLADE = new ItemStack(ItemRegistry.SHEAR_BLADE);
    private static final ItemStack GRAVEL = new ItemStack(Blocks.GRAVEL);
    private static final ItemStack SNOW = new ItemStack(Blocks.SNOW);
    private static final ItemStack MAGMA = new ItemStack(Blocks.MAGMA);
    private static final ItemStack QUARTZ = new ItemStack(Blocks.QUARTZ_ORE);
    private static final ItemStack SHIELD_NRM = new ItemStack(ItemRegistry.SOLDIER_SHIELD, 1, H_AS_EG_G ? 2 : 0);
    private static final ItemStack SHIELD_STD = new ItemStack(ItemRegistry.SOLDIER_SHIELD, 1, H_AS_EG_G ? 3 : 1);

    private final int priority;

    public RenderHookOffHandItem(int priority) {
        this.priority = priority;
    }

    @Override
    public int getPriority() {
        return this.priority;
    }

    @Override
    public boolean doHandRendererSetup(ISoldier<?> soldier, EnumHandSide handSide) {
        return handSide != soldier.getEntity().getPrimaryHand();
    }

    @Override
    public boolean onHandRender(ISoldier<?> soldier, ISoldierRender<?, ?> renderer, EnumHandSide handSide) {
        if( !this.doHandRendererSetup(soldier, handSide) ) {
            return false;
        }

        switch( this.priority ) {
            case 0: {
                if( soldier.hasUpgrade(UpgradeRegistry.MOH_SHEARBLADE, EnumUpgradeType.OFF_HAND) ) {
                    RenderUtils.renderStackInWorld(SHEARBLADE, 0.0D, 0.2D, 0.0D, 0.0F, -90.0F, -45.0F, 0.75D);
                    return true;
                } else if( soldier.hasUpgrade(UpgradeRegistry.OH_GRAVEL, EnumUpgradeType.OFF_HAND) ) {
                    RenderUtils.renderStackInWorld(GRAVEL, 0.0D, -0.125D, -0.05D, 0.0F, 0.0F, 0.0F, 0.6D);
                    return true;
                } else if( soldier.hasUpgrade(UpgradeRegistry.OH_SNOW, EnumUpgradeType.OFF_HAND) ) {
                    RenderUtils.renderStackInWorld(SNOW, 0.0D, -0.125D, -0.05D, 0.0F, 0.0F, 0.0F, 0.6D);
                    return true;
                } else if( soldier.hasUpgrade(UpgradeRegistry.OH_FIRECHARGE, EnumUpgradeType.OFF_HAND) ) {
                    RenderUtils.renderStackInWorld(MAGMA, 0.0D, -0.125D, -0.05D, 0.0F, 0.0F, 0.0F, 0.6D);
                    return true;
                } else if( soldier.hasUpgrade(UpgradeRegistry.OH_QUARTZ, EnumUpgradeType.OFF_HAND) ) {
                    RenderUtils.renderStackInWorld(QUARTZ, 0.0D, -0.125D, -0.05D, 0.0F, 0.0F, 0.0F, 0.6D);
                    return true;
                } else if( soldier.hasUpgrade(UpgradeRegistry.OH_BOWL, EnumUpgradeType.OFF_HAND) ) {
                    RenderUtils.renderStackInWorld(SHIELD_NRM, 0.0D, 0.2D, 0.0D, 90.0F, 0.0F, 0.0F, 0.75D);
                    return true;
                }
            } break;
            case 1: {
                if( soldier.hasUpgrade(UpgradeRegistry.EC_IRONBLOCK, EnumUpgradeType.ENHANCEMENT) ) {
                    RenderUtils.renderStackInWorld(SHIELD_STD, 0.0D, 0.2D, 0.0D, 90.0F, 0.0F, 0.0F, 0.75D);
                    return true;
                }
            }
        }
        return false;
    }
}
