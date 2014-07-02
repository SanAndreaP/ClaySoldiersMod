/*******************************************************************************************************************
* Name: ItemTurtle.java
* Author: SanAndreasP
* Copyright: SanAndreasP and SilverChiren
* License: Attribution-NonCommercial-ShareAlike 3.0 Unported (http://creativecommons.org/licenses/by-nc-sa/3.0/)
*******************************************************************************************************************/

package sanandreasp.mods.ClaySoldiersMod.item;
import java.util.List;

import sanandreasp.mods.ClaySoldiersMod.entity.mount.EntityTurtle;

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

public class ItemTurtle extends Item
{
	private Icon sndIcon;

    public ItemTurtle(int i)
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
	            if (spawnTurtle(par3World, par1ItemStack.getItemDamage(), (double)par4 + 0.5D, (double)par5 + var12, (double)par6 + 0.5D) && !par2EntityPlayer.capabilities.isCreativeMode)
	            {
	                --par1ItemStack.stackSize;
	            }
	            --stack;
			}
			
            return true;
        }
    }
    
    public boolean spawnTurtle(World par0World, int par1, double par2, double par4, double par6)
    {
    	EntityTurtle var8 = new EntityTurtle(par0World, par2, par4, par6, par1);

        if (var8 != null)
        {
            var8.setLocationAndAngles(par2, par4, par6, par0World.rand.nextFloat() * 360.0F, 0.0F);  
            par0World.spawnEntityInWorld(var8);
            var8.playLivingSound();
        }

        return var8 != null;
    }

    @Override
	public Icon getIconFromDamageForRenderPass(int i, int j)
    {
        if (j == 0)
        {
            return this.itemIcon;
        }
        else
        {
            return this.sndIcon;
        }
    }
    
    @Override
	public boolean requiresMultipleRenderPasses()
    {
        return true;
    }

	@Override
	public String getUnlocalizedName(ItemStack itemstack) { /*CHANGED*/
		switch(itemstack.getItemDamage()) {
			case 0: return "cobbleTurtle";
			case 1: return "mossyTurtle";
			case 2: return "netherackTurtle";
			case 3: return "melonTurtle";
			case 4: return "sandstoneTurtle";
			case 5: return "endstoneTurtle";
			case 6: return "pumpkinTurtle";
			default: return "stoneTurtle";
		}
	}

	@Override @SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack is, int j) {
		int i = is.getItemDamage();
  		if (j == 0) {
  			if (i == 3) {
	   		  return 0xFF3333; //Melon
	  		} else if (i == 6) {
	   		  return 0xffd76c; //Pumpkin
	  		} else if (i == 5) {
	   		  return 0x222222; //Endstone
	  		}
	  		return 0x553322;
  		} else {
  			if (i == 0) {
	   		  return 0x616161; //Cobble
	  		} else if (i == 1) {
	   		  return 0x618261; //Mossy
	  		} else if (i == 2) {
	   		  return 0x932626; //Netherack
	  		} else if (i == 3) {
	   		  return 0x66AA00; //Melon
	  		} else if (i == 4) {
	   		  return 0xBCB171; //Sandstone
	  		} else if (i == 5) {
	   		  return 0xCCC181; //Endstone
	  		} else if (i == 6) {
	  		  return 0xd07c14; //Pumpkin
	  		}
	  		return 0x553322;
  		}
 	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
	    this.itemIcon = par1IconRegister.registerIcon("ClaySoldiersMod:dollTurtle");
	    this.sndIcon = par1IconRegister.registerIcon("ClaySoldiersMod:dollShell");
	}
	
	@Override public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) {
		super.getSubItems(par1, par2CreativeTabs, par3List);
		for (int i = 1; i <= 6; i++) par3List.add(new ItemStack(this, 1, i));
	}
}