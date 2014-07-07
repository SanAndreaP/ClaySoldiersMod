package de.sanandrew.mods.claysoldiers.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.ClaymanTeam;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;

/**
 * @author SanAndreas
 * @version 1.0
 */
public class ItemClayDoll extends Item
{
    public ItemClayDoll() {
        super();
        maxStackSize = 16;
        setHasSubtypes(true);
        setMaxDamage(0);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int blockX, int blockY, int blockZ, int side, float offX, float offY, float offZ) {
        if( world.isRemote ) {
            return true;
        } else {
            Block block = world.getBlock(blockX, blockY, blockZ);
            double entityOffY = 0.0D;
            int maxSpawns = stack.stackSize;

            if( player.isSneaking() ) {
                maxSpawns = 1;
            }

            if( side == 1 && block.getRenderType() == 11 ) {
                entityOffY = 0.5D;
            }

            blockX += Facing.offsetsXForSide[side];
            blockY += Facing.offsetsYForSide[side];
            blockZ += Facing.offsetsZForSide[side];

            for( int i = 0; i < maxSpawns; i++ ) {
                EntityClayMan dan = spawnClayMan(world, getTeam(stack).getTeamName(), (double) blockX + 0.5D, (double) blockY + entityOffY, (double) blockZ + 0.5D);

                if( dan != null ) {
                    if( stack.hasDisplayName() ) {
                        dan.setCustomNameTag(stack.getDisplayName());
                    }

                    if( !player.capabilities.isCreativeMode ) {
                        --stack.stackSize;
                    } else {
                        dan.shouldDropDoll = false;
                    }
                }
            }

            return true;
        }
    }

    /**
     * Spawns the soldier specified by the team in the location specified by the last three parameters.
     * @param world the World the entity will spawn in
     * @param team the team the soldier will be
     */
    public static EntityClayMan spawnClayMan(World world, String team, double x, double y, double z) {
        EntityClayMan jordan = new EntityClayMan(world, team);

        jordan.setLocationAndAngles(x, y, z, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F), 0.0F);
        jordan.rotationYawHead = jordan.rotationYaw;
        jordan.renderYawOffset = jordan.rotationYaw;
        world.spawnEntityInWorld(jordan);
        jordan.playSound("dig.gravel", 1.0F, 1.0F);

        return jordan;
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        return this.getTeam(stack).getIconInstance();
    }

    @Override
    public IIcon getIconIndex(ItemStack stack) {
        return this.getTeam(stack).getIconInstance();
    }

    @Override
    public int getColorFromItemStack(ItemStack stack, int pass) {
        return this.getTeam(stack).getIconColor();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        ClaymanTeam.registerIcons(iconRegister);
    }

    private ClaymanTeam getTeam(ItemStack stack) {
        NBTTagCompound itemNbt = stack.getTagCompound();
        if( itemNbt != null && itemNbt.hasKey("team")) {
            return ClaymanTeam.getTeamFromName(itemNbt.getString("team"));
        } else {
            return ClaymanTeam.getTeamFromName("clay");
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void getSubItems(Item itemInst, CreativeTabs creativeTab, List stacks) {
        for( String team : ClaymanTeam.getTeamNames() ) {
            ItemStack stack = new ItemStack(this, 1);
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setString("team", team);
            stack.setTagCompound(nbt);
            stacks.add(stack);
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack) {
        return super.getUnlocalizedName(par1ItemStack) + "." + getTeam(par1ItemStack).getTeamName().toLowerCase();
    }
}
