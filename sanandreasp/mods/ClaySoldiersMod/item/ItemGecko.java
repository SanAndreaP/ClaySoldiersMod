/*******************************************************************************************************************
* Name: ItemGecko.java
* Author: SanAndreasP
* Copyright: SanAndreasP and SilverChiren
* License: Attribution-NonCommercial-ShareAlike 3.0 Unported (http://creativecommons.org/licenses/by-nc-sa/3.0/)
*******************************************************************************************************************/

package sanandreasp.mods.ClaySoldiersMod.item;
import java.util.List;

import sanandreasp.mods.ClaySoldiersMod.entity.mount.EntityGecko;

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
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class ItemGecko extends Item
{
    public ItemGecko(int i)
    {
        super(i);
		maxStackSize = 16;
		setHasSubtypes(true);
		setMaxDamage(0);
    }

    private Icon scndTexture;

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
	            if (spawnGecko(par3World, par1ItemStack.getItemDamage(), (double)par4 + 0.5D, (double)par5 + var12, (double)par6 + 0.5D) && !par2EntityPlayer.capabilities.isCreativeMode)
	            {
	                --par1ItemStack.stackSize;
	            }
	            --stack;
			}
			
            return true;
        }
    }
    
    public boolean spawnGecko(World par0World, int par1, double par2, double par4, double par6)
    {
    	EntityGecko var8 = new EntityGecko(par0World, par2, par4, par6, par1);

        if (var8 != null)
        {
            var8.setLocationAndAngles(par2, par4, par6, par0World.rand.nextFloat() * 360.0F, 0.0F);  
            par0World.spawnEntityInWorld(var8);
            var8.playLivingSound();
        }

        return var8 != null;
    }
    
    private int[] getTypes(int i) {
    	int j[] = new int[2];
    	if (i > 11 && i < 16) {
    		j[0] = 3;
    		j[1] = i - 12;
    	} else if (i > 7 && i < 12) {
    		j[0] = 2;
    		j[1] = i - 8;
    	} else if (i > 3 && i < 8) {
    		j[0] = 1;
    		j[1] = i - 4;
    	} else {
    		j[0] = 0;
    		j[1] = i;
    	}
    	
    	return j;
    }
	
	private String getTypeName(int i) {
		switch(i) {
			case 1: return "Birch";
			case 2: return "Pine";
			case 3: return "Jung";
			default: return "Oak";
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		String epona = "";
		int i = itemstack.getItemDamage();
		
		epona += getTypeName(getTypes(i)[0]) + "-" + getTypeName(getTypes(i)[1]);
		
		epona += "Gecko";
		
		return epona;
	}

    @Override
	public Icon getIconFromDamageForRenderPass(int i, int j)
    {
        if (j == 0)
        {
            return super.getIconFromDamageForRenderPass(i, j);
        }
        else
        {
            return this.scndTexture;
        }
    }
    
    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }
	
	private int getTypeColorWood(int i) {
		switch(i) {
			case 1: return 0xcfe3ba; //Birch
			case 2: return 0x50361a; //Pine
			case 3: return 0x393d0d; //Jungle
			default: return 0x7f6139; //Oak
		}
	}
	
	private int getTypeColorLeaves(int i) {
		switch(i) {
		case 1: return 0xa2c978; //Birch
		case 2: return 0x395a39; //Pine
		case 3: return 0x378020; //Jungle
		default: return 0x378020; //Oak
		}
	}

	@Override @SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack is, int j) {
		int i = is.getItemDamage();
  		if (j == 0) {
  			return getTypeColorLeaves(getTypes(i)[1]);
  		} else {
  			return getTypeColorWood(getTypes(i)[0]);
  		}
 	}
	
	@Override public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) {
		super.getSubItems(par1, par2CreativeTabs, par3List);
		for (int i = 1; i <= 15; i++) par3List.add(new ItemStack(this, 1, i));
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
        this.itemIcon = par1IconRegister.registerIcon("ClaySoldiersMod:dollGeckoBody");
        this.scndTexture = par1IconRegister.registerIcon("ClaySoldiersMod:dollGeckoSpots");
	}
}