/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.projectile;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.network.ParticlePacketSender;
import de.sanandrew.mods.claysoldiers.util.soldier.ClaymanTeam;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityGravelChunk
    extends EntityThrowable
    implements ISoldierProjectile<EntityGravelChunk>
{
    protected static final int DW_CLAYMANTEAM = 5;
    protected static final int DW_HOMING = 6;

    protected EntityLivingBase target;

    public EntityGravelChunk(World world) {
        super(world);

        this.setSize(0.1F, 0.1F);
        this.renderDistanceWeight = 5D;
    }

    public EntityGravelChunk(World world, EntityLivingBase thrower) {
        super(world, thrower);

        this.setSize(0.1F, 0.1F);
        this.renderDistanceWeight = 5D;
    }

    @Override
    public void initProjectile(EntityLivingBase target, boolean homing, String clayTeam) {
        this.target = target;
        this.dataWatcher.updateObject(DW_HOMING, (byte) (homing ? 1 : 0));
        this.dataWatcher.updateObject(DW_CLAYMANTEAM, clayTeam);
    }

    @Override
    public EntityGravelChunk getProjectileEntity() {
        return this;
    }

    @Override
    protected void entityInit() {
        super.entityInit();

        this.dataWatcher.addObject(DW_CLAYMANTEAM, ClaymanTeam.DEFAULT_TEAM);
        this.dataWatcher.addObject(DW_HOMING, (byte) 0);
    }

    @Override
    public void onUpdate() {
        if( this.isHoming() ) {
            double d = this.target.posX - posX;
            double d1 = this.target.posZ - posZ;
            double d2 = (this.target.posY + this.target.getEyeHeight()) - 0.10000000298023224D - this.posY;
            float f1 = MathHelper.sqrt_double(d * d + d1 * d1) * 0.2F;
            this.setThrowableHeading(d, d2 + f1, d1, 0.6F, 12F);
        }

        super.onUpdate();
    }

    @Override
    public void setThrowableHeading(double motX, double motY, double motZ, float motMulti, float rndMulti) {
        super.setThrowableHeading(motX, motY, motZ, motMulti, rndMulti);
        float f2 = MathHelper.sqrt_double(motX * motX + motY * motY + motZ * motZ);
        motX /= (double)f2;
        motY /= (double)f2;
        motZ /= (double)f2;
        if( !this.isHoming() ) {
            motX += this.rand.nextGaussian() * 0.007499999832361937D * (double) rndMulti;
            motY += this.rand.nextGaussian() * 0.007499999832361937D * (double) rndMulti;
            motZ += this.rand.nextGaussian() * 0.007499999832361937D * (double) rndMulti;
        }
        motX *= (double)motMulti;
        motY *= (double)motMulti;
        motZ *= (double)motMulti;
        this.motionX = motX;
        this.motionY = motY;
        this.motionZ = motZ;
        float f3 = MathHelper.sqrt_double(motX * motX + motZ * motZ);
        this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(motX, motZ) * 180.0D / Math.PI);
        this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(motY, (double)f3) * 180.0D / Math.PI);
    }

    @Override
    protected void onImpact(MovingObjectPosition movObjPos) {
        if( movObjPos.entityHit != null  ) {
            float attackDmg = 2 + this.rand.nextInt(2);

            if( movObjPos.entityHit == this.target && !movObjPos.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), attackDmg) ) {
                if( this.getThrower() instanceof EntityClayMan ) {
                    ((EntityClayMan) this.getThrower()).onProjectileHit(this, movObjPos);
                }
            } else {
                return;
            }
        }

        if( !this.worldObj.isRemote ) {
            ParticlePacketSender.sendDiggingFx(this.posX, this.posY, this.posZ, this.dimension, Blocks.gravel);
            this.setDead();
        }
    }

    private boolean isHoming() {
        return this.dataWatcher.getWatchableObjectByte(DW_HOMING) == 1 && this.target != null && !this.target.isDead && this.target.getHealth() > 0;
    }
}
