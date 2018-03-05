/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.registry.mount.EnumClayHorseType;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = CsmConstants.ID)
public class CsmConfiguration
{
    public static final String CAT_ENTITY_CFG = "entity values";
    public static final String SUBCAT_ENTITY_SOLDIER = CAT_ENTITY_CFG + Configuration.CATEGORY_SPLITTER + "soldiers";

    private static Configuration config;

    public static float soldierMaxHealth = 20.0F;
    public static float soldierAttackDamage = 1.0F;
    public static float soldierMovementSpeed = 0.3F;

    public static void initialize(FMLPreInitializationEvent event) {
        config = new Configuration(event.getSuggestedConfigurationFile());
        synchronize();
    }

    public static void synchronize() {
        config.getCategory(CAT_ENTITY_CFG).setComment("Configure values regarding entities");

        soldierMaxHealth = config.getFloat("soldierMaxHealth", SUBCAT_ENTITY_SOLDIER, soldierMaxHealth, 0.0F, 1024.0F, "Maximum health of a soldier");
        soldierMovementSpeed = config.getFloat("soldierMovementSpeed", SUBCAT_ENTITY_SOLDIER, soldierMovementSpeed, 0.0F, 1024.0F, "Movement speed of a soldier");
        soldierAttackDamage = config.getFloat("soldierAttackDamage", SUBCAT_ENTITY_SOLDIER, soldierAttackDamage, 0.0F, 2048.0F, "Attack damage dealt by a soldier");

        EnumClayHorseType.updateConfiguration(config);

        if( config.hasChanged() ) {
            config.save();
        }
    }

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if( event.getModID().equals(CsmConstants.ID) ) {
            synchronize();
        }
    }
}
