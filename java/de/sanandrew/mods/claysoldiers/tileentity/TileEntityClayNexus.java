/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.tileentity;

import de.sanandrew.core.manpack.util.SAPUtils;
import de.sanandrew.mods.claysoldiers.item.ItemClayManDoll;
import de.sanandrew.mods.claysoldiers.util.soldier.ClaymanTeam;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TileEntityClayNexus
    extends TileEntity implements IInventory
{
    public boolean isActive = false;
    public int spawnSoldierInterval = 40;
    public int spawnThrowableInterval = 30;
    public int soldierSpawnCount = 10;
    public int mountSpawnCount = 10;

    public long ticksActive = 0L;

    public TileEntityClayNexus() { }

    @Override
    public void updateEntity() {
        super.updateEntity();

        this.ticksActive++;

        if( !this.worldObj.isRemote && this.isActive ) {
            if( this.ticksActive % this.spawnSoldierInterval == 0 ) {
                for( int i = 0; i < this.soldierSpawnCount; i++ ) {
                    ItemClayManDoll.spawnClayMan(this.worldObj, ClaymanTeam.DEFAULT_TEAM, this.xCoord + SAPUtils.RANDOM.nextDouble(), this.yCoord + 0.5D,
                                                 this.zCoord + SAPUtils.RANDOM.nextDouble()
                    );
//                    EntityClayMan dodger = new EntityClayMan(this.worldObj, ClaymanTeam.DEFAULT_TEAM);
//                    dodger.setLocationAndAngles(this.xCoord + SAPUtils.RANDOM.nextDouble(), this.yCoord + 0.5D, this.zCoord + SAPUtils.RANDOM.nextDouble(),
//                                                this.);
                }
            }
        }
    }

    @Override
    public boolean canUpdate() {
        return true;
    }

    @Override
    public int getSizeInventory() {
        return 0;
    }

    @Override
    public ItemStack getStackInSlot(int p_70301_1_) {
        return null;
    }

    @Override
    public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {

    }

    @Override
    public String getInventoryName() {
        return null;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 0;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
        return false;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
        return false;
    }
}
