/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry.mount;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.doll.IDollType;
import de.sanandrew.mods.claysoldiers.item.ItemRegistry;
import de.sanandrew.mods.claysoldiers.util.CsmConfig;
import de.sanandrew.mods.sanlib.lib.util.config.Category;
import de.sanandrew.mods.sanlib.lib.util.config.EnumExclude;
import de.sanandrew.mods.sanlib.lib.util.config.Range;
import de.sanandrew.mods.sanlib.lib.util.config.Value;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;

import java.util.Arrays;

@Category(value = EnumClayHorseType.CFG_CAT, comment = "Horse and Pegasus entity configuration")
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

    @EnumExclude
    UNKNOWN(false, 0.0F, 0.0F, false, false, 0x0);

    public static final String CFG_CAT = CsmConfig.Entities.CAT_NAME + Configuration.CATEGORY_SPLITTER + "horses";
    public static final EnumClayHorseType[] VALUES = values();

    @Value(comment = "Maximum health of this type of horse.", range = @Range(minD = 0.0D, maxD = 1024.0D))
    public float maxHealth;
    @Value(comment = "Movement speed of this type of horse.", range = @Range(minD = 0.0D, maxD = 256.0D))
    public float movementSpeed;
    @Value(comment = "Allow this type of horse to breathe underwater.")
    public boolean amphibious;
    @Value(comment = "Allow this type of horse to resist fire and lava.")
    public boolean fireproof;
    public final boolean visible;
    public final int itemColor;
    public final String cstItemSuffix;
    public final ResourceLocation[] textures;

    EnumClayHorseType(boolean visible, float maxHealth, float movementSpeed, boolean canBreatheUnderwater, boolean immuneToFire, String cstItemSuffix, int itemColor, String... textures) {
        if (textures == null) {
            textures = new String[0];
        }

        this.visible = visible;
        this.maxHealth = maxHealth;
        this.movementSpeed = movementSpeed;
        this.amphibious = canBreatheUnderwater;
        this.itemColor = itemColor;
        this.textures = Arrays.stream(textures).map(s -> new ResourceLocation(CsmConstants.ID, String.format("textures/entities/mount/horses/%s.png", s)))
                              .toArray(ResourceLocation[]::new);
        this.cstItemSuffix = cstItemSuffix;
        this.fireproof = immuneToFire;
    }

    EnumClayHorseType(boolean visible, float maxHealth, float movementSpeed, boolean canBreatheUnderwater, boolean immuneToFire, int itemColor, String... textures) {
        this(visible, maxHealth, movementSpeed, canBreatheUnderwater, immuneToFire, null, itemColor, textures);
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
