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
        blockState.addCollisionBoxToList(worldIn, pos, Block.FULL_BLOCK_AABB.offset(pos).grow(1.0D / 16.0D), collisionBBs, e, false);

        start = start.subtract(pos.getX(), pos.getY(), pos.getZ());
        end = end.subtract(pos.getX(), pos.getY(), pos.getZ());

        for( AxisAlignedBB aabb : collisionBBs ) {
            aabb = aabb.offset(-pos.getX(), -pos.getY(), -pos.getZ()).grow(1.0D / 32.0D);
            if( calcIntercept(aabb, start, end) ) {
                return true;
            }
        }

        for( AxisAlignedBB aabb : collisionBBs ) {
            aabb = aabb.offset(-pos.getX(), -pos.getY(), -pos.getZ());//.grow(1.0D / 32.0D);
            if( calcIntercept(aabb, start, end) ) {
                return true;
            }
        }

        return false;
    }

    private static boolean calcIntercept(AxisAlignedBB aabb, Vec3d start, Vec3d end) {
        Vec3d realStart = (start.x > end.x) ? end : start;
        Vec3d realEnd = (start.x > end.x) ? start : end;
        double tanA = (realEnd.y - realStart.y) / (realEnd.x - realStart.x);
        tanA = Double.isNaN(tanA) ? 0.0 : tanA;
        double yMinSz = tanA * (aabb.minX - realStart.x) + realStart.y;
        double yMaxSz = tanA * (aabb.maxX - realStart.x) + realStart.y;
        if( (yMinSz > aabb.maxY || yMinSz < aabb.minY) && (yMaxSz > aabb.maxY || yMaxSz < aabb.maxY)) {
            return false;
        }

        realStart = (start.x > end.x) ? end : start;
        realEnd = (start.x > end.x) ? start : end;
        tanA = (realEnd.z - realStart.z) / (realEnd.x - realStart.x);
        tanA = Double.isNaN(tanA) ? 0.0 : tanA;
        yMinSz = tanA * (aabb.minX - realStart.x) + realStart.z;
        yMaxSz = tanA * (aabb.maxX - realStart.x) + realStart.z;
        if( (yMinSz > aabb.maxZ || yMinSz < aabb.minZ) && (yMaxSz > aabb.maxZ || yMaxSz < aabb.maxZ)) {
            return false;
        }

        realStart = (start.z > end.z) ? end : start;
        realEnd = (start.z > end.z) ? start : end;
        tanA = (realEnd.y - realStart.y) / (realEnd.z - realStart.z);
        tanA = Double.isNaN(tanA) ? 0.0 : tanA;
        yMinSz = tanA * (aabb.minZ - realStart.z) + realStart.y;
        yMaxSz = tanA * (aabb.maxZ - realStart.z) + realStart.y;

        return (yMinSz <= aabb.maxY && yMinSz >= aabb.minY) || (yMaxSz <= aabb.maxY && yMaxSz >= aabb.maxY);
    }

    @Nullable
    public static boolean rayTraceSight(Entity e, World world, Vec3d from, Vec3d to) {
        Set<BlockPos> blocksToCheck = new HashSet<>();
        Vec3d dirVec = to.subtract(from);
        if( dirVec.lengthSquared() < 0.0001D ) {
            return false;
        }
        Vec3d dirPart = dirVec.scale(Math.min(1.0D, 1.0D / Math.floor(dirVec.lengthVector())));

        Vec3d newFrom = from;
        Vec3d newTo = newFrom.add(dirPart);
        double dirLength = dirVec.lengthSquared();
        do {
            Vec3d min = new Vec3d(Math.min(newFrom.x, newTo.x), Math.min(newFrom.y, newTo.y), Math.min(newFrom.z, newTo.z));
            Vec3d max = new Vec3d(Math.max(newFrom.x, newTo.x), Math.max(newFrom.y, newTo.y), Math.max(newFrom.z, newTo.z));
            BlockPos bPosF = new BlockPos(min);
            BlockPos bPosT = new BlockPos(MathHelper.ceil(max.x), MathHelper.floor(max.y), MathHelper.ceil(max.z));
            newFrom = newTo;
            for( int x = bPosF.getX(), mX = bPosT.getX(); x <= mX; x++ ) {
                for( int y = bPosF.getY(), mY = bPosT.getY(); y <= mY; y++ ) {
                    for( int z = bPosF.getZ(), mZ = bPosT.getZ(); z <= mZ; z++ ) {
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
    }
}
