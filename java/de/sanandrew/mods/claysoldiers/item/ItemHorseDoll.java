/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.item;

import com.google.common.collect.Maps;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.mods.claysoldiers.entity.mounts.EntityHorseMount;
import de.sanandrew.mods.claysoldiers.entity.mounts.EnumHorseType;
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
import java.util.Map;

public class ItemHorseDoll extends Item
{
    @SideOnly(Side.CLIENT)
    private Map<EnumHorseType, IIcon> icons;

    public ItemHorseDoll() {
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
                EntityHorseMount dan = spawnHorse(world, getType(stack), (double) blockX + 0.5D, (double) blockY + entityOffY, (double) blockZ + 0.5D);

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
     * Spawns the horse specified by the type in the location specified by the last three parameters.
     * @param world the World the entity will spawn in
     * @param type the type the horse will be
     */
    public static EntityHorseMount spawnHorse(World world, EnumHorseType type, double x, double y, double z) {
        EntityHorseMount jordan = new EntityHorseMount(world, type);

        jordan.setLocationAndAngles(x, y, z, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F), 0.0F);
        jordan.rotationYawHead = jordan.rotationYaw;
        jordan.renderYawOffset = jordan.rotationYaw;
        world.spawnEntityInWorld(jordan);
        jordan.playSound("step.gravel", 1.0F, 1.0F);

        return jordan;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass) {
        return this.icons.get(getType(stack));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconIndex(ItemStack stack) {
        return this.icons.get(getType(stack));
    }

    @Override
    public int getColorFromItemStack(ItemStack stack, int pass) {
        return getType(stack).itemData.getValue1();
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromType(EnumHorseType type) {
        return this.icons.get(type);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        Map<String, IIcon> names = Maps.newHashMap();
        this.icons = Maps.newEnumMap(EnumHorseType.class);
        for( EnumHorseType type : EnumHorseType.values ) {
            if( type.itemData == null ) {
                continue;
            }
            if( !names.containsKey(type.itemData.getValue0()) ) {
                names.put(type.itemData.getValue0(), iconRegister.registerIcon(type.itemData.getValue0()));
            }
            this.icons.put(type, names.get(type.itemData.getValue0()));
        }
    }

    public static EnumHorseType getType(ItemStack stack) {
        NBTTagCompound itemNbt = stack.getTagCompound();
        if( itemNbt != null && itemNbt.hasKey("type")) {
            return EnumHorseType.valueOf(itemNbt.getString("type"));
        } else {
            return EnumHorseType.DIRT;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void getSubItems(Item itemInst, CreativeTabs creativeTab, List stacks) {
        for( EnumHorseType type : EnumHorseType.values ) {
            if( type.itemData == null ) {
                continue;
            }
            ItemStack stack = new ItemStack(this, 1);
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setString("type", type.toString());
            stack.setTagCompound(nbt);
            stacks.add(stack);
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack) {
        return super.getUnlocalizedName(par1ItemStack) + "." + getType(par1ItemStack).toString().toLowerCase();
    }
}
