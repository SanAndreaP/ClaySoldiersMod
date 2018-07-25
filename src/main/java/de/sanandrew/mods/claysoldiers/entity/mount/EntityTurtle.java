/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.mount;

import de.sanandrew.mods.claysoldiers.api.NBTConstants;
import de.sanandrew.mods.claysoldiers.registry.mount.EnumTurtleType;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import de.sanandrew.mods.claysoldiers.util.EnumParticle;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityTurtle
        extends EntityClayMount<EntityTurtle, EnumTurtleType>
{
    public EntityTurtle(World world) {
        super(world);

        this.setSize(0.35F, 0.15F);
    }

    public EntityTurtle(World world, EnumTurtleType type, ItemStack doll) {
        super(world, type, doll);

        this.setSize(0.35F, 0.15F);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(9, new EntityAISwimming(this));
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if( this.isInWater() || this.isInLava() ) {
            this.motionY *= 0.05D;
        }
    }

    @Override
    public boolean isInWater() {
        return super.isInWater() && this.isInsideOfMaterial(Material.WATER);
    }

    @Override
    public boolean isInLava() {
        return super.isInLava() && this.isInsideOfMaterial(Material.LAVA);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);

        compound.setInteger(NBTConstants.E_DOLL_TYPE_TURTLE, this.type.ordinal());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);

        this.setType(EnumTurtleType.VALUES[compound.getInteger(NBTConstants.E_DOLL_TYPE_TURTLE)]);
    }

    @Override
    public double getMountedYOffset() {
        return this.height * 0.4D;
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        if( !this.isSpecial() && damageSource != DISRUPT_DAMAGE && this.isPotionActive(MobEffects.POISON) && MiscUtils.RNG.randomInt(100) == 0 ) {
            this.dead = false;
            this.isDead = false;
            this.deathTime = 0;
            this.setSpecial();
        } else {
            super.onDeath(damageSource);
        }
    }

    @Override
    public boolean isPotionApplicable(PotionEffect potioneffectIn) {
        return !(this.isSpecial() && potioneffectIn.getPotion() == MobEffects.POISON) && super.isPotionApplicable(potioneffectIn);
    }

    @Override
    public boolean shouldDismountInWater(Entity rider) {
        return false;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    protected void doWaterSplashEffect() { }

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
    public void spawnBreakParticles() {
            ClaySoldiersMod.proxy.spawnParticle(EnumParticle.TURTLE_BREAK, this.world.provider.getDimension(), this.posX, this.posY + this.getEyeHeight(), this.posZ,
                                                this.type.ordinal());
    }

    @Override
    public boolean isPushedByWater() {
        return false;
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
    public void setSpecial() {
        this.setType(EnumTurtleType.KAWAKO);
        this.setHealth(this.getMaxHealth());
        PotionEffect effect = this.getActivePotionEffect(MobEffects.POISON);
        if( effect != null ) {
            this.onFinishedPotionEffect(effect);
            this.removePotionEffect(MobEffects.POISON);
        }
        this.removePassengers();
        ClaySoldiersMod.sendSpawnPacket(this);
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

    public ResourceLocation getTexture() {
        return this.textureId >= 0 && this.textureId < this.type.textures.length ? this.type.textures[this.textureId] : null;
    }

    void setType(EnumTurtleType type) {
        this.type = type;
        this.textureId = MiscUtils.RNG.randomInt(type.textures.length);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(type.maxHealth);
        this.setHealth(this.getMaxHealth());
        this.isImmuneToFire = type.fireproof;
    }

    @Override
    EnumTurtleType getUnknownType() {
        return EnumTurtleType.UNKNOWN;
    }

    @Override
    float getMovementMultiplier() {
        return this.type.movementSpeed;
    }
}
