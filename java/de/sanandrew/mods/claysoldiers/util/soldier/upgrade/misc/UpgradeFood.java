/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util.soldier.upgrade.misc;

import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.network.ParticlePacketSender;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgradeInst;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import org.apache.commons.lang3.mutable.MutableFloat;

import java.util.ArrayList;
import java.util.List;

public class UpgradeFood
        extends AUpgradeMisc
{
    private static List<ItemFood> s_excludedFood = new ArrayList<>();

    public static boolean isFoodExcluded(ItemFood food) {
        return s_excludedFood.contains(food);
    }

    public static void excludeFood(ItemFood food) {
        s_excludedFood.add(food);
    }

    @Override
    public void onConstruct(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        upgradeInst.getNbtTag().setShort(NBT_USES, (short) 4);
    }

    @Override
    public boolean onSoldierHurt(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, DamageSource source, MutableFloat damage) {
        if( clayMan.getHealth() + upgradeInst.getNbtTag().getFloat("healAmount") - damage.getValue() <= 0.0F ) {
            upgradeInst.getNbtTag().setBoolean("killed", true);
        }

        return false;
    }

    @Override
    public boolean onUpdate(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        if( clayMan.getHealth() < clayMan.getMaxHealth() * 0.25F && !upgradeInst.getNbtTag().getBoolean("killed") ) {
            upgradeInst.getNbtTag().setShort(NBT_USES, (short) (upgradeInst.getNbtTag().getShort(NBT_USES) - 1));
            clayMan.heal(upgradeInst.getNbtTag().getFloat("healAmount"));
            this.spawnParticles(clayMan, upgradeInst);
            clayMan.playSound("random.eat", 1.0F, 0.9F + SAPUtils.RNG.nextFloat() * 0.2F);
        }

        if( upgradeInst.getNbtTag().getShort(NBT_USES) == 0 ) {
            if( upgradeInst.getStoredItem().getItem() instanceof ItemSoup ) {
                clayMan.entityDropItem(new ItemStack(Items.bowl, 1), 0.0F);
            }

            return true;
        }

        return false;
    }

    @Override
    public void onPickup(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, ItemStack stack) {
        this.consumeItem(stack, upgradeInst);
        clayMan.playSound("random.pop", 1.0F, 1.0F);

        upgradeInst.getNbtTag().setFloat("healAmount", ((ItemFood) stack.getItem()).func_150905_g(stack));
    }

    protected void spawnParticles(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        ParticlePacketSender.sendBreakFx(clayMan.posX, clayMan.posY, clayMan.posZ, clayMan.dimension, upgradeInst.getStoredItem().getItem());
    }

    @Override
    public void onItemDrop(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, ArrayList<ItemStack> droppedItems) {
        // TODO: drop it when unused or?
        if( upgradeInst.getNbtTag().getShort(NBT_USES) == 4 ) {
            droppedItems.add(upgradeInst.getStoredItem());
        }
    }
}
