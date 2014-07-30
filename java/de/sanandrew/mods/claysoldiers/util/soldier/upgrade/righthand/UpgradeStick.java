package de.sanandrew.mods.claysoldiers.util.soldier.upgrade.righthand;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.network.ParticlePacketSender;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.IMeeleeUpgrade;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgradeInst;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.commons.lang3.mutable.MutableFloat;

/**
 * @author SanAndreas
 * @version 1.0
 */
public class UpgradeStick
    extends AUpgradeRightHanded
    implements IMeeleeUpgrade
{
    @Override
    public void onConstruct(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        NBTTagCompound nbt = upgradeInst.getNbtTag();
        nbt.setInteger("uses", 20);
    }

    @Override
    public void onSoldierAttack(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan target, MutableFloat damage) {
        damage.add(2.0F + clayMan.getRNG().nextFloat());
    }

    @Override
    public void onSoldierDamage(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan target) {
        upgradeInst.getNbtTag().setInteger("uses", upgradeInst.getNbtTag().getInteger("uses") - 1);
    }

    @Override
    public boolean onUpdate(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        if( upgradeInst.getNbtTag().getInteger("uses") <= 0 ) {
            clayMan.playSound("random.break", 1.0F, 1.0F);
            ParticlePacketSender.sendBreakFx(clayMan.posX, clayMan.posY, clayMan.posZ, clayMan.dimension, Items.stick);
            return true;
        }
        return false;
    }

    @Override
    public void onPickup(EntityClayMan clayMan, SoldierUpgradeInst upgInst, ItemStack stack) {
        this.consumeItem(stack, upgInst);
        clayMan.playSound("random.pop", 1.0F, 1.0F);
    }
}
