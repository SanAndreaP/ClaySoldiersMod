/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.api.client.soldier;

import de.sanandrew.mods.claysoldiers.api.client.ISoldierRenderHook;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;
import java.util.Queue;

@SideOnly(Side.CLIENT)
public interface ISoldierRender<T extends EntityLiving, U extends RenderBiped<T>> {
    boolean addRenderHook(ISoldierRenderHook renderer);

    boolean addRenderLayer(LayerRenderer<? extends EntityLivingBase> layer);

    ModelBiped getSoldierModel();

    void bindSoldierTexture(ResourceLocation location);

    U getRender();

    Map<Integer, Queue<ISoldierRenderHook>> getRenderHookDesc();

    Map<Integer, Queue<ISoldierRenderHook>> getRenderHookAsc();
}
