/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.item.ItemDisruptor;
import de.sanandrew.mods.claysoldiers.registry.mount.EnumClayHorseType;
import de.sanandrew.mods.claysoldiers.registry.mount.EnumGeckoType;
import de.sanandrew.mods.claysoldiers.registry.mount.EnumTurtleType;
import de.sanandrew.mods.claysoldiers.registry.mount.EnumWoolBunnyType;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = CsmConstants.ID)
public class CsmConfiguration
{
    private static final String CONFIG_VER = "1.0";

    private static Configuration config;

    public static final String CAT_ENTITY_VALS = "entity values";
    public static final String SUBCAT_ENTITY_SOLDIER = CAT_ENTITY_VALS + Configuration.CATEGORY_SPLITTER + "Soldiers";
    public static float soldierMaxHealth = 20.0F;
    public static float soldierAttackDamage = 1.0F;
    public static float soldierMovementSpeed = 0.3F;
    public static float soldierFollowRange = 16.0F;

    public static final String CAT_BLOCKSITEMS = "blocks and items";
    public static final String SUBCAT_DOLLS = CAT_BLOCKSITEMS + Configuration.CATEGORY_SPLITTER + "Dolls";
    public static int brickDollStackSize = 16;
    public static int soldierDollStackSize = 16;
    public static int horseDollStackSize = 16;
    public static int pegasusDollStackSize = 16;
    public static int turtleDollStackSize = 16;
    public static int bunnyDollStackSize = 16;
    public static int geckoDollStackSize = 16;

    public static final String CAT_RECIPES = "recipes";
    public static boolean enableDyedSoldierRecipe = true;
    public static boolean enableSoldierWashRecipe = true;
    public static boolean enableDyedGlassSoldierRecipe = true;
    public static boolean enableBrickSoldierReverseRecipe = true;
    public static boolean enableResourceSoldierRecipe = true;
    public static boolean enableCauldronSoldierWash = true;

    public static final String CAT_LEXICON = "lexicon";
    public static boolean lexiconForceUnicode = false;

    public static void initialize(FMLPreInitializationEvent event) {
        config = new Configuration(event.getSuggestedConfigurationFile(), CONFIG_VER, true);
        synchronize();
    }

    public static void synchronize() {
        config.getCategory(CAT_ENTITY_VALS).setComment("Config values regarding entities.");
        soldierMaxHealth = config.getFloat("soldierMaxHealth", SUBCAT_ENTITY_SOLDIER, soldierMaxHealth, 0.0F, 1024.0F, "Maximum health of a soldier");
        soldierMovementSpeed = config.getFloat("soldierMovementSpeed", SUBCAT_ENTITY_SOLDIER, soldierMovementSpeed, 0.0F, 1024.0F, "Movement speed of a soldier");
        soldierAttackDamage = config.getFloat("soldierAttackDamage", SUBCAT_ENTITY_SOLDIER, soldierAttackDamage, 0.0F, 2048.0F, "Base attack damage dealt by a soldier");
        soldierFollowRange = config.getFloat("soldierFollowRange", SUBCAT_ENTITY_SOLDIER, soldierFollowRange, 0.0F, 2048.0F, "The range a soldier searches in for targets to follow");
        EnumClayHorseType.updateConfiguration(config);
        EnumTurtleType.updateConfiguration(config);
        EnumWoolBunnyType.updateConfiguration(config);
        EnumGeckoType.updateConfiguration(config);

        config.getCategory(CAT_BLOCKSITEMS).setComment("Config values regarding blocks and items.");
        config.getCategory(SUBCAT_DOLLS).setRequiresMcRestart(true);
        brickDollStackSize = config.getInt("brickDollStackSize", SUBCAT_DOLLS, brickDollStackSize, 1, 64, "Maximum stack size of a brick doll");
        soldierDollStackSize = config.getInt("soldierDollStackSize", SUBCAT_DOLLS, soldierDollStackSize, 1, 64, "Maximum stack size of a soldier doll");
        horseDollStackSize = config.getInt("horseDollStackSize", SUBCAT_DOLLS, horseDollStackSize, 1, 64, "Maximum stack size of a horse doll");
        pegasusDollStackSize = config.getInt("pegasusDollStackSize", SUBCAT_DOLLS, pegasusDollStackSize, 1, 64, "Maximum stack size of a pegasus doll");
        turtleDollStackSize = config.getInt("turtleDollStackSize", SUBCAT_DOLLS, turtleDollStackSize, 1, 64, "Maximum stack size of a turtle doll");
        bunnyDollStackSize = config.getInt("bunnyDollStackSize", SUBCAT_DOLLS, bunnyDollStackSize, 1, 64, "Maximum stack size of a bunny doll");
        geckoDollStackSize = config.getInt("geckoDollStackSize", SUBCAT_DOLLS, geckoDollStackSize, 1, 64, "Maximum stack size of a gecko doll");
        ItemDisruptor.DisruptorType.updateConfiguration(config);

        config.getCategory(CAT_RECIPES).setRequiresMcRestart(true).setComment("Config values regarding custom coded recipes.");
        enableDyedSoldierRecipe = config.getBoolean("enableDyedSoldierRecipe", CAT_RECIPES, enableDyedSoldierRecipe, "Wether or not to allow dyed clay soldiers to be craftable");
        enableDyedGlassSoldierRecipe = config.getBoolean("enableDyedGlassSoldierRecipe", CAT_RECIPES, enableDyedGlassSoldierRecipe, "Wether or not to allow dyed glass soldiers to be craftable");
        enableBrickSoldierReverseRecipe = config.getBoolean("enableBrickSoldierReverseRecipe", CAT_RECIPES, enableBrickSoldierReverseRecipe, "Wether or not to allow brick soldiers to be reversible via crafting");
        enableResourceSoldierRecipe = config.getBoolean("enableResourceSoldierRecipe", CAT_RECIPES, enableResourceSoldierRecipe, "Wether or not to allow resource soldiers (redstone, melon, etc.) to be craftable");
        enableSoldierWashRecipe = config.getBoolean("enableSoldierWashRecipe", CAT_RECIPES, enableSoldierWashRecipe, "Wether or not to allow soldier dolls to be reverted into regular clay soldiers via crafting");
        enableCauldronSoldierWash = config.getBoolean("enableCauldronSoldierWash", CAT_RECIPES, enableCauldronSoldierWash, "Wether or not to allow soldier dolls to be reverted into regular clay soldiers via right-click on a cauldron");

        config.getCategory(CAT_LEXICON).setComment("Config values for the Lexica Lutum. These are only used on the client side. Servers will ignore these.");
        lexiconForceUnicode = config.getBoolean("forceUnicodeFont", CAT_LEXICON, lexiconForceUnicode, "Wether or not to force Unicode rendering for the lexicon");

        if( config.hasChanged() ) {
            config.save();
        }
    }

    public static ConfigCategory getCategory(String catName) {
        return config.getCategory(catName);
    }

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if( event.getModID().equals(CsmConstants.ID) ) {
            synchronize();
        }
    }
}
