/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.projectile;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.network.ParticlePacketSender;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityFirechargeChunk
    extends EntityGravelChunk
{
    public EntityFirechargeChunk(World world) {
        super(world);
    }

    public EntityFirechargeChunk(World world, EntityLivingBase thrower) {
        super(world, thrower);
    }

    @Override
    protected void onImpact(MovingObjectPosition movObjPos) {
        if( movObjPos.entityHit != null  ) {
            float damage = 1.0F + this.rand.nextFloat();

            if( movObjPos.entityHit == this.target && movObjPos.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), damage) ) {
                if( this.getThrower() instanceof EntityClayMan ) {
                    ((EntityClayMan) this.getThrower()).onProjectileHit(this, movObjPos);
                }
                if( movObjPos.entityHit instanceof EntityClayMan ) {
                    movObjPos.entityHit.setFire(3);
                    movObjPos.entityHit.playSound("random.fizz", 1.0F, 1.0F);
                }
            } else {
                return;
            }
        }

        if( !this.worldObj.isRemote ) {
            ParticlePacketSender.sendDiggingFx(this.posX, this.posY, this.posZ, this.dimension, Blocks.obsidian); //TODO: substitude! change it when
            this.setDead();                                                                                       //TODO: proper texture arrives
        }
    }
}
