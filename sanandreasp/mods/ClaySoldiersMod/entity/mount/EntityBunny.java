/*******************************************************************************************************************
 * Name:      EntityBunny.java
 * Author:    SanAndreasP
 * Copyright: SanAndreasP and SilverChiren
 * License:   Attribution-NonCommercial-ShareAlike 3.0 Unported (http://creativecommons.org/licenses/by-nc-sa/3.0/)
 *******************************************************************************************************************/

package sanandreasp.mods.ClaySoldiersMod.entity.mount;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.src.*;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;

import sanandreasp.mods.ClaySoldiersMod.entity.EntityClayMan;
import sanandreasp.mods.ClaySoldiersMod.entity.projectile.EntityFireball;
import sanandreasp.mods.ClaySoldiersMod.entity.projectile.EntityGravelChunk;
import sanandreasp.mods.ClaySoldiersMod.registry.CSMModRegistry;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityBunny extends EntityCreature implements IMount {
	
	public EntityBunny(World world) {
		super(world);
		this.dataWatcher.addObject(19, (short)0);
		this.dataWatcher.addObject(20, (byte)0); // altSkin
		yOffset = 0.0F;
		stepHeight = 0.1F;
		setSize(0.25F, 0.4F);
		setPosition(posX, posY, posZ);
		renderDistanceWeight = 5D;
	}

	public boolean hasAltSkin() {
		return (this.dataWatcher.getWatchableObjectByte(20) & 1) == 1;
	}
	
	public void setAltSkin(boolean b) {
		byte byt = this.dataWatcher.getWatchableObjectByte(20);
		this.dataWatcher.updateObject(20, (byte)(b ? (byt | 1) : (byt & ~1)));
	}
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(20.0D);
    }
	
	public EntityBunny(World world, double x, double y, double z, int i) {
		this(world);
		this.dataWatcher.updateObject(19, (short)i);
		yOffset = 0.0F;
		stepHeight = 0.1F;
		setSize(0.25F, 0.4F);
		setPosition(x, y, z);
		renderDistanceWeight = 5D;
		worldObj.playSoundAtEntity(this, "step.cloth", 0.8F, ((rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F) * 0.9F);
	}
	
	@Override
	public float getAIMoveSpeed() {
	    return 0.6F;
	}
	
	@Override
	public void onUpdate() {
        super.onUpdate();

        if (gotRider) {
            if (riddenByEntity != null) {
				gotRider = false;
                return;
            }

            List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(0.1D, 0.1D, 0.1D));

            for (int i = 0; i < list.size(); i++) {
                Entity entity = (Entity)list.get(i);

                if (!(entity instanceof EntityClayMan)) {
                    continue;
                }

                EntityLiving entityliving = (EntityLiving)entity;

                if (entityliving.ridingEntity != null || entityliving.riddenByEntity == this) {
                    continue;
                }

                entity.mountEntity(this);
                break;
            }

            gotRider = false;
        }
    }
	
	@Override
	public double getMountedYOffset() {
		return super.getMountedYOffset()-0.3D;
	}
	
	@Override
	public void updateEntityActionState() {
		if (riddenByEntity == null || !(riddenByEntity instanceof EntityClayMan)) {
			super.updateEntityActionState();
		} else {
			EntityClayMan rider = (EntityClayMan)riddenByEntity;
//			isJumping = rider.isJumping() || handleWaterMovement();
			this.isJumping = true;
			moveForward = rider.getMoveForward() * rider.getSpeedMulti();
			moveStrafing = rider.getMoveStrafing() * rider.getSpeedMulti();
			rotationYaw = prevRotationYaw = rider.rotationYaw;
			rotationPitch = prevRotationPitch = rider.rotationPitch;
			rider.renderYawOffset = renderYawOffset;
			riddenByEntity.fallDistance = 0.0F;
			
			if (rider.isDead || rider.getHealth() <= 0) {
				rider.mountEntity(null);
			}
		}
	}
	
	@Override
	public void moveEntityWithHeading(float f, float f1)
    {
		super.moveEntityWithHeading(f, f1);
		double d2 = (posX - prevPosX) * 2.0F;
        double d3 = (posZ - prevPosZ) * 2.0F;
		float f5 = MathHelper.sqrt_double(d2 * d2 + d3 * d3) * 4F;
        if (f5 > 1.0F)
        {
            f5 = 1.0F;
        }

        this.limbSwingAmount += (f5 - this.limbSwingAmount) * 0.4F;
        this.limbSwing += this.limbSwingAmount;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
    {
		super.writeToNBT(nbttagcompound);
		gotRider = (riddenByEntity == null);
		nbttagcompound.setBoolean("GotRider", gotRider);
		nbttagcompound.setBoolean("IsFromNexus", spawnedFromNexus);
		nbttagcompound.setShort("bunnyColor", (short)this.dataWatcher.getWatchableObjectShort(19));
		nbttagcompound.setBoolean("altSkin", this.hasAltSkin());
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
    {
		super.readFromNBT(nbttagcompound);
		gotRider = nbttagcompound.getBoolean("GotRider");
		spawnedFromNexus = nbttagcompound.getBoolean("IsFromNexus");
		this.dataWatcher.updateObject(19, nbttagcompound.getShort("bunnyColor"));
		this.setAltSkin(nbttagcompound.getBoolean("altSkin"));
	}
	
	@Override
	protected String getHurtSound()
    {
		worldObj.playSoundAtEntity(this, "step.cloth", 0.6F, 1.0F / (rand.nextFloat() * 0.2F + 0.9F));
		return "";
    }
	
	@Override
	protected String getDeathSound()
    {
		return "step.gravel";
    }
	
	@Override
	protected void jump()
    {
		motionY = 0.4D;
    }
	
	@Override
	public void mountEntity(Entity e) {
		if (!(e != null && e instanceof EntityMinecart)) {
			super.mountEntity(e);
		}
	}
	
	@Override
	protected boolean canDespawn()
    {
        return false;
    }
	
	@Override
	protected void dropFewItems(boolean flag, int i) {
		Item item1 = CSMModRegistry.bunnyDoll;
		dropItem(item1.itemID, 1, this.dataWatcher.getWatchableObjectShort(19));
	}
	
	protected void dropItem(int itemID, int i, int j) {
		entityDropItem(new ItemStack(itemID, i, j), 0.0F);
	}
	
	@Override
	public EntityItem entityDropItem(ItemStack par1ItemStack, float par2) {
		return spawnedFromNexus ? null : super.entityDropItem(par1ItemStack, par2);
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float i) {
		float origDmg = i;
		Entity e = damagesource.getSourceOfDamage();
		if ((e == null || !(e instanceof EntityClayMan)) && !damagesource.isFireDamage()) {
			i = 100F;
		}
		
		if (riddenByEntity != null && riddenByEntity instanceof EntityClayMan) {
			if (e instanceof EntityGravelChunk) {
				if (((EntityGravelChunk)e).getClayTeam() == ((EntityClayMan)riddenByEntity).getClayTeam())
					return false;
				else
					i = origDmg;
			}
			if (e instanceof EntityFireball) {
				if (((EntityFireball)e).getClayTeam() == ((EntityClayMan)riddenByEntity).getClayTeam())
					return false;
				else
					i = origDmg;
			}
			if (e instanceof EntitySnowball) {
				if (((EntitySnowball)e).getClayTeam() == ((EntityClayMan)riddenByEntity).getClayTeam())
					return false;
				else
					i = origDmg;
			}
		}
		
		if (this.getHealth()-i <= 0 && rand.nextInt(25) == 0 && !(e instanceof EntityPlayer)) {
			this.setHealth(20F);
			return false;
		}
		
		boolean fred = super.attackEntityFrom(damagesource, i);
		if (fred && this.getHealth() <= 0) {
				Item item1 = CSMModRegistry.bunnyDoll;
				for (int j = 0; j < 4; j++) {
					double a = posX + ((rand.nextFloat() - rand.nextFloat()) * 0.125D);
					double b = boundingBox.minY + 0.125D + ((rand.nextFloat() - rand.nextFloat()) * 0.25D);
					double c = posZ + ((rand.nextFloat() - rand.nextFloat()) * 0.125D);
					CSMModRegistry.proxy.showEffect(this.worldObj, this, 3);
				}
				isDead = true;
		}
		return fred;
	}
	
	@Override
	public void knockBack(Entity entity, float i, double d, double d1)
    {
        super.knockBack(entity, i, d, d1);
		if (entity != null && entity instanceof EntityClayMan) {
			motionX *= 0.6D;
			motionY *= 0.75D;
			motionZ *= 0.6D;
		}
    }
	
	@Override
	public boolean isOnLadder() {
		return false;
    }
	
	@Override
	public boolean interact(EntityPlayer e) {
		return false;
	}
	
	@Override
	public EntityBunny setSpawnedFromNexus() {
		spawnedFromNexus = true;
		return this;
	}
	
	public boolean gotRider, spawnedFromNexus = false;

	@Override
	public int getType() {
		return this.dataWatcher.getWatchableObjectShort(19);
	}
}