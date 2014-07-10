package de.sanandrew.mods.claysoldiers.util.soldier.upgrade.misc;

import de.sanandrew.core.manpack.util.javatuples.Pair;
import de.sanandrew.core.manpack.util.javatuples.Quintet;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.network.PacketProcessor;
import de.sanandrew.mods.claysoldiers.network.packet.PacketParticleFX;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgradeInst;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;

/**
 * @author SanAndreas
 * @version 1.0
 */
public class UpgradeLeather
    extends AUpgradeMisc
{
    @Override
    public void onConstruct(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        NBTTagCompound nbt = upgradeInst.getNbtTag();
        nbt.setInteger("uses", 20);
    }

    @Override
    public boolean onUpdate(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        if( upgradeInst.getNbtTag().getInteger("uses") <= 0 ) {
            clayMan.playSound("random.break", 1.0F, 1.0F);
            PacketProcessor.sendToAllAround(PacketProcessor.PKG_PARTICLES, clayMan.dimension, clayMan.posX, clayMan.posY, clayMan.posZ, 64.0D,
                                            Quintet.with(PacketParticleFX.FX_BREAK, clayMan.posX, clayMan.posY, clayMan.posZ,
                                                         Item.itemRegistry.getNameForObject(Items.leather)
                                            )
            );
            return true;
        }
        return false;
    }

    @Override
    public Pair<Float, Boolean> onSoldierHurt(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, DamageSource source, float damage) {
        upgradeInst.getNbtTag().setInteger("uses", upgradeInst.getNbtTag().getInteger("uses") - 1);
        return Pair.with(damage / 2.0F, true);
    }

    @Override
    public void onPickup(EntityClayMan clayMan, ItemStack stack) {
        stack.stackSize--;
        clayMan.playSound("random.pop", 1.0F, 1.0F);
    }
}
