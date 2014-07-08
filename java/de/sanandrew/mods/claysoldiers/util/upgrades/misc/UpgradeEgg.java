package de.sanandrew.mods.claysoldiers.util.upgrades.misc;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.upgrades.SoldierUpgradeInst;
import net.minecraft.item.ItemStack;

/**
 * @author SanAndreas
 * @version 1.0
 */
public class UpgradeEgg
    extends UpgradeMisc
{
//    @Override
//    public void onConstruct(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
//        NBTTagCompound nbt = upgradeInst.getNbtTag();
//        nbt.setInteger("uses", 20);
//    }

//    @Override
//    public boolean onUpdate(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
//        if( upgradeInst.getNbtTag().getInteger("uses") <= 0 ) {
//            clayMan.playSound("random.break", 1.0F, 1.0F);
//            PacketProcessor.sendToAllAround(PacketProcessor.PKG_PARTICLES, clayMan.dimension, clayMan.posX, clayMan.posY, clayMan.posZ, 64.0D,
//                                            Quintet.with(PacketParticleFX.FX_BREAK, clayMan.posX, clayMan.posY, clayMan.posZ, Item.itemRegistry.getNameForObject(Items.leather))
//                                           );
//            return true;
//        }
//        return false;
//    }

//    @Override
//    public Pair<Float, Boolean> onSoldierHurt(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, DamageSource source, float damage) {
//        upgradeInst.getNbtTag().setInteger("uses", upgradeInst.getNbtTag().getInteger("uses") - 1);
//        return Pair.with(damage / 2.0F, true);
//    }


    @Override
    public AttackState onBeingTargeted(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan attacker) {
        return AttackState.DENY;
    }

    @Override
    public float onSoldierAttack(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan target, float damage) {
        target.targetSoldier(clayMan);
        return super.onSoldierAttack(clayMan, upgradeInst, target, damage);
    }

    @Override
    public void onPickup(EntityClayMan clayMan, ItemStack stack) {
        stack.stackSize--;
        clayMan.playSound("random.pop", 1.0F, 1.0F);
    }
}
