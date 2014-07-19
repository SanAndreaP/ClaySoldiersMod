package de.sanandrew.mods.claysoldiers.util.soldier.effect;

import net.minecraft.nbt.NBTTagCompound;

/**
 * @author SanAndreas
 * @version 1.0
 */
public final class SoldierEffectInst
{
    private ASoldierEffect effect_;
    private NBTTagCompound nbt_ = new NBTTagCompound();

    public SoldierEffectInst(ASoldierEffect effect) {
        this.effect_ = effect;
    }

    public NBTTagCompound getNbtTag() {
        return this.nbt_;
    }

    public ASoldierEffect getEffect() {
        return this.effect_;
    }

    public void setNbtTag(NBTTagCompound nbt) {
        if( nbt != null ) {
            this.nbt_ = nbt;
        }
    }
}
