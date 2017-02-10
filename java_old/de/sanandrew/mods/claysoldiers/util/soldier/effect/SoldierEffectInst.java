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
    private ASoldierEffect p_effect;
    private NBTTagCompound p_nbt = new NBTTagCompound();

    public SoldierEffectInst(ASoldierEffect effect) {
        this.p_effect = effect;
    }

    public NBTTagCompound getNbtTag() {
        return this.p_nbt;
    }

    public void setNbtTag(NBTTagCompound nbt) {
        if( nbt != null ) {
            this.p_nbt = nbt;
        }
    }

    public ASoldierEffect getEffect() {
        return this.p_effect;
    }
}
