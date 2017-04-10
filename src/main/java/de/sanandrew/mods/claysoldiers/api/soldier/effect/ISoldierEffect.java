/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.api.soldier.effect;

import de.sanandrew.mods.claysoldiers.api.soldier.ISoldier;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public interface ISoldierEffect
{
    int getDuration();

    default boolean syncData() { return false; }

    default boolean syncNbtData() { return false; }

    default void writeSyncData(ByteBuf buf, NBTTagCompound nbt) { }

    default void readSyncData(ByteBuf buf, NBTTagCompound nbt) { }

    default void onAdded(ISoldier<?> soldier, ISoldierEffectInst effectInst) { }

    default void onTick(ISoldier<?> soldier, ISoldierEffectInst effectInst) { }

    default void onExpired(ISoldier<?> soldier, ISoldierEffectInst effectInst) { }
}
