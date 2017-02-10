/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.item;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.IDisruptable;
import de.sanandrew.mods.claysoldiers.util.soldier.ClaymanTeam;
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

public class ItemClayManDoll
        extends Item
        implements IDisruptable
{
    public ItemClayManDoll() {
        super();
        this.maxStackSize = 16;
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    /**
     * Spawns the soldier specified by the team in the location specified by the last three parameters.
     *
     * @param world the World the entity will spawn in
     * @param team  the team the soldier will be
     */
    public static EntityClayMan spawnClayMan(World world, String team, double x, double y, double z) {
        EntityClayMan jordan = new EntityClayMan(world, team);

        jordan.setLocationAndAngles(x, y, z, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F), 0.0F);
        jordan.rotationYawHead = jordan.rotationYaw;
        jordan.renderYawOffset = jordan.rotationYaw;
        world.spawnEntityInWorld(jordan);
        jordan.playSound("step.gravel", 1.0F, 1.0F);

        return jordan;
    }

    public static ClaymanTeam getTeam(ItemStack stack) {
        if( stack != null ) {
            NBTTagCompound itemNbt = stack.getTagCompound();
            if( itemNbt != null && itemNbt.hasKey("team") ) {
                return ClaymanTeam.getTeam(itemNbt.getString("team"));
            } else {
                return ClaymanTeam.NULL_TEAM;
            }
        } else {
            return ClaymanTeam.NULL_TEAM;
        }
    }

    public static ItemStack setTeamForItem(String team, ItemStack stack) {
        NBTTagCompound nbt = new NBTTagCompound();

        if( stack.hasTagCompound() ) {
            nbt = stack.getTagCompound();
        }

        nbt.setString("team", team);
        stack.setTagCompound(nbt);

        return stack;
    }

    @Override
    public IIcon getIconIndex(ItemStack stack) {
        return getTeam(stack).getIconInstance();
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
                EntityClayMan dan = spawnClayMan(world, getTeam(stack).getTeamName(), blockX + 0.5D, blockY + entityOffY, blockZ + 0.5D);

                if( dan != null ) {
                    if( stack.hasDisplayName() ) {
                        dan.setCustomNameTag(stack.getDisplayName());
                    }

                    if( !player.capabilities.isCreativeMode ) {
                        dan.dollItem = stack.splitStack(1);
                    }
                }
            }

            return true;
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack) {
        return super.getUnlocalizedName(par1ItemStack) + '.' + getTeam(par1ItemStack).getTeamName().toLowerCase();
    }

    @Override
    public int getColorFromItemStack(ItemStack stack, int pass) {
        return getTeam(stack).getIconColor();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void getSubItems(Item itemInst, CreativeTabs creativeTab, List stacks) {
        for( String team : ClaymanTeam.getTeamNamesForDolls() ) {
            ItemStack stack = new ItemStack(this, 1);
            setTeamForItem(team, stack);
            stacks.add(stack);
        }
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        ClaymanTeam.registerIcons(iconRegister);
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        return getTeam(stack).getIconInstance();
    }

    @Override
    public void disrupt() { }
}
