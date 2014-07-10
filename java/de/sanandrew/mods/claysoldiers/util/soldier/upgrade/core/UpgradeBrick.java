package de.sanandrew.mods.claysoldiers.util.soldier.upgrade.core;

import de.sanandrew.core.manpack.util.javatuples.Pair;
import de.sanandrew.core.manpack.util.javatuples.Triplet;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgradeInst;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

/**
 * @author SanAndreas
 * @version 1.0
 */
public class UpgradeBrick
    extends AUpgradeCore
{
    @Override
    public void onConstruct(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
    	clayMan.speed-=0.5f;
    	clayMan.canMove=false;
    }

    @Override
    public Pair<Float, Boolean> onSoldierHurt(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, DamageSource source, float damage) {
        //if( source.getEntity() instanceof EntityClayMan && ((EntityClayMan) source.getEntity()).hasUpgrade(SoldierUpgrades.getUpgradeFromName(SoldierUpgrades.UPG_IRONINGOT)) ) {
        //    clayMan.knockBack = Triplet.with(0.8D, 0.8D, 0.8D);
        //} else {
            clayMan.knockBack = Triplet.with(0.0D, 0.0D, 0.0D);
        //}
        return super.onSoldierHurt(clayMan, upgradeInst, source, damage);
    }

    @Override
    public void onPickup(EntityClayMan clayMan, ItemStack stack) {
        stack.stackSize--;
        clayMan.playSound("random.pop", 1.0F, 1.0F);
    }
}
