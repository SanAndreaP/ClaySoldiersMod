/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.renderer;

import de.sanandrew.mods.claysoldiers.api.client.ISoldierRenderHook;
import de.sanandrew.mods.claysoldiers.api.client.soldier.ISoldierRender;
import de.sanandrew.mods.claysoldiers.client.model.ModelClaySoldier;
import de.sanandrew.mods.claysoldiers.entity.EntityClaySoldier;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

@SideOnly(Side.CLIENT)
public class RenderClaySoldier
        extends RenderBiped<EntityClaySoldier>
        implements ISoldierRender<EntityClaySoldier, RenderClaySoldier>
{
    public ConcurrentNavigableMap<Integer, Queue<ISoldierRenderHook>> renderHooks;

    public RenderClaySoldier(RenderManager manager) {
        super(manager, new ModelClaySoldier(), 0.1F);
        this.renderHooks = new ConcurrentSkipListMap<>();

        ClaySoldiersMod.PLUGINS.forEach(plugin -> plugin.registerSoldierRenderLayer(this));
    }

    @Override
    protected void renderLivingAt(EntityClaySoldier soldier, double x, double y, double z) {
        super.renderLivingAt(soldier, x, y, z);
        if( soldier.i58O55 != null && soldier.i58O55 ) {
            GlStateManager.scale(0.4F, 0.4F, 0.4F);
        } else {
            GlStateManager.scale(0.2F, 0.2F, 0.2F);
        }
    }

    @Override
    protected void renderModel(EntityClaySoldier soldier, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        this.renderHooks.forEach((key, val) -> val.forEach(hook -> hook.renderModelPre(soldier, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor)));
        super.renderModel(soldier, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
        this.renderHooks.forEach((key, val) -> val.forEach(hook -> hook.renderModelPost(soldier, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor)));
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityClaySoldier entity) {
        int textureType = entity.getTextureType();
        int textureId = entity.getTextureId();
        if( textureType == 0x02 ) {
            return entity.getSoldierTeam().getUniqueTexture(textureId);
        } else if( textureType == 0x01 ) {
            return entity.getSoldierTeam().getRareTexture(textureId);
        }
        return entity.getSoldierTeam().getNormalTexture(textureId);
    }

    @Override
    public boolean addRenderHook(ISoldierRenderHook renderer) {
        if( renderer != null ) {
            Queue<ISoldierRenderHook> queue = this.renderHooks.computeIfAbsent(renderer.getPriority(), key -> new ConcurrentLinkedQueue<>());
            queue.add(renderer);
            return true;
        }

        return false;
    }

    @Override
    public boolean addRenderLayer(LayerRenderer<? extends EntityLivingBase> layer) {
        return this.addLayer(layer);
    }

    @Override
    public ModelBiped getSoldierModel() {
        return (ModelClaySoldier) this.mainModel;
    }

    @Override
    public void bindSoldierTexture(ResourceLocation location) {
        this.bindTexture(location);
    }

    @Override
    public RenderClaySoldier getRender() {
        return this;
    }

    @Override
    public Map<Integer, Queue<ISoldierRenderHook>> getRenderHookDesc() {
        return this.renderHooks.descendingMap();
    }

    @Override
    public Map<Integer, Queue<ISoldierRenderHook>> getRenderHookAsc() {
        return new ConcurrentSkipListMap<>(this.renderHooks);
    }
}
