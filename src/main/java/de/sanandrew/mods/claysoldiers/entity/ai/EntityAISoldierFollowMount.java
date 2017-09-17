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
    protected EntityClaySoldier attacker;
    double speedTowardsTarget;
    Path entityPathEntity;
    private double targetX;
    private double targetY;
    private double targetZ;

    public EntityAISoldierFollowMount(EntityClaySoldier soldier, double speedIn) {
        this.attacker = soldier;
        this.speedTowardsTarget = speedIn;
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        Entity mount = this.attacker.followingEntity;

        if( this.attacker.getRidingEntity() != null || !this.isMountRidable(mount) || !mount.isEntityAlive() ) {
            return false;
        } else {
            this.entityPathEntity = this.attacker.getNavigator().getPathToEntityLiving(mount);
            if( this.entityPathEntity == null ) {
                Vec3d vec = new Vec3d(this.targetX - this.attacker.posX, this.targetY - this.attacker.posY, this.targetZ - this.attacker.posZ).normalize().scale(1.1D);
                this.entityPathEntity = this.attacker.getNavigator().getPathToXYZ(this.targetX + vec.x, this.targetY + vec.y, this.targetZ + vec.z);
            }
            return true;
        }
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.attacker.hasPath() && super.shouldContinueExecuting();
    }

    @Override
    public void startExecuting() {
        if( this.entityPathEntity != null ) {
            this.attacker.getNavigator().setPath(this.entityPathEntity, this.speedTowardsTarget);
        }
    }

    @Override
    public void resetTask() {
        this.attacker.getNavigator().clearPathEntity();
        this.entityPathEntity = null;
    }

    @Override
    public void updateTask() {
        Entity jack = this.attacker.followingEntity;
        if( this.attacker.getRidingEntity() != null || !this.isMountRidable(jack) || !jack.isEntityAlive() ) {
            this.entityPathEntity = null;
            return;
        }

        this.attacker.getLookHelper().setLookPositionWithEntity(jack, 30.0F, 30.0F);
        double tgtDist = this.attacker.getDistanceSq(jack.posX, jack.getEntityBoundingBox().minY, jack.posZ);

        if( this.attacker.getEntitySenses().canSee(jack)
                && ((this.targetX == 0.0D && this.targetY == 0.0D && this.targetZ == 0.0D)
                    || jack.getDistanceSq(this.targetX, this.targetY, this.targetZ) >= 1.0D || this.attacker.getRNG().nextFloat() < 0.05F)
        ) {
            this.targetX = jack.posX;
            this.targetY = jack.getEntityBoundingBox().minY;
            this.targetZ = jack.posZ;
        }

        if( tgtDist < 1.0F ) {
            this.attacker.startRiding(jack);
            this.attacker.getNavigator().clearPathEntity();
            this.attacker.followingEntity = null;
        }
    }

    private boolean isMountRidable(Entity e) {
        return e instanceof IMount && ((IMount) e).getMaxPassengers() > e.getPassengers().size();
    }
}
