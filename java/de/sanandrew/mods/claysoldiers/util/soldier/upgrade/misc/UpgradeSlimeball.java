package de.sanandrew.mods.claysoldiers.util.soldier.upgrade.misc;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.soldier.effect.SoldierEffects;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgradeInst;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * @author SanAndreas
 * @version 1.0
 */
public class UpgradeSlimeball
    extends AUpgradeMisc
{
    @Override
    public void onConstruct(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        upgradeInst.getNbtTag().setByte("fromBlock", (byte) 0);
    }

    @Override
    public float onSoldierAttack(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan target, float damage) {
        target.applyEffect(SoldierEffects.getEffectFromName(SoldierEffects.EFF_SLIMEFEET));
        target.playSound("mob.slime.attack", 1.0F, 1.0F);
        return super.onSoldierAttack(clayMan, upgradeInst, target, damage);
    }

    @Override
    public void onPickup(EntityClayMan clayMan, ItemStack stack) {
        if( stack.getItem() == Items.slime_ball ) {
            stack.stackSize--;
            clayMan.playSound("random.pop", 1.0F, 1.0F);
        } else if( stack.getItem() == Item.getItemFromBlock(Blocks.command_block) ) { //TODO: 1.8, here goes slime block!
            clayMan.getUpgradeData(this).getNbtTag().setByte("fromBlock", (byte) 1);
            clayMan.playSound("mob.slime.small", 1.0F, 1.0F);
        }
    }
}
