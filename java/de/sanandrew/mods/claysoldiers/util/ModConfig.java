package de.sanandrew.mods.claysoldiers.util;

import net.minecraftforge.common.config.Configuration;

/**
 * @author SanAndreas
 * @version 1.0
 */
public final class ModConfig
{
    public static Configuration config;

    public static boolean useOldHurtSound = true;
    public static float soldierBaseHealth = 20.0F;
    public static float soldierBaseDamage = 1.0F;
    public static double statItemRange = 48.0D;

    public static void syncConfig() {
        config.getString("Squirrel", Configuration.CATEGORY_GENERAL, "yes", "A Squirrel!");
        useOldHurtSound = config.getBoolean("Use old hurt sound", Configuration.CATEGORY_GENERAL, useOldHurtSound, "Should soldiers use the old hurt sound?");
        soldierBaseHealth = config.getFloat("Soldier Base Health", Configuration.CATEGORY_GENERAL, soldierBaseHealth, 1.0F, 40.0F, "A soldiers base health");
        soldierBaseDamage = config.getFloat("Soldier Base Damage", Configuration.CATEGORY_GENERAL, soldierBaseDamage, 1.0F, 40.0F, "A soldiers unarmed/base damage");

        if( config.hasChanged() ) {
            config.save();
        }
    }
}
