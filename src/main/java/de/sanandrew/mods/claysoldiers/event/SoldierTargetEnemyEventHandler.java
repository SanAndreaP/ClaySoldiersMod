/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.event;

import de.sanandrew.mods.claysoldiers.api.event.SoldierTargetEnemyEvent;
import de.sanandrew.mods.claysoldiers.api.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.registry.effect.Effects;
import de.sanandrew.mods.claysoldiers.registry.upgrade.Upgrades;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public final class SoldierTargetEnemyEventHandler
{
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onTargetingHigh(SoldierTargetEnemyEvent event) {
        if( event.target instanceof ISoldier ) {
            ISoldier tgtSoldier = (ISoldier) event.target;
            if( tgtSoldier.hasUpgrade(Upgrades.MC_EGG, EnumUpgradeType.MISC) ) {
                event.setResult(Event.Result.DENY);
            }

            if( event.attacker.hasUpgrade(Upgrades.MH_SPECKLEDMELON, EnumUpgradeType.MAIN_HAND) ) {
                event.setResult(event.attacker.getSoldierTeam() == tgtSoldier.getSoldierTeam() && event.target.getHealth() < event.target.getMaxHealth() * 0.25F
                              ? Event.Result.ALLOW
                              : Event.Result.DENY);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onTargetingNormal(SoldierTargetEnemyEvent event) {
        if( event.attacker.hasUpgrade(Upgrades.MC_GLASS, EnumUpgradeType.MISC) ) {
            event.setResult(Event.Result.DEFAULT);
        }

        if( event.attacker.hasEffect(Effects.BLINDING_REDSTONE) ) {
            event.setResult(Event.Result.DENY);
        }

        if( event.attacker.hasUpgrade(Upgrades.MC_ENDERPEARL, EnumUpgradeType.MISC) && event.target instanceof ISoldier ) {
            if( !((ISoldier) event.target).hasUpgrade(Upgrades.MC_ENDERPEARL, EnumUpgradeType.MISC) ) {
                event.setResult(Event.Result.ALLOW);
            } else {
                event.setResult(Event.Result.DENY);
            }
        }
    }
}
