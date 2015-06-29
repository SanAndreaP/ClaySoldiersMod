/*******************************************************************************************************************
 * Name:      RenderGravelChunk.java
 * Author:    SanAndreasP
 * Copyright: SanAndreasP and SilverChiren
 * License:   Attribution-NonCommercial-ShareAlike 3.0 Unported (http://creativecommons.org/licenses/by-nc-sa/3.0/)
 *******************************************************************************************************************/

package sanandreasp.mods.ClaySoldiersMod.client.render;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.src.*;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import java.util.Random;
import org.lwjgl.opengl.GL11;

import sanandreasp.mods.ClaySoldiersMod.entity.projectile.EntityGravelChunk;

public class RenderGravelChunk extends Render
{
    public RenderGravelChunk()
    {
        renderBlocks = new RenderBlocks();
        random = new Random();
        shadowSize = 0.0F;
        shadowOpaque = 0.75F;
    }

    public void doRenderItem(EntityGravelChunk entitygravelchunk, double d, double d1, double d2, 
            float f, float f1)
    {
        random.setSeed(187L);
        ItemStack itemstack = new ItemStack(Block.gravel.blockID, 1 ,0);
        GL11.glPushMatrix();
        float f2 = MathHelper.sin((entitygravelchunk.entityAge + f1) / 10F);
        float f3 = ((entitygravelchunk.entityAge + f1) / 20F);
		
        byte byte0 = 1;
        GL11.glTranslatef((float)d, (float)d1 + f2, (float)d2);
        GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        if (itemstack.itemID < 256 && RenderBlocks.renderItemIn3d(Block.blocksList[itemstack.itemID].getRenderType()))
        {
            GL11.glRotatef(f3, 0.0F, 1.0F, 0.0F);
//            loadTexture("/terrain.png");
            float f4 = 0.1F;
            GL11.glScalef(f4, f4, f4);
            for (int j = 0; j < byte0; j++)
            {
                GL11.glPushMatrix();
                if (j > 0)
                {
                    float f5 = ((random.nextFloat() * 2.0F - 1.0F) * 0.2F) / f4;
                    float f7 = ((random.nextFloat() * 2.0F - 1.0F) * 0.2F) / f4;
                    float f9 = ((random.nextFloat() * 2.0F - 1.0F) * 0.2F) / f4;
                    GL11.glTranslatef(f5, f7, f9);
                }
                renderBlocks.renderBlockAsItem(Block.blocksList[itemstack.itemID], itemstack.getItemDamage(), entitygravelchunk.getBrightness(f1));
                GL11.glPopMatrix();
            }

        }
        GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        GL11.glPopMatrix();
    }

    private void renderQuad(Tessellator tessellator, int i, int j, int k, int l, int i1)
    {
        tessellator.startDrawingQuads();
        tessellator.setColorOpaque_I(i1);
        tessellator.addVertex(i + 0, j + 0, 0.0D);
        tessellator.addVertex(i + 0, j + l, 0.0D);
        tessellator.addVertex(i + k, j + l, 0.0D);
        tessellator.addVertex(i + k, j + 0, 0.0D);
        tessellator.draw();
    }

    public void renderTexturedQuad(int i, int j, int k, int l, int i1, int j1)
    {
        float f = 0.0F;
        float f1 = 0.00390625F;
        float f2 = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(i + 0, j + j1, f, (k + 0) * f1, (l + j1) * f2);
        tessellator.addVertexWithUV(i + i1, j + j1, f, (k + i1) * f1, (l + j1) * f2);
        tessellator.addVertexWithUV(i + i1, j + 0, f, (k + i1) * f1, (l + 0) * f2);
        tessellator.addVertexWithUV(i + 0, j + 0, f, (k + 0) * f1, (l + 0) * f2);
        tessellator.draw();
    }

    @Override
	public void doRender(Entity entity, double d, double d1, double d2, 
            float f, float f1)
    {
        doRenderItem((EntityGravelChunk)entity, d, d1, d2, f, f1);
    }

    private RenderBlocks renderBlocks;
    private Random random;
    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        // TODO Auto-generated method stub
        return null;
    }
}
