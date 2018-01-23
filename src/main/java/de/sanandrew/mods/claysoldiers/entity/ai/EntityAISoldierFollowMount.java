/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.ai;

import de.sanandrew.mods.claysoldiers.api.mount.IMount;
import de.sanandrew.mods.claysoldiers.entity.soldier.EntityClaySoldier;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.Vec3d;

public class EntityAISoldierFollowMount
        extends EntityAIBase
{
    protected EntityClaySoldier taskOwner;
    double speedTowardsTarget;
    Path entityPathEntity;
    private double targetX;
    private double targetY;
    private double targetZ;

    public EntityAISoldierFollowMount(EntityClaySoldier soldier, double speedIn) {
        this.taskOwner = soldier;
        this.speedTowardsTarget = speedIn;
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        Entity mount = this.taskOwner.followingEntity;

        if( mount instanceof IMount ) {
            if( this.taskOwner.getRidingEntity() != null || !this.isMountRidable(mount) || !mount.isEntityAlive() ) {
                this.taskOwner.followingEntity = null;
                return false;
            } else {
                this.entityPathEntity = this.taskOwner.getNavigator().getPathToEntityLiving(mount);
                if( this.entityPathEntity == null ) {
                    Vec3d vec = new Vec3d(this.targetX - this.taskOwner.posX, this.targetY - this.taskOwner.posY, this.targetZ - this.taskOwner.posZ).normalize().scale(1.1D);
                    this.entityPathEntity = this.taskOwner.getNavigator().getPathToXYZ(this.targetX + vec.x, this.targetY + vec.y, this.targetZ + vec.z);
                }
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public void startExecuting() {
    }

    @Override
    public void resetTask() {
        this.taskOwner.getNavigator().clearPathEntity();
        this.entityPathEntity = null;
    }

    @Override
    public void updateTask() {
        Entity jack = this.taskOwner.followingEntity;
        if( this.taskOwner.getRidingEntity() != null || !this.isMountRidable(jack) || !jack.isEntityAlive() ) {
            this.entityPathEntity = null;
            return;
        }

        if( this.taskOwner.getNavigator().noPath() && this.entityPathEntity != null ) {
            this.taskOwner.getNavigator().setPath(this.entityPathEntity, this.speedTowardsTarget);
        }

        this.taskOwner.getLookHelper().setLookPositionWithEntity(jack, 30.0F, 30.0F);
        double tgtDist = this.taskOwner.getDistanceSq(jack.posX, jack.getEntityBoundingBox().minY, jack.posZ);

        if( this.taskOwner.getEntitySenses().canSee(jack)
                && ((this.targetX == 0.0D && this.targetY == 0.0D && this.targetZ == 0.0D)
                    || jack.getDistanceSq(this.targetX, this.targetY, this.targetZ) >= 1.0D || this.taskOwner.getRNG().nextFloat() < 0.05F)
        ) {
            this.targetX = jack.posX;
            this.targetY = jack.getEntityBoundingBox().minY;
            this.targetZ = jack.posZ;
        }

        if( tgtDist < 1.0F ) {
            this.taskOwner.startRiding(jack);
            this.taskOwner.getNavigator().clearPathEntity();
            this.taskOwner.followingEntity = null;
        }
    }

    private boolean isMountRidable(Entity e) {
        return e instanceof IMount && ((IMount) e).getMaxPassengers() > e.getPassengers().size();
    }
}
