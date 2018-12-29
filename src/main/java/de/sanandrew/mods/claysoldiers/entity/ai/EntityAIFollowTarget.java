/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.ai;

import de.sanandrew.mods.claysoldiers.api.entity.mount.IMount;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.ISoldierUpgrade;
import de.sanandrew.mods.claysoldiers.entity.soldier.EntityClaySoldier;
import de.sanandrew.mods.claysoldiers.registry.upgrade.UpgradeRegistry;
import de.sanandrew.mods.claysoldiers.registry.upgrade.Upgrades;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.Vec3d;

public abstract class EntityAIFollowTarget<E extends EntityCreature>
        extends EntityAIBase
{
    final E taskOwner;

    final double speed;
    Path path;

    private Vec3d moveTowards = null;

    EntityAIFollowTarget(E soldier, double speedIn) {
        this.taskOwner = soldier;
        this.speed = speedIn;
        this.setMutexBits(MutexBits.MOTION);
    }

    @Override
    public boolean shouldExecute() {
        Entity target = this.getTarget();
        return target != null && target.isEntityAlive() && isTargetValid() && (this.taskOwner.canEntityBeSeen(target) || this.taskOwner.hasPath());
    }

    @Override
    public void resetTask() {
        this.taskOwner.getNavigator().clearPath();
        this.clearTarget();
    }

    @Override
    public void updateTask() {
        Entity target = this.getTarget();

        if( target != null ) {
            double tgtDist = this.taskOwner.getDistanceSq(target.posX, target.getEntityBoundingBox().minY, target.posZ);

            if( tgtDist > 2.0D ) {
                this.moveTowards = null;
                this.path = this.taskOwner.getNavigator().getPathToEntityLiving(target);
                if( this.path == null ) {
                    Vec3d vec = new Vec3d(target.posX - this.taskOwner.posX, target.posY - this.taskOwner.posY, target.posZ - this.taskOwner.posZ).normalize().scale(2.0D);
                    this.path = this.taskOwner.getNavigator().getPathToXYZ(this.taskOwner.posX + vec.x, this.taskOwner.posY + vec.y, this.taskOwner.posZ + vec.z);
                }
            } else if( this.taskOwner.ticksExisted % 20 == 0 || this.moveTowards == null ) {
                this.moveTowards = new Vec3d(target.posX - this.taskOwner.posX, 0, target.posZ - this.taskOwner.posZ)
                                       .normalize().scale(this.taskOwner.getAIMoveSpeed() * 0.1D);
            }
            if( this.moveTowards != null ) {
                this.taskOwner.motionX += this.moveTowards.x;
                this.taskOwner.motionZ += this.moveTowards.z;
            }

            this.taskOwner.getLookHelper().setLookPositionWithEntity(target, 30.0F, 30.0F);

            this.checkAndPerformAction(target, tgtDist);
        } else {
            this.moveTowards = null;
            this.path = null;
            return;
        }

        if( this.taskOwner.getNavigator().noPath() && this.path != null ) {
            this.taskOwner.getNavigator().setPath(this.path, this.speed);
        }
    }

    abstract Entity getTarget();

    abstract void clearTarget();

    abstract boolean isTargetValid();

    abstract void checkAndPerformAction(Entity entity, double dist);

    public static class Fallen
            extends EntityAIFollowTarget<EntityClaySoldier>
    {
        public Fallen(EntityClaySoldier soldier, double speedIn) {
            super(soldier, speedIn);
        }

        @Override
        boolean isTargetValid() {
            if( this.taskOwner.followingEntity instanceof EntityItem && this.taskOwner.followingEntity.onGround ) {
                ItemStack stack = ((EntityItem) this.taskOwner.followingEntity).getItem();
                return ResurrectionHelper.canBeResurrected(this.taskOwner, stack);
            }
            return false;
        }

        @Override
        void checkAndPerformAction(Entity entity, double dist) {
            if( dist < 1.0F && entity instanceof EntityItem ) {
                ItemStack stack = ((EntityItem) entity).getItem();
                ResurrectionHelper.resurrectDoll(this.taskOwner, stack, entity.posX, entity.posY, entity.posZ);

                this.clearTarget();
                if( stack.getCount() < 1 ) {
                    entity.setDead();
                }
            }
        }

        @Override
        Entity getTarget() {
            return this.taskOwner.followingEntity;
        }

        @Override
        void clearTarget() {
            this.taskOwner.followingEntity = null;
        }
    }

    public static class King
            extends EntityAIFollowTarget<EntityClaySoldier>
    {
        public King(EntityClaySoldier soldier, double speedIn) {
            super(soldier, speedIn);
        }

        @Override
        boolean isTargetValid() {
            if( this.taskOwner.followingEntity instanceof ISoldier ) {
                ISoldier soldier = (ISoldier) this.taskOwner.followingEntity;
                return soldier.getSoldierTeam() == this.taskOwner.getSoldierTeam() && soldier.hasUpgrade(Upgrades.MC_GOLDNUGGET, EnumUpgradeType.MISC);
            }

            return false;
        }

        @Override
        void checkAndPerformAction(Entity entity, double dist) {
            if( dist < 1.0F ) {
                this.clearTarget();
            }
        }

        @Override
        Entity getTarget() {
            return this.taskOwner.followingEntity;
        }

        @Override
        void clearTarget() {
            this.taskOwner.followingEntity = null;
        }
    }

    public static class Mount
            extends EntityAIFollowTarget<EntityClaySoldier>
    {
        public Mount(EntityClaySoldier soldier, double speedIn) {
            super(soldier, speedIn);
        }

        @Override
        boolean isTargetValid() {
            return this.taskOwner.followingEntity instanceof IMount && this.taskOwner.getRidingEntity() == null
                           && ((IMount) this.taskOwner.followingEntity).getMaxPassengers() > this.taskOwner.followingEntity.getPassengers().size();
        }

        @Override
        void checkAndPerformAction(Entity entity, double dist) {
            if( dist < 1.0F ) {
                this.taskOwner.startRiding(this.taskOwner.followingEntity);
                this.clearTarget();
            }
        }

        @Override
        Entity getTarget() {
            return this.taskOwner.followingEntity;
        }

        @Override
        void clearTarget() {
            this.taskOwner.followingEntity = null;
        }
    }

    public static class Upgrade
            extends EntityAIFollowTarget<EntityClaySoldier>
    {
        public Upgrade(EntityClaySoldier soldier, double speedIn) {
            super(soldier, speedIn);
        }

        @Override
        boolean isTargetValid() {
            if( this.taskOwner.followingEntity instanceof EntityItem ) {
                ItemStack stack = ((EntityItem) this.taskOwner.followingEntity).getItem();
                ISoldierUpgrade upg = UpgradeRegistry.INSTANCE.getUpgrade(stack);
                return upg != null && !this.taskOwner.hasUpgrade(upg, upg.getType(this.taskOwner));
            }

            return false;
        }

        @Override
        void checkAndPerformAction(Entity entity, double dist) {
            if( dist < 1.0F && this.taskOwner.followingEntity instanceof EntityItem ) {
                EntityItem item = (EntityItem) this.taskOwner.followingEntity;
                this.taskOwner.pickupUpgrade(item);
                this.clearTarget();
                if( item.getItem().getCount() < 1 ) {
                    item.setDead();
                }
            }
        }

        @Override
        Entity getTarget() {
            return this.taskOwner.followingEntity;
        }

        @Override
        void clearTarget() {
            this.taskOwner.followingEntity = null;
        }
    }
}
