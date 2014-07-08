package de.sanandrew.mods.claysoldiers.util.upgrades.righthand;

import de.sanandrew.core.manpack.util.javatuples.Quintet;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.network.PacketProcessor;
import de.sanandrew.mods.claysoldiers.network.packet.PacketParticleFX;
import de.sanandrew.mods.claysoldiers.util.upgrades.SoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.util.upgrades.SoldierUpgrades;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * @author SanAndreas
 * @version 1.0
 */
public class UpgradeBlazeRod
        extends AUpgradeRightHanded
{
    @Override
    public void onConstruct(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        NBTTagCompound nbt = upgradeInst.getNbtTag();
        nbt.setInteger("uses", 20);
    }

    @Override
    public float onSoldierAttack(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan target, float damage) {
    	if( !clayMan.hasUpgrade(SoldierUpgrades.getUpgradeFromName(SoldierUpgrades.UPG_COAL)) ) {
    		target.setFire(3);
    	} else {
    		target.setFire(6);
    	}

        return damage + 1.0F + clayMan.getRNG().nextFloat();
    }

    @Override
    public void onSoldierDamage(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan target) {
        upgradeInst.getNbtTag().setInteger("uses", upgradeInst.getNbtTag().getInteger("uses") - 1);
    }

    @Override
    public boolean onUpdate(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        if( upgradeInst.getNbtTag().getInteger("uses") <= 0 ) {
            clayMan.playSound("random.break", 1.0F, 1.0F);
            PacketProcessor.sendToAllAround(PacketProcessor.PKG_PARTICLES, clayMan.dimension, clayMan.posX, clayMan.posY, clayMan.posZ, 64.0D,
                    Quintet.with(PacketParticleFX.FX_BREAK, clayMan.posX, clayMan.posY, clayMan.posZ, Item.itemRegistry.getNameForObject(Items.blaze_rod))
            );
            return true;
        }
        return false;
    }

    @Override
    public void onPickup(EntityClayMan clayMan, ItemStack stack) {
        stack.stackSize--;
        clayMan.playSound("random.pop", 1.0F, 1.0F);
    }
}
