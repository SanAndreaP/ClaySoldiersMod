/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.projectile;

import de.sanandrew.core.manpack.util.annotation.UsedByReflection;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.network.ParticlePacketSender;
import de.sanandrew.mods.claysoldiers.util.soldier.effect.SoldierEffects;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class EntitySnowChunk
        extends EntityGravelChunk
{
    @UsedByReflection
    public EntitySnowChunk(World world) {
        super(world);
    }

    @UsedByReflection
    public EntitySnowChunk(World world, EntityLivingBase thrower) {
        super(world, thrower);
    }

    @UsedByReflection
    public EntitySnowChunk(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    @Override
    protected void onImpact(MovingObjectPosition movObjPos) {
        if( movObjPos.entityHit != null ) {
            boolean isEnemy = movObjPos.entityHit instanceof EntityClayMan && this.target instanceof EntityClayMan
                    && ((EntityClayMan) movObjPos.entityHit).getClayTeam().equals(((EntityClayMan) this.target).getClayTeam());

            DamageSource dmgSrc = DamageSource.causeThrownDamage(this, this.getThrower());
            if( this.getThrower() == null ) {
                dmgSrc = DamageSource.causeThrownDamage(this, this);
            }

            if( (movObjPos.entityHit == this.target || isEnemy) && movObjPos.entityHit.attackEntityFrom(dmgSrc, 0.0F) ) {
                if( this.getThrower() instanceof EntityClayMan ) {
                    ((EntityClayMan) this.getThrower()).onProjectileHit(this, movObjPos);
                }

                if( movObjPos.entityHit instanceof EntityClayMan ) {
                    if( ((EntityClayMan) movObjPos.entityHit).addEffect(SoldierEffects.getEffect(SoldierEffects.EFF_SLOWMOTION)) != null ) {
                        movObjPos.entityHit.playSound("step.snow", 1.0F, 1.0F);
                    }
                }
            } else {
                return;
            }
        }

        if( !this.worldObj.isRemote ) {
            if( movObjPos.typeOfHit != MovingObjectType.BLOCK
                || this.getBlockCollisionBox(this.worldObj, movObjPos.blockX, movObjPos.blockY, movObjPos.blockZ) != null )
            {
                ParticlePacketSender.sendDiggingFx(this.posX, this.posY, this.posZ, this.dimension, Blocks.snow);
                this.setDead();
            }

            this.dataWatcher.updateObject(DW_DEAD, (byte) (this.isDead ? 1 : 0));
        }
    }
}
