/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.renderer;

import de.sanandrew.mods.claysoldiers.api.client.ISoldierRenderer;
import de.sanandrew.mods.claysoldiers.api.soldier.IUpgradeInst;
import de.sanandrew.mods.claysoldiers.client.model.ModelClaySoldier;
import de.sanandrew.mods.claysoldiers.client.renderer.soldier.LayerSoldierHeldItem;
import de.sanandrew.mods.claysoldiers.entity.EntityClaySoldier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@SideOnly(Side.CLIENT)
public class RenderClaySoldier
        extends RenderBiped<EntityClaySoldier>
{
    public Map<Integer, Queue<ISoldierRenderer>> renderHooks;

    public RenderClaySoldier(RenderManager manager) {
        super(manager, new ModelClaySoldier(), 0.1F);
        this.renderHooks = new ConcurrentHashMap<>();

        this.layerRenderers.add(new LayerSoldierHeldItem(this));
    }

    @Override
    protected void renderLivingAt(EntityClaySoldier entityLivingBaseIn, double x, double y, double z) {
        super.renderLivingAt(entityLivingBaseIn, x, y, z);
        GlStateManager.scale(0.2F, 0.2F, 0.2F);
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
