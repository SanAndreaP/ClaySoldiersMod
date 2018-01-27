/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.mount;

import de.sanandrew.mods.claysoldiers.api.NBTConstants;
import de.sanandrew.mods.claysoldiers.registry.mount.EnumWoolBunnyType;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import de.sanandrew.mods.claysoldiers.util.EnumParticle;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityWoolBunny
        extends EntityClayMount<EntityWoolBunny, EnumWoolBunnyType>
{
    public EntityWoolBunny(World world) {
        super(world);

        this.setSize(0.35F, 0.5F);
    }

    public EntityWoolBunny(World world, EnumWoolBunnyType type, ItemStack doll) {
        super(world, type, doll);

        this.setSize(0.35F, 0.5F);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);

        compound.setInteger(NBTConstants.E_DOLL_TYPE_BUNNY, this.type.ordinal());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);

        this.setType(EnumWoolBunnyType.VALUES[compound.getInteger(NBTConstants.E_DOLL_TYPE_BUNNY)]);
    }

    @Override
    public double getMountedYOffset() {
        return 0.0D;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if( this.onGround && (this.moveForward < -0.001F || this.moveForward > 0.001F) ) {
            this.jump();
        }
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.BLOCK_CLOTH_BREAK;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.BLOCK_CLOTH_STEP;
    }

    @Override
    void spawnBreakParticles() {
        ClaySoldiersMod.proxy.spawnParticle(EnumParticle.BUNNY_BREAK, this.world.provider.getDimension(), this.posX, this.posY + this.getEyeHeight(), this.posZ,
                                            this.type.ordinal());
    }

    @Override
    float getMovementMultiplier() {
        return 1.0F;
    }

    @Override
    public void writeSpawnData(ByteBuf buffer) {
        buffer.writeInt(this.type.ordinal());
        buffer.writeInt(this.textureId);
    }

    @Override
    public void readSpawnData(ByteBuf additionalData) {
        this.setType(EnumWoolBunnyType.VALUES[additionalData.readInt()]);
        this.textureId = additionalData.readInt();
    }

    @Override
    public void setSpecial() { }

    @Override
    public boolean isSpecial() {
        return false;
    }

    @Override
    public EntityWoolBunny getEntity() {
        return this;
    }

    @Override
    public int getMaxPassengers() {
        return 1;
    }

    public ResourceLocation getTexture() {
        return this.textureId >= 0 && this.textureId < this.type.textures.length ? this.type.textures[this.textureId] : null;
    }

    void setType(EnumWoolBunnyType type) {
        this.type = type;
        this.textureId = MiscUtils.RNG.randomInt(type.textures.length);
    }

    @Override
    EnumWoolBunnyType getUnknownType() {
        return EnumWoolBunnyType.UNKNOWN;
    }
}
