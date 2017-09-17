/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.ai;

import de.sanandrew.mods.claysoldiers.api.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.entity.soldier.EntityClaySoldier;
import de.sanandrew.mods.claysoldiers.registry.upgrade.Upgrades;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.Vec3d;

public class EntityAISoldierFollowKing
        extends EntityAIBase
{
    protected EntityClaySoldier executor;
    double speedTowardsTarget;
    Path entityPathEntity;
    private double targetX;
    private double targetY;
    private double targetZ;

    public EntityAISoldierFollowKing(EntityClaySoldier soldier, double speedIn) {
        this.executor = soldier;
        this.speedTowardsTarget = speedIn;
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        Entity follower = this.executor.followingEntity;

        if( !this.isKingOfGroup(follower) || !follower.isEntityAlive() ) {
            return false;
        } else {
            this.entityPathEntity = this.executor.getNavigator().getPathToEntityLiving(follower);
            if( this.entityPathEntity == null ) {
                Vec3d vec = new Vec3d(this.targetX - this.executor.posX, this.targetY - this.executor.posY, this.targetZ - this.executor.posZ).normalize().scale(1.1D);
                this.entityPathEntity = this.executor.getNavigator().getPathToXYZ(this.targetX + vec.x, this.targetY + vec.y, this.targetZ + vec.z);
            }
            return true;
        }
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.executor.hasPath() && super.shouldContinueExecuting();
    }

    @Override
    public void startExecuting() {
        if( this.entityPathEntity != null ) {
            this.executor.getNavigator().setPath(this.entityPathEntity, this.speedTowardsTarget);
        }
    }

    @Override
    public void resetTask() {
        this.executor.followingEntity = null;
        this.executor.getNavigator().clearPathEntity();
        this.entityPathEntity = null;
    }

    @Override
    public void updateTask() {
        Entity jack = this.executor.followingEntity;
        if( !this.isKingOfGroup(jack) || !jack.isEntityAlive() ) {
            this.entityPathEntity = null;
            return;
        }

        this.executor.getLookHelper().setLookPositionWithEntity(jack, 30.0F, 30.0F);
        double tgtDist = this.executor.getDistanceSq(jack.posX, jack.getEntityBoundingBox().minY, jack.posZ);

        if( this.executor.getEntitySenses().canSee(jack)
                && ((this.targetX == 0.0D && this.targetY == 0.0D && this.targetZ == 0.0D)
                    || jack.getDistanceSq(this.targetX, this.targetY, this.targetZ) >= 1.0D || this.executor.getRNG().nextFloat() < 0.05F)
        ) {
            this.targetX = jack.posX;
            this.targetY = jack.getEntityBoundingBox().minY;
            this.targetZ = jack.posZ;
        }

//        if( tgtDist < 1.0F ) {
//            this.executor.getNavigator().clearPathEntity();
//            this.executor.followingEntity = null;
//        }
    }

    private boolean isKingOfGroup(Entity e) {
        if( e instanceof ISoldier ) {
            ISoldier soldier = (ISoldier) e;
            return soldier.getSoldierTeam() == this.executor.getSoldierTeam() && soldier.hasUpgrade(Upgrades.MC_GOLDNUGGET, EnumUpgradeType.MISC);
        }

        return false;
    }
}
