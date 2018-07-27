package de.sanandrew.mods.claysoldiers.api.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;

public interface ITargetingEntity<E extends EntityCreature>
{
    boolean isEnemyValid(EntityLivingBase target);

    double getReach();

    E getEntity();
}
