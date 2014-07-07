package de.sanandrew.mods.claysoldiers.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.core.manpack.util.NbtTypes;
import de.sanandrew.core.manpack.util.javatuples.Pair;
import de.sanandrew.mods.claysoldiers.util.ClaymanTeam;
import de.sanandrew.mods.claysoldiers.util.IDisruptable;
import de.sanandrew.mods.claysoldiers.util.upgrades.ISoldierUpgrade;
import de.sanandrew.mods.claysoldiers.util.upgrades.SoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.util.upgrades.SoldierUpgrades;
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
    public static final int DW_TEAM = 20;
    public static final int[] DW_UPG_RENDER = { 21, 22, 23, 24 };
    public static final int DW_MISC_COLOR = 25;
    public static final int DW_IS_RARE = 26;

    public boolean shouldDropDoll = false;

    private final Map<ISoldierUpgrade, SoldierUpgradeInst> upgrades_ = new ConcurrentHashMap<>();

    private Entity targetFollow_ = null;

    public EntityClayMan(World world) {
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
        this.dataWatcher.addObject(DW_UPG_RENDER[0], 0);
        this.dataWatcher.addObject(DW_UPG_RENDER[1], 0);
        this.dataWatcher.addObject(DW_UPG_RENDER[2], 0);
        this.dataWatcher.addObject(DW_UPG_RENDER[3], 0);
        this.dataWatcher.addObject(DW_MISC_COLOR, (byte) 15);
        this.dataWatcher.addObject(DW_IS_RARE, (byte) (rand.nextInt(8192)==0 ? 1 : 0));
    }

    @Override
    protected boolean isAIEnabled() {
        return false;
    }

    @Override
    public float getAIMoveSpeed() {
        return 0.5F;
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
        Iterator<Map.Entry<ISoldierUpgrade, SoldierUpgradeInst>> iter = upgrades_.entrySet().iterator();
        while( !this.worldObj.isRemote && iter.hasNext() ) {
            SoldierUpgradeInst upg = iter.next().getValue();
            if( upg.getUpgrade().onUpdate(this, upg) ) {
                iter.remove();
            }
        }

        if( (ticksExisted % 5) == 0 ) {
            this.updateUpgradeRenders();
        }
    }



    @Override
    public void knockBack(Entity par1Entity, float par2, double par3, double par5) {
        super.knockBack(par1Entity, par2, par3, par5);
        this.motionX *= 0.9D;
        this.motionY *= 0.9D;
        this.motionZ *= 0.9D;
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
                            ISoldierUpgrade.AttackState result = upg.getUpgrade().onTargeting(this, upg, uberhaxornova);
                            if( result == ISoldierUpgrade.AttackState.DENY ) {
                                continue targetLoop;
                            } else if( result == ISoldierUpgrade.AttackState.ALLOW ) {
                                shouldTargetCheck = true;
                                break;
                            }
                        }
                        if( !shouldTargetCheck && (uberhaxornova.getClayTeam().equals(this.getClayTeam()) || !this.canEntityBeSeen(uberhaxornova)) ) {
                            continue;
                        }

                        ISoldierUpgrade.AttackState targetResult = uberhaxornova.onBeingTargeted(this);
                        if( targetResult != ISoldierUpgrade.AttackState.DENY ) {
                            this.targetSoldier(uberhaxornova);
                        } else {
                            continue;
                        }

//                    for (int id : this.upgrades.keySet())
//                        this.entityToAttack = CSMModRegistry.clayUpgRegistry.getUpgradeByID(id).onTargeting(this, uberhaxornova);

//                    if (this.entityToAttack != null && this.entityToAttack instanceof IUpgradeEntity) {
//                        for (int id2 : ((IUpgradeEntity) this.entityToAttack).getUpgrades())
//                            this.entityToAttack = CSMModRegistry.clayUpgRegistry.getUpgradeByID(id2).onTargeted((IUpgradeEntity) this.entityToAttack, this);
//                    }

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
//                                if( this.upgrades.containsKey(CSMModRegistry.clayUpgRegistry.getIDByUpgrade(upgrade)) ) continue;
//                                NBTTagCompound nbt = new NBTTagCompound();
//                                this.upgrades.put(CSMModRegistry.clayUpgRegistry.getIDByUpgrade(upgrade), nbt);
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
//                    for( int id : this.upgrades.keySet() )
//                        atkRng = Math.max(CSMModRegistry.clayUpgRegistry.getUpgradeByID(id).getTargetRange(this), atkRng);

                    if( this.getDistanceToEntity(this.entityToAttack) < atkRng && this.entityToAttack instanceof EntityLivingBase && !this.entityToAttack.isEntityInvulnerable() ) {
                        EntityLivingBase target = (EntityLivingBase)this.entityToAttack;
                        if( target.hurtTime == 0 ) {
                            float damage = 1.0F;
                            if( target instanceof EntityClayMan ) {
                                EntityClayMan soldierTarget = (EntityClayMan) target;
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
    protected String getLivingSound() {
        return null;
    }

    @Override
    protected String getHurtSound() {
        return "claysoldiers:mob.soldier.hurt";
    }

    @Override
    protected String getDeathSound() {
        return "dig.gravel";
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);

        this.dataWatcher.updateObject(DW_TEAM, nbt.getString("team"));
        this.dataWatcher.updateObject(DW_IS_RARE, nbt.getByte("isRare"));

        NBTTagList upgNbtList = nbt.getTagList("upgrades", NbtTypes.NBT_COMPOUND);
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

        NBTTagList upgNbtList = new NBTTagList();
        for( SoldierUpgradeInst upg : this.upgrades_.values() ) {
            NBTTagCompound savedUpg = new NBTTagCompound();
            savedUpg.setString("name", SoldierUpgrades.getNameFromUpgrade(upg.getUpgrade()));
            savedUpg.setTag("data", upg.getNbtTag());
            upgNbtList.appendTag(savedUpg);
        }
        nbt.setTag("upgrades", upgNbtList);
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
    public void disrupt(EntityPlayer player) {
        this.attackEntityFrom(DamageSource.causePlayerDamage(player), 99999);
    }

    private void updateUpgradeRenders() {
        if( this.worldObj.isRemote ) {
            this.upgrades_.clear();

            int[] dwValues = new int[DW_UPG_RENDER.length];
            for (int i = 0; i < DW_UPG_RENDER.length; i++) {
                dwValues[i] = this.dataWatcher.getWatchableObjectInt(DW_UPG_RENDER[i]);
            }

            for (byte renderId : SoldierUpgrades.getAvailableRenderIds()) {
                int renderFlag = 1 << (renderId % 32);
                int renderStorageDw = (renderId / 32);
                int dwValue = dwValues[renderStorageDw];

                if ((dwValue & renderFlag) == renderFlag) {
                    this.upgrades_.put(SoldierUpgrades.getUpgradeFromRenderId(renderId), new SoldierUpgradeInst(null));
                    //SoldierUpgrades.getUpgradeFromRenderId(renderId).onRender(stage, this, clayManRender, x, y, z, yaw, partTicks);
                }
            }
        } else {
            for (int dwId : DW_UPG_RENDER) {
                this.dataWatcher.updateObject(dwId, 0);
            }

            for (SoldierUpgradeInst upgInst : this.upgrades_.values()) {
                int renderId = SoldierUpgrades.getRenderIdFromUpgrade(upgInst.getUpgrade());
                if (renderId >= 0) {
                    int renderFlag = 1 << (renderId % 32);
                    int renderStorageDw = renderId / 32;
                    int prevDwValue = this.dataWatcher.getWatchableObjectInt(DW_UPG_RENDER[renderStorageDw]);

                    this.dataWatcher.updateObject(DW_UPG_RENDER[renderStorageDw], prevDwValue | renderFlag);
                }
            }
        }
    }

//    @SideOnly(Side.CLIENT)
//    public void renderUpgrades(ISoldierUpgrade.RenderStage stage, RenderClayMan clayManRender, double x, double y, double z, float yaw, float partTicks) {
////        if( stage == ISoldierUpgrade.RenderStage.PRE ) {
////            this.renderedUpgrades_ = new ArrayList<>();
////
////            int[] dwValues = new int[DW_UPG_RENDER.length];
////            for (int i = 0; i < DW_UPG_RENDER.length; i++) {
////                dwValues[i] = this.dataWatcher.getWatchableObjectInt(DW_UPG_RENDER[i]);
////            }
////
////            for (byte renderId : SoldierUpgrades.getAvailableRenderIds()) {
////                int renderFlag = 1 << (renderId % 32);
////                int renderStorageDw = (renderId / 32);
////                int dwValue = dwValues[renderStorageDw];
////
////                if ((dwValue & renderFlag) == renderFlag) {
////                    renderedUpgrades_.add(renderId);
////                    SoldierUpgrades.getUpgradeFromRenderId(renderId).onRender(stage, this, clayManRender, x, y, z, yaw, partTicks);
////                }
////            }
////        } else {
////            for( byte renderId : this.renderedUpgrades_ ) {
////                SoldierUpgrades.getUpgradeFromRenderId(renderId).onRender(stage, this, clayManRender, x, y, z, yaw, partTicks);
////            }
////        }
//    }

    public ISoldierUpgrade.AttackState onBeingTargeted(EntityClayMan attacker) {
        for( SoldierUpgradeInst upg : this.upgrades_.values() ) {
            ISoldierUpgrade.AttackState result = upg.getUpgrade().onBeingTargeted(this, upg, attacker);
            if( result == ISoldierUpgrade.AttackState.DENY || result == ISoldierUpgrade.AttackState.ALLOW ) {
                return result;
            }
        }
        return ISoldierUpgrade.AttackState.SKIP;
    }

    public boolean targetSoldier(EntityClayMan target) {
        if( this.entityToAttack == null || this.entityToAttack.isDead ) {
            this.entityToAttack = target;
            return true;
        } else {
            return false;
        }
    }

    public boolean hasUpgrade(ISoldierUpgrade upgrade) {
        return this.upgrades_.containsKey(upgrade);
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

    public int getMiscColor() {
        return ItemDye.field_150922_c[this.dataWatcher.getWatchableObjectByte(DW_MISC_COLOR)];
    }

    public void setMiscColor(int colorIndex) {
        if( colorIndex >= 0 && colorIndex < ItemDye.field_150922_c.length ) {
            this.dataWatcher.updateObject(DW_MISC_COLOR, (byte) colorIndex);
        }
    }
}
