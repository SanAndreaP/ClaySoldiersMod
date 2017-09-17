/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.item;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.entity.mount.EntityClayHorse;
import de.sanandrew.mods.claysoldiers.registry.ItemRegistry;
import de.sanandrew.mods.claysoldiers.registry.mount.EnumClayHorseType;
import de.sanandrew.mods.claysoldiers.util.CsmCreativeTabs;
import de.sanandrew.mods.sanlib.lib.util.ItemStackUtils;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import net.minecraft.block.BlockFence;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ItemHorseMount
        extends Item
{
    public ItemHorseMount() {
        super();
        this.setCreativeTab(CsmCreativeTabs.DOLLS);
        this.setUnlocalizedName(CsmConstants.ID + ":doll_horse");
        this.setMaxDamage(0);
        this.maxStackSize = 16;
        this.setRegistryName(CsmConstants.ID, "doll_horse");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
        if( this.isInCreativeTab(tab) ) {
            list.addAll(Arrays.stream(EnumClayHorseType.VALUES).filter(type -> type.visible).map(ItemHorseMount::getTypeStack).collect(Collectors.toList()));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack) + '.' + getType(stack).name().toLowerCase();
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        if( !player.canPlayerEdit(pos.offset(facing), facing, stack) ) {
            return EnumActionResult.FAIL;
        } else if( world.isRemote ) {
            return EnumActionResult.SUCCESS;
        } else {
            IBlockState iblockstate = world.getBlockState(pos);

            pos = pos.offset(facing);
            double yShift = 0.0D;

            if( facing == EnumFacing.UP && iblockstate.getBlock() instanceof BlockFence ) {
                yShift = 0.5D;
            }

            EntityClayHorse[] horses = spawnHorses(world, getType(stack), player.isSneaking() ? 1 : stack.getCount(),
                                                   pos.getX() + 0.5D, pos.getY() + yShift, pos.getZ() + 0.4D + MiscUtils.RNG.randomFloat() * 0.2D, stack);

            for( EntityClayHorse trevor : horses ) {
                if( trevor != null ) {
                    if( stack.hasDisplayName() ) {
                        trevor.setCustomNameTag(stack.getDisplayName());
                    }

                    stack.shrink(1);
                }
            }

            if( hand != null && player.capabilities.isCreativeMode ) {
                if( stack.getCount() < 1 ) {
                    player.setHeldItem(hand, ItemStack.EMPTY);
                } else {
                    player.setHeldItem(hand, stack.copy());
                }

                player.inventoryContainer.detectAndSendChanges();
            }

            return EnumActionResult.SUCCESS;
        }
    }

    public static EntityClayHorse[] spawnHorses(World world, EnumClayHorseType type, final int count, double x, double y, double z, ItemStack dollStack) {
        if( type != EnumClayHorseType.UNKNOWN ) {
            EntityClayHorse[] horses = new EntityClayHorse[count];

            for( int i = 0; i < count; i++ ) {
                double xs = x - 0.1D + MiscUtils.RNG.randomFloat() * 0.02D;
                double zs = z - 0.1D + MiscUtils.RNG.randomFloat() * 0.02D;

                ItemStack newDollStack = ItemStack.EMPTY;
                if( dollStack != null ) {
                    newDollStack = dollStack.copy();
                    newDollStack.setCount(1);
                }
                EntityClayHorse aleks = new EntityClayHorse(world, type, newDollStack);
                aleks.setLocationAndAngles(xs, y, zs, MathHelper.wrapDegrees(world.rand.nextFloat() * 360.0F), 0.0F);
                aleks.rotationYawHead = aleks.rotationYaw;
                aleks.renderYawOffset = aleks.rotationYaw;
                aleks.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(aleks)), null);
                world.spawnEntity(aleks);
                aleks.playLivingSound();

                horses[i] = aleks;
            }

            return horses;
        } else {
            return new EntityClayHorse[0];
        }
    }

    public static EnumClayHorseType getType(ItemStack stack) {
        if( ItemStackUtils.isItem(stack, ItemRegistry.DOLL_HORSE) ) {
            NBTTagCompound nbt = stack.getSubCompound("dollHorse");
            if( nbt != null && nbt.hasKey("type", Constants.NBT.TAG_INT) ) {
                return EnumClayHorseType.VALUES[nbt.getInteger("type")];
            }
        }

        return EnumClayHorseType.UNKNOWN;
    }

    public static ItemStack getTypeStack(EnumClayHorseType type) {
        ItemStack stack = new ItemStack(ItemRegistry.DOLL_HORSE, 1);
        stack.getOrCreateSubCompound("dollHorse").setInteger("type", type.ordinal());
        return stack;
    }
}
