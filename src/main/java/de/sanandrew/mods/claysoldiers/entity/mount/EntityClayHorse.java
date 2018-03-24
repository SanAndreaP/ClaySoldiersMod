/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.mount;

import de.sanandrew.mods.claysoldiers.api.NBTConstants;
import de.sanandrew.mods.claysoldiers.registry.mount.EnumClayHorseType;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import de.sanandrew.mods.claysoldiers.util.EnumParticle;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityClayHorse
        extends EntityClayMount<EntityClayHorse, EnumClayHorseType>
{
    public EntityClayHorse(World world) {
        super(world);

        this.setSize(0.35F, 0.5F);
    }

    public EntityClayHorse(World world, EnumClayHorseType type, ItemStack doll) {
        super(world, type, doll);

        this.setSize(0.35F, 0.5F);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);

        compound.setInteger(NBTConstants.E_DOLL_TYPE_HORSE, this.type.ordinal());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);

        this.setType(EnumClayHorseType.VALUES[compound.getInteger(NBTConstants.E_DOLL_TYPE_HORSE)]);
    }

    @Override
    public double getMountedYOffset() {
        return this.height * 0.6D;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return this.type.canBreatheUnderwater;
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
    public void onDeath(DamageSource damageSource) {
        if( !this.isSpecial() && damageSource.isFireDamage() && MiscUtils.RNG.randomInt(100) == 0 ) {
            this.dead = false;
            this.isDead = false;
            this.deathTime = 0;
            this.setSpecial();
        } else {
            super.onDeath(damageSource);
        }
    }

    @Override
    void spawnBreakParticles() {
        ClaySoldiersMod.proxy.spawnParticle(EnumParticle.HORSE_BREAK, this.world.provider.getDimension(), this.posX, this.posY + this.getEyeHeight(), this.posZ,
                                            this.type.ordinal());
    }

    @Override
    public void writeSpawnData(ByteBuf buffer) {
        buffer.writeInt(this.type.ordinal());
        buffer.writeInt(this.textureId);
    }

    @Override
    public void readSpawnData(ByteBuf additionalData) {
        this.setType(EnumClayHorseType.VALUES[additionalData.readInt()]);
        this.textureId = additionalData.readInt();
    }

    @Override
    public void setSpecial() {
        this.setType(EnumClayHorseType.NIGHTMARE);
        this.setHealth(this.getMaxHealth());
        this.extinguish();
        this.removePassengers();
        ClaySoldiersMod.sendSpawnPacket(this);
    }

    @Override
    public boolean isSpecial() {
        return this.type == EnumClayHorseType.NIGHTMARE;
    }

    @Override
    public EntityClayHorse getEntity() {
        return this;
    }

    @Override
    public int getMaxPassengers() {
        return 1;
    }

    public ResourceLocation getTexture() {
        return this.textureId >= 0 && this.textureId < this.type.textures.length ? this.type.textures[this.textureId] : null;
    }

    void setType(EnumClayHorseType type) {
        this.type = type;
        this.textureId = MiscUtils.RNG.randomInt(type.textures.length);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(type.maxHealth);
        this.setHealth(this.getMaxHealth());
        this.isImmuneToFire = type.hasFireImmunity;
    }

    @Override
    EnumClayHorseType getUnknownType() {
        return EnumClayHorseType.UNKNOWN;
    }

    @Override
    float getMovementMultiplier() {
        return this.type.movementFactor;
    }
}
