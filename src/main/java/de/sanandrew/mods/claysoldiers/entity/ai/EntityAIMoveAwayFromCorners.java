/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class EntityAIMoveAwayFromCorners
        extends EntityAIBase
{
    final EntityCreature taskOwner;

    final double speed;
    Path path;
    int lastCheckedTickTime;

    public EntityAIMoveAwayFromCorners(EntityCreature soldier, double speedIn) {
        this.taskOwner = soldier;
        this.speed = speedIn;
        this.setMutexBits(MutexBits.MOTION);
    }

    @Override
    public boolean shouldExecute() {
        return (this.taskOwner.collidedHorizontally && this.taskOwner.onGround) || this.path != null;
    }

    @Override
    public void resetTask() {
        this.taskOwner.getNavigator().clearPath();
        this.path = null;
    }

    @Override
    public void updateTask() {
        if( this.path != null && this.taskOwner.getNavigator().noPath() ) {
            this.resetTask();
            return;
        }

        if( !this.taskOwner.hasPath() ) {
            this.lastCheckedTickTime = this.taskOwner.ticksExisted;
            BlockPos.MutableBlockPos tgtPos = new BlockPos.MutableBlockPos(this.taskOwner.getPosition());
            for( EnumFacing facing : EnumFacing.HORIZONTALS ) {
                BlockPos blockPos = this.taskOwner.getPosition().offset(facing);
                IBlockState blockState = this.taskOwner.world.getBlockState(blockPos);
                if( blockState.isFullCube() && blockState.getCollisionBoundingBox(this.taskOwner.world, tgtPos) != Block.NULL_AABB ) {
                    tgtPos.move(facing.getOpposite());
                }
            }

            this.path = this.taskOwner.getNavigator().getPathToPos(tgtPos);
            this.taskOwner.setAttackTarget(null);
            this.taskOwner.getNavigator().setPath(this.path, this.speed);
        } else if( this.taskOwner.ticksExisted - this.lastCheckedTickTime >= 10 ) {
            this.resetTask();
        }
    }
}
