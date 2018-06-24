/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.renderer.world;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.IDisruptable;
import de.sanandrew.mods.claysoldiers.item.ItemDisruptor;
import de.sanandrew.mods.claysoldiers.registry.ItemRegistry;
import de.sanandrew.mods.claysoldiers.util.CsmConfiguration;
import de.sanandrew.mods.claysoldiers.util.Lang;
import de.sanandrew.mods.sanlib.lib.util.ItemStackUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

public class RenderDisruptorOverlay
{
    public static final RenderDisruptorOverlay INSTANCE = new RenderDisruptorOverlay();

    private static final long MAX_SHOWTIME_NANOSEC = 3__000_000_000L;
    private static final long FADEOUT_AT_NANOSEC = 2_500_000_000L;

    private long lastShownTime = 0L;
    private IDisruptable.DisruptState lastState = null;
    private int lastItemId = 0;
    private Map<IDisruptable.DisruptState, ResourceLocation> images = null;

    private RenderDisruptorOverlay() { }

    public void render(ScaledResolution sr) {
        if( this.images == null ) {
            this.images = new EnumMap<>(IDisruptable.DisruptState.class);
            Arrays.stream(IDisruptable.DisruptState.VALUES)
                  .forEach(state -> this.images.put(state, new ResourceLocation(CsmConstants.ID, "textures/gui/disruptor/" + state.name().toLowerCase(Locale.ROOT) + ".png")));
        }

        Minecraft mc = Minecraft.getMinecraft();

        if( mc.player != null ) {
            ItemStack currentItem = mc.player.inventory.getCurrentItem();
            if( ItemStackUtils.isItem(currentItem, ItemRegistry.DISRUPTOR) ) {
                IDisruptable.DisruptState newState = ItemDisruptor.getState(currentItem);
                if( this.lastItemId == mc.player.inventory.currentItem ) {
                    long shownTimeDelta = (System.nanoTime() - this.lastShownTime);
                    if( newState != this.lastState ) {
                        this.lastState = newState;
                        this.lastShownTime = System.nanoTime();
                        shownTimeDelta = 0;
                    }
                    if( shownTimeDelta < MAX_SHOWTIME_NANOSEC ) {
                        renderOverlay(sr, currentItem, mc, shownTimeDelta);
                    }
                } else {
                    this.lastState = newState;
                }
            } else {
                this.lastShownTime = 0;
            }

            this.lastItemId = mc.player.inventory.currentItem;
        }
    }

    private void renderOverlay(ScaledResolution sr, ItemStack stack, Minecraft mc, long time) {
        int stateId = this.lastState.ordinal();
        IDisruptable.DisruptState[] vals = IDisruptable.DisruptState.VALUES;
        ResourceLocation prevPic = this.images.get(stateId == 0 ? vals[vals.length - 1] : vals[stateId - 1]);
        ResourceLocation currPic = this.images.get(vals[stateId]);
        ResourceLocation postPic = this.images.get(stateId == vals.length - 1 ? vals[0] : vals[stateId + 1]);

        float fade = 1.0F - (time >= FADEOUT_AT_NANOSEC ? (time - FADEOUT_AT_NANOSEC) / (float)(MAX_SHOWTIME_NANOSEC - FADEOUT_AT_NANOSEC) : 0.0F);
        GlStateManager.pushMatrix();
        GlStateManager.translate(sr.getScaledWidth() / 2.0F - 16.0F, sr.getScaledHeight() / 2.0F + 10.0F, 0.0F);

        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.5F * fade);
        mc.renderEngine.bindTexture(prevPic);
        Gui.drawModalRectWithCustomSizedTexture(-34, 0, 0, 0, 32, 32, 32.0F, 32.0F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F * fade);
        mc.renderEngine.bindTexture(currPic);
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, 32, 32, 32.0F, 32.0F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.5F * fade);
        mc.renderEngine.bindTexture(postPic);
        Gui.drawModalRectWithCustomSizedTexture(34, 0, 0, 0, 32, 32, 32.0F, 32.0F);

        String s = Lang.translate(ItemRegistry.DISRUPTOR.getTranslateKey(stack, "state." + vals[stateId].name().toLowerCase(Locale.ROOT)));
        mc.fontRenderer.drawString(s, 16 - mc.fontRenderer.getStringWidth(s) / 2, 36, 0xFFFFFF | Math.max(((int)(0xFF * fade)), 0x04) << 24, true);

        GlStateManager.popMatrix();
    }
}
