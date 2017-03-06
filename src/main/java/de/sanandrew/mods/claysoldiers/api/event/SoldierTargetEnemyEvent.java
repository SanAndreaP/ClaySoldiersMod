/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.api.event;

import de.sanandrew.mods.claysoldiers.api.soldier.ISoldier;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

@Event.HasResult
public class SoldierTargetEnemyEvent
        extends EntityEvent
{
    public final ISoldier<?> attacker;
    public final EntityLivingBase target;

    public SoldierTargetEnemyEvent(ISoldier<?> attacker, EntityLivingBase target) {
        super(attacker.getEntity());
        this.attacker = attacker;
        this.target = target;
    }
}
