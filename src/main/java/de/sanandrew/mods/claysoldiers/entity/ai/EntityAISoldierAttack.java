/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.ai;

import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgradeThrowable;
import de.sanandrew.mods.claysoldiers.entity.projectile.EntityClayProjectile;
import de.sanandrew.mods.claysoldiers.entity.soldier.EntityClaySoldier;
import de.sanandrew.mods.claysoldiers.registry.upgrade.UpgradeRegistry;
import de.sanandrew.mods.claysoldiers.registry.upgrade.Upgrades;
import de.sanandrew.mods.claysoldiers.registry.upgrade.hand.UpgradeThrowable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;

public abstract class EntityAISoldierAttack
        extends EntityAIBase
{
    protected EntityClaySoldier attacker;
    protected int attackTick;
    double speedTowardsTarget;
    Path entityPathEntity;
    private double targetX;
    private double targetY;
    private double targetZ;

    public EntityAISoldierAttack(EntityClaySoldier creature, double speedIn) {
        this.attacker = creature;
        this.speedTowardsTarget = speedIn;
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {
        EntityLivingBase target = this.attacker.getAttackTarget();

        if( target == null ) {
            return false;
        } else if( !target.isEntityAlive() ) {
            return false;
        } else if( this.attacker.getDistanceSqToEntity(target) <= getAttackReachSqr() ) {
            return true;
        } else {
            this.entityPathEntity = this.attacker.getNavigator().getPathToEntityLiving(target);
            if( this.entityPathEntity == null ) {
                Vec3d vec = new Vec3d(this.targetX - this.attacker.posX, this.targetY - this.attacker.posY, this.targetZ - this.attacker.posZ).normalize().scale(1.1D);
                this.entityPathEntity = this.attacker.getNavigator().getPathToXYZ(this.targetX + vec.x, this.targetY + vec.y, this.targetZ + vec.z);
            }
            if( this.entityPathEntity == null ) {
                Vec3d rndPos = RandomPositionGenerator.findRandomTarget(this.attacker, 5, 3);
                this.entityPathEntity = this.attacker.getNavigator().getPathToXYZ(rndPos.x, rndPos.y, rndPos.z);
            }
//            return this.entityPathEntity != null;
            return true;//this.attacker.canEntityBeSeen(target);
        }
    }

    @Override
    public boolean shouldContinueExecuting() {
        return /*!this.attacker.getNavigator().noPath() &&*/ super.shouldContinueExecuting();
    }

    @Override
    public void startExecuting() {
    }

    @Override
    public void resetTask() {
        this.attacker.getNavigator().clearPathEntity();
    }

    @Override
    public void updateTask() {
        if( this.attacker.getNavigator().noPath() ) {
            this.attacker.getNavigator().setPath(this.entityPathEntity, this.speedTowardsTarget);
        }

        EntityLivingBase jack = this.attacker.getAttackTarget();
        if( jack == null ) {
            return;
        }

        this.attacker.getLookHelper().setLookPositionWithEntity(jack, 30.0F, 30.0F);
        double tgtDist = this.attacker.getDistanceSq(jack.posX, jack.getEntityBoundingBox().minY, jack.posZ);

        if( this.attacker.getEntitySenses().canSee(jack)
                && (this.targetX == 0.0D && this.targetY == 0.0D && this.targetZ == 0.0D
                    || jack.getDistanceSq(this.targetX, this.targetY, this.targetZ) >= 0.5D || this.attacker.getRNG().nextFloat() < 0.05F)
        ) {
            this.targetX = jack.posX;
            this.targetY = jack.getEntityBoundingBox().minY;
            this.targetZ = jack.posZ;
        }

        this.attackTick = Math.max(this.attackTick - 1, 0);
        this.checkAndPerformAttack(jack, tgtDist);
    }

    protected abstract void checkAndPerformAttack(EntityLivingBase entity, double dist);

    protected abstract double getAttackReachSqr();

    public static final class Meelee
            extends EntityAISoldierAttack
    {
        public Meelee(EntityClaySoldier creature, double speedIn) {
            super(creature, speedIn);
        }

        @Override
        protected void checkAndPerformAttack(EntityLivingBase entity, double dist) {
            if( dist <= this.getAttackReachSqr() && this.attackTick <= 0 ) {
                this.attackTick = 20;
                this.attacker.swingArm(EnumHand.MAIN_HAND);
                this.attacker.attackEntityAsMob(entity);
            }
        }

        protected double getAttackReachSqr() {
            final double reach = 0.5F + (this.attacker.hasUpgrade(Upgrades.MH_BONE, EnumUpgradeType.MAIN_HAND) ? 0.5F : 0.0F);
            return reach * reach;
        }
    }

    public static final class Ranged
            extends EntityAISoldierAttack
    {
        public Ranged(EntityClaySoldier creature, double speedIn) {
            super(creature, speedIn);
        }

        @Override
        protected void checkAndPerformAttack(EntityLivingBase entity, double dist) {
            if( dist <= this.getAttackReachSqr() ) {
                this.attacker.setMoveMultiplier(-1.0F);

                if( this.attackTick <= 0 ) {
                    this.attackTick = 20;
                    this.attacker.swingArm(EnumHand.MAIN_HAND);
                    this.attacker.getUpgradeInstanceList().stream().filter(inst -> inst.getUpgrade() instanceof ISoldierUpgradeThrowable).findFirst()
                                 .ifPresent(inst -> {
                                     ISoldierUpgradeThrowable upgThrowable = (ISoldierUpgradeThrowable) inst.getUpgrade();
                                     Entity proj = upgThrowable.createProjectile(this.attacker.world, this.attacker, entity);
                                     if( this.attacker.hasUpgrade(Upgrades.EM_SUGARCANE, EnumUpgradeType.ENHANCEMENT) && proj instanceof EntityClayProjectile ) {
                                         ((EntityClayProjectile) proj).setHoming();
                                     }
                                     this.attacker.world.spawnEntity(proj);
                                     inst.getUpgrade().onAttack(this.attacker, inst, entity, null, null);
                                 });
                }
            } else {
                this.attacker.setMoveMultiplier(1.0F);
            }
        }

        @Override
        public boolean shouldExecute() {
            return super.shouldExecute() && this.attacker.getUpgradeInstanceList().stream().anyMatch(inst -> inst.getUpgrade() instanceof ISoldierUpgradeThrowable);
        }

        @Override
        public void resetTask() {
            super.resetTask();
        }

        protected double getAttackReachSqr() {
            return this.attacker.hasUpgrade(Upgrades.EM_SUGARCANE, EnumUpgradeType.ENHANCEMENT) ? 16.0D : 9.0D;
        }
    }
}
