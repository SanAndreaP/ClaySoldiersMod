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
import de.sanandrew.mods.claysoldiers.registry.mount.EnumGeckoType;
import de.sanandrew.mods.claysoldiers.registry.mount.EnumTurtleType;
import de.sanandrew.mods.claysoldiers.registry.mount.EnumWoolBunnyType;
import de.sanandrew.mods.claysoldiers.util.CsmConfiguration;
import net.minecraft.block.BlockDispenser;

public final class DispenserBehaviorRegistry
{
    public static void initialize() {
        if( CsmConfiguration.BlocksAndItems.Dispenser.enableSoldierDispense )
            BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ItemRegistry.DOLL_SOLDIER, new BehaviorDollDispenseItem<ITeam>());
        if( CsmConfiguration.BlocksAndItems.Dispenser.enableHorseDispense )
            BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ItemRegistry.DOLL_HORSE, new BehaviorDollDispenseItem<EnumClayHorseType>());
        if( CsmConfiguration.BlocksAndItems.Dispenser.enablePegasusDispense )
            BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ItemRegistry.DOLL_PEGASUS, new BehaviorDollDispenseItem<EnumClayHorseType>());
        if( CsmConfiguration.BlocksAndItems.Dispenser.enableTurtleDispense )
            BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ItemRegistry.DOLL_TURTLE, new BehaviorDollDispenseItem<EnumTurtleType>());
        if( CsmConfiguration.BlocksAndItems.Dispenser.enableBunnyDispense )
            BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ItemRegistry.DOLL_BUNNY, new BehaviorDollDispenseItem<EnumWoolBunnyType>());
        if( CsmConfiguration.BlocksAndItems.Dispenser.enableGeckoDispense )
            BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ItemRegistry.DOLL_GECKO, new BehaviorDollDispenseItem<EnumGeckoType>());
    }
}
