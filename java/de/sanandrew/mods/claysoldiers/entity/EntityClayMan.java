package de.sanandrew.mods.claysoldiers.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.mods.claysoldiers.util.upgrades.ISoldierUpgrade;
import de.sanandrew.mods.claysoldiers.util.ClaymanTeam;
import de.sanandrew.mods.claysoldiers.util.IDisruptable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
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

    public boolean shouldDropDoll = false;

    private final Vector<ISoldierUpgrade> upgrades_ = new Vector<>();

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
        Iterator<ISoldierUpgrade> iter = upgrades_.iterator();
        while( iter.hasNext() ) {
            if(iter.next().onUpdate(this)) {
                iter.remove();
            }
        }
        super.onUpdate();
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
                List<EntityClayMan> claymen = (List<EntityClayMan>) this.worldObj.getEntitiesWithinAABB(EntityClayMan.class, this.getTargetArea());
                for (EntityClayMan uberhaxornova : claymen) {
                    if( uberhaxornova.getClayTeam().equals(this.getClayTeam()) || uberhaxornova == this || uberhaxornova.isDead || !this.canEntityBeSeen(uberhaxornova)|| rand.nextInt(4) != 0 ) {
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
                if( this.entityToAttack.isDead || !this.canEntityBeSeen(this.entityToAttack) ) {
                    this.entityToAttack = null;
                } else {
                    float atkRng = 0.5F;
//                    for( int id : this.upgrades.keySet() )
//                        atkRng = Math.max(CSMModRegistry.clayUpgRegistry.getUpgradeByID(id).getTargetRange(this), atkRng);

                    if( this.getDistanceToEntity(this.entityToAttack) < atkRng && this.entityToAttack instanceof EntityLivingBase && !this.entityToAttack.isEntityInvulnerable() ) {
                        if( ((EntityLivingBase)this.entityToAttack).hurtTime == 0 ) {
                            float damage = 1.0F;
//                            List<Integer> currUpgrades = new ArrayList<Integer>(this.upgrades.keySet());
//                            for( int i : currUpgrades ) {
//                                IUpgradeItem upg = CSMModRegistry.clayUpgRegistry.getUpgradeByID(i);
//                                damage = upg.onAttack(this, (EntityLivingBase)this.entityToAttack, damage);
//                            }
                            this.entityToAttack.attackEntityFrom(DamageSource.causeMobDamage(this), damage);
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
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);

        nbt.setString("team", this.getClayTeam());
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
}
