/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util;

import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.io.Serializable;

public class HashItemStack
        implements Serializable
{
    private static final long serialVersionUID = -1529393340135181330L;

    private ItemStack heldStack;

    public HashItemStack() { }

    public HashItemStack(ItemStack stack) {
        this(stack, false);
    }

    public HashItemStack(ItemStack stack, boolean withWildcard) {
        this.heldStack = stack;
    }

    @Override
    @SuppressWarnings("NonFinalFieldReferencedInHashCode")
    public int hashCode() {
        return this.hashCode(this.heldStack, this.heldStack.getItemDamage() == OreDictionary.WILDCARD_VALUE);
    }

    @Override
    @SuppressWarnings("NonFinalFieldReferenceInEquals")
    public boolean equals(Object obj) {
        if( obj instanceof ItemStack ) {
            ItemStack stack = (ItemStack) obj;
            boolean withWildcard = this.heldStack.getItemDamage() == OreDictionary.WILDCARD_VALUE || stack.getItemDamage() == OreDictionary.WILDCARD_VALUE;
            return this.hashCode(this.heldStack, withWildcard) == this.hashCode(stack, withWildcard);
        }

        return obj instanceof HashItemStack ? obj.equals(this.heldStack) : this == obj;
    }

    public ItemStack getStack() {
        return this.heldStack;
    }

    public int hashCode(ItemStack stack, boolean withWC) {
        return 911 * stack.getItem().hashCode() ^ 401 * (withWC ? 1 : stack.getItemDamage()) ^ 521 * (MiscUtils.defIfNull(stack.getTagCompound(), 1).hashCode());
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        NBTTagCompound nbt = new NBTTagCompound();
        this.heldStack.writeToNBT(nbt);

        CompressedStreamTools.writeCompressed(nbt, out);
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        NBTTagCompound nbt = CompressedStreamTools.read(in, NBTSizeTracker.INFINITE);
        this.heldStack = ItemStack.loadItemStackFromNBT(nbt);
    }

    private void readObjectNoData() throws ObjectStreamException {
        throw new InvalidObjectException("This object cannot be empty!");
    }
}
