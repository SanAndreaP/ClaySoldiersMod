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
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public final class RayTraceFixed
{
    @Nullable
    public static RayTraceResult collisionRayTrace(IBlockState blockState, Entity e, World worldIn, BlockPos pos, Vec3d start, Vec3d end) {
        List<AxisAlignedBB> collisionBBs = new ArrayList<>();
        blockState.addCollisionBoxToList(worldIn, pos, blockState.getBoundingBox(worldIn, pos).offset(pos), collisionBBs, e, true);
        for( AxisAlignedBB aabb : collisionBBs ) {
            aabb = aabb.offset(-pos.getX(), -pos.getY(), -pos.getZ());
            start = start.subtract(pos.getX(), pos.getY(), pos.getZ());
            end = end.subtract(pos.getX(), pos.getY(), pos.getZ());
            RayTraceResult res = rayTrace(pos, start, end, aabb.grow(0.1D));
            if( res != null ) {
                return res;
            }
            rayTrace(pos, start, end, aabb.grow(0.1D));
        }
        return null;
    }

    @Nullable
    private static RayTraceResult rayTrace(BlockPos pos, Vec3d start, Vec3d end, AxisAlignedBB boundingBox) {
        RayTraceResult raytraceresult = boundingBox.calculateIntercept(start, end);
        return raytraceresult == null ? null : new RayTraceResult(raytraceresult.hitVec, raytraceresult.sideHit, pos);
    }

//    @Nullable
//    public static RayTraceResult rayTraceSight(Entity e, World world, Vec3d fromVec, Vec3d toVec) {
//        if( !Double.isNaN(fromVec.x) && !Double.isNaN(fromVec.y) && !Double.isNaN(fromVec.z) ) {
//            if( !Double.isNaN(toVec.x) && !Double.isNaN(toVec.y) && !Double.isNaN(toVec.z) ) {
//                int bBlockX = MathHelper.floor(toVec.x);
//                int bBlockY = MathHelper.floor(toVec.y);
//                int bBlockZ = MathHelper.floor(toVec.z);
//                int eBlockX = MathHelper.floor(fromVec.x);
//                int eBlockY = MathHelper.floor(fromVec.y);
//                int eBlockZ = MathHelper.floor(fromVec.z);
//                BlockPos endBlockPos = new BlockPos(eBlockX, eBlockY, eBlockZ);
//                IBlockState endBlockState = world.getBlockState(endBlockPos);
//
//                if( (endBlockState.getCollisionBoundingBox(world, endBlockPos) != Block.NULL_AABB) && endBlockState.getBlock().canCollideCheck(endBlockState, false) ) {
//                    RayTraceResult raytraceresult = collisionRayTrace(endBlockState, e, world, endBlockPos, fromVec, toVec);
//
//                    if( raytraceresult != null ) {
//                        return raytraceresult;
//                    }
//                }
//
//                int k1 = 200;
//
//                while( k1-- >= 0 ) {
//                    if( Double.isNaN(fromVec.x) || Double.isNaN(fromVec.y) || Double.isNaN(fromVec.z) ) {
//                        return null;
//                    }
//
//                    if( eBlockX == bBlockX && eBlockY == bBlockY && eBlockZ == bBlockZ ) {
//                        return null;
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
//                    double d6 = toVec.x - fromVec.x;
//                    double d7 = toVec.y - fromVec.y;
//                    double d8 = toVec.z - fromVec.z;
//
//                    if( flag2 ) {
//                        d3 = (d0 - fromVec.x) / d6;
//                    }
//
//                    if( flag ) {
//                        d4 = (d1 - fromVec.y) / d7;
//                    }
//
//                    if( flag1 ) {
//                        d5 = (d2 - fromVec.z) / d8;
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
//                        fromVec = new Vec3d(d0, fromVec.y + d7 * d3, fromVec.z + d8 * d3);
//                    } else if( d4 < d5 ) {
//                        enumfacing = bBlockY > eBlockY ? EnumFacing.DOWN : EnumFacing.UP;
//                        fromVec = new Vec3d(fromVec.x + d6 * d4, d1, fromVec.z + d8 * d4);
//                    } else {
//                        enumfacing = bBlockZ > eBlockZ ? EnumFacing.NORTH : EnumFacing.SOUTH;
//                        fromVec = new Vec3d(fromVec.x + d6 * d5, fromVec.y + d7 * d5, d2);
//                    }
//
//                    eBlockX = MathHelper.floor(fromVec.x) - (enumfacing == EnumFacing.EAST ? 1 : 0);
//                    eBlockY = MathHelper.floor(fromVec.y) - (enumfacing == EnumFacing.UP ? 1 : 0);
//                    eBlockZ = MathHelper.floor(fromVec.z) - (enumfacing == EnumFacing.SOUTH ? 1 : 0);
//                    endBlockPos = new BlockPos(eBlockX, eBlockY, eBlockZ);
//                    IBlockState iblockstate1 = world.getBlockState(endBlockPos);
//                    Block block1 = iblockstate1.getBlock();
//
//                    if( iblockstate1.getMaterial() == Material.PORTAL || iblockstate1.getCollisionBoundingBox(world, endBlockPos) != Block.NULL_AABB ) {
//                        if( block1.canCollideCheck(iblockstate1, false) ) {
//                            RayTraceResult raytraceresult1 = collisionRayTrace(iblockstate1, e, world, endBlockPos, fromVec, toVec);
//
//                            if( raytraceresult1 != null ) {
//                                return raytraceresult1;
//                            }
//                        }
//                    }
//                }
//
//                return null;
//            } else {
//                return null;
//            }
//        } else {
//            return null;
//        }
//    }
}
