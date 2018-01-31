/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.ai;

import de.sanandrew.mods.claysoldiers.entity.soldier.EntityClaySoldier;
import de.sanandrew.mods.claysoldiers.registry.ItemRegistry;
import de.sanandrew.mods.claysoldiers.registry.team.TeamRegistry;
import de.sanandrew.mods.sanlib.lib.util.ItemStackUtils;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.List;

public class EntityAISearchInventory
        extends EntityAIBase
{
    final EntityClaySoldier taskOwner;

    BlockPos block;

    public EntityAISearchInventory(EntityClaySoldier soldier) {
        this.taskOwner = soldier;
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        if( EntityAISearchTarget.hasFollowTarget(this.taskOwner) ) {
            return false;
        }

        List<EntityItemFrame> frames = this.taskOwner.world.getEntitiesWithinAABB(EntityItemFrame.class, EntityAISearchTarget.getTargetableArea(this.taskOwner), e -> {
            if( e != null && e.isEntityAlive() && this.taskOwner.canEntityBeSeen(e) ) {
                ItemStack itm = e.getDisplayedItem();
                if( TeamRegistry.INSTANCE.getTeam(itm).getId().equals(this.taskOwner.getSoldierTeam().getId()) || ItemStackUtils.isItem(itm, ItemRegistry.DOLL_BRICK_SOLDIER) ) {
                    BlockPos surfaceBlock = e.getHangingPosition().offset(e.facingDirection.getOpposite());
                    if( this.taskOwner.world.isBlockLoaded(surfaceBlock) && this.taskOwner.world.getChunkFromBlockCoords(surfaceBlock).isLoaded() ) {
                        TileEntity te = this.taskOwner.world.getTileEntity(surfaceBlock);
                        if( te != null && !te.isInvalid() && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN) ) {
                            return true;
                        }
                    }
                }
            }

            return false;
        });

        if( !frames.isEmpty() ) {
            EntityItemFrame frame = frames.get(MiscUtils.RNG.randomInt(frames.size()));
            this.block = frame.getHangingPosition().offset(frame.facingDirection.getOpposite());
            return true;
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
