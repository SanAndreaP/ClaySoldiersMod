package de.sanandrew.mods.claysoldiers.event;

import cpw.mods.fml.common.eventhandler.Event;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;

/**
 * @author SanAndreas
 * @version 1.0
 */
public class SoldierEvent
    extends Event
{
    public final EntityClayMan clayMan;

    public SoldierEvent(EntityClayMan clayMan) {
        this.clayMan = clayMan;
    }
}
