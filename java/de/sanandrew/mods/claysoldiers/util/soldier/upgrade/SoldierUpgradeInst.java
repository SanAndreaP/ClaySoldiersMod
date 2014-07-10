package de.sanandrew.mods.claysoldiers.util.soldier.upgrade;

import net.minecraft.nbt.NBTTagCompound;

/**
 * @author SanAndreas
 * @version 1.0
 */
public final class SoldierUpgradeInst
{
    private ISoldierUpgrade upgrade_;
    private NBTTagCompound nbt_ = new NBTTagCompound();

    public SoldierUpgradeInst(ISoldierUpgrade upgrade) {
        this.upgrade_ = upgrade;
    }

    public NBTTagCompound getNbtTag() {
        return this.nbt_;
    }

    public ISoldierUpgrade getUpgrade() {
        return this.upgrade_;
    }

    public void setNbtTag(NBTTagCompound nbt) {
        if( nbt != null ) {
            this.nbt_ = nbt;
        }
    }
}
