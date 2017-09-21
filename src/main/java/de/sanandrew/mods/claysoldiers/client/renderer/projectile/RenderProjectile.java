/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.renderer.projectile;

import de.sanandrew.mods.claysoldiers.entity.projectile.EntityClayProjectile;
import de.sanandrew.mods.claysoldiers.entity.projectile.EntityProjectileEmerald;
import de.sanandrew.mods.claysoldiers.entity.projectile.EntityProjectileFirecharge;
import de.sanandrew.mods.claysoldiers.entity.projectile.EntityProjectileGravel;
import de.sanandrew.mods.claysoldiers.entity.projectile.EntityProjectileSnow;
import de.sanandrew.mods.sanlib.lib.client.util.RenderUtils;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderProjectile<T extends EntityClayProjectile>
        extends Render<T>
{
    private final ItemStack renderedBlock;

    protected RenderProjectile(RenderManager renderManager, Block blockRendered) {
        super(renderManager);

        this.renderedBlock = new ItemStack(blockRendered, 1);
    }

    @Override
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        RenderUtils.renderStackInWorld(this.renderedBlock, 0, 0, 0, 0, 0, 0, 0.15D);
        GlStateManager.popMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(T entity) {
        return null;
    }

    public static class Gravel
            extends RenderProjectile<EntityProjectileGravel>
    {
        public Gravel(RenderManager renderManager) {
            super(renderManager, Blocks.GRAVEL);
        }
    }

    public static class Snow
            extends RenderProjectile<EntityProjectileSnow>
    {
        public Snow(RenderManager renderManager) {
            super(renderManager, Blocks.SNOW);
        }
    }

    public static class Firecharge
            extends RenderProjectile<EntityProjectileFirecharge>
    {
        public Firecharge(RenderManager renderManager) {
            super(renderManager, Blocks.MAGMA);
        }
    }

    public static class Emerald
            extends RenderProjectile<EntityProjectileEmerald>
    {
        public Emerald(RenderManager renderManager) {
            super(renderManager, Blocks.EMERALD_BLOCK);
        }
    }
}
