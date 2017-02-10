/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util;

import net.minecraftforge.common.config.Configuration;

public final class ModConfig
{
    public static Configuration config;

    public static boolean useOldHurtSound = true;
    public static float soldierBaseHealth = 20.0F;
    public static float soldierBaseDamage = 1.0F;
    public static double statItemRange = 48.0D;
    public static int clayHutSpawnChance = 256;
    public static int clayHutZombieChance = 32;

    public static void syncConfig() {
        config.getString("Squirrel", Configuration.CATEGORY_GENERAL, "yes", "A Squirrel!");
        useOldHurtSound = config.getBoolean("Use old hurt sound", Configuration.CATEGORY_GENERAL, useOldHurtSound,
                                            "Should soldiers use the old hurt sound?");
        soldierBaseHealth = config.getFloat("Soldier Base Health", Configuration.CATEGORY_GENERAL, soldierBaseHealth, 1.0F, 40.0F,
                                            "A soldiers base health.");
        soldierBaseDamage = config.getFloat("Soldier Base Damage", Configuration.CATEGORY_GENERAL, soldierBaseDamage, 1.0F, 40.0F,
                                            "A soldiers unarmed/base damage.");
        clayHutSpawnChance = config.getInt("Clay-Hut Spawn Chance", Configuration.CATEGORY_GENERAL, clayHutSpawnChance, 0, Integer.MAX_VALUE,
                                           "This alue determines how often the clayHut will spawn. The higher the value, the rarer the spawn. " +
                                               "Set it to 0 to disable the spawn.");
        clayHutZombieChance = config.getInt("Clay-Hut Spawn Chance", Configuration.CATEGORY_GENERAL, clayHutZombieChance, 0, Integer.MAX_VALUE,
                                            "This alue determines how often the clayHut be infested with zombies. The higher the value, the rarer the chance. " +
                                                "Set it to 0 to disable zombiefication.");

        if( config.hasChanged() ) {
            config.save();
        }
    }
}
