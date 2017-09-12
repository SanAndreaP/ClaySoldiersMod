/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.ai;

import de.sanandrew.mods.claysoldiers.entity.soldier.EntityClaySoldier;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.Vec3d;

public class EntityAISoldierFollowUpgrade
        extends EntityAIBase
{
    protected EntityClaySoldier attacker;
    double speedTowardsTarget;
    Path entityPathEntity;
    private double targetX;
    private double targetY;
    private double targetZ;

    public EntityAISoldierFollowUpgrade(EntityClaySoldier soldier, double speedIn) {
        this.attacker = soldier;
        this.speedTowardsTarget = speedIn;
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        Entity item = this.attacker.followingEntity;

        if( !(item instanceof EntityItem) || !item.isEntityAlive() ) {
            return false;
        } else {
            this.entityPathEntity = this.attacker.getNavigator().getPathToEntityLiving(item);
            if( this.entityPathEntity == null ) {
                Vec3d vec = new Vec3d(this.targetX - this.attacker.posX, this.targetY - this.attacker.posY, this.targetZ - this.attacker.posZ).normalize().scale(1.1D);
                this.entityPathEntity = this.attacker.getNavigator().getPathToXYZ(this.targetX + vec.x, this.targetY + vec.y, this.targetZ + vec.z);
            }
            return true;
        }
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.attacker.followingEntity instanceof EntityItem && this.attacker.followingEntity.isEntityAlive() && this.attacker.hasPath();
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
        if( !(jack instanceof EntityItem) || !jack.isEntityAlive() ) {
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
            EntityItem item = (EntityItem) jack;
            this.attacker.pickupUpgrade(item);
            this.attacker.getNavigator().clearPathEntity();
            this.attacker.followingEntity = null;
            if( item.getItem().getCount() < 1 ) {
                item.setDead();
            }
        }
    }
}
