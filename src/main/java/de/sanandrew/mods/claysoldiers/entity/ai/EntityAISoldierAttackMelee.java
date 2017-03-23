/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;

public class EntityAISoldierAttackMelee
        extends EntityAIBase
{
    protected EntityCreature attacker;
    protected int attackTick;
    double speedTowardsTarget;
    Path entityPathEntity;
    private double targetX;
    private double targetY;
    private double targetZ;

    public EntityAISoldierAttackMelee(EntityCreature creature, double speedIn) {
        this.attacker = creature;
        this.speedTowardsTarget = speedIn;
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {
        EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();

        if( entitylivingbase == null ) {
            return false;
        } else if( !entitylivingbase.isEntityAlive() ) {
            return false;
        } else {
            this.entityPathEntity = this.attacker.getNavigator().getPathToEntityLiving(entitylivingbase);
            if( this.entityPathEntity == null ) {
                Vec3d vec = new Vec3d(this.targetX - this.attacker.posX, this.targetY - this.attacker.posY, this.targetZ - this.attacker.posZ).normalize().scale(1.1D);
                this.entityPathEntity = this.attacker.getNavigator().getPathToXYZ(this.targetX + vec.xCoord, this.targetY + vec.yCoord, this.targetZ + vec.zCoord);
            }
            return this.entityPathEntity != null;
        }
    }

    @Override
    public boolean continueExecuting() {
        EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
        return entitylivingbase != null && (entitylivingbase.isEntityAlive() && (!this.attacker.getNavigator().noPath()));
    }

    @Override
    public void startExecuting() {
        this.attacker.getNavigator().setPath(this.entityPathEntity, this.speedTowardsTarget);
    }

    @Override
    public void resetTask() {
        this.attacker.getNavigator().clearPathEntity();
    }

    @Override
    public void updateTask() {
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

    protected void checkAndPerformAttack(EntityLivingBase entity, double dist) {
        double d0 = this.getAttackReachSqr(entity);

        if( dist <= d0 && this.attackTick <= 0 ) {
            this.attackTick = 20;
            this.attacker.swingArm(EnumHand.MAIN_HAND);
            this.attacker.attackEntityAsMob(entity);
        }
    }

    protected double getAttackReachSqr(EntityLivingBase attackTarget) {
        return this.attacker.width * 1.5F * this.attacker.width * 1.5F + attackTarget.width;
    }
}
