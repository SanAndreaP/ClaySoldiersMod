/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.model.item;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.util.ResourceLocation;

interface MeshDef
        extends ItemMeshDefinition
{
    ResourceLocation[] getResLocations();
}
