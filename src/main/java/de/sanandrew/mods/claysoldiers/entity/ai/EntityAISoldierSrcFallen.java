/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.ai;

import de.sanandrew.mods.claysoldiers.api.soldier.ITeam;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgrade;
import de.sanandrew.mods.claysoldiers.entity.soldier.EntityClaySoldier;
import de.sanandrew.mods.claysoldiers.registry.ItemRegistry;
import de.sanandrew.mods.claysoldiers.registry.TeamRegistry;
import de.sanandrew.mods.claysoldiers.registry.upgrade.UpgradeRegistry;
import de.sanandrew.mods.claysoldiers.registry.upgrade.Upgrades;
import de.sanandrew.mods.sanlib.lib.util.ItemStackUtils;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import de.sanandrew.mods.sanlib.lib.util.UuidUtils;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;
import java.util.function.Predicate;

public class EntityAISoldierSrcFallen
        extends EntityAIBase
{
    private final EntityClaySoldier taskOwner;
    private EntityItem target;

    private final Predicate<EntityItem> tgtSelector;

    public EntityAISoldierSrcFallen(EntityClaySoldier soldier) {
        super();
        this.taskOwner = soldier;
        this.tgtSelector = entity -> entity != null && entity.isEntityAlive() && !entity.cannotPickup()
                                             && this.isValidItem(entity.getItem())
                                             && this.taskOwner.canEntityBeSeen(entity);
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
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
    public void updateTask() {
        if( this.taskOwner.followingEntity != null ) {
            if( this.taskOwner.followingEntity instanceof EntityItem && !this.tgtSelector.test((EntityItem) this.taskOwner.followingEntity) ) {
                this.taskOwner.followingEntity = null;
            }
        } else {
            this.taskOwner.followingEntity = this.target;
        }
    }

    private AxisAlignedBB getTargetableArea() {
        double targetDistance = this.taskOwner.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue();
        return this.taskOwner.getEntityBoundingBox().grow(targetDistance, targetDistance, targetDistance);
    }

    private boolean isValidItem(ItemStack stack) {
        return (TeamRegistry.INSTANCE.getTeam(stack).getId().equals(this.taskOwner.getSoldierTeam().getId()) && this.taskOwner.hasUpgrade(Upgrades.MC_CLAY, EnumUpgradeType.MISC))
                || (ItemStackUtils.isItem(stack, ItemRegistry.DOLL_BRICK_SOLDIER) && this.taskOwner.hasUpgrade(Upgrades.MC_GHASTTEAR, EnumUpgradeType.MISC));
    }
}
