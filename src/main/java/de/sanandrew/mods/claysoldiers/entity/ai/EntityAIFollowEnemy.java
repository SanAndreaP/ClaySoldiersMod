/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.ai;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.attribute.AttributeHelper;
import de.sanandrew.mods.claysoldiers.api.entity.ITargetingEntity;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.ISoldierUpgradeThrowable;
import de.sanandrew.mods.claysoldiers.entity.EntityHelper;
import de.sanandrew.mods.claysoldiers.entity.soldier.EntityClaySoldier;
import de.sanandrew.mods.claysoldiers.registry.effect.EffectBackWalk;
import de.sanandrew.mods.claysoldiers.registry.effect.Effects;
import de.sanandrew.mods.claysoldiers.registry.upgrade.Upgrades;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.util.EnumHand;

import java.util.UUID;

public abstract class EntityAIFollowEnemy
        extends EntityAIFollowTarget<EntityCreature>
{
    protected int attackTick;
    protected ITargetingEntity<?> taskOwner;

    public EntityAIFollowEnemy(ITargetingEntity<?> targetingEntity, double speedIn) {
        super(targetingEntity.getEntity(), speedIn);
        this.taskOwner = targetingEntity;
        this.setMutexBits(MutexBits.MOTION | MutexBits.LOOK_MOVEMENT);
    }

    @Override
    void checkAndPerformAction(Entity entity, double dist) {
        this.attackTick = Math.max(this.attackTick - 1, 0);
        this.checkAndPerformAttack(entity, dist);
    }

    @Override
    boolean isTargetValid() {
        return this.taskOwner.isEnemyValid(super.taskOwner.getAttackTarget());
    }

    @Override
    Entity getTarget() {
        return super.taskOwner.getAttackTarget();
    }

    @Override
    void clearTarget() {
        super.taskOwner.setAttackTarget(null);
    }

    protected abstract void checkAndPerformAttack(Entity entity, double dist);

    public static final class Meelee
            extends EntityAIFollowEnemy
    {
        public Meelee(ITargetingEntity<?> targetingEntity, double speedIn) {
            super(targetingEntity, speedIn);
        }

        @Override
        protected void checkAndPerformAttack(Entity entity, double dist) {
            if( dist <= this.getAttackReachSqr() && this.attackTick <= 0 ) {
                this.attackTick = 20;
                this.taskOwner.getEntity().swingArm(EnumHand.MAIN_HAND);
                this.taskOwner.getEntity().attackEntityAsMob(entity);
            }
        }

        protected double getAttackReachSqr() {
            final double reach = this.taskOwner.getReach();
            return reach * reach;
        }
    }

    public static final class RangedSoldier
            extends EntityAIFollowEnemy
    {
        private final EntityClaySoldier soldier;

        public RangedSoldier(EntityClaySoldier soldier, double speedIn) {
            super(soldier, speedIn);
            this.soldier = soldier;
        }

        @Override
        protected void checkAndPerformAttack(Entity entity, double dist) {
            if( dist <= this.getAttackReachSqr() ) {
                if( !this.soldier.hasEffect(Effects.MOVE_BACK) ) {
                    this.soldier.addEffect(EffectBackWalk.INSTANCE, 20);
                }

                if( this.attackTick <= 0 ) {
                    this.attackTick = 20;
                    this.taskOwner.getEntity().swingArm(EnumHand.OFF_HAND);
                    this.soldier.getUpgradeInstanceList().stream().filter(inst -> inst.getUpgrade() instanceof ISoldierUpgradeThrowable).findFirst()
                                 .ifPresent(inst -> {
                                     ISoldierUpgradeThrowable upgThrowable = (ISoldierUpgradeThrowable) inst.getUpgrade();
                                     this.soldier.world.spawnEntity(upgThrowable.createProjectile(this.soldier.world, this.soldier, entity));
                                     inst.getUpgrade().onAttack(this.soldier, inst, entity, null, null);
                                 });
                }
            } else {
                if( this.soldier.hasEffect(Effects.MOVE_BACK) ) {
                    this.soldier.expireEffect(EffectBackWalk.INSTANCE);
                }
            }
        }

        @Override
        public boolean shouldExecute() {
            return super.shouldExecute() && this.soldier.getUpgradeInstanceList().stream().anyMatch(inst -> inst.getUpgrade() instanceof ISoldierUpgradeThrowable);
        }

        protected double getAttackReachSqr() {
            return this.soldier.hasUpgrade(Upgrades.EM_SUGARCANE, EnumUpgradeType.ENHANCEMENT) ? 16.0D : 9.0D;
        }
    }
}
