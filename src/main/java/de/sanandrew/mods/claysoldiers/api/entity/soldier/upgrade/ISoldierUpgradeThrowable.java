package de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public interface ISoldierUpgradeThrowable
        extends ISoldierUpgrade
{
    @Nonnull
    Entity createProjectile(World world, Entity shooter, Entity target);
}
