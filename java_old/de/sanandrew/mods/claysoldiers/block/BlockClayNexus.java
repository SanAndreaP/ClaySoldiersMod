/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.mods.claysoldiers.tileentity.TileEntityClayNexus;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockClayNexus
        extends Block
{
    public BlockClayNexus() {
        super(Material.rock);
    }

    @Override
    public MapColor getMapColor(int meta) {
        return MapColor.obsidianColor;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return Blocks.obsidian.getIcon(side, meta);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return null;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float offX, float offY, float offZ) {
        TileEntityClayNexus teNexus = (TileEntityClayNexus) world.getTileEntity(x, y, z);
        if( !world.isRemote ) {
            ItemStack playerItem = player.getCurrentEquippedItem();
            if( playerItem != null ) {
                if( teNexus.isItemValidForSlot(TileEntityClayNexus.SOLDIER_SLOT, playerItem) ) {
                    teNexus.setInventorySlotContents(TileEntityClayNexus.SOLDIER_SLOT, playerItem);
                    teNexus.markDirty();
                    world.markBlockForUpdate(x, y, z);
                    return true;
                } else if( teNexus.isItemValidForSlot(TileEntityClayNexus.THROWABLE_SLOT, playerItem) ) {
                    teNexus.setInventorySlotContents(TileEntityClayNexus.THROWABLE_SLOT, playerItem);
                    teNexus.markDirty();
                    world.markBlockForUpdate(x, y, z);
                    return true;
                }
            }

            if( player.isSneaking() ) {
                teNexus.repair();
                teNexus.markDirty();
                world.markBlockForUpdate(x, y, z);
                return true;
            }

            teNexus.isActive = !teNexus.isActive;
            world.setBlockMetadataWithNotify(x, y, z, teNexus.isActive ? 1 : 0, 2);
            teNexus.markDirty();
            world.markBlockForUpdate(x, y, z);
        }

        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z) == 1 ? 6 : 0;
    }

    @Override
    public boolean hasTileEntity(int meta) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, int meta) {
        return new TileEntityClayNexus();
    }

    @Override
    public boolean canConnectRedstone(IBlockAccess world, int x, int y, int z, int side) {
        return side >= 0;
    }
}
