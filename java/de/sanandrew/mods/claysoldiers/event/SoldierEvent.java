/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.event;

import cpw.mods.fml.common.eventhandler.Event;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;

public class SoldierEvent
        extends Event
{
    public final EntityClayMan clayMan;

    public SoldierEvent(EntityClayMan clayMan) {
        this.clayMan = clayMan;
    }
}
