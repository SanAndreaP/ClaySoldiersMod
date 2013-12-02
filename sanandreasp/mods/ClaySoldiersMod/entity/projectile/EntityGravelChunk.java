/*******************************************************************************************************************
 * Name:      EntityGravelChunk.java
 * Author:    SanAndreasP
 * Copyright: SanAndreasP and SilverChiren
 * License:   Attribution-NonCommercial-ShareAlike 3.0 Unported (http://creativecommons.org/licenses/by-nc-sa/3.0/)
 *******************************************************************************************************************/

package sanandreasp.mods.ClaySoldiersMod.entity.projectile;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.src.*;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.List;

import cpw.mods.fml.common.FMLCommonHandler;

import paulscode.sound.Vector3D;
import sanandreasp.mods.ClaySoldiersMod.entity.EntityClayMan;
import sanandreasp.mods.ClaySoldiersMod.entity.mount.EntitySnowball;
import sanandreasp.mods.ClaySoldiersMod.registry.CSMModRegistry;

public class EntityGravelChunk extends Entity
{

    public EntityGravelChunk(World world)
    {
        super(world);
        xTile = -1;
        yTile = -1;
        zTile = -1;
        inTile = 0;
        field_28019_h = 0;
        inGround = false;
        doesArrowBelongToPlayer = false;
        arrowShake = 0;
        ticksInAir = 0;
        setSize(0.1F, 0.1F);
		renderDistanceWeight = 5D;
		setPosition(posX, posY, posZ);
		this.dataWatcher.addObject(5, (short) 0); // clayTeam
		this.dataWatcher.addObject(6, (byte) 0); // followTarget
    }

    public EntityGravelChunk(World world, double d, double d1, double d2, int i)
    {
        this(world);
        setPosition(d, d1, d2);
        yOffset = 0.0F;
		setClayTeam((short) i);
    }

    public EntityGravelChunk(World world, EntityLivingBase entityliving, int i)
    {
        this(world);
        owner = entityliving;
        doesArrowBelongToPlayer = entityliving instanceof EntityPlayer;
        setLocationAndAngles(entityliving.posX, entityliving.posY + entityliving.getEyeHeight(), entityliving.posZ, entityliving.rotationYaw, entityliving.rotationPitch);
        posX -= MathHelper.cos((rotationYaw / 180F) * 3.141593F) * 0.16F;
        posY -= 0.10000000149011612D;
        posZ -= MathHelper.sin((rotationYaw / 180F) * 3.141593F) * 0.16F;
        yOffset = 0.0F;
        motionX = -MathHelper.sin((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F);
        motionZ = MathHelper.cos((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F);
        motionY = -MathHelper.sin((rotationPitch / 180F) * 3.141593F);
        setArrowHeading(motionX, motionY, motionZ, 1.5F, 1.0F);
		setClayTeam((short) i);
    }

    @Override
	protected void entityInit()
    {
    }

    public void setArrowHeading(double d, double d1, double d2, float f, 
            float f1)
    {
        float f2 = MathHelper.sqrt_double(d * d + d1 * d1 + d2 * d2);
        d /= f2;
        d1 /= f2;
        d2 /= f2;
        d += rand.nextGaussian() * 0.0074999998323619366D * f1;
        d1 += rand.nextGaussian() * 0.0074999998323619366D * f1;
        d2 += rand.nextGaussian() * 0.0074999998323619366D * f1;
        d *= f;
        d1 *= f;
        d2 *= f;
        motionX = d;
        motionY = d1;
        motionZ = d2;
        float f3 = MathHelper.sqrt_double(d * d + d2 * d2);
        prevRotationYaw = rotationYaw = (float)((Math.atan2(d, d2) * 180D) / 3.1415927410125732D);
        prevRotationPitch = rotationPitch = (float)((Math.atan2(d1, f3) * 180D) / 3.1415927410125732D);
        ticksInGround = 0;
    }

    @Override
	public void setVelocity(double d, double d1, double d2)
    {
        motionX = d;
        motionY = d1;
        motionZ = d2;
        if (prevRotationPitch == 0.0F && prevRotationYaw == 0.0F)
        {
            float f = MathHelper.sqrt_double(d * d + d2 * d2);
            prevRotationYaw = rotationYaw = (float)((Math.atan2(d, d2) * 180D) / 3.1415927410125732D);
            prevRotationPitch = rotationPitch = (float)((Math.atan2(d1, f) * 180D) / 3.1415927410125732D);
            prevRotationPitch = rotationPitch;
            prevRotationYaw = rotationYaw;
            setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
            ticksInGround = 0;
        }
    }

    @Override
	public void onUpdate()
    {
		entityAge ++;
		if (entityAge > 99999) {
			entityAge = 0;
		}
	
        super.onUpdate();
        
        if (!(this instanceof EntitySnowball)) {
	        if (prevRotationPitch == 0.0F && prevRotationYaw == 0.0F)
	        {
	            float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
	            prevRotationYaw = rotationYaw = (float)((Math.atan2(motionX, motionZ) * 180D) / 3.1415927410125732D);
	            prevRotationPitch = rotationPitch = (float)((Math.atan2(motionY, f) * 180D) / 3.1415927410125732D);
	        }
	        int i = worldObj.getBlockId(xTile, yTile, zTile);
	        if (i > 0)
	        {
	            Block.blocksList[i].setBlockBoundsBasedOnState(worldObj, xTile, yTile, zTile);
	            AxisAlignedBB axisalignedbb = Block.blocksList[i].getCollisionBoundingBoxFromPool(worldObj, xTile, yTile, zTile);
	            if (axisalignedbb != null && axisalignedbb.isVecInside(Vec3.createVectorHelper(posX, posY, posZ)))
	            {
	                inGround = true;
	            }
	        }
	        if (arrowShake > 0)
	        {
	            arrowShake--;
	        }
	        if (inGround)
	        {
	            int j = worldObj.getBlockId(xTile, yTile, zTile);
	            int k = worldObj.getBlockMetadata(xTile, yTile, zTile);
	            if (j != inTile || k != field_28019_h)
	            {
	                inGround = false;
	                motionX *= rand.nextFloat() * 0.2F;
	                motionY *= rand.nextFloat() * 0.2F;
	                motionZ *= rand.nextFloat() * 0.2F;
	                ticksInGround = 0;
	                ticksInAir = 0;
	                return;
	            }
	            ticksInGround++;
	            if (ticksInGround == 1200)
	            {
	                setDead();
	            }
	            return;
	        }
	        ticksInAir++;
	        
	        if (canFollow())
	        {
	            if (target != null && !target.isDead && target.getHealth() > 0)
	            {
	                double d = (target.boundingBox.minX + (target.boundingBox.maxX - target.boundingBox.minX) / 2D) - posX;
	                double d1 = (target.boundingBox.minY + (target.boundingBox.maxY - target.boundingBox.minY) / 2D) - posY;
	                double d2 = (target.boundingBox.minZ + (target.boundingBox.maxZ - target.boundingBox.minZ) / 2D) - posZ;
	                setArrowHeading(d, d1, d2, 1.5F, 0.0F);
	            }
	        }
	        
	        Vec3 vec3d = Vec3.createVectorHelper(posX, posY, posZ);
	        Vec3 vec3d1 = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);
	        MovingObjectPosition movingobjectposition = worldObj.rayTraceBlocks_do_do(vec3d, vec3d1, false, true);
	        vec3d = Vec3.createVectorHelper(posX, posY, posZ);
	        vec3d1 = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);
	        if (movingobjectposition != null)
	        {
	            vec3d1 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
	        }
	        Entity entity = null;
	        List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
	        double d = 0.0D;
	        for (int l = 0; l < list.size(); l++)
	        {
	            Entity entity1 = (Entity)list.get(l);
	            if (!entity1.canBeCollidedWith() || entity1 == owner && ticksInAir < 5)
	            {
	                continue;
	            }
	            float f4 = 0.3F;
	            AxisAlignedBB axisalignedbb1 = entity1.boundingBox.expand(f4, f4, f4);
	            MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept(vec3d, vec3d1);
	            if (movingobjectposition1 == null)
	            {
	                continue;
	            }
	            double d1 = vec3d.distanceTo(movingobjectposition1.hitVec);
	            if (d1 < d || d == 0.0D)
	            {
	                entity = entity1;
	                d = d1;
	            }
	        }
	
	        if (entity != null)
	        {
	            movingobjectposition = new MovingObjectPosition(entity);
	        }
	        if (movingobjectposition != null)
	        {
	            if (movingobjectposition.entityHit != null)
	            {
					int attackU = 4;
					
					if (!(movingobjectposition.entityHit instanceof EntityClayMan || movingobjectposition.entityHit instanceof EntityMob)) {
						attackU = 0;
					}
				
	                if (movingobjectposition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, owner), attackU))
	                {
	                    setDead();
	                }
	            } else
	            {
	                xTile = movingobjectposition.blockX;
	                yTile = movingobjectposition.blockY;
	                zTile = movingobjectposition.blockZ;
	                inTile = worldObj.getBlockId(xTile, yTile, zTile);
	                field_28019_h = worldObj.getBlockMetadata(xTile, yTile, zTile);
	                motionX = (float)(movingobjectposition.hitVec.xCoord - posX);
	                motionY = (float)(movingobjectposition.hitVec.yCoord - posY);
	                motionZ = (float)(movingobjectposition.hitVec.zCoord - posZ);
	                float f1 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
	                posX -= (motionX / f1) * 0.05000000074505806D;
	                posY -= (motionY / f1) * 0.05000000074505806D;
	                posZ -= (motionZ / f1) * 0.05000000074505806D;
	                inGround = true;
	                arrowShake = 7;
	            }
	        }
	        posX += motionX;
	        posY += motionY;
	        posZ += motionZ;
	        float f2 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
	        rotationYaw = (float)((Math.atan2(motionX, motionZ) * 180D) / 3.1415927410125732D);
	        for (rotationPitch = (float)((Math.atan2(motionY, f2) * 180D) / 3.1415927410125732D); rotationPitch - prevRotationPitch < -180F; prevRotationPitch -= 360F) { }
	        for (; rotationPitch - prevRotationPitch >= 180F; prevRotationPitch += 360F) { }
	        for (; rotationYaw - prevRotationYaw < -180F; prevRotationYaw -= 360F) { }
	        for (; rotationYaw - prevRotationYaw >= 180F; prevRotationYaw += 360F) { }
	        rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
	        rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
	        float f3 = 0.99F;
	        float f5 = 0.03F;
	        if (isInWater())
	        {
	            for (int i1 = 0; i1 < 4; i1++)
	            {
	                float f6 = 0.25F;
	                worldObj.spawnParticle("bubble", posX - motionX * f6, posY - motionY * f6, posZ - motionZ * f6, motionX, motionY, motionZ);
	            }
	
	            f3 = 0.8F;
	        }
	        motionX *= f3;
	        motionY *= f3;
	        motionZ *= f3;
	        motionY -= f5;
	        setPosition(posX, posY, posZ);
			
			if (ticksInGround > 0 || inGround) {
				isDead = true;
			}
			
			if (isDead) {
				double a = posX + ((rand.nextFloat() - rand.nextFloat()) * 0.125D);
				double b = boundingBox.minY + 0.125D + ((rand.nextFloat() - rand.nextFloat()) * 0.25D);
				double c = posZ + ((rand.nextFloat() - rand.nextFloat()) * 0.125D);
				CSMModRegistry.proxy.showEffect(this.worldObj, this, 12);
//				if (FMLCommonHandler.instance().getSide().isClient())
//					CSM_ModRegistry.proxy.showEffect((new EntityDiggingFX(CSM_ModRegistry.proxy.getClientWorld(), a, b, c, 0.0D, 0.0D, 0.0D, Block.gravel, 0, 0)));
				worldObj.playSoundAtEntity(this, "step.gravel", 0.6F, 1.0F / (rand.nextFloat() * 0.2F + 0.9F));
			}
    	}
    }

    @Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setShort("xTile", (short)xTile);
        nbttagcompound.setShort("yTile", (short)yTile);
        nbttagcompound.setShort("zTile", (short)zTile);
        nbttagcompound.setByte("inTile", (byte)inTile);
        nbttagcompound.setByte("inData", (byte)field_28019_h);
        nbttagcompound.setByte("shake", (byte)arrowShake);
        nbttagcompound.setByte("inGround", (byte)(inGround ? 1 : 0));
        nbttagcompound.setBoolean("player", doesArrowBelongToPlayer);
		nbttagcompound.setByte("ClayTeam", (byte)getClayTeam());
    }

    @Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        xTile = nbttagcompound.getShort("xTile");
        yTile = nbttagcompound.getShort("yTile");
        zTile = nbttagcompound.getShort("zTile");
        inTile = nbttagcompound.getByte("inTile") & 0xff;
        field_28019_h = nbttagcompound.getByte("inData") & 0xff;
        arrowShake = nbttagcompound.getByte("shake") & 0xff;
        inGround = nbttagcompound.getByte("inGround") == 1;
        doesArrowBelongToPlayer = nbttagcompound.getBoolean("player");
		setClayTeam((short)(nbttagcompound.getByte("ClayTeam") & 0xff));
    }

    @Override
	public void onCollideWithPlayer(EntityPlayer entityplayer)
    {
        return;
    }

    @Override
	public float getShadowSize()
    {
        return 0.0F;
    }

    protected int xTile;
    protected int yTile;
    protected int zTile;
    protected int inTile;
    protected int field_28019_h;
    protected boolean inGround;
    public boolean doesArrowBelongToPlayer;
    public int arrowShake;
    public EntityLivingBase owner;
    public EntityLiving target;
    protected int ticksInGround;
    protected int ticksInAir;
	
    private void setClayTeam(short team) {
    	this.dataWatcher.updateObject(5, team);
	}
    public int getClayTeam() {
    	return this.dataWatcher.getWatchableObjectShort(5);
    }
    
    public void setCanFollow(boolean flag) {
    	this.dataWatcher.updateObject(6, (byte)(flag ? 1 : 0));
    }
    public boolean canFollow() {
    	return this.dataWatcher.getWatchableObjectByte(6) != 0;
    }
    
	public int entityAge;
}
