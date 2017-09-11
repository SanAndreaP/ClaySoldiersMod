/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.mount;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;

public enum EnumClayHorseType
{
    DIRT("dirt1", "dirt2", "dirt3", "dirt4"),
    GRASS("grass1", "grass2"),

    UNKNOWN();

    public static final EnumClayHorseType[] VALUES = values();

    public final ResourceLocation[] textures;

    EnumClayHorseType(String... textures) {
        if( textures == null ) {
            textures = new String[0];
        }

        this.textures = Arrays.stream(textures).map(s -> new ResourceLocation(CsmConstants.ID, String.format("textures/entities/mount/horses/%s.png", s)))
                              .toArray(ResourceLocation[]::new);
    }
}
