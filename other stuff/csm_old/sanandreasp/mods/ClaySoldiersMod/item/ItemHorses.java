/*******************************************************************************************************************
* Name: ItemHorses.java
* Author: SanAndreasP
* Copyright: SanAndreasP and SilverChiren
* License: Attribution-NonCommercial-ShareAlike 3.0 Unported (http://creativecommons.org/licenses/by-nc-sa/3.0/)
*******************************************************************************************************************/

package sanandreasp.mods.ClaySoldiersMod.item;
import java.util.List;

import sanandreasp.mods.ClaySoldiersMod.entity.mount.EntityHorse;
import sanandreasp.mods.ClaySoldiersMod.entity.mount.EntityPegasus;

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

public class ItemHorses extends Item {
	
	private Icon sndIcon;

    public ItemHorses(int i, int j)
    {
        super(i);
		maxStackSize = 16;
		setHasSubtypes(true);
		setMaxDamage(0);
		horseType = j;
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
	            if (spawnHorse(par3World, par1ItemStack.getItemDamage(), (double)par4 + 0.5D, (double)par5 + var12, (double)par6 + 0.5D) && !par2EntityPlayer.capabilities.isCreativeMode)
	            {
	                --par1ItemStack.stackSize;
	            }
	            --stack;
			}
			
            return true;
        }
    }
    
    public boolean spawnHorse(World par0World, int par1, double par2, double par4, double par6)
    {
    	EntityHorse var8 = new EntityHorse(par0World, par2, par4, par6, par1);
    	if (horseType == 1)
    		var8 = new EntityPegasus(par0World, par2, par4, par6, par1);

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
        if (j == 0 || horseType != 1)
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
	public String getUnlocalizedName(ItemStack itemstack) {
		if (horseType == 0)
			switch(itemstack.getItemDamage()) {
				case 0: return "dirtHorse";
				case 1: return "sandHorse";
				case 2: return "gravelHorse";
				case 3: return "snowHorse";
				case 4: return "grassHorse";
				case 5: return "lapisHorse";
				case 6: return "clayHorse";
				case 7: return "carrotHorse";
				default: return "dirtHorse";
			}
		else if (horseType == 1)
			switch(itemstack.getItemDamage()) {
				case 0: return "dirtPegasus";
				case 1: return "sandPegasus";
				case 2: return "gravelPegasus";
				case 3: return "snowPegasus";
				case 4: return "grassPegasus";
				case 5: return "lapisPegasus";
				case 6: return "clayPegasus";
				case 7: return "carrotPegasus";
				default: return "dirtPegasus";
			}
		else return "dirtHorse";
	}

	@Override @SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack is, int j) {
		int i = is.getItemDamage();
        if (j > 0 && horseType == 1)
        {
            return 0xffffff;
        }
        else
        {
	  		if (i == 0) {
	   		  return 0x553322; //dirt
	  		} else if (i == 1) {
	   		  return 0xBCB171; //sand
	  		} else if (i == 2) {
	   		  return 0x616161; //gravel
	  		} else if (i == 3) {
	   		  return 0xFFFFFF; //snow
	  		} else if (i == 4) {
	   		  return 0x309630; //grass
	  		} else if (i == 5) {
	   		  return 0x3458A4; //lapis
	  		} else if (i == 6) {
	   		  return 0x808080; //clay
	  		} else if (i == 7) {
	   		  return 0xFF7700; //carrot
	  		}
	  		return 0x553322;
        }
 	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
	    this.itemIcon = par1IconRegister.registerIcon("ClaySoldiersMod:dollHorse");
	    this.sndIcon = par1IconRegister.registerIcon("ClaySoldiersMod:wing");
	}
	
	@Override public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) {
		super.getSubItems(par1, par2CreativeTabs, par3List);
		for (int i = 1; i <= 7; i++) par3List.add(new ItemStack(this, 1, i));
	}
	
	public int horseType;
}