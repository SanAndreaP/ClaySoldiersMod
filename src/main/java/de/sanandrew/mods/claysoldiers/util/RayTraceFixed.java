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
    private static RayTraceResult collisionRayTrace(IBlockState blockState, Entity e, World worldIn, BlockPos pos, Vec3d start, Vec3d end) {
        List<AxisAlignedBB> collisionBBs = new ArrayList<>();
        blockState.addCollisionBoxToList(worldIn, pos, blockState.getBoundingBox(worldIn, pos).offset(pos), collisionBBs, e);
        for( AxisAlignedBB aabb : collisionBBs ) {
            RayTraceResult res = rayTrace(pos, start, end, aabb.offset(-pos.getX(), -pos.getY(), -pos.getZ()));
            if( res != null ) {
                return res;
            }
        }
        return null;
    }

    @Nullable
    private static RayTraceResult rayTrace(BlockPos pos, Vec3d start, Vec3d end, AxisAlignedBB boundingBox)
    {
        Vec3d vec3d = start.subtract(pos.getX(), pos.getY(), pos.getZ());
        Vec3d vec3d1 = end.subtract(pos.getX(), pos.getY(), pos.getZ());
        RayTraceResult raytraceresult = boundingBox.calculateIntercept(vec3d, vec3d1);
        return raytraceresult == null ? null : new RayTraceResult(raytraceresult.hitVec.addVector(pos.getX(), pos.getY(), pos.getZ()), raytraceresult.sideHit, pos);
    }

    @Nullable
    public static RayTraceResult rayTraceSight(Entity e, World world, Vec3d vec31, Vec3d vec32) {
        if( !Double.isNaN(vec31.xCoord) && !Double.isNaN(vec31.yCoord) && !Double.isNaN(vec31.zCoord) ) {
            if( !Double.isNaN(vec32.xCoord) && !Double.isNaN(vec32.yCoord) && !Double.isNaN(vec32.zCoord) ) {
                int i = MathHelper.floor_double(vec32.xCoord);
                int j = MathHelper.floor_double(vec32.yCoord);
                int k = MathHelper.floor_double(vec32.zCoord);
                int l = MathHelper.floor_double(vec31.xCoord);
                int i1 = MathHelper.floor_double(vec31.yCoord);
                int j1 = MathHelper.floor_double(vec31.zCoord);
                BlockPos blockpos = new BlockPos(l, i1, j1);
                IBlockState iblockstate = world.getBlockState(blockpos);
                Block block = iblockstate.getBlock();

                if( (iblockstate.getCollisionBoundingBox(world, blockpos) != Block.NULL_AABB) && block.canCollideCheck(iblockstate, false) ) {
                    RayTraceResult raytraceresult = collisionRayTrace(iblockstate, e, world, blockpos, vec31, vec32);

                    if( raytraceresult != null ) {
                        return raytraceresult;
                    }
                }

                int k1 = 200;

                while( k1-- >= 0 ) {
                    if( Double.isNaN(vec31.xCoord) || Double.isNaN(vec31.yCoord) || Double.isNaN(vec31.zCoord) ) {
                        return null;
                    }

                    if( l == i && i1 == j && j1 == k ) {
                        return null;
                    }

                    boolean flag2 = true;
                    boolean flag = true;
                    boolean flag1 = true;
                    double d0 = 999.0D;
                    double d1 = 999.0D;
                    double d2 = 999.0D;

                    if( i > l ) {
                        d0 = l + 1.0D;
                    } else if( i < l ) {
                        d0 = l + 0.0D;
                    } else {
                        flag2 = false;
                    }

                    if( j > i1 ) {
                        d1 = i1 + 1.0D;
                    } else if( j < i1 ) {
                        d1 = i1 + 0.0D;
                    } else {
                        flag = false;
                    }

                    if( k > j1 ) {
                        d2 = j1 + 1.0D;
                    } else if( k < j1 ) {
                        d2 = j1 + 0.0D;
                    } else {
                        flag1 = false;
                    }

                    double d3 = 999.0D;
                    double d4 = 999.0D;
                    double d5 = 999.0D;
                    double d6 = vec32.xCoord - vec31.xCoord;
                    double d7 = vec32.yCoord - vec31.yCoord;
                    double d8 = vec32.zCoord - vec31.zCoord;

                    if( flag2 ) {
                        d3 = (d0 - vec31.xCoord) / d6;
                    }

                    if( flag ) {
                        d4 = (d1 - vec31.yCoord) / d7;
                    }

                    if( flag1 ) {
                        d5 = (d2 - vec31.zCoord) / d8;
                    }

                    if( d3 == -0.0D ) {
                        d3 = -1.0E-4D;
                    }

                    if( d4 == -0.0D ) {
                        d4 = -1.0E-4D;
                    }

                    if( d5 == -0.0D ) {
                        d5 = -1.0E-4D;
                    }

                    EnumFacing enumfacing;

                    if( d3 < d4 && d3 < d5 ) {
                        enumfacing = i > l ? EnumFacing.WEST : EnumFacing.EAST;
                        vec31 = new Vec3d(d0, vec31.yCoord + d7 * d3, vec31.zCoord + d8 * d3);
                    } else if( d4 < d5 ) {
                        enumfacing = j > i1 ? EnumFacing.DOWN : EnumFacing.UP;
                        vec31 = new Vec3d(vec31.xCoord + d6 * d4, d1, vec31.zCoord + d8 * d4);
                    } else {
                        enumfacing = k > j1 ? EnumFacing.NORTH : EnumFacing.SOUTH;
                        vec31 = new Vec3d(vec31.xCoord + d6 * d5, vec31.yCoord + d7 * d5, d2);
                    }

                    l = MathHelper.floor_double(vec31.xCoord) - (enumfacing == EnumFacing.EAST ? 1 : 0);
                    i1 = MathHelper.floor_double(vec31.yCoord) - (enumfacing == EnumFacing.UP ? 1 : 0);
                    j1 = MathHelper.floor_double(vec31.zCoord) - (enumfacing == EnumFacing.SOUTH ? 1 : 0);
                    blockpos = new BlockPos(l, i1, j1);
                    IBlockState iblockstate1 = world.getBlockState(blockpos);
                    Block block1 = iblockstate1.getBlock();

                    if( iblockstate1.getMaterial() == Material.PORTAL || iblockstate1.getCollisionBoundingBox(world, blockpos) != Block.NULL_AABB ) {
                        if( block1.canCollideCheck(iblockstate1, false) ) {
                            RayTraceResult raytraceresult1 = collisionRayTrace(iblockstate1, e, world, blockpos, vec31, vec32);

                            if( raytraceresult1 != null ) {
                                return raytraceresult1;
                            }
                        }
                    }
                }

                return null;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
