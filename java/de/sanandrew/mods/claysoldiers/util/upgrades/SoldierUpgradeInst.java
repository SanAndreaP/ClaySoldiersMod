package de.sanandrew.mods.claysoldiers.util.upgrades;

import net.minecraft.nbt.NBTTagCompound;

/**
 * @author SanAndreas
 * @version 1.0
 */
public final class SoldierUpgradeInst
{
    private ISoldierUpgrade upgrade_;
    private NBTTagCompound upgNbt_ = new NBTTagCompound();

    public SoldierUpgradeInst(ISoldierUpgrade upgrade) {
        this.upgrade_ = upgrade;
    }

    public NBTTagCompound getNbtTag() {
        return this.upgNbt_;
    }

    public ISoldierUpgrade getUpgrade() {
        return this.upgrade_;
    }

    public void setNbtTag(NBTTagCompound nbt) {
        if( nbt != null ) {
            this.upgNbt_ = nbt;
        }
    }
}
