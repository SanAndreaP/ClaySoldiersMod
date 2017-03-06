/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity;

import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgrade;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class SoldierUpgrade
        implements ISoldierUpgradeInst
{
    private final ISoldierUpgrade upgrade;
    private final NBTTagCompound nbt;
    private final ItemStack stack;
    private final EnumUpgradeType type;

    public SoldierUpgrade(ISoldierUpgrade upgrade, EnumUpgradeType type, ItemStack stack) {
        this.upgrade = upgrade;
        this.nbt = new NBTTagCompound();
        this.stack = stack;
        this.type = type;
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
    public EnumUpgradeType getUpgradeType() {
        return this.type;
    }

    @Override
    public ItemStack getSavedStack() {
        return this.stack.copy();
    }
}
