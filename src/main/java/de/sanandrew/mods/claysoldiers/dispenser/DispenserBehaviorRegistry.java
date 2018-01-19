/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.dispenser;

import de.sanandrew.mods.claysoldiers.api.soldier.ITeam;
import de.sanandrew.mods.claysoldiers.item.ItemRegistry;
import de.sanandrew.mods.claysoldiers.registry.mount.EnumClayHorseType;
import net.minecraft.block.BlockDispenser;

public final class DispenserBehaviorRegistry
{
    public static void initialize() {
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ItemRegistry.DOLL_SOLDIER, new BehaviorDollDispenseItem<ITeam>());
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ItemRegistry.DOLL_HORSE, new BehaviorDollDispenseItem<EnumClayHorseType>());
    }
}
