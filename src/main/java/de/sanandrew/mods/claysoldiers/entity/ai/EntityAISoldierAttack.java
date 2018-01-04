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
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
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
        } else {
            boolean b1 = this.attacker.canEntityBeSeen(target);
            boolean b2 = !this.attacker.getNavigator().noPath();
            return b1 || b2;
        }
    }

    @Override
    public void resetTask() {
        this.attacker.getNavigator().clearPathEntity();
    }

    @Override
    public void updateTask() {
        EntityLivingBase target = this.attacker.getAttackTarget();

        if( target != null ) {
            this.entityPathEntity = this.attacker.getNavigator().getPathToEntityLiving(target);
            if( this.entityPathEntity == null ) {
                Vec3d vec = new Vec3d(target.posX - this.attacker.posX, target.posY - this.attacker.posY, target.posZ - this.attacker.posZ).normalize().scale(2.0D);
                this.entityPathEntity = this.attacker.getNavigator().getPathToXYZ(this.attacker.posX + vec.x, this.attacker.posY + vec.y, this.attacker.posZ + vec.z);
            }
        } else {
            this.entityPathEntity = null;
            return;
        }

        if( this.attacker.getNavigator().noPath() ) {
            this.attacker.getNavigator().setPath(this.entityPathEntity, this.speedTowardsTarget);
        }

        this.attacker.getLookHelper().setLookPositionWithEntity(target, 30.0F, 30.0F);
        double tgtDist = this.attacker.getDistanceSq(target.posX, target.getEntityBoundingBox().minY, target.posZ);

        this.attackTick = Math.max(this.attackTick - 1, 0);
        this.checkAndPerformAttack(target, tgtDist);
    }

    protected abstract void checkAndPerformAttack(EntityLivingBase entity, double dist);

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
                                     this.attacker.world.spawnEntity(upgThrowable.createProjectile(this.attacker.world, this.attacker, entity));
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
