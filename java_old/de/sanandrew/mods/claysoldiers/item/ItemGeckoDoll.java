/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import de.sanandrew.mods.claysoldiers.entity.mount.EntityGeckoMount;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import de.sanandrew.mods.claysoldiers.util.IDisruptable;
import de.sanandrew.mods.claysoldiers.util.mount.EnumGeckoType;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class ItemGeckoDoll
        extends Item
        implements IDisruptable
{
    public IIcon iconBody;

    public ItemGeckoDoll() {
        super();
        this.setMaxStackSize(16);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    /**
     * Spawns the gecko specified by the type in the location specified by the last three parameters.
     *
     * @param world the World the entity will spawn in
     * @param type  the type the bunny will be
     */
    public static EntityGeckoMount spawnGecko(World world, EnumGeckoType type, double x, double y, double z) {
        EntityGeckoMount jordan = new EntityGeckoMount(world, type);

        jordan.setLocationAndAngles(x, y, z, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F), 0.0F);
        jordan.rotationYawHead = jordan.rotationYaw;
        jordan.renderYawOffset = jordan.rotationYaw;
        world.spawnEntityInWorld(jordan);
        jordan.playSound("step.wood", 1.0F, 1.0F);

        return jordan;
    }

    public static EnumGeckoType getType(ItemStack stack) {
        if(stack == null || !SAPUtils.isIndexInRange(EnumGeckoType.VALUES, stack.getItemDamage())) {
            return EnumGeckoType.BIRCH_BIRCH;
        }

        return EnumGeckoType.VALUES[stack.getItemDamage()];
    }

    public static void setType(ItemStack stack, EnumGeckoType type) {
        stack.setItemDamage(type.ordinal());
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
                EntityGeckoMount dan = spawnGecko(world, getType(stack), blockX + 0.5D, blockY + entityOffY, blockZ + 0.5D);

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
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack) + '.' + getType(stack).toString().toLowerCase();
    }

    @Override
    public int getColorFromItemStack(ItemStack stack, int pass) {
        return pass == 1 ? getType(stack).colors.getValue1() : getType(stack).colors.getValue0();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void getSubItems(Item item, CreativeTabs creativeTab, List stacks) {
        for( EnumGeckoType type : EnumGeckoType.VALUES ) {
            ItemStack stack = new ItemStack(this, 1);
            setType(stack, type);
            stacks.add(stack);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(ClaySoldiersMod.MOD_ID + ":doll_gecko_spots");
        this.iconBody = iconRegister.registerIcon(ClaySoldiersMod.MOD_ID + ":doll_gecko_body");
    }

    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    public int getRenderPasses(int metadata) {
        return 2;
    }

    @Override
    public IIcon getIconFromDamageForRenderPass(int damage, int pass) {
        return pass == 1 ? this.iconBody : this.itemIcon;
    }

    @Override
    public void disrupt() { }
}
