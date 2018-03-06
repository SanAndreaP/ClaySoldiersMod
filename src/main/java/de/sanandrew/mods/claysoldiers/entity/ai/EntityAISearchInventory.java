/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.ai;

import de.sanandrew.mods.claysoldiers.entity.soldier.EntityClaySoldier;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;

public class EntityAISearchInventory
        extends EntityAIBase
{
    final EntityClaySoldier taskOwner;

    BlockPos block;
    int srcCounter;

    public EntityAISearchInventory(EntityClaySoldier soldier) {
        this.taskOwner = soldier;
        this.setMutexBits(MutexBits.TARGETING);
    }

    @Override
    public boolean shouldExecute() {
        if( this.srcCounter++ % 10 == 0 ) {
            if( EntityAISearchTarget.hasFollowTarget(this.taskOwner) ) {
                return false;
            }

            UpgradesChestHelper.ChestEntry entry = UpgradesChestHelper.findValidInventoryStack(this.taskOwner);
            if( entry != null ) {
                this.block = entry.getPos();
                return true;
            }
        }

        return false;
    }

    @Override
    public void resetTask() {
        this.block = null;
    }

    @Override
    public void updateTask() {
        if( this.block != null ) {
            this.taskOwner.followingBlock = this.block;
        }
    }
}
