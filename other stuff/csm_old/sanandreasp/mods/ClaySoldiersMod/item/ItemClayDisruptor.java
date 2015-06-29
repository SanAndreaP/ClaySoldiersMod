/*******************************************************************************************************************
* Name: ItemClayDisruptor.java
* Author: SanAndreasP
* Copyright: SanAndreasP and SilverChiren
* License: Attribution-NonCommercial-ShareAlike 3.0 Unported (http://creativecommons.org/licenses/by-nc-sa/3.0/)
*******************************************************************************************************************/

package sanandreasp.mods.ClaySoldiersMod.item;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.*;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

import sanandreasp.mods.ClaySoldiersMod.entity.EntityClayMan;
import sanandreasp.mods.ClaySoldiersMod.entity.mount.EntityHorse;
import sanandreasp.mods.ClaySoldiersMod.entity.mount.EntityTurtle;
import sanandreasp.mods.ClaySoldiersMod.entity.mount.IMount;
import sanandreasp.mods.ClaySoldiersMod.registry.CSMModRegistry;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemClayDisruptor extends Item
{

    public ItemClayDisruptor(int i)
    {
        super(i);
        setMaxDamage(32);
        setMaxStackSize(1);
    }

    @Override
	public boolean shouldRotateAroundWhenRendering()
    {
        return true;
    }

    @Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
        if (CSMModRegistry.getWaveTime(entityplayer) <= 0)
        {
	        itemstack.damageItem(1, entityplayer);
	        if (!world.isRemote || (FMLCommonHandler.instance().getSide().isClient() && FMLCommonHandler.instance().getMinecraftServerInstance() == null))
	        	CSMModRegistry.setWaveTime(entityplayer, 100);
        	if (world.isRemote) {
				((EntityClientPlayerMP)entityplayer).timeInPortal = 1.5F;
				((EntityClientPlayerMP)entityplayer).prevTimeInPortal = 1.5F;
        	} else {
    			world.playSoundAtEntity(entityplayer, "portal.trigger", 1.5F, (itemRand.nextFloat() * 0.2F) + 1.4F);
        	}
        	
			killSoldiers(world, entityplayer);
			
			int x = MathHelper.floor_double(entityplayer.posX);
			int y = MathHelper.floor_double(entityplayer.boundingBox.minY);
			int z = MathHelper.floor_double(entityplayer.posZ);
		
			for (int i = -12; i < 13; i++) {
				for (int j = -12; j < 13; j++) {
					for (int k = -12; k < 13; k++) {
						if (j + y <= 0 || j  + y >= 127) {
							continue;
						}
						
						if (world.getBlockId(x + i, y + j, z + k) != Block.blockClay.blockID) {
							continue;
						}
						
						double a = i;
						double b = j;
						double c = k;
						if (Math.sqrt((a*a) + (b*b) + (c*c)) <= 12D) {
							blockCrush(world, x + i, y + j, z + k);
						}
					}
				}
			}
			
			for (int i = 0; i < 128; i++) {
				double angle = i / 3D;
				double distance = 0.5D + (i / 6D);
				double d = Math.sin(angle) * 0.25D;
				double f = Math.cos(angle) * 0.25D;
				double a = entityplayer.posX + (d * distance);
				double b = entityplayer.boundingBox.minY + 0.5D;
				double c = entityplayer.posZ + (f * distance);
				world.spawnParticle("portal", a, b, c, d, 0.0D, f);
			}
        }
        return itemstack;
    }
    
    private void killSoldiers(World world, EntityPlayer player) {    	
    	List list1 = world.getEntitiesWithinAABBExcludingEntity(player, player.boundingBox.expand(16D, 16D, 16D));
    	
        for (int k = 0; k < list1.size(); k++) {
            Entity entity1 = (Entity)list1.get(k);
			if (entity1 instanceof EntityClayMan) {
				entity1.attackEntityFrom(DamageSource.causePlayerDamage(player), 500);
			} else if (entity1 instanceof EntityHorse) {
				entity1.attackEntityFrom(DamageSource.causePlayerDamage(player), 500);
			} else if (entity1 instanceof EntityTurtle || entity1 instanceof IMount) {
				entity1.attackEntityFrom(DamageSource.causePlayerDamage(player), 500);
			}
		}
    }
	
	public void blockCrush(World worldObj, int x, int y, int z) {
		int a = worldObj.getBlockId(x, y, z);
		int b = worldObj.getBlockMetadata(x, y, z);
		if (a == 0) {
			return;
		}
		if (worldObj.isRemote && worldObj instanceof WorldClient)
			addBlockDestroyEffects(worldObj, x, y, z, a, b);
		Block.blocksList[a].breakBlock(worldObj, x, y, z, 0, 0);
		Block.blocksList[a].dropBlockAsItem(worldObj, x, y, z, b, 0);
		worldObj.setBlock(x, y, z, 0);
	}

    public void addBlockDestroyEffects(World worldObj, int par1, int par2, int par3, int par4, int par5)
    {
    	Random rand = new Random();
        if (par4 != 0)
        {
            Block var6 = Block.blocksList[par4];
            byte var7 = 4;

            for (int var8 = 0; var8 < var7; ++var8)
            {
                for (int var9 = 0; var9 < var7; ++var9)
                {
                    for (int var10 = 0; var10 < var7; ++var10)
                    {
                        double var11 = (double)par1 + ((double)var8 + 0.5D) / (double)var7;
                        double var13 = (double)par2 + ((double)var9 + 0.5D) / (double)var7;
                        double var15 = (double)par3 + ((double)var10 + 0.5D) / (double)var7;
                        int var17 = rand.nextInt(6);
                        CSMModRegistry.proxy.spawnBlockParticles(0, new Object[] {var11, var13, var15, (double)par1, (double)par2, (double)par3, var6, par5});
                    }
                }
            }
        }
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
	    this.itemIcon = par1IconRegister.registerIcon("ClaySoldiersMod:claydisruptor");
	}
}
