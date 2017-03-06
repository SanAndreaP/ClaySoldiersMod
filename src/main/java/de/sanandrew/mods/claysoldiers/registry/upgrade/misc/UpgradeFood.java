/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry.upgrade.misc;

import de.sanandrew.mods.claysoldiers.api.Disruptable;
import de.sanandrew.mods.claysoldiers.api.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgrade;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import de.sanandrew.mods.claysoldiers.util.EnumParticle;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import org.apache.commons.lang3.mutable.MutableFloat;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class UpgradeFood
        implements ISoldierUpgrade
{
    private static final ItemStack[] UPG_ITEMS;
    private static final EnumFunctionCalls[] FUNC_CALLS = new EnumFunctionCalls[] { EnumFunctionCalls.ON_DAMAGED,
                                                                                    EnumFunctionCalls.ON_DEATH };
    private static final byte MAX_USES = 4;

    static {
        List<ItemStack> foods = new ArrayList<>();
        Item.REGISTRY.forEach(itm -> {
            if( itm instanceof ItemFood ) {
                for( int i = 0, max = itm.getHasSubtypes() ? itm.getMaxDamage() : 0; i <= max; i++ ) {
                    foods.add(new ItemStack(itm, 1, i));
                }
            }
        });
        UPG_ITEMS = foods.toArray(new ItemStack[foods.size()]);
    }

    @Override
    @Nonnull
    public ItemStack[] getStacks() {
        return UPG_ITEMS;
    }

    @Override
    public EnumFunctionCalls[] getFunctionCalls() {
        return FUNC_CALLS;
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
    public void onAdded(ISoldier<?> soldier, ItemStack stack, ISoldierUpgradeInst upgInstance) {
        if( !soldier.getEntity().world.isRemote ) {
            ItemFood itm = (ItemFood) stack.getItem();

            upgInstance.getNbtData().setByte("uses", MAX_USES);
            upgInstance.getNbtData().setFloat("restorePts", itm.getHealAmount(stack) * 0.5F);
            upgInstance.getNbtData().setBoolean("hasBowl", itm instanceof ItemSoup);

            soldier.getEntity().playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.2F, ((MiscUtils.RNG.randomFloat() - MiscUtils.RNG.randomFloat()) * 0.7F + 1.0F) * 2.0F);
            stack.stackSize--;

        }
    }

    @Override
    public void onDamaged(ISoldier<?> soldier, ISoldierUpgradeInst upgInstance, Entity attacker, DamageSource dmgSource, MutableFloat damage) {
        EntityCreature entity = soldier.getEntity();
        if( entity.getHealth() < soldier.getEntity().getMaxHealth() * 0.25F && !entity.world.isRemote && dmgSource != Disruptable.DISRUPT_DAMAGE ) {
            byte uses = (byte) (upgInstance.getNbtData().getByte("uses") - 1);
            soldier.getEntity().heal(upgInstance.getNbtData().getFloat("restorePts"));
            if( uses < 1 ) {
                soldier.destroyUpgrade(upgInstance.getUpgrade(), upgInstance.getUpgradeType(), true);
                if( upgInstance.getNbtData().getBoolean("hasBowl") ) {
                    entity.entityDropItem(new ItemStack(Items.BOWL, 1), 0.0F);
                }
            } else {
                upgInstance.getNbtData().setByte("uses", uses);
            }

            entity.playSound(SoundEvents.ENTITY_PLAYER_BURP, 0.8F, 0.8F + MiscUtils.RNG.randomFloat() * 0.4F);

            ClaySoldiersMod.proxy.spawnParticle(EnumParticle.ITEM_BREAK, entity.world.provider.getDimension(), entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ,
                                                Item.getIdFromItem(upgInstance.getSavedStack().getItem()));
        }
    }

    @Override
    public void onDeath(ISoldier<?> soldier, ISoldierUpgradeInst upgInstance, List<ItemStack> drops) {
        if( upgInstance.getNbtData().getByte("uses") >= MAX_USES ) {
            drops.add(upgInstance.getSavedStack());

        } else if( upgInstance.getNbtData().getBoolean("hasBowl") ) {
            drops.add(new ItemStack(Items.BOWL, 1));
        }
    }
}
