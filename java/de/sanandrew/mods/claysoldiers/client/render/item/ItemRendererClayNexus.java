/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.render.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.mods.claysoldiers.client.model.tileentity.ModelClayNexus;
import de.sanandrew.mods.claysoldiers.client.util.Textures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class ItemRendererClayNexus
        implements IItemRenderer
{
    private ModelClayNexus modelClayNexus = new ModelClayNexus();

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        switch( type ) {
            case ENTITY:
                this.renderNexus(0.0F, -0.45F, 0.0F);
                break;
            case EQUIPPED:
                this.renderNexus(0.5F, 0.4F, 0.5F);
                break;
            case INVENTORY:
                this.renderNexus(1.0F, 0.26F, 1.0F);
                break;
            default:
                this.renderNexus(0.0F, 0.4F, 0.5F);
        }
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    private void renderNexus(float x, float y, float z) {
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y + 1.57F + 0.125F, z);
        GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glScalef(1.1875F, 1.1875F, 1.1875F);

        Minecraft.getMinecraft().getTextureManager().bindTexture(Textures.NEXUS_TEXTURE);
        this.modelClayNexus.renderTileEntity();
        Minecraft.getMinecraft().getTextureManager().bindTexture(Textures.NEXUS_GLOWING);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        this.modelClayNexus.renderTileEntityGlowmap();
        GL11.glDisable(GL11.GL_BLEND);

        GL11.glPopMatrix();
    }
}
