/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry.mount;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.doll.IDollType;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;

public enum EnumClayHorseType
        implements IDollType
{
    DIRT(true, 35.0F, 0.6F, false, 0x9C5300, "dirt1", "dirt2", "dirt3", "dirt4"),
    SAND(true, 30.0F, 0.7F, false, 0xF9FF80, "sand"),
    GRAVEL(true, 45.0F, 0.4F, false, 0xD1BABA, "gravel1", "gravel2"),
    SNOW(true, 40.0F, 0.5F, false, 0xFFFFFF, "snow"),
    GRASS(true, 20.0F, 0.9F, false, 0x2ABA1A, "grass1", "grass2"),
    LAPIS(true, 35.0F, 0.9F, true, 0x4430C2, "lapis"),
    CLAY(true, 35.0F, 0.6F, true, 0xA3A3A3, "clay"),
    CARROT(true, 35.0F, 0.9F, true, 0xF0A800, "carrot1", "carrot2"),
    SOULSAND(true, 35.0F, 0.8F, false, 0x5C3100, "soulsand"),
    CAKE(true, 30.0F, 1.1F, false, "cake", "cake"),

    NIGHTMARE(false, 50.0F, 1.2F, false, 0x0, "spec_nightmare1", "spec_nightmare2"),
    UNKNOWN(false, 0.0F, 0.0F, false, 0x0);

    public static final EnumClayHorseType[] VALUES = values();

    public final float maxHealth;
    public final float movementSpeed;
    public final boolean canBreatheUnderwater;
    public final boolean visible;
    public final int itemColor;
    public final String cstItemSuffix;
    public final ResourceLocation[] textures;

    EnumClayHorseType(boolean visible, float maxHealth, float movementSpeed, boolean canBreatheUnderwater, String cstItemSuffix, int itemColor, String... textures) {
        if (textures == null) {
            textures = new String[0];
        }

        this.visible = visible;
        this.maxHealth = maxHealth;
        this.movementSpeed = movementSpeed;
        this.canBreatheUnderwater = canBreatheUnderwater;
        this.itemColor = itemColor;
        this.textures = Arrays.stream(textures).map(s -> new ResourceLocation(CsmConstants.ID, String.format("textures/entities/mount/horses/%s.png", s)))
                              .toArray(ResourceLocation[]::new);
        this.cstItemSuffix = cstItemSuffix;
    }

    EnumClayHorseType(boolean visible, float maxHealth, float movementSpeed, boolean canBreatheUnderwater, int itemColor, String... textures) {
        this(visible, maxHealth, movementSpeed, canBreatheUnderwater, null, itemColor, textures);
    }

    EnumClayHorseType(boolean visible, float maxHealth, float movementSpeed, boolean canBreatheUnderwater, String cstItemSuffix, String... textures) {
        this(visible, maxHealth, movementSpeed, canBreatheUnderwater, cstItemSuffix, 0xFFFFFF, textures);
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
}
