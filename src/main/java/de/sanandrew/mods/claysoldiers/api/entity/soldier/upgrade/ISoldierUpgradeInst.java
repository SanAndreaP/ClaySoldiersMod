/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface ISoldierUpgradeInst
{
    NBTTagCompound getNbtData();

    void setNbtData(NBTTagCompound compound);

    ISoldierUpgrade getUpgrade();

    EnumUpgradeType getUpgradeType();

    ItemStack getSavedStack();
}
