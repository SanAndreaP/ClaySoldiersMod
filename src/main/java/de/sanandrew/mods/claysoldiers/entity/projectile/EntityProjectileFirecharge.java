/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityProjectileFirecharge
        extends EntityClayProjectile
{
    public EntityProjectileFirecharge(World world) {
        super(world);
    }

    public EntityProjectileFirecharge(World world, Entity shooter, Entity target) {
        super(world, shooter, target);
    }

    @Override
    public float getInitialSpeedMultiplier() {
        return 0.5F;
    }

    @Override
    public float getDamage() {
        return 0.0F;
    }

    @Override
    public float getKnockbackStrengthH() {
        return 0.01F;
    }

    @Override
    public float getKnockbackStrengthV() {
        return 0.2F;
    }

    @Override
    public SoundEvent getRicochetSound() {
        return SoundEvents.ENTITY_GHAST_SHOOT;
    }

    @Override
    public float getArc() {
        return 0.1F;
    }

    @Override
    public void onPostHit(Entity e, DamageSource dmgSource) {
        if( e != null ) {
            e.setFire(5);
        }
    }
}
