/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.tileentity;

import de.sanandrew.core.manpack.util.NbtTypes;
import de.sanandrew.core.manpack.util.SAPUtils;
import de.sanandrew.core.manpack.util.SAPUtils.RGBAValues;
import de.sanandrew.core.manpack.util.javatuples.Sextet;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.item.IMountDoll;
import de.sanandrew.mods.claysoldiers.item.ItemClayManDoll;
import de.sanandrew.mods.claysoldiers.network.packet.PacketParticleFX;
import de.sanandrew.mods.claysoldiers.util.CSM_Main;
import de.sanandrew.mods.claysoldiers.util.ModItems;
import de.sanandrew.mods.claysoldiers.util.soldier.ClaymanTeam;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.ASoldierUpgrade;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.IThrowableUpgrade;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgrades;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

import java.util.List;

public class TileEntityClayNexus
    extends TileEntity implements IInventory
{
    public boolean isActive = false;

    public int spawnSoldierInterval = 40;
    public int spawnThrowableInterval = 30;
    public int soldierSpawnCount = 10;
    public int mountSpawnCount = 10;
    public int maxSoldierCount = 10;

    public long ticksActive = 0L;

    public float spinAngle = 0F;
    public float prevSpinAngle = 0F;

    protected String customName;

    private ItemStack[] nexusContents = new ItemStack[36];
    private ItemStack soldierSlot;
    private ItemStack throwableSlot;
    private ItemStack mountSlot;

    public TileEntityClayNexus() { }

    @Override
    public void updateEntity() {
        super.updateEntity();

        if( this.isActive && this.soldierSlot != null ) {
            this.ticksActive++;

            if( !this.worldObj.isRemote ) {
                if( this.ticksActive % this.spawnSoldierInterval == 0 ) {
                    String team = ItemClayManDoll.getTeam(this.soldierSlot).getTeamName();
                    int maxAllowed = Math.min(this.soldierSpawnCount, this.maxSoldierCount - this.countTeammates(team));
                    for( int i = 0; i < maxAllowed; i++ ) {
                        ItemClayManDoll.spawnClayMan(this.worldObj, team, this.xCoord + 0.5F, this.yCoord + 0.2D,
                                                     this.zCoord + 0.5F
                        );
                    }
                }
            }
        }

        if( this.worldObj.isRemote ) {
            this.prevSpinAngle = this.spinAngle;
            if( this.isActive ) {
                this.spinAngle += 4;
                ClaymanTeam team = ItemClayManDoll.getTeam(this.soldierSlot);
                RGBAValues rgba = SAPUtils.getRgbaFromColorInt(team.getTeamColor());
                CSM_Main.proxy.spawnParticles(PacketParticleFX.FX_NEXUS, Sextet.with((double)this.xCoord, (double)this.yCoord, (double)this.zCoord,
                                                                                     rgba.getRed() / 255.0F, rgba.getGreen() / 255.0F, rgba.getBlue() / 255.0F));
            } else if( this.spinAngle % 90 != 0 ) {
                this.spinAngle += 2;
            }

            if( this.spinAngle >= 360 ) {
                this.prevSpinAngle = -1;
                this.spinAngle = 0;
            }
        }
    }

    @Override
    public boolean canUpdate() {
        return true;
    }

    @Override
    public int getSizeInventory() {
        return 39;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        switch( slot ) {
            case 0:
                return soldierSlot;
            case 1:
                return throwableSlot;
            case 2:
                return mountSlot;
            default:
                return this.nexusContents[slot - 3];
        }
    }

    @Override
    public ItemStack decrStackSize(int slot, int reduceAmount) {
        ItemStack stack = this.getStackInSlot(slot);

        if( stack != null ) {
            if( stack.stackSize <= reduceAmount ) {
                this.setStackInSlot(slot, null);
                this.markDirty();
                return stack;
            } else {
                ItemStack splitStack = stack.splitStack(reduceAmount);

                if( stack.stackSize == 0 ) {
                    this.setStackInSlot(slot, null);
                }

                this.markDirty();
                return splitStack;
            }
        } else {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        ItemStack stack = this.getStackInSlot(slot);
        if( stack != null ) {
            this.setStackInSlot(slot, null);
            return stack;
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack newStack) {
        this.setStackInSlot(slot, newStack);

        if( newStack != null && newStack.stackSize > this.getInventoryStackLimit() ) {
            newStack.stackSize = this.getInventoryStackLimit();
        }

        this.markDirty();
    }

    @Override
    public String getInventoryName() {
        return this.hasCustomInventoryName() ? this.customName : CSM_Main.MOD_ID + ":container.nexus";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return this.customName != null;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this
                && p_70300_1_.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory() { }

    @Override
    public void closeInventory() { }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        switch( slot ) {
            case 0:
                return stack == null || stack.getItem() == ModItems.dollSoldier;
            case 1:
                ASoldierUpgrade upgrade = SoldierUpgrades.getUpgradeFromItem(stack);
                return stack == null || upgrade instanceof IThrowableUpgrade;
            case 2:
                return stack == null || stack.getItem() instanceof IMountDoll;
            default:
                return stack == null || SoldierUpgrades.getUpgradeFromItem(stack) != null;
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        this.nexusContents = new ItemStack[36];

        NBTTagList nbttaglist = nbt.getTagList("items", NbtTypes.NBT_COMPOUND);
        for( int i = 0; i < nbttaglist.tagCount(); i++ ) {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            int slot = nbttagcompound1.getByte("slot") & 255;

            if( slot >= 0 && slot < this.getSizeInventory() ) {
                this.setStackInSlot(slot, ItemStack.loadItemStackFromNBT(nbttagcompound1));
            }
        }

        this.readTileNBT(nbt);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        NBTTagList nbttaglist = new NBTTagList();
        for( int slot = 0; slot < this.getSizeInventory(); slot++ ) {
            ItemStack slotStack = this.getStackInSlot(slot);
            if( slotStack != null ) {
                NBTTagCompound itemTag = new NBTTagCompound();
                itemTag.setByte("slot", (byte) slot);
                slotStack.writeToNBT(itemTag);
                nbttaglist.appendTag(itemTag);
            }
        }
        nbt.setTag("items", nbttaglist);

        this.writeTileNBT(nbt);
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.func_148857_g());
    }

    private void readTileNBT(NBTTagCompound nbt) {
        this.isActive = nbt.getBoolean("active");
        this.spawnSoldierInterval = nbt.getInteger("spawnSldInterval");
        this.spawnThrowableInterval = nbt.getInteger("spawnThrwInterval");
        this.soldierSpawnCount = nbt.getInteger("spawnSldCount");
        this.mountSpawnCount = nbt.getInteger("spawnMntCount");
        this.maxSoldierCount = nbt.getInteger("maxSldCount");

        if( nbt.hasKey("customName") ) {
            customName = nbt.getString("customName");
        }
    }

    private void writeTileNBT(NBTTagCompound nbt) {
        nbt.setInteger("spawnSldInterval", this.spawnSoldierInterval);
        nbt.setInteger("spawnThrwInterval", this.spawnThrowableInterval);
        nbt.setInteger("spawnSldCount", this.soldierSpawnCount);
        nbt.setInteger("spawnMntCount", this.mountSpawnCount);
        nbt.setInteger("maxSldCount", this.maxSoldierCount);

        if( this.hasCustomInventoryName() ) {
            nbt.setString("customName", this.customName);
        }
    }

    private int countTeammates(String team) {
        @SuppressWarnings("unchecked")
        List<EntityClayMan> soldiers = this.worldObj.getEntitiesWithinAABB(EntityClayMan.class,
                                                                           AxisAlignedBB.getBoundingBox(this.xCoord - 63.0D, this.yCoord - 63.0D, this.zCoord - 63.0D,
                                                                                                        this.xCoord + 64.0D, this.yCoord + 64.0D, this.zCoord + 64.0D)
        );
        int cnt = 0;
        for( EntityClayMan dodger : soldiers ) {
            if( dodger.getClayTeam().equals(team) ) {
                cnt++;
            }
        }
        return cnt;
    }

    private void setStackInSlot(int slot, ItemStack stack) {
        switch( slot ) {
            case 0:
                soldierSlot = stack;
                break;
            case 1:
                throwableSlot = stack;
                break;
            case 2:
                mountSlot = stack;
                break;
            default:
                this.nexusContents[slot - 3] = stack;
        }
    }
}
