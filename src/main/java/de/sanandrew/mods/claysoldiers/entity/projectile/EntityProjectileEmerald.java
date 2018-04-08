/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.projectile;

import de.sanandrew.mods.claysoldiers.api.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.registry.upgrade.Upgrades;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
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
    public float getDamage(Entity e) {
        return (3.0F + MiscUtils.RNG.randomFloat()) * (e.isWet() ? 2.0F : 1.0F);
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

    @Override
    public void onPostHit(Entity e, DamageSource dmgSource) {
        EnumDyeColor color = null;
        if( this.shooterCache instanceof ISoldier ) {
            ISoldierUpgradeInst inst = ((ISoldier) this.shooterCache).getUpgradeInstance(Upgrades.MC_CONCRETEPOWDER, EnumUpgradeType.MISC);
            if( inst != null ) {
                color = EnumDyeColor.byMetadata(inst.getNbtData().getInteger("color"));
            }
        }
        ClaySoldiersMod.proxy.setRenderLightningAt(e.posX, e.posY, e.posZ, color);
    }

    @Override
    protected void onBlockHit(BlockPos pos) {
        EnumDyeColor color = null;
        if( this.shooterCache instanceof ISoldier ) {
            ISoldierUpgradeInst inst = ((ISoldier) this.shooterCache).getUpgradeInstance(Upgrades.MC_CONCRETEPOWDER, EnumUpgradeType.MISC);
            if( inst != null ) {
                color = EnumDyeColor.byMetadata(inst.getNbtData().getInteger("color"));
            }
        }
        ClaySoldiersMod.proxy.setRenderLightningAt(pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D, color);
    }

    public DamageSource getProjDamageSource(Entity hitEntity) {
        return DamageSource.causeThrownDamage(this, this.shooterCache == null ? this : this.shooterCache).setDamageBypassesArmor();
    }
}
