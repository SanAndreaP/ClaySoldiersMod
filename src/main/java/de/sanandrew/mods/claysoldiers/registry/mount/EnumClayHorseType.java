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
import de.sanandrew.mods.claysoldiers.util.CsmConfig;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;

import java.util.Arrays;
import java.util.Locale;

@CsmConfig.Category(EnumClayHorseType.CFG_CAT)
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
    CAKE(true, 30.0F, 1.4F, false, false, "cake", 0xFFFFFF, "cake"),

    NIGHTMARE(false, 50.0F, 1.6F, false, true, 0x0, "spec_nightmare1", "spec_nightmare2"),

    @CsmConfig.EnumExclude
    UNKNOWN(false, 0.0F, 0.0F, false, false, 0x0);

    public static final String CFG_CAT = CsmConfig.Entities.CAT_NAME + Configuration.CATEGORY_SPLITTER + "horses";
    public static final EnumClayHorseType[] VALUES = values();

    @CsmConfig.Value(value = "%sHorseMaxHealth", category = CFG_CAT, comment = "Maximum health of a %s horse.", range = @CsmConfig.Range(minD = 0.0D, maxD = 1024.0D))
    public float maxHealth;
    @CsmConfig.Value(value = "%sHorseMovementSpeed", category = CFG_CAT, comment = "Movement speed of a %s horse.", range = @CsmConfig.Range(minD = 0.0D, maxD = 256.0D))
    public float movementFactor;
    @CsmConfig.Value(value = "%sHorseAmphibious", category = CFG_CAT, comment = "Allow %s horses to breathe underwater.")
    public boolean canBreatheUnderwater;
    @CsmConfig.Value(value = "%sHorseFireproof", category = CFG_CAT, comment = "Allow %s horses to resist fire and lava.")
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
}
