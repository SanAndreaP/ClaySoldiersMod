package de.sanandrew.mods.claysoldiers.client.util.event.java.de.sanandrew.mods.claysoldiers.item;

import de.sanandrew.core.manpack.util.SAPUtils;
import de.sanandrew.mods.claysoldiers.util.IDisruptable;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

/**
 * @author SanAndreas
 * @version 1.0
 */
public class ItemDisruptor extends Item
{
    public int cooldown = 10;
    private boolean isHard_ = false;

    public ItemDisruptor(boolean hardened) {
        super();
        this.maxStackSize = 1;
        this.isHard_ = hardened;
        this.setMaxDamage(hardened ? 50 : 10);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        disrupt(stack, world, player.posX, player.posY, player.posZ, player);
        return stack;
    }

    @SuppressWarnings("unchecked")
    public static void disrupt(ItemStack stack, World world, double x, double y, double z, EntityPlayer player) {
        if( !world.isRemote ) {
            List<IDisruptable> disruptables = world.getEntitiesWithinAABB(IDisruptable.class, AxisAlignedBB.getBoundingBox(
                                                                                  x - 16D, y - 16D, z - 16D,
                                                                                  x + 16D, y + 16D, z + 16D
                                                                          ));
            for( IDisruptable disruptable : disruptables ) {
                disruptable.disrupt();
            }

            if( player != null ) {
                if( !player.capabilities.isCreativeMode ) {
                    stack.damageItem(1, player);
                }
            } else {
                if( stack.attemptDamageItem(1, SAPUtils.RANDOM) ) {
                    stack.stackSize--;
                }
            }
        }
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(this.isHard_ ? "claysoldiers:disruptor_cooked" : "claysoldiers:disruptor");
    }
}
