/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.eventhandler;

import de.sanandrew.mods.claysoldiers.api.client.event.ClayModelRotationEvent;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.client.model.ModelClaySoldier;
import de.sanandrew.mods.claysoldiers.entity.soldier.EntityClaySoldier;
import de.sanandrew.mods.claysoldiers.registry.upgrade.Upgrades;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClayModelRotationEventHandler
{
    @SubscribeEvent
    public void onModelRotation(ClayModelRotationEvent event) {
        if( event.model instanceof ModelClaySoldier && event.entity instanceof EntityClaySoldier ) {
            ModelClaySoldier model = (ModelClaySoldier) event.model;
            EntityClaySoldier soldier = (EntityClaySoldier) event.entity;
            if( soldier.hasUpgrade(Upgrades.MC_FEATHER, EnumUpgradeType.MISC) && !soldier.onGround ) {
                model.bipedLeftArm.rotateAngleX += Math.PI;
                model.bipedRightArm.rotateAngleX += Math.PI;
            } else if( soldier.hasMainHandUpgrade() ) {
                (soldier.isLeftHanded() ? model.bipedLeftArm : model.bipedRightArm).rotateAngleX -= 0.25D;
            } else if( soldier.hasOffHandUpgrade() ) {
                (soldier.isLeftHanded() ? model.bipedRightArm : model.bipedLeftArm).rotateAngleX -= 0.25D;
            }
        }
    }
}
