/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import de.sanandrew.mods.claysoldiers.util.CsmPlayerProperties;
import de.sanandrew.mods.claysoldiers.util.IDisruptable;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

public class ItemDisruptor
        extends Item
{
    private final boolean p_isHard;

    public ItemDisruptor(boolean hardened) {
        super();
        this.maxStackSize = 1;
        this.p_isHard = hardened;
        this.setMaxDamage((hardened ? 50 : 10) - 1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        disrupt(stack, world, player.posX, player.posY, player.posZ, player);
        return stack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(ClaySoldiersMod.MOD_ID + (this.p_isHard ? ":disruptor_cooked" : ":disruptor"));
    }

    @SuppressWarnings("unchecked")
    public static void disrupt(ItemStack stack, World world, double x, double y, double z, EntityPlayer player) {
        if( !world.isRemote ) {
            if( player != null && player.capabilities.isCreativeMode && player.isSneaking() ) {
                List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, getRangedBB(x, y, z, 32.0D));

                for( EntityItem item : items ) {
                    if( item.getEntityItem() != null && item.getEntityItem().getItem() instanceof IDisruptable ) {
                        item.setDead();
                    }
                }
            }

            if( player != null ) {
                CsmPlayerProperties prop = CsmPlayerProperties.get(player);
                if( prop != null && prop.canDisruptorBeUsed() ) {
                    prop.setDisruptorFired();
                } else {
                    return;
                }
            }

            List<IDisruptable> disruptables = world.getEntitiesWithinAABB(IDisruptable.class, getRangedBB(x, y, z, 32.0D));

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

    private static AxisAlignedBB getRangedBB(double x, double y, double z, double range) {
        return AxisAlignedBB.getBoundingBox(x - range, y - range, z - range, x + range, y + range, z + range);
    }
}
