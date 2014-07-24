/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.block;

import de.sanandrew.mods.claysoldiers.tileentity.TileEntityClayNexus;
import de.sanandrew.mods.claysoldiers.util.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockClayNexus
    extends Block
{
    public BlockClayNexus() {
        super(Material.rock);
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
    public MapColor getMapColor(int meta) {
        return MapColor.obsidianColor;
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return Blocks.obsidian.getIcon(side, meta);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return null;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float offX, float offY, float offZ) {
        TileEntityClayNexus teNexus = (TileEntityClayNexus) world.getTileEntity(x, y, z);
        if( !world.isRemote ) {
            ItemStack playerItem = player.getCurrentEquippedItem();
            if( playerItem != null && playerItem.getItem() == ModItems.dollSoldier ) {
                teNexus.setInventorySlotContents(0, player.getCurrentEquippedItem());
                teNexus.markDirty();
                world.markBlockForUpdate(x, y, z);
                return true;
            }

            if( player.isSneaking() ) {
                teNexus.heal(teNexus.getMaxHealth());
                teNexus.markDirty();
                world.markBlockForUpdate(x, y, z);
                return true;
            }

            teNexus.isActive = !teNexus.isActive;
            teNexus.markDirty();
            world.markBlockForUpdate(x, y, z);
        }
        return true;
    }
}
