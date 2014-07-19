/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util.soldier.upgrade.lefthand;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.entity.projectile.EntitySnowChunk;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgradeInst;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.mutable.MutableFloat;

public class UpgradeSnow
    extends AUpgradeLeftHanded
{
    @Override
    public void onConstruct(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        upgradeInst.getNbtTag().setShort("uses", (short) 20);
    }

    @Override
    public boolean onUpdate(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        return upgradeInst.getNbtTag().getShort("uses") == 0;
    }

    public void onPickup(EntityClayMan clayMan, SoldierUpgradeInst upgInst, ItemStack stack) {
        if( stack.getItem() == Items.snowball ) {
            upgInst.getNbtTag().setShort("uses", (short) 5);
        } else if( stack.getItem() == Item.getItemFromBlock(Blocks.snow_layer) ) {
            upgInst.getNbtTag().setShort("uses", (short) 10);
        }

        this.consumeItem(stack, upgInst);
        clayMan.playSound("random.pop", 1.0F, 1.0F);
    }

    @Override
    public void getAiMoveSpeed(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, MutableFloat speed) {
        Entity target = clayMan.getEntityToAttack();
        if( target instanceof EntityLivingBase && !target.isDead && clayMan.canEntityBeSeen(target) && ((EntityLivingBase) target).getHealth() > 0 ) {
            float multiplier = Math.min(1.0F, Math.max(-1.0F, (float) clayMan.getDistanceSqToEntity(target) - 16.0F));
            speed.setValue(speed.getValue() * multiplier);
        }
    }

    @Override
    public void getAttackRange(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, Entity target, MutableFloat attackRange) {
        attackRange.setValue(-1.0F);
        if( target instanceof EntityLivingBase && !target.isDead && clayMan.canEntityBeSeen(target) && ((EntityLivingBase) target).getHealth() > 0 ) {
            clayMan.throwSomethingAtEnemy(((EntityLivingBase) target), EntitySnowChunk.class, true);
            clayMan.attackTime = 30;
            upgradeInst.getNbtTag().setShort("uses", (short) (upgradeInst.getNbtTag().getShort("uses") - 1));
        }
    }
}
