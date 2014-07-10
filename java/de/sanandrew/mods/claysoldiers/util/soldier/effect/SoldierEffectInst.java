package de.sanandrew.mods.claysoldiers.util.soldier.effect;

import net.minecraft.nbt.NBTTagCompound;

/**
 * @author SanAndreas
 * @version 1.0
 */
public final class SoldierEffectInst
{
    private ISoldierEffect effect_;
    private NBTTagCompound nbt_ = new NBTTagCompound();

    public SoldierEffectInst(ISoldierEffect effect) {
        this.effect_ = effect;
    }

    public NBTTagCompound getNbtTag() {
        return this.nbt_;
    }

    public ISoldierEffect getEffect() {
        return this.effect_;
    }

    public void setNbtTag(NBTTagCompound nbt) {
        if( nbt != null ) {
            this.nbt_ = nbt;
        }
    }
}
