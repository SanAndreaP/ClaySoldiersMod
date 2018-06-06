/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.mount;

import de.sanandrew.mods.claysoldiers.api.NBTConstants;
import de.sanandrew.mods.claysoldiers.registry.mount.EnumGeckoType;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import de.sanandrew.mods.claysoldiers.util.EnumParticle;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityGecko
        extends EntityClayMount<EntityGecko, EnumGeckoType>
{
    private static final DataParameter<Byte> CLIMBING = EntityDataManager.createKey(EntityGecko.class, DataSerializers.BYTE);

    public EntityGecko(World world) {
        super(world);

        this.setSize(0.35F, 0.15F);
    }

    public EntityGecko(World world, EnumGeckoType type, ItemStack doll) {
        super(world, type, doll);

        this.setSize(0.35F, 0.15F);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(CLIMBING, (byte) 0);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if( !this.world.isRemote ) {
            this.setBesideClimbableBlock(this.collidedHorizontally);
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);

        compound.setInteger(NBTConstants.E_DOLL_TYPE_GECKO, this.type.ordinal());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);

        this.setType(EnumGeckoType.VALUES[compound.getInteger(NBTConstants.E_DOLL_TYPE_GECKO)]);
    }

    @Override
    public double getMountedYOffset() {
        return this.height * 0.4D;
    }

    @Override
    public float getEyeHeight() {
        return this.height * 0.5F;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.BLOCK_WOOD_BREAK;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.BLOCK_WOOD_STEP;
    }

    @Override
    public void spawnBreakParticles() {
            ClaySoldiersMod.proxy.spawnParticle(EnumParticle.GECKO_BREAK, this.world.provider.getDimension(), this.posX, this.posY + this.getEyeHeight(), this.posZ,
                                                this.type.ordinal());
    }

    @Override
    public void writeSpawnData(ByteBuf buffer) {
        buffer.writeInt(this.type.ordinal());
        buffer.writeInt(this.textureId);
    }

    @Override
    public void readSpawnData(ByteBuf additionalData) {
        this.setType(EnumGeckoType.VALUES[additionalData.readInt()]);
        this.textureId = additionalData.readInt();
    }

    @Override
    public void setSpecial() { }

    @Override
    public boolean isSpecial() {
        return false;
    }

    @Override
    public EntityGecko getEntity() {
        return this;
    }

    @Override
    public int getMaxPassengers() {
        return 1;
    }

    public ResourceLocation getTextureBody() {
        return this.type.textureBody;
    }

    public ResourceLocation getTextureSpots() {
        return this.type.textureSpots;
    }

    void setType(EnumGeckoType type) {
        this.type = type;
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(type.maxHealth);
    }

    @Override
    EnumGeckoType getUnknownType() {
        return EnumGeckoType.UNKNOWN;
    }

    @Override
    float getMovementMultiplier() {
        return this.type.movementFactor;
    }

    @Override
    public boolean isOnLadder() {
        return this.isBesideClimbableBlock();
    }

    @Override
    protected PathNavigate createNavigator(World worldIn) {
        return new PathNavigateClimber(this, worldIn);
    }

    public boolean isBesideClimbableBlock() {
        return (this.dataManager.get(CLIMBING) & 1) != 0;
    }

    public void setBesideClimbableBlock(boolean climbing) {
        this.dataManager.set(CLIMBING, (byte) (climbing ? 0x01 : 0x00));
    }
}
