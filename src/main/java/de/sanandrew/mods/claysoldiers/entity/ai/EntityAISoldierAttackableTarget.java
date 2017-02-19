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
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class EntityAISoldierAttackableTarget
        extends EntityAITarget
{
    private final EntityClaySoldier attacker;
    private EntityClaySoldier targetSoldier;
    private Comparator<Entity> nearestSorter;

    private final Predicate<EntityClaySoldier> tgtSelector;

    public EntityAISoldierAttackableTarget(EntityClaySoldier soldier) {
        super(soldier, false, false);
        this.attacker = soldier;
        this.nearestSorter = new EntityAINearestAttackableTarget.Sorter(soldier);
        this.tgtSelector = entity -> entity != null && entity.isEntityAlive() && entity.getSoldierTeam() != this.attacker.getSoldierTeam() && entity.canEntityBeSeen(this.attacker);
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        Entity e = this.attacker.getAttackTarget();
        if( e != null && e.isEntityAlive() ) {
            return false;
        }

        List<EntityClaySoldier> list = this.taskOwner.world.getEntitiesWithinAABB(EntityClaySoldier.class, this.getTargetableArea(this.getTargetDistance()), this.tgtSelector::test);

        if( list.isEmpty() ) {
            return false;
        } else {
            list.sort(this.nearestSorter);
            this.targetSoldier = list.get(MiscUtils.RNG.randomInt(list.size()));
            return true;
        }
    }

    public void startExecuting() {
        this.attacker.setAttackTarget(this.targetSoldier);
        super.startExecuting();
    }

    private AxisAlignedBB getTargetableArea(double targetDistance) {
        return this.attacker.getEntityBoundingBox().expand(targetDistance, targetDistance, targetDistance);
    }
}
