/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.renderer.soldier;

import de.sanandrew.mods.claysoldiers.api.client.ISoldierRenderer;
import de.sanandrew.mods.claysoldiers.api.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.soldier.ISoldierUpgrade;
import de.sanandrew.mods.claysoldiers.registry.UpgradeRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.EntityCreature;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderHookMainHandItem
        implements ISoldierRenderer
{
    private final int priority;

    public RenderHookMainHandItem(int priority) {
        this.priority = priority;
    }

    @Override
    public int getPriority() {
        return this.priority;
    }

    @Override
    public boolean doHandRendererSetup(ISoldier soldier, EnumHandSide handSide) {
        return (handSide == EnumHandSide.LEFT && soldier.getEntity().isLeftHanded()) || handSide == EnumHandSide.RIGHT;
    }

    @Override
    public boolean onHandRender(ISoldier soldier, RenderBiped<? extends EntityCreature> renderer, EnumHandSide handSide) {
        if( !this.doHandRendererSetup(soldier, handSide) ) {
            return false;
        }

        switch( this.priority ) {
            case 0: {
                ISoldierUpgrade upg = UpgradeRegistry.INSTANCE.getUpgrade(UpgradeRegistry.MH_STICK);
                if( upg != null && soldier.hasUpgrade(upg) ) {
                    ItemCameraTransforms.TransformType transformType = handSide == EnumHandSide.RIGHT ? ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND
                                                                                                      : ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND;
                    Minecraft.getMinecraft().getItemRenderer().renderItemSide(soldier.getEntity(), upg.getStack(), transformType, handSide == EnumHandSide.LEFT);
                    return true;
                }
            } break;
            case 1: {
                ISoldierUpgrade upg = UpgradeRegistry.INSTANCE.getUpgrade(UpgradeRegistry.MC_FLINT);
                if( upg != null && soldier.hasUpgrade(upg) ) {
                    ItemCameraTransforms.TransformType transformType = handSide == EnumHandSide.RIGHT ? ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND
                            : ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND;
                    Minecraft.getMinecraft().getItemRenderer().renderItemSide(soldier.getEntity(), upg.getStack(), transformType, handSide == EnumHandSide.LEFT);
                    return true;
                }
            } break;
        }
        return false;
    }
}
