/*******************************************************************************************************************
* Name: ItemNexus.java
* Author: SanAndreasP
* Copyright: SanAndreasP and SilverChiren
* License: Attribution-NonCommercial-ShareAlike 3.0 Unported (http://creativecommons.org/licenses/by-nc-sa/3.0/)
*******************************************************************************************************************/

package sanandreasp.mods.ClaySoldiersMod.item;
import sanandreasp.mods.ClaySoldiersMod.entity.EntityClayNexus;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.*;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

public class ItemNexus extends Item
{

    public ItemNexus(int i)
    {
        super(i);
		maxStackSize = 4;
		setHasSubtypes(false);
		setMaxDamage(0);
    }
    
    @Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
    	if (par3World.isRemote)
        {
            return true;
        }
        else
        {
            int var11 = par3World.getBlockId(par4, par5, par6);
            par4 += Facing.offsetsXForSide[par7];
            par5 += Facing.offsetsYForSide[par7];
            par6 += Facing.offsetsZForSide[par7];
            double var12 = 0.0D;

            if (par7 == 1 && var11 == Block.fence.blockID || var11 == Block.netherFence.blockID)
            {
                var12 = 0.5D;
            }
            
            int stack = par1ItemStack.stackSize;

			while(par1ItemStack.stackSize > 0 && stack > 0) {
	            if (spawnNexus(par3World, par1ItemStack.getItemDamage(), (double)par4 + 0.5D, (double)par5 + var12, (double)par6 + 0.5D) && !par2EntityPlayer.capabilities.isCreativeMode)
	            {
	                --par1ItemStack.stackSize;
	            }
	            --stack;
			}
			
            return true;
        }
    }
    
    public boolean spawnNexus(World par0World, int par1, double par2, double par4, double par6)
    {
    	EntityClayNexus var8 = new EntityClayNexus(par0World, par4, par6, par1);

        if (var8 != null)
        {
            var8.setLocationAndAngles(par2, par4, par6, par0World.rand.nextFloat() * 360.0F, 0.0F);  
            par0World.spawnEntityInWorld(var8);
            var8.playLivingSound();
        }

        return var8 != null;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
    	this.itemIcon = par1IconRegister.registerIcon("ClaySoldiersMod:dollNexus");
    }
}