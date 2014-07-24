/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.render.tileentity;

import de.sanandrew.core.manpack.util.SAPUtils;
import de.sanandrew.core.manpack.util.SAPUtils.RGBAValues;
import de.sanandrew.core.manpack.util.client.ItemRenderHelper;
import de.sanandrew.mods.claysoldiers.client.models.tileentity.ModelClayNexus;
import de.sanandrew.mods.claysoldiers.client.util.Textures;
import de.sanandrew.mods.claysoldiers.item.ItemClayManDoll;
import de.sanandrew.mods.claysoldiers.tileentity.TileEntityClayNexus;
import de.sanandrew.mods.claysoldiers.util.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class RenderClayNexus
    extends TileEntitySpecialRenderer
{
    public ModelClayNexus nexusModel = new ModelClayNexus();
    public ItemStack dollItem = new ItemStack(ModItems.dollSoldier);

    public RenderClayNexus() { }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partTicks) {
        TileEntityClayNexus nexus = (TileEntityClayNexus) tileEntity;

        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);

        this.bindTexture(Textures.NEXUS_TEXTURE);
        nexusModel.renderTileEntity(0.0625F);

        this.renderGlowmap(nexus);
        this.renderItem(nexus, partTicks);

        ItemStack heldItem = Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem();
        if( heldItem != null && heldItem.getItem() == ModItems.statDisplay ) {
            this.renderHealth(nexus);
        }

        GL11.glPopMatrix();
    }

    private void renderGlowmap(TileEntityClayNexus nexus) {
        float[] colors = new float[] {1.0F, 1.0F, 1.0F};
        if( nexus.getStackInSlot(0) != null ) {
            RGBAValues rgba = SAPUtils.getRgbaFromColorInt(ItemClayManDoll.getTeam(nexus.getStackInSlot(0)).getIconColor());
            colors[0] = rgba.getRed() / 255.0F;
            colors[1] = rgba.getGreen() / 255.0F;
            colors[2] = rgba.getBlue() / 255.0F;
        }

        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        int bright = 0xF0;
        int j = bright % 65536;
        int k = bright / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j / 1.0F, (float) k / 1.0F);
        this.bindTexture(Textures.NEXUS_GLOWING);
        GL11.glColor3f(colors[0], colors[1], colors[2]);
        this.nexusModel.renderTileEntityGlowmap(0.0625F);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glDisable(GL11.GL_BLEND);
    }

    private void renderItem(TileEntityClayNexus nexus, float partTicks) {
        float[] colors = new float[] {1.0F, 1.0F, 1.0F};
        if( nexus.getStackInSlot(0) != null ) {
            RGBAValues rgba = SAPUtils.getRgbaFromColorInt(ItemClayManDoll.getTeam(nexus.getStackInSlot(0)).getIconColor());
            colors[0] = rgba.getRed() / 255.0F;
            colors[1] = rgba.getGreen() / 255.0F;
            colors[2] = rgba.getBlue() / 255.0F;
        }

        float itmAngle = nexus.prevSpinAngle + (nexus.spinAngle - nexus.prevSpinAngle) * partTicks - 45.0F;

        if( nexus.getStackInSlot(0) != null ) {
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, 1.225F, 0.0F);
            GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
            GL11.glScalef(0.25F, 0.25F, 0.25F);
            GL11.glRotatef(itmAngle, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(-0.5F, 0.0F, 0.0F);
            GL11.glColor3f(colors[0], colors[1], colors[2]);
            ItemRenderHelper.renderItemIn3D(nexus.getStackInSlot(0), 0);
            GL11.glColor3f(1.0F, 1.0F, 1.0F);
            GL11.glPopMatrix();
        }
    }

    private void renderHealth(TileEntityClayNexus nexus) {
        Tessellator tessellator = Tessellator.instance;

        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, 0.5F, 0.0F);
        GL11.glRotatef(RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(RenderManager.instance.playerViewX, 1.0F, 0.0F, 0.0F);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LIGHTING);

        float healthPerc = Math.min(1.0F, 1.0F - nexus.getHealth() / nexus.getMaxHealth());

        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 1.0F);
        tessellator.addVertex(-0.5D, -0.05D, 0.0D);
        tessellator.addVertex(-0.5D + healthPerc, -0.05D, 0.0D);
        tessellator.addVertex(-0.5D + healthPerc, 0.05D, 0.0D);
        tessellator.addVertex(-0.5D, 0.05D, 0.0D);
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(1.0F, 0.0F, 0.0F, 1.0F);
        tessellator.addVertex(-0.5D + healthPerc, -0.05D, 0.0D);
        tessellator.addVertex(0.5D, -0.05D, 0.0D);
        tessellator.addVertex(0.5D, 0.05D, 0.0D);
        tessellator.addVertex(-0.5D + healthPerc, 0.05D, 0.0D);
        tessellator.draw();

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glPopMatrix();
    }
}
