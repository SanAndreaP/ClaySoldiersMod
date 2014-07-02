/*******************************************************************************************************************
* Name: ItemBunny.java
* Author: SanAndreasP
* Copyright: SanAndreasP and SilverChiren
* License: Attribution-NonCommercial-ShareAlike 3.0 Unported (http://creativecommons.org/licenses/by-nc-sa/3.0/)
*******************************************************************************************************************/

package sanandreasp.mods.ClaySoldiersMod.item;
import java.util.List;

import sanandreasp.mods.ClaySoldiersMod.entity.mount.EntityBunny;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.*;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

public class ItemBunny extends Item
{

    public ItemBunny(int i)
    {
        super(i);
		maxStackSize = 16;
		setHasSubtypes(true);
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
	            if (spawnBunny(par3World, par1ItemStack.getItemDamage(), (double)par4 + 0.5D, (double)par5 + var12, (double)par6 + 0.5D) && !par2EntityPlayer.capabilities.isCreativeMode)
	            {
	                --par1ItemStack.stackSize;
	            }
	            --stack;
			}
			
            return true;
        }
    }
    
    public boolean spawnBunny(World par0World, int par1, double par2, double par4, double par6)
    {
    	EntityBunny var8 = new EntityBunny(par0World, par2, par4, par6, par1);

        if (var8 != null)
        {
            var8.setLocationAndAngles(par2, par4, par6, par0World.rand.nextFloat() * 360.0F, 0.0F);  
            par0World.spawnEntityInWorld(var8);
            var8.playLivingSound();
        }

        return var8 != null;
    }

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		String epona = "";
		int i = itemstack.getItemDamage();
		
		if (i == 1) {
			epona = epona + "orange";
		} else if (i == 2) {
			epona = epona + "magenta";
		} else if (i == 3) {
			epona = epona + "lightBlue";
		} else if (i == 4) {
			epona = epona + "yellow";
		} else if (i == 5) {
			epona = epona + "lime";
		} else if (i == 6) {
			epona = epona + "pink";
		} else if (i == 7) {
			epona = epona + "grey";
		} else if (i == 8) {
			epona = epona + "lightGrey";
		} else if (i == 9) {
			epona = epona + "cyan";
		} else if (i == 10) {
			epona = epona + "purple";
		} else if (i == 11) {
			epona = epona + "blue";
		} else if (i == 12) {
			epona = epona + "brown";
		} else if (i == 13) {
			epona = epona + "green";
		} else if (i == 14) {
			epona = epona + "red";
		} else if (i == 15) {
			epona = epona + "black";
		} else {
			epona = epona + "white";
		}
		
		epona += "Bunny";
		
		return epona;
	}
	
	@Override @SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack is, int j) {
		int i = is.getItemDamage();
		if (i == 1) {
			return 0xE8A033; //orange
		} else if (i == 2) {
			return 0xFF00FF; //magenta
		} else if (i == 3) {
			return 0x8080FF; //lightblue
		} else if (i == 4) {
			return 0xD2D228; //yellow
		} else if (i == 5) {
			return 0x28FF28; //lime
		} else if (i == 6) {
			return 0xF16878; //pink
		} else if (i == 7) {
			return 0x808080; //grey
		} else if (i == 8) {
			return 0xC0C0C0; //lightgrey
		} else if (i == 9) {
			return 0x00FFFF; //cyan
		} else if (i == 10) {
			return 0x9044AA; //purple
		} else if (i == 11) {
			return 0x3458A4; //blue
		} else if (i == 12) {
			return 0x553322; //brown
		} else if (i == 13) {
			return 0x309630; //green
		} else if (i == 14) {
			return 0xB24444; //red
		} else if (i == 15) {
			return 0x282828; //black
		} else {
			return 0xFFFFFF; //white
		}			
 	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
	    this.itemIcon = par1IconRegister.registerIcon("ClaySoldiersMod:dollBunny");
	}
	
	@Override public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) {
		super.getSubItems(par1, par2CreativeTabs, par3List);
		for (int i = 1; i <= 15; i++) par3List.add(new ItemStack(this, 1, i));
	}
}