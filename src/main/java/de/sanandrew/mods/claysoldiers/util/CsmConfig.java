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
import de.sanandrew.mods.sanlib.lib.util.config.Category;
import de.sanandrew.mods.sanlib.lib.util.config.ConfigUtils;
import de.sanandrew.mods.sanlib.lib.util.config.Range;
import de.sanandrew.mods.sanlib.lib.util.config.Value;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

@Mod.EventBusSubscriber(modid = CsmConstants.ID)
public class CsmConfig
{
    private static final String CONFIG_VER = "3.0";

    public static Configuration config;

    @Category(value = Entities.CAT_NAME, comment = "Entity configuration", reqWorldRestart = true)
    public static final class Entities
    {
        public static final String CAT_NAME = "entity values";

        @Category(value = Soldiers.SUBCAT_ENTITY_SOLDIER, comment = "Soldier entity configuration")
        public static final class Soldiers
        {
            public static final String SUBCAT_ENTITY_SOLDIER = "soldiers";

            @Value(comment = "Maximum health of a soldier.", range = @Range(minD = 0.0D, maxD = 1024.0D))
            public static float maxHealth = 20.0F;
            @Value(comment = "Base attack damage dealt by a soldier.", range = @Range(minD = 0.0D, maxD = 2048.0D))
            public static float attackDamage = 1.0F;
            @Value(comment = "Movement speed of a soldier.", range = @Range(minD = 0.0D, maxD = 256.0D))
            public static float movementSpeed = 0.3F;
            @Value(comment ="The range a soldier searches in for targets to follow.", range = @Range(minD = 0.0D, maxD = 2048.0D))
            public static float followRange = 16.0F;
        }

        public static void init() {
            ConfigUtils.loadCategory(config, EnumClayHorseType.class, CAT_NAME);
            ConfigUtils.loadCategory(config, EnumTurtleType.class, CAT_NAME);
            ConfigUtils.loadCategory(config, EnumWoolBunnyType.class, CAT_NAME);
            ConfigUtils.loadCategory(config, EnumGeckoType.class, CAT_NAME);
        }
    }

    @Category(value = BlocksAndItems.CAT_NAME, comment = "Item and block configuration")
    public static final class BlocksAndItems
    {
        public static final String CAT_NAME = "blocks and items";

        @Category(value = Dolls.SUBCAT_DOLLS, comment = "Doll items configuration.", reqMcRestart = true)
        public static final class Dolls
        {
            public static final String SUBCAT_DOLLS = "dolls";

            @Value(comment = "Maximum stack size of a brick doll.", range = @Range(minI = 1, maxI = 64))
            public static int brickDollStackSize = 16;
            @Value(comment = "Maximum stack size of a soldier doll.", range = @Range(minI = 1, maxI = 64))
            public static int soldierDollStackSize = 16;
            @Value(comment = "Maximum stack size of a horse doll.", range = @Range(minI = 1, maxI = 64))
            public static int horseDollStackSize = 16;
            @Value(comment = "Maximum stack size of a pegasus doll.", range = @Range(minI = 1, maxI = 64))
            public static int pegasusDollStackSize = 16;
            @Value(comment = "Maximum stack size of a turtle doll.", range = @Range(minI = 1, maxI = 64))
            public static int turtleDollStackSize = 16;
            @Value(comment = "Maximum stack size of a bunny doll.", range = @Range(minI = 1, maxI = 64))
            public static int bunnyDollStackSize = 16;
            @Value(comment = "Maximum stack size of a gecko doll.", range = @Range(minI = 1, maxI = 64))
            public static int geckoDollStackSize = 16;
        }

        @Category(value = Dispenser.SUBCAT_DISPENSER, comment = "Dispenser behavior configuration", reqMcRestart = true)
        public static final class Dispenser
        {
            public static final String SUBCAT_DISPENSER = "dispenser behavior";

            @Value(comment = "Allow dispenser to spawn soldiers via dolls.")
            public static boolean enableSoldierDispense = true;
            @Value(comment = "Allow dispenser to spawn horses via dolls.")
            public static boolean enableHorseDispense = true;
            @Value(comment = "Allow dispenser to spawn pegasi via dolls.")
            public static boolean enablePegasusDispense = true;
            @Value(comment = "Allow dispenser to spawn turtles via dolls.")
            public static boolean enableTurtleDispense = true;
            @Value(comment = "Allow dispenser to spawn bunnies via dolls.")
            public static boolean enableBunnyDispense = true;
            @Value(comment = "Allow dispenser to spawn geckos via dolls.")
            public static boolean enableGeckoDispense = true;
            @Value(comment = "Allow dispenser to use the disruptor.")
            public static boolean enableDisruptorDispense = true;
        }

        @Category(value = Disruptor.SUBCAT_DISRUPTOR, comment = "Disruptor item configuration")
        public static final class Disruptor
        {
            public static final String SUBCAT_DISRUPTOR = "disruptor";
            @Value(comment = "Allow disruptor to break clay blocks.")
            public static boolean enableClayBlockDisruptMode = true;
            @Value(comment = "Allow disruptor to be automateable (through dispenser or fake player) regarding clay creatures.")
            public static boolean enableAutomatedDollDisrupt = true;
            @Value(comment = "Allow disruptor to be automateable (through dispenser or fake player) regarding clay block break.")
            public static boolean enableAutomatedClayBlockDisrupt = true;

            public static void init() {
                ConfigUtils.loadCategory(config, ItemDisruptor.DisruptorType.class, CAT_NAME);
            }
        }
    }

    @Category(value = Recipes.CAT_NAME, comment = "Custom coded recipe configuration")
    public static final class Recipes
    {
        public static final String CAT_NAME = "recipes";

        @Value(comment = "Allow recipe registration of dyed clay soldiers.")
        public static boolean enableDyedSoldierRecipe = true;
        @Value(comment = "Allow recipe registration of glass clay soldiers.")
        public static boolean enableDyedGlassSoldierRecipe = true;
        @Value(comment = "Allow recipe registration of resource clay soldiers. (Melon, Coal, Redstone, etc.)")
        public static boolean enableResourceSoldierRecipe = true;
        @Value(comment = "Allow recipe registration of washing clay soldiers. (Bucket of water + teamed soldier doll -> clay soldier doll)")
        public static boolean enableSoldierWashRecipe = true;
        @Value(comment = "Allow cauldron to wash clay soldiers. (teamed soldier doll -> clay soldier doll)")
        public static boolean enableCauldronSoldierWash = true;
        @Value(comment = "Allow recipe registration of reverting brick soldier dolls to regular soldier dolls. (ghast tear + brick soldier doll (+ opt. teamed soldier doll) -> (teamed) soldier doll)")
        public static boolean enableBrickSoldierReverseRecipe = true;
    }

    @Category(value = Lexicon.CAT_NAME, comment = "Clay Lexicon configuration")
    public static final class Lexicon
    {
        public static final String CAT_NAME = "lexicon";

        @Value(comment = "Force unicode font to be used in clay lexicon.")
        public static boolean lexiconForceUnicode = false;
    }

    public static void initialize(FMLPreInitializationEvent event) {
        File cfgFile = event.getSuggestedConfigurationFile();

        config = ConfigUtils.loadConfigFile(cfgFile, CONFIG_VER, CsmConstants.NAME);

        synchronize();
    }

    public static void synchronize() {
        ConfigUtils.loadCategories(config, CsmConfig.class);

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
