/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.event;

import de.sanandrew.mods.claysoldiers.api.event.SoldierDeathEvent;
import de.sanandrew.mods.claysoldiers.api.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.registry.upgrade.UpgradeRegistry;
import de.sanandrew.mods.claysoldiers.registry.upgrade.Upgrades;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SoldierDeathEventHandler
{
    public static final SoldierDeathEventHandler INSTANCE = new SoldierDeathEventHandler();

    private SoldierDeathEventHandler() { }

    @SubscribeEvent
    public void onSoldierDeath(SoldierDeathEvent event) {
        if( event.dmgSource.getTrueSource() instanceof ISoldier && ((ISoldier) event.dmgSource.getTrueSource()).hasUpgrade(Upgrades.MC_ENDERPEARL, EnumUpgradeType.MISC) ) {
            event.drops.clear();
        }
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        if( event.getEntity() instanceof ISoldier && event.getSource().getTrueSource() instanceof ISoldier ) {
            ISoldier dying = (ISoldier) event.getEntity();
            ISoldier attacker = (ISoldier) event.getSource().getTrueSource();

            if( attacker.hasUpgrade(Upgrades.MC_ENDERPEARL, EnumUpgradeType.MISC) && !dying.hasUpgrade(Upgrades.MC_ENDERPEARL, EnumUpgradeType.MISC) ) {
                event.setCanceled(true);
                ISoldierUpgradeInst upgInst = attacker.getUpgradeInstance(Upgrades.MC_ENDERPEARL, EnumUpgradeType.MISC);
                upgInst.getUpgrade().onAttackSuccess(attacker, upgInst, dying.getEntity());
            }
        }
    }
}
