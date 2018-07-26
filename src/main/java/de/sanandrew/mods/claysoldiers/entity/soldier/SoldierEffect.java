/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.soldier;

import de.sanandrew.mods.claysoldiers.api.entity.soldier.effect.ISoldierEffect;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.effect.ISoldierEffectInst;
import net.minecraft.nbt.NBTTagCompound;

public class SoldierEffect
        implements ISoldierEffectInst
{
    private final ISoldierEffect effect;
    private final NBTTagCompound nbt;
    private int duration;

    public SoldierEffect(ISoldierEffect effect, int duration) {
        this.effect = effect;
        this.nbt = new NBTTagCompound();
        this.duration = duration;
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
    public ISoldierEffect getEffect() {
        return this.effect;
    }

    @Override
    public int getDurationLeft() {
        return this.duration;
    }

    @Override
    public void decreaseDuration(int amount) {
        this.duration -= amount;
    }

    @Override
    public boolean stillActive() {
        return this.duration > 0;
    }
}
