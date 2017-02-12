/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class PathNavigateSoldier
        extends PathNavigateGround
{
    private NavPos targetPos;

    public PathNavigateSoldier(EntityLiving entitylivingIn, World worldIn) {
        super(entitylivingIn, worldIn);
    }

    @Override
    public boolean canUpdatePathOnTimeout() {
        return this.tryUpdatePath;
    }

    @Override
    public void updatePath() {
        if( this.worldObj.getTotalWorldTime() - this.lastTimeUpdated > 20L ) {
            if( this.targetPos != null ) {
                this.currentPath = null;
                this.currentPath = this.getPathToPos2(this.targetPos);
                this.lastTimeUpdated = this.worldObj.getTotalWorldTime();
                this.tryUpdatePath = false;
            }
        } else {
            this.tryUpdatePath = true;
        }
    }

    /**
     * Returns the path to the given coordinates. Args : x, y, z
     */
    @Nullable
    public final Path getPathToXYZ2(double x, double y, double z) {
        return this.getPathToPos(new BlockPos(x, y, z));
    }

    @Nullable
    @Override
    public Path getPathToPos(BlockPos pos) {
        if( !this.canNavigate() ) {
            return null;
        } else if( this.currentPath != null && !this.currentPath.isFinished() && pos.equals(this.targetPos) ) {
            return this.currentPath;
        } else {
            this.targetPos = new NavPos(pos.getX(), pos.getY(), pos.getZ());
            float f = this.getPathSearchRange();
            this.worldObj.theProfiler.startSection("pathfind");
            BlockPos blockpos = new BlockPos(this.theEntity);
            int i = (int) (f + 8.0F);
            ChunkCache chunkcache = new ChunkCache(this.worldObj, blockpos.add(-i, -i, -i), blockpos.add(i, i, i), 0);
            Path path = this.pathFinder.findPath(chunkcache, this.theEntity, this.targetPos.x, this.targetPos.y, this.targetPos.z, f);
            this.worldObj.theProfiler.endSection();
            return path;
        }
    }

    @Nullable
    public Path getPathToPos2(NavPos pos) {
        if( !this.canNavigate() ) {
            return null;
        } else if( this.currentPath != null && !this.currentPath.isFinished() && pos.equals(this.targetPos) ) {
            return this.currentPath;
        } else {
            this.targetPos = pos;
            float f = this.getPathSearchRange();
            this.worldObj.theProfiler.startSection("pathfind");
            BlockPos blockpos = new BlockPos(this.theEntity);
            int i = (int) (f + 8.0F);
            ChunkCache chunkcache = new ChunkCache(this.worldObj, blockpos.add(-i, -i, -i), blockpos.add(i, i, i), 0);
            Path path = this.pathFinder.findPath(chunkcache, this.theEntity, this.targetPos.x, this.targetPos.y, this.targetPos.z, f);
            this.worldObj.theProfiler.endSection();
            return path;
        }
    }

    @Nullable
    public Path getPathToEntityLiving(Entity entityIn) {
        if( !this.canNavigate() ) {
            return null;
        } else {
            NavPos navPos = new NavPos(entityIn.posX, entityIn.posY, entityIn.posZ);

            if( this.currentPath != null && !this.currentPath.isFinished() && navPos.equals(this.targetPos) ) {
                return this.currentPath;
            } else {
                this.targetPos = navPos;
                float f = this.getPathSearchRange();
                this.worldObj.theProfiler.startSection("pathfind");
                BlockPos blockpos1 = (new BlockPos(this.theEntity)).up();
                int i = (int) (f + 16.0F);
                ChunkCache chunkcache = new ChunkCache(this.worldObj, blockpos1.add(-i, -i, -i), blockpos1.add(i, i, i), 0);
                Path path = this.pathFinder.findPath(chunkcache, this.theEntity, entityIn.posX, entityIn.posY, entityIn.posZ, f);
                this.worldObj.theProfiler.endSection();
                return path;
            }
        }
    }

    public void onUpdateNavigation() {
        ++this.totalTicks;

        if( this.tryUpdatePath ) {
            this.updatePath();
        }

        if( !this.noPath() ) {
            if( this.canNavigate() ) {
                this.pathFollow();
            } else if( this.currentPath != null && this.currentPath.getCurrentPathIndex() < this.currentPath.getCurrentPathLength() ) {
                Vec3d vec3d = this.getEntityPosition();
                Vec3d vec3d1 = this.currentPath.getVectorFromIndex(this.theEntity, this.currentPath.getCurrentPathIndex());

                if( vec3d.yCoord > vec3d1.yCoord && !this.theEntity.onGround && MathHelper.floor_double(vec3d.xCoord) == MathHelper.floor_double(vec3d1.xCoord) && MathHelper
                        .floor_double(vec3d.zCoord) == MathHelper.floor_double(vec3d1.zCoord) ) {
                    this.currentPath.setCurrentPathIndex(this.currentPath.getCurrentPathIndex() + 1);
                }
            }

            if( !this.noPath() ) {
                Vec3d vec3d2 = this.currentPath.getPosition(this.theEntity);

                if( vec3d2 != null ) {
                    BlockPos blockpos = (new BlockPos(vec3d2)).down();
                    AxisAlignedBB axisalignedbb = this.worldObj.getBlockState(blockpos).getBoundingBox(this.worldObj, blockpos);
                    vec3d2 = vec3d2.subtract(0.0D, 1.0D - axisalignedbb.maxY, 0.0D);
                    this.theEntity.getMoveHelper().setMoveTo(vec3d2.xCoord, vec3d2.yCoord, vec3d2.zCoord, this.speed);
                }
            }
        }
    }

    @Override
    public boolean tryMoveToXYZ(double x, double y, double z, double speedIn) {
        return this.setPath(this.getPathToXYZ2(x, y, z), speedIn);
    }

    @Override
    protected boolean isInLiquid() {
        return false;
    }

    public static class NavPos
    {
        public double x;
        public double y;
        public double z;

        public NavPos(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public boolean equals(Object obj) {
            if( obj instanceof NavPos ) {
                NavPos otr = (NavPos) obj;
                double dX = this.x - otr.x;
                double dY = this.y - otr.y;
                double dZ = this.z - otr.z;

                return dX > -0.1 && dX < 0.1 && dY > -0.1 && dY < 0.1 && dZ > -0.1 && dZ < 0.1;
            }
            return super.equals(obj);
        }
    }
}
