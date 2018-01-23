/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry;

import de.sanandrew.mods.claysoldiers.api.soldier.ITeam;
import de.sanandrew.mods.claysoldiers.dispenser.BehaviorDollDispenseItem;
import de.sanandrew.mods.claysoldiers.registry.mount.EnumClayHorseType;
import de.sanandrew.mods.claysoldiers.registry.mount.EnumTurtleType;
import net.minecraft.block.BlockDispenser;

public final class DispenserBehaviorRegistry
{
    public static void initialize() {
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ItemRegistry.DOLL_SOLDIER, new BehaviorDollDispenseItem<ITeam>());
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ItemRegistry.DOLL_HORSE, new BehaviorDollDispenseItem<EnumClayHorseType>());
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ItemRegistry.DOLL_PEGASUS, new BehaviorDollDispenseItem<EnumClayHorseType>());
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ItemRegistry.DOLL_TURTLE, new BehaviorDollDispenseItem<EnumTurtleType>());
    }
}
