/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.mount;

import de.sanandrew.core.manpack.util.javatuples.Triplet;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.entity.projectile.ISoldierProjectile;
import de.sanandrew.mods.claysoldiers.util.IDisruptable;
import de.sanandrew.mods.claysoldiers.util.mount.EnumGeckoType;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;

public class EntityGeckoMount
        extends EntityCreature
        implements IMount, IDisruptable
{
    protected static final int DW_TYPE = 20;
    protected static final int DW_CLIMBABLE = 21;

    public boolean spawnedFromNexus = false;
    public ItemStack dollItem = null;

    private float tmpFallDistance;

    public EntityGeckoMount(World world) {
        super(world);

        this.stepHeight = 0.1F;
        this.renderDistanceWeight = 5.0D;

        this.setSize(0.35F, 0.7F);
    }

    public EntityGeckoMount(World world, EnumGeckoType type) {
        this(world);

        this.setType(type);

        this.worldObj.playSoundAtEntity(this, "step.wood", 0.8F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 0.9F);
    }

    @Override
    protected void entityInit() {
        super.entityInit();

        this.dataWatcher.addObject(DW_TYPE, (short) 0);
        this.dataWatcher.addObject(DW_CLIMBABLE, (byte) 0);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D);
    }

    @Override
    public float getAIMoveSpeed() {
        return 0.5F;
    }

    @Override
    public double getMountedYOffset() {
        return super.getMountedYOffset() - 0.42D;
    }

    @Override
    public void updateEntityActionState() {
        if( riddenByEntity == null || !(riddenByEntity instanceof EntityClayMan) ) {
            super.updateEntityActionState();
        } else {
            EntityClayMan rider = (EntityClayMan)riddenByEntity;
            this.isJumping = rider.isJumping();
            this.moveForward = rider.moveForward;
            this.moveStrafing = rider.moveStrafing;
            this.rotationYaw = this.prevRotationYaw = rider.rotationYaw;
            this.rotationPitch = this.prevRotationPitch = rider.rotationPitch;
            rider.renderYawOffset = this.renderYawOffset;
            this.riddenByEntity.fallDistance = 0.0F;

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

        nbt.setBoolean("fromNexus", this.spawnedFromNexus);
        nbt.setShort("geckoType", (short) this.getType());
        if( this.dollItem != null ) {
            NBTTagCompound itemNBT = new NBTTagCompound();
            this.dollItem.writeToNBT(itemNBT);
            nbt.setTag("dollItem", itemNBT);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        this.spawnedFromNexus = nbt.getBoolean("fromNexus");
        this.setType(EnumGeckoType.VALUES[nbt.getShort("geckoType")]);
        if( nbt.hasKey("dollItem", NBT.TAG_COMPOUND) ) {
            this.dollItem = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("dollItem"));
        }
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
        return this.isBesideClimbableBlock();
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
        }

        if( this.riddenByEntity instanceof EntityClayMan && source.getEntity() instanceof ISoldierProjectile ) {
            EntityClayMan clayMan = (EntityClayMan) this.riddenByEntity;
            ISoldierProjectile projectile = (ISoldierProjectile) source.getEntity();

            if( clayMan.getClayTeam().equals(projectile.getTrowingTeam()) ) {
                return false;
            }
        }

        return super.attackEntityFrom(source, damage);
    }

    @Override
    public void disrupt() {
        this.attackEntityFrom(IDisruptable.DISRUPT_DAMAGE, 99999);
    }

    @Override
    public EntityGeckoMount setSpawnedFromNexus() {
        this.spawnedFromNexus = true;
        return this;
    }

    @Override
    public int getType() {
        return this.dataWatcher.getWatchableObjectShort(DW_TYPE);
    }

    @Override
    public void setSpecial() { }

    @Override
    public boolean isSpecial() {
        return false;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if( !this.worldObj.isRemote ) {
            boolean hasGround = false;
            for( int i = 1; this.isCollidedHorizontally && !hasGround && i <= 4; i++ ) {
                Triplet<Integer, Integer, Integer> blockCoords = Triplet.with(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY) - i,
                                                                              MathHelper.floor_double(this.posZ)
                );
                Block block = this.worldObj.getBlock(blockCoords.getValue0(), blockCoords.getValue1(), blockCoords.getValue2());
                if( block != null && !block.isAir(this.worldObj, blockCoords.getValue0(), blockCoords.getValue1(), blockCoords.getValue2()) ) {
                    hasGround = true;
                }
            }
            this.setBesideClimbableBlock(this.isCollidedHorizontally && hasGround);
        }

        if( this.tmpFallDistance > 3.5F ) {
            this.tmpFallDistance = this.fallDistance + 3.5F;
        } else {
            this.tmpFallDistance = this.fallDistance;
        }

        if( this.fallDistance > 3.5F && this.tmpFallDistance < 7.0F ) {
            this.fallDistance -= 3.5F;
        }
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    public void setType(EnumGeckoType type) {
        this.dataWatcher.updateObject(DW_TYPE, (short) type.ordinal());
    }

    public ResourceLocation[] getGeckoTexture() {
        return EnumGeckoType.VALUES[this.getType()].textures;
    }

    @Override
    protected String getHurtSound() {
        return "step.wood";
    }

    @Override
    protected String getDeathSound() {
        return "step.wood";
    }

    @Override
    protected void dropFewItems(boolean flag, int i) {
        if( !this.spawnedFromNexus && this.dollItem != null ) {
            this.entityDropItem(this.dollItem, 0.0F);
        }
    }

    @Override
    protected void onDeathUpdate() {
        this.deathTime = 20;
        this.setDead();
        //TODO: spawn particles!
//        ParticlePacketSender.sendBunnyDeathFx(this.posX, this.posY, this.posZ, this.dimension, (byte) this.getType());
    }

    private boolean isBesideClimbableBlock() {
        return this.dataWatcher.getWatchableObjectByte(DW_CLIMBABLE) == 1;
    }

    private void setBesideClimbableBlock(boolean climbable) {
        this.dataWatcher.updateObject(DW_CLIMBABLE, (byte)(climbable ? 1 : 0));
    }
}
