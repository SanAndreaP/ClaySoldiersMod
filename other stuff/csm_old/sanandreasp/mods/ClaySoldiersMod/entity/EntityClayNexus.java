/*******************************************************************************************************************
 * Name:      EntityClayNexus.java
 * Author:    SanAndreasP
 * Copyright: SanAndreasP and SilverChiren
 * License:   Attribution-NonCommercial-ShareAlike 3.0 Unported (http://creativecommons.org/licenses/by-nc-sa/3.0/)
 *******************************************************************************************************************/

package sanandreasp.mods.ClaySoldiersMod.entity;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;
import net.minecraft.src.*;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.List;

import sanandreasp.mods.ClaySoldiersMod.entity.mount.EntityBunny;
import sanandreasp.mods.ClaySoldiersMod.entity.mount.EntityGecko;
import sanandreasp.mods.ClaySoldiersMod.entity.mount.EntityHorse;
import sanandreasp.mods.ClaySoldiersMod.entity.mount.EntityPegasus;
import sanandreasp.mods.ClaySoldiersMod.entity.mount.EntityTurtle;
import sanandreasp.mods.ClaySoldiersMod.entity.mount.IMount;
import sanandreasp.mods.ClaySoldiersMod.inventory.InventoryNexus;
import sanandreasp.mods.ClaySoldiersMod.item.ItemBunny;
import sanandreasp.mods.ClaySoldiersMod.item.ItemClayMan;
import sanandreasp.mods.ClaySoldiersMod.item.ItemGecko;
import sanandreasp.mods.ClaySoldiersMod.item.ItemHorses;
import sanandreasp.mods.ClaySoldiersMod.item.ItemTurtle;
import sanandreasp.mods.ClaySoldiersMod.registry.CSMModRegistry;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;

public class EntityClayNexus extends EntityCreature {
    public int innerRotation;
    public int ticksSpawn = 0;
    public ItemStack[] nexusIS = new ItemStack[47];
    public InventoryNexus nexusInv = new InventoryNexus(this);

	public EntityClayNexus(World par1World) {
		super(par1World);
		this.dataWatcher.addObject(18, (short)-1); // color
		this.dataWatcher.addObject(13, (byte)0); // booleanStuff
		this.dataWatcher.addObject(14, (short)10); // waveDurationSec
		this.dataWatcher.addObject(15, (short)5); // spawnSoldiersMax
		this.dataWatcher.addObject(16, (short)15); // maxLivingSoldiers
		this.dataWatcher.addObject(17, (short)0); // chanceGetNone
		setSize(0.3F, 0.3F);
        innerRotation = rand.nextInt(0x186a0);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(60.0D);
	}

	public boolean isActive() {
		return (this.dataWatcher.getWatchableObjectByte(13) & 1) == 1;
	}
	public void setActive(boolean flag) {
		byte prevDW = this.dataWatcher.getWatchableObjectByte(13);
		this.dataWatcher.updateObject(13, (byte)(flag ? prevDW | 1 : prevDW & ~1));
	}
	public boolean isDestroyed() {
		return (this.dataWatcher.getWatchableObjectByte(13) & 2) == 2;
	}
	public void setDestroyed(boolean flag) {
		byte prevDW = this.dataWatcher.getWatchableObjectByte(13);
		this.dataWatcher.updateObject(13, (byte)(flag ? prevDW | 2 : prevDW & ~2));
	}
	public boolean hasRandItems() {
		return (this.dataWatcher.getWatchableObjectByte(13) & 4) == 4;
	}
	public void setRandItems(boolean flag) {
		byte prevDW = this.dataWatcher.getWatchableObjectByte(13);
		this.dataWatcher.updateObject(13, (byte)(flag ? prevDW | 4 : prevDW & ~4));
	}
	public boolean getSpawnMount() {
		return (this.dataWatcher.getWatchableObjectByte(13) & 8) == 8;
	}
	public void setSpawnMount(boolean flag) {
		byte prevDW = this.dataWatcher.getWatchableObjectByte(13);
		this.dataWatcher.updateObject(13, (byte)(flag ? prevDW | 8 : prevDW & ~8));
	}

    public float[] getTeamColor() {
    	switch(getColor()) {
	    	case 0: return new float[] {0.5F, 0.5F, 0.5F};
	    	case 1: // FALL-THROUGH
	    	case 19: return new float[] {1.0F, 0.0F, 0.0F};
	    	case 2: return new float[] {1.0F, 1.0F, 0.0F};
	    	case 3: return new float[] {0.0F, 1.0F, 0.0F};
	    	case 4: return new float[] {0.0F, 0.0F, 1.0F};
	    	case 5: return new float[] {1.0F, 0.5F, 0.0F};
	    	case 6: return new float[] {0.5F, 0.0F, 1.0F};
	    	case 7: return new float[] {1.0F, 0.5F, 0.5F};
	    	case 8: return new float[] {0.5F, 0.25F, 0.0F};
	    	case 9: return new float[] {1.0F, 1.0F, 1.0F};
	    	case 10: // FALL-THROUGH
	    	case 18: return new float[] {0.1F, 0.1F, 0.1F};
	    	case 11: return new float[] {0.0F, 1.0F, 1.0F};
	    	case 12: return new float[] {0.7F, 0.7F, 0.7F};
	    	case 13: return new float[] {0.25F, 1.0F, 0.25F};
	    	case 14: return new float[] {0.7F, 0.7F, 1.0F};
	    	case 15: return new float[] {1.0F, 0.0F, 1.0F};
	    	case 16: return new float[] {0.57F, 0.75F, 0.15F};
	    	case 17: return new float[] {0.75F, 0.67F, 0.15F};
	    	default: return new float[] {0.5F, 0.5F, 0.5F};
    	}
    }

    @Override
    public boolean isPotionApplicable(PotionEffect par1PotionEffect) {
    	return false;
    }

    public void setSrvMaxHealth(float heal) {
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(heal);
    }

	public int getColor() {
		return this.dataWatcher.getWatchableObjectShort(18);
	}
	public void setColor(int clr) {
		this.dataWatcher.updateObject(18, (short)clr);
	}

	public short getWaveDurSec() {
		return this.dataWatcher.getWatchableObjectShort(14);
	}
	public void setWaveDurSec(int wds) {
		this.dataWatcher.updateObject(14, (short)wds);
	}

	public short getMaxSpwnSoldiers() {
		return this.dataWatcher.getWatchableObjectShort(15);
	}
	public void setMaxSpwnSoldiers(int mspsl) {
		this.dataWatcher.updateObject(15, (short)mspsl);
	}

	public short getMaxLvngSoldiers() {
		return this.dataWatcher.getWatchableObjectShort(16);
	}
	public void setMaxLvngSoldiers(int mlsl) {
		this.dataWatcher.updateObject(16, (short)mlsl);
	}

	public short getChanceGetNone() {
		return this.dataWatcher.getWatchableObjectShort(17);
	}
	public void setChanceGetNone(int cgn) {
		this.dataWatcher.updateObject(17, (short)cgn);
	}

	public EntityClayNexus(World par1World, double x, double y, double z) {
		this(par1World);
		setPositionAndRotation(x, y, z, 0F, 0F);
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
		if (par1DamageSource.getEntity() instanceof EntityPlayer && !par1DamageSource.isMagicDamage()) {
			setDead();
		}

		if (this.getHealth() - par2 < 1) {
			CSMModRegistry.proxy.showEffect(this.worldObj, this, 11);
			setDestroyed(true);
			setHealth(this.getMaxHealth());
			return false;
		}

		if (this.hurtTime <= 0) {
			setHealth(this.getHealth()-par2);
			hurtTime = 10;
			return true;
		}

		return false;
	}

	private boolean hasTeamKing() {
		List<Entity> list = worldObj.loadedEntityList;
		for (Entity entity : list) {
//			if (entity != null && entity instanceof EntityClayMan) {
//				if (((EntityClayMan)entity).getClayTeam() == getColor() && ((EntityClayMan)entity).hasCrown()) {
//					return true;
//				}
//			}
		}
		return false;
	}

	public boolean checkIfItemIsValidAndApply(ItemStack item, EntityClayMan soldier, boolean isUpgrade) {
		boolean valid = false;

//		if (!isUpgrade && !soldier.hasString() && !soldier.heavyCore && !soldier.brawler && !soldier.isCorrupt() && itemData.itemID == Item.silk.itemID) {
//			valid = true;
//		} else if (!isUpgrade && !soldier.hasRightShear() && !soldier.villager && !soldier.hasBlazeRod() && !soldier.hasBone() && !soldier.hasStick() && itemData.itemID == Item.stick.itemID && !soldier.hasGlister()) {
//			valid = true;
//		} else if (!isUpgrade && !soldier.hasRightShear() && !soldier.villager && !soldier.hasBlazeRod() && !soldier.hasBone() && !soldier.hasStick() && itemData.itemID == Item.blazeRod.itemID && !soldier.hasGlister()) {
//			valid = true;
//		} else if (!isUpgrade && !soldier.brawler && !soldier.isCorrupt() && !soldier.hasGlister() && itemData.itemID == Item.speckledMelon.itemID && !((soldier.gooStock > 0) || (soldier.smokeStock > 0) || (soldier.blazeStock > 0) ||  soldier.hasBlazeRod() || soldier.hasBone() || soldier.hasFireballs() || soldier.hasRocks() || soldier.hasShield() || soldier.hasSnowballs() || soldier.hasTimeBomb() || soldier.hasStick() || (soldier.toxinStock > 0))) {
//			valid = true;
//		} else if (!isUpgrade && !soldier.hasArmor() && itemData.itemID == Item.leather.itemID) {
//			valid = true;
//		} else if (!isUpgrade && !soldier.hasArmor() && itemData.itemID == Item.leather.itemID) {
//			valid = true;
//		} else if (!isUpgrade && !soldier.villager && !soldier.timeBombReady && !soldier.hasGunPowder() && itemData.itemID == Item.magmaCream.itemID && !soldier.hasGlister()) {
//			valid = true;
//		} else if (!isUpgrade && !soldier.hasPants() && itemData.itemID == Block.waterlily.blockID) {
//			valid = true;
//		} else if (!isUpgrade && !soldier.hasReed() && itemData.itemID == Item.reed.itemID && !soldier.hasGlister()) {
//			valid = true;
//		} else if (!isUpgrade && !soldier.neutral && itemData.itemID == Item.fermentedSpiderEye.itemID && !soldier.villager) {
//			valid = true;
//		} else if (!isUpgrade && !soldier.isCorrupt() && itemData.itemID == CSMModRegistry.shearBlade.itemID && !soldier.hasGlister() && ((!soldier.hasLeftShear() && !soldier.hasShield() && !soldier.hasRocks() && !soldier.hasSnowballs() && !soldier.hasFireballs()) || (!soldier.hasRightShear() && !soldier.hasBlazeRod() && !soldier.hasBone() && !soldier.hasStick()))) {
//			valid = true;
//		} else if (!isUpgrade && !soldier.hasShield() && !soldier.hasLeftShear() && itemData.itemID == Item.bowlEmpty.itemID && !soldier.hasRocks() && !soldier.hasSnowballs() && !soldier.hasFireballs() && !soldier.hasGlister()) {
//			valid = true;
//		} else if (!isUpgrade && !soldier.villager && !soldier.hasLeftShear() && !soldier.hasRocks() && itemData.itemID == Block.gravel.blockID && !soldier.hasShield() && !soldier.hasSnowballs() && !soldier.hasFireballs() && !soldier.hasGlister()) {
//			valid = true;
//		} else if (!isUpgrade && !soldier.villager && !soldier.hasLeftShear() && !soldier.hasRocks() && itemData.itemID == Item.fireballCharge.itemID && !soldier.hasShield() && !soldier.hasSnowballs() && !soldier.hasFireballs() && !soldier.hasGlister()) {
//			valid = true;
//		} else if (!isUpgrade && !soldier.villager && !soldier.hasLeftShear()  && !soldier.hasSnowballs() && !soldier.hasRocks() && itemData.itemID == Item.snowball.itemID && !soldier.hasShield() && !soldier.hasFireballs() && !soldier.hasGlister()) {
//			valid = true;
//		} else if (!isUpgrade && !soldier.villager && soldier.toxinStock <= 0 && itemData.itemID == Block.mushroomRed.blockID && !soldier.hasGlister()) {
//			valid = true;
//		} else if (!isUpgrade && soldier.foodLeft <= 0 && itemData.itemID == Block.mushroomBrown.blockID) {
//			valid = true;
//		} else if (isUpgrade && !soldier.hasCoal && !soldier.villager && itemData.itemID == Item.coal.itemID && (soldier.hasBlazeRod() || soldier.hasFireballs())) {
//			valid = true;
//		} else if (!isUpgrade && !soldier.isGlowing() && itemData.itemID == Item.glowstone.itemID) {
//			valid = true;
//		} else if (!isUpgrade && !soldier.villager && !soldier.brawler && itemData.itemID == Item.wheat.itemID) {
//			valid = true;
//		} else if (!isUpgrade && !soldier.villager && !soldier.brawler && itemData.itemID == Item.netherStalkSeeds.itemID) {
//			valid = true;
//		} else if (!isUpgrade && !soldier.hasCrown() && !soldier.brawler && !hasTeamKing() && itemData.itemID == Item.goldNugget.itemID) {
//			valid = true;
//		} else if (!isUpgrade && !soldier.isSuper() && !soldier.isCaped() && itemData.itemID == Item.diamond.itemID) {
//			valid = true;
//		} else if (!isUpgrade && !soldier.isSuper() && !soldier.isCaped() && itemData.itemID == Item.paper.itemID) {
//			valid = true;
//		} else if (!isUpgrade && !soldier.isCorrupt() && itemData.itemID == Item.enderPearl.itemID) {
//			valid = true;
//		} else if (!isUpgrade && !soldier.hasGunPowder() && itemData.itemID == Item.gunpowder.itemID) {
//			valid = true;
//		} else if (!isUpgrade && !soldier.isCamouflaged() && itemData.itemID == Item.egg.itemID) {
//			valid = true;
//		} else if (!isUpgrade && !soldier.isSuper() && soldier.sugarTime <= 0 && itemData.itemID == Item.sugar.itemID) {
//			valid = true;
//		} else if (!isUpgrade && soldier.foodLeft <= 0 && itemData.getItem() != null && itemData.getItem() instanceof ItemFood) {
//			valid = true;
//		} else if (!isUpgrade && !soldier.brawler && soldier.resPoints <= 0 && itemData.itemID == Item.clay.itemID) {
//			valid = true;
//		} else if (!isUpgrade && !soldier.brawler && soldier.ghastTearPts <= 0 && itemData.itemID == Item.ghastTear.itemID) {
//			valid = true;
//		} else if (!isUpgrade && !soldier.villager && soldier.gooStock <= 0 && itemData.itemID == Item.slimeBall.itemID && !soldier.hasGlister()) {
//			valid = true;
//		} else if (!isUpgrade && !soldier.villager && soldier.smokeStock <= 0 && itemData.itemID == Item.redstone.itemID && !soldier.hasGlister()) {
//			valid = true;
//		} else if (!isUpgrade && !soldier.villager && soldier.blazeStock <= 0 && itemData.itemID == Item.blazePowder.itemID && !soldier.hasGlister()) {
//			valid = true;
//		} else if (isUpgrade && !soldier.villager && soldier.hasStick() && !soldier.isStickSharp() && itemData.itemID == Item.flint.itemID) {
//			valid = true;
//		} else if (!isUpgrade && !soldier.hasRightShear() && !soldier.villager && !soldier.hasBlazeRod() && !soldier.hasBone() && !soldier.hasStick() && itemData.itemID == Item.arrow.itemID && !soldier.hasGlister()) {
//			valid = true;
//		} else if (isUpgrade && ((soldier.hasArmor() && !soldier.isPadded()) || soldier.isCaped()) && itemData.itemID == Block.cloth.blockID) {
//			valid = true;
//		} else if (isUpgrade && soldier.hasShield() && !soldier.isShieldStud() && itemData.itemID == Block.blockIron.blockID) {
//			valid = true;
//		} else if (!soldier.hasGoggles() && (itemData.itemID == Block.glass.blockID || itemData.itemID == Item.glassBottle.itemID || itemData.itemID == Block.thinGlass.blockID)) {
//			valid = true;
//		} else if (!isUpgrade && !soldier.heavyCore && ridingEntity == null && !soldier.hasFeather && itemData.itemID == Item.ingotIron.itemID) {
//			valid = true;
//		} else if (!isUpgrade && !soldier.hasFeather && !soldier.heavyCore && itemData.itemID == Item.feather.itemID) {
//			valid = true;
//		} else if (!isUpgrade && itemData.itemID == CSMModRegistry.clayCookie.itemID) {
//			valid = true;
//		} else if (!isUpgrade && itemData.itemID == Item.monsterPlacer.itemID && itemData.getItemDamage() == soldier.teamEgg(soldier.getClayTeam()) && !soldier.hasSpecSkin()) {
//			valid = true;
//		}
//
//		if (getChanceGetNone() > 0 && rand.nextInt(getChanceGetNone()) == 0)
//			valid = false;
//
//		if (valid) {
//			applyItemForSoldier(itemData, soldier);
//			return true;
//		}

		return false;
	}

	public void applyItemForSoldier(ItemStack item, EntityClayMan soldier) {
//		soldier.applyStatsFromItem(itemData);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		this.renderYawOffset = 0F;
		this.prevRotationPitch = 0F;
		this.prevRotationYaw = 0F;
		this.prevRotationYawHead = 0F;
		this.rotationPitch = 0F;
		this.rotationYaw = 0F;
		this.rotationYawHead = 0F;

		if (isActive() && !isDestroyed()) {
			ticksSpawn++;
	        innerRotation++;
		}

		if (this.worldObj.isRemote) return;

		if (getWaveDurSec() < 1) setWaveDurSec(1);
		if (getMaxLvngSoldiers() < 1) setMaxLvngSoldiers(1);
		if (getMaxSpwnSoldiers() < 1) setMaxSpwnSoldiers(1);
		if (getChanceGetNone() < 0) setChanceGetNone(0);
		if (ticksSpawn >= 10000 || !isActive() || isDestroyed()) ticksSpawn = 0;

		if (ticksSpawn % (getWaveDurSec() * 20) == 0 && !isDestroyed() && isActive() && (getColor() >= 0 || getSpawnMount())) {
			spawnSoldiers();
		}

		if (nexusIS[0] != null && nexusIS[0].stackSize > 0 && nexusIS[0].getItem() instanceof ItemClayMan) {
			setColor(nexusIS[0].getItemDamage());
			setSpawnMount(false);
		} else if (nexusIS[1] != null && nexusIS[1].stackSize > 0) {
			setSpawnMount(true);
		} else {
			setColor(-1);
			setSpawnMount(false);
		}

		int cPosX = (int) Math.floor(posX);
    	int cPosY = (int) Math.floor(posY);
    	int cPosZ = (int) Math.floor(posZ);

		if (worldObj.isBlockIndirectlyGettingPowered(cPosX, cPosY-1, cPosZ) || worldObj.getStrongestIndirectPower(cPosX, cPosY, cPosZ) > 0)
			setActive(true);
		else
			setActive(false);
	}

	@Override
	public void setDead() {
		if (worldObj.isRemote) {
			super.setDead();
			return;
		}

//		spawnExplosionParticle();
		CSMModRegistry.proxy.showEffect(this.worldObj, this, 11);
		if (!this.worldObj.getWorldInfo().getGameType().isCreative())
			dropItem(CSMModRegistry.nexus.itemID, 1);
		for (ItemStack is : nexusIS) {
			if (is != null && is.stackSize > 0) {
				entityDropItem(is, 0.0F);
			}
		}
		super.setDead();
	}

	@Override
	public boolean interact(EntityPlayer par1EntityPlayer) {
	    if (this.worldObj.isRemote) {
	        return true;
	    }

	    par1EntityPlayer.openGui(CSMModRegistry.instance, 0, this.worldObj, this.entityId, 0, 0);
	    return true;
	}

	private void spawnSoldiers() {

		if (worldObj.isRemote) return;

		for (int i = 0; i < getMaxSpwnSoldiers() && !isMaxSpawningReached(); i++) {
			double clayX;
			double clayY;
			double clayZ;

			clayX = posX + rand.nextInt(2)*0.5F*(rand.nextBoolean() ? -1 : 1)+0.2D;
			clayY = posY + 0.5F;
			clayZ = posZ + rand.nextInt(2)*0.5F*(rand.nextBoolean() ? -1 : 1)+0.2D;

			EntityClayMan ec = null;

			if (!getSpawnMount()) {
				ec = new EntityClayMan(worldObj, (int) Math.floor(clayX), (int) Math.floor(clayY), (int) Math.floor(clayZ), getColor());
				ec.setNexusSpawn();
			}

			EntityLiving mount = null;

			if (nexusIS[1] != null && nexusIS[1].stackSize > 0) {
				if (nexusIS[1].itemID == CSMModRegistry.horseDoll.itemID)
					mount = new EntityHorse(worldObj, clayX, clayY, clayZ, nexusIS[1].getItemDamage()).setSpawnedFromNexus();
				if (nexusIS[1].itemID == CSMModRegistry.pegasusDoll.itemID)
					mount = new EntityPegasus(worldObj, clayX, clayY, clayZ, nexusIS[1].getItemDamage()).setSpawnedFromNexus();
				if (nexusIS[1].itemID == CSMModRegistry.turtleDoll.itemID)
					mount = new EntityTurtle(worldObj, clayX, clayY, clayZ, nexusIS[1].getItemDamage()).setSpawnedFromNexus();
				if (nexusIS[1].itemID == CSMModRegistry.bunnyDoll.itemID)
					mount = new EntityBunny(worldObj, clayX, clayY, clayZ, nexusIS[1].getItemDamage()).setSpawnedFromNexus();
				if (nexusIS[1].itemID == CSMModRegistry.geckoDoll.itemID)
					mount = new EntityGecko(worldObj, clayX, clayY, clayZ, nexusIS[1].getItemDamage()).setSpawnedFromNexus();
			}

			if (!getSpawnMount()) {
				boolean[] usedStacks = new boolean[nexusIS.length];

				for (int j = 2; j < nexusIS.length && !hasRandItems(); j++) {
					if (nexusIS[j] != null && nexusIS[j].stackSize > 0 && !usedStacks[j]) {
						if (checkIfItemIsValidAndApply(nexusIS[j], ec, false)) {
							checkForUpgradeItem(ec);
						}
						usedStacks[j] = true;
					}
				}

				if (hasRandItems()) {
					int loopInd = 0;

					do {
						int index = rand.nextInt(nexusIS.length - 2) + 2;

						if (nexusIS[index] != null && nexusIS[index].stackSize > 0 && !usedStacks[index]) {
							loopInd = 0;
							if (checkIfItemIsValidAndApply(nexusIS[index], ec, false)) {
								checkForUpgradeItem(ec);
							}
							usedStacks[index] = true;
						} else {
							loopInd++;
						}

					} while(loopInd < nexusIS.length - 2);
				}

				worldObj.spawnEntityInWorld(ec);
				CSMModRegistry.proxy.showEffect(this.worldObj, ec, 11);
//				ec.spawnExplosionParticle();
			}

			if (mount != null) {
				worldObj.spawnEntityInWorld(mount);
				if (!getSpawnMount()) ec.mountEntity(mount);
			}
		}
	}

	private void checkForUpgradeItem(EntityClayMan ec) {
		for (int j = 2; j < nexusIS.length; j++) {
			if (nexusIS[j] != null && nexusIS[j].stackSize > 0) {
				checkIfItemIsValidAndApply(nexusIS[j], ec, true);
			}
		}
	}

	private boolean isMaxSpawningReached() {
		List<Entity> el = worldObj.loadedEntityList;
		int count = 0;
		for (Entity entity : el) {
			if (entity instanceof EntityClayMan) {
				if (((EntityClayMan)entity).getClayTeam() == getColor() && ((EntityClayMan)entity).spawnedFromNexus())
					count++;
			} else if (getSpawnMount() && (entity instanceof IMount) && ((IMount)entity).getType() == nexusIS[1].getItemDamage()) {
				if (entity instanceof EntityPegasus && nexusIS[1].getItem() instanceof ItemHorses && ((ItemHorses)nexusIS[1].getItem()).horseType == 1) {
					count++;
				} else if (entity instanceof EntityHorse && nexusIS[1].getItem() instanceof ItemHorses && ((ItemHorses)nexusIS[1].getItem()).horseType == 0) {
					count++;
				} else if (entity instanceof EntityTurtle && nexusIS[1].getItem() instanceof ItemTurtle) {
					count++;
				} else if (entity instanceof EntityBunny && nexusIS[1].getItem() instanceof ItemBunny) {
					count++;
				} else if (entity instanceof EntityGecko && nexusIS[1].getItem() instanceof ItemGecko) {
					count++;
				}
			}
		}

		return count >= getMaxLvngSoldiers();
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();

        for (int k = 0; k < 2 && isActive() && !isDestroyed() && CSMModRegistry.proxy.isClient(); k++)
        {
        	//TODO: nexus FX
        	CSMModRegistry.proxy.showEffect(this.worldObj, this, 10);
//        	CSM_EntityNexusFX particle = new CSM_EntityNexusFX(CSM_ModRegistry.proxy.getClientWorld(), posX + (rand.nextDouble() - 0.5D) * width, (posY + rand.nextDouble() * height) - 0.25D, posZ + (rand.nextDouble() - 0.5D) * width, (rand.nextDouble() - 0.5D) * 2D, -rand.nextDouble(), (rand.nextDouble() - 0.5D) * 2D, getTeamColor()[0], getTeamColor()[1], getTeamColor()[2]);
//        	CSM_ModRegistry.proxy.showEffect(particle);
        }
	}

//	@Override
//	public int getMaxHealth() {
//		return 60;
//	}

	@Override
	protected boolean isMovementBlocked() {
		return true;
	}

	@Override
	protected String getDeathSound() {
		return "step.stone";
	}

	@Override
	protected String getHurtSound() {
		return "step.stone";
	}

	@Override
	protected String getLivingSound() {
		return "";
	}

	@Override
	public void moveEntity(double par1, double par3, double par5) {
//		super.moveEntity(par1, par3, par5);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound par1nbtTagCompound) {
		super.writeEntityToNBT(par1nbtTagCompound);
		par1nbtTagCompound.setShort("CSM_WaveDurSec", getWaveDurSec());

//		par1nbtTagCompound.setInteger("CSM_CNhealth", getSrvHealth());
		par1nbtTagCompound.setShort("CSM_spwnSldMax", getMaxSpwnSoldiers());
		par1nbtTagCompound.setFloat("CSM_CNmaxHealthF", getMaxHealth());
		par1nbtTagCompound.setShort("CSM_maxLvngSld", getMaxLvngSoldiers());
		par1nbtTagCompound.setShort("CSM_chncGetNone", getChanceGetNone());
		par1nbtTagCompound.setShort("CSM_color", (short)getColor());
		par1nbtTagCompound.setBoolean("CSM_destroyed", isDestroyed());
		par1nbtTagCompound.setBoolean("CSM_randomItems", hasRandItems());

        NBTTagList var2 = new NBTTagList();

        for (int var3 = 0; var3 < this.nexusIS.length; ++var3)
        {
            if (this.nexusIS[var3] != null)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("CSM_Slot", (byte)var3);
                this.nexusIS[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }

        par1nbtTagCompound.setTag("CSM_Items", var2);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound par1nbtTagCompound) {
		super.readEntityFromNBT(par1nbtTagCompound);

        NBTTagList var2 = par1nbtTagCompound.getTagList("CSM_Items");

        for (int var3 = 0; var3 < var2.tagCount(); ++var3)
        {
            NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
            int var5 = var4.getByte("CSM_Slot") & 255;

            if (var5 >= 0 && var5 < this.nexusIS.length)
            {
                this.nexusIS[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }

		if (par1nbtTagCompound.hasKey("CSM_WaveDurSec"))
			setWaveDurSec(par1nbtTagCompound.getShort("CSM_WaveDurSec"));
		if (par1nbtTagCompound.hasKey("CSM_spwnSldMax"))
			setMaxSpwnSoldiers(par1nbtTagCompound.getShort("CSM_spwnSldMax"));
		if (par1nbtTagCompound.hasKey("CSM_CNmaxHealth"))
			setSrvMaxHealth(par1nbtTagCompound.getInteger("CSM_CNmaxHealth"));
		if (par1nbtTagCompound.hasKey("CSM_CNmaxHealthF"))
			setSrvMaxHealth(par1nbtTagCompound.getFloat("CSM_CNmaxHealthF"));
		if (par1nbtTagCompound.hasKey("CSM_CNhealth"))
			setHealth(par1nbtTagCompound.getInteger("CSM_CNhealth"));
		if (par1nbtTagCompound.hasKey("CSM_color"))
			setColor(par1nbtTagCompound.getShort("CSM_color"));
		if (par1nbtTagCompound.hasKey("CSM_maxLvngSld"))
			setMaxLvngSoldiers(par1nbtTagCompound.getShort("CSM_maxLvngSld"));
		if (par1nbtTagCompound.hasKey("CSM_chncGetNone"))
			setChanceGetNone(par1nbtTagCompound.getShort("CSM_chncGetNone"));
		if (par1nbtTagCompound.hasKey("CSM_destroyed"))
			setDestroyed(par1nbtTagCompound.getBoolean("CSM_destroyed"));
		if (par1nbtTagCompound.hasKey("CSM_randomItems"))
			setRandItems(par1nbtTagCompound.getBoolean("CSM_randomItems"));
	}

}
