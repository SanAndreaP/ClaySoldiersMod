/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.renderer;

import de.sanandrew.mods.claysoldiers.api.client.ISoldierRenderer;
import de.sanandrew.mods.claysoldiers.client.model.ModelClaySoldier;
import de.sanandrew.mods.claysoldiers.client.renderer.soldier.LayerGoggles;
import de.sanandrew.mods.claysoldiers.client.renderer.soldier.LayerLeatherArmor;
import de.sanandrew.mods.claysoldiers.client.renderer.soldier.LayerSoldierHeldItem;
import de.sanandrew.mods.claysoldiers.entity.EntityClaySoldier;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

@SideOnly(Side.CLIENT)
public class RenderClaySoldier
        extends RenderBiped<EntityClaySoldier>
{
    public ConcurrentNavigableMap<Integer, Queue<ISoldierRenderer>> renderHooks;

    public RenderClaySoldier(RenderManager manager) {
        super(manager, new ModelClaySoldier(), 0.1F);
        this.renderHooks = new ConcurrentSkipListMap<>();

        this.layerRenderers.add(new LayerSoldierHeldItem(this));
        this.layerRenderers.add(new LayerGoggles(this));
        this.layerRenderers.add(new LayerLeatherArmor(this));
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

    public boolean addRenderHook(ISoldierRenderer renderer) {
        if( renderer != null ) {
            Queue<ISoldierRenderer> queue = this.renderHooks.computeIfAbsent(renderer.getPriority(), key -> new ConcurrentLinkedQueue<>());
            queue.add(renderer);
            return true;
        }

        return false;
    }
}
