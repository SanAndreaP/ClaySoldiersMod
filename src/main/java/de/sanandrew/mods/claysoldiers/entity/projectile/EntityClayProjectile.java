/*
 * ****************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 * *****************************************************************************************************************
 */
package de.sanandrew.mods.claysoldiers.entity.projectile;

import de.sanandrew.mods.sanlib.lib.util.EntityUtils;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import org.apache.commons.lang3.mutable.MutableFloat;

import java.util.List;
import java.util.UUID;

@SuppressWarnings({"SuspiciousNameCombination", "BooleanMethodNameMustStartWithQuestion"})
public abstract class EntityClayProjectile
        extends Entity
        implements IProjectile, IEntityAdditionalSpawnData
{
    protected UUID shooterUUID;
    protected Entity shooterCache;
    protected UUID targetUUID;
    protected Entity targetCache;

    protected double maxDist;

    public EntityClayProjectile(World world) {
        super(world);
        this.setSize(0.08F, 0.08F);
        this.maxDist = 32.0D;
    }

    public EntityClayProjectile(World world, Entity shooter, Entity target) {
        this(world);

        double y = shooter.posY + shooter.getEyeHeight() - 0.1D;

        this.setPosition(shooter.posX, y, shooter.posZ);

        this.shooterUUID = shooter.getUniqueID();
        this.shooterCache = shooter;

        this.targetUUID = target.getUniqueID();
        this.targetCache = target;

        Vec3d targetVec = new Vec3d(target.posX - shooter.posX, (target.getEntityBoundingBox().minY + target.height / 1.4D) - y, target.posZ - shooter.posZ);
        this.setHeadingFromVec(targetVec.normalize());

        this.motionY += this.getArc() * Math.sqrt(targetVec.x * targetVec.x + targetVec.z * targetVec.z) * 0.05;
    }

    private void setHeadingFromVec(Vec3d vector) {
        double scatterVal = getScatterValue();
        float initSpeed = getInitialSpeedMultiplier();

        this.motionX = vector.x * initSpeed + (MiscUtils.RNG.randomDouble() * 2.0D - 1.0D) * scatterVal;
        this.motionZ = vector.z * initSpeed + (MiscUtils.RNG.randomDouble() * 2.0D - 1.0D) * scatterVal;
        this.motionY = vector.y * initSpeed + (MiscUtils.RNG.randomDouble() * 2.0D - 1.0D) * scatterVal;

        float vecPlaneNormal = MathHelper.sqrt(vector.x * vector.x + vector.z * vector.z);

        this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(vector.x, vector.z) * 180.0D / Math.PI);
        this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(vector.y, vecPlaneNormal) * 180.0D / Math.PI);
    }

    @Override
    protected void entityInit() {
        if( this.shooterUUID != null && this.shooterCache == null ) {
            this.shooterCache = EntityUtils.getEntityByUUID(this.world, this.shooterUUID);
        }
        if( this.targetUUID != null && this.targetCache == null ) {
            this.targetCache = EntityUtils.getEntityByUUID(this.world, this.targetUUID);
        }
    }

    @Override
    public void onUpdate() {
        this.isAirBorne = true;

        if( this.shooterCache != null && this.getDistanceToEntity(this.shooterCache) > this.maxDist ) {
            this.setDead();
            return;
        }

        this.doCollisionCheck();

        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;
        float f2 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

        this.rotationPitch = (float)(Math.atan2(this.motionY, f2) * 180.0D / Math.PI);
        while( this.rotationPitch - this.prevRotationPitch < -180.0F ) {
            this.prevRotationPitch -= 360.0F;
        }

        while( this.rotationPitch - this.prevRotationPitch >= 180.0F ) {
            this.prevRotationPitch += 360.0F;
        }

        while( this.rotationYaw - this.prevRotationYaw < -180.0F ) {
            this.prevRotationYaw -= 360.0F;
        }

        while( this.rotationYaw - this.prevRotationYaw >= 180.0F ) {
            this.prevRotationYaw += 360.0F;
        }

        this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
        this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
        float speed = this.getSpeedMultiplierAir();

        if( this.isInWater() ) {
            for( int i = 0; i < 4; i++ ) {
                float disPos = 0.25F;
                this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * disPos, this.posY - this.motionY * disPos, this.posZ - this.motionZ * disPos, this.motionX, this.motionY, this.motionZ);
            }

            speed = this.getSpeedMultiplierLiquid();
        }

        if( this.isWet() ) {
            this.extinguish();
        }


        this.motionX *= speed;
        this.motionY *= speed;
        this.motionZ *= speed;
        this.motionY -= this.getArc() * 0.1F;
        this.setPosition(this.posX, this.posY, this.posZ);
        this.doBlockCollisions();
    }

    private void doCollisionCheck() {
        Vec3d posVec = new Vec3d(this.posX, this.posY, this.posZ);
        Vec3d futurePosVec = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        RayTraceResult hitObj = this.world.rayTraceBlocks(posVec, futurePosVec, false, true, false);

        posVec = new Vec3d(this.posX, this.posY, this.posZ);
        futurePosVec = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

        if( hitObj != null ) {
            futurePosVec = new Vec3d(hitObj.hitVec.x, hitObj.hitVec.y, hitObj.hitVec.z);
        }

        Entity entity = null;
        AxisAlignedBB checkBB = this.getEntityBoundingBox().offset(this.motionX, this.motionY, this.motionZ).grow(1.0D, 1.0D, 1.0D);

        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, checkBB);
        double minDist = 0.0D;
        float collisionRange;

        for( Entity collidedEntity : list ) {
            if( collidedEntity.canBeCollidedWith() && collidedEntity != this.shooterCache ) {
                collisionRange = 0.1F;
                AxisAlignedBB collisionAABB = collidedEntity.getEntityBoundingBox().grow(collisionRange, collisionRange, collisionRange);
                RayTraceResult interceptObj = collisionAABB.calculateIntercept(posVec, futurePosVec);

                if( interceptObj != null ) {
                    double vecDistance = posVec.distanceTo(interceptObj.hitVec);

                    if( collidedEntity == this.targetCache && (vecDistance < minDist || minDist == 0.0D) ) {
                        entity = collidedEntity;
                        minDist = vecDistance;
                    }
                }
            }
        }

        if( entity != null ) {
            hitObj = new RayTraceResult(entity);
        }

        if( hitObj != null && hitObj.entityHit != null && hitObj.entityHit instanceof EntityPlayer ) {
            EntityPlayer player = (EntityPlayer)hitObj.entityHit;

            if( player.capabilities.disableDamage ) {
                hitObj = null;
            }
        }

        if( hitObj != null ) {
            if( hitObj.entityHit != null ) {
                MutableFloat dmg = new MutableFloat(this.getDamage());

                DamageSource damagesource = this.getProjDamageSource(hitObj.entityHit);

                if( this.isBurning() && !(hitObj.entityHit instanceof EntityEnderman) ) {
                    hitObj.entityHit.setFire(5);
                }

                boolean preHitVelocityChanged = hitObj.entityHit.velocityChanged;
                boolean preHitAirBorne = hitObj.entityHit.isAirBorne;
                double preHitMotionX = hitObj.entityHit.motionX;
                double preHitMotionY = hitObj.entityHit.motionY;
                double preHitMotionZ = hitObj.entityHit.motionZ;
                if( this.onPreHit(hitObj.entityHit, damagesource, dmg) && hitObj.entityHit.attackEntityFrom(damagesource, dmg.floatValue()) ) {
                    hitObj.entityHit.velocityChanged = preHitVelocityChanged;
                    hitObj.entityHit.isAirBorne = preHitAirBorne;
                    hitObj.entityHit.motionX = preHitMotionX;
                    hitObj.entityHit.motionY = preHitMotionY;
                    hitObj.entityHit.motionZ = preHitMotionZ;

                    this.onPostHit(hitObj.entityHit, damagesource);
                    if( hitObj.entityHit instanceof EntityLivingBase ) {
                        EntityLivingBase living = (EntityLivingBase) hitObj.entityHit;

                        if( !this.world.isRemote ) {
                            living.setArrowCountInEntity(living.getArrowCountInEntity() + 1);
                        }

                        double deltaX = this.posX - living.posX;
                        double deltaZ = this.posZ - living.posZ;

                        while( deltaX * deltaX + deltaZ * deltaZ < 0.0001D ) {
                            deltaZ = (Math.random() - Math.random()) * 0.01D;
                            deltaX = (Math.random() - Math.random()) * 0.01D;
                        }

                        this.knockBackEntity(living, deltaX, deltaZ);

                        if( this.shooterCache instanceof EntityLivingBase ) {
                            EnchantmentHelper.applyThornEnchantments(living, this.shooterCache);
                            EnchantmentHelper.applyArthropodEnchantments((EntityLivingBase) this.shooterCache, living);
                        }
                    }
                }
            } else {
                this.onBlockHit(hitObj.getBlockPos());
            }

            this.processHit(hitObj);
        }
    }

    public DamageSource getProjDamageSource(Entity hitEntity) {
        return DamageSource.causeThrownDamage(this, this.shooterCache == null ? this : this.shooterCache);
    }

    public void knockBackEntity(EntityLivingBase living, double deltaX, double deltaZ) {
        if( this.rand.nextDouble() >= living.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).getAttributeValue() ) {
            living.isAirBorne = true;
            double normXZ = MathHelper.sqrt(deltaX * deltaX + deltaZ * deltaZ);
            double kbStrengthXZ = this.getKnockbackStrengthH();
            double kbStrengthY = this.getKnockbackStrengthV();
            living.motionX /= 2.0D;
            living.motionY /= 2.0D;
            living.motionZ /= 2.0D;
            living.motionX -= deltaX / normXZ * kbStrengthXZ;
            living.motionY += kbStrengthY;
            living.motionZ -= deltaZ / normXZ * kbStrengthXZ;

            if( living.motionY > 0.4000000059604645D ) {
                living.motionY = 0.4000000059604645D;
            }
        }
    }

    protected void processHit(@SuppressWarnings("UnusedParameters") RayTraceResult hitObj) {
        this.setPosition(hitObj.hitVec.x, hitObj.hitVec.y, hitObj.hitVec.z);
        this.playSound(this.getRicochetSound(), 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
        this.setDead();
    }

    private void onBlockHit(@SuppressWarnings("UnusedParameters") BlockPos pos) { }

    public abstract float getInitialSpeedMultiplier();

    public abstract float getDamage();

    public abstract float getKnockbackStrengthH();

    public abstract float getKnockbackStrengthV();

    public abstract SoundEvent getRicochetSound();

    public double getScatterValue() {
        return 0.0F;
    }

    public float getSpeedMultiplierAir() {
        return 1.0F;
    }

    public float getSpeedMultiplierLiquid() {
        return 0.8F;
    }

    public boolean onPreHit(Entity e, DamageSource dmgSource, MutableFloat dmg) {
        return !(e instanceof EntityWither && ((EntityWither) e).isArmored() && dmgSource.isProjectile());
    }

    public void onPostHit(Entity e, DamageSource dmgSource) { }

    @Override
    protected void readEntityFromNBT(NBTTagCompound nbt) {
        if( nbt.hasKey("shooter") ) {
            this.shooterUUID = UUID.fromString(nbt.getString("shooter"));
            this.shooterCache = EntityUtils.getEntityByUUID(this.world, this.shooterUUID);
        }

        if( nbt.hasKey("target") ) {
            this.targetUUID = UUID.fromString(nbt.getString("target"));
            this.targetCache = EntityUtils.getEntityByUUID(this.world, this.targetUUID);
        }
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound nbt) {
        if( this.shooterUUID != null ) {
            nbt.setString("shooter", this.shooterUUID.toString());
        }

        if( this.targetUUID != null ) {
            nbt.setString("target", this.targetUUID.toString());
        }
    }

    @Override
    public void setThrowableHeading(double x, double y, double z, float recoil, float randMulti) {
        float vecNormal = MathHelper.sqrt(x * x + y * y + z * z);
        x /= vecNormal;
        y /= vecNormal;
        z /= vecNormal;
        x += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * randMulti;
        y += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * randMulti;
        z += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * randMulti;
        x *= recoil;
        y *= recoil;
        z *= recoil;
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;
        float vecPlaneNormal = MathHelper.sqrt(x * x + z * z);
        this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(x, z) * 180.0D / Math.PI);
        this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(y, vecPlaneNormal) * 180.0D / Math.PI);
    }

    @Override
    public void writeSpawnData(ByteBuf buffer) {
        buffer.writeFloat(this.rotationYaw);
        buffer.writeFloat(this.rotationPitch);
        buffer.writeBoolean(this.shooterCache != null);
        if( this.shooterCache != null ) {
            buffer.writeInt(this.shooterCache.getEntityId());
        }
        buffer.writeBoolean(this.targetCache != null);
        if( this.targetCache != null ) {
            buffer.writeInt(this.targetCache.getEntityId());
        }
    }

    @Override
    public void readSpawnData(ByteBuf buffer) {
        this.rotationYaw = buffer.readFloat();
        this.rotationPitch = buffer.readFloat();

        if( buffer.readBoolean() ) {
            this.shooterCache = this.world.getEntityByID(buffer.readInt());
        }
        if( buffer.readBoolean() ) {
            this.targetCache = this.world.getEntityByID(buffer.readInt());
        }
    }

    @Override
    public void move(MoverType type, double x, double y, double z) {
        super.move(type, x, y, z);
    }

    public abstract float getArc();
}
