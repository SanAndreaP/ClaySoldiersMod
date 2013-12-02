/*******************************************************************************************************************
 * Name:      EntityTurtle.java
 * Author:    SanAndreasP
 * Copyright: SanAndreasP and SilverChiren
 * License:   Attribution-NonCommercial-ShareAlike 3.0 Unported (http://creativecommons.org/licenses/by-nc-sa/3.0/)
 *******************************************************************************************************************/

package sanandreasp.mods.ClaySoldiersMod.entity.mount;
import net.minecraft.block.material.Material;
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
import sanandreasp.mods.ClaySoldiersMod.registry.Textures;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityTurtle extends EntityCreature implements IMount {
    protected float moveSpeed;
	
	public EntityTurtle(World world) {
		super(world);
//		health = 30;
//		turtleType = 0;
		this.dataWatcher.addObject(19, (short)0); // type
		this.dataWatcher.addObject(18, (byte)0); // isKawako
		this.dataWatcher.addObject(20, (int)0); // altSkin
		preInit = true;
		yOffset = 0.0F;
		stepHeight = 0.1F;
		moveSpeed = 0.6F; 
		setSize(0.25F, 0.1F);
		setPosition(posX, posY, posZ);
//		texture = turtleTexture(0);
		renderDistanceWeight = 5D;
	}
	
	public int getAltTex() {
        return this.dataWatcher.getWatchableObjectInt(20);
    }
	
	public void setAltTex(int index) {
        this.dataWatcher.updateObject(20, index);
    }
	
	public boolean isKawako() {
        return this.dataWatcher.getWatchableObjectByte(18) != 0;
    }
	
	public void setKawako(boolean b) {
        this.dataWatcher.updateObject(18, (byte)(b ? 1 : 0));
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(30.0D);
    }
	
	public EntityTurtle(World world, double x, double y, double z, int i) {
		this(world);
		this.dataWatcher.updateObject(19, (short)i);
		setPosition(x, y, z);
		if (rand.nextInt(8192) == 0)
		{
			this.dataWatcher.updateObject(18, (byte)1);
		}
		setTurtleSpecs(i);
		worldObj.playSoundAtEntity(this, "step.stone", 0.8F, ((rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F) * 0.9F);
		this.setAltTex(rand.nextInt(3) != 0 ? 0 : rand.nextInt(Textures.TURTLE[i].length));
	}

//	public String turtleTexture(int i) {
//		String shell = "/sanandreasp/mods/ClaySoldiersMod/claymans/turtle/turtle";
//		if (this.dataWatcher.getWatchableObjectByte(18) > 0) {
//			shell = shell + "Kawako";
//		} else if (i == 0 && hasAltSkin()) {
//			shell = shell + "Cobble2";
//		} else if (i == 0) {
//			shell = shell + "Cobble";
//		} else if (i == 1) {
//			shell = shell + "Mossy";
//		} else if (i == 2) {
//			shell = shell + "Netherrack";
//		} else if (i == 3) {
//			shell = shell + "Melon";
//		} else if (i == 4 && hasAltSkin()) {
//			shell = shell + "Sandstone2";
//		} else if (i == 4) {
//			shell = shell + "Sandstone";
//		} else if (i == 5) {
//			shell = shell + "Endstone";
//		} else if (i == 6 && hasAltSkin()) {
//			shell = shell + "Pumpkin2";
//		} else if (i == 6) {
//			shell = shell + "Pumpkin";
//		} else if (i == 7) {
//			shell = shell + "Lapis";
//		} else if (i == 8) {
//			shell = shell + "Cake";
//		}
//		return shell + ".png";
//	}

//	public boolean hasAltSkin() {
//		return (this.dataWatcher.getWatchableObjectByte(20) & 1) == 1;
//	}
//	public void setAltSkin(boolean b) {
//		byte byt = this.dataWatcher.getWatchableObjectByte(20);
//		this.dataWatcher.updateObject(20, (byte)(b ? (byt | 1) : (byt & ~1)));
//	}
	
//	@Override
//	@SideOnly(Side.CLIENT)
//	public String getTexture() {
//		return turtleTexture(this.dataWatcher.getWatchableObjectShort(12));
//	}
	
	private boolean thisInWater = false;
	private boolean handleThisWaterMovement() {
        return worldObj.handleMaterialAcceleration(boundingBox.expand(0.0D, -0.40000000596046448D, 0.0D).contract(0.001D, 0.001D, 0.001D), Material.water, this);
	}
	
	@Override
	public boolean isInWater() {
		return thisInWater;
	}
	
	@Override
	public boolean handleWaterMovement() {
		return false;
	}
	
	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}
	
	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
		if (handleThisWaterMovement())
        {
            if (!thisInWater)
            {
                float f = MathHelper.sqrt_double(motionX * motionX * 0.20000000298023224D + motionY * motionY + motionZ * motionZ * 0.20000000298023224D) * 0.2F;
                if (f > 1.0F)
                {
                    f = 1.0F;
                }
                if (rand.nextInt(20) == 0) {
                	worldObj.playSoundAtEntity(this, "random.splash", f, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.4F);
	                float f1 = MathHelper.floor_double(boundingBox.minY);
	                for (int l = 0; l < 1.0F + width * 20F; l++)
	                {
	                    float f2 = (rand.nextFloat() * 2.0F - 1.0F) * width;
	                    float f4 = (rand.nextFloat() * 2.0F - 1.0F) * width;
	                    worldObj.spawnParticle("bubble", posX + f2, f1 + 1.0F, posZ + f4, motionX, motionY - (rand.nextFloat() * 0.2F), motionZ);
	                }
	
	                for (int i1 = 0; i1 < 1.0F + width * 20F; i1++)
	                {
	                    float f3 = (rand.nextFloat() * 2.0F - 1.0F) * width;
	                    float f5 = (rand.nextFloat() * 2.0F - 1.0F) * width;
	                    worldObj.spawnParticle("splash", posX + f3, f1 + 1.0F, posZ + f5, motionX, motionY, motionZ);
	                }
                }
            }
            fallDistance = 0.0F;
            thisInWater = true;
            setFire(0);
        }
        else
        {
        	thisInWater = false;
        }
	}
	
	@Override
	public void onLivingUpdate() {
		if (isInWater()) {
			isJumping = false;
			motionY = 0.01F;
			
			if (worldObj.getBlockMaterial((int)Math.floor(posX), (int)Math.ceil(posY-0.5), (int)Math.floor(posZ)).equals(Material.water)) {
				motionY = 0.2F;
				posY+=0.01F;
			}
		}
		super.onLivingUpdate();
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
	
	public void setTurtleSpecs(int i) {
		if (this.dataWatcher.getWatchableObjectByte(18) > 0) {
		    updateHealth(50);
			moveSpeed = 0.5f;
		} else if (i == 0) {
            updateHealth(40);
			moveSpeed = 0.4f;
		} else if (i == 1) {
            updateHealth(45);
			moveSpeed = 0.3f;
		} else if (i == 2) {
            updateHealth(35);
			moveSpeed = 0.4f;
		} else if (i == 3) {
            updateHealth(25);
			moveSpeed = 0.6f;
		} else if (i == 4) {
            updateHealth(30);
			moveSpeed = 0.4f;
		} else if (i == 5) {
            updateHealth(45);
			moveSpeed = 0.4f;
		} else if (i == 6) {
            updateHealth(25);
			moveSpeed = 0.6f;
		} else if (i == 7) {
            updateHealth(35);
			moveSpeed = 0.6f;
		} else if (i == 8) {
            updateHealth(30);
			moveSpeed = 0.6f;
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
    {
		super.writeToNBT(nbttagcompound);
		gotRider = (riddenByEntity == null);
		nbttagcompound.setBoolean("Kawako", this.dataWatcher.getWatchableObjectByte(18) > 0);
		nbttagcompound.setBoolean("GotRider", gotRider);
		nbttagcompound.setShort("TurtleType", (short)this.dataWatcher.getWatchableObjectShort(19));
		nbttagcompound.setBoolean("IsFromNexus", spawnedFromNexus);
		nbttagcompound.setInteger("altSkinInt", this.getAltTex());
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
    {
		super.readFromNBT(nbttagcompound);
		this.setKawako(nbttagcompound.getBoolean("Kawako"));
		gotRider = nbttagcompound.getBoolean("GotRider");
		this.dataWatcher.updateObject(19, nbttagcompound.getShort("TurtleType"));
		spawnedFromNexus = nbttagcompound.getBoolean("IsFromNexus");
		setTurtleSpecs(this.dataWatcher.getWatchableObjectShort(19));
		if(nbttagcompound.hasKey("altSkin"))
		    this.setAltTex(this.getType());
		else
		    this.dataWatcher.updateObject(20, nbttagcompound.getInteger("altSkinInt"));
	}
	
	@Override
	protected String getHurtSound()
    {
		worldObj.playSoundAtEntity(this, "step.stone", 0.6F, 1.0F / (rand.nextFloat() * 0.2F + 0.9F));
		return "";
    }
	
	@Override
	protected String getDeathSound()
    {
		return "step.stone";
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
	public double getMountedYOffset() {
		return super.getMountedYOffset();
	}
	
	@Override
	protected void dropFewItems(boolean flag, int i) {
		Item item1 = CSMModRegistry.turtleDoll;
		dropItem(item1.itemID, 1, this.dataWatcher.getWatchableObjectShort(19));
	}
	
	protected void dropItem(int itemID, int i, int j) {
		entityDropItem(new ItemStack(itemID, i, j), 0.0F);
	}
	
	@Override
	public float getAIMoveSpeed() {
	    return this.moveSpeed;
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float i) {
		float origDmg = i;
		
		int randSpec = rand.nextInt(16);
		
		if ((damagesource.equals(DamageSource.magic)) && this.dataWatcher.getWatchableObjectByte(18) <= 0 && randSpec == 0)
			specDeath = true;
		else
			specDeath = false;
		
		Entity e = damagesource.getSourceOfDamage();
		if (e == null || !(e instanceof EntityClayMan) && !(e instanceof EntityGravelChunk) && !(e instanceof EntityTurtle) && !(damagesource.equals(DamageSource.magic))) {
			i = 100;
		} if ((damagesource.equals(DamageSource.magic)) && this.dataWatcher.getWatchableObjectByte(18) > 0) {
			i = 0;
		}
		
		if (this.dataWatcher.getWatchableObjectShort(19) != 3 && this.dataWatcher.getWatchableObjectShort(19) != 6 && (damagesource.equals(DamageSource.inFire) || damagesource.equals(DamageSource.onFire))) {
			return false;
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
		
		if (fred && e != null && !(e instanceof EntityTurtle) && !(e instanceof EntityGravelChunk) && !(e instanceof EntityPlayer) && !(e instanceof EntityFireball) && !(e instanceof EntitySnowball)) {
			float i1 = i/2F;
			e.attackEntityFrom(DamageSource.causeMobDamage(this), i1);
		}
		
		if (fred && this.getHealth() <= 0) {
//			Item item1 = CSM_ModRegistry.turtle;
			for (int j = 0; j < 4; j++) {
				double a = posX + ((rand.nextFloat() - rand.nextFloat()) * 0.125D);
				double b = boundingBox.minY + 0.125D + ((rand.nextFloat() - rand.nextFloat()) * 0.25D);
				double c = posZ + ((rand.nextFloat() - rand.nextFloat()) * 0.125D);
//				if (FMLCommonHandler.instance().getSide().isClient())
//					CSM_ModRegistry.proxy.showEffect((new EntityDiggingFX(CSM_ModRegistry.proxy.getClientWorld(), a, b, c, 0.0D, 0.0D, 0.0D, Block.dirt, 0, 0)));
				CSMModRegistry.proxy.showEffect(this.worldObj, this, 13);
			}
			if ((damagesource.equals(DamageSource.magic)) && !this.isKawako() && randSpec == 0) {
				EntityTurtle entity = new EntityTurtle(worldObj, posX, posY, posZ, this.getType());
				entity.setKawako(true);
//				entity.texture = entity.turtleTexture(entity.dataWatcher.getWatchableObjectShort(19));
				entity.setTurtleSpecs(entity.getType());
				worldObj.spawnEntityInWorld(entity);
				this.setDead();
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
	
//	@Override
//	public int getMaxHealth() {
//		if (!preInit) return 40;
//		switch (this.dataWatcher.getWatchableObjectShort(19)) {
//			case 1: return 45;
//			case 2: return 35;
//			case 3: return 25;
//			case 4: return 30;
//			case 6: return 25;
//			case 7: return 35;
//			case 8: return 30;
//			default: return 40;
//		}
//	}
	
	@Override
	public EntityItem entityDropItem(ItemStack par1ItemStack, float par2) {
		return spawnedFromNexus || specDeath ? null : super.entityDropItem(par1ItemStack, par2);
	}
	
	public EntityTurtle setSpawnedFromNexus() {
		spawnedFromNexus = true;
		return this;
	}
	
	@Override
	public int getType() {
		return this.dataWatcher.getWatchableObjectShort(19);
	}

	public boolean spawnedFromNexus = false, specDeath = false;
	public boolean preInit;
	public boolean gotRider;
}