/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.eventhandler;

import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.entity.soldier.EntityClaySoldier;
import de.sanandrew.mods.claysoldiers.registry.upgrade.Upgrades;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityFallEventHandler
{
    @SubscribeEvent
    public void onFall(LivingFallEvent event) {
        if( event.getEntity() instanceof EntityClaySoldier ) {
            EntityClaySoldier soldier = (EntityClaySoldier) event.getEntity();
            if( soldier.hasUpgrade(Upgrades.MC_FEATHER, EnumUpgradeType.MISC) && !soldier.hasUpgrade(Upgrades.CR_IRONINGOT, EnumUpgradeType.CORE) && !event.getEntity().isRiding() ) {
                event.setCanceled(true);
            }
        }
    }
}
