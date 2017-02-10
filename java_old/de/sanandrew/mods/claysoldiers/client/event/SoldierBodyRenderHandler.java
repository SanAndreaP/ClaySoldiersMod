/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.core.manpack.util.javatuples.Triplet;
import de.sanandrew.mods.claysoldiers.client.event.SoldierRenderEvent.EnumRenderStage;
import de.sanandrew.mods.claysoldiers.client.render.entity.RenderClayMan;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.soldier.effect.SoldierEffects;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgrades;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SideOnly(Side.CLIENT)
public class SoldierBodyRenderHandler
{
    private Random p_thunderboldRNG = new Random();

    private ItemStack p_feather = new ItemStack(Items.feather);
    private ItemStack p_glass = new ItemStack(Blocks.glass);
    private ItemStack p_glassStained = new ItemStack(Blocks.stained_glass);

    @SubscribeEvent
    public void onSoldierRender(SoldierRenderEvent event) {
        if( event.stage == EnumRenderStage.PRE || event.stage == EnumRenderStage.POST ) {
            if( event.clayMan.hasUpgrade(SoldierUpgrades.UPG_EGG) ) {
                renderStealthEffect(event.stage);
            }

            if( event.clayMan.hasUpgrade(SoldierUpgrades.UPG_GLOWSTONE) ) {
                renderGlowstoneEffect(event.stage);
            }

            if( event.clayMan.hasEffect(SoldierEffects.EFF_THUNDER) && event.stage == EnumRenderStage.PRE ) {
                renderThunderbolt(event.clayMan, event.renderX, event.renderY, event.renderZ);
            }
        }

        if( event.stage == EnumRenderStage.EQUIPPED ) {
            if( event.clayMan.hasUpgrade(SoldierUpgrades.UPG_FEATHER) && !event.clayMan.onGround && event.clayMan.motionY < -0.1D
                && event.clayMan.fallDistance >= 1.3F )
            {
                this.renderFeather(event.clayMan, event.clayManRender);
            }

            if( event.clayMan.hasUpgrade(SoldierUpgrades.UPG_GLASS) ) {
                this.renderGlass(event.clayMan, event.clayManRender);
            }
        }
    }

    @SubscribeEvent
    public void onSoldierLivingRender(SoldierRenderEvent.RenderLivingEvent event) {
        if( event.clayMan.hasUpgrade(SoldierUpgrades.UPG_IRON_INGOT) ) {
            GL11.glScalef(1.19F, 1.19F, 1.19F);
        }

        if( event.clayMan.hasUpgrade(SoldierUpgrades.UPG_ENDERPEARL) ) {
            GL11.glColor3f(0.5F, 0.5F, 0.5F);
        }
    }

    private static void renderStealthEffect(EnumRenderStage stage) {
        if( stage == EnumRenderStage.PRE ) {
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
        } else {
            GL11.glDisable(GL11.GL_BLEND);
        }
    }

    private static void renderGlowstoneEffect(EnumRenderStage stage) {
        if( stage == EnumRenderStage.PRE ) {
            int brightness = 0xF0;
            int brightX = brightness % 65536;
            int brightY = brightness / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightX / 1.0F, brightY / 1.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    private void renderThunderbolt(EntityClayMan clayMan, double targetX, double targetY, double targetZ) {
        NBTTagCompound effectNbt = clayMan.getEffect(SoldierEffects.EFF_THUNDER).getNbtTag();
        if( effectNbt.getShort("ticksRemaining") < 17 ) {
            return;
        }

        double originX = effectNbt.getDouble("originX") - clayMan.posX + targetX;
        double originY = effectNbt.getDouble("originY") - clayMan.posY + targetY;
        double originZ = effectNbt.getDouble("originZ") - clayMan.posZ + targetZ;

        List<Triplet<Double, Double, Double>> randCoords = new ArrayList<>();
        randCoords.add(Triplet.with(0.0D, 0.0D, 0.0D));

        this.p_thunderboldRNG.setSeed(effectNbt.getLong("randomLightning"));

        int size = this.p_thunderboldRNG.nextInt(5) + 6;
        for( int i = 0; i < size; i++ ) {
            randCoords.add(Triplet.with(this.p_thunderboldRNG.nextDouble() * 0.5D - 0.25D, this.p_thunderboldRNG.nextDouble() * 0.5D - 0.25D,
                                        this.p_thunderboldRNG.nextDouble() * 0.5D - 0.25D)
            );
        }
        randCoords.add(Triplet.with(0.0D, 0.25D, 0.0D));
        size++;

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glShadeModel(GL11.GL_SMOOTH);

        float prevLightX = OpenGlHelper.lastBrightnessX;
        float prevLightY = OpenGlHelper.lastBrightnessY;
        int brightness = 0xF0;
        int brightX = brightness % 65536;
        int brightY = brightness / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightX / 1.0F, brightY / 1.0F);

        for( int i = 0; i < size; i++ ) {
            Triplet<Double, Double, Double> origin = randCoords.get(i);
            Triplet<Double, Double, Double> target = randCoords.get(i + 1);

            double oX = originX + ((targetX - originX) / size) * (i) + origin.getValue0();
            double tX = originX + ((targetX - originX) / size) * (i + 1) + target.getValue0();

            double oY = originY + ((targetY - originY) / size) * (i) + origin.getValue1();
            double tY = originY + ((targetY - originY) / size) * (i + 1) + target.getValue1();

            double oZ = originZ + ((targetZ - originZ) / size) * (i) + origin.getValue2();
            double tZ = originZ + ((targetZ - originZ) / size) * (i + 1) + target.getValue2();

            drawThunderboldPart(Tessellator.instance, oX, oY, oZ, tX, tY, tZ);
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, prevLightX, prevLightY);
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }

    private void renderFeather(EntityClayMan clayMan, RenderClayMan renderer) {
        GL11.glPushMatrix();
        renderer.modelBipedMain.bipedBody.postRender(0.0625F);
        GL11.glTranslatef(0.0F, -0.6F, 0.0F);

        float itemScale = 1.5F;
        GL11.glScalef(itemScale, itemScale, itemScale);
        GL11.glTranslatef(0.6F, 0.05F, 0.0F);
        GL11.glRotatef(22.5F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(90.0F, -1.0F, 0.0F, 1.0F);

        renderer.getItemRenderer().renderItem(clayMan, this.p_feather, 0);
        GL11.glPopMatrix();
    }

    private void renderGlass(EntityClayMan clayMan, RenderClayMan renderer) {
        GL11.glPushMatrix();
        renderer.modelBipedMain.bipedHead.postRender(0.0625F);
        GL11.glTranslatef(0.0F, -0.6F, 0.0F);

        float itemScale = 0.18F;

        GL11.glScalef(itemScale, itemScale, itemScale);
        GL11.glTranslatef(0.84F, 1.5F, -1.1F);

        short color = clayMan.getUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_GLASS)).getNbtTag().getShort("leftColor");
        if( color < 0 ) {
            renderer.getItemRenderer().renderItem(clayMan, this.p_glass, 0);
        } else {
            this.p_glassStained.setItemDamage(color);
            renderer.getItemRenderer().renderItem(clayMan, this.p_glassStained, 0);
        }
        GL11.glTranslatef(-1.68F, 0.0F, 0.0F);

        color = clayMan.getUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_GLASS)).getNbtTag().getShort("rightColor");
        if( color < 0 ) {
            renderer.getItemRenderer().renderItem(clayMan, this.p_glass, 0);
        } else {
            this.p_glassStained.setItemDamage(color);
            renderer.getItemRenderer().renderItem(clayMan, this.p_glassStained, 0);
        }
        GL11.glPopMatrix();
    }

    private static void drawThunderboldPart(Tessellator tessellator, double oX, double oY, double oZ, double tX, double tY, double tZ) {
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA(255, 255, 255, 0);
        tessellator.addVertex(tX - 0.05D, tY, tZ);
        tessellator.addVertex(oX - 0.05D, oY, oZ);
        tessellator.setColorRGBA(255, 255, 255, 255);
        tessellator.addVertex(oX, oY, oZ);
        tessellator.addVertex(tX, tY, tZ);
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setColorRGBA(255, 255, 255, 255);
        tessellator.addVertex(oX, oY, oZ);
        tessellator.addVertex(tX, tY, tZ);
        tessellator.setColorRGBA(255, 255, 255, 0);
        tessellator.addVertex(oX + 0.05D, oY, oZ);
        tessellator.addVertex(tX + 0.05D, tY, tZ);
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setColorRGBA(255, 255, 255, 0);
        tessellator.addVertex(tX, tY - 0.05D, tZ);
        tessellator.addVertex(oX, oY - 0.05D, oZ);
        tessellator.setColorRGBA(255, 255, 255, 255);
        tessellator.addVertex(oX, oY, oZ);
        tessellator.addVertex(tX, tY, tZ);
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setColorRGBA(255, 255, 255, 255);
        tessellator.addVertex(oX, oY, oZ);
        tessellator.addVertex(tX, tY, tZ);
        tessellator.setColorRGBA(255, 255, 255, 0);
        tessellator.addVertex(oX, oY + 0.05D, oZ);
        tessellator.addVertex(tX, tY + 0.05D, tZ);
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setColorRGBA(255, 255, 255, 0);
        tessellator.addVertex(tX, tY, tZ - 0.05D);
        tessellator.addVertex(oX, oY, oZ - 0.05D);
        tessellator.setColorRGBA(255, 255, 255, 255);
        tessellator.addVertex(oX, oY, oZ);
        tessellator.addVertex(tX, tY, tZ);
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setColorRGBA(255, 255, 255, 255);
        tessellator.addVertex(oX, oY, oZ);
        tessellator.addVertex(tX, tY, tZ);
        tessellator.setColorRGBA(255, 255, 255, 0);
        tessellator.addVertex(oX, oY, oZ + 0.05D);
        tessellator.addVertex(tX, tY, tZ + 0.05D);
        tessellator.draw();
    }
}
