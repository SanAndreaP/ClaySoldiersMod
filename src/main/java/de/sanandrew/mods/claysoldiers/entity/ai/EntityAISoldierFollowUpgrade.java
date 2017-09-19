/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.ai;

import de.sanandrew.mods.claysoldiers.entity.soldier.EntityClaySoldier;
import de.sanandrew.mods.claysoldiers.registry.upgrade.UpgradeRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.Vec3d;

public class EntityAISoldierFollowUpgrade
        extends EntityAIBase
{
    protected EntityClaySoldier taskOwner;
    double speedTowardsTarget;
    Path entityPathEntity;
    private double targetX;
    private double targetY;
    private double targetZ;

    public EntityAISoldierFollowUpgrade(EntityClaySoldier soldier, double speedIn) {
        this.taskOwner = soldier;
        this.speedTowardsTarget = speedIn;
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        Entity item = this.taskOwner.followingEntity;

        if( !(item instanceof EntityItem) || !item.isEntityAlive() ) {
            return false;
        } else if( UpgradeRegistry.INSTANCE.getUpgrade(((EntityItem) item).getItem()) == null ) {
            return false;
        } else {
            this.entityPathEntity = this.taskOwner.getNavigator().getPathToEntityLiving(item);
            if( this.entityPathEntity == null ) {
                Vec3d vec = new Vec3d(this.targetX - this.taskOwner.posX, this.targetY - this.taskOwner.posY, this.targetZ - this.taskOwner.posZ).normalize().scale(1.1D);
                this.entityPathEntity = this.taskOwner.getNavigator().getPathToXYZ(this.targetX + vec.x, this.targetY + vec.y, this.targetZ + vec.z);
            }
            return true;
        }
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.taskOwner.hasPath() && super.shouldContinueExecuting();
    }

    @Override
    public void startExecuting() {
        if( this.entityPathEntity != null ) {
            this.taskOwner.getNavigator().setPath(this.entityPathEntity, this.speedTowardsTarget);
        }
    }

    @Override
    public void resetTask() {
        this.taskOwner.getNavigator().clearPathEntity();
        this.entityPathEntity = null;
    }

    @Override
    public void updateTask() {
        Entity jack = this.taskOwner.followingEntity;
        if( !(jack instanceof EntityItem) || !jack.isEntityAlive() ) {
            this.entityPathEntity = null;
            return;
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
            EntityItem item = (EntityItem) jack;
            this.taskOwner.pickupUpgrade(item);
            this.taskOwner.getNavigator().clearPathEntity();
            this.taskOwner.followingEntity = null;
            if( item.getItem().getCount() < 1 ) {
                item.setDead();
            }
        }
    }
}