/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.WalkNodeProcessor;
import net.minecraft.world.IBlockAccess;

public class WalkNodeProcessorSoldier
        extends WalkNodeProcessor
{
    @Override
    public PathNodeType getPathNodeType(IBlockAccess blockaccessIn, int x, int y, int z) {
        PathNodeType type = super.getPathNodeType(blockaccessIn, x, y, z);
        return type == PathNodeType.WATER ? PathNodeType.OPEN : type;
    }

    @Override
    public PathNodeType getPathNodeType(IBlockAccess blockaccessIn, int x, int y, int z, EntityLiving entitylivingIn, int xSize, int ySize, int zSize, boolean canBreakDoorsIn, boolean canEnterDoorsIn) {
        PathNodeType type = super.getPathNodeType(blockaccessIn, x, y, z, entitylivingIn, xSize, ySize, zSize, canBreakDoorsIn, canEnterDoorsIn);
        return type == PathNodeType.WATER ? PathNodeType.OPEN : type;
    }
}
