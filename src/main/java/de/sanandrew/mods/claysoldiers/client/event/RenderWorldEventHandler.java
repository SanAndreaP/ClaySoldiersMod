/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.event;

import de.sanandrew.mods.sanlib.lib.XorShiftRandom;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityCreature;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.Queue;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@SideOnly(Side.CLIENT)
public class RenderWorldEventHandler
{
    public static int ticksInGame;
    private static final WeakHashMap<Vec3d, Queue<RenderLightning>> LIGHTNING_RENDERS = new WeakHashMap<>();

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
        LIGHTNING_RENDERS.forEach((key, value) -> value.removeIf(RenderLightning::finished));
        LIGHTNING_RENDERS.entrySet().removeIf(entry -> entry.getValue().isEmpty());

        float lastBrightX = OpenGlHelper.lastBrightnessX;
        float lastBrightY = OpenGlHelper.lastBrightnessY;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 0xF0, 0x0);
        LIGHTNING_RENDERS.forEach((key, value) -> {
            GlStateManager.pushMatrix();
            GlStateManager.translate(key.x, key.y, key.z);
            GlStateManager.scale(0.01D, 0.01D, 0.01D);
            GlStateManager.translate(0.0F, -9*16, 0.0F);
            value.forEach(val -> val.doRender(event.getPartialTicks()));
            GlStateManager.popMatrix();
        });
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightX, lastBrightY);
    }

    public void setRenderLightningAt(EntityCreature entity) {
        LIGHTNING_RENDERS.computeIfAbsent(new Vec3d(entity.posX, entity.posY, entity.posZ), inst -> new ConcurrentLinkedQueue<>()).add(new RenderLightning());
    }

    private static class RenderLightning
    {
        private static final int MAX_TICKS_VISIBLE = 10;

        private final int ticksVisible;
        private final long seed;

        public RenderLightning() {
            this.ticksVisible = RenderWorldEventHandler.ticksInGame + MAX_TICKS_VISIBLE;
            this.seed = MiscUtils.RNG.randomLong();
        }

        public boolean finished() {
            return this.ticksVisible <= RenderWorldEventHandler.ticksInGame;
        }

        public void doRender(float partTicks) {
            GlStateManager.disableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);

            Tessellator tess = Tessellator.getInstance();
            this.renderLightning(tess, partTicks);

            GlStateManager.disableBlend();
            GlStateManager.enableLighting();
            GlStateManager.enableTexture2D();
        }

        public void renderLightning(Tessellator tess, float partTicks) {
            double xPos[] = new double[8];
            double zPos[] = new double[8];
            double maxX = 0.0D;
            double maxZ = 0.0D;
            XorShiftRandom rngMain = new XorShiftRandom(this.seed);
            BufferBuilder buf = tess.getBuffer();

            for( int i = 7; i >= 0; i-- ) {
                xPos[i] = maxX;
                zPos[i] = maxZ;
                maxX += rngMain.randomInt(11) - 5;
                maxZ += rngMain.randomInt(11) - 5;
            }

            for( int j = 0; j < 4; j++ ) {
                XorShiftRandom rngBranch = new XorShiftRandom(this.seed);

                for( int k = 0; k < 3; k++ ) {
                    int maxLvl = 7;
                    int minLvl = rngBranch.randomInt(7);

                    double minX = xPos[maxLvl] - maxX;
                    double minZ = zPos[maxLvl] - maxZ;

                    for( int level = maxLvl; level >= minLvl; level-- ) {
                        double xBranch = minX;
                        double zBranch = minZ;

                        if( k == 0 ) {
                            minX += rngBranch.randomInt(10) - 5;
                            minZ += rngBranch.randomInt(10) - 5;
                        } else {
                            minX += rngBranch.randomInt(31) - 15;
                            minZ += rngBranch.randomInt(31) - 15;
                        }

                        buf.begin(GL11.GL_TRIANGLE_STRIP, DefaultVertexFormats.POSITION_COLOR);

                        double maxDither = 0.1D + j * 0.2D;

                        if( k == 0 ) {
                            maxDither *= level * 0.1D + 1.0D;
                        }

                        double minDither = 0.1D + j * 0.2D;

                        if( k == 0 ) {
                            minDither *= (level - 1) * 0.1D + 1.0D;
                        }

                        for( int k1 = 0; k1 < 5; k1++ ) {
                            double xTwigMax = 0.5D - maxDither;
                            double zTwigMax = 0.5D - maxDither;

                            if( k1 == 1 || k1 == 2 ) {
                                xTwigMax += maxDither * 2.0D;
                            }

                            if( k1 == 2 || k1 == 3 ) {
                                zTwigMax += maxDither * 2.0D;
                            }

                            double xTwigMin = 0.5D - minDither;
                            double zTwigMin = 0.5D - minDither;

                            if( k1 == 1 || k1 == 2 ) {
                                xTwigMin += minDither * 2.0D;
                            }

                            if( k1 == 2 || k1 == 3 ) {
                                zTwigMin += minDither * 2.0D;
                            }

                            float lum = 0.5F;
                            float alpha = (this.ticksVisible - RenderWorldEventHandler.ticksInGame - partTicks) / MAX_TICKS_VISIBLE;
                            buf.pos(xTwigMin + minX,    level * 16,       zTwigMin + minZ)   .color(0.3F * lum, 0.9F * lum, 0.3F * lum, alpha).endVertex();
                            buf.pos(xTwigMax + xBranch, (level + 1) * 16, zTwigMax + zBranch).color(0.4F * lum, 0.9F * lum, 0.3F * lum, alpha).endVertex();
                        }

                        tess.draw();
                    }
                }
            }
        }
    }
}
