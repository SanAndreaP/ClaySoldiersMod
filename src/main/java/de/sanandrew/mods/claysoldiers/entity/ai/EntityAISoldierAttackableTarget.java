/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.ai;

import de.sanandrew.mods.claysoldiers.api.event.SoldierTargetEnemyEvent;
import de.sanandrew.mods.claysoldiers.entity.EntityClaySoldier;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class EntityAISoldierAttackableTarget
        extends EntityAITarget
{
    private final EntityClaySoldier attacker;
    private EntityLivingBase target;

    private final Predicate<EntityLivingBase> tgtSelector;

    public EntityAISoldierAttackableTarget(EntityClaySoldier soldier) {
        super(soldier, false, false);
        this.attacker = soldier;
        this.tgtSelector = entity -> {
            if( entity != null && entity.isEntityAlive() && entity.canEntityBeSeen(this.attacker) ) {
                SoldierTargetEnemyEvent evt = new SoldierTargetEnemyEvent(this.attacker, entity);
                if( !ClaySoldiersMod.EVENT_BUS.post(evt) ) {
                    return evt.getResult() == Event.Result.ALLOW
                           || (evt.getResult() != Event.Result.DENY && entity instanceof EntityClaySoldier
                               && ((EntityClaySoldier)entity).getSoldierTeam() != this.attacker.getSoldierTeam());
                }
            }

            return false;
        };
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        Entity e = this.attacker.getAttackTarget();
        if( e != null && e.isEntityAlive() ) {
            return false;
        }

        List<EntityLivingBase> list = this.taskOwner.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getTargetableArea(this.getTargetDistance()), this.tgtSelector::test);


        if( list.isEmpty() ) {
            return false;
        } else {
            this.target = list.get(MiscUtils.RNG.randomInt(list.size()));
            return true;
        }
    }

    public void startExecuting() {
        this.attacker.setAttackTarget(this.target);
        super.startExecuting();
    }

    private AxisAlignedBB getTargetableArea(double targetDistance) {
        return this.attacker.getEntityBoundingBox().expand(targetDistance, targetDistance, targetDistance);
    }
}
