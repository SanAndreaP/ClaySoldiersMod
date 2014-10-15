/*******************************************************************************************************************
 * Author:    SanAndreasP
 * Copyright: SanAndreasP and SilverChiren
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported
 *            (http://creativecommons.org/licenses/by-nc-sa/3.0/)
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.mount;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.network.ParticlePacketSender;
import de.sanandrew.mods.claysoldiers.util.IDisruptable;
import de.sanandrew.mods.claysoldiers.util.mount.EnumHorseType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityHorseMount
        extends EntityCreature
        implements IMount, IDisruptable
{
    protected static final int DW_TYPE = 20;
    protected static final int DW_TEXTURE = 21;

    public boolean spawnedFromNexus = false;
    public boolean specialDeath = false;
    public boolean shouldDropDoll = true;

    protected float moveSpeed;

    public EntityHorseMount(World world) {
        super(world);

        this.stepHeight = 0.1F;
        this.moveSpeed = 0.6F;
        this.renderDistanceWeight = 5D;

        this.setSize(0.35F, 0.7F);
    }

    public EntityHorseMount(World world, EnumHorseType horseType) {
        this(world);

        if( rand.nextInt(8192) == 0 ) {
            this.setSpecial();
        } else {
            this.setType(horseType);
        }

        this.worldObj.playSoundAtEntity(this, "step.gravel", 0.8F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 0.9F);
    }

    @Override
    public void updateEntityActionState() {
        if( this.riddenByEntity == null || !(this.riddenByEntity instanceof EntityClayMan) ) {
            super.updateEntityActionState();
        } else {
            EntityClayMan rider = (EntityClayMan) this.riddenByEntity;
            this.isJumping = rider.isJumping() || this.handleWaterMovement();
            this.moveForward = rider.moveForward;
            this.moveStrafing = rider.moveStrafing;
            this.rotationYaw = this.prevRotationYaw = rider.rotationYaw;
            this.rotationPitch = this.prevRotationPitch = rider.rotationPitch;
            rider.renderYawOffset = this.renderYawOffset;
            rider.fallDistance = 0.0F;

            if( rider.isDead || rider.getHealth() <= 0 ) {
                rider.mountEntity(null);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        nbt.setBoolean("fromNexus", spawnedFromNexus);
        nbt.setShort("horseType", (short) this.getType());
        nbt.setShort("texture", this.dataWatcher.getWatchableObjectShort(DW_TEXTURE));
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        this.spawnedFromNexus = nbt.getBoolean("fromNexus");
        this.setType(EnumHorseType.values[nbt.getShort("horseType")]);
        this.dataWatcher.updateObject(DW_TEXTURE, nbt.getShort("texture"));
    }

    @Override
    public EntityItem entityDropItem(ItemStack par1ItemStack, float par2) {
        return this.spawnedFromNexus || this.specialDeath ? null : super.entityDropItem(par1ItemStack, par2);
    }

    @Override
    public double getMountedYOffset() {
        return super.getMountedYOffset() - 0.3D;
    }

    @Override
    public void mountEntity(Entity entity) {
        if( !(entity != null && entity instanceof EntityMinecart) ) {
            super.mountEntity(entity);
        }
    }

    @Override
    public boolean canBreatheUnderwater() {
        short horseType = this.dataWatcher.getWatchableObjectShort(DW_TYPE);
        return horseType == EnumHorseType.LAPIS.ordinal() || horseType == EnumHorseType.CLAY.ordinal() || horseType == EnumHorseType.CARROT.ordinal();
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float damage) {
        if( source == IDisruptable.disruptDamage ) {
            return super.attackEntityFrom(source, damage);
        }

        boolean shouldSpawnSpecial = rand.nextInt(16) == 0;

        this.specialDeath = source.isFireDamage() && !this.isSpecial() && shouldSpawnSpecial;

        Entity entity = source.getSourceOfDamage();
        if( !(entity instanceof EntityClayMan) && !source.isFireDamage() ) {
            damage = 999;
        } else if( source.isFireDamage() && this.isSpecial() ) {
            return false;
        }

        //TODO: readd ranged projectile check!
        if( this.riddenByEntity instanceof EntityClayMan ) {
//			if (e instanceof EntityGravelChunk) {
//				if (((EntityGravelChunk)e).getClayTeam() == ((EntityClayMan)riddenByEntity).getClayTeam())
//					return false;
//				else
//					i = origDmg;
//			}
//			if (e instanceof EntityFireball) {
//				if (((EntityFireball)e).getClayTeam() == ((EntityClayMan)riddenByEntity).getClayTeam())
//					return false;
//				else
//					i = origDmg;
//			}
//			if (e instanceof EntitySnowball) {
//				if (((EntitySnowball)e).getClayTeam() == ((EntityClayMan)riddenByEntity).getClayTeam())
//					return false;
//				else
//					i = origDmg;
//			}
        }

        boolean damageSuccess = super.attackEntityFrom(source, damage);

        if( damageSuccess && this.getHealth() <= 0 ) {
//				Item item1 = CSM_ModRegistry.horseDoll;
            //TODO: readd particles!
//				for( int i = 0; i < 4; i++ ) {
//					double a = posX + ((rand.nextFloat() - rand.nextFloat()) * 0.125D);
//					double b = boundingBox.minY + 0.125D + ((rand.nextFloat() - rand.nextFloat()) * 0.25D);
//					double c = posZ + ((rand.nextFloat() - rand.nextFloat()) * 0.125D);
//
////					CSMModRegistry.proxy.showEffect(this.worldObj, this, 13);
////					if (FMLCommonHandler.instance().getSide().isClient())
////						CSM_ModRegistry.proxy.showEffect((new EntityDiggingFX(CSM_ModRegistry.proxy.getClientWorld(), a, b, c, 0.0D, 0.0D, 0.0D, Block.dirt, 0, 0)));
//				}
            if( source.isFireDamage() && !this.isSpecial() && shouldSpawnSpecial ) {
                EntityHorseMount specialHorse = new EntityHorseMount(this.worldObj, EnumHorseType.values[this.getType()]);
                specialHorse.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
                specialHorse.setSpecial();
                specialHorse.chooseTexture();
                specialHorse.setHorseSpecs();
                this.worldObj.spawnEntityInWorld(specialHorse);
            }
        }
        return damageSuccess;
    }

    @Override
    public void knockBack(Entity entity, float i, double d, double d1) {
        super.knockBack(entity, i, d, d1);
        if( entity instanceof EntityClayMan ) {
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
    public float getAIMoveSpeed() {
        return this.moveSpeed;
    }

    @Override
    public EntityHorseMount setSpawnedFromNexus() {
        this.spawnedFromNexus = true;
        return this;
    }

    @Override
    public int getType() {
        return this.dataWatcher.getWatchableObjectShort(DW_TYPE);
    }

    @Override
    public void setSpecial() {
        this.setType(EnumHorseType.NIGHTMARE);
    }

    @Override
    public boolean isSpecial() {
        return this.getType() == EnumHorseType.NIGHTMARE.ordinal();
    }

    @Override
    public void disrupt() {
        this.attackEntityFrom(IDisruptable.disruptDamage, 99999);
    }

    @Override
    public boolean interact(EntityPlayer e) {
        return false;
    }

    @Override
    protected void onDeathUpdate() {
        this.deathTime = 20;
        this.setDead();
        ParticlePacketSender.sendHorseDeathFx(this.posX, this.posY, this.posZ, this.dimension, (byte) this.getType());
    }

    @Override
    protected String getHurtSound() {
        return "step.gravel";
    }

    @Override
    protected String getDeathSound() {
        return "step.gravel";
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();

        this.dataWatcher.addObject(DW_TYPE, (short) 0);
        this.dataWatcher.addObject(DW_TEXTURE, (short) 0);
    }

    @Override
    protected void jump() {
        this.motionY = 0.4D;
        this.isAirBorne = true;
    }

    //TODO: drop doll!
    @Override
    protected void dropFewItems(boolean flag, int i) {
//		Item item1 = CSMModRegistry.horseDoll;
//		dropItem(item1.itemID, 1, this.dataWatcher.getWatchableObjectShort(19));
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    public void setHorseSpecs() {
        EnumHorseType type = EnumHorseType.values[this.getType()];
        this.updateHealth(type.health);
        this.moveSpeed = type.moveSpeed;
    }

    public void setType(EnumHorseType type) {
        this.dataWatcher.updateObject(DW_TYPE, (short) type.ordinal());
        this.chooseTexture();
        this.setHorseSpecs();
    }

    public ResourceLocation getHorseTexture() {
        return EnumHorseType.values[this.getType()].textures[this.dataWatcher.getWatchableObjectShort(DW_TEXTURE)];
    }

    protected void chooseTexture() {
        int textureId = (this.rand.nextInt(EnumHorseType.values[this.getType()].textures.length));
        this.dataWatcher.updateObject(DW_TEXTURE, (short) textureId);
    }

    //TODO: drop doll!
    protected void dropItem(int itemID, int i, int j) {
//		entityDropItem(new ItemStack(itemID, i, j), 0.0F);
    }

    private void updateHealth(float health) {
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(health);
        this.setHealth(health);
    }
}
