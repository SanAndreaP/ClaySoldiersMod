/*******************************************************************************************************************
 * Name:      EntityGecko.java
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
import net.minecraft.world.World;

import java.util.List;

import sanandreasp.mods.ClaySoldiersMod.entity.EntityClayMan;
import sanandreasp.mods.ClaySoldiersMod.entity.projectile.EntityFireball;
import sanandreasp.mods.ClaySoldiersMod.entity.projectile.EntityGravelChunk;
import sanandreasp.mods.ClaySoldiersMod.registry.CSMModRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityGecko extends EntityCreature implements IMount {
	protected float moveSpeed;

	public EntityGecko(World world) {
		super(world);
		this.dataWatcher.addObject(19, (short)0);
		this.dataWatcher.addObject(18, (short)0);
//		this.dataWatcher.addObject(20, (byte)0); // altSkin
		postInit = true;
		yOffset = 0.0F;
		stepHeight = 0.1F;
		moveSpeed = 1F;
		setSize(0.25F, 0.4F);
		setPosition(posX, posY, posZ);
		renderDistanceWeight = 5D;
        this.dataWatcher.addObject(16, new Byte((byte)0));
	}

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(30.0D);
    }

//	public boolean hasAltSkin() {
//		return (this.dataWatcher.getWatchableObjectByte(20) & 1) == 1;
//	}

//	public void setAltSkin(boolean b) {
//		byte byt = this.dataWatcher.getWatchableObjectByte(20);
//		this.dataWatcher.updateObject(20, (byte)(b ? (byt | 1) : (byt & ~1)));
//	}

	public float prevSpeed = 0.0F;

	public EntityGecko(World world, double x, double y, double z, int i, int j) {
		this(world);
		setType(i, j);
		setPosition(x, y, z);
		worldObj.playSoundAtEntity(this, "step.wood", 0.8F, ((rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F) * 0.9F);
	}

	private void setType(int i, int j) {
		this.dataWatcher.updateObject(19, (short)i);
		this.dataWatcher.updateObject(18, (short)j);
	}

	public int getType(int ind) {
		if (ind == 0) {
            return this.dataWatcher.getWatchableObjectShort(19);
        }
		return this.dataWatcher.getWatchableObjectShort(18);
	}

    private static int[] getTypes(int i) {
    	int j[] = new int[2];
    	if (i > 11 && i < 16) {
    		j[0] = 3;
    		j[1] = i - 12;
    	} else if (i > 7 && i < 12) {
    		j[0] = 2;
    		j[1] = i - 8;
    	} else if (i > 3 && i < 8) {
    		j[0] = 1;
    		j[1] = i - 4;
    	} else {
    		j[0] = 0;
    		j[1] = i;
    	}

    	return j;
    }

	public EntityGecko(World world, double x, double y, double z, int i) {
    	this(world, x, y, z, getTypes(i)[0],  getTypes(i)[1]);
	}

//	private String getTypeName(int i) {
//		switch(i) {
//			case 1: return "Birch";
//			case 2: return "Pine";
//			case 3: return "Jung";
//			default: return "Oak";
//		}
//	}

    public boolean isBesideClimbableBlock()
    {
        return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
    }

    public void setBesideClimbableBlock(boolean par1)
    {
        byte var2 = this.dataWatcher.getWatchableObjectByte(16);

        if (par1)
        {
            var2 = (byte)(var2 | 1);
        }
        else
        {
            var2 &= -2;
        }

        this.dataWatcher.updateObject(16, Byte.valueOf(var2));
    }

//	public String geckoTexture(int i, int j) {
//		String epona = "/sanandreasp/mods/ClaySoldiersMod/claymans/gecko/gecko";
//			epona += getTypeName(i) + getTypeName(j);
//		return epona + ".png";
//	}

	@Override
	public void onUpdate() {
        super.onUpdate();

        if (!this.worldObj.isRemote)
        {
            this.setBesideClimbableBlock(this.isCollidedHorizontally);
        }

        if (this.onGround && !this.isCollidedHorizontally) {
        	maxHeightCnt = 0;
        } else if (this.isCollidedHorizontally) {
        	maxHeightCnt++;
        }

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
			isJumping = rider.isJumping() || handleWaterMovement();
			this.moveSpeed = rider.getSpeedMulti();
//			moveForward = rider.getMoveForward() * (rider.getSpeedMulti()) * moveSpeed;
//			moveStrafing = rider.getMoveStrafing() * (rider.getSpeedMulti()) * moveSpeed;
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
	public float getAIMoveSpeed() {
	    return 0.5F * this.moveSpeed;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
    {
		super.writeToNBT(nbttagcompound);
		gotRider = (riddenByEntity == null);
		nbttagcompound.setBoolean("GotRider", gotRider);
		nbttagcompound.setBoolean("IsFromNexus", spawnedFromNexus);
		nbttagcompound.setShort("Type1", (short)getType(0));
		nbttagcompound.setShort("Type2", (short)getType(1));
		nbttagcompound.setInteger("maxHeightCnt", maxHeightCnt);
//		nbttagcompound.setBoolean("altSkin", this.hasAltSkin());
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
    {
		super.readFromNBT(nbttagcompound);
		gotRider = nbttagcompound.getBoolean("GotRider");
		spawnedFromNexus = nbttagcompound.getBoolean("IsFromNexus");
		setType(nbttagcompound.getShort("Type1"), nbttagcompound.getShort("Type2"));
		if (nbttagcompound.hasKey("maxHeightCnt"))
			maxHeightCnt = nbttagcompound.getInteger("maxHeightCnt");
//		texture = geckoTexture(getType(0), getType(1));
//		this.setAltSkin(nbttagcompound.getBoolean("altSkin"));
	}

//	@Override
//	@SideOnly(Side.CLIENT)
//	public String getTexture() {
//		return geckoTexture(getType(0), getType(1));
//	}

	@Override
	protected String getHurtSound()
    {
		worldObj.playSoundAtEntity(this, "step.wood", 0.6F, 1.0F / (rand.nextFloat() * 0.2F + 0.9F));
		return "";
    }

	@Override
	protected String getDeathSound()
    {
		return "step.wood";
    }

	@Override
	protected void jump() { }

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
		Item item1 = CSMModRegistry.geckoDoll;
		int dmg = 0;
		for (int j = 0; j <= getType(0); j++) {
			if (j == 0)
				dmg += getType(1);
			else
				dmg += 4;
		}

		dropItem(item1.itemID, 1, dmg);
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
			i = 100;
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

		if (e != null && (e instanceof EntityClayMan)) {
//			EntityClayMan danz = ((EntityClayMan)e);
//			if (danz.getDataWatcherShort("stickPoints") <= 0 ||
//			danz.getDataWatcherShort("rodPoints") <= 0 ||
//			danz.getDataWatcherShort("shearPointsA") <= 0 ||
//			danz.getDataWatcherShort("shearPointsB") <= 0) {
//				moveSpeed = 2F;
//			} else {
//				moveSpeed = 1F;
//			}
		}

		boolean fred = super.attackEntityFrom(damagesource, i);
		if (fred && this.getHealth() <= 0) {
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
	public boolean isOnLadder()
    {
        return this.isBesideClimbableBlock() && maxHeightCnt <= 27;
    }

	@Override
	public boolean interact(EntityPlayer e) {
		return false;
	}

	@Override
	public EntityGecko setSpawnedFromNexus() {
		spawnedFromNexus = true;
		return this;
	}

//	@Override
//	public int getMaxHealth() {
//		return 30;
//	}

	public boolean gotRider, spawnedFromNexus = false;
	public int maxHeightCnt = 0;
	public boolean postInit;

	@Override
	public int getType() {
		return getType(0) + getType(1);
	}
}
