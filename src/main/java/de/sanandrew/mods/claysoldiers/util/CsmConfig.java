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
import de.sanandrew.mods.sanlib.lib.Tuple;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Mod.EventBusSubscriber(modid = CsmConstants.ID)
public class CsmConfig
{
    private static final String CONFIG_VER = "2.0";

    public static Configuration config;

    @Category(value = Entities.CAT_NAME, comment = "Entity configuration", reqWorldRestart = true)
    public static final class Entities
    {
        public static final String CAT_NAME = "entity values";

        public static final String SUBCAT_ENTITY_SOLDIER = CAT_NAME + Configuration.CATEGORY_SPLITTER + "soldiers";
        @Value(category = SUBCAT_ENTITY_SOLDIER, comment = "Maximum health of a soldier.",
               range = @Range(minD = 0.0D, maxD = 1024.0D))
        public static float soldierMaxHealth = 20.0F;
        @Value(category = SUBCAT_ENTITY_SOLDIER, comment = "Base attack damage dealt by a soldier.",
               range = @Range(minD = 0.0D, maxD = 2048.0D))
        public static float soldierAttackDamage = 1.0F;
        @Value(category = SUBCAT_ENTITY_SOLDIER, comment = "Movement speed of a soldier.",
               range = @Range(minD = 0.0D, maxD = 256.0D))
        public static float soldierMovementSpeed = 0.3F;
        @Value(category = SUBCAT_ENTITY_SOLDIER, comment ="The range a soldier searches in for targets to follow.",
               range = @Range(minD = 0.0D, maxD = 2048.0D))
        public static float soldierFollowRange = 16.0F;

        public static void init() {
            loadCategory(EnumClayHorseType.class);
            loadCategory(EnumTurtleType.class);
            loadCategory(EnumWoolBunnyType.class);
            loadCategory(EnumGeckoType.class);
        }
    }

    @Category(value = BlocksAndItems.CAT_NAME, comment = "Item and block configuration")
    public static final class BlocksAndItems
    {
        public static final String CAT_NAME = "blocks and items";

        @Category(value = Dolls.SUBCAT_DOLLS, comment = "Doll items configuration.", reqMcRestart = true)
        public static final class Dolls
        {
            public static final String SUBCAT_DOLLS = CAT_NAME + Configuration.CATEGORY_SPLITTER + "dolls";

            @Value(category = SUBCAT_DOLLS, comment = "Maximum stack size of a brick doll.", range = @Range(minI = 1, maxI = 64))
            public static int brickDollStackSize = 16;
            @Value(category = SUBCAT_DOLLS, comment = "Maximum stack size of a soldier doll.", range = @Range(minI = 1, maxI = 64))
            public static int soldierDollStackSize = 16;
            @Value(category = SUBCAT_DOLLS, comment = "Maximum stack size of a horse doll.", range = @Range(minI = 1, maxI = 64))
            public static int horseDollStackSize = 16;
            @Value(category = SUBCAT_DOLLS, comment = "Maximum stack size of a pegasus doll.", range = @Range(minI = 1, maxI = 64))
            public static int pegasusDollStackSize = 16;
            @Value(category = SUBCAT_DOLLS, comment = "Maximum stack size of a turtle doll.", range = @Range(minI = 1, maxI = 64))
            public static int turtleDollStackSize = 16;
            @Value(category = SUBCAT_DOLLS, comment = "Maximum stack size of a bunny doll.", range = @Range(minI = 1, maxI = 64))
            public static int bunnyDollStackSize = 16;
            @Value(category = SUBCAT_DOLLS, comment = "Maximum stack size of a gecko doll.", range = @Range(minI = 1, maxI = 64))
            public static int geckoDollStackSize = 16;
        }

        @Category(value = Dispenser.SUBCAT_DISPENSER, comment = "Dispenser behavior configuration", reqMcRestart = true)
        public static final class Dispenser
        {
            public static final String SUBCAT_DISPENSER = CAT_NAME + Configuration.CATEGORY_SPLITTER + "dispenser behavior";

            @Value(category = SUBCAT_DISPENSER, comment = "Allow dispenser to spawn soldiers via dolls.")
            public static boolean enableSoldierDispense = true;
            @Value(category = SUBCAT_DISPENSER, comment = "Allow dispenser to spawn horses via dolls.")
            public static boolean enableHorseDispense = true;
            @Value(category = SUBCAT_DISPENSER, comment = "Allow dispenser to spawn pegasi via dolls.")
            public static boolean enablePegasusDispense = true;
            @Value(category = SUBCAT_DISPENSER, comment = "Allow dispenser to spawn turtles via dolls.")
            public static boolean enableTurtleDispense = true;
            @Value(category = SUBCAT_DISPENSER, comment = "Allow dispenser to spawn bunnies via dolls.")
            public static boolean enableBunnyDispense = true;
            @Value(category = SUBCAT_DISPENSER, comment = "Allow dispenser to spawn geckos via dolls.")
            public static boolean enableGeckoDispense = true;
            @Value(category = SUBCAT_DISPENSER, comment = "Allow dispenser to use the disruptor.")
            public static boolean enableDisruptorDispense = true;
        }

        @Category(value = Disruptor.SUBCAT_DISRUPTOR, comment = "Disruptor item configuration")
        public static final class Disruptor
        {
            public static final String SUBCAT_DISRUPTOR = CAT_NAME + Configuration.CATEGORY_SPLITTER + "disruptor";
            @Value(category = SUBCAT_DISRUPTOR, comment = "Allow disruptor to break clay blocks.")
            public static boolean enableClayBlockDisruptMode = true;
            @Value(category = SUBCAT_DISRUPTOR, comment = "Allow disruptor to be automateable (through dispenser or fake player) regarding clay creatures.")
            public static boolean enableAutomatedDollDisrupt = true;
            @Value(category = SUBCAT_DISRUPTOR, comment = "Allow disruptor to be automateable (through dispenser or fake player) regarding clay block break.")
            public static boolean enableAutomatedClayBlockDisrupt = true;

            public static void init() {
                loadCategory(ItemDisruptor.DisruptorType.class);
            }
        }
    }

    @Category(value = Recipes.CAT_NAME, comment = "Custom coded recipe configuration")
    public static final class Recipes
    {
        public static final String CAT_NAME = "recipes";

        @Value(category = CAT_NAME, comment = "Allow recipe registration of dyed clay soldiers.")
        public static boolean enableDyedSoldierRecipe = true;
        @Value(category = CAT_NAME, comment = "Allow recipe registration of glass clay soldiers.")
        public static boolean enableDyedGlassSoldierRecipe = true;
        @Value(category = CAT_NAME, comment = "Allow recipe registration of resource clay soldiers. (Melon, Coal, Redstone, etc.)")
        public static boolean enableResourceSoldierRecipe = true;
        @Value(category = CAT_NAME, comment = "Allow recipe registration of washing clay soldiers. (Bucket of water + teamed soldier doll -> clay soldier doll)")
        public static boolean enableSoldierWashRecipe = true;
        @Value(category = CAT_NAME, comment = "Allow cauldron to wash clay soldiers. (teamed soldier doll -> clay soldier doll)")
        public static boolean enableCauldronSoldierWash = true;
        @Value(category = CAT_NAME, comment = "Allow recipe registration of reverting brick soldier dolls to regular soldier dolls. (ghast tear + brick soldier doll (+ opt. teamed soldier doll) -> (teamed) soldier doll)")
        public static boolean enableBrickSoldierReverseRecipe = true;
    }

    @Category(value = Lexicon.CAT_NAME, comment = "Clay Lexicon configuration")
    public static final class Lexicon
    {
        public static final String CAT_NAME = "lexicon";

        @Value(category = CAT_NAME, comment = "Force unicode font to be used in clay lexicon.")
        public static boolean lexiconForceUnicode = false;
    }

    // region CONFIG HANDLER

    private static final Map<String, Tuple> DEFAULTS = new HashMap<>();

    public static void initialize(FMLPreInitializationEvent event) {
        File cfgFile = event.getSuggestedConfigurationFile();
        config = new Configuration(cfgFile, CONFIG_VER, true);
        if( Integer.parseInt(config.getLoadedConfigVersion().split("\\.")[0]) < Integer.parseInt(CONFIG_VER.split("\\.")[0]) ) {
            try {
                FileUtils.copyFile(cfgFile, new File(cfgFile.getAbsoluteFile() + ".old"));
                config.getCategoryNames().forEach(cat -> config.removeCategory(config.getCategory(cat)));
                CsmConstants.LOG.log(Level.WARN, String.format("Clay Soldiers config file is too outdated! Config will be overwritten - the old config file can be found at %s.old", cfgFile.getAbsoluteFile()));
            } catch( IOException ex ) {
                CsmConstants.LOG.log(Level.ERROR, "Clay Soldiers config file is too outdated but cannot be updated! This will cause errors! Please copy the old config somewhere and remove it from the config folder!", ex);
            }
        }

        synchronize();
    }

    public static void synchronize() {
        loadCategories(CsmConfig.class);

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

    private static void loadCategories(Class<?> base) {
        for( Class<?> c : base.getDeclaredClasses() ) {
            loadCategory(c);
        }
    }

    public static void loadCategory(Class<?> c) {
        Category cat = c.getAnnotation(Category.class);
        if( cat != null ) {
            ConfigCategory cCat = config.getCategory(cat.value());
            if( !cat.inherit() ) {
                cCat.setComment(cat.comment());
                cCat.setRequiresMcRestart(cat.reqMcRestart());
                cCat.setRequiresWorldRestart(cat.reqWorldRestart());
            }

            if( c.isEnum() ) {
                for( Field f : c.getDeclaredFields() ) {
                    if( f.isEnumConstant() && f.getAnnotation(EnumExclude.class) == null ) {
                        try {
                            loadValues(c, f.get(null), f.getName().toLowerCase(Locale.ROOT));
                        } catch( IllegalAccessException ex ) {
                            CsmConstants.LOG.log(Level.ERROR, String.format("Could not load config value for enum value %s in enum %s", f.getName(), f.getDeclaringClass().getName()), ex);
                        }
                    }
                }
            } else {
                loadCategories(c);
                loadValues(c);

                try {
                    Method init = c.getDeclaredMethod("init");
                    init.invoke(null);
                } catch( NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored ) { }
            }
        }
    }

    private static void loadValues(Class<?> c) {
        loadValues(c, null, null);
    }

    private static void loadValues(Class<?> c, Object inst, String instName) {
        for( Field f : c.getDeclaredFields() ) {
            Value val = f.getAnnotation(Value.class);
            try {
                if( val != null ) {
                    Class<?> cv = f.getType();
                    String name = val.value().isEmpty() ? f.getName() : String.format(val.value(), instName);
                    String comment = String.format(val.comment(), instName);
                    String category = val.category();

                    if( cv == long.class || cv == int.class || cv == short.class || cv == byte.class ) {
                        if( !DEFAULTS.containsKey(category + name) ) {
                            DEFAULTS.put(category + name, new Tuple(cv == long.class ? (int) f.getLong(inst) : f.getInt(inst)));
                        }
                        Property p = config.get(category, name, DEFAULTS.get(category + name).<Integer>getValue(0), comment, val.range().minI(), val.range().maxI());
                        p.setRequiresMcRestart(val.reqMcRestart());
                        p.setRequiresWorldRestart(val.reqWorldRestart());

                        if( cv == long.class || cv == int.class ) {
                            f.setInt(inst, p.getInt());
                        } else if( cv == short.class ) {
                            f.setShort(inst, (short) p.getInt());
                        } else if( cv == byte.class ) {
                            f.setByte(inst, (byte) p.getInt());
                        }
                    } else if( cv == double.class || cv == float.class ) {
                        if( !DEFAULTS.containsKey(category + name) ) {
                            DEFAULTS.put(category + name, new Tuple(cv == float.class ? Double.valueOf(Float.valueOf(f.getFloat(inst)).toString()) : f.getDouble(inst)));
                        }
                        Property p = config.get(category, name, DEFAULTS.get(category + name).<Double>getValue(0), comment, val.range().minD(), val.range().maxD());
                        p.setRequiresMcRestart(val.reqMcRestart());
                        p.setRequiresWorldRestart(val.reqWorldRestart());

                        if( cv == float.class ) {
                            f.setFloat(inst, (float) p.getDouble());
                        } else {
                            f.setDouble(inst, p.getDouble());
                        }
                    } else if( cv == boolean.class ) {
                        if( !DEFAULTS.containsKey(category + name) ) {
                            DEFAULTS.put(category + name, new Tuple(f.getBoolean(inst)));
                        }
                        Property p = config.get(category, name, DEFAULTS.get(category + name).<Boolean>getValue(0), comment);
                        p.setRequiresMcRestart(val.reqMcRestart());
                        p.setRequiresWorldRestart(val.reqWorldRestart());
                        f.setBoolean(inst, p.getBoolean());
                    } else if( cv == String.class ) {
                        if( !DEFAULTS.containsKey(category + name) ) {
                            DEFAULTS.put(category + name, new Tuple(f.get(inst).toString()));
                        }
                        Property p = config.get(category, name, DEFAULTS.get(category + name).<String>getValue(0), comment);
                        p.setRequiresMcRestart(val.reqMcRestart());
                        p.setRequiresWorldRestart(val.reqWorldRestart());
                        f.set(inst, p.getString());
                    } else if( cv == int[].class ) {
                        if( !DEFAULTS.containsKey(category + name) ) {
                            DEFAULTS.put(category + name, new Tuple((Object) f.get(inst)));
                        }
                        Property p = config.get(category, name, DEFAULTS.get(category + name).<int[]>getValue(0), comment, val.range().minI(), val.range().maxI(),
                                                val.range().listFixed(), val.range().maxListLength());
                        p.setRequiresMcRestart(val.reqMcRestart());
                        p.setRequiresWorldRestart(val.reqWorldRestart());
                        f.set(inst, p.getIntList());
                    } else if( cv == double[].class ) {
                        if( !DEFAULTS.containsKey(category + name) ) {
                            DEFAULTS.put(category + name, new Tuple((Object) f.get(inst)));
                        }
                        Property p = config.get(category, name, DEFAULTS.get(category + name).getValue(0), comment, val.range().minD(), val.range().maxD(),
                                                val.range().listFixed(), val.range().maxListLength());
                        p.setRequiresMcRestart(val.reqMcRestart());
                        p.setRequiresWorldRestart(val.reqWorldRestart());
                        f.set(inst, p.getDoubleList());
                    } else if( cv == boolean[].class ) {
                        if( !DEFAULTS.containsKey(category + name) ) {
                            DEFAULTS.put(category + name, new Tuple((Object) f.get(inst)));
                        }
                        Property p = config.get(category, name, DEFAULTS.get(category + name).getValue(0), comment, val.range().listFixed(), val.range().maxListLength());
                        p.setRequiresMcRestart(val.reqMcRestart());
                        p.setRequiresWorldRestart(val.reqWorldRestart());
                        f.set(inst, p.getBooleanList());
                    } else if( cv == String[].class ) {
                        if( !DEFAULTS.containsKey(category + name) ) {
                            DEFAULTS.put(category + name, new Tuple((Object) f.get(inst)));
                        }
                        Pattern validationPattern = val.range().validationPattern();
                        @SuppressWarnings("MagicConstant")
                        Property p = config.get(category, name, DEFAULTS.get(category + name).getValue(0), comment, val.range().listFixed(), val.range().maxListLength(),
                                                validationPattern.regex().isEmpty() ? null : java.util.regex.Pattern.compile(validationPattern.regex(),
                                                                                                                             validationPattern.flags()));
                        p.setRequiresMcRestart(val.reqMcRestart());
                        p.setRequiresWorldRestart(val.reqWorldRestart());
                        f.set(inst, p.getStringList());
                    }
                }
            } catch( IllegalAccessException | IllegalArgumentException ex ) {
                CsmConstants.LOG.log(Level.ERROR, String.format("Could not load config value for field %s in class %s", f.getName(), f.getDeclaringClass().getName()), ex);
            }
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Category
    {
        String value();
        String comment() default "";
        boolean reqMcRestart() default false;
        boolean reqWorldRestart() default false;
        boolean inherit() default false;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface Value
    {
        String value() default "";
        String category();
        String comment() default "";
        Range range() default @Range;
        boolean reqMcRestart() default false;
        boolean reqWorldRestart() default false;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.ANNOTATION_TYPE)
    public @interface Range
    {
        int minI() default Integer.MIN_VALUE;
        int maxI() default Integer.MAX_VALUE;
        double minD() default -Double.MAX_VALUE;
        double maxD() default Double.MAX_VALUE;
        boolean listFixed() default false;
        int maxListLength() default -1;
        Pattern validationPattern() default @Pattern;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.ANNOTATION_TYPE)
    public @interface Pattern {
        String regex() default "";
        int flags() default 0;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface EnumExclude {}

    // endregion
}
