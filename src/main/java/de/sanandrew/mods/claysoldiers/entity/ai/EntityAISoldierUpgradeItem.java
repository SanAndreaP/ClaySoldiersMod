/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.ai;

import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgrade;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.entity.EntityClaySoldier;
import de.sanandrew.mods.claysoldiers.registry.upgrade.UpgradeRegistry;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;
import java.util.function.Predicate;

public class EntityAISoldierUpgradeItem
        extends EntityAIBase
{
    private final EntityClaySoldier taskOwner;
    private EntityItem target;

    private final Predicate<EntityItem> tgtSelector;

    public EntityAISoldierUpgradeItem(EntityClaySoldier soldier) {
        super();
        this.taskOwner = soldier;
        this.tgtSelector = entity -> {
            if( entity != null && entity.isEntityAlive() && !entity.cannotPickup() ) {
                ISoldierUpgrade upgrade = UpgradeRegistry.INSTANCE.getUpgrade(entity.getItem());
                return upgrade != null && upgrade.checkPickupable(this.taskOwner, entity.getItem()) && this.taskOwner.canEntityBeSeen(entity)
                       && !this.taskOwner.hasUpgrade(entity.getItem(), upgrade.getType(this.taskOwner))
                       && (upgrade.getType(this.taskOwner) != EnumUpgradeType.MAIN_HAND || !this.taskOwner.hasMainHandUpgrade())
                       && (upgrade.getType(this.taskOwner) != EnumUpgradeType.OFF_HAND || !this.taskOwner.hasOffHandUpgrade());
            }
            return false;
        };
        this.setMutexBits(2);
    }

    @Override
    public boolean shouldExecute() {
        if( this.taskOwner.followingEntity != null ) {
            if( this.taskOwner.followingEntity instanceof EntityItem && !this.tgtSelector.test((EntityItem) this.taskOwner.followingEntity) ) {
                this.taskOwner.followingEntity = null;
            }
            return false;
        }

        List<EntityItem> list = this.taskOwner.world.getEntitiesWithinAABB(EntityItem.class, this.getTargetableArea(), this.tgtSelector::test);

        if( list.isEmpty() ) {
            return false;
        } else {
            this.target = list.get(MiscUtils.RNG.randomInt(list.size()));
            return true;
        }
    }

    @Override
    public void resetTask() {
        this.target = null;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.taskOwner.followingEntity == null;
    }

    @Override
    public void updateTask() {
        this.taskOwner.followingEntity = this.target;
    }

    private AxisAlignedBB getTargetableArea() {
        double targetDistance = this.taskOwner.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue();
        return this.taskOwner.getEntityBoundingBox().grow(targetDistance, targetDistance, targetDistance);
    }
}
