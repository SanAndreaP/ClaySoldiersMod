/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.projectile;

import de.sanandrew.mods.claysoldiers.api.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.registry.upgrade.Upgrades;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityProjectileSnow
        extends EntityClayProjectile
{
    public EntityProjectileSnow(World world) {
        super(world);
    }

    public EntityProjectileSnow(World world, Entity shooter, Entity target) {
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
        return SoundEvents.BLOCK_SNOW_BREAK;
    }

    @Override
    public float getArc() {
        return 0.1F;
    }

    @Override
    public void onPostHit(Entity e, DamageSource dmgSource) {
        if( e instanceof EntityLivingBase ) {
            if( !(e instanceof ISoldier) || MiscUtils.RNG.randomBool() || !((ISoldier) e).hasUpgrade(Upgrades.EC_IRONBLOCK, EnumUpgradeType.ENHANCEMENT) ) {
                ((EntityLivingBase) e).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100, 1));
            }
        }
    }
}
