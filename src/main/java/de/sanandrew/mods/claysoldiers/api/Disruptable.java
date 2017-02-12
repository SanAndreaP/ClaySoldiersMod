/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.api;

import net.minecraft.entity.Entity;

public interface Disruptable
{
    void disrupt();

    DisruptType getDisruptType();

    enum DisruptType {
        SOLDIER,
        ALL
    }
}
