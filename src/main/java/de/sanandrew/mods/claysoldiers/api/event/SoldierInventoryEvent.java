/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.api.event;

import de.sanandrew.mods.claysoldiers.api.soldier.ISoldier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

import javax.annotation.Nonnull;

public abstract class SoldierInventoryEvent
        extends EntityEvent
{
    public final ISoldier<?> soldier;
    public final BlockPos tilePos;

    public SoldierInventoryEvent(ISoldier<?> soldier, BlockPos tilePos) {
        super(soldier.getEntity());
        this.soldier = soldier;
        this.tilePos = tilePos;
    }

    @Cancelable
    @HasResult
    public static class ItemValid
            extends SoldierInventoryEvent
    {
        public final ItemStack stack;

        public ItemValid(ISoldier<?> soldier, BlockPos tilePos, @Nonnull ItemStack stack) {
            super(soldier, tilePos);
            this.stack = stack.copy();
        }
    }

    @Cancelable
    public static class Grab
            extends SoldierInventoryEvent
    {
        public final ItemStack stack;

        public Grab(ISoldier<?> soldier, BlockPos tilePos, @Nonnull ItemStack stack) {
            super(soldier, tilePos);
            this.stack = stack;
        }
    }
}
