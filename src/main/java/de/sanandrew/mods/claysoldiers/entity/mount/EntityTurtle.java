/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.mount;

import de.sanandrew.mods.claysoldiers.api.IDisruptable;
import de.sanandrew.mods.claysoldiers.api.NBTConstants;
import de.sanandrew.mods.claysoldiers.api.mount.IMount;
import de.sanandrew.mods.claysoldiers.registry.mount.EnumTurtleType;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import de.sanandrew.mods.claysoldiers.util.EnumParticle;
import de.sanandrew.mods.sanlib.lib.util.ItemStackUtils;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EntityTurtle
        extends EntityCreature
        implements IMount<EntityTurtle>, IEntityAdditionalSpawnData
{
    private EnumTurtleType type = EnumTurtleType.UNKNOWN;
    private int textureId = 0;
    @Nonnull
    private ItemStack doll = ItemStack.EMPTY;

    public EntityTurtle(World world) {
        super(world);

        this.stepHeight = 0.1F;
        this.jumpMovementFactor = 0.2F;

        this.setSize(0.35F, 0.15F);
    }

    public EntityTurtle(World world, EnumTurtleType type, ItemStack doll) {
        this(world);

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
        this.tasks.addTask(9, new EntityAISwimming(this));
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if( this.isInWater()/* && this.motionY < 0.0D*/ ) {
            this.motionY *= 0.05D;
        }
    }

    @Override
    public boolean isInWater() {
        return super.isInWater() && this.isInsideOfMaterial(Material.WATER);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);

        compound.setInteger(NBTConstants.E_DOLL_TYPE_TURTLE, this.type.ordinal());
        compound.setInteger(NBTConstants.E_MOUNT_TEXTURE_ID, this.textureId);
        compound.setTag(NBTConstants.E_DOLL_ITEM, this.doll.serializeNBT());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);

        this.setType(EnumTurtleType.VALUES[compound.getInteger(NBTConstants.E_DOLL_TYPE_TURTLE)]);
        this.textureId = compound.getInteger(NBTConstants.E_MOUNT_TEXTURE_ID);
        this.doll = new ItemStack(compound.getCompoundTag(NBTConstants.E_DOLL_ITEM));
    }

    @Override
    public double getMountedYOffset() {
        return this.height * 0.6D;
    }

    @Override
    public float getAIMoveSpeed() {
        return super.getAIMoveSpeed() * this.type.movementSpeed;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    protected void doWaterSplashEffect() { }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.BLOCK_GRAVEL_BREAK;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.BLOCK_GRAVEL_STEP;
    }

    @Override
    protected void onDeathUpdate() {
        this.deathTime = 20;
        this.setDead();
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        if( this.rand.nextInt(16) == 0 && damageSource.isFireDamage() ) {
            this.setSpecial();
            this.setHealth(this.getMaxHealth());
            return;
        }

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
            ClaySoldiersMod.proxy.spawnParticle(EnumParticle.HORSE_BREAK, this.world.provider.getDimension(), this.posX, this.posY + this.getEyeHeight(), this.posZ,
                                                this.type.ordinal());
        }
    }

    @Override
    public boolean isPushedByWater() {
        return false;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if( source.getTrueSource() instanceof EntityPlayer ) {
            amount = Float.MAX_VALUE;
        }

        return super.attackEntityFrom(source, amount);
    }

    @Override
    public void writeSpawnData(ByteBuf buffer) {
        buffer.writeInt(this.type.ordinal());
        buffer.writeInt(this.textureId);
    }

    @Override
    public void readSpawnData(ByteBuf additionalData) {
        this.setType(EnumTurtleType.VALUES[additionalData.readInt()]);
        this.textureId = additionalData.readInt();
    }

    @Override
    public IMount setSpawnedFromNexus() {
        return this;
    }

    @Override
    public void setSpecial() {
        this.setType(EnumTurtleType.KAWAKO);
    }

    @Override
    public boolean isSpecial() {
        return this.type == EnumTurtleType.KAWAKO;
    }

    @Override
    public EntityTurtle getEntity() {
        return this;
    }

    @Override
    public int getMaxPassengers() {
        return 1;
    }

    @Override
    public void disrupt() {
        this.attackEntityFrom(IDisruptable.DISRUPT_DAMAGE, Float.MAX_VALUE);
    }

    @Override
    public DisruptType getDisruptType() {
        return DisruptType.MOUNT;
    }

    public ResourceLocation getTexture() {
        return this.textureId >= 0 && this.textureId < this.type.textures.length ? this.type.textures[this.textureId] : null;
    }

    private void setType(EnumTurtleType type) {
        this.type = type;
        this.textureId = MiscUtils.RNG.randomInt(type.textures.length);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(type.maxHealth);
        this.isImmuneToFire = type.fireproof;
    }
}
