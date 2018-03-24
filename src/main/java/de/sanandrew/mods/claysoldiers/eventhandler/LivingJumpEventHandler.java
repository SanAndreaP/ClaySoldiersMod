/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.eventhandler;

import de.sanandrew.mods.claysoldiers.api.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.registry.upgrade.Upgrades;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LivingJumpEventHandler
{
    @SubscribeEvent
    public void onLivingAttack(LivingEvent.LivingJumpEvent event) {
        if( event.getEntity() instanceof ISoldier && ((ISoldier) event.getEntity()).hasUpgrade(Upgrades.MC_RABBITFOOT, EnumUpgradeType.MISC) ) {
            event.getEntity().motionY += 0.25D;
        }
    }
}
