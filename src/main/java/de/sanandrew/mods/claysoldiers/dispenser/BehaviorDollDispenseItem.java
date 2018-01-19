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
import net.minecraft.dispenser.IPosition;
import net.minecraft.item.ItemStack;

public class BehaviorDollDispenseItem<T extends IDollType>
        extends BehaviorDefaultDispenseItem
{
    @Override
    protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
        IPosition iposition = BlockDispenser.getDispensePosition(source);
        ItemStack itemstack = stack.splitStack(1);

        ItemDoll<?, T> doll = ReflectionUtils.getCasted(itemstack.getItem());
        doll.spawnEntities(source.getWorld(), doll.getType(itemstack), 1, iposition.getX(), iposition.getY(), iposition.getZ(), itemstack);

        return stack;
    }
}
