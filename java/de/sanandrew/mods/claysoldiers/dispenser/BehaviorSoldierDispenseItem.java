/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.dispenser;

import de.sanandrew.mods.claysoldiers.item.ItemClayManDoll;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BehaviorSoldierDispenseItem
        implements IBehaviorDispenseItem
{
    @Override
    public final ItemStack dispense(IBlockSource blockSource, ItemStack stack) {
        ItemStack dispenseStack = this.dispenseStack(blockSource, stack);
        this.playDispenseSound(blockSource);
        this.spawnDispenseParticles(blockSource, BlockDispenser.func_149937_b(blockSource.getBlockMetadata()));
        return dispenseStack;
    }

    private static void doDispense(World world, ItemStack stack, EnumFacing facing, IPosition position) {
        double x = position.getX();
        double y = position.getY() - (facing == EnumFacing.UP ? 0.0D : 0.3D);
        double z = position.getZ();
        ItemClayManDoll.spawnClayMan(world, ItemClayManDoll.getTeam(stack).getTeamName(), x, y, z);
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
        blockSource.getWorld().playAuxSFX(2000, blockSource.getXInt(), blockSource.getYInt(), blockSource.getZInt(), this.getParticleOffset(facing));
    }

    private static int getParticleOffset(EnumFacing facing) {
        return facing.getFrontOffsetX() + 1 + (facing.getFrontOffsetZ() + 1) * 3;
    }
}
