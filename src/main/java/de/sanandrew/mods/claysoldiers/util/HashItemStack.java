/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util;

import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class HashItemStack
{
    private final ItemStack heldStack;

    public HashItemStack(ItemStack stack) {
        this(stack, false);
    }

    public HashItemStack(ItemStack stack, boolean withWildcard) {
        this.heldStack = stack;
    }

    @Override
    public int hashCode() {
        return this.hashCode(this.heldStack, this.heldStack.getItemDamage() == OreDictionary.WILDCARD_VALUE);
    }

    @Override
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
}
