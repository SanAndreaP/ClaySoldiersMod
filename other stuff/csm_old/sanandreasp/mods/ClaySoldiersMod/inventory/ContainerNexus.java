/*******************************************************************************************************************
* Name: ContainerNexus.java
* Author: SanAndreasP
* Copyright: SanAndreasP and SilverChiren
* License: Attribution-NonCommercial-ShareAlike 3.0 Unported (http://creativecommons.org/licenses/by-nc-sa/3.0/)
*******************************************************************************************************************/

package sanandreasp.mods.ClaySoldiersMod.inventory;

import sanandreasp.mods.ClaySoldiersMod.entity.EntityClayNexus;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.src.*;

public class ContainerNexus extends Container {
	public EntityClayNexus nexus;
	public InventoryPlayer playerInv;

	public ContainerNexus(InventoryPlayer inventoryplayer, EntityClayNexus par2Nexus, boolean active) {
		nexus = par2Nexus;
		playerInv = inventoryplayer;
		
		if (active) {
			addSlotToContainer(new Slot(nexus.nexusInv, 0, 8, 24));
			addSlotToContainer(new Slot(nexus.nexusInv, 1, 98, 24));
			
			for (int m = 0; m < 5; m++) {
				for (int n = 0; n < 9; n++) {
					addSlotToContainer(new Slot(nexus.nexusInv, n+m*9+2, (n+2)*18-28, 46 + m*18));
				}
			}

			
			for (int i = 0; i < 3; i++)
	        {
	            for (int k = 0; k < 9; k++)
	            {
	            	addSlotToContainer(new Slot(inventoryplayer, k + i * 9 + 9, 8 + k * 18, 140 + i * 18));
	            }
	
	        }
	
	        for (int j = 0; j < 9 && active; j++)
	        {
	        	addSlotToContainer(new Slot(inventoryplayer, j, 8 + j * 18, 198));
	        }
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return true;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        ItemStack var3 = null;
        Slot var4 = (Slot)this.inventorySlots.get(par2);

        if (var4 != null && var4.getHasStack())
        {
            ItemStack var5 = var4.getStack();
            var3 = var5.copy();

            if (par2 < 5 * 9+2 && par2 > 1)
            {
                if (!this.mergeItemStack(var5, 5 * 9+2, this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(var5, 2, 5 * 9+2, false))
            {
                return null;
            }

            if (var5.stackSize == 0)
            {
                var4.putStack((ItemStack)null);
            }
            else
            {
                var4.onSlotChanged();
            }
        }

        return var3;
	}
	
	@Override
	public void putStackInSlot(int par1, ItemStack par2ItemStack) {
		super.putStackInSlot(par1, par2ItemStack);
	}

}
