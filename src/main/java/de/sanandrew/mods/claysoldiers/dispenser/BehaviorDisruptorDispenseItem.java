/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.dispenser;

import de.sanandrew.mods.claysoldiers.item.ItemDisruptor;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;

public class BehaviorDisruptorDispenseItem
        extends BehaviorDefaultDispenseItem
{
    @Override
    protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
        EnumFacing facing = source.getBlockState().getValue(BlockDispenser.FACING);
        double x = source.getX() + facing.getFrontOffsetX() * 1.5D;
        double y = source.getY() + facing.getFrontOffsetY() * 1.5D;
        double z = source.getZ() + facing.getFrontOffsetZ() * 1.5D;

        ItemDisruptor.disruptAction(source.getWorld(), stack, new Vec3d(x, y, z), null);

        return stack;
    }
}
