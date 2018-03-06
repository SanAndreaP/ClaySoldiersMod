/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.ai;

import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgradeThrowable;
import de.sanandrew.mods.claysoldiers.entity.soldier.EntityClaySoldier;
import de.sanandrew.mods.claysoldiers.registry.upgrade.Upgrades;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumHand;

public abstract class EntityAIFollowEnemy
        extends EntityAIFollowTarget
{
    protected int attackTick;

    public EntityAIFollowEnemy(EntityClaySoldier soldier, double speedIn) {
        super(soldier, speedIn);
        this.setMutexBits(MutexBits.MOTION | MutexBits.LOOK_MOVEMENT);
    }

    @Override
    void checkAndPerformAction(Entity entity, double dist) {
        this.attackTick = Math.max(this.attackTick - 1, 0);
        this.checkAndPerformAttack(entity, dist);
    }

    @Override
    boolean isTargetValid() {
        return true;
    }

    @Override
    Entity getTarget() {
        return this.taskOwner.getAttackTarget();
    }

    @Override
    void clearTarget() {
        this.taskOwner.setAttackTarget(null);
    }

    protected abstract void checkAndPerformAttack(Entity entity, double dist);

    public static final class Meelee
            extends EntityAIFollowEnemy
    {
        public Meelee(EntityClaySoldier creature, double speedIn) {
            super(creature, speedIn);
        }

        @Override
        protected void checkAndPerformAttack(Entity entity, double dist) {
            if( dist <= this.getAttackReachSqr() && this.attackTick <= 0 ) {
                this.attackTick = 20;
                this.taskOwner.swingArm(EnumHand.MAIN_HAND);
                this.taskOwner.attackEntityAsMob(entity);
            }
        }

        protected double getAttackReachSqr() {
            final double reach = 0.5F + (this.taskOwner.hasUpgrade(Upgrades.MH_BONE, EnumUpgradeType.MAIN_HAND) ? 0.5F : 0.0F);
            return reach * reach;
        }
    }

    public static final class Ranged
            extends EntityAIFollowEnemy
    {
        public Ranged(EntityClaySoldier creature, double speedIn) {
            super(creature, speedIn);
        }

        @Override
        protected void checkAndPerformAttack(Entity entity, double dist) {
            if( dist <= this.getAttackReachSqr() ) {
                this.taskOwner.setMoveMultiplier(-1.0F);

                if( this.attackTick <= 0 ) {
                    this.attackTick = 20;
                    this.taskOwner.swingArm(EnumHand.MAIN_HAND);
                    this.taskOwner.getUpgradeInstanceList().stream().filter(inst -> inst.getUpgrade() instanceof ISoldierUpgradeThrowable).findFirst()
                                 .ifPresent(inst -> {
                                     ISoldierUpgradeThrowable upgThrowable = (ISoldierUpgradeThrowable) inst.getUpgrade();
                                     this.taskOwner.world.spawnEntity(upgThrowable.createProjectile(this.taskOwner.world, this.taskOwner, entity));
                                     inst.getUpgrade().onAttack(this.taskOwner, inst, entity, null, null);
                                 });
                }
            } else {
                this.taskOwner.setMoveMultiplier(1.0F);
            }
        }

        @Override
        public boolean shouldExecute() {
            return super.shouldExecute() && this.taskOwner.getUpgradeInstanceList().stream().anyMatch(inst -> inst.getUpgrade() instanceof ISoldierUpgradeThrowable);
        }

        protected double getAttackReachSqr() {
            return this.taskOwner.hasUpgrade(Upgrades.EM_SUGARCANE, EnumUpgradeType.ENHANCEMENT) ? 16.0D : 9.0D;
        }
    }
}
