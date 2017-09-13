/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.event;

import de.sanandrew.mods.claysoldiers.api.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.registry.upgrade.Upgrades;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LivingAttackEventHandler
{
    @SubscribeEvent
    public void onLivingAttack(LivingAttackEvent event) {
        if( event.getSource().getTrueSource() instanceof ISoldier ) {
            ISoldier attacker = (ISoldier) event.getSource().getTrueSource();
            if( attacker.hasUpgrade(Upgrades.MH_SPECKLEDMELON, EnumUpgradeType.MAIN_HAND) ) {
                event.setCanceled(true);
            }
        }
    }
}
