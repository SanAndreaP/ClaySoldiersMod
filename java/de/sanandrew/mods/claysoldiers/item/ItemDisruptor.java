package de.sanandrew.mods.claysoldiers.item;

import de.sanandrew.core.manpack.util.SAPUtils;
import de.sanandrew.mods.claysoldiers.util.CSM_Main;
import de.sanandrew.mods.claysoldiers.util.IDisruptable;
import de.sanandrew.mods.claysoldiers.util.RegistryItems;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
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
public class ItemDisruptor
        extends Item
{
    public int cooldown = 10;
    private boolean isHard_ = false;

    public ItemDisruptor(boolean hardened) {
        super();
        this.maxStackSize = 1;
        this.isHard_ = hardened;
        this.setMaxDamage((hardened ? 50 : 10) - 1);
    }

    @SuppressWarnings("unchecked")
    public static void disrupt(ItemStack stack, World world, double x, double y, double z, EntityPlayer player) {
        if( !world.isRemote ) {
            if( player != null && player.capabilities.isCreativeMode && player.isSneaking() ) {
                List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(x - 32.0D, y - 32.0D, z - 32.0D,
                                                                                                                    x + 32.0D, y + 32.0D, z + 32.0D
                                                                     )
                );

                for( EntityItem item : items ) {
                    if( item.getEntityItem() != null && item.getEntityItem().getItem() == RegistryItems.dollSoldier ) {
                        item.setDead();
                    }
                }
            }

            List<IDisruptable> disruptables = world.getEntitiesWithinAABB(IDisruptable.class, AxisAlignedBB.getBoundingBox(x - 32.0D, y - 32.0D, z - 32.0D,
                                                                                                                           x + 32.0D, y + 32.0D, z + 32.0D
                                                                          )
            );

            for( IDisruptable disruptable : disruptables ) {
                disruptable.disrupt();
            }

            if( player != null ) {
                if( !player.capabilities.isCreativeMode ) {
                    stack.damageItem(1, player);
                }
            } else {
                if( stack.attemptDamageItem(1, SAPUtils.RNG) ) {
                    stack.stackSize--;
                }
            }
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        disrupt(stack, world, player.posX, player.posY, player.posZ, player);
        return stack;
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(CSM_Main.MOD_ID + (this.isHard_ ? ":disruptor_cooked" : ":disruptor"));
    }
}
