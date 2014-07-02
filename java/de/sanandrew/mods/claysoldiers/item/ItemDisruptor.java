package de.sanandrew.mods.claysoldiers.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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
    @SuppressWarnings("unchecked")
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if( !world.isRemote ) {
            List<IDisruptable> disruptables = world.getEntitiesWithinAABB(IDisruptable.class, AxisAlignedBB.getBoundingBox(
                    player.posX - 16D, player.posY - 16D, player.posZ - 16D,
                    player.posX + 16D, player.posY + 16D, player.posZ + 16D
            ));
            for( IDisruptable disruptable : disruptables ) {
                disruptable.disrupt(player);
            }

            if( !player.capabilities.isCreativeMode ) {
                stack.damageItem(1, player);
            }
        }

        return stack;
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(this.isHard_ ? "claysoldiers:disruptor_cooked" : "claysoldiers:disruptor");
    }
}
