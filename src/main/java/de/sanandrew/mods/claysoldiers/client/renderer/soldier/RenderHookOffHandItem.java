/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.renderer.soldier;

import de.sanandrew.mods.claysoldiers.api.client.ISoldierRenderer;
import de.sanandrew.mods.claysoldiers.api.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgrade;
import de.sanandrew.mods.claysoldiers.registry.ItemRegistry;
import de.sanandrew.mods.claysoldiers.registry.upgrade.UpgradeRegistry;
import de.sanandrew.mods.sanlib.lib.client.util.RenderUtils;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.EntityCreature;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.UUID;

@SideOnly(Side.CLIENT)
public class RenderHookOffHandItem
        implements ISoldierRenderer
{
    private static final ItemStack SHEARBLADE = new ItemStack(ItemRegistry.shear_blade);
    private static final ItemStack GRAVEL = new ItemStack(Blocks.GRAVEL);
    private static final ItemStack SNOW = new ItemStack(Blocks.SNOW);
    private static final ItemStack MAGMA = new ItemStack(Blocks.MAGMA);

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
    public boolean onHandRender(ISoldier<?> soldier, RenderBiped<? extends EntityCreature> renderer, EnumHandSide handSide) {
        if( !this.doHandRendererSetup(soldier, handSide) ) {
            return false;
        }

        switch( this.priority ) {
            case 0: {
                if( this.getUpgrade(UpgradeRegistry.MOH_SHEARBLADE, soldier) != null ) {
                    RenderUtils.renderStackInWorld(SHEARBLADE, 0.0D, 0.2D, 0.0D, 0.0F, -90.0F, -135.0F, 0.75D);
                    return true;
                } else if( this.getUpgrade(UpgradeRegistry.OH_GRAVEL, soldier) != null ) {
                    RenderUtils.renderStackInWorld(GRAVEL, 0.0D, -0.125D, -0.05D, 0.0F, 0.0F, 0.0F, 0.6D);
                    return true;
                } else if( this.getUpgrade(UpgradeRegistry.OH_SNOW, soldier) != null ) {
                    RenderUtils.renderStackInWorld(SNOW, 0.0D, -0.125D, -0.05D, 0.0F, 0.0F, 0.0F, 0.6D);
                    return true;
                } else if( this.getUpgrade(UpgradeRegistry.OH_FIRECHARGE, soldier) != null ) {
                    RenderUtils.renderStackInWorld(MAGMA, 0.0D, -0.125D, -0.05D, 0.0F, 0.0F, 0.0F, 0.6D);
                    return true;
                }
            } break;
        }
        return false;
    }

    private ISoldierUpgrade getUpgrade(UUID upgId, ISoldier soldier) {
        ISoldierUpgrade upg = UpgradeRegistry.INSTANCE.getUpgrade(upgId);
        if( upg != null && soldier.hasUpgrade(upg, EnumUpgradeType.OFF_HAND) ) {
            return upg;
        }

        return null;
    }
}
