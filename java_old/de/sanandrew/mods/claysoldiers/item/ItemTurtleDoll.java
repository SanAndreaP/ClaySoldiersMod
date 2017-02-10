/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.item;

import com.google.common.collect.Maps;
import de.sanandrew.mods.claysoldiers.entity.mount.EntityTurtleMount;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import de.sanandrew.mods.claysoldiers.util.IDisruptable;
import de.sanandrew.mods.claysoldiers.util.mount.EnumTurtleType;
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

public class ItemTurtleDoll
        extends Item
        implements IDisruptable
{
    private Map<EnumTurtleType, IIcon> icons;
    private IIcon baseIcon;

    public ItemTurtleDoll() {
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
    public static EntityTurtleMount spawnTurtle(World world, EnumTurtleType type, double x, double y, double z) {
        EntityTurtleMount jordan = new EntityTurtleMount(world, type);

        jordan.setLocationAndAngles(x, y, z, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F), 0.0F);
        jordan.rotationYawHead = jordan.rotationYaw;
        jordan.renderYawOffset = jordan.rotationYaw;
        world.spawnEntityInWorld(jordan);
        jordan.playSound("step.gravel", 1.0F, 1.0F);

        return jordan;
    }

    public static ItemStack setType(ItemStack stack, EnumTurtleType type) {
        if( type.itemData == null ) {
            return stack;
        }

        NBTTagCompound nbt = stack.hasTagCompound() ? stack.getTagCompound() : new NBTTagCompound();
        nbt.setString("type", type.toString());
        stack.setTagCompound(nbt);
        return stack;
    }

    public static EnumTurtleType getType(ItemStack stack) {
        NBTTagCompound itemNbt = stack.getTagCompound();
        if( itemNbt != null && itemNbt.hasKey("type") ) {
            return EnumTurtleType.valueOf(itemNbt.getString("type"));
        } else {
            return EnumTurtleType.COBBLE;
        }
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
                EntityTurtleMount dan = spawnTurtle(world, getType(stack), blockX + 0.5D, blockY + entityOffY, blockZ + 0.5D);

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
        return pass == 0 ? getType(stack).itemData.getValue2() : getType(stack).itemData.getValue1();
    }

    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void getSubItems(Item item, CreativeTabs creativeTab, List stacks) {
        for( EnumTurtleType type : EnumTurtleType.VALUES ) {
            if( type.itemData == null ) {
                continue;
            }

            ItemStack stack = new ItemStack(this, 1);
            setType(stack, type);
            stacks.add(stack);
        }
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        Map<String, IIcon> names = Maps.newHashMap();
        this.icons = Maps.newEnumMap(EnumTurtleType.class);
        for( EnumTurtleType type : EnumTurtleType.VALUES ) {
            if( type.itemData == null ) {
                continue;
            }
            if( !names.containsKey(type.itemData.getValue0()) ) {
                names.put(type.itemData.getValue0(), iconRegister.registerIcon(type.itemData.getValue0()));
            }
            this.icons.put(type, names.get(type.itemData.getValue0()));
        }
        this.baseIcon = iconRegister.registerIcon(ClaySoldiersMod.MOD_ID + ":doll_turtle_base");
    }

    @Override
    public int getRenderPasses(int metadata) {
        return 2;
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        return pass == 0 ? this.icons.get(getType(stack)) : this.baseIcon;
    }

    @Override
    public void disrupt() { }
}
