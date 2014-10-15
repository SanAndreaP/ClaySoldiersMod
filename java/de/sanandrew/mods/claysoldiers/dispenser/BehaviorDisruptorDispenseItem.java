/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.dispenser;

import de.sanandrew.mods.claysoldiers.item.ItemDisruptor;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public class BehaviorDisruptorDispenseItem
        implements IBehaviorDispenseItem
{
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
        IPosition position = BlockDispenser.func_149939_a(blockSource);
        ItemDisruptor.disrupt(stack, blockSource.getWorld(), position.getX(), position.getY(), position.getZ(), null);
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
