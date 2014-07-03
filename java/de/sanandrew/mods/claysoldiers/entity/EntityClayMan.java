package de.sanandrew.mods.claysoldiers.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.core.manpack.util.NbtTypes;
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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * @author SanAndreasP
 * @version 1.0
 */
public class EntityClayMan
        extends EntityCreature
        implements IDisruptable
{
    public static final int DW_TEAM = 20;
    public static final int DW_UPG_RENDER1 = 21;
    public static final int DW_UPG_RENDER2 = 22;

    public boolean shouldDropDoll = false;

    private final Vector<SoldierUpgradeInst> upgrades_ = new Vector<>();

    private Entity targetFollow_ = null;

    public EntityClayMan(World world) {
        super(world);

        this.yOffset = 0.0F;
        this.stepHeight = 0.1F;
        this.renderDistanceWeight = 5.0D;

        this.setSize(0.1F, 0.2F);
    }

    public EntityClayMan(World world, String team) {
        this(world);
        this.dataWatcher.updateObject(DW_TEAM, team);
    }

    @Override
    protected void entityInit() {
        super.entityInit();

        this.dataWatcher.addObject(DW_TEAM, ClaymanTeam.DEFAULT_TEAM);
        this.dataWatcher.addObject(DW_UPG_RENDER1, 0L);
        this.dataWatcher.addObject(DW_UPG_RENDER2, 0L);
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
        if( damageSource.getEntity() instanceof EntityPlayer) {
            damage = 999;
        }

        return super.attackEntityFrom(damageSource, damage);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        Iterator<SoldierUpgradeInst> iter = upgrades_.iterator();
        while( iter.hasNext() ) {
            SoldierUpgradeInst upg = iter.next();
            if( upg.getUpgrade().onUpdate(this, upg) ) {
                iter.remove();
            }
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
                if( rand.nextInt(4) != 0 ) {
                    List<EntityClayMan> claymen = (List<EntityClayMan>) this.worldObj.getEntitiesWithinAABB(EntityClayMan.class, this.getTargetArea());
                    for( EntityClayMan uberhaxornova : claymen ) {
                        if( uberhaxornova == this || uberhaxornova.isDead || rand.nextInt(4) != 0 ) {
                            continue;
                        }
                        boolean shouldTargetCheck = true;
                        for( SoldierUpgradeInst upg : this.upgrades_ ) {
                            if( upg.getUpgrade().allowSoldierTarget(this, upg, uberhaxornova) ) {
                                shouldTargetCheck = false;
                                break;
                            }
                        }
                        if( shouldTargetCheck && (uberhaxornova.getClayTeam().equals(this.getClayTeam()) || !this.canEntityBeSeen(uberhaxornova)) ) {
                            continue;
                        }

                        this.entityToAttack = uberhaxornova;

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
                                for( SoldierUpgradeInst upgradeInst : this.upgrades_ ) {
                                    if( upgrade == upgradeInst.getUpgrade() || !upgrade.isCompatibleWith(upgradeInst.getUpgrade()) ) {
                                        continue items;
                                    }
                                }
                            }

                            this.targetFollow_ = seamus;

                            break;
                        }
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

                    if( this.targetFollow_ != null ) {
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
                                this.upgrades_.add(upgradeInst);
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
                            for( SoldierUpgradeInst upg : this.upgrades_ ) {
                                damage = Math.max(damage, upg.getUpgrade().onEntityAttack(this, upg, target, damage));
                            }
                            target.attackEntityFrom(DamageSource.causeMobDamage(this), damage);
                        }
                    }
                }
            }
        }
    }

    @Override
    protected String getLivingSound() {
        return null;
    }

    @Override
    protected String getHurtSound() {
        return "dig.gravel";
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);

        this.dataWatcher.updateObject(DW_TEAM, nbt.getString("team"));

        NBTTagList upgNbtList = nbt.getTagList("upgrades", NbtTypes.NBT_COMPOUND);
        for( int i = 0; i < upgNbtList.tagCount(); i++ ) {
            NBTTagCompound savedUpg = upgNbtList.getCompoundTagAt(i);
            SoldierUpgradeInst upgInst = new SoldierUpgradeInst(SoldierUpgrades.getUpgradeFromName(savedUpg.getString("name")));
            upgInst.setNbtTag(savedUpg.getCompoundTag("data"));
            this.upgrades_.add(upgInst);
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);

        nbt.setString("team", this.getClayTeam());

        NBTTagList upgNbtList = new NBTTagList();
        for( SoldierUpgradeInst upg : this.upgrades_ ) {
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
        return ClaymanTeam.getTeamFromName(this.dataWatcher.getWatchableObjectString(DW_TEAM)).getDefaultTextures()[0];
    }

    @Override
    public void disrupt(EntityPlayer player) {
        this.attackEntityFrom(DamageSource.causePlayerDamage(player), 99999);
    }

    public void setUpgradeRender() {

    }
}
