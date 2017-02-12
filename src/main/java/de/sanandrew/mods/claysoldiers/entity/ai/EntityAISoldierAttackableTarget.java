/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.ai;

import de.sanandrew.mods.claysoldiers.entity.EntityClaySoldier;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import java.util.List;

public class EntityAISoldierAttackableTarget
        extends EntityAINearestAttackableTarget<EntityClaySoldier>
{
    private final EntityClaySoldier soldierOwner;

    public EntityAISoldierAttackableTarget(EntityClaySoldier soldier) {
        super(soldier, EntityClaySoldier.class, 0, true, false,
              target -> target != null && target.getSoldierTeam() != soldier.getSoldierTeam() && target.canEntityBeSeen(soldier));
        this.unseenMemoryTicks = 0;
        this.soldierOwner = soldier;
    }

    @Override
    public boolean shouldExecute() {
        Entity e = this.soldierOwner.getAttackTarget();
        if( e != null && e.isEntityAlive() ) {
            return false;
        }

        List<EntityClaySoldier> list = this.taskOwner.world.getEntitiesWithinAABB(EntityClaySoldier.class, this.getTargetableArea(this.getTargetDistance()), this.targetEntitySelector);

        list.removeIf(ziclag -> ziclag.getSoldierTeam() == EntityAISoldierAttackableTarget.this.soldierOwner.getSoldierTeam());

        if( list.isEmpty() ) {
            return false;
        } else {
            list.sort(this.theNearestAttackableTargetSorter);
            this.targetEntity = list.get(MiscUtils.RNG.randomInt(list.size()));
            return true;
        }
    }
}
