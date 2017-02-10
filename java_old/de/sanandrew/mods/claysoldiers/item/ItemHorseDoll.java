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
import de.sanandrew.mods.claysoldiers.entity.mount.EntityHorseMount;
import de.sanandrew.mods.claysoldiers.entity.mount.EntityPegasusMount;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import de.sanandrew.mods.claysoldiers.util.IDisruptable;
import de.sanandrew.mods.claysoldiers.util.mount.EnumHorseType;
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

public class ItemHorseDoll
        extends Item
        implements IDisruptable
{
    private Map<EnumHorseType, IIcon> p_icons;
    private IIcon p_pegasusWings;

    public ItemHorseDoll() {
        super();
        this.setMaxStackSize(16);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    /**
     * Spawns the horse specified by the type in the location specified by the last three parameters.
     *
     * @param world the World the entity will spawn in
     * @param type  the type the horse will be
     */
    public static EntityHorseMount spawnHorse(World world, EnumHorseType type, boolean isPegasus, double x, double y, double z) {
        EntityHorseMount jordan;

        if( isPegasus ) {
            jordan = new EntityPegasusMount(world, type);
        } else {
            jordan = new EntityHorseMount(world, type);
        }

        jordan.setLocationAndAngles(x, y, z, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F), 0.0F);
        jordan.rotationYawHead = jordan.rotationYaw;
        jordan.renderYawOffset = jordan.rotationYaw;
        world.spawnEntityInWorld(jordan);
        jordan.playSound("step.gravel", 1.0F, 1.0F);

        return jordan;
    }

    public static void setType(ItemStack stack, EnumHorseType type, boolean isPegasus) {
        if( type.itemData == null ) {
            return;
        }

        NBTTagCompound nbt = stack.hasTagCompound() ? stack.getTagCompound() : new NBTTagCompound();
        nbt.setString("type", type.toString());
        nbt.setBoolean("pegasus", isPegasus);
        stack.setTagCompound(nbt);
    }

    public static EnumHorseType getType(ItemStack stack) {
        NBTTagCompound itemNbt = stack.getTagCompound();
        if( itemNbt != null && itemNbt.hasKey("type") ) {
            return EnumHorseType.valueOf(itemNbt.getString("type"));
        } else {
            return EnumHorseType.DIRT;
        }
    }

    public static boolean isPegasus(ItemStack stack) {
        NBTTagCompound itemNbt = stack.getTagCompound();
        return itemNbt != null && itemNbt.getBoolean("pegasus");
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
                EntityHorseMount dan = spawnHorse(world, getType(stack), isPegasus(stack), blockX + 0.5D, blockY + entityOffY, blockZ + 0.5D);

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
        String name = super.getUnlocalizedName(stack) + '.' + getType(stack).toString().toLowerCase();

        if( isPegasus(stack) ) {
            name += ".pegasus";
        }

        return name;
    }

    @Override
    public int getColorFromItemStack(ItemStack stack, int pass) {
        return pass == 0 || !isPegasus(stack) ? getType(stack).itemData.getValue1() : 0xFFFFFF;
    }

    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void getSubItems(Item item, CreativeTabs creativeTab, List stacks) {
        for( EnumHorseType type : EnumHorseType.VALUES ) {
            if( type.itemData == null ) {
                continue;
            }

            ItemStack stack = new ItemStack(this, 1);
            setType(stack, type, false);
            stacks.add(stack.copy());
            setType(stack, type, true);
            stacks.add(stack);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        Map<String, IIcon> names = Maps.newHashMap();
        this.p_icons = Maps.newEnumMap(EnumHorseType.class);
        for( EnumHorseType type : EnumHorseType.VALUES ) {
            if( type.itemData == null ) {
                continue;
            }
            if( !names.containsKey(type.itemData.getValue0()) ) {
                names.put(type.itemData.getValue0(), iconRegister.registerIcon(type.itemData.getValue0()));
            }
            this.p_icons.put(type, names.get(type.itemData.getValue0()));
        }
        this.p_pegasusWings = iconRegister.registerIcon(ClaySoldiersMod.MOD_ID + ":doll_pegasus_wing");
    }

    @Override
    public int getRenderPasses(int metadata) {
        return 2;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass) {
        return pass == 0 || !isPegasus(stack) ? this.p_icons.get(getType(stack)) : this.p_pegasusWings;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromType(EnumHorseType type) {
        return this.p_icons.get(type);
    }

    @Override
    public void disrupt() { }
}
