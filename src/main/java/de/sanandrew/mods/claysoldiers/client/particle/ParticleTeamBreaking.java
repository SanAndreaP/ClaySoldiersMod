/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.particle;

import de.sanandrew.mods.claysoldiers.api.soldier.ITeam;
import de.sanandrew.mods.claysoldiers.registry.ItemRegistry;
import de.sanandrew.mods.claysoldiers.registry.TeamRegistry;
import de.sanandrew.mods.sanlib.lib.client.ColorObj;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleBreaking;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParticleTeamBreaking
        extends ParticleBreaking
{
    protected ParticleTeamBreaking(World worldIn, double posXIn, double posYIn, double posZIn, ITeam sldTeam) {
        super(worldIn, posXIn, posYIn, posZIn, ItemRegistry.doll_soldier);

        ItemStack teamStack = TeamRegistry.INSTANCE.getNewTeamStack(1, sldTeam);
        ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
        IBakedModel model = mesher.getItemModel(teamStack);
        //noinspection ConstantConditions
        this.setParticleTexture(model.getOverrides().handleItemState(model, teamStack, null, null).getParticleTexture());

        ColorObj clr = new ColorObj(sldTeam.getItemColor());
        this.particleRed = clr.fRed();
        this.particleGreen = clr.fGreen();
        this.particleBlue = clr.fBlue();
    }
}
