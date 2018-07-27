/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.companion;

import de.sanandrew.mods.claysoldiers.api.NBTConstants;
import de.sanandrew.mods.claysoldiers.api.attribute.CsmMobAttributes;
import de.sanandrew.mods.claysoldiers.api.doll.IDollType;
import de.sanandrew.mods.claysoldiers.api.entity.IDisruptable;
import de.sanandrew.mods.claysoldiers.api.entity.ITargetingEntity;
import de.sanandrew.mods.claysoldiers.api.entity.companion.ICompanion;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.ITeam;
import de.sanandrew.mods.claysoldiers.entity.EntityHelper;
import de.sanandrew.mods.claysoldiers.entity.ai.EntityAIFollowEnemy;
import de.sanandrew.mods.claysoldiers.entity.ai.EntityAIMoveAwayFromCorners;
import de.sanandrew.mods.claysoldiers.entity.ai.EntityAISearchTarget;
import de.sanandrew.mods.claysoldiers.entity.ai.PathHelper;
import de.sanandrew.mods.claysoldiers.network.datasync.DataSerializerUUID;
import de.sanandrew.mods.claysoldiers.registry.team.TeamRegistry;
import de.sanandrew.mods.claysoldiers.util.CsmConfig;
import de.sanandrew.mods.sanlib.lib.util.ItemStackUtils;
import de.sanandrew.mods.sanlib.lib.util.UuidUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

import javax.annotation.Nonnull;
import java.util.UUID;

public abstract class EntityClayCompanion<E extends EntityCreature, T extends IDollType>
        extends EntityCreature
        implements ICompanion<E>, IEntityAdditionalSpawnData, ITargetingEntity<E>
{
    private static final DataParameter<UUID> OCCUPATION_PARAM = EntityDataManager.createKey(EntityClayCompanion.class, DataSerializerUUID.INSTANCE);

    private final PathHelper pathHelper;
    T type = this.getUnknownType();
    int textureId = 0;
    @Nonnull
    ItemStack doll = ItemStack.EMPTY;

    public EntityClayCompanion(World worldIn) {
        super(worldIn);

        this.dataManager.register(OCCUPATION_PARAM, TeamRegistry.NULL_TEAM.getId());

        this.stepHeight = 0.1F;
        this.jumpMovementFactor = 0.2F;

        this.pathHelper = new PathHelper(this);
    }

    public EntityClayCompanion(World worldIn, T type, ItemStack doll) {
        this(worldIn);

        this.doll = doll;
        this.setType(type);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();

        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);

        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAIMoveAwayFromCorners(this, 0.5D));
        this.tasks.addTask(7, new EntityAIWander(this, 0.5D));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyAttackAI();

        this.targetTasks.addTask(4, new EntityAISearchTarget.Enemy(this));
    }

    protected void applyAttackAI() {
        this.tasks.addTask(5, new EntityAIFollowEnemy.Meelee(this, 1.0D));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);

        compound.setString(NBTConstants.E_COMPANION_TEAM, this.dataManager.get(OCCUPATION_PARAM).toString());
        compound.setInteger(NBTConstants.E_TEXTURE_ID, this.textureId);
        compound.setTag(NBTConstants.E_DOLL_ITEM, this.doll.serializeNBT());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);

        String teamId = compound.getString(NBTConstants.E_COMPANION_TEAM);
        this.dataManager.set(OCCUPATION_PARAM, UuidUtils.isStringUuid(teamId) ? UUID.fromString(teamId) : UuidUtils.EMPTY_UUID);
        this.textureId = compound.getInteger(NBTConstants.E_TEXTURE_ID);
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
    public void onUpdate() {
        this.pathHelper.update();

        super.onUpdate();
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);

        if( !this.world.isRemote ) {
            NonNullList<ItemStack> drops = NonNullList.create();

            if( ItemStackUtils.isValid(this.doll) ) {
                drops.add(this.doll);
            }

            EntityHelper.dropItems(this, drops);
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
    public void disrupt() {
        this.attackEntityFrom(IDisruptable.DISRUPT_DAMAGE, Float.MAX_VALUE);
    }

    @Override
    public DisruptState getDisruptableState() {
        return DisruptState.COMPANIONS;
    }

    public T getType() {
        return this.type;
    }

    abstract void setType(T type);

    abstract void spawnBreakParticles();

    abstract float getMovementMultiplier();

    abstract T getUnknownType();

    @Override
    public ICompanion setSpawnedFromNexus() {
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
    public ITeam getOccupation() {
        return TeamRegistry.INSTANCE.getTeam(this.dataManager.get(OCCUPATION_PARAM));
    }

    @Override
    public void applyOccupation(ITeam team) {
        if( !this.world.isRemote ) {
            this.dataManager.set(OCCUPATION_PARAM, team.getId());
        }
    }

    @Override
    public void writeSpawnData(ByteBuf buffer) {

    }

    @Override
    public void readSpawnData(ByteBuf additionalData) {

    }
}
