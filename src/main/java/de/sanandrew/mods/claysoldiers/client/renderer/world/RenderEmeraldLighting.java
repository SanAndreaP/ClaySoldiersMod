/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.renderer.world;

import de.sanandrew.mods.claysoldiers.client.eventhandler.ClientEventHandler;
import de.sanandrew.mods.sanlib.lib.ColorObj;
import de.sanandrew.mods.sanlib.lib.XorShiftRandom;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@SideOnly(Side.CLIENT)
public class RenderEmeraldLighting
{
    public static final RenderEmeraldLighting INSTANCE = new RenderEmeraldLighting();
    private static final Queue<RenderLightning> LIGHTNING_RENDERS = new ConcurrentLinkedQueue<>();

    private RenderEmeraldLighting() { }

    public void render(float partTicks) {
        Entity renderEntity = Minecraft.getMinecraft().getRenderViewEntity();
        if( renderEntity == null ) {
            return;
        }

        double renderX = renderEntity.lastTickPosX + (renderEntity.posX - renderEntity.lastTickPosX) * partTicks;
        double renderY = renderEntity.lastTickPosY + (renderEntity.posY - renderEntity.lastTickPosY) * partTicks;
        double renderZ = renderEntity.lastTickPosZ + (renderEntity.posZ - renderEntity.lastTickPosZ) * partTicks;

        LIGHTNING_RENDERS.removeIf(RenderLightning::finished);

        float lastBrightX = OpenGlHelper.lastBrightnessX;
        float lastBrightY = OpenGlHelper.lastBrightnessY;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 0xF0, 0x0);
        LIGHTNING_RENDERS.forEach(value -> {
            GlStateManager.pushMatrix();
            GlStateManager.translate(value.x - renderX, value.y - renderY, value.z - renderZ);
            GlStateManager.scale(0.01D, 0.01D, 0.01D);
            value.doRender(partTicks);
            GlStateManager.popMatrix();
        });
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightX, lastBrightY);
    }

    public void setRenderLightningAt(double x, double y, double z, int color) {
        LIGHTNING_RENDERS.add(new RenderLightning(x, y, z, color));
    }

    private static class RenderLightning
    {
        private static final int MAX_TICKS_VISIBLE = 10;

        private final int ticksVisible;
        private final long seed;

        final double x;
        final double y;
        final double z;
        private final ColorObj color;

        public RenderLightning(double x, double y, double z, int color) {
            this.ticksVisible = ClientEventHandler.ticksInGame + MAX_TICKS_VISIBLE;
            this.seed = MiscUtils.RNG.randomLong();

            this.x = x;
            this.y = y;
            this.z = z;

            this.color = new ColorObj(color);
        }

        public boolean finished() {
            return this.ticksVisible <= ClientEventHandler.ticksInGame;
        }

        public void doRender(float partTicks) {
            GlStateManager.disableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);

            Tessellator tess = Tessellator.getInstance();
            this.renderLightning(tess, partTicks);

            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
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
                            float alpha = (this.ticksVisible - ClientEventHandler.ticksInGame - partTicks) / MAX_TICKS_VISIBLE;
                            buf.pos(xTwigMin + minX,    level * 16,       zTwigMin + minZ)   .color(color.fRed() * lum, color.fGreen() * lum, color.fBlue() * lum, alpha).endVertex();
                            buf.pos(xTwigMax + xBranch, (level + 1) * 16, zTwigMax + zBranch).color(color.fRed() * lum, color.fGreen() * lum, color.fBlue() * lum, alpha).endVertex();
                        }

                        tess.draw();
                    }
                }
            }
        }
    }
}
