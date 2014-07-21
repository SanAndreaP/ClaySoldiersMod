/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.render.tileentity;

import de.sanandrew.core.manpack.util.client.ItemRenderHelper;
import de.sanandrew.mods.claysoldiers.client.models.tileentity.ModelClayNexus;
import de.sanandrew.mods.claysoldiers.client.util.Textures;
import de.sanandrew.mods.claysoldiers.util.ModItems;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class RenderClayNexus
    extends TileEntitySpecialRenderer
{
    public ModelClayNexus nexusModel = new ModelClayNexus();
    public ItemStack dollItem = new ItemStack(ModItems.dollSoldier);

    public RenderClayNexus() {

    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partTicks) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glRotatef(180F, 1, 0, 0);

        this.bindTexture(Textures.NEXUS_TEXTURE);
        nexusModel.renderTileEntity(0.0625F);

        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        int bright = 0xF0;
        int j = bright % 65536;
        int k = bright / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j / 1.0F, (float) k / 1.0F);
        this.bindTexture(Textures.NEXUS_GLOWING);
        nexusModel.renderTileEntityGlowmap(0.0625F);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glDisable(GL11.GL_BLEND);

        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 0.275F, (float) z + 0.5F);
        GL11.glScalef(0.25F, 0.25F, 0.25F);
        GL11.glRotatef(45F, 0F, 1, 0);
        GL11.glTranslatef(-0.5F, 0, 0);
        ItemRenderHelper.renderItemIn3D(dollItem, 0);
        GL11.glPopMatrix();
    }
}
