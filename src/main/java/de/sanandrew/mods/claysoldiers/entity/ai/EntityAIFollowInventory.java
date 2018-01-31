/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.ai;

import de.sanandrew.mods.claysoldiers.entity.soldier.EntityClaySoldier;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.Path;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;

public class EntityAIFollowInventory
        extends EntityAIBase
{
    final EntityClaySoldier taskOwner;

    double speedTowardsTarget;
    Path entityPathEntity;

    public EntityAIFollowInventory(EntityClaySoldier soldier, double speedIn) {
        this.taskOwner = soldier;
        this.speedTowardsTarget = speedIn;
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        return this.taskOwner.followingBlock != null && this.isTargetValid();
    }

    @Override
    public void resetTask() {
        this.taskOwner.getNavigator().clearPathEntity();
        this.taskOwner.followingBlock = null;
    }

    public boolean isTargetValid() {
        if( this.taskOwner.followingBlock != null && this.taskOwner.world.isBlockLoaded(this.taskOwner.followingBlock)
                    && this.taskOwner.world.getChunkFromBlockCoords(this.taskOwner.followingBlock).isLoaded() )
        {
            TileEntity te = this.taskOwner.world.getTileEntity(this.taskOwner.followingBlock);
            return te != null && !te.isInvalid() && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
        }

        return false;
    }
}
