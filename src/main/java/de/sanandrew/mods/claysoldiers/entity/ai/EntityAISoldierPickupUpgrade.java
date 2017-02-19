/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.ai;

import de.sanandrew.mods.claysoldiers.entity.EntityClaySoldier;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityAISoldierPickupUpgrade
        extends EntityAIBase
{
    protected EntityClaySoldier attacker;
    double speedTowardsTarget;
    Path entityPathEntity;
    private double targetX;
    private double targetY;
    private double targetZ;

    public EntityAISoldierPickupUpgrade(EntityClaySoldier soldier, double speedIn) {
        this.attacker = soldier;
        this.speedTowardsTarget = speedIn;
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {
        Entity item = this.attacker.followingEntity;

        if( !(item instanceof EntityItem) ) {
            return false;
        } else if( !item.isEntityAlive() ) {
            return false;
        } else {
            this.entityPathEntity = this.attacker.getNavigator().getPathToEntityLiving(item);
            if( this.entityPathEntity == null ) {
                Vec3d vec = new Vec3d(this.targetX - this.attacker.posX, this.targetY - this.attacker.posY, this.targetZ - this.attacker.posZ).normalize().scale(1.1D);
                this.entityPathEntity = this.attacker.getNavigator().getPathToXYZ(this.targetX + vec.xCoord, this.targetY + vec.yCoord, this.targetZ + vec.zCoord);
            }
            return this.entityPathEntity != null;
        }
    }

    @Override
    public boolean continueExecuting() {
        return this.attacker.followingEntity instanceof EntityItem && (this.attacker.followingEntity.isEntityAlive() && (!this.attacker.getNavigator().noPath()));
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
        Entity jack = this.attacker.followingEntity;
        if( !(jack instanceof EntityItem) ) {
            return;
        }

        this.attacker.getLookHelper().setLookPositionWithEntity(jack, 30.0F, 30.0F);
        double tgtDist = this.attacker.getDistanceSq(jack.posX, jack.getEntityBoundingBox().minY, jack.posZ);

        if( this.attacker.getEntitySenses().canSee(jack)
                && (this.targetX == 0.0D && this.targetY == 0.0D && this.targetZ == 0.0D
                    || jack.getDistanceSq(this.targetX, this.targetY, this.targetZ) >= 1.0D || this.attacker.getRNG().nextFloat() < 0.05F)
        ) {
            this.targetX = jack.posX;
            this.targetY = jack.getEntityBoundingBox().minY;
            this.targetZ = jack.posZ;
        }

        if( this.attacker.getDistanceSqToEntity(jack) < 1.0F ) {
            EntityItem item = (EntityItem) jack;
            this.attacker.pickupUpgrade(item);
            if( item.getEntityItem().stackSize < 1 ) {
                item.setDead();
            }
        }
    }
}
