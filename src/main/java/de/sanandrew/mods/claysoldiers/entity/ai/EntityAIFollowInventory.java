/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.ai;

import de.sanandrew.mods.claysoldiers.api.event.SoldierInventoryEvent;
import de.sanandrew.mods.claysoldiers.entity.soldier.EntityClaySoldier;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import de.sanandrew.mods.sanlib.lib.util.ItemStackUtils;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class EntityAIFollowInventory
        extends EntityAIBase
{
    final EntityClaySoldier taskOwner;

    final double speed;
    Path path;

    public EntityAIFollowInventory(EntityClaySoldier soldier, double speedIn) {
        this.taskOwner = soldier;
        this.speed = speedIn;
        this.setMutexBits(MutexBits.MOTION);
    }

    @Override
    public boolean shouldExecute() {
        return this.taskOwner.followingBlock != null && this.isTargetValid();
    }

    @Override
    public void updateTask() {
        BlockPos target = this.taskOwner.followingBlock;

        if( target != null ) {
            this.path = this.taskOwner.getNavigator().getPathToPos(target);
            if( this.path == null ) {
                Vec3d vec = new Vec3d(target.getX() - this.taskOwner.posX, target.getY() - this.taskOwner.posY, target.getZ() - this.taskOwner.posZ).normalize().scale(2.0D);
                this.path = this.taskOwner.getNavigator().getPathToXYZ(this.taskOwner.posX + vec.x, this.taskOwner.posY + vec.y, this.taskOwner.posZ + vec.z);
            }
        } else {
            this.path = null;
            return;
        }

        if( this.taskOwner.getNavigator().noPath() && this.path != null ) {
            this.taskOwner.getNavigator().setPath(this.path, this.speed);
        }

        this.taskOwner.getLookHelper().setLookPosition(target.getX(), target.getY(), target.getZ(), 30.0F, 30.0F);
        double tgtDist = this.taskOwner.getDistanceSq(target.getX() + 0.5D, target.getY() + 0.5D, target.getZ() + 0.5D);

        if( tgtDist < 9.0 && UpgradesChestHelper.isBlockTileValid(this.taskOwner.world, this.taskOwner.followingBlock) ) {
            ItemStack stack = UpgradesChestHelper.getStackFromInv(this.taskOwner, this.taskOwner.world.getTileEntity(target));
            if( ItemStackUtils.isValid(stack) ) {
                ClaySoldiersMod.EVENT_BUS.post(new SoldierInventoryEvent.Grab(this.taskOwner, this.taskOwner.followingBlock, stack));
            }
        }
    }

    @Override
    public void resetTask() {
        this.taskOwner.getNavigator().clearPath();
        this.taskOwner.followingBlock = null;
    }

    public boolean isTargetValid() {
        return UpgradesChestHelper.isBlockTileValid(this.taskOwner.world, this.taskOwner.followingBlock)
                       && ItemStackUtils.isValid(UpgradesChestHelper.getStackFromInv(this.taskOwner, this.taskOwner.world.getTileEntity(this.taskOwner.followingBlock)));
    }
}
