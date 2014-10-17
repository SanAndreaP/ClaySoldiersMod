/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.mount;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.IDisruptable;
import de.sanandrew.mods.claysoldiers.util.mount.EnumBunnyType;
import de.sanandrew.mods.claysoldiers.util.mount.EnumTurtleType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityBunnyMount
        extends EntityCreature
        implements IMount, IDisruptable
{
    protected static final int DW_TYPE = 20;

    public boolean spawnedFromNexus = false;
    public boolean shouldDropDoll = true;

    public EntityBunnyMount(World world) {
        super(world);

        this.stepHeight = 0.1F;
        this.renderDistanceWeight = 5.0D;

        this.setSize(0.35F, 0.7F);
    }

    public EntityBunnyMount(World world, EnumBunnyType type) {
        this(world);

        this.setType(type);

        this.worldObj.playSoundAtEntity(this, "step.cloth", 0.8F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 0.9F);
    }

    @Override
    protected void entityInit() {
        super.entityInit();

        this.dataWatcher.addObject(DW_TYPE, (short) 0);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
    }

    @Override
    public float getAIMoveSpeed() {
        return 0.6F;
    }

    @Override
    public double getMountedYOffset() {
        return super.getMountedYOffset() - 0.3D;
    }

    @Override
    public void updateEntityActionState() {
        if (riddenByEntity == null || !(riddenByEntity instanceof EntityClayMan)) {
            super.updateEntityActionState();
        } else {
            EntityClayMan rider = (EntityClayMan)riddenByEntity;
//			isJumping = rider.isJumping() || handleWaterMovement();
            this.isJumping = true;
            moveForward = rider.moveForward;
            moveStrafing = rider.moveStrafing;
            rotationYaw = prevRotationYaw = rider.rotationYaw;
            rotationPitch = prevRotationPitch = rider.rotationPitch;
            rider.renderYawOffset = renderYawOffset;
            riddenByEntity.fallDistance = 0.0F;

            if (rider.isDead || rider.getHealth() <= 0) {
                rider.mountEntity(null);
            }
        }
    }

    @Override
    protected void jump() {
        this.motionY = 0.4D;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        nbt.setBoolean("fromNexus", spawnedFromNexus);
        nbt.setShort("bunnyType", (short) this.getType());
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        this.spawnedFromNexus = nbt.getBoolean("fromNexus");
        this.setType(EnumBunnyType.VALUES[nbt.getShort("bunnyType")]);
    }

    @Override
    public void knockBack(Entity entity, float f, double motionShiftX, double motionShiftZ) {
        super.knockBack(entity, f, motionShiftX, motionShiftZ);
        if (entity != null && entity instanceof EntityClayMan) {
            this.motionX *= 0.6D;
            this.motionY *= 0.75D;
            this.motionZ *= 0.6D;
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

    @Override
    public boolean attackEntityFrom(DamageSource source, float damage) {
        if( source == IDisruptable.DISRUPT_DAMAGE ) {
            return super.attackEntityFrom(source, damage);
        }

        Entity entity = source.getSourceOfDamage();
        if( !(entity instanceof EntityClayMan) && !source.isFireDamage() ) {
            damage = 999;
        } else if( source.isFireDamage() && this.getType() == EnumTurtleType.NETHERRACK.ordinal() ) {
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
        }

        return damageSuccess;
    }

    @Override
    public void disrupt() {
        this.attackEntityFrom(IDisruptable.DISRUPT_DAMAGE, 99999);
    }

    @Override
    public EntityBunnyMount setSpawnedFromNexus() {
        this.spawnedFromNexus = true;
        return this;
    }

    @Override
    public int getType() {
        return this.dataWatcher.getWatchableObjectShort(DW_TYPE);
    }

    @Override
    public void setSpecial() {

    }

    @Override
    public boolean isSpecial() {
        return false;
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    public void setType(EnumBunnyType type) {
        this.dataWatcher.updateObject(DW_TYPE, (short) type.ordinal());
    }

    public ResourceLocation getBunnyTexture() {
        return EnumBunnyType.VALUES[this.getType()].texture;
    }

    @Override
    protected String getHurtSound() {
        return "step.cloth";
    }

    @Override
    protected String getDeathSound() {
        return "step.cloth";
    }
}
