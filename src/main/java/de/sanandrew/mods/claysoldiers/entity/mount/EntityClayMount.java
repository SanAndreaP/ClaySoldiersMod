/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.mount;

import de.sanandrew.mods.claysoldiers.api.IDisruptable;
import de.sanandrew.mods.claysoldiers.api.NBTConstants;
import de.sanandrew.mods.claysoldiers.api.doll.IDollType;
import de.sanandrew.mods.claysoldiers.api.mount.IMount;
import de.sanandrew.mods.sanlib.lib.util.ItemStackUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class EntityClayMount<E extends EntityLivingBase, T extends IDollType>
        extends EntityCreature
        implements IMount<E>, IEntityAdditionalSpawnData
{
    T type = this.getUnknownType();
    int textureId = 0;
    @Nonnull
    ItemStack doll = ItemStack.EMPTY;

    public EntityClayMount(World worldIn) {
        super(worldIn);

        this.stepHeight = 0.1F;
        this.jumpMovementFactor = 0.2F;
    }

    public EntityClayMount(World worldIn, T type, ItemStack doll) {
        this(worldIn);

        this.doll = doll;
        this.setType(type);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();

        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(7, new EntityAIWander(this, 0.5D));
        this.tasks.addTask(8, new EntityAILookIdle(this));
    }

    @Override
    public void setAIMoveSpeed(float speedIn) {
        Entity controller = this.getControllingPassenger();
        if( controller instanceof EntityLivingBase ) {
            super.setAIMoveSpeed(((EntityLivingBase) controller).getAIMoveSpeed() * this.getMovementMultiplier());
        } else {
            super.setAIMoveSpeed(speedIn * this.getMovementMultiplier());
        }
    }

    @Nullable
    @Override
    public Entity getControllingPassenger() {
        return this.getPassengers().stream().findFirst().orElse(null);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);

        compound.setInteger(NBTConstants.E_MOUNT_TEXTURE_ID, this.textureId);
        compound.setTag(NBTConstants.E_DOLL_ITEM, this.doll.serializeNBT());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);

        this.textureId = compound.getInteger(NBTConstants.E_MOUNT_TEXTURE_ID);
        this.doll = new ItemStack(compound.getCompoundTag(NBTConstants.E_DOLL_ITEM));
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Override
    protected void onDeathUpdate() {
        this.deathTime = 20;
        this.setDead();
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);

        if( !this.world.isRemote ) {
            NonNullList<ItemStack> drops = NonNullList.create();

            if( ItemStackUtils.isValid(this.doll) ) {
                drops.add(this.doll);
            }

            drops.removeIf(stack -> !ItemStackUtils.isValid(stack));
            for( ItemStack drop : drops ) {
                this.entityDropItem(drop, 0.5F);
            }
        } else {
            this.spawnBreakParticles();
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if( source.getTrueSource() instanceof EntityPlayer ) {
            amount = Float.MAX_VALUE;
        }

        return super.attackEntityFrom(source, amount);
    }

    @Override
    public boolean canBeSteered() {
        return this.getControllingPassenger() != null;
    }

    @Override
    public IMount setSpawnedFromNexus() {
        return this;
    }

    @Override
    public void disrupt() {
        this.attackEntityFrom(IDisruptable.DISRUPT_DAMAGE, Float.MAX_VALUE);
    }

    @Override
    public DisruptType getDisruptType() {
        return DisruptType.MOUNT;
    }

    public T getType() {
        return this.type;
    }

    abstract void setType(T type);

    abstract void spawnBreakParticles();

    abstract float getMovementMultiplier();

    abstract T getUnknownType();
}
