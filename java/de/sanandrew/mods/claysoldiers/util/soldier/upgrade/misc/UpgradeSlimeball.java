package de.sanandrew.mods.claysoldiers.util.soldier.upgrade.misc;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.entity.projectile.ISoldierProjectile;
import de.sanandrew.mods.claysoldiers.util.soldier.effect.SoldierEffects;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgradeInst;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import org.apache.commons.lang3.mutable.MutableFloat;

/**
 * @author SanAndreas
 * @version 1.0
 */
public class UpgradeSlimeball
    extends AUpgradeMisc
{
    @Override
    public void onSoldierAttack(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan target, MutableFloat damage) {
        if( target.addEffect(SoldierEffects.getEffect(SoldierEffects.EFF_SLIMEFEET)) != null ) {
            target.playSound("mob.slime.attack", 1.0F, 1.0F);
        }
    }

    @Override
    public void onPickup(EntityClayMan clayMan, SoldierUpgradeInst upgInst, ItemStack stack) {
        if( stack.getItem() == Items.slime_ball ) {
            this.consumeItem(stack, upgInst);
            clayMan.playSound("random.pop", 1.0F, 1.0F);
        } else if( stack.getItem() == Item.getItemFromBlock(Blocks.command_block) ) { //TODO: 1.8, here goes slime block!
            clayMan.playSound("mob.slime.small", 1.0F, 1.0F);
        }
    }

    @Override
    public void onProjectileHit(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, MovingObjectPosition target,
                                ISoldierProjectile<? extends EntityThrowable> projectile) {
        if( target.entityHit instanceof EntityClayMan ) {
            EntityClayMan caddicarus = (EntityClayMan) target.entityHit;
            if( caddicarus.addEffect(SoldierEffects.getEffect(SoldierEffects.EFF_SLIMEFEET)) != null ) {
                caddicarus.playSound("mob.slime.attack", 1.0F, 1.0F);
            }
        }
    }
}
