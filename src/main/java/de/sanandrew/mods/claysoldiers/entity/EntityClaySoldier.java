/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP, SilverChiren and CliffracerX
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported
   *            (http://creativecommons.org/licenses/by-nc-sa/3.0/)
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity;

import de.sanandrew.mods.claysoldiers.api.Disruptable;
import de.sanandrew.mods.claysoldiers.api.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.soldier.IUpgrade;
import de.sanandrew.mods.claysoldiers.api.soldier.IUpgradeInst;
import de.sanandrew.mods.claysoldiers.api.soldier.Team;
import de.sanandrew.mods.claysoldiers.entity.ai.EntityAISoldierAttackMelee;
import de.sanandrew.mods.claysoldiers.entity.ai.EntityAISoldierAttackableTarget;
import de.sanandrew.mods.claysoldiers.entity.ai.EntityAISoldierPickupUpgrade;
import de.sanandrew.mods.claysoldiers.entity.ai.EntityAISoldierUpgradeItem;
import de.sanandrew.mods.claysoldiers.item.ItemDisruptor;
import de.sanandrew.mods.claysoldiers.network.datasync.DataSerializerUUID;
import de.sanandrew.mods.claysoldiers.network.datasync.DataWatcherBooleans;
import de.sanandrew.mods.claysoldiers.registry.TeamRegistry;
import de.sanandrew.mods.claysoldiers.registry.UpgradeRegistry;
import de.sanandrew.mods.claysoldiers.util.HashItemStack;
import de.sanandrew.mods.sanlib.lib.util.ItemStackUtils;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import de.sanandrew.mods.sanlib.lib.util.UuidUtils;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.mutable.MutableFloat;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

public class EntityClaySoldier
        extends EntityCreature
        implements Disruptable, ISoldier<EntityClaySoldier>
{
    private static final DataParameter<UUID> TEAM_PARAM = EntityDataManager.createKey(EntityClaySoldier.class, DataSerializerUUID.INSTANCE);
    private static final DataParameter<Byte> TEXTURE_TYPE_PARAM = EntityDataManager.createKey(EntityClaySoldier.class, DataSerializers.BYTE);
    private static final DataParameter<Byte> TEXTURE_ID_PARAM = EntityDataManager.createKey(EntityClaySoldier.class, DataSerializers.BYTE);
    private final DataWatcherBooleans<EntityClaySoldier> dwBooleans;

    private final Map<IUpgrade.EnumFunctionCalls, Map<Integer, Queue<IUpgradeInst>>> upgradeFuncMap;
    private final Queue<IUpgradeInst> upgradeSyncList;
    private final Map<HashItemStack, IUpgradeInst> upgradeItemMap;

    private ItemStack doll;

    public Entity followingEntity;

    public EntityClaySoldier(World world) {
        super(world);

        this.stepHeight = 0.1F;
        this.ignoreFrustumCheck = true;
        this.jumpMovementFactor = 0.2F;

        this.setSize(0.17F, 0.4F);

        this.dataManager.register(TEAM_PARAM, TeamRegistry.NULL_TEAM.getId());
        this.dataManager.register(TEXTURE_TYPE_PARAM, (byte) 0x00);
        this.dataManager.register(TEXTURE_ID_PARAM, (byte) 0x00);

        this.dwBooleans = new DataWatcherBooleans<>(this);
        this.dwBooleans.registerDwValue();

        this.upgradeFuncMap = initUpgFuncMap();
        this.upgradeSyncList = new ConcurrentLinkedQueue<>();
        this.upgradeItemMap = new ConcurrentHashMap<>();

        this.setMovable(true);

        ((PathNavigateGround) this.getNavigator()).setCanSwim(true);
    }

    public EntityClaySoldier(World world, @Nonnull Team team, @Nullable ItemStack doll) {
        this(world, team);

        this.doll = doll;
    }

    public EntityClaySoldier(World world, @Nonnull Team team) {
        this(world);

        this.dataManager.set(TEAM_PARAM, team.getId());

        if( MiscUtils.RNG.randomInt(1_000_000) == 0 ) {
            int[] texIds = team.getUniqueTextureIds();
            if( texIds.length > 0 ) {
                this.setUniqueTextureId((byte) texIds[MiscUtils.RNG.randomInt(texIds.length)]);
            }
        } else if( MiscUtils.RNG.randomInt(250) == 0 ) {
            int[] texIds = team.getRareTextureIds();
            if( texIds.length > 0 ) {
                this.setRareTextureId((byte) texIds[MiscUtils.RNG.randomInt(texIds.length)]);
            }
        } else {
            int[] texIds = team.getNormalTextureIds();
            this.setNormalTextureId((byte) texIds[MiscUtils.RNG.randomInt(texIds.length)]);
        }
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAISoldierPickupUpgrade(this, 1.0D));
        this.tasks.addTask(2, new EntityAISoldierAttackMelee(this, 1.0D));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.5D));
        this.tasks.addTask(7, new EntityAIWander(this, 0.5D));
        this.tasks.addTask(8, new EntityAILookIdle(this));

        this.targetTasks.addTask(1, new EntityAISoldierUpgradeItem(this));
        this.targetTasks.addTask(2, new EntityAISoldierAttackableTarget(this));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();

        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);

        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
    }

    @Override
    public boolean canMove() {
        return this.dwBooleans.getBit(DataWatcherBooleans.Soldier.CAN_MOVE.bit);
    }

    @Override
    public void setMovable(boolean move) {
        this.dwBooleans.setBit(DataWatcherBooleans.Soldier.CAN_MOVE.bit, move);
    }

    @Override
    public void setBreathableUnderwater(boolean breathable) {
        this.dwBooleans.setBit(DataWatcherBooleans.Soldier.BREATHE_WATER.bit, breathable);
    }

    @Override
    public Team getSoldierTeam() {
        return TeamRegistry.INSTANCE.getTeam(this.dataManager.get(TEAM_PARAM));
    }

    @Override
    public EntityClaySoldier getEntity() {
        return this;
    }

    @Override
    public int getTextureType() {
        return this.dataManager.get(TEXTURE_TYPE_PARAM);
    }

    @Override
    public int getTextureId() {
        return this.dataManager.get(TEXTURE_ID_PARAM);
    }

    @Override
    public void setNormalTextureId(byte id) {
        if( id < 0 || id >= this.getSoldierTeam().getNormalTextureIds().length ) {
            return;
        }

        this.dataManager.set(TEXTURE_TYPE_PARAM, (byte) 0x00);
        this.dataManager.set(TEXTURE_ID_PARAM, id);
    }

    @Override
    public void setRareTextureId(byte id) {
        if( id < 0 || id >= this.getSoldierTeam().getRareTextureIds().length ) {
            return;
        }

        this.dataManager.set(TEXTURE_TYPE_PARAM, (byte) 0x01);
        this.dataManager.set(TEXTURE_ID_PARAM, id);
    }

    @Override
    public void setUniqueTextureId(byte id) {
        if( id < 0 || id >= this.getSoldierTeam().getNormalTextureIds().length ) {
            return;
        }

        this.dataManager.set(TEXTURE_TYPE_PARAM, (byte) 0x02);
        this.dataManager.set(TEXTURE_ID_PARAM, id);
    }

    @Override
    public void destroyUpgrade(IUpgrade upgrade) {
        HashItemStack hashStack = new HashItemStack(upgrade.getItem());
        IUpgradeInst inst = this.upgradeItemMap.get(hashStack);

        this.upgradeItemMap.remove(hashStack);
        this.upgradeSyncList.remove(inst);
        this.upgradeFuncMap.forEach((key, val) -> {if( Arrays.asList(upgrade.getFunctionCalls()).contains(key) ) { val.remove(upgrade.getPriority(), inst); }});

        this.playSound(SoundEvents.ENTITY_ITEM_BREAK, 0.8F, 0.8F + MiscUtils.RNG.randomFloat() * 0.4F);
    }

    @Override
    public boolean hasUpgrade(ItemStack stack) {
        if( !ItemStackUtils.isValid(stack) ) {
            return false;
        }

        ItemStack wcStack = stack.copy();
        wcStack.setItemDamage(OreDictionary.WILDCARD_VALUE);

        return this.upgradeItemMap.containsKey(new HashItemStack(stack)) || this.upgradeItemMap.containsKey(new HashItemStack(wcStack));
    }

    @Override
    public boolean hasUpgrade(IUpgrade upgrade) {
        return this.hasUpgrade(upgrade.getItem());
    }

    @Override
    public void moveEntity(double motionX, double motionY, double motionZ) {
        if( this.canMove() ) {
            super.moveEntity(motionX, motionY, motionZ);
        } else {
            super.moveEntity(0.0D, motionY > 0.0D ? motionY / 2.0D : motionY, 0.0D);
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        if( !(entityIn instanceof EntityLivingBase) ) {
            return false;
        }

        EntityLivingBase trevor = ((EntityLivingBase) entityIn);

        MutableFloat attackDmg = new MutableFloat((float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
        DamageSource dmgSrc = DamageSource.causeMobDamage(this);
        int i = 0;

//        attackDmg += EnchantmentHelper.getModifierForCreature(this.getHeldItemMainhand(), trevor.getCreatureAttribute());
//        i += EnchantmentHelper.getKnockbackModifier(this);

        this.callUpgradeFunc(IUpgrade.EnumFunctionCalls.ON_ATTACK, upg -> upg.getUpgrade().onAttack(this, upg, trevor, dmgSrc, attackDmg));

        boolean attackSuccess = trevor.attackEntityFrom(dmgSrc, attackDmg.floatValue());

        if( attackSuccess ) {
            if( i > 0 ) {
                trevor.knockBack(this, i * 0.5F, MathHelper.sin(this.rotationYaw * 0.017453292F), (-MathHelper.cos(this.rotationYaw * 0.017453292F)));
                this.motionX *= 0.6D;
                this.motionZ *= 0.6D;
            }

            int fireAspectMod = EnchantmentHelper.getFireAspectModifier(this);

            if( fireAspectMod > 0 ) {
                trevor.setFire(fireAspectMod * 4);
            }

            this.applyEnchantments(this, trevor);

            float additDifficulty = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();
            if( this.getHeldItemMainhand() == null ) {
                if( this.isBurning() && this.rand.nextFloat() < additDifficulty * 0.3F ) {
                    trevor.setFire(2 * (int)additDifficulty);
                }
            }

            this.callUpgradeFunc(IUpgrade.EnumFunctionCalls.ON_ATTACK_SUCCESS, upg -> upg.getUpgrade().onAttackSuccess(this, upg, trevor));
        }

        return attackSuccess;
    }

    @Override
    protected float getWaterSlowDown() {
        return 1.0F;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

//        this.navigator.clearPathEntity();
//        this.cloakHelper.onUpdate(this.posX, this.posY, this.posZ);

        this.callUpgradeFunc(IUpgrade.EnumFunctionCalls.ON_TICK, upg -> upg.getUpgrade().onTick(this, upg));

        if( !this.canMove() ) {
            this.motionX = 0.0F;
            this.motionZ = 0.0F;
            this.isJumping = false;
        }

//        if( !this.world.isRemote ) {
//            System.out.println(this.getAttackTarget());
//        }

//        this.canMove = true;

//        Iterator<Entry<ASoldierUpgrade, SoldierUpgradeInst>> iterUpgrades = p_upgrades.entrySet().iterator();
//        while( iterUpgrades.hasNext() ) {
//            if( !this.worldObj.isRemote ) {
//                SoldierUpgradeInst upg = iterUpgrades.next().getValue();
//                if( upg.getUpgrade().onUpdate(this, upg) ) {
//                    iterUpgrades.remove();
//                }
//            } else {
//                SoldierUpgradeInst upg = iterUpgrades.next().getValue();
//                upg.getUpgrade().onClientUpdate(this, upg);
//            }
//        }
//        Iterator<Entry<ASoldierEffect, SoldierEffectInst>> iterEffects = p_effects.entrySet().iterator();
//        while( iterEffects.hasNext() ) {
//            if( !this.worldObj.isRemote ) {
//                SoldierEffectInst upg = iterEffects.next().getValue();
//                if( upg.getEffect().onUpdate(this, upg) ) {
//                    iterEffects.remove();
//                }
//            } else {
//                SoldierEffectInst upg = iterEffects.next().getValue();
//                upg.getEffect().onClientUpdate(this, upg);
//            }
//        }

//        if( (ticksExisted % 5) == 0 ) {
//            this.updateUpgradeEffectRenders();
//        }

    }

    @Override
    public boolean canBreatheUnderwater() {
        return this.dwBooleans.getBit(DataWatcherBooleans.Soldier.BREATHE_WATER.bit);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);

        compound.setString("soldier_team", this.dataManager.get(TEAM_PARAM).toString());
        compound.setByte("soldier_texture_type", this.dataManager.get(TEXTURE_TYPE_PARAM));
        compound.setByte("soldier_texture_id", this.dataManager.get(TEXTURE_ID_PARAM));

        if( this.doll != null ) {
            ItemStackUtils.writeStackToTag(this.doll, compound, "soldier_doll");
        }

        this.dwBooleans.writeToNbt(compound);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);

        String teamId = compound.getString("soldier_team");
        this.dataManager.set(TEAM_PARAM, UuidUtils.isStringUuid(teamId) ? UUID.fromString(teamId) : UuidUtils.EMPTY_UUID);
        this.dataManager.set(TEXTURE_TYPE_PARAM, compound.getByte("soldier_texture_type"));
        this.dataManager.set(TEXTURE_ID_PARAM, compound.getByte("soldier_texture_id"));

        if( compound.hasKey("soldier_doll", Constants.NBT.TAG_COMPOUND) ) {
            this.doll = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("soldier_doll"));
        }

        this.dwBooleans.readFromNbt(compound);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float damage) {
        Entity srcEntity = source.getEntity();
        if( !(srcEntity instanceof EntityPlayer) && source != ItemDisruptor.DISRUPT_DAMAGE ) {
            if( this.getRidingEntity() != null && MiscUtils.RNG.randomInt(4) == 0 ) {
                this.getRidingEntity().attackEntityFrom(source, damage);
                return false;
            }
        } else {
            damage = Float.MAX_VALUE;
        }

        if( srcEntity instanceof EntityClaySoldier ) {
            if( srcEntity == this.getAttackTarget() ) {
                this.getNavigator().clearPathEntity();
            }
            this.setAttackTarget((EntityClaySoldier) srcEntity);
        }

        return super.attackEntityFrom(source, damage);
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);

        if( !this.world.isRemote ) {
//            if( damageSource.isFireDamage() && this.dollItem != null ) {
//                ItemStack brickItem = new ItemStack(RegistryItems.dollBrick, this.dollItem.stackSize);
//                brickItem.setTagCompound(this.dollItem.getTagCompound());
//                this.dollItem = brickItem;
//            }

            ArrayList<ItemStack> drops = new ArrayList<>();

            if( this.doll != null ) {
                drops.add(this.doll);
            }
//            if( !this.nexusSpawn ) {
//                if( this.dollItem != null ) {
//                    drops.add(this.dollItem.copy());
//                }

//                for( SoldierUpgradeInst upg : this.p_upgrades.values() ) {
//                    upg.getUpgrade().onItemDrop(this, upg, drops);
//                }
//
                drops.removeAll(Collections.<ItemStack>singleton(null));
                for( ItemStack drop : drops ) {
                    this.entityDropItem(drop, 0.0F);
                }
//            }

//            for( SoldierUpgradeInst upg : this.p_upgrades.values() ) {
//                upg.getUpgrade().onSoldierDeath(this, upg, damageSource);
//            }
//
//            for( SoldierEffectInst eff : this.p_effects.values() ) {
//                eff.getEffect().onSoldierDeath(this, eff, damageSource);
//            }
        }
    }

    @Override
    public void knockBack(Entity par1Entity, float par2, double par3, double par5) {
        if( !this.canMove() ) {
            return;
        }

        super.knockBack(par1Entity, par2, par3, par5);
    }

    @Override
    public boolean canEntityBeSeen(Entity target) {
//        RayTraceResult res = RayTraceFixed.rayTraceSight(this, this.world, new Vec3d(this.posX, this.posY + this.getEyeHeight(), this.posZ),
//                                                         new Vec3d(target.posX, target.posY + target.getEyeHeight(), target.posZ));
//        return res == null;
        return super.canEntityBeSeen(target);
    }

    @Override
    public boolean canBePushed() {
        return this.canMove();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return null;
    }


    @Override
    protected SoundEvent getHurtSound() {
        return SoundEvents.BLOCK_GRAVEL_BREAK;//return ModConfig.useOldHurtSound ? "claysoldiers:mob.soldier.hurt" : "dig.gravel";
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
//        ParticlePacketSender.sendSoldierDeathFx(this.posX, this.posY, this.posZ, this.dimension, this.getClayTeam());
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance) {
        double bbEdgeLength = this.getEntityBoundingBox().getAverageEdgeLength();

        if( Double.isNaN(bbEdgeLength) ) {
            bbEdgeLength = 1.0D;
        }

        bbEdgeLength = bbEdgeLength * 320.0D;

        return distance < bbEdgeLength * bbEdgeLength;
    }

    @Override
    public double getYOffset() {
        return 0.01F;
    }

    @Override
    public void disrupt() {
        this.attackEntityFrom(ItemDisruptor.DISRUPT_DAMAGE, Float.MAX_VALUE);
    }

    @Override
    public DisruptType getDisruptType() {
        return DisruptType.SOLDIER;
    }

    private static EnumMap<IUpgrade.EnumFunctionCalls, Map<Integer, Queue<IUpgradeInst>>> initUpgFuncMap() {
        EnumMap<IUpgrade.EnumFunctionCalls, Map<Integer, Queue<IUpgradeInst>>> enumMap = new EnumMap<>(IUpgrade.EnumFunctionCalls.class);
        Arrays.asList(IUpgrade.EnumFunctionCalls.VALUES).forEach(val -> enumMap.put(val, new ConcurrentHashMap<>()));

        return enumMap;
    }

    public boolean pickupUpgrade(EntityItem item) {
        if( item.getEntityItem().stackSize < 1 ) {
            return false;
        }

        IUpgrade upg = UpgradeRegistry.INSTANCE.getUpgrade(item.getEntityItem());
        if( upg == null ) {
            return false;
        }

        IUpgradeInst upgInst = new SoldierUpgrade(upg, item.getEntityItem().copy().splitStack(1));

        List<IUpgrade.EnumFunctionCalls> funcCalls = Arrays.asList(upg.getFunctionCalls());
        if( funcCalls.contains(IUpgrade.EnumFunctionCalls.ON_PICKUP) ) {
            upg.onPickup(this, item.getEntityItem(), upgInst);
        }

        this.upgradeItemMap.put(new HashItemStack(upg.getItem()), upgInst);
        this.upgradeSyncList.add(upgInst);
        funcCalls.forEach(func -> {
            Queue<IUpgradeInst> upgList = this.upgradeFuncMap.get(func).computeIfAbsent(upg.getPriority(), k -> new ConcurrentLinkedQueue<>());
            upgList.add(upgInst);
        });

        this.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.2F, (MiscUtils.RNG.randomFloat() - MiscUtils.RNG.randomFloat()) * 0.7F + 1.0F);
        return true;
    }

    private void callUpgradeFunc(IUpgrade.EnumFunctionCalls funcCall, final Consumer<IUpgradeInst> forEach) {
        Map<Integer, Queue<IUpgradeInst>> upgCalls = this.upgradeFuncMap.get(funcCall);
        List<Integer> pririties = new ArrayList<>(upgCalls.keySet());
        Collections.sort(pririties);
        pririties.forEach(val -> upgCalls.get(val).forEach(forEach));
    }
}
