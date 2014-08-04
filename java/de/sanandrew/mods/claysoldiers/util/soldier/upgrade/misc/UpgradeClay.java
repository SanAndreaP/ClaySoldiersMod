/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util.soldier.upgrade.misc;

import de.sanandrew.core.manpack.util.javatuples.Quintet;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.item.ItemClayManDoll;
import de.sanandrew.mods.claysoldiers.network.PacketProcessor;
import de.sanandrew.mods.claysoldiers.network.packet.PacketParticleFX;
import de.sanandrew.mods.claysoldiers.util.ModItems;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgradeInst;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

import java.util.Collection;

public class UpgradeClay
    extends AUpgradeMisc
{
    @Override
    public void onConstruct(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        upgradeInst.getNbtTag().setShort("uses", (short) 4);
    }

    @Override
    public boolean onUpdate(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        short uses = upgradeInst.getNbtTag().getShort("uses");

        Collection<EntityItem> items = clayMan.getItemsInRange();
        for( EntityItem item : items ) {
            if( !clayMan.hasPath() ) {
                if( item.getEntityItem() != null && item.getEntityItem().getItem() == ModItems.dollSoldier
                    && ItemClayManDoll.getTeam(item.getEntityItem()).getTeamName().equals(clayMan.getClayTeam()) )
                {
                    clayMan.setTargetFollowing(item);
                    break;
                }
            } else if( clayMan.getTargetFollowing() != null ) {
                if( item.getEntityItem() != null && item.getEntityItem().getItem() == ModItems.dollSoldier
                    && ItemClayManDoll.getTeam(item.getEntityItem()).getTeamName().equals(clayMan.getClayTeam())
                    && item.getDistanceSqToEntity(clayMan) < 1.0D )
                {
                    EntityClayMan awakened = ItemClayManDoll.spawnClayMan(clayMan.worldObj, clayMan.getClayTeam(), item.posX, item.posY, item.posZ);
                    awakened.playSound("dig.gravel", 1.0F, 1.0F);
                    PacketProcessor.sendToAllAround(PacketProcessor.PKG_PARTICLES, awakened.dimension, awakened.posX, awakened.posY, awakened.posZ, 64.0D,
                                                    Quintet.with(PacketParticleFX.FX_SOLDIER_DEATH, awakened.posX, awakened.posY, awakened.posZ,
                                                                 awakened.getClayTeam())
                    );
                    item.setDead();
                    upgradeInst.getNbtTag().setShort("uses", --uses);

                    if( uses == 0 ) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public void onPickup(EntityClayMan clayMan, SoldierUpgradeInst upgInst, ItemStack stack) {
        this.consumeItem(stack, upgInst);
        clayMan.playSound("random.pop", 1.0F, 1.0F);
    }
}
