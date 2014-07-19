package de.sanandrew.mods.claysoldiers.util.soldier.upgrade;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * @author SanAndreas
 * @version 1.0
 */
public final class SoldierUpgradeInst
{
    private ASoldierUpgrade upgrade_;
    private ItemStack storedItem_;
    private NBTTagCompound nbt_ = new NBTTagCompound();

    public SoldierUpgradeInst(ASoldierUpgrade upgrade) {
        this.upgrade_ = upgrade;
    }

    public ItemStack getStoredItem() {
        return this.storedItem_;
    }

    public void setStoredItem(ItemStack stack) {
        this.storedItem_ = stack;
    }

    public void readStoredItemFromNBT(NBTTagCompound nbt) {
        if( nbt != null ) {
            this.storedItem_ = ItemStack.loadItemStackFromNBT(nbt);
        }
    }

    public NBTTagCompound saveStoredItemToNBT() {
        if( this.storedItem_ != null ) {
            NBTTagCompound nbt = new NBTTagCompound();
            this.storedItem_.writeToNBT(nbt);
            return nbt;
        }

        return null;
    }

    public NBTTagCompound getNbtTag() {
        return this.nbt_;
    }

    public ASoldierUpgrade getUpgrade() {
        return this.upgrade_;
    }

    public void setNbtTag(NBTTagCompound nbt) {
        if( nbt != null ) {
            this.nbt_ = nbt;
        }
    }
}
