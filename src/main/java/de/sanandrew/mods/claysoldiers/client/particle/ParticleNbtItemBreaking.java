/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.particle;

import de.sanandrew.mods.claysoldiers.api.doll.IDollType;
import de.sanandrew.mods.claysoldiers.registry.ItemRegistry;
import de.sanandrew.mods.sanlib.lib.ColorObj;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleBreaking;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParticleNbtItemBreaking
        extends ParticleBreaking
{
    protected ParticleNbtItemBreaking(World worldIn, double posXIn, double posYIn, double posZIn, int itemId, int damage, NBTTagCompound nbt) {
        super(worldIn, posXIn, posYIn, posZIn, Item.getItemById(itemId));

        ItemStack stack = new ItemStack(Item.getItemById(itemId), 1, damage);
        stack.setTagCompound(nbt);
        this.init(stack);
    }

    protected ParticleNbtItemBreaking(World worldIn, double posXIn, double posYIn, double posZIn, ItemStack stack) {
        super(worldIn, posXIn, posYIn, posZIn, stack.getItem());
        this.init(stack);
    }

    private void init(ItemStack stack) {
        ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
        IBakedModel model = mesher.getItemModel(stack);
        this.setParticleTexture(model.getOverrides().handleItemState(model, stack, null, null).getParticleTexture());

        ColorObj clr = new ColorObj(Minecraft.getMinecraft().getItemColors().colorMultiplier(stack, 0));
        this.particleRed = clr.fRed();
        this.particleGreen = clr.fGreen();
        this.particleBlue = clr.fBlue();
    }
}
