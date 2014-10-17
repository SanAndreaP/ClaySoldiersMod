/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.render.entity.projectile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderBlockProjectile
        extends Render
{
    private final RenderBlocks p_renderBlocks = new RenderBlocks();
    private final Block p_block;

    public RenderBlockProjectile(Block block) {
        this.shadowSize = 0.0F;
        this.p_block = block;
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float yaw, float partTicks) {
        int blockX = MathHelper.floor_double(entity.posX);
        int blockY = MathHelper.floor_double(entity.posY);
        int blockZ = MathHelper.floor_double(entity.posZ);

        GL11.glPushMatrix();
        GL11.glTranslatef((float) x, (float) y, (float) z);
        GL11.glScalef(0.1F, 0.1F, 0.1F);
        this.bindEntityTexture(entity);
        GL11.glDisable(GL11.GL_LIGHTING);

        this.p_renderBlocks.setRenderBoundsFromBlock(this.p_block);
        this.p_renderBlocks.renderBlockSandFalling(this.p_block, entity.worldObj, blockX, blockY, blockZ, 0);

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return TextureMap.locationBlocksTexture;
    }
}
