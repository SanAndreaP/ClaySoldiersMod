/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util.soldier.upgrade;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

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

    public void setNbtTag(NBTTagCompound nbt) {
        if( nbt != null ) {
            this.nbt_ = nbt;
        }
    }

    public ASoldierUpgrade getUpgrade() {
        return this.upgrade_;
    }
}
