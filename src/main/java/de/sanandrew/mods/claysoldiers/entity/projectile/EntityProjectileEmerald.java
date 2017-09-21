/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.projectile;

import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityProjectileEmerald
        extends EntityClayProjectile
{
    public EntityProjectileEmerald(World world) {
        super(world);
    }

    public EntityProjectileEmerald(World world, Entity shooter, Entity target) {
        super(world, shooter, target);
    }

    @Override
    public float getInitialSpeedMultiplier() {
        return 1.0F;
    }

    @Override
    public float getDamage() {
        return 4.0F + MiscUtils.RNG.randomFloat();
    }

    @Override
    public float getKnockbackStrengthH() {
        return 0.01F;
    }

    @Override
    public float getKnockbackStrengthV() {
        return 0.05F;
    }

    @Override
    public SoundEvent getRicochetSound() {
        return SoundEvents.ENTITY_LIGHTNING_THUNDER;
    }

    @Override
    public float getArc() {
        return 0.1F;
    }

    public DamageSource getProjDamageSource(Entity hitEntity) {
        return DamageSource.causeThrownDamage(this, this.shooterCache == null ? this : this.shooterCache).setDamageBypassesArmor();
    }
}
