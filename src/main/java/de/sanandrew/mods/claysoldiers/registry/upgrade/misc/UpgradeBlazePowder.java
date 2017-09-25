/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry.upgrade.misc;

import de.sanandrew.mods.claysoldiers.api.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgFunctions;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgrade;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.UpgradeFunctions;
import de.sanandrew.mods.claysoldiers.registry.upgrade.Upgrades;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

@UpgradeFunctions({EnumUpgFunctions.ON_DEATH, EnumUpgFunctions.ON_ATTACK_SUCCESS, EnumUpgFunctions.ON_TICK})
public class UpgradeBlazePowder
        implements ISoldierUpgrade
{
    private static final ItemStack[] UPG_ITEMS = { new ItemStack(Items.BLAZE_POWDER, 1) };
    private static final short MAX_USES = 1;

    @Nonnull
    @Override
    public ItemStack[] getStacks() {
        return UPG_ITEMS;
    }

    @Nonnull
    @Override
    public EnumUpgradeType getType(ISoldier<?> checker) {
        return EnumUpgradeType.MISC;
    }

    @Override
    public boolean syncData() {
        return true;
    }

    @Override
    public void onAdded(ISoldier<?> soldier, ItemStack stack, ISoldierUpgradeInst upgradeInst) {
        if( !soldier.getEntity().world.isRemote ) {
            upgradeInst.getNbtData().setShort("uses", MAX_USES);
            soldier.getEntity().playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.2F, ((MiscUtils.RNG.randomFloat() - MiscUtils.RNG.randomFloat()) * 0.7F + 1.0F) * 2.0F);
            stack.shrink(1);
        }
    }

    @Override
    public void onAttackSuccess(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst, Entity target) {
        if( target instanceof ISoldier ) {
            if( MiscUtils.RNG.randomBool() || !((ISoldier) target).hasUpgrade(Upgrades.EM_IRONBLOCK, EnumUpgradeType.ENHANCEMENT) ) {
                target.attackEntityFrom(DamageSource.ON_FIRE, Float.MAX_VALUE);
            }
            soldier.getEntity().playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 0.2F, ((MiscUtils.RNG.randomFloat() - MiscUtils.RNG.randomFloat()) * 0.7F + 1.0F) * 2.0F);

            short uses = (short) (upgradeInst.getNbtData().getShort("uses") - 1);
            if( uses < 1 ) {
                soldier.destroyUpgrade(upgradeInst.getUpgrade(), upgradeInst.getUpgradeType(), false);
            } else {
                upgradeInst.getNbtData().setShort("uses", uses);
            }
        }
    }

    @Override
    public void onTick(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst) {
        EntityCreature soldierL = soldier.getEntity();
        if( soldierL.world.isRemote && MiscUtils.RNG.randomInt(10) == 0 ) {
            soldierL.world.spawnParticle(EnumParticleTypes.FLAME, soldierL.posX, soldierL.posY, soldierL.posZ, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public void onDeath(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst, DamageSource dmgSource, NonNullList<ItemStack> drops) {
        if( upgradeInst.getNbtData().getShort("uses") >= MAX_USES ) {
            drops.add(upgradeInst.getSavedStack());
        }
    }
}
