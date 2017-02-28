/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity;

import de.sanandrew.mods.claysoldiers.api.soldier.ISoldierUpgrade;
import de.sanandrew.mods.claysoldiers.api.soldier.ISoldierUpgradeInst;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class SoldierUpgrade
        implements ISoldierUpgradeInst
{
    private final ISoldierUpgrade upgrade;
    private final NBTTagCompound nbt;
    public final ItemStack stack;

    public SoldierUpgrade(ISoldierUpgrade upgrade, ItemStack stack) {
        this.upgrade = upgrade;
        this.nbt = new NBTTagCompound();
        this.stack = stack;
    }

    @Override
    public NBTTagCompound getNbtData() {
        return this.nbt;
    }

    @Override
    public void setNbtData(NBTTagCompound compound) {
        this.nbt.getKeySet().forEach(this.nbt::removeTag);
        this.nbt.merge(compound);
    }

    @Override
    public ISoldierUpgrade getUpgrade() {
        return this.upgrade;
    }

    @Override
    public ItemStack getSavedStack() {
        return this.stack.copy();
    }
}
