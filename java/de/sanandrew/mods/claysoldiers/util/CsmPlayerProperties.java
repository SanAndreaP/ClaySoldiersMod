/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public final class CsmPlayerProperties
        implements IExtendedEntityProperties
{
    public static final String PROPERTY_CLS_ID = ClaySoldiersMod.MOD_ID + ":EEPPlayer";

    public static final byte MAX_DISRUPT_DELAY = 50;

    private final EntityPlayer p_player;
    private byte currDisruptDelay;

    public CsmPlayerProperties(EntityPlayer player) {
        this.p_player = player;
        this.currDisruptDelay = 0;
    }

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setByte("currDisruptDelay", this.currDisruptDelay);
        compound.setTag(PROPERTY_CLS_ID, nbt);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        NBTTagCompound nbt = compound.getCompoundTag(PROPERTY_CLS_ID);
        this.currDisruptDelay = nbt.getByte("currDisplayDelay");
    }

    @Override
    public void init(Entity entity, World world) {

    }

    public boolean canDisruptorBeUsed() {
        return this.currDisruptDelay == 0;
    }

    public void setDisruptorFired() {
        this.currDisruptDelay = MAX_DISRUPT_DELAY;
    }

    public void decrDisruptDelay() {
        if( this.currDisruptDelay > 0 ) {
            this.currDisruptDelay--;
        }
    }

    public static void register(EntityPlayer player) {
        player.registerExtendedProperties(PROPERTY_CLS_ID, new CsmPlayerProperties(player));
    }

    public static CsmPlayerProperties get(EntityPlayer player) {
        return (CsmPlayerProperties) player.getExtendedProperties(PROPERTY_CLS_ID);
    }
}
