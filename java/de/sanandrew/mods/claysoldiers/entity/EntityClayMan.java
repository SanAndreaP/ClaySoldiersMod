/*******************************************************************************************************************
 * Authors:   SanAndreasP, CliffracerX
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported
 *            (http://creativecommons.org/licenses/by-nc-sa/3.0/)
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity;

import com.google.common.base.Function;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import cpw.mods.fml.common.FMLLog;
import de.sanandrew.core.manpack.util.javatuples.Pair;
import de.sanandrew.core.manpack.util.javatuples.Triplet;
import de.sanandrew.mods.claysoldiers.entity.mount.IMount;
import de.sanandrew.mods.claysoldiers.entity.projectile.ISoldierProjectile;
import de.sanandrew.mods.claysoldiers.network.PacketManager;
import de.sanandrew.mods.claysoldiers.network.ParticlePacketSender;
import de.sanandrew.mods.claysoldiers.util.*;
import de.sanandrew.mods.claysoldiers.util.soldier.ClaymanTeam;
import de.sanandrew.mods.claysoldiers.util.soldier.EnumMethodState;
import de.sanandrew.mods.claysoldiers.util.soldier.effect.ASoldierEffect;
import de.sanandrew.mods.claysoldiers.util.soldier.effect.SoldierEffectInst;
import de.sanandrew.mods.claysoldiers.util.soldier.effect.SoldierEffects;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.ASoldierUpgrade;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgrades;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;
import org.apache.commons.lang3.mutable.MutableDouble;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.apache.logging.log4j.Level;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class EntityClayMan
        extends EntityCreature
        implements IDisruptable
{
    private static final int DW_TEAM = 20;
    private static final int DW_MISC_COLOR = 21;
    private static final int DW_IS_TEXTURE_RARE_OR_UNIQUE = 22;
    private static final int DW_TEXTURE_INDEX = 23;

    public final SoldierCloakHelper cloakHelper = new SoldierCloakHelper();

    public ItemStack dollItem = null;
    public Triplet<Double, Double, Double> knockBack = Triplet.with(0.7D, 0.7D, 0.7D);
    public boolean canMove = true;
    public boolean nexusSpawn = false;

    private final Map<ASoldierUpgrade, SoldierUpgradeInst> p_upgrades = new ConcurrentHashMap<>();
    private final Map<ASoldierEffect, SoldierEffectInst> p_effects = new ConcurrentHashMap<>();
    private final long[] p_upgradeRenderFlags = new long[2];
    private final long[] p_effectRenderFlags = new long[2];

    private Entity p_targetFollow = null;
    private Collection p_entitiesInRange;

    public EntityClayMan(World world) {
        super(world);

        this.stepHeight = 0.1F;
        this.renderDistanceWeight = 5.0D;

        this.setSize(0.17F, 0.4F);

        this.yOffset = 0.01F;
        this.ignoreFrustumCheck = true;

        this.jumpMovementFactor = 0.2F;
    }

    public EntityClayMan(World world, String team) {
        this(world);
        this.dataWatcher.updateObject(DW_TEAM, team);

        this.setupTexture(this.rand.nextInt(8196) == 0, false);
    }

    @Override
    public void moveEntity(double motionX, double motionY, double motionZ) {
        if( this.canMove ) {
            super.moveEntity(motionX, motionY, motionZ);
        } else {
            super.moveEntity(0.0D, motionY > 0.0D ? motionY / 2.0D : motionY, 0.0D);
        }
    }

    @Override
    public void disrupt() {
        if( !this.getCustomNameTag().startsWith("[UNDISRUPTABLE]") ) {
            this.attackEntityFrom(IDisruptable.DISRUPT_DAMAGE, 99999);
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        this.cloakHelper.onUpdate(this.posX, this.posY, this.posZ);

        if( !this.canMove ) {
            this.motionX = 0.0F;
            this.motionZ = 0.0F;
            this.isJumping = false;
        }

        this.canMove = true;

        Iterator<Map.Entry<ASoldierUpgrade, SoldierUpgradeInst>> iterUpgrades = p_upgrades.entrySet().iterator();
        while( iterUpgrades.hasNext() ) {
            if( !this.worldObj.isRemote ) {
                SoldierUpgradeInst upg = iterUpgrades.next().getValue();
                if( upg.getUpgrade().onUpdate(this, upg) ) {
                    iterUpgrades.remove();
                }
            } else {
                SoldierUpgradeInst upg = iterUpgrades.next().getValue();
                upg.getUpgrade().onClientUpdate(this, upg);
            }
        }
        Iterator<Map.Entry<ASoldierEffect, SoldierEffectInst>> iterEffects = p_effects.entrySet().iterator();
        while( iterEffects.hasNext() ) {
            if( !this.worldObj.isRemote ) {
                SoldierEffectInst upg = iterEffects.next().getValue();
                if( upg.getEffect().onUpdate(this, upg) ) {
                    iterEffects.remove();
                }
            } else {
                SoldierEffectInst upg = iterEffects.next().getValue();
                upg.getEffect().onClientUpdate(this, upg);
            }
        }

        if( (ticksExisted % 5) == 0 ) {
            this.updateUpgradeEffectRenders();
        }

    }

    @Override
    public boolean canBreatheUnderwater() {
        return this.hasUpgrade(SoldierUpgrades.UPG_LILYPADS);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);

        nbt.setString("team", this.getClayTeam());
        nbt.setByte("miscColor", this.dataWatcher.getWatchableObjectByte(DW_MISC_COLOR));
        nbt.setByte("isRareOrUnique", this.dataWatcher.getWatchableObjectByte(DW_IS_TEXTURE_RARE_OR_UNIQUE));
        nbt.setInteger("textureIndex", this.dataWatcher.getWatchableObjectInt(DW_TEXTURE_INDEX));
        nbt.setBoolean("canMove", this.canMove);
        nbt.setBoolean("nexusSpawned", this.nexusSpawn);

        if( this.dollItem != null ) {
            NBTTagCompound stackNbt = new NBTTagCompound();
            this.dollItem.writeToNBT(stackNbt);
            nbt.setTag("dollItem", stackNbt);
        }

        NBTTagList upgNbtList = new NBTTagList();
        for( SoldierUpgradeInst upg : this.p_upgrades.values() ) {
            NBTTagCompound savedUpg = new NBTTagCompound();
            savedUpg.setString("name", SoldierUpgrades.getName(upg.getUpgrade()));
            savedUpg.setTag("data", upg.getNbtTag());
            NBTTagCompound upgItem = upg.saveStoredItemToNBT();
            if( upgItem != null ) {
                savedUpg.setTag("item", upgItem);
            }
            upgNbtList.appendTag(savedUpg);
        }
        nbt.setTag("upgrade", upgNbtList);

        NBTTagList effNbtList = new NBTTagList();
        for( SoldierEffectInst eff : this.p_effects.values() ) {
            NBTTagCompound savedEff = new NBTTagCompound();
            savedEff.setString("name", SoldierEffects.getEffectName(eff.getEffect()));
            savedEff.setTag("data", eff.getNbtTag());
            effNbtList.appendTag(savedEff);
        }
        nbt.setTag("effect", effNbtList);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);

        this.dataWatcher.updateObject(DW_TEAM, nbt.getString("team"));

        this.dataWatcher.updateObject(DW_MISC_COLOR, nbt.getByte("miscColor"));
        this.dataWatcher.updateObject(DW_IS_TEXTURE_RARE_OR_UNIQUE, nbt.getByte("isRareOrUnique"));
        this.dataWatcher.updateObject(DW_TEXTURE_INDEX, nbt.getInteger("textureIndex"));

        this.canMove = nbt.getBoolean("canMove");
        this.nexusSpawn = nbt.getBoolean("nexusSpawned");

        if( nbt.hasKey("dollItem") ) {
            this.dollItem = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("dollItem"));
        }

        NBTTagList upgNbtList = nbt.getTagList("upgrade", NBT.TAG_COMPOUND);
        for( int i = 0; i < upgNbtList.tagCount(); i++ ) {
            NBTTagCompound savedUpg = upgNbtList.getCompoundTagAt(i);
            SoldierUpgradeInst upgInst = new SoldierUpgradeInst(SoldierUpgrades.getUpgrade(savedUpg.getString("name")));
            upgInst.setNbtTag(savedUpg.getCompoundTag("data"));
            if( savedUpg.hasKey("item") ) {
                upgInst.readStoredItemFromNBT(savedUpg.getCompoundTag("item"));
            }
            this.p_upgrades.put(upgInst.getUpgrade(), upgInst);
        }
        NBTTagList effNbtList = nbt.getTagList("effect", NBT.TAG_COMPOUND);
        for( int i = 0; i < effNbtList.tagCount(); i++ ) {
            NBTTagCompound savedEff = effNbtList.getCompoundTagAt(i);
            SoldierEffectInst effInst = new SoldierEffectInst(SoldierEffects.getEffect(savedEff.getString("name")));
            effInst.setNbtTag(savedEff.getCompoundTag("data"));
            this.p_effects.put(effInst.getEffect(), effInst);
        }
    }

    @Override
    public Entity getEntityToAttack() {
        return this.entityToAttack;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float damage) {
        if( !(source.getEntity() instanceof EntityPlayer) && source != IDisruptable.DISRUPT_DAMAGE ) {
            if( this.ridingEntity != null && rand.nextInt(4) == 0 ) {
                this.ridingEntity.attackEntityFrom(source, damage);
                return false;
            }
        } else {
            damage = 10000.0F;
        }

        if( !this.worldObj.isRemote ) {
            for( Entry<ASoldierUpgrade, SoldierUpgradeInst> upgrade : this.p_upgrades.entrySet() ) {
                SoldierUpgradeInst upg = upgrade.getValue();
                MutableFloat newDamage = new MutableFloat(damage);
                if( upg.getUpgrade().onSoldierHurt(this, upg, source, newDamage) ) {
                    return false;
                } else {
                    damage = newDamage.floatValue();
                }
            }
        }

        return super.attackEntityFrom(source, damage);
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);

        if( !this.worldObj.isRemote ) {
            if( damageSource.isFireDamage() && this.dollItem != null ) {
                ItemStack brickItem = new ItemStack(RegistryItems.dollBrick, this.dollItem.stackSize);
                brickItem.setTagCompound(this.dollItem.getTagCompound());
                this.dollItem = brickItem;
            }

            ArrayList<ItemStack> drops = new ArrayList<>();

            if( !this.nexusSpawn ) {
                if( this.dollItem != null ) {
                    drops.add(this.dollItem.copy());
                }

                for( SoldierUpgradeInst upg : this.p_upgrades.values() ) {
                    upg.getUpgrade().onItemDrop(this, upg, drops);
                }

                drops.removeAll(Collections.singleton(null));
                for( ItemStack drop : drops ) {
                    this.entityDropItem(drop, 0.0F);
                }
            }

            for( SoldierUpgradeInst upg : this.p_upgrades.values() ) {
                upg.getUpgrade().onSoldierDeath(this, upg, damageSource);
            }

            for( SoldierEffectInst eff : this.p_effects.values() ) {
                eff.getEffect().onSoldierDeath(this, eff, damageSource);
            }
        }
    }

    @Override
    public void knockBack(Entity par1Entity, float par2, double par3, double par5) {
        if( !this.canMove ) {
            return;
        }

        super.knockBack(par1Entity, par2, par3, par5);
        this.motionX *= knockBack.getValue0();
        this.motionY *= knockBack.getValue1();
        this.motionZ *= knockBack.getValue2();
    }

    @Override
    public float getAIMoveSpeed() {
        MutableFloat speed = new MutableFloat(0.5F);

        for( SoldierUpgradeInst upg : this.p_upgrades.values() ) {
            upg.getUpgrade().getAiMoveSpeed(this, upg, speed);
        }
        for( SoldierEffectInst eff : this.p_effects.values() ) {
            eff.getEffect().getAiMoveSpeed(this, eff, speed);
        }

        return speed.floatValue();
    }

    @Override
    public boolean canEntityBeSeen(Entity target) {
        return this.worldObj.func_147447_a(Vec3.createVectorHelper(this.posX, this.posY + this.getEyeHeight(), this.posZ),
                                           Vec3.createVectorHelper(target.posX, target.posY + target.getEyeHeight(), target.posZ),
                                           false, true, false
        ) == null;
    }

    @Override
    public boolean canBePushed() {
        return this.canMove;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();

        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(ModConfig.soldierBaseHealth);
    }

    @Override
    protected void entityInit() {
        super.entityInit();

        this.dataWatcher.addObject(DW_TEAM, ClaymanTeam.NULL_TEAM.getTeamName());
        this.dataWatcher.addObject(DW_MISC_COLOR, (byte) 15);
        this.dataWatcher.addObject(DW_IS_TEXTURE_RARE_OR_UNIQUE, (byte) 0);
        this.dataWatcher.addObject(DW_TEXTURE_INDEX, 0);
    }

    @Override
    protected String getLivingSound() {
        return null;
    }

    @Override
    protected String getHurtSound() {
        return ModConfig.useOldHurtSound ? "claysoldiers:mob.soldier.hurt" : "dig.gravel";
    }

    @Override
    protected String getDeathSound() {
        return "step.gravel";
    }

    @Override
    protected void onDeathUpdate() {
        this.deathTime = 20;
        this.setDead();
        ParticlePacketSender.sendSoldierDeathFx(this.posX, this.posY, this.posZ, this.dimension, this.getClayTeam());

    }

    @Override
    protected boolean isAIEnabled() {
        return false;
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Override
    protected boolean interact(EntityPlayer player) {
        if( this.worldObj.isRemote ) {
            ClaySoldiersMod.proxy.switchClayCam(true, this);
        }

        return super.interact(player);
    }

    @Override
    protected void updateEntityActionState() {
        //BUGFIX: fixes movement in blocks w/o collision box (snow layer, torches, tall grass, possibly cobweb?, etc.)
        if( !this.hasPath() ) {
            if( this.entityToAttack != null ) {
                this.setPathToEntity(BugfixHelper.getPathEntityToEntity(this.worldObj, this, this.entityToAttack, 16.0F, true, false, false, true));
            } else if( this.p_targetFollow != null ) {
                this.setPathToEntity(BugfixHelper.getPathEntityToEntity(this.worldObj, this, this.p_targetFollow, 16.0F, true, false, false, true));
            } else if( (this.rand.nextInt(180) == 0 || this.rand.nextInt(120) == 0 || this.fleeingTick > 0) && this.entityAge < 100 ) {
                this.updateWanderPath();
            }
        }

        super.updateEntityActionState();

        if( !this.worldObj.isRemote ) {
            this.p_entitiesInRange = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getTargetArea());

            if( this.entityToAttack == null ) {
                if( rand.nextInt(4) != 0 && p_targetFollow == null ) {
                    Collection<EntityClayMan> claymen = this.getSoldiersInRange();
                    for( EntityClayMan uberhaxornova : claymen ) {
                        if( uberhaxornova.isDead || rand.nextInt(3) != 0 ) {
                            continue;
                        }

                        if( !this.checkIfValidTarget(uberhaxornova) ) {
                            continue;
                        }

                        this.entityToAttack = uberhaxornova;

                        break;
                    }
                } else {
                    if( this.p_targetFollow == null ) {
                        Collection<EntityItem> items = this.getItemsInRange();
                        items:
                        for( EntityItem seamus : items ) {
                            if( !this.canEntityBeSeen(seamus) ) {
                                continue;
                            }

                            ASoldierUpgrade upgrade = SoldierUpgrades.getUpgrade(seamus.getEntityItem());
                            if( upgrade != null ) {
                                if( this.hasUpgrade(upgrade) || !upgrade.canBePickedUp(this, seamus.getEntityItem(), null) ) {
                                    continue;
                                } else {
                                    for( SoldierUpgradeInst upgradeInst : this.p_upgrades.values() ) {
                                        if( upgrade == upgradeInst.getUpgrade() || !upgrade.canBePickedUp(this, seamus.getEntityItem(), upgradeInst.getUpgrade()) ) {
                                            continue items;
                                        }
                                    }
                                }
                            } else {
                                continue;
                            }

                            this.p_targetFollow = seamus;

                            break;
                        }
                    } else {
                        if( this.p_targetFollow.isDead ) {
                            this.p_targetFollow = null;
                        } else if( !this.canEntityBeSeen(this.p_targetFollow) ) {
                            this.p_targetFollow = null;
                        }

                        if( this.p_targetFollow instanceof EntityItem && this.p_targetFollow.getDistanceToEntity(this) < 0.5F ) {
                            EntityItem itemEntity = (EntityItem) this.p_targetFollow;
                            ASoldierUpgrade upgrade = SoldierUpgrades.getUpgrade(itemEntity.getEntityItem());
                            if( upgrade != null ) {
                                this.addUpgrade(upgrade, itemEntity.getEntityItem());

                                if( itemEntity.getEntityItem().stackSize <= 0 ) {
                                    itemEntity.setDead();
                                }

                                this.p_targetFollow = null;
                            }
                        } else if( this.p_targetFollow instanceof IMount ) {
                            if( this.p_targetFollow.riddenByEntity != null ) {
                                this.p_targetFollow = null;
                            } else if( this.p_targetFollow.getDistanceToEntity(this) < 0.5D ) {
                                this.mountEntity(this.p_targetFollow);
                                this.p_targetFollow = null;
                            }
                        }
                    }
                    if( this.p_targetFollow == null && this.ridingEntity == null ) {
                        Collection<IMount> items = this.getMountsInRange();
                        for( IMount mount : items ) {
                            EntityLivingBase slyfox = (EntityLivingBase) mount;
                            if( this.rand.nextInt(4) != 0 || !this.canEntityBeSeen(slyfox) || slyfox.riddenByEntity != null ) {
                                continue;
                            }

                            this.p_targetFollow = slyfox;
                            break;
                        }
                    }
                }
            } else {
                if( this.entityToAttack.isDead || !this.canEntityBeSeen(this.entityToAttack)
                    || (this.entityToAttack instanceof EntityClayMan && !this.checkIfValidTarget((EntityClayMan) this.entityToAttack)) )
                {
                    this.entityToAttack = null;
                } else if( this.attackTime == 0 ) {
                    this.attackTime = 5;

                    MutableFloat atkRng = new MutableFloat(this.riddenByEntity != null ? 0.6F : 0.7F);

                    for( SoldierUpgradeInst upg : this.p_upgrades.values() ) {
                        upg.getUpgrade().getAttackRange(this, upg, this.entityToAttack, atkRng);
                    }

                    if( this.getDistanceToEntity(this.entityToAttack) < atkRng.floatValue() && this.entityToAttack instanceof EntityLivingBase
                            && !this.entityToAttack.isEntityInvulnerable() ) {
                        EntityLivingBase target = (EntityLivingBase) this.entityToAttack;
                        if( target.hurtTime == 0 ) {
                            MutableFloat damage = new MutableFloat(ModConfig.soldierBaseDamage);
                            if( target instanceof EntityClayMan ) {
                                EntityClayMan soldierTarget = (EntityClayMan) target;
                                soldierTarget.knockBack = Triplet.with(0.8D, 0.8D, 0.8D);
                                for( SoldierUpgradeInst upg : this.p_upgrades.values() ) {
                                    upg.getUpgrade().onSoldierAttack(this, upg, soldierTarget, damage);
                                }
                            }

                            if( target.attackEntityFrom(DamageSource.causeMobDamage(this), damage.getValue()) && target instanceof EntityClayMan ) {
                                for( SoldierUpgradeInst upg : this.p_upgrades.values() ) {
                                    upg.getUpgrade().onSoldierDamage(this, upg, (EntityClayMan) target);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void updateWanderPath() {
        this.worldObj.theProfiler.startSection("stroll");
        boolean blockFound = false;
        int x = -1;
        int y = -1;
        int z = -1;
        float maxPathWeight = -99999.0F;

        for( int i = 0; i < 10; ++i ) {
            int currX = MathHelper.floor_double(this.posX + this.rand.nextInt(13) - 6.0D);
            int currY = MathHelper.floor_double(this.posY + this.rand.nextInt(7) - 3.0D);
            int currZ = MathHelper.floor_double(this.posZ + this.rand.nextInt(13) - 6.0D);
            float pathWeight = this.getBlockPathWeight(currX, currY, currZ);

            if( pathWeight > maxPathWeight ) {
                maxPathWeight = pathWeight;
                x = currX;
                y = currY;
                z = currZ;
                blockFound = true;
            }
        }

        if( blockFound ) {
            this.setPathToEntity(BugfixHelper.getEntityPathToXYZ(this.worldObj, this, x, y, z, 10.0F, true, false, false, true));
        }

        this.worldObj.theProfiler.endSection();
    }

    public void setupTexture(boolean isRare, boolean isUnique) {
        ClaymanTeam team = ClaymanTeam.getTeam(this.getClayTeam());
        if( isUnique && team.getUniqueTextures().length > 0 ) {
            this.dataWatcher.updateObject(DW_IS_TEXTURE_RARE_OR_UNIQUE, (byte) 2);
            this.dataWatcher.updateObject(DW_TEXTURE_INDEX, this.rand.nextInt(team.getUniqueTextures().length));
        } else if( isRare && team.getRareTextures().length > 0 ) {
            this.dataWatcher.updateObject(DW_IS_TEXTURE_RARE_OR_UNIQUE, (byte) 1);
            this.dataWatcher.updateObject(DW_TEXTURE_INDEX, this.rand.nextInt(team.getRareTextures().length));
        } else {
            this.dataWatcher.updateObject(DW_IS_TEXTURE_RARE_OR_UNIQUE, (byte) 0);
            this.dataWatcher.updateObject(DW_TEXTURE_INDEX, this.rand.nextInt(team.getDefaultTextures().length));
        }
    }

    public boolean isJumping() {
        return this.isJumping;
    }

    public double getLookRangeRad() {
        MutableDouble radiusMT = new MutableDouble(8.0D);

        for( SoldierUpgradeInst upg : this.p_upgrades.values() ) {
            upg.getUpgrade().getLookRange(this, upg, radiusMT);
        }

        return radiusMT.getValue();
    }

    //TODO shove Functions into a seperate class file

    @SuppressWarnings("unchecked")
    public Collection<EntityClayMan> getSoldiersInRange() {
        return Collections2.transform(Collections2.filter(this.p_entitiesInRange, Predicates.instanceOf(EntityClayMan.class)),
                                      new Function<Object, EntityClayMan>()
                                      {
                                          @Override
                                          public EntityClayMan apply(Object input) {
                                              return (EntityClayMan) input;
                                          }
                                      }
        );
    }

    @SuppressWarnings("unchecked")
    public Collection<EntityItem> getItemsInRange() {
        return Collections2.transform(Collections2.filter(this.p_entitiesInRange, Predicates.instanceOf(EntityItem.class)),
                                      new Function<Object, EntityItem>()
                                      {
                                          @Override
                                          public EntityItem apply(Object input) {
                                              return (EntityItem) input;
                                          }
                                      }
        );
    }

    @SuppressWarnings("unchecked")
    public Collection<IMount> getMountsInRange() {
        return Collections2.transform(Collections2.filter(this.p_entitiesInRange, Predicates.instanceOf(IMount.class)),
                                      new Function<Object, IMount>()
                                      {
                                          @Override
                                          public IMount apply(Object input) {
                                              return (IMount) input;
                                          }
                                      }
        );
    }

    public String getClayTeam() {
        return this.dataWatcher.getWatchableObjectString(DW_TEAM);
    }

    public ResourceLocation getTexture() {
        if( this.dataWatcher.getWatchableObjectByte(DW_IS_TEXTURE_RARE_OR_UNIQUE) == 2 ) {
            return ClaymanTeam.getTeam(this.dataWatcher.getWatchableObjectString(DW_TEAM))
                              .getUniqueTextures()[this.dataWatcher.getWatchableObjectInt(DW_TEXTURE_INDEX)];
        } else if( this.dataWatcher.getWatchableObjectByte(DW_IS_TEXTURE_RARE_OR_UNIQUE) == 1 ) {
            return ClaymanTeam.getTeam(this.dataWatcher.getWatchableObjectString(DW_TEAM))
                              .getRareTextures()[this.dataWatcher.getWatchableObjectInt(DW_TEXTURE_INDEX)];
        } else {
            return ClaymanTeam.getTeam(this.dataWatcher.getWatchableObjectString(DW_TEAM))
                              .getDefaultTextures()[this.dataWatcher.getWatchableObjectInt(DW_TEXTURE_INDEX)];
        }
    }

    public void updateUpgradeEffectRenders() {
        if( this.worldObj.isRemote ) {
            for( Byte renderId : SoldierUpgrades.getRegisteredRenderIds() ) {
                long renderFlag = 1L << (renderId % 64);
                int renderStorageDw = (renderId / 64);
                long dwValue = this.p_upgradeRenderFlags[renderStorageDw];

                ASoldierUpgrade upgrade = SoldierUpgrades.getUpgrade(renderId);
                if( (dwValue & renderFlag) == renderFlag ) {
                    if( !this.p_upgrades.containsKey(upgrade) ) {
                        this.p_upgrades.put(upgrade, new SoldierUpgradeInst(upgrade));
                    }
                } else {
                    this.p_upgrades.remove(upgrade);
                }
            }

            for( byte renderId : SoldierEffects.getRegisteredRenderIds() ) {
                long renderFlag = 1L << (renderId % 64);
                int renderStorageDw = (renderId / 64);
                long dwValue = this.p_effectRenderFlags[renderStorageDw];

                ASoldierEffect effect = SoldierEffects.getEffect(renderId);

                if( (dwValue & renderFlag) == renderFlag ) {
                    if( !this.p_effects.containsKey(effect) ) {
                        SoldierEffectInst effectInst = new SoldierEffectInst(effect);
                        this.p_effects.put(effect, effectInst);
                        effect.onConstruct(this, effectInst);
                    }
                } else {
                    this.p_effects.remove(effect);
                }
            }
        } else {
            this.p_upgradeRenderFlags[0] = 0;
            this.p_upgradeRenderFlags[1] = 0;
            this.p_effectRenderFlags[0] = 0;
            this.p_effectRenderFlags[1] = 0;

            for( SoldierUpgradeInst upgInst : this.p_upgrades.values() ) {
                byte renderId = SoldierUpgrades.getRenderId(upgInst.getUpgrade());
                if( renderId >= 0 ) {
                    long renderFlag = 1L << (renderId % 64);
                    int renderStorageDw = renderId / 64;
                    long prevDwValue = p_upgradeRenderFlags[renderStorageDw];

                    p_upgradeRenderFlags[renderStorageDw] = prevDwValue | renderFlag;
                }
            }

            List<Pair<Byte, NBTTagCompound>> effectNbtToClt = new ArrayList<>();
            for( SoldierEffectInst effInst : this.p_effects.values() ) {
                byte renderId = SoldierEffects.getRenderId(effInst.getEffect());
                if( renderId >= 0 ) {
                    long renderFlag = 1L << (renderId % 64);
                    int renderStorageDw = renderId / 64;
                    long prevDwValue = this.p_effectRenderFlags[renderStorageDw];

                    this.p_effectRenderFlags[renderStorageDw] = prevDwValue | renderFlag;
                    if( effInst.getEffect().shouldNbtSyncToClient(this, effInst) ) {
                        effectNbtToClt.add(Pair.with(renderId, effInst.getNbtTag()));
                    }
                }
            }

            List<Pair<Byte, NBTTagCompound>> upgradeNbtToClt = new ArrayList<>();
            for( SoldierUpgradeInst upgInst : this.p_upgrades.values() ) {
                byte renderId = SoldierUpgrades.getRenderId(upgInst.getUpgrade());
                if( renderId >= 0 ) {
                    long renderFlag = 1L << (renderId % 64);
                    int renderStorageDw = renderId / 64;
                    long prevDwValue = this.p_upgradeRenderFlags[renderStorageDw];

                    this.p_upgradeRenderFlags[renderStorageDw] = prevDwValue | renderFlag;
                    if( upgInst.getUpgrade().shouldNbtSyncToClient(this, upgInst) ) {
                        upgradeNbtToClt.add(Pair.with(renderId, upgInst.getNbtTag()));
                    }
                }
            }

            PacketManager.sendToAllAround(PacketManager.PKG_SOLDIER_RENDERS, this.dimension, this.posX, this.posY, this.posZ, 64.0D,
                                          Triplet.with(this.getEntityId(), this.p_upgradeRenderFlags, this.p_effectRenderFlags)
            );

            for( Pair<Byte, NBTTagCompound> effNbt : effectNbtToClt ) {
                PacketManager.sendToAllAround(PacketManager.PKG_SOLDIER_EFFECT_NBT, this.dimension, this.posX, this.posY, this.posZ, 64.0D,
                                                Triplet.with(this.getEntityId(), effNbt.getValue0(), effNbt.getValue1())
                );
            }

            for( Pair<Byte, NBTTagCompound> upgNbt : upgradeNbtToClt ) {
                PacketManager.sendToAllAround(PacketManager.PKG_SOLDIER_UPGRADE_NBT, this.dimension, this.posX, this.posY, this.posZ, 64.0D,
                                                Triplet.with(this.getEntityId(), upgNbt.getValue0(), upgNbt.getValue1())
                );
            }
        }
    }

    public void onProjectileHit(ISoldierProjectile<? extends EntityThrowable> projectile, MovingObjectPosition movObjPos) {
        for( SoldierUpgradeInst upg : this.p_upgrades.values() ) {
            upg.getUpgrade().onProjectileHit(this, upg, movObjPos, projectile);
        }
    }

    public boolean canTargetSoldier(EntityClayMan target) {
        return this.canTargetSoldier(target, true);
    }

    public boolean canTargetSoldier(EntityClayMan target, boolean withUpgradeCheck) {
        if( this.entityToAttack == null || this.entityToAttack.isDead ) {
            if( withUpgradeCheck ) {
                if( !this.checkIfValidTarget(target) ) {
                    return false;
                }
            }
            this.entityToAttack = target;
            return true;
        } else {
            return false;
        }
    }

    public boolean hasUpgrade(ASoldierUpgrade upgrade) {
        return this.p_upgrades.containsKey(upgrade);
    }

    public boolean hasUpgrade(String upgradeName) {
        return this.hasUpgrade(SoldierUpgrades.getUpgrade(upgradeName));
    }

    public boolean hasUpgrade(Class upgradeClass) {
        for( ASoldierUpgrade upgrade : this.p_upgrades.keySet() ) {
            if( upgradeClass.isInstance(upgrade) ) {
                return true;
            }
        }
        return false;
    }

    public ASoldierUpgrade[] getAvailableUpgrades() {
        return this.p_upgrades.keySet().toArray(new ASoldierUpgrade[this.p_upgrades.size()]);
    }

    public SoldierUpgradeInst getUpgrade(ASoldierUpgrade upgrade) {
        if( this.hasUpgrade(upgrade) ) {
            return this.p_upgrades.get(upgrade);
        } else {
            return null;
        }
    }

    public void removeEffect(ASoldierEffect effect) {
        if( this.hasEffect(effect) ) {
            this.p_effects.remove(effect);
        }
    }

    public Entity getTargetFollowing() {
        return this.p_targetFollow;
    }

    public void setTargetFollowing(Entity entity) {
        this.p_targetFollow = entity;
    }

    public SoldierUpgradeInst addUpgrade(ASoldierUpgrade upgrade) {
        return this.addUpgrade(upgrade, null);
    }

    public SoldierUpgradeInst addUpgrade(ASoldierUpgrade upgrade, ItemStack stack) {
        if( !this.hasUpgrade(upgrade) ) {
            SoldierUpgradeInst upgradeInst = new SoldierUpgradeInst(upgrade);

            upgrade.onConstruct(this, upgradeInst);

            if( stack != null ) {
                upgrade.onPickup(this, upgradeInst, stack);
            }

            for( SoldierUpgradeInst inst : this.p_upgrades.values() ) {
                inst.getUpgrade().onUpgradeAdded(this, inst, upgradeInst);
            }

            this.p_upgrades.put(upgrade, upgradeInst);

            return upgradeInst;
        } else {
            return null;
        }
    }

    public int getMiscColor() {
        return ItemDye.field_150922_c[getMiscColorIndex()];
    }

    public int getMiscColorIndex() {
        return this.dataWatcher.getWatchableObjectByte(DW_MISC_COLOR);
    }

    public void setMiscColorIndex(int colorIndex) {
        if( colorIndex >= 0 && colorIndex < ItemDye.field_150922_c.length ) {
            this.dataWatcher.updateObject(DW_MISC_COLOR, (byte) colorIndex);
        }
    }

    public SoldierEffectInst addEffect(ASoldierEffect effect) {
        if( !this.hasEffect(effect) ) {
            SoldierEffectInst effectInst = new SoldierEffectInst(effect);
            effect.onConstruct(this, effectInst);

            for(SoldierEffectInst existEffect : this.p_effects.values()) {
                if( !effect.isCompatibleWith(this, effectInst, existEffect) ) {
                    return null;
                }
            }

            this.p_effects.put(effect, effectInst);
            return effectInst;
        } else {
            return null;
        }
    }

    public SoldierEffectInst getEffect(ASoldierEffect effect) {
        if( this.hasEffect(effect) ) {
            return this.p_effects.get(effect);
        } else {
            return null;
        }
    }

    public SoldierEffectInst getEffect(String effectName) {
        return this.getEffect(SoldierEffects.getEffect(effectName));
    }

    public void applyRenderFlags(long... flags) {
        this.p_upgradeRenderFlags[0] = flags[0];
        this.p_upgradeRenderFlags[1] = flags[1];
        this.p_effectRenderFlags[0] = flags[2];
        this.p_effectRenderFlags[1] = flags[3];
    }

    public boolean hasEffect(ASoldierEffect effect) {
        return this.p_effects.containsKey(effect);
    }

    public boolean hasEffect(String effectName) {
        return this.hasEffect(SoldierEffects.getEffect(effectName));
    }

    public void throwSomethingAtEnemy(EntityLivingBase entity, Class<? extends ISoldierProjectile<? extends EntityThrowable>> projClass, boolean homing) {
        double d = entity.posX - posX;
        double d1 = entity.posZ - posZ;

        try {
            ISoldierProjectile<? extends EntityThrowable> projectile = projClass.getConstructor(World.class, EntityLivingBase.class).newInstance(this.worldObj, this);
            projectile.initProjectile(entity, homing, this.getClayTeam());
            EntityThrowable throwable = projectile.getProjectileEntity();
            throwable.posY += 0.1D;
            double d2 = (entity.posY + entity.getEyeHeight()) - 0.10000000298023224D - throwable.posY;
            float f1 = MathHelper.sqrt_double(d * d + d1 * d1) * 0.2F;
            this.worldObj.spawnEntityInWorld(throwable);
            throwable.setThrowableHeading(d, d2 + f1, d1, 0.6F, 12.0F);
            this.attackTime = 30;
            this.hasAttacked = true;
        } catch( InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e ) {
            FMLLog.log(ClaySoldiersMod.MOD_LOG, Level.ERROR, "%1$s cannot be instantiated! %1$s is not thrown to target!", projClass.getName());
            e.printStackTrace();
        }
    }

    /**
     * checks if the soldier is a valid target
     * @param target the soldier as a target-candidate
     * @return true, if the soldier is a valid target
     */
    private boolean checkIfValidTarget(EntityClayMan target) {
        for( SoldierEffectInst eff : this.p_effects.values() ) {
            EnumMethodState result = eff.getEffect().onTargeting(this, eff, target);
            if( result == EnumMethodState.DENY ) {
                return false;
            } else if( result == EnumMethodState.ALLOW ) {
                return true;
            }
        }

        for( SoldierUpgradeInst upg : this.p_upgrades.values() ) {
            EnumMethodState result = upg.getUpgrade().onTargeting(this, upg, target);
            if( result == EnumMethodState.DENY ) {
                return false;
            } else if( result == EnumMethodState.ALLOW ) {
                return true;
            }
        }

        for( SoldierUpgradeInst upg : target.p_upgrades.values() ) {
            EnumMethodState result = upg.getUpgrade().onBeingTargeted(target, upg, this);
            if( result == EnumMethodState.DENY ) {
                return false;
            } else if( result == EnumMethodState.ALLOW ) {
                return true;
            }
        }

        return !target.getClayTeam().equals(this.getClayTeam()) && this.canEntityBeSeen(target);
    }

    private AxisAlignedBB getTargetArea() {
        double radius = getLookRangeRad();

        return AxisAlignedBB.getBoundingBox(
                this.posX - radius,
                this.posY - radius,
                this.posZ - radius,
                this.posX + radius,
                this.posY + radius,
                this.posZ + radius
        );
    }
}
