package de.sanandrew.mods.claysoldiers.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.core.manpack.util.NbtTypes;
import de.sanandrew.core.manpack.util.javatuples.Pair;
import de.sanandrew.core.manpack.util.javatuples.Triplet;
import de.sanandrew.mods.claysoldiers.network.PacketProcessor;
import de.sanandrew.mods.claysoldiers.util.IDisruptable;
import de.sanandrew.mods.claysoldiers.util.soldier.AttackState;
import de.sanandrew.mods.claysoldiers.util.soldier.ClaymanTeam;
import de.sanandrew.mods.claysoldiers.util.soldier.effect.ISoldierEffect;
import de.sanandrew.mods.claysoldiers.util.soldier.effect.SoldierEffectInst;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.ISoldierUpgrade;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgrades;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemDye;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author SanAndreasP, CliffracerX
 * @version 1.0
 */
public class EntityClayMan
        extends EntityCreature
        implements IDisruptable
{
    private static final int DW_TEAM = 20;
    private static final int DW_MISC_COLOR = 21;
    private static final int DW_IS_RARE = 22;

    public boolean shouldDropDoll = false;
    public float speed = 0.5F;
    public Triplet<Double, Double, Double> knockBack = Triplet.with(0.8D, 0.8D, 0.8D);

    private final Map<ISoldierUpgrade, SoldierUpgradeInst> upgrades_ = new ConcurrentHashMap<>();
    private final Map<ISoldierEffect, SoldierEffectInst> effects_ = new ConcurrentHashMap<>();
    private final long[] upgradeRenderFlags_ = new long[2];
    private final long[] effectRenderFlags_ = new long[2];

    private Entity targetFollow_ = null;
	public boolean canMove = true;

    private EntityClayMan(World world) {
        super(world);

        this.yOffset = 0.0F;
        this.stepHeight = 0.1F;
        this.renderDistanceWeight = 5.0D;

        this.setSize(0.17F, 0.4F);
    }

    public EntityClayMan(World world, String team) {
        this(world);
        this.dataWatcher.updateObject(DW_TEAM, team);
    }

    @Override
    protected void entityInit() {
        super.entityInit();

        this.dataWatcher.addObject(DW_TEAM, ClaymanTeam.DEFAULT_TEAM);
        this.dataWatcher.addObject(DW_MISC_COLOR, (byte) 15);
        this.dataWatcher.addObject(DW_IS_RARE, (byte) (rand.nextInt(8192)==0 ? 1 : 0));
    }

    @Override
    protected boolean isAIEnabled() {
        return false;
    }

    @Override
    public float getAIMoveSpeed() {
        return this.speed;
    }

    @Override
    public void moveEntity(double motionX, double motionY, double motionZ)
    {
    	if( this.canMove ) {
            super.moveEntity(motionX, motionY, motionZ);
        } else {
            super.moveEntity(0.0D, motionY / 2D, 0.0D);
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float damage) {
        Iterator<Map.Entry<ISoldierUpgrade, SoldierUpgradeInst>> iter = upgrades_.entrySet().iterator();
        while( !this.worldObj.isRemote && iter.hasNext() ) {
            SoldierUpgradeInst upg = iter.next().getValue();
            Pair<Float, Boolean> result = upg.getUpgrade().onSoldierHurt(this, upg, damageSource, damage);
            if( !result.getValue1() ) {
                return false;
            } else {
                damage = result.getValue0();
            }
        }

        if( damageSource.getEntity() instanceof EntityPlayer) {
            damage = 999;
        }

        return super.attackEntityFrom(damageSource, damage);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        Iterator<Map.Entry<ISoldierUpgrade, SoldierUpgradeInst>> iterUpgrades = upgrades_.entrySet().iterator();
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
        Iterator<Map.Entry<ISoldierEffect, SoldierEffectInst>> iterEffects = effects_.entrySet().iterator();
        while( !this.worldObj.isRemote && iterEffects.hasNext() ) {
            SoldierEffectInst upg = iterEffects.next().getValue();
            if( upg.getEffect().onUpdate(this, upg) ) {
                iterEffects.remove();
            }
        }

        if( (ticksExisted % 5) == 0 ) {
            this.updateUpgradeRenders();
        }
    }

    @Override
    public void knockBack(Entity par1Entity, float par2, double par3, double par5) {
        super.knockBack(par1Entity, par2, par3, par5);
        this.motionX *= knockBack.getValue0();
        this.motionY *= knockBack.getValue1();
        this.motionZ *= knockBack.getValue2();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void updateEntityActionState() {
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
                            AttackState result = upg.getUpgrade().onTargeting(this, upg, uberhaxornova);
                            if( result == AttackState.DENY ) {
                                continue targetLoop;
                            } else if( result == AttackState.ALLOW ) {
                                shouldTargetCheck = true;
                                break;
                            }
                        }
                        if( !shouldTargetCheck && (uberhaxornova.getClayTeam().equals(this.getClayTeam()) || !this.canEntityBeSeen(uberhaxornova)) ) {
                            continue;
                        }

                        AttackState targetResult = uberhaxornova.onBeingTargeted(this);
                        if( targetResult != AttackState.DENY ) {
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

                            ISoldierUpgrade upgrade = SoldierUpgrades.getUpgradeFromItem(seamus.getEntityItem());
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
                        } else if( !hasPath() || rand.nextInt(10) == 0 ) {
                            setPathToEntity(worldObj.getPathEntityToEntity(this.targetFollow_, this, 8.0F, false, false, false, false));
                        }

                        if( this.targetFollow_ instanceof EntityItem && this.targetFollow_.getDistanceToEntity(this) < 0.5F ) {
                            EntityItem item = (EntityItem)this.targetFollow_;
                            ISoldierUpgrade upgrade = SoldierUpgrades.getUpgradeFromItem(item.getEntityItem());
                            if( upgrade != null ) {
                                SoldierUpgradeInst upgradeInst = new SoldierUpgradeInst(upgrade);
                                this.upgrades_.put(upgrade, upgradeInst);
                                upgradeInst.getUpgrade().onConstruct(this, upgradeInst);
                                upgradeInst.getUpgrade().onPickup(this, item.getEntityItem());
                                if( item.getEntityItem().stackSize <= 0 ) {
                                    item.setDead();
                                }
                                this.targetFollow_ = null;
                            }
                        }

//                        if( this.targetFollow_ instanceof EntityItem && this.targetFollow_.getDistanceToEntity(this) < 0.5F ) {
//                            for( IUpgradeItem upgrade : CSMModRegistry.clayUpgRegistry.getUpgrades() ) {
//                                if( !CommonUsedStuff.areStacksEqualWithWCV(upgrade.getItemStack(this), ((EntityItem)this.targetFollow_).getEntityItem()) ) continue;
//                                if( this.upgrade.containsKey(CSMModRegistry.clayUpgRegistry.getIDByUpgrade(upgrade)) ) continue;
//                                NBTTagCompound nbt = new NBTTagCompound();
//                                this.upgrade.put(CSMModRegistry.clayUpgRegistry.getIDByUpgrade(upgrade), nbt);
//                                upgrade.onPickup(this, (EntityItem)this.targetFollow_, nbt);
//                                this.targetFollow_ = null;
//                                break;
//                            }
//                        } else if( this.targetFollow_ instanceof IMount ) {
//                            if( this.targetFollow_.riddenByEntity != null ) {
//                                this.targetFollow_ = null;
//                            } else if( this.targetFollow_.getDistanceToEntity(this) < 0.5D ) {
//                                this.mountEntity(this.targetFollow_);
//                                this.targetFollow_ = null;
//                            }
//                        }
                    }
//                    if( this.targetFollow_ == null && this.ridingEntity == null ) {
//                        List<IMount> items = (List<IMount>)this.worldObj.getEntitiesWithinAABB(IMount.class, this.getTargetArea());
//                        for( IMount mount : items ) {
//                            if( !(mount instanceof EntityLivingBase) ) continue;
//                            if( this.rand.nextInt(4) != 0 ) continue;
//                            EntityLivingBase slyfox = (EntityLivingBase)mount;
//                            if( !slyfox.canEntityBeSeen(this) ) continue;
//                            if( slyfox.riddenByEntity != null ) continue;
//                            this.targetFollow_ = slyfox;
//                            break;
//                        }
//                    }

                }
            } else {
                if( this.entityToAttack.isDead || !this.canEntityBeSeen(this.entityToAttack) ) {
                    this.entityToAttack = null;
                } else {
                    float atkRng = 0.5F;
//                    for( int id : this.upgrade.keySet() )
//                        atkRng = Math.max(CSMModRegistry.clayUpgRegistry.getUpgradeByID(id).getTargetRange(this), atkRng);

                    if( this.getDistanceToEntity(this.entityToAttack) < atkRng && this.entityToAttack instanceof EntityLivingBase && !this.entityToAttack.isEntityInvulnerable() ) {
                        EntityLivingBase target = (EntityLivingBase)this.entityToAttack;
                        if( target.hurtTime == 0 ) {
                            float damage = 1.0F;
                            if( target instanceof EntityClayMan ) {
                                EntityClayMan soldierTarget = (EntityClayMan) target;
                                soldierTarget.knockBack = Triplet.with(0.8D, 0.8D, 0.8D);
                                for( SoldierUpgradeInst upg : this.upgrades_.values() ) {
                                    damage = Math.max(damage, upg.getUpgrade().onSoldierAttack(this, upg, soldierTarget, damage));
                                }
                            }

                            if( target.attackEntityFrom(DamageSource.causeMobDamage(this), damage) && target instanceof EntityClayMan ) {
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
    protected void onDeathUpdate() {
        this.deathTime = 20;
        this.setDead();
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
        return "claysoldiers:mob.soldier.hurt";
    }

    @Override
    protected String getDeathSound() {
        return "step.gravel";
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);

        this.dataWatcher.updateObject(DW_TEAM, nbt.getString("team"));
        this.dataWatcher.updateObject(DW_IS_RARE, nbt.getByte("isRare"));
        this.speed=nbt.getFloat("speed");
        this.canMove=nbt.getBoolean("canMove");

        NBTTagList upgNbtList = nbt.getTagList("upgrade", NbtTypes.NBT_COMPOUND);
        for( int i = 0; i < upgNbtList.tagCount(); i++ ) {
            NBTTagCompound savedUpg = upgNbtList.getCompoundTagAt(i);
            SoldierUpgradeInst upgInst = new SoldierUpgradeInst(SoldierUpgrades.getUpgradeFromName(savedUpg.getString("name")));
            upgInst.setNbtTag(savedUpg.getCompoundTag("data"));
            this.upgrades_.put(upgInst.getUpgrade(), upgInst);
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);

        nbt.setString("team", this.getClayTeam());
        nbt.setByte("isRare", this.dataWatcher.getWatchableObjectByte(DW_IS_RARE));
        nbt.setFloat("speed", speed);
        nbt.setBoolean("canMove", canMove);

        NBTTagList upgNbtList = new NBTTagList();
        for( SoldierUpgradeInst upg : this.upgrades_.values() ) {
            NBTTagCompound savedUpg = new NBTTagCompound();
            savedUpg.setString("name", SoldierUpgrades.getNameFromUpgrade(upg.getUpgrade()));
            savedUpg.setTag("data", upg.getNbtTag());
            upgNbtList.appendTag(savedUpg);
        }
        nbt.setTag("upgrade", upgNbtList);
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
    	if( this.dataWatcher.getWatchableObjectByte(DW_IS_RARE) == 1 ) {
            return ClaymanTeam.getTeamFromName(this.dataWatcher.getWatchableObjectString(DW_TEAM)).getRareTextures()[0];
        } else {
            return ClaymanTeam.getTeamFromName(this.dataWatcher.getWatchableObjectString(DW_TEAM)).getDefaultTextures()[0];
        }
    }

    @Override
    public void disrupt() {
        this.attackEntityFrom(IDisruptable.disruptDamage, 99999);
    }

    private void updateUpgradeRenders() {
        if( this.worldObj.isRemote ) {
            this.upgrades_.clear();

//            int[] dwValues = new int[DW_UPG_RENDER.length];
//            for (int i = 0; i < this.upgradeRenderFlags_.length; i++) {
//                dwValues[i] = this.dataWatcher.getWatchableObjectInt(DW_UPG_RENDER[i]);
//            }

            for (byte renderId : SoldierUpgrades.getAvailableRenderIds()) {
                long renderFlag = 1 << (renderId % 64);
                int renderStorageDw = (renderId / 64);
                long dwValue = this.upgradeRenderFlags_[renderStorageDw];

                if ((dwValue & renderFlag) == renderFlag) {
                    this.upgrades_.put(SoldierUpgrades.getUpgradeFromRenderId(renderId), new SoldierUpgradeInst(null));
                }
            }
        } else {
//            for (long dwId : this.upgradeRenderFlags_) {
//                this.dataWatcher.updateObject(dwId, 0);
//            }

            this.upgradeRenderFlags_[0] = 0;
            this.upgradeRenderFlags_[1] = 0;
            this.effectRenderFlags_[0] = 0;
            this.effectRenderFlags_[1] = 0;

            for (SoldierUpgradeInst upgInst : this.upgrades_.values()) {
                int renderId = SoldierUpgrades.getRenderIdFromUpgrade(upgInst.getUpgrade());
                if (renderId >= 0) {
                    long renderFlag = 1 << (renderId % 64);
                    int renderStorageDw = renderId / 64;
                    long prevDwValue = upgradeRenderFlags_[renderStorageDw];

                    upgradeRenderFlags_[renderStorageDw] = prevDwValue | renderFlag;
                }
            }

            PacketProcessor.sendToAllAround(PacketProcessor.PKG_SOLDIER_RENDERS, this.dimension, this.posX, this.posY, this.posZ, 64D,
                                            Triplet.with(this.getEntityId(), this.upgradeRenderFlags_, this.effectRenderFlags_)
            );
        }
    }

    public AttackState onBeingTargeted(EntityClayMan attacker) {
        for( SoldierUpgradeInst upg : this.upgrades_.values() ) {
            AttackState result = upg.getUpgrade().onBeingTargeted(this, upg, attacker);
            if( result == AttackState.DENY || result == AttackState.ALLOW ) {
                return result;
            }
        }
        return AttackState.SKIP;
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
                    AttackState result = upg.getUpgrade().onTargeting(this, upg, target);
                    if( result == AttackState.DENY ) {
                        return false;
                    } else if( result == AttackState.ALLOW ) {
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

    public boolean hasUpgrade(ISoldierUpgrade upgrade) {
        return this.upgrades_.containsKey(upgrade);
    }

    public boolean hasUpgradeInst(Class<? extends ISoldierUpgrade> upgradeClass) {
        for( ISoldierUpgrade upgrade : this.upgrades_.keySet() ) {
            if( upgradeClass.isInstance(upgrade) ) {
                return true;
            }
        }
        return false;
    }

    public SoldierUpgradeInst getUpgradeData(ISoldierUpgrade upgrade) {
        if( this.hasUpgrade(upgrade) ) {
            return this.upgrades_.get(upgrade);
        } else {
            return null;
        }
    }

    public void removeUpgrade(ISoldierUpgrade upgrade) {
        if( this.hasUpgrade(upgrade) ) {
            this.upgrades_.remove(upgrade);
        }
    }

    public SoldierUpgradeInst addNewUpgrade(ISoldierUpgrade upgrade) {
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

    public SoldierEffectInst applyEffect(ISoldierEffect effect) {
        SoldierEffectInst effectInst = new SoldierEffectInst(effect);
        this.effects_.put(effect, effectInst);
        effect.onConstruct(this, effectInst);
        return effectInst;
    }

    @SideOnly(Side.CLIENT)
    public void applyRenderFlags(long... flags) {
        this.upgradeRenderFlags_[0] = flags[0];
        this.upgradeRenderFlags_[1] = flags[1];
        this.effectRenderFlags_[0] = flags[2];
        this.effectRenderFlags_[1] = flags[3];
    }
}
