/*******************************************************************************************************************
 * Authors:   SanAndreasP, CliffracerX
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported
 *            (http://creativecommons.org/licenses/by-nc-sa/3.0/)
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.core.manpack.util.NbtTypes;
import de.sanandrew.core.manpack.util.javatuples.Pair;
import de.sanandrew.core.manpack.util.javatuples.Triplet;
import de.sanandrew.mods.claysoldiers.entity.mount.IMount;
import de.sanandrew.mods.claysoldiers.entity.projectile.ISoldierProjectile;
import de.sanandrew.mods.claysoldiers.network.PacketProcessor;
import de.sanandrew.mods.claysoldiers.network.ParticlePacketSender;
import de.sanandrew.mods.claysoldiers.util.BugfixHelper;
import de.sanandrew.mods.claysoldiers.util.CSM_Main;
import de.sanandrew.mods.claysoldiers.util.IDisruptable;
import de.sanandrew.mods.claysoldiers.util.ModConfig;
import de.sanandrew.mods.claysoldiers.util.soldier.ClaymanTeam;
import de.sanandrew.mods.claysoldiers.util.soldier.MethodState;
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
import org.apache.commons.lang3.mutable.MutableFloat;
import org.apache.logging.log4j.Level;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EntityClayMan
        extends EntityCreature
        implements IDisruptable
{
    private static final int DW_TEAM = 20;
    private static final int DW_MISC_COLOR = 21;
    private static final int DW_IS_TEXTURE_RARE_OR_UNIQUE = 22;
    private static final int DW_TEXTURE_INDEX = 23;

    public ItemStack dollItem = null;
    public Triplet<Double, Double, Double> knockBack = Triplet.with(0.8D, 0.8D, 0.8D);
    public boolean canMove = true;

    private final Map<ASoldierUpgrade, SoldierUpgradeInst> upgrades_ = new ConcurrentHashMap<>();
    private final Map<ASoldierEffect, SoldierEffectInst> effects_ = new ConcurrentHashMap<>();
    private final long[] upgradeRenderFlags_ = new long[2];
    private final long[] effectRenderFlags_ = new long[2];

    private Entity targetFollow_ = null;

    public EntityClayMan(World world) {
        super(world);

        this.stepHeight = 0.1F;
        this.renderDistanceWeight = 5.0D;

        this.setSize(0.17F, 0.4F);

        this.yOffset = 0.01F;
        this.ignoreFrustumCheck = true;
    }

    public EntityClayMan(World world, String team) {
        this(world);
        this.dataWatcher.updateObject(DW_TEAM, team);

        this.setupTexture(this.rand.nextInt(2) == 0, this.rand.nextInt(2) == 0);
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
    protected boolean isAIEnabled() {
        return false;
    }

    @Override
    public float getAIMoveSpeed() {
        MutableFloat speed = new MutableFloat(0.5F);

        for( SoldierUpgradeInst upg : this.upgrades_.values() ) {
            upg.getUpgrade().getAiMoveSpeed(this, upg, speed);
        }
        for( SoldierEffectInst eff : this.effects_.values() ) {
            eff.getEffect().getAiMoveSpeed(this, eff, speed);
        }

        return speed.floatValue();
    }

    @Override
    public void moveEntity(double motionX, double motionY, double motionZ) {
    	if( this.canMove ) {
            super.moveEntity(motionX, motionY, motionZ);
        } else {
            super.moveEntity(0.0D, motionY > 0.0D ? motionY / 2.0D : motionY, 0.0D);
        }
    }

    public void setupTexture(boolean isRare, boolean isUnique) {
        ClaymanTeam team = ClaymanTeam.getTeamFromName(this.getClayTeam());
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

    @Override
    public boolean attackEntityFrom(DamageSource source, float damage) {
        if( source == IDisruptable.disruptDamage ) {
            return super.attackEntityFrom(source, damage);
        }

        Iterator<Map.Entry<ASoldierUpgrade, SoldierUpgradeInst>> iter = upgrades_.entrySet().iterator();
        while( !this.worldObj.isRemote && iter.hasNext() ) {
            SoldierUpgradeInst upg = iter.next().getValue();
            MutableFloat newDamage = new MutableFloat(damage);
            boolean result = upg.getUpgrade().onSoldierHurt(this, upg, source, newDamage);
            if( !result ) {
                return false;
            } else {
                damage = newDamage.floatValue();
            }
        }

        if( !(source.getEntity() instanceof EntityPlayer) ) {
        	if( this.ridingEntity!=null ) {
        		if( rand.nextInt(4)==0 ) {
        			this.ridingEntity.attackEntityFrom(source, damage);
        			return false;
        		}
        	}
        } else {
            damage = 999;
        }

        return super.attackEntityFrom(source, damage);
    }

    @Override
    public boolean canBePushed() {
        return this.canMove;
    }

    @Override
    protected boolean isMovementBlocked() {
        return !this.canMove || super.isMovementBlocked();
    }

    public boolean isJumping() {
        return this.isJumping;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if( !this.canMove ) {
            this.motionX = 0.0F;
            this.motionZ = 0.0F;
            this.isJumping = false;
        }

        this.canMove = true;

        Iterator<Map.Entry<ASoldierUpgrade, SoldierUpgradeInst>> iterUpgrades = upgrades_.entrySet().iterator();
        while( iterUpgrades.hasNext() ) {
            if( !this.worldObj.isRemote ) {
                SoldierUpgradeInst upg = iterUpgrades.next().getValue();
                if( upg.getUpgrade().onUpdate(this, upg) ) {
                    iterUpgrades.remove();
                }
            } else {
                iterUpgrades.next().getKey().onClientUpdate(this);
            }
        }
        Iterator<Map.Entry<ASoldierEffect, SoldierEffectInst>> iterEffects = effects_.entrySet().iterator();
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
    @SuppressWarnings("unchecked")
    protected void updateEntityActionState() {
        //BUGFIX: fixes movement in blocks w/o collision box (snow layer, torches, tall grass, possibly cobweb?, etc.)
        if( !this.hasPath() ) {
            if( this.entityToAttack != null ) {
                this.setPathToEntity(BugfixHelper.getPathEntityToEntity(this.worldObj, this, this.entityToAttack, 16.0F, true, false, false, true));
            } else if( this.targetFollow_ != null ) {
                this.setPathToEntity(BugfixHelper.getPathEntityToEntity(this.worldObj, this, this.targetFollow_, 16.0F, true, false, false, true));
            } else if( (this.rand.nextInt(180) == 0 || this.rand.nextInt(120) == 0 || this.fleeingTick > 0) && this.entityAge < 100 ) {
                this.updateWanderPath();
            }
        }

        super.updateEntityActionState();

        if( !this.worldObj.isRemote ) {
            if( this.entityToAttack == null ) {
                if( rand.nextInt(4) != 0 && targetFollow_ == null ) {
                    List<EntityClayMan> claymen = (List<EntityClayMan>) this.worldObj.getEntitiesWithinAABB(EntityClayMan.class, this.getTargetArea());
                    targetLoop: for( EntityClayMan uberhaxornova : claymen ) {
                        if( uberhaxornova == this || uberhaxornova.isDead || rand.nextInt(4) != 0 ) {
                            continue;
                        }

                        boolean shouldTargetCheck = false;
                        for( SoldierUpgradeInst upg : this.upgrades_.values() ) {
                            MethodState result = upg.getUpgrade().onTargeting(this, upg, uberhaxornova);
                            if( result == MethodState.DENY ) {
                                continue targetLoop;
                            } else if( result == MethodState.ALLOW ) {
                                shouldTargetCheck = true;
                                break;
                            }
                        }
                        if( !shouldTargetCheck && (uberhaxornova.getClayTeam().equals(this.getClayTeam()) || !this.canEntityBeSeen(uberhaxornova)) ) {
                            continue;
                        }

                        MethodState targetResult = uberhaxornova.onBeingTargeted(this);
                        if( targetResult != MethodState.DENY ) {
                            this.entityToAttack = uberhaxornova;
                        } else {
                            continue;
                        }

                        break;
                    }
                } else {
                    if( this.targetFollow_ == null ) {
                        List<EntityItem> items = (List<EntityItem>)this.worldObj.getEntitiesWithinAABB(EntityItem.class, this.getTargetArea());
                        items: for( EntityItem seamus : items ) {
                            if( !this.canEntityBeSeen(seamus) ) {
                                continue;
                            }

                            ASoldierUpgrade upgrade = SoldierUpgrades.getUpgradeFromItem(seamus.getEntityItem());
                            if( upgrade != null ) {
                                if( this.hasUpgrade(upgrade) || !upgrade.canBePickedUp(this, seamus.getEntityItem(), null) ) {
                                    continue;
                                } else {
                                    for( SoldierUpgradeInst upgradeInst : this.upgrades_.values() ) {
                                        if( upgrade == upgradeInst.getUpgrade() || !upgrade.canBePickedUp(this, seamus.getEntityItem(), upgradeInst.getUpgrade()) ) {
                                            continue items;
                                        }
                                    }
                                }
                            } else {
                                continue;
                            }

                            this.targetFollow_ = seamus;

                            break;
                        }
                    } else {
                        if( this.targetFollow_.isDead ) {
                            this.targetFollow_ = null;
                        } else if( !this.canEntityBeSeen(this.targetFollow_) ) {
                            this.targetFollow_ = null;
                        }

                        if( this.targetFollow_ instanceof EntityItem && this.targetFollow_.getDistanceToEntity(this) < 0.5F ) {
                            EntityItem item = (EntityItem)this.targetFollow_;
                            ASoldierUpgrade upgrade = SoldierUpgrades.getUpgradeFromItem(item.getEntityItem());
                            if( upgrade != null ) {
                                SoldierUpgradeInst upgradeInst = new SoldierUpgradeInst(upgrade);
                                this.upgrades_.put(upgrade, upgradeInst);
                                upgradeInst.getUpgrade().onConstruct(this, upgradeInst);
                                upgradeInst.getUpgrade().onPickup(this, upgradeInst, item.getEntityItem());
                                if( item.getEntityItem().stackSize <= 0 ) {
                                    item.setDead();
                                }
                                this.targetFollow_ = null;
                            }
                        }  else if( this.targetFollow_ instanceof IMount ) {
                            if( this.targetFollow_.riddenByEntity != null ) {
                                this.targetFollow_ = null;
                            } else if( this.targetFollow_.getDistanceToEntity(this) < 0.5D ) {
                                this.mountEntity(this.targetFollow_);
                                this.targetFollow_ = null;
                            }
                        }
                    }
                    if( this.targetFollow_ == null && this.ridingEntity == null ) {
                        List<IMount> items = (List<IMount>)this.worldObj.getEntitiesWithinAABB(IMount.class, this.getTargetArea());
                        for( IMount mount : items ) {
                            EntityLivingBase slyfox = (EntityLivingBase)mount;
                            if( this.rand.nextInt(4) != 0 || !this.canEntityBeSeen(slyfox) || slyfox.riddenByEntity != null ) {
                                continue;
                            }

                            this.targetFollow_ = slyfox;
                            break;
                        }
                    }

                }
            } else {
                if( this.entityToAttack.isDead || !this.canEntityBeSeen(this.entityToAttack) ) {
                    this.entityToAttack = null;
                } else if( this.attackTime == 0 ) {
                    this.attackTime = 5;

                    MutableFloat atkRng = new MutableFloat(0.5F);

                    for( SoldierUpgradeInst upg : this.upgrades_.values() ) {
                        upg.getUpgrade().getAttackRange(this, upg, this.entityToAttack, atkRng);
                    }

                    if( this.getDistanceToEntity(this.entityToAttack) < atkRng.floatValue() && this.entityToAttack instanceof EntityLivingBase
                            && !this.entityToAttack.isEntityInvulnerable() )
                    {
                        EntityLivingBase target = (EntityLivingBase)this.entityToAttack;
                        if( target.hurtTime == 0 ) {
                            MutableFloat damage = new MutableFloat(ModConfig.soldierBaseDamage);
                            if( target instanceof EntityClayMan ) {
                                EntityClayMan soldierTarget = (EntityClayMan) target;
                                soldierTarget.knockBack = Triplet.with(0.8D, 0.8D, 0.8D);
                                for( SoldierUpgradeInst upg : this.upgrades_.values() ) {
                                    upg.getUpgrade().onSoldierAttack(this, upg, soldierTarget, damage);
                                }
                            }

                            if( target.attackEntityFrom(DamageSource.causeMobDamage(this), damage.getValue()) && target instanceof EntityClayMan ) {
                                for( SoldierUpgradeInst upg : this.upgrades_.values() ) {
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
    protected boolean interact(EntityPlayer p_70085_1_) {
        p_70085_1_.mountEntity(this);

        return super.interact(p_70085_1_);
    }

    @Override
    protected void onDeathUpdate() {
        this.deathTime = 20;
        this.setDead();
        ParticlePacketSender.sendSoldierDeathFx(this.posX, this.posY, this.posZ, this.dimension, this.getClayTeam());
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);

        if( !worldObj.isRemote ) {
            for( SoldierUpgradeInst upg : this.upgrades_.values() ) {
                upg.getUpgrade().onSoldierDeath(this, upg, damageSource);
            }
        }
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

    //BUGFIX: fixes movement in blocks w/o collision box (snow layer, torches, tall grass, possibly cobweb?, etc.)
    @Override
    protected void updateWanderPath() {
        this.worldObj.theProfiler.startSection("stroll");
        boolean blockFound = false;
        int x = -1;
        int y = -1;
        int z = -1;
        float maxPathWeight = -99999.0F;

        for( int l = 0; l < 10; ++l ) {
            int currX = MathHelper.floor_double(this.posX + (double)this.rand.nextInt(13) - 6.0D);
            int currY = MathHelper.floor_double(this.posY + (double)this.rand.nextInt(7) - 3.0D);
            int currZ = MathHelper.floor_double(this.posZ + (double)this.rand.nextInt(13) - 6.0D);
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
    //BUGFIX END

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);

        this.dataWatcher.updateObject(DW_TEAM, nbt.getString("team"));

        this.dataWatcher.updateObject(DW_IS_TEXTURE_RARE_OR_UNIQUE, nbt.getByte("isRareOrUnique"));
        this.dataWatcher.updateObject(DW_TEXTURE_INDEX, nbt.getInteger("textureIndex"));

        this.canMove = nbt.getBoolean("canMove");

        if( nbt.hasKey("dollItem") ) {
            this.dollItem = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("dollItem"));
        }

        NBTTagList upgNbtList = nbt.getTagList("upgrade", NbtTypes.NBT_COMPOUND);
        for( int i = 0; i < upgNbtList.tagCount(); i++ ) {
            NBTTagCompound savedUpg = upgNbtList.getCompoundTagAt(i);
            SoldierUpgradeInst upgInst = new SoldierUpgradeInst(SoldierUpgrades.getUpgrade(savedUpg.getString("name")));
            upgInst.setNbtTag(savedUpg.getCompoundTag("data"));
            if( savedUpg.hasKey("item") ) {
                upgInst.readStoredItemFromNBT(savedUpg.getCompoundTag("item"));
            }
            this.upgrades_.put(upgInst.getUpgrade(), upgInst);
        }
        NBTTagList effNbtList = nbt.getTagList("effect", NbtTypes.NBT_COMPOUND);
        for( int i = 0; i < effNbtList.tagCount(); i++ ) {
            NBTTagCompound savedEff = effNbtList.getCompoundTagAt(i);
            SoldierEffectInst effInst = new SoldierEffectInst(SoldierEffects.getEffect(savedEff.getString("name")));
            effInst.setNbtTag(savedEff.getCompoundTag("data"));
            this.effects_.put(effInst.getEffect(), effInst);
        }
    }

    //BUGFIX: fixes movement in blocks w/o collision box (snow layer, torches, tall grass, possibly cobweb?, etc.)
    @Override
    public boolean canEntityBeSeen(Entity target) {
        return this.worldObj.func_147447_a(Vec3.createVectorHelper(this.posX, this.posY + (double) this.getEyeHeight(), this.posZ),
                                           Vec3.createVectorHelper(target.posX, target.posY + (double) target.getEyeHeight(), target.posZ),
                                           false, true, false) == null;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);

        nbt.setString("team", this.getClayTeam());
        nbt.setByte("isRareOrUnique", this.dataWatcher.getWatchableObjectByte(DW_IS_TEXTURE_RARE_OR_UNIQUE));
        nbt.setInteger("textureIndex", this.dataWatcher.getWatchableObjectInt(DW_TEXTURE_INDEX));
        nbt.setBoolean("canMove", canMove);

        if( this.dollItem != null ) {
            NBTTagCompound stackNbt = new NBTTagCompound();
            this.dollItem.writeToNBT(stackNbt);
            nbt.setTag("dollItem", stackNbt);
        }

        NBTTagList upgNbtList = new NBTTagList();
        for( SoldierUpgradeInst upg : this.upgrades_.values() ) {
            NBTTagCompound savedUpg = new NBTTagCompound();
            savedUpg.setString("name", SoldierUpgrades.getNameFromUpgrade(upg.getUpgrade()));
            savedUpg.setTag("data", upg.getNbtTag());
            NBTTagCompound upgItem = upg.saveStoredItemToNBT();
            if( upgItem != null ) {
                savedUpg.setTag("item", upgItem);
            }
            upgNbtList.appendTag(savedUpg);
        }
        nbt.setTag("upgrade", upgNbtList);

        NBTTagList effNbtList = new NBTTagList();
        for( SoldierEffectInst eff : this.effects_.values() ) {
            NBTTagCompound savedEff = new NBTTagCompound();
            savedEff.setString("name", SoldierEffects.getEffectName(eff.getEffect()));
            savedEff.setTag("data", eff.getNbtTag());
            effNbtList.appendTag(savedEff);
        }
        nbt.setTag("effect", effNbtList);
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

    public String getClayTeam() {
        return this.dataWatcher.getWatchableObjectString(DW_TEAM);
    }

    @SideOnly(Side.CLIENT)
    public ResourceLocation getTexture() {
    	if( this.dataWatcher.getWatchableObjectByte(DW_IS_TEXTURE_RARE_OR_UNIQUE) == 2 ) {
            return ClaymanTeam.getTeamFromName(this.dataWatcher.getWatchableObjectString(DW_TEAM))
                              .getUniqueTextures()[this.dataWatcher.getWatchableObjectInt(DW_TEXTURE_INDEX)];
        } else if( this.dataWatcher.getWatchableObjectByte(DW_IS_TEXTURE_RARE_OR_UNIQUE) == 1 ) {
            return ClaymanTeam.getTeamFromName(this.dataWatcher.getWatchableObjectString(DW_TEAM))
                              .getRareTextures()[this.dataWatcher.getWatchableObjectInt(DW_TEXTURE_INDEX)];
        } else {
            return ClaymanTeam.getTeamFromName(this.dataWatcher.getWatchableObjectString(DW_TEAM))
                              .getDefaultTextures()[this.dataWatcher.getWatchableObjectInt(DW_TEXTURE_INDEX)];
        }
    }

    @Override
    public void disrupt() {
        if( !this.getCustomNameTag().startsWith("[UNDISRUPTABLE]") ) {
            this.attackEntityFrom(IDisruptable.disruptDamage, 99999);
        }
    }

    public void updateUpgradeEffectRenders() {
        if( this.worldObj.isRemote ) {
//            this.upgrades_.clear();
//            this.effects_.clear();

            for( byte renderId : SoldierUpgrades.getRegisteredRenderIds() ) {
                long renderFlag = 1 << (renderId % 64);
                int renderStorageDw = (renderId / 64);
                long dwValue = this.upgradeRenderFlags_[renderStorageDw];

                ASoldierUpgrade upgrade = SoldierUpgrades.getUpgradeFromRenderId(renderId);

                if( (dwValue & renderFlag) == renderFlag ) {
                    if( !this.upgrades_.containsKey(upgrade) ) {
                        this.upgrades_.put(upgrade, new SoldierUpgradeInst(upgrade));
                    }
                } else {
                    this.upgrades_.remove(upgrade);
                }
            }

            for( byte renderId : SoldierEffects.getRegisteredRenderIds() ) {
                long renderFlag = 1 << (renderId % 64);
                int renderStorageDw = (renderId / 64);
                long dwValue = this.effectRenderFlags_[renderStorageDw];

                ASoldierEffect effect = SoldierEffects.getEffect(renderId);

                if( (dwValue & renderFlag) == renderFlag ) {
                    if( !this.effects_.containsKey(effect) ) {
                        SoldierEffectInst effectInst =  new SoldierEffectInst(effect);
                        this.effects_.put(effect,effectInst);
                        effect.onConstruct(this, effectInst);
                    }
                } else {
                    this.effects_.remove(effect);
                }
            }
        } else {
            this.upgradeRenderFlags_[0] = 0;
            this.upgradeRenderFlags_[1] = 0;
            this.effectRenderFlags_[0] = 0;
            this.effectRenderFlags_[1] = 0;

            for( SoldierUpgradeInst upgInst : this.upgrades_.values() ) {
                byte renderId = SoldierUpgrades.getRenderIdFromUpgrade(upgInst.getUpgrade());
                if( renderId >= 0 ) {
                    long renderFlag = 1 << (renderId % 64);
                    int renderStorageDw = renderId / 64;
                    long prevDwValue = upgradeRenderFlags_[renderStorageDw];

                    upgradeRenderFlags_[renderStorageDw] = prevDwValue | renderFlag;
                }
            }

            List<Pair<Byte, NBTTagCompound>> effectNbtToClt = new ArrayList<>();
            for( SoldierEffectInst effInst : this.effects_.values() ) {
                byte renderId = SoldierEffects.getRenderId(effInst.getEffect());
                if( renderId >= 0 ) {
                    long renderFlag = 1 << (renderId % 64);
                    int renderStorageDw = renderId / 64;
                    long prevDwValue = this.effectRenderFlags_[renderStorageDw];

                    this.effectRenderFlags_[renderStorageDw] = prevDwValue | renderFlag;
                    if( effInst.getEffect().sendNbtToClient(this, effInst) ) {
                        effectNbtToClt.add(Pair.with(renderId, effInst.getNbtTag()));
                    }
                }
            }

            PacketProcessor.sendToAllAround(PacketProcessor.PKG_SOLDIER_RENDERS, this.dimension, this.posX, this.posY, this.posZ, 64D,
                                            Triplet.with(this.getEntityId(), this.upgradeRenderFlags_, this.effectRenderFlags_)
            );

            for( Pair<Byte, NBTTagCompound> effNbt : effectNbtToClt ) {
                PacketProcessor.sendToAllAround(PacketProcessor.PKG_SOLDIER_EFFECT_NBT, this.dimension, this.posX, this.posY, this.posZ, 64D,
                                                Triplet.with(this.getEntityId(), effNbt.getValue0(), effNbt.getValue1())
                );
            }
        }
    }

    public MethodState onBeingTargeted(EntityClayMan attacker) {
        for( SoldierUpgradeInst upg : this.upgrades_.values() ) {
            MethodState result = upg.getUpgrade().onBeingTargeted(this, upg, attacker);
            if( result == MethodState.DENY || result == MethodState.ALLOW ) {
                return result;
            }
        }
        return MethodState.SKIP;
    }

    public void onProjectileHit(ISoldierProjectile<? extends EntityThrowable> projectile, MovingObjectPosition movObjPos) {
        for( SoldierUpgradeInst upg : this.upgrades_.values() ) {
            upg.getUpgrade().onProjectileHit(this, upg, movObjPos, projectile);
        }
    }

    public boolean targetSoldier(EntityClayMan target) {
        return this.targetSoldier(target, true);
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    public boolean targetSoldier(EntityClayMan target, boolean withUpgradeCheck) {
        if( this.entityToAttack == null || this.entityToAttack.isDead ) {
            if( withUpgradeCheck ) {
                for( SoldierUpgradeInst upg : this.upgrades_.values() ) {
                    MethodState result = upg.getUpgrade().onTargeting(this, upg, target);
                    if( result == MethodState.DENY ) {
                        return false;
                    } else if( result == MethodState.ALLOW ) {
                        this.entityToAttack = target;
                        return true;
                    }
                }
            }
            this.entityToAttack = target;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Entity getEntityToAttack() {
        return this.entityToAttack;
    }

    public boolean hasUpgrade(ASoldierUpgrade upgrade) {
        return this.upgrades_.containsKey(upgrade);
    }

    public boolean hasUpgradeInst(Class<? extends ASoldierUpgrade> upgradeClass) {
        for( ASoldierUpgrade upgrade : this.upgrades_.keySet() ) {
            if( upgradeClass.isInstance(upgrade) ) {
                return true;
            }
        }
        return false;
    }

    public SoldierUpgradeInst getUpgradeData(ASoldierUpgrade upgrade) {
        if( this.hasUpgrade(upgrade) ) {
            return this.upgrades_.get(upgrade);
        } else {
            return null;
        }
    }

    public void removeUpgrade(ASoldierUpgrade upgrade) {
        if( this.hasUpgrade(upgrade) ) {
            this.upgrades_.remove(upgrade);
        }
    }

    public SoldierUpgradeInst addNewUpgrade(ASoldierUpgrade upgrade) {
        SoldierUpgradeInst upgradeInst = new SoldierUpgradeInst(upgrade);
        this.upgrades_.put(upgrade, upgradeInst);
        return upgradeInst;
    }

    public int getMiscColor() {
        return ItemDye.field_150922_c[this.dataWatcher.getWatchableObjectByte(DW_MISC_COLOR)];
    }

    public void setMiscColor(int colorIndex) {
        if( colorIndex >= 0 && colorIndex < ItemDye.field_150922_c.length ) {
            this.dataWatcher.updateObject(DW_MISC_COLOR, (byte) colorIndex);
        }
    }

    public SoldierEffectInst applyEffect(ASoldierEffect effect) {
        if( !this.hasEffect(effect) ) {
            SoldierEffectInst effectInst = new SoldierEffectInst(effect);
            this.effects_.put(effect, effectInst);
            effect.onConstruct(this, effectInst);
            return effectInst;
        } else {
            return null;
        }
    }

    public SoldierEffectInst getEffectInst(ASoldierEffect effect) {
        if( this.hasEffect(effect) ) {
            return this.effects_.get(effect);
        } else {
            return null;
        }
    }

    @SideOnly(Side.CLIENT)
    public void applyRenderFlags(long... flags) {
        this.upgradeRenderFlags_[0] = flags[0];
        this.upgradeRenderFlags_[1] = flags[1];
        this.effectRenderFlags_[0] = flags[2];
        this.effectRenderFlags_[1] = flags[3];
    }

    public boolean hasEffect(ASoldierEffect effect) {
        return this.effects_.containsKey(effect);
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
            throwable.setThrowableHeading(d, d2 + f1, d1, 0.6F, 12F);
            this.attackTime = 30;
            this.hasAttacked = true;

        } catch( InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e ) {
            FMLLog.log(CSM_Main.MOD_LOG, Level.ERROR, "%1$s cannot be instantiated! %1$s is not thrown to target!", projClass.getName());
            e.printStackTrace();
        }
    }
}
