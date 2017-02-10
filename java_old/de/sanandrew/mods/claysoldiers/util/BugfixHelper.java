/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util;

import de.sanandrew.core.manpack.util.SAPReflectionHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BugfixHelper
{
    public static PathEntity getEntityPathToXYZ(World world, Entity entity, int x, int y, int z, float range, boolean allowWoodDoor, boolean blockMovement,
                                                boolean pathInWater, boolean canEntityDrown) {
        world.theProfiler.startSection("pathfind");
        int i = MathHelper.floor_double(entity.posX);
        int i1 = MathHelper.floor_double(entity.posY);
        int j1 = MathHelper.floor_double(entity.posZ);
        int k1 = (int) (range + 8.0F);
        int l1 = i - k1;
        int i2 = i1 - k1;
        int j2 = j1 - k1;
        int k2 = i + k1;
        int l2 = i1 + k1;
        int i3 = j1 + k1;
        ChunkCache chunkcache = new ChunkCache(world, l1, i2, j2, k2, l2, i3, 0);
        PathEntity pathentity = (new FixedPathFinder(chunkcache, allowWoodDoor, blockMovement, pathInWater, canEntityDrown))
                .createEntityPathTo(entity, x, y, z, range);
        world.theProfiler.endSection();
        return pathentity;
    }

    public static PathEntity getPathEntityToEntity(World world, Entity entity, Entity target, float range, boolean allowWoodDoor, boolean blockMovement,
                                                   boolean pathInWater, boolean canEntityDrown) {
        world.theProfiler.startSection("pathfind");
        int i = MathHelper.floor_double(entity.posX);
        int j = MathHelper.floor_double(entity.posY + 1.0D);
        int k = MathHelper.floor_double(entity.posZ);
        int m = (int) (range + 16.0F);
        int n = i - m;
        int o = j - m;
        int p = k - m;
        int q = i + m;
        int r = j + m;
        int t = k + m;
        ChunkCache chunkcache = new ChunkCache(world, n, o, p, q, r, t, 0);
        PathEntity pathentity = (new FixedPathFinder(chunkcache, allowWoodDoor, blockMovement, pathInWater, canEntityDrown))
                .createEntityPathTo(entity, target, range);
        world.theProfiler.endSection();
        return pathentity;
    }

    private static class FixedPathFinder
            extends PathFinder
    {
        private boolean isWoddenDoorAllowed;
        private boolean isMovementBlockAllowed;
        private boolean isPathingInWater;

        public FixedPathFinder(IBlockAccess blockAccess, boolean allowWoodDoor, boolean blockMovement, boolean pathInWater, boolean canEntityDrown) {
            super(blockAccess, allowWoodDoor, blockMovement, pathInWater, canEntityDrown);
            this.isWoddenDoorAllowed = allowWoodDoor;
            this.isMovementBlockAllowed = blockMovement;
        }

        public static int getPathApplicable(Entity entity, int x, int y, int z, PathPoint pathPoint, boolean pathInWater, boolean canMovementBlock,
                                            boolean allowWoodDoor) {
            boolean isTrapdoorOrWater = false;

            for( int i = x; i < x + pathPoint.xCoord; ++i ) {
                for( int j = y; j < y + pathPoint.yCoord; ++j ) {
                    for( int k = z; k < z + pathPoint.zCoord; ++k ) {
                        Block block = entity.worldObj.getBlock(i, j, k);

                        if( block.getMaterial() != Material.air ) {
                            if( block == Blocks.trapdoor ) {
                                isTrapdoorOrWater = true;
                            } else if( block != Blocks.flowing_water && block != Blocks.water ) {
                                if( !allowWoodDoor && block == Blocks.wooden_door ) {
                                    return 0;
                                }
                            } else {
                                if( pathInWater ) {
                                    return -1;
                                }

                                isTrapdoorOrWater = true;
                            }

                            int blockRenderType = block.getRenderType();

                            if( entity.worldObj.getBlock(i, j, k).getRenderType() == 9 ) {
                                int entityBlockX = MathHelper.floor_double(entity.posX);
                                int entityBlockY = MathHelper.floor_double(entity.posY);
                                int entityBlockZ = MathHelper.floor_double(entity.posZ);

                                if( entity.worldObj.getBlock(entityBlockX, entityBlockY, entityBlockZ).getRenderType() != 9
                                        && entity.worldObj.getBlock(entityBlockX, entityBlockY - 1, entityBlockZ).getRenderType() != 9 ) {
                                    return -3;
                                }
                            } else if( !block.getBlocksMovement(entity.worldObj, i, j, k) && (!canMovementBlock || block != Blocks.wooden_door) ) {
                                if( blockRenderType == 11 || block == Blocks.fence_gate || blockRenderType == 32 ) {
                                    return -3;
                                }

                                if( block == Blocks.trapdoor ) {
                                    return -4;
                                }

                                Material material = block.getMaterial();

                                if( material != Material.lava ) {
                                    return block.getCollisionBoundingBoxFromPool(entity.worldObj, i, j, k) != null ? 0 : 1;
                                }

                                if( !entity.handleLavaMovement() ) {
                                    return -2;
                                }
                            }
                        }
                    }
                }
            }

            return isTrapdoorOrWater ? 2 : 1;
        }

        @Override
        public int getVerticalOffset(Entity entity, int x, int y, int z, PathPoint pathPoint) {
            try {
                this.isPathingInWater = SAPReflectionHelper.getCachedField(PathFinder.class, "isPathingInWater", "field_75863_g").getBoolean(this);
                return getPathApplicable(entity, x, y, z, pathPoint, this.isPathingInWater, this.isMovementBlockAllowed,
                                         this.isWoddenDoorAllowed
                );
            } catch( IllegalAccessException e ) {
                e.printStackTrace();
            }

            return 0;
        }
    }
}
