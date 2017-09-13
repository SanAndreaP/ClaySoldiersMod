/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.particle;

import de.sanandrew.mods.claysoldiers.item.ItemHorseMount;
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
public class ParticleHorseBreaking
        extends ParticleBreaking
{
    protected ParticleHorseBreaking(World worldIn, double posXIn, double posYIn, double posZIn, EnumClayHorseType horseType) {
        super(worldIn, posXIn, posYIn, posZIn, ItemRegistry.DOLL_SOLDIER);

        ItemStack horseStack = ItemHorseMount.getTypeStack(horseType);
        ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
        IBakedModel model = mesher.getItemModel(horseStack);
        this.setParticleTexture(model.getOverrides().handleItemState(model, horseStack, null, null).getParticleTexture());

        ColorObj clr = new ColorObj(horseType.itemColor);
        this.particleRed = clr.fRed();
        this.particleGreen = clr.fGreen();
        this.particleBlue = clr.fBlue();
    }
}
