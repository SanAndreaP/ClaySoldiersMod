/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.api.soldier;

import net.minecraft.nbt.NBTTagCompound;

public interface IUpgradeInst
{
    NBTTagCompound getNbtData();

    void setNbtData(NBTTagCompound compound);

    void destroy();
}
