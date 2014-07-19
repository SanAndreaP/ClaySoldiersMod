/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.projectile;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.network.ParticlePacketSender;
import de.sanandrew.mods.claysoldiers.util.soldier.effect.SoldierEffects;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntitySnowChunk
    extends EntityGravelChunk
{
    public EntitySnowChunk(World world) {
        super(world);
    }

    public EntitySnowChunk(World world, EntityLivingBase thrower) {
        super(world, thrower);
    }

    @Override
    protected void onImpact(MovingObjectPosition movObjPos) {
        if( movObjPos.entityHit != null  ) {
            if( movObjPos.entityHit == this.target && movObjPos.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0.0F) ) {
                if( this.getThrower() instanceof EntityClayMan ) {
                    ((EntityClayMan) this.getThrower()).onProjectileHit(this, movObjPos);
                }
                if( movObjPos.entityHit instanceof EntityClayMan ) {
                    if( ((EntityClayMan) movObjPos.entityHit).applyEffect(SoldierEffects.getEffectFromName(SoldierEffects.EFF_SLOWMOTION)) != null ) {
                        movObjPos.entityHit.playSound("random.step.snow", 1.0F, 1.0F);
                    }
                }
            } else {
                return;
            }
        }

        if( !this.worldObj.isRemote ) {
            ParticlePacketSender.sendDiggingFx(this.posX, this.posY, this.posZ, this.dimension, Blocks.snow);
            this.setDead();
        }
    }
}
