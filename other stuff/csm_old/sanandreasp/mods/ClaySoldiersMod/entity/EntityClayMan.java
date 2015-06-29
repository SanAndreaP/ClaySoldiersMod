/*******************************************************************************************************************
 * Name:      EntityClayMan.java
 * Author:    SanAndreasP
 * Copyright: SanAndreasP and SilverChiren
 * License:   Attribution-NonCommercial-ShareAlike 3.0 Unported (http://creativecommons.org/licenses/by-nc-sa/3.0/)
 *******************************************************************************************************************/

package sanandreasp.mods.ClaySoldiersMod.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import com.google.common.collect.Maps;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import sanandreasp.core.manpack.helpers.CommonUsedStuff;
import sanandreasp.mods.ClaySoldiersMod.entity.mount.IMount;
import sanandreasp.mods.ClaySoldiersMod.packet.PacketSendSldUpgrades;
import sanandreasp.mods.ClaySoldiersMod.registry.CSMModRegistry;
import sanandreasp.mods.ClaySoldiersMod.registry.Textures;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.IUpgradeEntity;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.IUpgradeItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityClayMan extends EntityCreature implements IUpgradeEntity
{
    public static final float baseMoveSpeed = 0.3F;
    public float moveSpeed = this.baseMoveSpeed;

    private boolean fromNexus = false;
    private boolean isSwinging;
    private boolean isSwingingLeft;
    private float swingLeft;

    private Entity targetFollow = null;
    public Entity attackingEntity = null;

    private Map<Integer, NBTTagCompound> upgrades = Maps.newHashMap();
    private int upgHash = this.upgrades.hashCode();

	public EntityClayMan(World par1World) {
		super(par1World);
		this.yOffset = 0.0F;
		this.stepHeight = 0.1F;
		this.setSize(0.15F, 0.2F);
		this.renderDistanceWeight = 5D;
	}

	public EntityClayMan(World world, double xPos, double yPos, double zPos, int team) {
		this(world);
		this.setPosition(xPos, yPos, zPos);
		this.setClayTeam(team);

	    int rareTextIndex = this.rand.nextInt(8196) == 0 ? rand.nextInt(3) == 0 ? 0 : rand.nextInt(Textures.CLAYMAN[1][this.getClayTeam()].length) : -1;
	    this.setRareTexture(rareTextIndex);
        this.setClayTexture(rand.nextInt(3) == 0 ? 0 : rand.nextInt(Textures.CLAYMAN[0][this.getClayTeam()].length));
	}

	public Entity getFollowEntity() {
        return this.targetFollow;
    }

	@Override
	public void knockBack(Entity par1Entity, float par2, double par3, double par5) {
	    super.knockBack(par1Entity, par2, par3, par5);
	    this.motionX *= 0.9D;
	    this.motionY *= 0.9D;
	    this.motionZ *= 0.9D;
	}

	@Override
	protected void entityInit() {
	    super.entityInit();

        this.dataWatcher.addObject(20, (short)0); // clay team
        this.dataWatcher.addObject(21, (int)0); // clay Texture
        this.dataWatcher.addObject(22, (int)-1); // rare Texture
        this.dataWatcher.addObject(23, (int)-1); // unique Texture
	}

	@Override
	public float getAIMoveSpeed() {
	    return this.moveSpeed * (this.entityToAttack != null || this.targetFollow != null ? 1.6F : 1.0F);
	}

	private AxisAlignedBB getTargetArea() {
		double radius = 8.0D;
		return AxisAlignedBB.getBoundingBox(
				this.posX - radius,
				this.posY - radius,
				this.posZ - radius,
				this.posX + radius,
				this.posY + radius,
				this.posZ + radius);
	}

	@Override
	public void onUpdate() {
		if( this.upgHash != this.upgrades.hashCode() && !this.worldObj.isRemote ) {
			this.upgHash = this.upgrades.hashCode();
			PacketSendSldUpgrades.sendUpgrades(this);
		}

		if( this.ticksExisted == 5 && this.worldObj.isRemote )
		    PacketSendSldUpgrades.requestUpgrades(this);

		super.onUpdate();
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
	    if( this.entityToAttack == null && !(par1DamageSource.getEntity() instanceof EntityPlayer) )
	        this.entityToAttack = par1DamageSource.getEntity();
	    if( par1DamageSource.getEntity() instanceof EntityPlayer )
	        par2 = 999;
        if( !this.worldObj.isRemote ) {
            for( int id : this.upgrades.keySet() ) {
                par2 = CSMModRegistry.clayUpgRegistry.getUpgradeByID(id).onHit(this, par1DamageSource, par2);
            }
        }
        if( this.riddenByEntity != null && this.rand.nextInt(3) == 0 ) {
            this.riddenByEntity.attackEntityFrom(par1DamageSource, par2);
            return false;
        }
	    return super.attackEntityFrom(par1DamageSource, par2);
	}

	@Override
	protected void updateEntityActionState() {
		super.updateEntityActionState();

		if( !this.worldObj.isRemote ) {
			if( this.entityToAttack == null ) {
				List<EntityClayMan> claymen = (List<EntityClayMan>)this.worldObj.getEntitiesWithinAABB(EntityClayMan.class, this.getTargetArea());
				for( EntityClayMan uberhaxornova : claymen ) {
					if( uberhaxornova.getClayTeam() == this.getClayTeam() ) continue;
					if( uberhaxornova.isDead ) continue;
					if( !this.canEntityBeSeen(uberhaxornova) ) continue;
					if( rand.nextInt(4) != 0 ) continue;

					this.entityToAttack = uberhaxornova;

                    for( int id : this.upgrades.keySet() )
                        this.entityToAttack = CSMModRegistry.clayUpgRegistry.getUpgradeByID(id).onTargeting(this, uberhaxornova);

                    if( this.entityToAttack != null && this.entityToAttack instanceof IUpgradeEntity ) {
                        for( int id2 : ((IUpgradeEntity)this.entityToAttack).getUpgrades() )
                            this.entityToAttack = CSMModRegistry.clayUpgRegistry.getUpgradeByID(id2).onTargeted((IUpgradeEntity)this.entityToAttack, this);
                    }

					break;
				}

				if( this.entityToAttack == null ) {
				    if( this.targetFollow == null ) {
    					List<EntityItem> items = (List<EntityItem>)this.worldObj.getEntitiesWithinAABB(EntityItem.class, this.getTargetArea());
    					items: for( EntityItem seamus : items ) {
    					    if( !this.canEntityBeSeen(seamus) ) continue;
    						for( IUpgradeItem upgrade : CSMModRegistry.clayUpgRegistry.getUpgrades() ) {
    							if( !CommonUsedStuff.areStacksEqualWithWCV(upgrade.getItemStack(this), seamus.getEntityItem()) ) continue;
    							if( this.upgrades.containsKey(CSMModRegistry.clayUpgRegistry.getIDByUpgrade(upgrade)) ) continue;
    							for( int id : this.upgrades.keySet() ) {
    								if( upgrade.isCompatibleWith(CSMModRegistry.clayUpgRegistry.getUpgradeByID(id)) )
    									continue;
    								continue items;
    							}
    							this.targetFollow = seamus;
    							break items;
    						}
    					}
					}
                    if( this.targetFollow == null && this.ridingEntity == null ) {
                        List<IMount> items = (List<IMount>)this.worldObj.getEntitiesWithinAABB(IMount.class, this.getTargetArea());
                        for( IMount mount : items ) {
                            if( !(mount instanceof EntityLivingBase) ) continue;
                            if( this.rand.nextInt(4) != 0 ) continue;
                            EntityLivingBase slyfox = (EntityLivingBase)mount;
                            if( !slyfox.canEntityBeSeen(this) ) continue;
                            if( slyfox.riddenByEntity != null ) continue;
                            this.targetFollow = slyfox;
                            break;
                        }
                    }

					if( this.targetFollow != null ) {
						if( this.targetFollow.isDead )
							this.targetFollow = null;
						else if( !this.canEntityBeSeen(this.targetFollow) )
                            this.targetFollow = null;
						else if( !hasPath() || rand.nextInt(10) == 0 )
							setPathToEntity(worldObj.getPathEntityToEntity(this.targetFollow, this, 8.0F, false, false, false, false));

						if( this.targetFollow instanceof EntityItem && this.targetFollow.getDistanceToEntity(this) < 0.5F ) {
							for( IUpgradeItem upgrade : CSMModRegistry.clayUpgRegistry.getUpgrades() ) {
								if( !CommonUsedStuff.areStacksEqualWithWCV(upgrade.getItemStack(this), ((EntityItem)this.targetFollow).getEntityItem()) ) continue;
                                if( this.upgrades.containsKey(CSMModRegistry.clayUpgRegistry.getIDByUpgrade(upgrade)) ) continue;
								NBTTagCompound nbt = new NBTTagCompound();
								this.upgrades.put(CSMModRegistry.clayUpgRegistry.getIDByUpgrade(upgrade), nbt);
								upgrade.onPickup(this, (EntityItem)this.targetFollow, nbt);
								this.targetFollow = null;
								break;
							}
						} else if( this.targetFollow instanceof IMount ) {
						    if( this.targetFollow.riddenByEntity != null ) {
						        this.targetFollow = null;
						    } else if( this.targetFollow.getDistanceToEntity(this) < 0.5D ) {
    						    this.mountEntity(this.targetFollow);
    						    this.targetFollow = null;
						    }
						}
					}
				}
			} else {
				if( this.entityToAttack.isDead ) {
					this.entityToAttack = null;
				} else {
                    float atkRng = 0.5F;
                    for( int id : this.upgrades.keySet() )
                        atkRng = Math.max(CSMModRegistry.clayUpgRegistry.getUpgradeByID(id).getTargetRange(this), atkRng);

					if( this.getDistanceToEntity(this.entityToAttack) < atkRng && this.entityToAttack instanceof EntityLivingBase && !this.entityToAttack.isEntityInvulnerable() ) {
						if( ((EntityLivingBase)this.entityToAttack).hurtTime == 0 ) {
    						float damage = 1.0F;
    						List<Integer> currUpgrades = new ArrayList<Integer>(this.upgrades.keySet());
    						for( int i : currUpgrades ) {
    							IUpgradeItem upg = CSMModRegistry.clayUpgRegistry.getUpgradeByID(i);
    							damage = upg.onAttack(this, (EntityLivingBase)this.entityToAttack, damage);
    						}
    						this.entityToAttack.attackEntityFrom(DamageSource.causeMobDamage(this), damage);
						}
					}
				}
			}
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound par1nbtTagCompound) {
		super.writeEntityToNBT(par1nbtTagCompound);

		par1nbtTagCompound.setInteger("clayTeam", this.getClayTeam());
		par1nbtTagCompound.setIntArray("clayTextures", new int[] {this.getClayTexture(), this.getRareTexture(), this.getUniqTexture() });

		NBTTagList upgrades = new NBTTagList("upgradeList");
		for( int id : this.upgrades.keySet() ) {
		    NBTTagCompound upgNbt = new NBTTagCompound("upgElem");
		    upgNbt.setInteger("id", id);
		    upgNbt.setTag("upgTag", this.upgrades.get(id));
		    upgrades.appendTag(upgNbt);
		}
		par1nbtTagCompound.setTag("upgrade", upgrades);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound par1nbtTagCompound) {
		super.readEntityFromNBT(par1nbtTagCompound);

		try { // workaround for older worlds
			this.setClayTeam(par1nbtTagCompound.getInteger("clayTeam"));

	        int[] textures = par1nbtTagCompound.getIntArray("clayTextures");
	        if( textures.length > 0 ) {
	            this.setClayTexture(textures[0]);
	            this.setRareTexture(textures[1]);
	            this.setUniqTexture(textures[2]);
	        }

	        this.upgrades.clear();
	        NBTTagList upgList = par1nbtTagCompound.getTagList("upgrade");
	        for( int i = 0; i < upgList.tagCount(); i++ ) {
	            NBTTagCompound upgNbt = (NBTTagCompound) upgList.tagAt(i);
	            this.upgrades.put(upgNbt.getInteger("id"), (NBTTagCompound) upgNbt.getTag("upgTag"));
	        }
            this.upgHash = -1;

		} catch(Exception e) {
		    FMLLog.log(CSMModRegistry.modID, Level.WARNING, "%s", "There was an exception during load of a clay soldier! Probably an old world?");
		    e.printStackTrace();
			this.setClayTeam(par1nbtTagCompound.getShort("clayTeam"));
		}
	}

	public int getClayTeam() {
		return this.dataWatcher.getWatchableObjectShort(20);
	}

	public void setClayTeam(int team) {
		this.dataWatcher.updateObject(20, (short)team);
	}

    public int getClayTexture() {
        return this.dataWatcher.getWatchableObjectInt(21);
    }

    public void setClayTexture(int index) {
        this.dataWatcher.updateObject(21, index);
    }

	public int getRareTexture() {
	    return this.dataWatcher.getWatchableObjectInt(22);
	}

	public void setRareTexture(int index) {
        this.dataWatcher.updateObject(22, index);
	}

    public int getUniqTexture() {
        return this.dataWatcher.getWatchableObjectInt(23);
    }

    public void setUniqTexture(int index) {
        this.dataWatcher.updateObject(23, index);
    }

	public boolean isJumping() {
	    return this.isJumping;
    }

	public float getMoveForward() {
	    return this.moveForward;
	}

    public float getMoveStrafing() {
        return this.moveStrafing;
    }

    public float getSpeedMulti() {
        return this.moveSpeed / this.baseMoveSpeed;
    }

    @Override
    public void onDeath(DamageSource par1DamageSource) {
        if( !this.fromNexus && !this.worldObj.isRemote ) {
            if( par1DamageSource.getEntity() instanceof EntityPlayer && ((EntityPlayer)par1DamageSource.getEntity()).capabilities.isCreativeMode ) {
                super.onDeath(par1DamageSource);
                return;
            }
            this.entityDropItem(new ItemStack(CSMModRegistry.greyDoll, 1, this.getClayTeam()), 0.0F);
            for( int id : this.upgrades.keySet() ) {
                ItemStack upgItm = CSMModRegistry.clayUpgRegistry.getUpgradeByID(id).getItemStack(this);
                this.entityDropItem(new ItemStack(upgItm.itemID, 1, upgItm.getItemDamage()), 0.0F);
            }
        }
        super.onDeath(par1DamageSource);
    }

    @Override
    protected void onDeathUpdate() {
        this.setDead();
    }

    public boolean spawnedFromNexus() {
        return this.fromNexus;
    }

    public void setNexusSpawn() {
        this.fromNexus = true;
    }

    public void swingArm() {
        if (!isSwinging) {
            isSwinging = true;
            prevSwingProgress = 0.0F;
            swingProgress = 0.0F;
        }
    }

    public void swingLeftArm() {
        if (!isSwingingLeft) {
            isSwingingLeft = true;
            swingLeft = 0.01F;
        }
    }

//    public int teamCloth(int teamNum) {
//        if (teamNum == 0) {
//            return 8;
//        } else if (teamNum == 1) {
//            return 14;
//        } else if (teamNum == 2) {
//            return 4;
//        } else if (teamNum == 3) {
//            return 13;
//        } else if (teamNum == 4) {
//            return 11;
//        } else if (teamNum == 5) {
//            return 1;
//        } else if (teamNum == 6) {
//            return 10;
//        } else if (teamNum == 7) {
//            return 6;
//        } else if (teamNum == 8) {
//            return 12;
//        } else if (teamNum == 9) {
//            return 0;
//        } else if (teamNum == 10) {
//            return 15;
//        } else if (teamNum == 11) {
//            return 9;
//        } else if (teamNum == 12) {
//            return 8;
//        } else if (teamNum == 13) {
//            return 5;
//        } else if (teamNum == 14) {
//            return 3;
//        } else if (teamNum == 15) {
//            return 2;
//        } else if (teamNum == 16) {
//            return 5;
//        } else if (teamNum == 17) {
//            return 1;
//        } else {
//            return 8;
//        }
//    }
//
//    public int teamDye(int teamNum) {
//        if (teamNum == 0) {
//            return 8;
//        } else if (teamNum == 1) {
//            return 1;
//        } else if (teamNum == 2) {
//            return 11;
//        } else if (teamNum == 3) {
//            return 2;
//        } else if (teamNum == 4) {
//            return 4;
//        } else if (teamNum == 5) {
//            return 14;
//        } else if (teamNum == 6) {
//            return 5;
//        } else if (teamNum == 7) {
//            return 9;
//        } else if (teamNum == 8) {
//            return 3;
//        } else if (teamNum == 9) {
//            return 15;
//        } else if (teamNum == 10) {
//            return 0;
//        } else if (teamNum == 11) {
//            return 6;
//        } else if (teamNum == 12) {
//            return 7;
//        } else if (teamNum == 13) {
//            return 10;
//        } else if (teamNum == 14) {
//            return 12;
//        } else if (teamNum == 15) {
//            return 13;
//        } else {
//            return 16;
//        }
//    }

	@Override
	public NBTTagCompound getUpgradeNBT(int id) {
	    if( this.upgrades.containsKey(id) && this.upgrades.get(id) == null )
	        this.upgrades.put(id, new NBTTagCompound());
		return this.upgrades.get(id);
	}

	@Override
	public EntityLivingBase getEntity() {
		return this;
	}

	@Override
	public void breakUpgrade(int id) {
		CSMModRegistry.clayUpgRegistry.getUpgradeByID(id).onBreak(this, this.rand);
		this.playSound("random.break", 0.2F, 0.8F + this.worldObj.rand.nextFloat() * 0.4F);
		this.upgrades.remove(id);
	}

	@Override
	public void addUpgrade(int id) {
		if( this.worldObj.isRemote ) {
			this.upgrades.put(id, null);
		} else if( !this.upgrades.containsKey(id) ) {
		    NBTTagCompound nbt = new NBTTagCompound();
		    IUpgradeItem upg = CSMModRegistry.clayUpgRegistry.getUpgradeByID(id);
		    this.upgrades.put(id, nbt);
		    upg.initUpgrade(this, nbt);
		}

	}

	@Override
	public void clearUpgrades() {
		this.upgHash = 0;
		this.upgrades.clear();
	}

    @Override
    public List<Integer> getUpgrades() {
        return new ArrayList(this.upgrades.keySet());
    }

    @Override
    public void deleteUpgrade(int id) {
        this.upgrades.remove(id);
    }

    @Override
    public boolean hasUpgrade(int id) {
        return this.upgrades.containsKey(id);
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }
}
