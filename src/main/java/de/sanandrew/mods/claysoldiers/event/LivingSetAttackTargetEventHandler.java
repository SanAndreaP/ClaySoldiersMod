/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.event;

import de.sanandrew.mods.claysoldiers.api.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.registry.upgrade.UpgradeRegistry;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LivingSetAttackTargetEventHandler
{
    @SubscribeEvent
    public void onLivingSetAttackTarget(LivingSetAttackTargetEvent event) {
        if( event.getEntity() instanceof ISoldier) {
            ISoldier victim = (ISoldier) event.getEntity();
            if( victim.hasUpgrade(UpgradeRegistry.MH_SPECKLEDMELON, EnumUpgradeType.MAIN_HAND) && victim.getEntity().getAttackTarget() instanceof ISoldier ) {
                ISoldier tgt = (ISoldier) victim.getEntity().getAttackTarget();
                if( tgt.getSoldierTeam() != victim.getSoldierTeam() ){
                    victim.getEntity().setAttackTarget(null);
                }
            }
        }
    }
}
