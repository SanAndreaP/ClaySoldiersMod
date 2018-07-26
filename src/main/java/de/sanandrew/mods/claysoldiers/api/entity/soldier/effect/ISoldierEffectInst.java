/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.api.entity.soldier.effect;

import net.minecraft.nbt.NBTTagCompound;

public interface ISoldierEffectInst
{
    NBTTagCompound getNbtData();

    void setNbtData(NBTTagCompound compound);

    ISoldierEffect getEffect();

    int getDurationLeft();

    void decreaseDuration(int amount);

    boolean stillActive();
}
