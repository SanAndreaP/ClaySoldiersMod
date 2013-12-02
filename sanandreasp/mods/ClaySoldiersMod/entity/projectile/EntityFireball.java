/*******************************************************************************************************************
 * Name:      EntityFireball.java
 * Author:    SanAndreasP
 * Copyright: SanAndreasP and SilverChiren
 * License:   Attribution-NonCommercial-ShareAlike 3.0 Unported (http://creativecommons.org/licenses/by-nc-sa/3.0/)
 *******************************************************************************************************************/

package sanandreasp.mods.ClaySoldiersMod.entity.projectile;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.src.*;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.List;

import sanandreasp.mods.ClaySoldiersMod.entity.EntityClayMan;
import sanandreasp.mods.ClaySoldiersMod.registry.CSMModRegistry;
import cpw.mods.fml.common.FMLCommonHandler;

public class EntityFireball extends EntityGravelChunk
{

    public EntityFireball(World world)
    {
        super(world);
    }

    public EntityFireball(World world, double d, double d1, double d2, int i)
    {
        super(world, d, d1, d2, i);
    }

    public EntityFireball(World world, EntityLiving entityliving, int i)
    {
        super(world, entityliving, i);
    }

    @Override
	public void onUpdate()
    {	
        super.onUpdate();
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
				int attackU = 0;
				
				if (!(movingobjectposition.entityHit instanceof EntityClayMan || movingobjectposition.entityHit instanceof EntityMob)) {
					attackU = 2;
				} else {
					if (movingobjectposition.entityHit instanceof EntityClayMan) {
						if (((EntityClayMan)movingobjectposition.entityHit).getClayTeam() == getClayTeam()) {
		                    setDead();
							return;
						}
					}
				}
			
                if (movingobjectposition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, owner), attackU))
                {
                	int fire = 4;
                	if (owner != null && owner instanceof EntityClayMan) {
//                		if (((EntityClayMan)owner).hasCoal)
//                			fire = 8;
                	}
                	
                	movingobjectposition.entityHit.setFire(fire);
                    worldObj.playAuxSFXAtEntity(null, 1009, (int)movingobjectposition.entityHit.posX, (int)movingobjectposition.entityHit.posY, (int)movingobjectposition.entityHit.posZ, 0);
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
//			if (FMLCommonHandler.instance().getSide().isClient())
//				CSM_ModRegistry.proxy.showEffect((new EntityDiggingFX(CSM_ModRegistry.proxy.getClientWorld(), a, b, c, 0.0D, 0.0D, 0.0D, Block.gravel, 0, 0)));
			worldObj.playSoundAtEntity(this, "step.gravel", 0.6F, 1.0F / (rand.nextFloat() * 0.2F + 0.9F));
		}
    }
}
