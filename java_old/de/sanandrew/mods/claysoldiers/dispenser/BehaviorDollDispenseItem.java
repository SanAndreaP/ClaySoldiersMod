/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.dispenser;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.entity.mount.EntityBunnyMount;
import de.sanandrew.mods.claysoldiers.entity.mount.EntityGeckoMount;
import de.sanandrew.mods.claysoldiers.entity.mount.EntityHorseMount;
import de.sanandrew.mods.claysoldiers.entity.mount.EntityTurtleMount;
import de.sanandrew.mods.claysoldiers.item.*;
import de.sanandrew.mods.claysoldiers.util.RegistryItems;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BehaviorDollDispenseItem
        implements IBehaviorDispenseItem
{
    private static void doDispense(World world, ItemStack stack, EnumFacing facing, IPosition position) {
        double x = position.getX();
        double y = position.getY() - (facing == EnumFacing.UP ? 0.0D : 0.3D);
        double z = position.getZ();

        if( stack.getItem() == RegistryItems.dollSoldier ) {
            EntityClayMan spencer = ItemClayManDoll.spawnClayMan(world, ItemClayManDoll.getTeam(stack).getTeamName(), x, y, z);
            spencer.dollItem = stack;
        } else if( stack.getItem() == RegistryItems.dollHorseMount ) {
            EntityHorseMount spencer = ItemHorseDoll.spawnHorse(world, ItemHorseDoll.getType(stack), ItemHorseDoll.isPegasus(stack), x, y, z);
            spencer.dollItem = stack;
        } else if( stack.getItem() == RegistryItems.dollTurtleMount ) {
            EntityTurtleMount spencer = ItemTurtleDoll.spawnTurtle(world, ItemTurtleDoll.getType(stack), x, y, z);
            spencer.dollItem = stack;
        } else if( stack.getItem() == RegistryItems.dollGeckoMount ) {
            EntityGeckoMount spencer = ItemGeckoDoll.spawnGecko(world, ItemGeckoDoll.getType(stack), x, y, z);
            spencer.dollItem = stack;
        } else if( stack.getItem() == RegistryItems.dollBunnyMount ) {
            EntityBunnyMount spencer = ItemBunnyDoll.spawnBunny(world, ItemBunnyDoll.getType(stack), x, y, z);
            spencer.dollItem = stack;
        }
    }

    @Override
    public final ItemStack dispense(IBlockSource blockSource, ItemStack stack) {
        ItemStack dispenseStack = this.dispenseStack(blockSource, stack);
        this.playDispenseSound(blockSource);
        this.spawnDispenseParticles(blockSource, BlockDispenser.func_149937_b(blockSource.getBlockMetadata()));
        return dispenseStack;
    }

    /**
     * Dispense the specified stack, play the dispense sound and spawn particles.
     */
    ItemStack dispenseStack(IBlockSource blockSource, ItemStack stack) {
        EnumFacing facing = BlockDispenser.func_149937_b(blockSource.getBlockMetadata());
        IPosition position = BlockDispenser.func_149939_a(blockSource);
        ItemStack splitStack = stack.splitStack(1);
        doDispense(blockSource.getWorld(), splitStack, facing, position);
        return stack;
    }

    /**
     * Play the dispense sound from the specified block.
     */
    void playDispenseSound(IBlockSource blockSource) {
        blockSource.getWorld().playAuxSFX(1000, blockSource.getXInt(), blockSource.getYInt(), blockSource.getZInt(), 0);
    }

    /**
     * Order clients to display dispense particles from the specified block and facing.
     */
    void spawnDispenseParticles(IBlockSource blockSource, EnumFacing facing) {
        blockSource.getWorld().playAuxSFX(2000, blockSource.getXInt(), blockSource.getYInt(), blockSource.getZInt(), getParticleOffset(facing));
    }

    private static int getParticleOffset(EnumFacing facing) {
        return facing.getFrontOffsetX() + 1 + (facing.getFrontOffsetZ() + 1) * 3;
    }
}
