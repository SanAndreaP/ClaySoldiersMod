/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util.soldier.effect;

import net.minecraft.nbt.NBTTagCompound;

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

    public void setNbtTag(NBTTagCompound nbt) {
        if( nbt != null ) {
            this.nbt_ = nbt;
        }
    }

    public ASoldierEffect getEffect() {
        return this.effect_;
    }
}
