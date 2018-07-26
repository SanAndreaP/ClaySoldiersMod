/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.eventhandler;

import de.sanandrew.mods.claysoldiers.api.entity.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.ISoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.entity.soldier.EntityClaySoldier;
import de.sanandrew.mods.claysoldiers.registry.upgrade.Upgrades;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LivingEventHandler
{
    @SubscribeEvent
    public void onAttack(LivingAttackEvent event) {
        if( event.getSource().getTrueSource() instanceof ISoldier ) {
            ISoldier attacker = (ISoldier) event.getSource().getTrueSource();
            if( attacker.hasUpgrade(Upgrades.MH_SPECKLEDMELON, EnumUpgradeType.MAIN_HAND) ) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onJump(LivingEvent.LivingJumpEvent event) {
        if( event.getEntity() instanceof ISoldier && ((ISoldier) event.getEntity()).hasUpgrade(Upgrades.MC_RABBITFOOT, EnumUpgradeType.MISC) ) {
            event.getEntity().motionY += 0.25D;
        }
    }

    @SubscribeEvent
    public void onFall(LivingFallEvent event) {
        if( event.getEntity() instanceof EntityClaySoldier ) {
            EntityClaySoldier soldier = (EntityClaySoldier) event.getEntity();
            if( soldier.hasUpgrade(Upgrades.MC_FEATHER, EnumUpgradeType.MISC) && !soldier.hasUpgrade(Upgrades.CR_IRONINGOT, EnumUpgradeType.CORE)
                && !event.getEntity().isRiding() )
            {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onDeath(LivingDeathEvent event) {
        if( event.getEntity() instanceof ISoldier && event.getSource().getTrueSource() instanceof ISoldier ) {
            ISoldier dying = (ISoldier) event.getEntity();
            ISoldier attacker = (ISoldier) event.getSource().getTrueSource();

            if( attacker.hasUpgrade(Upgrades.MC_ENDERPEARL, EnumUpgradeType.MISC) && !dying.hasUpgrade(Upgrades.MC_ENDERPEARL, EnumUpgradeType.MISC)
                        && !dying.hasUpgrade(Upgrades.MC_WHEATSEEDS, EnumUpgradeType.MISC) )
            {
                event.setCanceled(true);
                ISoldierUpgradeInst upgInst = attacker.getUpgradeInstance(Upgrades.MC_ENDERPEARL, EnumUpgradeType.MISC);
                upgInst.getUpgrade().onAttackSuccess(attacker, upgInst, dying.getEntity());
            }
        }
    }
}
