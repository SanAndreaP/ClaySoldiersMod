/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.particle;

import de.sanandrew.mods.claysoldiers.api.doll.IDollType;
import de.sanandrew.mods.claysoldiers.registry.ItemRegistry;
import de.sanandrew.mods.claysoldiers.registry.mount.EnumClayHorseType;
import de.sanandrew.mods.sanlib.lib.ColorObj;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleBreaking;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParticleDollBreaking
        extends ParticleBreaking
{
    protected ParticleDollBreaking(World worldIn, double posXIn, double posYIn, double posZIn, IDollType type) {
        super(worldIn, posXIn, posYIn, posZIn, ItemRegistry.DOLL_SOLDIER);

        ItemStack typeStack = type.getTypeStack();
        ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
        IBakedModel model = mesher.getItemModel(typeStack);
        this.setParticleTexture(model.getOverrides().handleItemState(model, typeStack, null, null).getParticleTexture());

        ColorObj clr = new ColorObj(type.getItemColor());
        this.particleRed = clr.fRed();
        this.particleGreen = clr.fGreen();
        this.particleBlue = clr.fBlue();
    }
}
