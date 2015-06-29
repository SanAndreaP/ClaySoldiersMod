/*******************************************************************************************************************
* Name: InventoryNexus.java
* Author: SanAndreasP
* Copyright: SanAndreasP and SilverChiren
* License: Attribution-NonCommercial-ShareAlike 3.0 Unported (http://creativecommons.org/licenses/by-nc-sa/3.0/)
*******************************************************************************************************************/

package sanandreasp.mods.ClaySoldiersMod.inventory;
import sanandreasp.mods.ClaySoldiersMod.entity.EntityClayNexus;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.src.*;

public class InventoryNexus implements IInventory {
	public EntityClayNexus nexus;
	
	public InventoryNexus(EntityClayNexus cln) {
		nexus = cln;
	}

	@Override
	public int getSizeInventory() {
		return 47;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return nexus.nexusIS[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		ItemStack is = nexus.nexusIS[i].copy();
		nexus.nexusIS[i].stackSize -= j;
		
		if (nexus.nexusIS[i].stackSize <= 0) nexus.nexusIS[i] = null;
		
		return is;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		nexus.nexusIS[i] = itemstack;
	}

	@Override
	public String getInvName() {
		return "Clay Nexus";
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public void onInventoryChanged() {

	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return true;
	}

	@Override
	public void openChest() {

	}

	@Override
	public void closeChest() {

	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

//	@Override
//	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
//		return false;
//	}

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        return false;
    }

}
