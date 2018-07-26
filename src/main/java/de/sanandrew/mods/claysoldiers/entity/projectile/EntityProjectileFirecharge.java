/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.projectile;

import de.sanandrew.mods.claysoldiers.api.entity.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.registry.upgrade.Upgrades;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
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
    public float getDamage(Entity e) {
        return 1.0F + MiscUtils.RNG.randomFloat();
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
            e.setFire(this.shooterCache instanceof ISoldier && ((ISoldier) this.shooterCache).hasUpgrade(Upgrades.EM_COAL, EnumUpgradeType.ENHANCEMENT) ? 10 : 5);
        }
    }
}
