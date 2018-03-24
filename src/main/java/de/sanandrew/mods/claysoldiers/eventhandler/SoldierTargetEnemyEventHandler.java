/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.eventhandler;

import de.sanandrew.mods.claysoldiers.api.event.SoldierTargetEnemyEvent;
import de.sanandrew.mods.claysoldiers.api.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.registry.effect.Effects;
import de.sanandrew.mods.claysoldiers.registry.upgrade.Upgrades;
import net.minecraft.entity.monster.IMob;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public final class SoldierTargetEnemyEventHandler
{
    @SubscribeEvent
    public void onTargetingNormal(SoldierTargetEnemyEvent event) {
        if( event.attacker.hasEffect(Effects.BLINDING_REDSTONE) || event.attacker.hasUpgrade(Upgrades.BH_WHEAT, EnumUpgradeType.BEHAVIOR) ) {
            event.setResult(Event.Result.DENY);
        } else {
            boolean rotten = event.attacker.hasUpgrade(Upgrades.BH_ROTTENFLESH, EnumUpgradeType.BEHAVIOR);
            if( rotten && (event.target instanceof IMob || event.attacker.hasUpgrade(Upgrades.MC_ENDERPEARL, EnumUpgradeType.MISC)) ) {
                event.setResult(Event.Result.ALLOW);
            }

            if( event.target instanceof ISoldier ) {
                ISoldier tgtSoldier = (ISoldier) event.target;

                if( event.attacker.hasUpgrade(Upgrades.MH_SPECKLEDMELON, EnumUpgradeType.MAIN_HAND) ) {
                    boolean isTeam = event.attacker.getSoldierTeam() == tgtSoldier.getSoldierTeam()
                                     || event.attacker.hasUpgrade(Upgrades.BH_SPONGE, EnumUpgradeType.BEHAVIOR);
                    event.setResult(isTeam && event.target.getHealth() < event.target.getMaxHealth() * 0.25F
                                            ? Event.Result.ALLOW
                                            : Event.Result.DENY);
                } else {
                    if( !event.attacker.hasUpgrade(Upgrades.MC_GLASS, EnumUpgradeType.MISC) && tgtSoldier.hasUpgrade(Upgrades.MC_EGG, EnumUpgradeType.MISC) ) {
                        event.setResult(Event.Result.DENY);
                    }

                    if( event.autoTargeted && (event.attacker.hasUpgrade(Upgrades.BH_FERMSPIDEREYE, EnumUpgradeType.BEHAVIOR)
                                               || tgtSoldier.hasUpgrade(Upgrades.BH_SPONGE, EnumUpgradeType.BEHAVIOR)
                                               || event.attacker.hasUpgrade(Upgrades.BH_SPONGE, EnumUpgradeType.BEHAVIOR))
                    ) {
                        event.setResult(Event.Result.DENY);
                    }

                    if( event.attacker.hasUpgrade(Upgrades.MC_ENDERPEARL, EnumUpgradeType.MISC) ) {
                        if( !((ISoldier) event.target).hasUpgrade(Upgrades.MC_ENDERPEARL, EnumUpgradeType.MISC) ) {
                            event.setResult(Event.Result.ALLOW);
                        } else {
                            event.setResult(Event.Result.DENY);
                        }
                    }

                    if( event.attacker.hasUpgrade(Upgrades.BH_NETHERWART, EnumUpgradeType.BEHAVIOR) ) {
                        event.setResult(Event.Result.ALLOW);
                    }
                }
            }
        }
    }
}
