/*******************************************************************************************************************
* Name: Handler_BehaviorDispenseItem.java
* Author: SanAndreasP
* Copyright: SanAndreasP and SilverChiren
* License: Attribution-NonCommercial-ShareAlike 3.0 Unported (http://creativecommons.org/licenses/by-nc-sa/3.0/)
*******************************************************************************************************************/

package sanandreasp.mods.ClaySoldiersMod.registry;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import sanandreasp.mods.ClaySoldiersMod.entity.EntityClayMan;
import sanandreasp.mods.ClaySoldiersMod.entity.mount.EntityBunny;
import sanandreasp.mods.ClaySoldiersMod.entity.mount.EntityGecko;
import sanandreasp.mods.ClaySoldiersMod.entity.mount.EntityHorse;
import sanandreasp.mods.ClaySoldiersMod.entity.mount.EntityPegasus;
import sanandreasp.mods.ClaySoldiersMod.entity.mount.EntityTurtle;
import sanandreasp.mods.ClaySoldiersMod.item.ItemBunny;
import sanandreasp.mods.ClaySoldiersMod.item.ItemClayMan;
import sanandreasp.mods.ClaySoldiersMod.item.ItemGecko;
import sanandreasp.mods.ClaySoldiersMod.item.ItemHorses;
import sanandreasp.mods.ClaySoldiersMod.item.ItemTurtle;

public class Handler_BehaviorDispenseItem implements IBehaviorDispenseItem {

	@Override
	public final ItemStack dispense(IBlockSource par1IBlockSource, ItemStack par2ItemStack)
    {
        ItemStack var3 = this.dispense2(par1IBlockSource, par2ItemStack);
        this.func_82485_a(par1IBlockSource);
        this.func_82489_a(par1IBlockSource, EnumFacing.getFront(par1IBlockSource.getBlockMetadata()));
        return var3;
    }

    protected ItemStack dispense2(IBlockSource par1IBlockSource, ItemStack par2ItemStack)
    {
        EnumFacing var3 = EnumFacing.getFront(par1IBlockSource.getBlockMetadata());
        IPosition var4 = BlockDispenser.getIPositionFromBlockSource(par1IBlockSource);
        ItemStack var5 = par2ItemStack.splitStack(1);
        func_82486_a(par1IBlockSource.getWorld(), var5, 6, var3, var4);
        return par2ItemStack;
    }

    public static void func_82486_a(World par0World, ItemStack par1ItemStack, int par2, EnumFacing par3EnumFacing, IPosition par4IPosition)
    {
    	double var5 = par4IPosition.getX() + par3EnumFacing.getFrontOffsetX() * 0.5F;
        double var7 = par4IPosition.getY();
        double var9 = par4IPosition.getZ() + par3EnumFacing.getFrontOffsetZ() * 0.5F;
        
        EntityLiving var11 = getLivingFromItem(par0World, var5, var7 - 0.3D, var9, par1ItemStack);
        par0World.spawnEntityInWorld(var11);
    }
    
    private static EntityLiving getLivingFromItem(World world, double x, double y, double z, ItemStack item) {
    	if (item.getItem() instanceof ItemClayMan) {
			EntityClayMan entityclayman = new EntityClayMan(world, x, y, z, item.getItemDamage());
			world.playSoundEffect(x, y, z, "step.gravel", 1.0F, 1.0F / 0.4F + 0.8F);
			return entityclayman;
		} else if (item.getItem() instanceof ItemHorses) {
			EntityHorse entityhorse = new EntityHorse(world, x, y, z, item.getItemDamage());
				if (item.getItem() == CSMModRegistry.pegasusDoll) {
					entityhorse = new EntityPegasus(world, x, y, z, item.getItemDamage());
				}
			world.playSoundEffect(x, y, z, "step.gravel", 1.0F, 1.0F / 0.4F + 1.2F);
			return entityhorse;
		} else if (item.getItem() instanceof ItemTurtle) {
			EntityTurtle entityturtle = new EntityTurtle(world, x, y, z, item.getItemDamage());
			world.playSoundEffect(x, y, z, "step.stone", 1.0F, 1.0F / 0.4F + 0.8F);
			return entityturtle;
		} else if (item.getItem() instanceof ItemBunny) {
			EntityBunny entitybunny = new EntityBunny(world, x, y, z, item.getItemDamage());
			world.playSoundEffect(x, y, z, "step.stone", 1.0F, 1.0F / 0.4F + 0.8F);
			return entitybunny;
		} else if (item.getItem() instanceof ItemGecko) {
			EntityGecko entitygecko = new EntityGecko(world, x, y, z, item.getItemDamage());
			world.playSoundEffect(x, y, z, "step.gravel", 1.0F, 1.0F / 0.4F + 1.2F);
			return entitygecko;
		}
    	return null;
    }

    protected void func_82485_a(IBlockSource par1IBlockSource)
    {
    }

    protected void func_82489_a(IBlockSource par1IBlockSource, EnumFacing par2EnumFacing)
    {
    }

    private int func_82488_a(EnumFacing par1EnumFacing)
    {
        return par1EnumFacing.getFrontOffsetX() + 1 + (par1EnumFacing.getFrontOffsetZ() + 1) * 3;
    }

}
