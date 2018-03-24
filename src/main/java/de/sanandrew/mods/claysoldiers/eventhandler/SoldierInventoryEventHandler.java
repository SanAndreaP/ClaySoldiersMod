/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.eventhandler;

import de.sanandrew.mods.claysoldiers.api.event.SoldierInventoryEvent;
import de.sanandrew.mods.claysoldiers.entity.ai.ResurrectionHelper;
import de.sanandrew.mods.claysoldiers.registry.upgrade.UpgradeRegistry;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SoldierInventoryEventHandler
{
    @SubscribeEvent
    public void onSoldierItemValidity(SoldierInventoryEvent.ItemValid event) {
        if( event.soldier.canPickupUpgrade(UpgradeRegistry.INSTANCE.getUpgrade(event.stack), event.stack) ) {
            event.setResult(Event.Result.ALLOW);
        } else if( ResurrectionHelper.canBeResurrected(event.soldier, event.stack) ) {
            event.setResult(Event.Result.ALLOW);
        }
    }

    @SubscribeEvent
    public void onSoldierItemGrab(SoldierInventoryEvent.Grab event) {
        if( event.soldier.canPickupUpgrade(UpgradeRegistry.INSTANCE.getUpgrade(event.stack), event.stack) ) {
            event.soldier.addUpgrade(UpgradeRegistry.INSTANCE.getUpgrade(event.stack), event.stack);
        } else if( ResurrectionHelper.canBeResurrected(event.soldier, event.stack) ) {
            ResurrectionHelper.resurrectDoll(event.soldier, event.stack, event.tilePos.getX() + 0.5D, event.tilePos.getY() + 1.0D, event.tilePos.getZ() + 0.5D);
        }
    }
}
