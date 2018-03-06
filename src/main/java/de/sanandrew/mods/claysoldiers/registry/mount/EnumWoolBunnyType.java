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
    
    UNKNOWN(false, 0x0);

    public static final EnumWoolBunnyType[] VALUES = values();

    public float maxHealth;
    public float movementFactor;
    public float jumpMoveFactor;
    public final boolean visible;
    public final int itemColor;
    public final ResourceLocation[] textures;

    EnumWoolBunnyType(boolean visible, int itemColor, String... textures) {
        if (textures == null) {
            textures = new String[0];
        }

        this.maxHealth = 20.0F;
        this.movementFactor = 1.0F;
        this.jumpMoveFactor = 0.42F;
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

    public static void updateConfiguration(Configuration config) {
        final String category = CsmConfiguration.CAT_ENTITY_VALS + Configuration.CATEGORY_SPLITTER + "Bunnies";
        config.getCategory(category).setRequiresWorldRestart(true);

        for( EnumWoolBunnyType type : VALUES ) {
            if( type == UNKNOWN ) {
                continue;
            }
            String typeNameL = type.getName().toLowerCase(Locale.ENGLISH);

            type.maxHealth = config.getFloat(typeNameL + "BunnyMaxHealth", category, type.maxHealth, 0.0F, 1024.0F,
                                             "Maximum health of a " + typeNameL + " bunny");
            type.movementFactor = config.getFloat(typeNameL + "BunnyMovementFactor", category, type.movementFactor, 0.0F, 1024.0F,
                                                  "Movement factor of a " + typeNameL + " bunny");
            type.jumpMoveFactor = config.getFloat(type.getName().toLowerCase(Locale.ENGLISH) + "BunnyJumpMoveFactor", category, type.jumpMoveFactor, 0.0F, 1024.0F,
                                                  "Jump movement factor of a " + typeNameL + " bunny");
        }
    }
}
