/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry.mount;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.doll.IDollType;
import de.sanandrew.mods.claysoldiers.registry.ItemRegistry;
import de.sanandrew.mods.claysoldiers.util.CsmConfiguration;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;

import java.util.Arrays;
import java.util.Locale;

public enum EnumClayHorseType
        implements IDollType
{
    DIRT(true, 35.0F, 1.05F, false, false, 0x9C5300, "dirt1", "dirt2", "dirt3", "dirt4"),
    SAND(true, 30.0F, 1.1F, false, false, 0xF9FF80, "sand"),
    GRAVEL(true, 45.0F, 0.95F, false, false, 0xD1BABA, "gravel1", "gravel2"),
    SNOW(true, 40.0F, 1.0F, false, false, 0xFFFFFF, "snow"),
    GRASS(true, 20.0F, 1.2F, false, false, 0x2ABA1A, "grass1", "grass2"),
    LAPIS(true, 35.0F, 1.2F, true, false, 0x4430C2, "lapis"),
    CLAY(true, 35.0F, 1.1F, true, false, 0xA3A3A3, "clay"),
    CARROT(true, 35.0F, 1.2F, true, false, 0xF0A800, "carrot1", "carrot2"),
    SOULSAND(true, 35.0F, 1.15F, false, false, 0x5C3100, "soulsand"),
    CAKE(true, 30.0F, 1.4F, false, false, 0xFFFFFF, "cake", "cake"),

    NIGHTMARE(false, 50.0F, 1.6F, false, true, 0x0, "spec_nightmare1", "spec_nightmare2"),
    UNKNOWN(false, 0.0F, 0.0F, false, false, 0x0);

    public static final EnumClayHorseType[] VALUES = values();

    public float maxHealth;
    public float movementFactor;
    public boolean canBreatheUnderwater;
    public boolean hasFireImmunity;
    public final boolean visible;
    public final int itemColor;
    public final String cstItemSuffix;
    public final ResourceLocation[] textures;

    EnumClayHorseType(boolean visible, float maxHealth, float movementFactor, boolean canBreatheUnderwater, boolean immuneToFire, String cstItemSuffix, int itemColor, String... textures) {
        if (textures == null) {
            textures = new String[0];
        }

        this.visible = visible;
        this.maxHealth = maxHealth;
        this.movementFactor = movementFactor;
        this.canBreatheUnderwater = canBreatheUnderwater;
        this.itemColor = itemColor;
        this.textures = Arrays.stream(textures).map(s -> new ResourceLocation(CsmConstants.ID, String.format("textures/entities/mount/horses/%s.png", s)))
                              .toArray(ResourceLocation[]::new);
        this.cstItemSuffix = cstItemSuffix;
        this.hasFireImmunity = immuneToFire;
    }

    EnumClayHorseType(boolean visible, float maxHealth, float movementFactor, boolean canBreatheUnderwater, boolean immuneToFire, int itemColor, String... textures) {
        this(visible, maxHealth, movementFactor, canBreatheUnderwater, immuneToFire, null, itemColor, textures);
    }

    @Override
    public boolean isVisible() {
        return this.visible;
    }

    @Override
    public boolean isValid() {
        return this != UNKNOWN;
    }

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public int getItemColor() {
        return this.itemColor;
    }

    @Override
    public ItemStack getTypeStack() {
        return ItemRegistry.DOLL_HORSE.getTypeStack(this);
    }

    public static void updateConfiguration(Configuration config) {
        final String category = CsmConfiguration.CAT_ENTITY_VALS + Configuration.CATEGORY_SPLITTER + "Horses";
        config.getCategory(category).setRequiresWorldRestart(true).setComment("This category controls both horses and pegasi!");

        for( EnumClayHorseType type : VALUES ) {
            if( type == UNKNOWN ) {
                continue;
            }
            String typeNameL = type.getName().toLowerCase(Locale.ENGLISH);

            type.maxHealth = config.getFloat(typeNameL + "HorseMaxHealth", category, type.maxHealth, 0.0F, 1024.0F,
                                             "Maximum health of a " + typeNameL + " horse");
            type.movementFactor = config.getFloat(typeNameL + "HorseMovementFactor", category, type.movementFactor, 0.0F, 1024.0F,
                                                  "Movement factor of a " + typeNameL + " horse");
            type.canBreatheUnderwater = config.getBoolean(typeNameL + "HorseUnderwaterBreath", category, type.canBreatheUnderwater,
                                                          "Wether or not a " + typeNameL + " horse can breathe underwater");
            type.hasFireImmunity = config.getBoolean(typeNameL + "HorseHasFireImmunity", category, type.hasFireImmunity,
                                                          "Wether or not a " + typeNameL + " horse is immune to fire");
        }
    }
}
