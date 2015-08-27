/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [Jul 2, 2014, 12:12:45 AM (GMT)]
 */
package de.sanandrew.mods.claysoldiers.client.particle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.Tessellator;

@SideOnly(Side.CLIENT)
public final class ParticleRenderDispatcher
{
    public static void dispatch() {
        Tessellator tessellator = Tessellator.instance;

//        GL11.glDepthMask(false);
//        GL11.glEnable(GL11.GL_BLEND);
//        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
//        GL11.glAlphaFunc(GL11.GL_GREATER, 0.003921569F);
        ParticleNexusFX.dispatchQueuedRenders(tessellator);
//        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
//        GL11.glDisable(GL11.GL_BLEND);
//        GL11.glDepthMask(true);
    }
}
