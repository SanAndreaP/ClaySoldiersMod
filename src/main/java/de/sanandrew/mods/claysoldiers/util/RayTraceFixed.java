/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class RayTraceFixed
{
//    @Nullable
    public static boolean collisionRayTrace(IBlockState blockState, Entity e, World worldIn, BlockPos pos, Vec3d start, Vec3d end) {
        List<AxisAlignedBB> collisionBBs = new ArrayList<>();
        blockState.addCollisionBoxToList(worldIn, pos, blockState.getBoundingBox(worldIn, pos).offset(pos), collisionBBs, e, true);

        start = start.subtract(pos.getX(), pos.getY(), pos.getZ());
        end = end.subtract(pos.getX(), pos.getY(), pos.getZ());
        Vec3d dist = end.subtract(start);

        for( AxisAlignedBB aabb : collisionBBs ) {
            aabb = aabb.offset(-pos.getX(), -pos.getY(), -pos.getZ());//.grow(1.0D / 32.0D);
            if( calcIntercept(aabb, start, dist) ) {
                return true;
            }
//            RayTraceResult res = rayTrace(pos, start, end, aabb.grow(0.1D));
//            if( res != null ) {
//                return res;
//            }
//            rayTrace(pos, start, end, aabb.grow(0.1D));
        }
        return false;
    }

//    @Nullable
//    private static RayTraceResult rayTrace(BlockPos pos, Vec3d start, Vec3d end, AxisAlignedBB boundingBox) {
////        RayTraceResult raytraceresult = boundingBox.calculateIntercept(start, end);
//        return raytraceresult == null ? null : new RayTraceResult(raytraceresult.hitVec, raytraceresult.sideHit, pos);
//    }

    private static boolean calcIntercept(AxisAlignedBB aabb, Vec3d origin, Vec3d dist) {
        Vec3d invDist = new Vec3d(1.0D / dist.x, 1.0D / dist.y, 1.0D / dist.z);

        boolean signDirX = invDist.x < 0;
        boolean signDirY = invDist.y < 0;
        boolean signDirZ = invDist.z < 0;

        double tMin = ((signDirX ? aabb.maxX : aabb.minX) - origin.x) * invDist.x;
        double tMax = ((signDirX ? aabb.minX : aabb.maxX) - origin.x) * invDist.x;
        double tMinY = ((signDirY ? aabb.maxY : aabb.minY) - origin.y) * invDist.y;
        double tMaxY = ((signDirY ? aabb.minY : aabb.maxY) - origin.y) * invDist.y;

        if( tMin > tMaxY || tMinY > tMax ) {
            return false;
        }

        if( tMinY > tMin ) {
            tMin = tMinY;
        }
        if( tMaxY < tMax ) {
            tMax = tMaxY;
        }

        double tMinZ = ((signDirZ ? aabb.maxZ : aabb.minZ) - origin.z) * invDist.z;
        double tMaxZ = ((signDirZ ? aabb.minZ : aabb.maxZ) - origin.z) * invDist.z;

        return !(tMin > tMaxZ) && !(tMinZ > tMax);
        //        double t0x = (aabb.minX - origin.x) / dist.x;
//        double t1x = (aabb.maxX - origin.x) / dist.x;
//        double t0y = (aabb.minY - origin.y) / dist.y;
//        double t1y = (aabb.maxY - origin.y) / dist.y;
//        double t0z = (aabb.minZ - origin.z) / dist.z;
//        double t1z = (aabb.maxZ - origin.z) / dist.z;
//
//        double tMin = Math.max(t0x, t0y);
//        double tMax = Math.min(t1x, t1y);
//
//        if( t0x > t1y || t0y > t1x || tMin > t1z || t0z > tMax ) {
//            return false;
//        }

//        return true;
//        tMin = Math.max(t0z, tMin);
//        tMax = Math.min(t1z, tMax);
    }

    @Nullable
    public static boolean rayTraceSight(Entity e, World world, Vec3d from, Vec3d to) {
        Set<BlockPos> blocksToCheck = new HashSet<>();
        Vec3d dirVec = to.subtract(from);
        if( dirVec.lengthSquared() < 0.0001D ) {
            return false;
        }
        Vec3d dirPart = dirVec.scale(Math.min(1.0D, 1.0D / Math.floor(dirVec.lengthVector())));
        Vec3d dirNrm = dirVec.normalize();

        Vec3d newFrom = from;
        Vec3d newTo = newFrom.add(dirPart);
        double dirLength = dirVec.lengthSquared();
//        double prevDist = Double.MAX_VALUE;
        do {
//            prevDist = newFrom.distanceTo(to);
            BlockPos bPosF = new BlockPos(newFrom);
            BlockPos bPosT = new BlockPos(newFrom = newTo);
            for( int x = Math.min(bPosF.getX(), bPosT.getX()), mX = Math.max(bPosF.getX(), bPosT.getX()); x <= mX; x++ ) {
                for( int y = Math.min(bPosF.getY(), bPosT.getY()), mY = Math.max(bPosF.getY(), bPosT.getY()); y <= mY; y++ ) {
                    for( int z = Math.min(bPosF.getZ(), bPosT.getZ()), mZ = Math.max(bPosF.getZ(), bPosT.getZ()); z <= mZ; z++ ) {
                        blocksToCheck.add(new BlockPos(x, y, z));
                    }
                }
            }
        } while( (newTo = newFrom.add(dirPart)).lengthSquared() <= dirLength );

        for( BlockPos block : blocksToCheck ) {
            if( collisionRayTrace(world.getBlockState(block), e, world, block, from, to) ) {
                return true;
            }
        }
        for( BlockPos block : blocksToCheck ) {
            if( collisionRayTrace(world.getBlockState(block), e, world, block, from, to) ) {
                return true;
            }
        }

        return false;

//        if (!Double.isNaN(from.x) && !Double.isNaN(from.y) && !Double.isNaN(from.z))
//        {
//            if (!Double.isNaN(to.x) && !Double.isNaN(to.y) && !Double.isNaN(to.z))
//            {
//                int i = MathHelper.floor(to.x);
//                int j = MathHelper.floor(to.y);
//                int k = MathHelper.floor(to.z);
//                int l = MathHelper.floor(from.x);
//                int i1 = MathHelper.floor(from.y);
//                int j1 = MathHelper.floor(from.z);
//                BlockPos blockpos = new BlockPos(l, i1, j1);
//                IBlockState iblockstate = world.getBlockState(blockpos);
//                Block block = iblockstate.getBlock();
//
//                if ((iblockstate.getCollisionBoundingBox(world, blockpos) != Block.NULL_AABB) && block.canCollideCheck(iblockstate, false))
//                {
////                    RayTraceResult raytraceresult = iblockstate.collisionRayTrace(this, blockpos, from, to);
//
//                    if (collisionRayTrace(iblockstate, e, world, blockpos, from, to))
//                    {
//                        return true;
//                    }
//                }
//
////                RayTraceResult raytraceresult2 = null;
//                int k1 = 200;
//
//                while (k1-- >= 0)
//                {
//                    if (Double.isNaN(from.x) || Double.isNaN(from.y) || Double.isNaN(from.z))
//                    {
//                        return false;
//                    }
//
//                    if (l == i && i1 == j && j1 == k)
//                    {
//                        return false;
//                    }
//
//                    boolean flag2 = true;
//                    boolean flag = true;
//                    boolean flag1 = true;
//                    double d0 = 999.0D;
//                    double d1 = 999.0D;
//                    double d2 = 999.0D;
//
//                    if (i > l)
//                    {
//                        d0 = (double)l + 1.0D;
//                    }
//                    else if (i < l)
//                    {
//                        d0 = (double)l + 0.0D;
//                    }
//                    else
//                    {
//                        flag2 = false;
//                    }
//
//                    if (j > i1)
//                    {
//                        d1 = (double)i1 + 1.0D;
//                    }
//                    else if (j < i1)
//                    {
//                        d1 = (double)i1 + 0.0D;
//                    }
//                    else
//                    {
//                        flag = false;
//                    }
//
//                    if (k > j1)
//                    {
//                        d2 = (double)j1 + 1.0D;
//                    }
//                    else if (k < j1)
//                    {
//                        d2 = (double)j1 + 0.0D;
//                    }
//                    else
//                    {
//                        flag1 = false;
//                    }
//
//                    double d3 = 999.0D;
//                    double d4 = 999.0D;
//                    double d5 = 999.0D;
//                    double d6 = to.x - from.x;
//                    double d7 = to.y - from.y;
//                    double d8 = to.z - from.z;
//
//                    if (flag2)
//                    {
//                        d3 = (d0 - from.x) / d6;
//                    }
//
//                    if (flag)
//                    {
//                        d4 = (d1 - from.y) / d7;
//                    }
//
//                    if (flag1)
//                    {
//                        d5 = (d2 - from.z) / d8;
//                    }
//
//                    if (d3 == -0.0D)
//                    {
//                        d3 = -1.0E-4D;
//                    }
//
//                    if (d4 == -0.0D)
//                    {
//                        d4 = -1.0E-4D;
//                    }
//
//                    if (d5 == -0.0D)
//                    {
//                        d5 = -1.0E-4D;
//                    }
//
//                    EnumFacing enumfacing;
//
//                    if (d3 < d4 && d3 < d5)
//                    {
//                        enumfacing = i > l ? EnumFacing.WEST : EnumFacing.EAST;
//                        from = new Vec3d(d0, from.y + d7 * d3, from.z + d8 * d3);
//                    }
//                    else if (d4 < d5)
//                    {
//                        enumfacing = j > i1 ? EnumFacing.DOWN : EnumFacing.UP;
//                        from = new Vec3d(from.x + d6 * d4, d1, from.z + d8 * d4);
//                    }
//                    else
//                    {
//                        enumfacing = k > j1 ? EnumFacing.NORTH : EnumFacing.SOUTH;
//                        from = new Vec3d(from.x + d6 * d5, from.y + d7 * d5, d2);
//                    }
//
//                    l = MathHelper.floor(from.x) - (enumfacing == EnumFacing.EAST ? 1 : 0);
//                    i1 = MathHelper.floor(from.y) - (enumfacing == EnumFacing.UP ? 1 : 0);
//                    j1 = MathHelper.floor(from.z) - (enumfacing == EnumFacing.SOUTH ? 1 : 0);
//                    blockpos = new BlockPos(l, i1, j1);
//                    IBlockState iblockstate1 = world.getBlockState(blockpos);
//                    Block block1 = iblockstate1.getBlock();
//
//                    if (iblockstate1.getMaterial() == Material.PORTAL || iblockstate1.getCollisionBoundingBox(world, blockpos) != Block.NULL_AABB)
//                    {
//                        if (block1.canCollideCheck(iblockstate1, false))
//                        {
////                            RayTraceResult raytraceresult1 = iblockstate1.collisionRayTrace(world, blockpos, from, to);
//
//                            if (collisionRayTrace(iblockstate1, e, world, blockpos, from, to))
//                            {
//                                return true;
//                            }
//                        }
//                        else
//                        {
////                            raytraceresult2 = new RayTraceResult(RayTraceResult.Type.MISS, from, enumfacing, blockpos);
//                        }
//                    }
//                }
//
//                return false;
//            }
//            else
//            {
//                return false;
//            }
//        }
//        else
//        {
//            return false;
//        }



//        if( !Double.isNaN(from.x) && !Double.isNaN(from.y) && !Double.isNaN(from.z) ) {
//            if( !Double.isNaN(to.x) && !Double.isNaN(to.y) && !Double.isNaN(to.z) ) {
//                int bBlockX = MathHelper.floor(to.x);
//                int bBlockY = MathHelper.floor(to.y);
//                int bBlockZ = MathHelper.floor(to.z);
//                int eBlockX = MathHelper.floor(from.x);
//                int eBlockY = MathHelper.floor(from.y);
//                int eBlockZ = MathHelper.floor(from.z);
//                BlockPos endBlockPos = new BlockPos(eBlockX, eBlockY, eBlockZ);
//                IBlockState endBlockState = world.getBlockState(endBlockPos);
//
//                if( (endBlockState.getCollisionBoundingBox(world, endBlockPos) != Block.NULL_AABB) && endBlockState.getBlock().canCollideCheck(endBlockState, false) ) {
//                    return collisionRayTrace(endBlockState, e, world, endBlockPos, from, to);
//                }
//
//                int k1 = 200;
//
//                while( k1-- >= 0 ) {
//                    if( Double.isNaN(from.x) || Double.isNaN(from.y) || Double.isNaN(from.z) ) {
//                        return false;
//                    }
//
//                    if( eBlockX == bBlockX && eBlockY == bBlockY && eBlockZ == bBlockZ ) {
//                        return false;
//                    }
//
//                    boolean flag2 = true;
//                    boolean flag = true;
//                    boolean flag1 = true;
//                    double d0 = 999.0D;
//                    double d1 = 999.0D;
//                    double d2 = 999.0D;
//
//                    if( bBlockX > eBlockX ) {
//                        d0 = eBlockX + 1.0D;
//                    } else if( bBlockX < eBlockX ) {
//                        d0 = eBlockX + 0.0D;
//                    } else {
//                        flag2 = false;
//                    }
//
//                    if( bBlockY > eBlockY ) {
//                        d1 = eBlockY + 1.0D;
//                    } else if( bBlockY < eBlockY ) {
//                        d1 = eBlockY + 0.0D;
//                    } else {
//                        flag = false;
//                    }
//
//                    if( bBlockZ > eBlockZ ) {
//                        d2 = eBlockZ + 1.0D;
//                    } else if( bBlockZ < eBlockZ ) {
//                        d2 = eBlockZ + 0.0D;
//                    } else {
//                        flag1 = false;
//                    }
//
//                    double d3 = 999.0D;
//                    double d4 = 999.0D;
//                    double d5 = 999.0D;
//                    double d6 = to.x - from.x;
//                    double d7 = to.y - from.y;
//                    double d8 = to.z - from.z;
//
//                    if( flag2 ) {
//                        d3 = (d0 - from.x) / d6;
//                    }
//
//                    if( flag ) {
//                        d4 = (d1 - from.y) / d7;
//                    }
//
//                    if( flag1 ) {
//                        d5 = (d2 - from.z) / d8;
//                    }
//
//                    if( d3 == -0.0D ) {
//                        d3 = -1.0E-4D;
//                    }
//
//                    if( d4 == -0.0D ) {
//                        d4 = -1.0E-4D;
//                    }
//
//                    if( d5 == -0.0D ) {
//                        d5 = -1.0E-4D;
//                    }
//
//                    EnumFacing enumfacing;
//
//                    if( d3 < d4 && d3 < d5 ) {
//                        enumfacing = bBlockX > eBlockX ? EnumFacing.WEST : EnumFacing.EAST;
//                        from = new Vec3d(d0, from.y + d7 * d3, from.z + d8 * d3);
//                    } else if( d4 < d5 ) {
//                        enumfacing = bBlockY > eBlockY ? EnumFacing.DOWN : EnumFacing.UP;
//                        from = new Vec3d(from.x + d6 * d4, d1, from.z + d8 * d4);
//                    } else {
//                        enumfacing = bBlockZ > eBlockZ ? EnumFacing.NORTH : EnumFacing.SOUTH;
//                        from = new Vec3d(from.x + d6 * d5, from.y + d7 * d5, d2);
//                    }
//
//                    eBlockX = MathHelper.floor(from.x) - (enumfacing == EnumFacing.EAST ? 1 : 0);
//                    eBlockY = MathHelper.floor(from.y) - (enumfacing == EnumFacing.UP ? 1 : 0);
//                    eBlockZ = MathHelper.floor(from.z) - (enumfacing == EnumFacing.SOUTH ? 1 : 0);
//                    endBlockPos = new BlockPos(eBlockX, eBlockY, eBlockZ);
//                    IBlockState iblockstate1 = world.getBlockState(endBlockPos);
//                    Block block1 = iblockstate1.getBlock();
//
//                    if( iblockstate1.getMaterial() == Material.PORTAL || iblockstate1.getCollisionBoundingBox(world, endBlockPos) != Block.NULL_AABB ) {
//                        if( block1.canCollideCheck(iblockstate1, false) ) {
//                            if( collisionRayTrace(iblockstate1, e, world, endBlockPos, from, to) ) {
//                                return true;
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//        return false;
    }
}
