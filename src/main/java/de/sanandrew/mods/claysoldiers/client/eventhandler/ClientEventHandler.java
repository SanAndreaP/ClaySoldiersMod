/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.eventhandler;

import de.sanandrew.mods.claysoldiers.client.renderer.world.RenderDisruptorOverlay;
import de.sanandrew.mods.claysoldiers.client.renderer.world.RenderEmeraldLighting;
import de.sanandrew.mods.claysoldiers.network.PacketManager;
import de.sanandrew.mods.claysoldiers.network.packet.PacketSwitchDisruptorState;
import de.sanandrew.mods.claysoldiers.registry.ItemRegistry;
import de.sanandrew.mods.sanlib.lib.ColorObj;
import de.sanandrew.mods.sanlib.lib.XorShiftRandom;
import de.sanandrew.mods.sanlib.lib.util.ItemStackUtils;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@SideOnly(Side.CLIENT)
public class ClientEventHandler
{
    public static final ClientEventHandler INSTANCE = new ClientEventHandler();

    public static float partTicks;
    public static int ticksInGame;

    private ClientEventHandler() { }

    @SubscribeEvent
    public void onMouseEvent(MouseEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        int dWheel = event.getDwheel();
        if( mc.currentScreen == null && mc.player != null && mc.player.isSneaking() && !mc.player.isSpectator() && dWheel != 0 ) {
            ItemStack stack = mc.player.inventory.getCurrentItem();
            if( ItemStackUtils.isItem(stack, ItemRegistry.DISRUPTOR) ) {
                event.setCanceled(true);
                PacketManager.sendToServer(new PacketSwitchDisruptorState(dWheel > 0));
            }
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if( event.phase == TickEvent.Phase.END ) {
            GuiScreen gui = Minecraft.getMinecraft().currentScreen;
            if( gui == null || !gui.doesGuiPauseGame() ) {
                ticksInGame++;
            }
        }
    }

    @SubscribeEvent
    public void renderWorldLast(RenderWorldLastEvent event) {
        partTicks = event.getPartialTicks();

        RenderEmeraldLighting.INSTANCE.render(partTicks);
    }

    @SubscribeEvent
    public void onIngameGuiRender(RenderGameOverlayEvent.Pre event) {
        if( event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR ) {
            RenderDisruptorOverlay.INSTANCE.render(event.getResolution());
        }
    }
}
