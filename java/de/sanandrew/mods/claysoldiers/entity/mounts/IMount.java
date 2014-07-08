package de.sanandrew.mods.claysoldiers.entity.mounts;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.src.*;

public interface IMount
{
    public abstract IMount setSpawnedFromNexus();
    
    public abstract int getType();
}
