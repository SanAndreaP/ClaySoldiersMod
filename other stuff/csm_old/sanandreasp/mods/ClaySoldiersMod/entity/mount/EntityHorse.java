/*******************************************************************************************************************
 * Name:      EntityHorseMount.java
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
import net.minecraft.entity.passive.IAnimals;
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
import sanandreasp.mods.ClaySoldiersMod.registry.Textures;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityHorse extends EntityCreature implements IMount {
	protected float moveSpeed;

	public EntityHorse(World world) {
		super(world);
		this.dataWatcher.addObject(19, (short)0); // horseType
		this.dataWatcher.addObject(18, (byte)0); // isNightmare
		this.dataWatcher.addObject(20, (int)0); // altSkin
		postInit = true;
//		health = 30;
		yOffset = 0.0F;
		stepHeight = 0.1F;
		moveSpeed = 0.6F;
		setSize(0.25F, 0.4F);
		setPosition(posX, posY, posZ);
//		texture = dirtHorseTexture(0);
		renderDistanceWeight = 5D;
	}

	@Override
	public float getAIMoveSpeed() {
	    return moveSpeed;
	}

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(30.0D);
    }

    public int getAltTex() {
        return this.dataWatcher.getWatchableObjectInt(20);
    }

    public void setAltTex(int index) {
        this.dataWatcher.updateObject(20, index);
    }

    public boolean isNightmare() {
        return this.dataWatcher.getWatchableObjectByte(18) != 0;
    }

    public void setNightmare(boolean b) {
        this.dataWatcher.updateObject(18, (byte)(b ? 1 : 0));
    }

	public EntityHorse(World world, double x, double y, double z, int i) {
		this(world);
		this.dataWatcher.updateObject(19, (short)i);
		setPosition(x, y, z);
		if (rand.nextInt(8192) == 0)
		{
			this.dataWatcher.updateObject(18, (byte)1);
		}
//		texture = dirtHorseTexture(i);
		setHorseSpecs(i);
		renderDistanceWeight = 5D;
		worldObj.playSoundAtEntity(this, "step.gravel", 0.8F, ((rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F) * 0.9F);

        this.setAltTex(rand.nextInt(3) != 0 ? 0 : rand.nextInt(Textures.HORSE[i].length));
//		if (rand.nextInt(4) == 0 ) {
//			setAltSkin1(true);
//		}
//		if (rand.nextInt(5) == 0 ) {
//			setAltSkin2(true);
//		}
	}

//	public String dirtHorseTexture(int i) {
//		String epona = "/sanandreasp/mods/ClaySoldiersMod/claymans/horse/horse";
//		if (this.dataWatcher.getWatchableObjectByte(18) > 0) {
//			epona = epona + "Nightmare";
//		} else if (this.dataWatcher.getWatchableObjectByte(18) > 0 && hasAltSkin2()) {
//			epona = epona + "Nightmare2";
//		} else if (i == 0 && hasAltSkin1() && hasAltSkin2()) {
//			epona = epona + "Dirt3";
//		} else if (i == 0 && hasAltSkin2()) {
//			epona = epona + "Dirt4";
//		} else if (i == 0 && hasAltSkin1()) {
//			epona = epona + "Dirt2";
//		} else if (i == 0) {
//			epona = epona + "Dirt";
//		} else if (i == 1) {
//			epona = epona + "Sand";
//		} else if (i == 2 && hasAltSkin2()) {
//			epona = epona + "Gravel2";
//		} else if (i == 2) {
//			epona = epona + "Gravel";
//		} else if (i == 3) {
//			epona = epona + "Snow";
//		} else if (i == 4 && hasAltSkin1()) {
//			epona = epona + "Grass2";
//		} else if (i == 4) {
//			epona = epona + "Grass";
//		} else if (i == 5) {
//			epona = epona + "Lapis";
//		} else if (i == 6) {
//			epona = epona + "Clay";
//		} else if (i == 7 && hasAltSkin1() && hasAltSkin2()) {
//			epona = epona + "Carrot2";
//		} else if (i == 7) {
//			epona = epona + "Carrot";
//		} else if (i == 8) {
//			epona = epona + "Soul";
//		} else if (i == 9) {
//			epona = epona + "Cake";
//		}
//		return epona + ".png";
//	}
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public String getTexture() {
//		return dirtHorseTexture(this.dataWatcher.getWatchableObjectShort(19));
//	}

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
	public void updateEntityActionState() {
		if (riddenByEntity == null || !(riddenByEntity instanceof EntityClayMan)) {
			super.updateEntityActionState();
		} else {
			EntityClayMan rider = (EntityClayMan)riddenByEntity;
			isJumping = rider.isJumping() || handleWaterMovement();
			moveForward = rider.getMoveForward() * (rider.getSpeedMulti());
			moveStrafing = rider.getMoveStrafing() * (rider.getSpeedMulti());
			rotationYaw = prevRotationYaw = rider.rotationYaw;
			rotationPitch = prevRotationPitch = rider.rotationPitch;
			rider.renderYawOffset = renderYawOffset;
			riddenByEntity.fallDistance = 0.0F;

			if (rider.isDead || rider.getHealth() <= 0) {
				rider.mountEntity(null);
			}
		}
	}

    private void updateHealth(float f) {
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(f);
        this.setHealth(f);
    }

	public void setHorseSpecs(int i) {
		if (this.isNightmare()) {
		    updateHealth(50);
			moveSpeed = 1.2f;
		} else if (i == 0) {
		    updateHealth(35);
			moveSpeed = 0.6f;
		} else if (i == 1) {
		    updateHealth(30);
			moveSpeed = 0.7f;
		} else if (i == 2) {
		    updateHealth(45);
			moveSpeed = 0.4f;
		} else if (i == 3) {
		    updateHealth(40);
			moveSpeed = 0.5f;
		} else if (i == 4) {
		    updateHealth(20);
			moveSpeed = 0.9f;
		} else if (i == 5) {
		    updateHealth(35);
			moveSpeed = 0.9f;
		} else if (i == 6) {
		    updateHealth(35);
			moveSpeed = 0.6f;
		} else if (i == 7) {
		    updateHealth(35);
			moveSpeed = 0.9f;
		} else if (i == 8) {
		    updateHealth(35);
			moveSpeed = 0.8f;
		} else if (i == 9) {
		    updateHealth(30);
			moveSpeed = 1.1f;
		}
	}

//	public boolean hasAltSkin1() {
//		return (this.dataWatcher.getWatchableObjectByte(20) & 1) == 1;
//	}
//	public boolean hasAltSkin2() {
//		return (this.dataWatcher.getWatchableObjectByte(20) & 2) == 2;
//	}
//	public void setAltSkin1(boolean b) {
//		byte byt = this.dataWatcher.getWatchableObjectByte(20);
//		this.dataWatcher.updateObject(20, (byte) (b ? (byt | 1) : (byt & ~1)));
//	}
//	public void setAltSkin2(boolean b) {
//		byte byt = this.dataWatcher.getWatchableObjectByte(20);
//		this.dataWatcher.updateObject(20, (byte) (b ? (byt | 2) : (byt & ~2)));
//	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
    {
		super.writeToNBT(nbttagcompound);
		gotRider = (riddenByEntity == null);
		nbttagcompound.setBoolean("IsFromNexus", spawnedFromNexus);
		nbttagcompound.setBoolean("GotRider", gotRider);
		nbttagcompound.setBoolean("Nightmare", this.isNightmare());
		nbttagcompound.setShort("HorseType", (short)this.getType());
		nbttagcompound.setInteger("AltSkin", this.getAltTex());
//		nbttagcompound.setBoolean("altSkin1", this.hasAltSkin1());
//		nbttagcompound.setBoolean("altSkin2", this.hasAltSkin2());
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
    {
		super.readFromNBT(nbttagcompound);
		gotRider = nbttagcompound.getBoolean("GotRider");
		this.setNightmare(nbttagcompound.getBoolean("Nightmare"));
//		if (nbttagcompound.hasKey("HorseType"))
		this.dataWatcher.updateObject(19, nbttagcompound.getShort("HorseType"));
//		texture = dirtHorseTexture(this.dataWatcher.getWatchableObjectShort(19));
		spawnedFromNexus = nbttagcompound.getBoolean("IsFromNexus");
		setHorseSpecs(this.getType());
		this.dataWatcher.updateObject(20, nbttagcompound.getInteger("AltSkin"));
//		this.setAltSkin1(nbttagcompound.getBoolean("altSkin1"));
//		this.setAltSkin2(nbttagcompound.getBoolean("altSkin2"));
	}

	@Override
	protected String getHurtSound()
    {
		worldObj.playSoundAtEntity(this, "step.gravel", 0.6F, 1.0F / (rand.nextFloat() * 0.2F + 0.9F));
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
		Item item1 = CSMModRegistry.horseDoll;
		dropItem(item1.itemID, 1, this.dataWatcher.getWatchableObjectShort(19));
	}

	protected void dropItem(int itemID, int i, int j) {
		entityDropItem(new ItemStack(itemID, i, j), 0.0F);
	}

	@Override
	public EntityItem entityDropItem(ItemStack par1ItemStack, float par2) {
		return spawnedFromNexus || specDeath ? null : super.entityDropItem(par1ItemStack, par2);
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float i) {
	    float origDmg = i;

		boolean hasSpec = rand.nextInt(16) == 0;

		if (damagesource.isFireDamage() && this.dataWatcher.getWatchableObjectByte(18) <= 0 && hasSpec)
			specDeath = true;
		else
			specDeath = false;

		Entity e = damagesource.getSourceOfDamage();
		if ((e == null || !(e instanceof EntityClayMan)) && !damagesource.isFireDamage()) {
			i = 100;
		} if (damagesource.isFireDamage() && this.dataWatcher.getWatchableObjectByte(18) > 0) {
			i = 0;
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

		boolean fred = super.attackEntityFrom(damagesource, i);
		if (fred && this.getHealth() <= 0) {
//				Item item1 = CSM_ModRegistry.horseDoll;
				for (int j = 0; j < 4; j++) {
					double a = posX + ((rand.nextFloat() - rand.nextFloat()) * 0.125D);
					double b = boundingBox.minY + 0.125D + ((rand.nextFloat() - rand.nextFloat()) * 0.25D);
					double c = posZ + ((rand.nextFloat() - rand.nextFloat()) * 0.125D);
					CSMModRegistry.proxy.showEffect(this.worldObj, this, 13);
//					if (FMLCommonHandler.instance().getSide().isClient())
//						CSM_ModRegistry.proxy.showEffect((new EntityDiggingFX(CSM_ModRegistry.proxy.getClientWorld(), a, b, c, 0.0D, 0.0D, 0.0D, Block.dirt, 0, 0)));
				}
				if (damagesource.isFireDamage() && this.dataWatcher.getWatchableObjectByte(18) <= 0 && this.getHealth() <= 2 && hasSpec) {
					EntityHorse entity = new EntityHorse(worldObj, posX, posY, posZ, this.getType());
					entity.setNightmare(true);
					entity.setAltTex(rand.nextInt(3) != 0 ? 0 : rand.nextInt(Textures.HORSE_NIGHTMARE.length));
//					entity.texture = entity.dirtHorseTexture(entity.dataWatcher.getWatchableObjectShort(19));
					entity.setHorseSpecs(entity.getType());
					worldObj.spawnEntityInWorld(entity);
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
	public boolean canBreatheUnderwater()
	{
		short horseType = this.dataWatcher.getWatchableObjectShort(19);
		if ((horseType == 5 || horseType == 6 || horseType == 7) && this.dataWatcher.getWatchableObjectByte(18) <= 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean interact(EntityPlayer e) {
		return false;
	}

	@Override
	public EntityHorse setSpawnedFromNexus() {
		spawnedFromNexus = true;
		return this;
	}

//	@Override
//	public int getMaxHealth() {
//		if (!postInit)
//			return 30;
//		switch (this.dataWatcher.getWatchableObjectShort(19)) {
//			case 0: return 35;
//			case 1: return 30;
//			case 2: return 45;
//			case 3: return 40;
//			case 4: return 20;
//			case 5: return 35;
//			case 6: return 35;
//			case 7: return 35;
//			case 8: return 35;
//			case 9: return 30;
//			default: return 30;
//		}
//	}

	public boolean gotRider, spawnedFromNexus = false, specDeath = false;
	public boolean postInit = false;
	@Override
	public int getType() {
		return this.dataWatcher.getWatchableObjectShort(19);
	}
}
