package de.sanandrew.mods.claysoldiers.dispenser;

import de.sanandrew.mods.claysoldiers.client.util.event.java.de.sanandrew.mods.claysoldiers.item.ItemDisruptor;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

/**
 * @author SanAndreas
 * @version 1.0
 */
public class BehaviorDisruptorDispenseItem
    implements IBehaviorDispenseItem
{
    public final ItemStack dispense(IBlockSource blockSource, ItemStack stack) {
        ItemStack dispenseStack = this.dispenseStack(blockSource, stack);
        this.playDispenseSound(blockSource);
        this.spawnDispenseParticles(blockSource, BlockDispenser.func_149937_b(blockSource.getBlockMetadata()));
        return dispenseStack;
    }

    /**
     * Dispense the specified stack, play the dispense sound and spawn particles.
     */
    protected ItemStack dispenseStack(IBlockSource blockSource, ItemStack stack) {
        EnumFacing facing = BlockDispenser.func_149937_b(blockSource.getBlockMetadata());
        IPosition position = BlockDispenser.func_149939_a(blockSource);
        ItemDisruptor.disrupt(stack, blockSource.getWorld(), position.getX(), position.getY(), position.getZ(), null);
        return stack;
    }

    /**
     * Play the dispense sound from the specified block.
     */
    protected void playDispenseSound(IBlockSource blockSource) {
        blockSource.getWorld().playAuxSFX(1000, blockSource.getXInt(), blockSource.getYInt(), blockSource.getZInt(), 0);
    }

    /**
     * Order clients to display dispense particles from the specified block and facing.
     */
    protected void spawnDispenseParticles(IBlockSource blockSource, EnumFacing facing) {
        blockSource.getWorld().playAuxSFX(2000, blockSource.getXInt(), blockSource.getYInt(), blockSource.getZInt(), this.func_82488_a(facing));
    }

    private int func_82488_a(EnumFacing facing) {
        return facing.getFrontOffsetX() + 1 + (facing.getFrontOffsetZ() + 1) * 3;
    }
}
