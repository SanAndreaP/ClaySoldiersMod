/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util.soldier.upgrade.behavior;

import de.sanandrew.core.manpack.util.SAPUtils;
import de.sanandrew.core.manpack.util.javatuples.Quintet;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.network.PacketProcessor;
import de.sanandrew.mods.claysoldiers.network.packet.PacketParticleFX;
import de.sanandrew.mods.claysoldiers.util.soldier.EnumMethodState;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgradeInst;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import org.apache.commons.lang3.mutable.MutableFloat;

public class UpgradeFermSpiderEye
        extends AUpgradeBehavior
{
    @Override
    public EnumMethodState onTargeting(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan target) {
        return EnumMethodState.DENY;
    }

    @Override
    public boolean onSoldierHurt(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, DamageSource source, MutableFloat damage) {
        if( source.getEntity() instanceof EntityClayMan ) {
            clayMan.canTargetSoldier((EntityClayMan) source.getEntity(), false);
        }

        return super.onSoldierHurt(clayMan, upgradeInst, source, damage);
    }

    @Override
    public boolean onUpdate(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        if( !clayMan.worldObj.isRemote && clayMan.ticksExisted % 10 == 0 && SAPUtils.RNG.nextInt(5) == 0 ) {
            PacketProcessor.sendToAllAround(PacketProcessor.PKG_PARTICLES, clayMan.dimension, clayMan.posX, clayMan.posY, clayMan.posZ, 64.0D,
                                            Quintet.with(PacketParticleFX.FX_BREAK, clayMan.posX, clayMan.posY, clayMan.posZ,
                                                         Item.itemRegistry.getNameForObject(Items.fermented_spider_eye))
            );
        }
        return super.onUpdate(clayMan, upgradeInst);
    }

    @Override
    public void onPickup(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, ItemStack stack) {
        this.consumeItem(stack, upgradeInst);
        clayMan.playSound("random.pop", 1.0F, 1.0F);
    }
}
