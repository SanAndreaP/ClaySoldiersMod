/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.mount;

import de.sanandrew.mods.claysoldiers.api.IDisruptable;
import de.sanandrew.mods.claysoldiers.api.mount.IMount;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

import javax.annotation.Nonnull;

public class EntityClayHorse
        extends EntityCreature
        implements IMount<EntityClayHorse>, IEntityAdditionalSpawnData
{
    private EnumClayHorseType type;
    private int textureId = 0;
    @Nonnull
    private ItemStack doll = ItemStack.EMPTY;

    public EntityClayHorse(World world) {
        super(world);

        this.stepHeight = 0.1F;
        this.jumpMovementFactor = 0.2F;

        this.setSize(0.35F, 0.7F);
    }

    public EntityClayHorse(World world, EnumClayHorseType type, ItemStack doll) {
        this(world);

        this.type = type;
        this.doll = doll;
        this.textureId = MiscUtils.RNG.randomInt(type.textures.length);
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
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);

        compound.setInteger("horseType", this.type.ordinal());
        compound.setInteger("textureId", this.textureId);
        compound.setTag("dollItem", this.doll.serializeNBT());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);

        this.type = EnumClayHorseType.VALUES[compound.getInteger("horseType")];
        this.textureId = compound.getInteger("textureId");
        this.doll = new ItemStack(compound.getCompoundTag("dollItem"));
    }

    @Override
    public void writeSpawnData(ByteBuf buffer) {
        buffer.writeInt(this.type.ordinal());
        buffer.writeInt(this.textureId);
    }

    @Override
    public void readSpawnData(ByteBuf additionalData) {
        this.type = EnumClayHorseType.VALUES[additionalData.readInt()];
        this.textureId = additionalData.readInt();
    }

    @Override
    public IMount setSpawnedFromNexus() {
        return this;
    }

    @Override
    public void setSpecial() {

    }

    @Override
    public boolean isSpecial() {
        return false;
    }

    @Override
    public EntityClayHorse getEntity() {
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
        return this.type.textures[this.textureId];
    }
}
