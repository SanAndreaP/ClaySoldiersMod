/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.ai;

import de.sanandrew.mods.claysoldiers.api.doll.ItemDoll;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.registry.ItemRegistry;
import de.sanandrew.mods.claysoldiers.registry.team.TeamRegistry;
import de.sanandrew.mods.claysoldiers.registry.upgrade.Upgrades;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeClay;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeGhastTear;
import de.sanandrew.mods.sanlib.lib.util.ItemStackUtils;
import net.minecraft.item.ItemStack;

import java.util.Objects;

public final class ResurrectionHelper
{
    public static boolean canBeResurrected(ISoldier<?> solder, ItemStack stack) {
        if( ItemStackUtils.isItem(stack, ItemRegistry.DOLL_BRICK_SOLDIER) ) {
            return solder.hasUpgrade(Upgrades.MC_GHASTTEAR, EnumUpgradeType.MISC);
        } else if( stack.getItem() instanceof ItemDoll && ((ItemDoll<?, ?>) stack.getItem()).canBeResurrected(stack, solder) ) {
            return solder.hasUpgrade(Upgrades.MC_CLAY, EnumUpgradeType.MISC);
        }
        
        return false;
    }
    
    @SuppressWarnings("unchecked")
    public static void resurrectDoll(ISoldier<?> solder, ItemStack stack, double posX, double posY, double posZ) {
        if( stack.getItem() instanceof ItemDoll ) {
            ItemDoll doll = (ItemDoll) stack.getItem();
            doll.spawnEntities(solder.getEntity().world, doll.getType(stack), 1, posX, posY + 0.25D, posZ, stack);
            UpgradeClay.decrUses(solder, solder.getUpgradeInstance(Upgrades.MC_CLAY, EnumUpgradeType.MISC));
            stack.shrink(1);
        } else if( ItemStackUtils.isItem(stack, ItemRegistry.DOLL_BRICK_SOLDIER) ) {
            ItemStack teamStack = TeamRegistry.INSTANCE.getNewTeamStack(1, solder.getSoldierTeam());
            if( stack.hasTagCompound() ) {
                Objects.requireNonNull(teamStack.getTagCompound()).merge(Objects.requireNonNull(stack.getTagCompound()));
            }
            ItemRegistry.DOLL_SOLDIER.spawnEntities(solder.getEntity().world, solder.getSoldierTeam(), 1, posX, posY + 0.25D, posZ, teamStack);
            UpgradeGhastTear.decrUses(solder, solder.getUpgradeInstance(Upgrades.MC_GHASTTEAR, EnumUpgradeType.MISC));
            stack.shrink(1);
        }
    }
}
