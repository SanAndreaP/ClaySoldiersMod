/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import net.minecraft.util.ResourceLocation;

public enum Resources
{
    ITEM_SOLDIER_CLAY("soldiers/clay"),

    ENTITY_SOLDIER_NORM_CLAY("textures/entities/soldiers/lightgray.png");

    private final ResourceLocation location;

    Resources(String texture) {
        this.location = new ResourceLocation(CsmConstants.ID, texture);
    }

    public ResourceLocation getResource() {
        return this.location;
    }

    @Override
    public String toString() {
        return this.location.toString();
    }
}
