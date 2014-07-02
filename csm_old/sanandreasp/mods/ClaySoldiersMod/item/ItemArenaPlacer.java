package sanandreasp.mods.ClaySoldiersMod.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemArenaPlacer extends Item {

    public ItemArenaPlacer(int par1) {
        super(par1);
    }
    
    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
        par2EntityPlayer.setPositionAndUpdate(par2EntityPlayer.posX, par2EntityPlayer.posY + 2D, par2EntityPlayer.posZ);
        for( int i = -4; i <= 4; i++  ) {
            for( int j = -4; j <= 4; j++ ) {
                if( j*j == 16 || i*i == 16) {
                    par3World.setBlock(par4+i, par5+1, par6+j, Block.planks.blockID);
                    par3World.setBlock(par4+i, par5+2, par6+j, Block.planks.blockID);
                }
                if( j == 0 && i*i != 16 ) {
                    par3World.setBlock(par4+i, par5, par6, Block.planks.blockID);
                    par3World.setBlock(par4+i, par5+2, par6, Block.glass.blockID);
//                    par3World.setBlock(par4+i, par5, par6, 0);
                    par3World.setBlock(par4+i, par5-1, par6, Block.pistonStickyBase.blockID, 1, 3);
                    par3World.setBlock(par4+i, par5-2, par6-1, Block.planks.blockID);
                    par3World.setBlock(par4+i, par5-2, par6-2, Block.planks.blockID);
                    par3World.setBlock(par4+i, par5-2, par6+1, Block.planks.blockID);
                    par3World.setBlock(par4+i, par5-2, par6+2, Block.planks.blockID);
                    par3World.setBlock(par4+i, par5-1, par6-2, Block.redstoneWire.blockID);
                    par3World.setBlock(par4+i, par5-1, par6+2, Block.redstoneWire.blockID);
                    if( i % 2 == 0 ) par3World.setBlock(par4+i, par5-1, par6-1, Block.redstoneWire.blockID);
                    else par3World.setBlock(par4+i, par5-1, par6+1, Block.redstoneWire.blockID);
                    par3World.setBlock(par4+i, par5-1, par6+2, Block.redstoneWire.blockID);
                }
            }
        }
        par3World.setBlock(par4+4, par5-2, par6+2, Block.planks.blockID);
        par3World.setBlock(par4+4, par5-2, par6-2, Block.planks.blockID);
        par3World.setBlock(par4+4, par5-1, par6-1, Block.planks.blockID);
        par3World.setBlock(par4+4, par5-1, par6, Block.planks.blockID);
        par3World.setBlock(par4+4, par5-1, par6+1, Block.planks.blockID);

        par3World.setBlock(par4+4, par5-1, par6+2, Block.redstoneWire.blockID);
        par3World.setBlock(par4+4, par5-1, par6-2, Block.redstoneWire.blockID);
        par3World.setBlock(par4+4, par5, par6+2, 0);
        par3World.setBlock(par4+4, par5, par6-2, 0);
        par3World.setBlock(par4+4, par5, par6-1, Block.redstoneWire.blockID);
        par3World.setBlock(par4+4, par5, par6, Block.redstoneWire.blockID);
        par3World.setBlock(par4+4, par5, par6+1, Block.redstoneWire.blockID);
        
        par3World.setBlock(par4+5, par5+1, par6, Block.lever.blockID, 1, 2);
        
        return super.onItemUse(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10);
    }

}
