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
import de.sanandrew.mods.sanlib.lib.util.config.Category;
import de.sanandrew.mods.sanlib.lib.util.config.EnumExclude;
import de.sanandrew.mods.sanlib.lib.util.config.Range;
import de.sanandrew.mods.sanlib.lib.util.config.Value;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;

import java.util.Arrays;

@Category(value = EnumWoolBunnyType.CFG_CAT, comment = "Bunny entity configuration")
public enum EnumWoolBunnyType
        implements IDollType
{
    BLACK(true, 0x191919, "black"),
    RED(true, 0xCC4646, "red"),
    GREEN(true, 0x667F33, "green"),
    BROWN(true, 0x664C33, "brown"),
    BLUE(true, 0x334CB2, "blue"),
    PURPLE(true, 0x7F3FB2, "purple"),
    CYAN(true, 0x4C7F99, "cyan"),
    LIGHT_GRAY(true, 0x999999, "light_gray"),
    GRAY(true, 0x4C4C4C, "gray"),
    PINK(true, 0xF27FA5, "pink"),
    LIME(true, 0x7FCC19, "lime"),
    YELLOW(true, 0xE5E533, "yellow"),
    LIGHT_BLUE(true, 0x6699D8, "light_blue"),
    MAGENTA(true, 0xE000FF, "magenta"),
    ORANGE(true, 0xD87F33, "orange"),
    WHITE(true, 0xFFFFFF, "white"),

    @EnumExclude
    UNKNOWN(false, 0x0);

    public static final String CFG_CAT = CsmConfig.Entities.CAT_NAME + Configuration.CATEGORY_SPLITTER + "bunnies";
    public static final EnumWoolBunnyType[] VALUES = values();

    @Value(comment = "Maximum health of this type of bunny", range = @Range(minD = 0.0D, maxD = 1024.0D))
    public float maxHealth;
    @Value(comment = "Movement speed of this type of bunny", range = @Range(minD = 0.0D, maxD = 256.0D))
    public float movementSpeed;
    @Value(comment = "Jumping movement speed of this type of bunny", range = @Range(minD = 0.0D, maxD = 256.0D))
    public float jumpMovementSpeed;
    public final boolean visible;
    public final int itemColor;
    public final ResourceLocation[] textures;

    EnumWoolBunnyType(boolean visible, int itemColor, String... textures) {
        if (textures == null) {
            textures = new String[0];
        }

        this.maxHealth = 20.0F;
        this.movementSpeed = 1.0F;
        this.jumpMovementSpeed = 0.42F;
        this.visible = visible;
        this.itemColor = itemColor;
        this.textures = Arrays.stream(textures).map(s -> new ResourceLocation(CsmConstants.ID, String.format("textures/entities/mount/bunnies/%s.png", s)))
                              .toArray(ResourceLocation[]::new);
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
        return ItemRegistry.DOLL_BUNNY.getTypeStack(this);
    }
}
