/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.api.client.soldier;

import de.sanandrew.mods.claysoldiers.api.client.ISoldierRenderHook;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public interface ISoldierRender<T extends EntityLiving, U extends RenderBiped<T>> {
    boolean addRenderHook(ISoldierRenderHook renderer);

    ModelBiped getSoldierModel();

    void bindSoldierTexture(ResourceLocation location);

    U getRender();
}
