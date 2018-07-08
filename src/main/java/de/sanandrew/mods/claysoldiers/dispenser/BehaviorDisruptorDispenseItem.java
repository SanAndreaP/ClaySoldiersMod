/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.dispenser;

import de.sanandrew.mods.claysoldiers.api.doll.IDollType;
import de.sanandrew.mods.claysoldiers.api.doll.ItemDoll;
import de.sanandrew.mods.sanlib.lib.util.ReflectionUtils;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public class BehaviorDisruptorDispenseItem
        extends BehaviorDefaultDispenseItem
{
    @Override
    protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
        EnumFacing facing = source.getBlockState().getValue(BlockDispenser.FACING);
        double x = source.getX() + facing.getFrontOffsetX();
        double y = source.getY() + facing.getFrontOffsetY();
        double z = source.getZ() + facing.getFrontOffsetZ();

        ItemStack itemstack = stack.splitStack(1);

//        ItemDoll<?, T> doll = ReflectionUtils.getCasted(itemstack.getItem());
//        doll.spawnEntities(source.getWorld(), doll.getType(itemstack), 1, x, y, z, itemstack);

        return stack;
    }
}
