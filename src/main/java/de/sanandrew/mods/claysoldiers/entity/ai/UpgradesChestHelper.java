/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.ai;

import de.sanandrew.mods.claysoldiers.api.event.SoldierInventoryEvent;
import de.sanandrew.mods.claysoldiers.entity.soldier.EntityClaySoldier;
import de.sanandrew.mods.claysoldiers.registry.ItemRegistry;
import de.sanandrew.mods.claysoldiers.registry.team.TeamRegistry;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import de.sanandrew.mods.sanlib.lib.Tuple;
import de.sanandrew.mods.sanlib.lib.util.ItemStackUtils;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.List;
import java.util.Objects;

public final class UpgradesChestHelper
{
    public static ChestEntry findValidInventoryStack(EntityClaySoldier soldier) {
        List<EntityItemFrame> frames = soldier.world.getEntitiesWithinAABB(EntityItemFrame.class, EntityAISearchTarget.getTargetableArea(soldier), e -> {
            if( e != null && e.facingDirection != null && e.isEntityAlive() && soldier.canEntityBeSeen(e) ) {
                ItemStack stack = e.getDisplayedItem();
                if( TeamRegistry.INSTANCE.getTeam(stack).getId().equals(soldier.getSoldierTeam().getId()) || ItemStackUtils.isItem(stack, ItemRegistry.DOLL_BRICK_SOLDIER) ) {
                    BlockPos pos = e.getHangingPosition().offset(e.facingDirection.getOpposite());
                    return isBlockTileValid(soldier.world, pos);
                }
            }

            return false;
        });

        for( EntityItemFrame frame : frames ) {
            BlockPos pos = frame.getHangingPosition().offset(Objects.requireNonNull(frame.facingDirection).getOpposite());
            ItemStack stack = getStackFromInv(soldier, soldier.world.getTileEntity(pos));
            if( ItemStackUtils.isValid(stack) ) {
                return new ChestEntry(pos, stack);
            }
        }

        return null;
    }

    public static ItemStack getStackFromInv(EntityClaySoldier soldier, TileEntity tile) {
        if( tile == null ) {
            return ItemStack.EMPTY;
        }

        IItemHandler handler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
        if( handler != null ) {
            for( int i = 0, max = handler.getSlots(); i < max; i++ ) {
                ItemStack stack = handler.getStackInSlot(i);

                SoldierInventoryEvent.ItemValid event = new SoldierInventoryEvent.ItemValid(soldier, tile.getPos(), stack);
                if( !ClaySoldiersMod.EVENT_BUS.post(event) && event.getResult() != Event.Result.DENY ) {
                    if( event.getResult() == Event.Result.ALLOW ) {
                        return stack;
                    }
                } else {
                    return ItemStack.EMPTY;
                }
            }
        }

        return ItemStack.EMPTY;
    }

    public static boolean isBlockTileValid(World world, BlockPos pos) {
        if( pos != null && world.isBlockLoaded(pos) && world.getChunkFromBlockCoords(pos).isLoaded() ) {
            TileEntity te = world.getTileEntity(pos);
            return te != null && !te.isInvalid() && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
        }

        return false;
    }

    public static class ChestEntry
            extends Tuple
    {
        private static final long serialVersionUID = -611710157992948038L;

        ChestEntry(BlockPos pos, ItemStack stack) {
            super(pos, stack);
        }

        public BlockPos getPos() {
            return this.getValue(0);
        }

        public ItemStack getStack() {
            return this.getValue(1);
        }
    }
}
