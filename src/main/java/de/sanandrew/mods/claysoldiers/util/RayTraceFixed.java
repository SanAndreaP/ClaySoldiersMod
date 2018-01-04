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
    private static boolean collisionRayTrace(IBlockState blockState, Entity e, World worldIn, BlockPos pos, Vec3d start, Vec3d end) {
        List<AxisAlignedBB> collisionBBs = new ArrayList<>();
        blockState.addCollisionBoxToList(worldIn, pos, Block.FULL_BLOCK_AABB.offset(pos), collisionBBs, e, false);

        for( AxisAlignedBB aabb : collisionBBs ) {
            if( calcIntercept(aabb.grow(1.0D / 32.0D), start, end) ) {
                return true;
            }
        }

        return false;
    }

    private static boolean calcIntercept(AxisAlignedBB aabb, Vec3d start, Vec3d end) {
        Vec3d delta = end.subtract(start);

        double scaleX = 1.0 / delta.x;
        double scaleY = 1.0 / delta.y;
        double scaleZ = 1.0 / delta.z;
        boolean negX = scaleX < 0.0D;
        boolean negY = scaleY < 0.0D;
        boolean negZ = scaleZ < 0.0D;

        double nearTimeX = ((negX ? aabb.maxX : aabb.minX) - start.x) * scaleX;
        double nearTimeY = ((negY ? aabb.maxY : aabb.minY) - start.y) * scaleY;
        double nearTimeZ = ((negZ ? aabb.maxZ : aabb.minZ) - start.z) * scaleZ;
        double farTimeX = ((negX ? aabb.minX : aabb.maxX) - start.x) * scaleX;
        double farTimeY = ((negY ? aabb.minY : aabb.maxY) - start.y) * scaleY;
        double farTimeZ = ((negZ ? aabb.minZ : aabb.maxZ) - start.z) * scaleZ;

        if( nearTimeX > farTimeY || nearTimeY > farTimeX || nearTimeZ > farTimeY || nearTimeY > farTimeZ ) {
            return false;
        }

        double nearTimeXY = Math.max(nearTimeX, nearTimeY);
        double nearTimeZY = Math.max(nearTimeZ, nearTimeY);
        double farTimeXY = Math.min(farTimeX, farTimeY);
        double farTimeZY = Math.min(farTimeZ, farTimeY);

        if( nearTimeXY >= 1.0 || farTimeXY <= 0.0 || nearTimeZY >= 1.0 || farTimeZY <= 0.0 ) {
            return false;
        }

        return true;
    }

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
            newFrom = newTo;
            for( int x = MathHelper.floor(Math.min(newFrom.x, newTo.x)) - 1, mX = MathHelper.ceil(Math.max(newFrom.x, newTo.x)) + 1; x <= mX; x++ ) {
                for( int y = MathHelper.floor(Math.min(newFrom.y, newTo.y)) - 1, mY = MathHelper.ceil(Math.max(newFrom.y, newTo.y)) + 1; y <= mY; y++ ) {
                    for( int z = MathHelper.floor(Math.min(newFrom.z, newTo.z)) - 1, mZ = MathHelper.ceil(Math.max(newFrom.z, newTo.z)) + 1; z <= mZ; z++ ) {
                        if( !world.isAirBlock(new BlockPos(x, y, z)) ) {
                            blocksToCheck.add(new BlockPos(x, y, z));
                        }
                    }
                }
            }
        } while( (newTo = newFrom.add(dirPart)).lengthSquared() <= dirLength );

        for( BlockPos block : blocksToCheck ) {
            if( collisionRayTrace(world.getBlockState(block), e, world, block, from, to) ) {
                return true;
            }
        }

        return false;
    }
}
