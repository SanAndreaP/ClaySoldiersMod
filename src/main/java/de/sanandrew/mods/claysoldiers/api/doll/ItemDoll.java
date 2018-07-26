/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.api.doll;

import de.sanandrew.mods.claysoldiers.api.entity.soldier.ISoldier;
import net.minecraft.block.BlockFence;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * This class provides the foundation for Items that should spawn entities of type {@link E} with a specific type {@link T}
 * @param <E> the type of Entity this Item should spawn
 * @param <T> the type of Type that defines the Entity
 */
public abstract class ItemDoll<E extends EntityLiving, T extends IDollType>
        extends Item
{
    public static final Random RNG = new Random();

    public ItemDoll(String modId, String name, CreativeTabs tab) {
        super();
        this.setCreativeTab(tab);
        this.setUnlocalizedName(modId + ':' + name);
        this.setMaxDamage(0);
        this.maxStackSize = 16;
        this.setRegistryName(modId, name);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
        if( this.isInCreativeTab(tab) ) {
            list.addAll(Arrays.stream(getTypes()).filter(IDollType::isVisible).map(this::getTypeStack).collect(Collectors.toList()));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack) + '.' + getType(stack).getName().toLowerCase(Locale.ROOT);
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

            List<E> spawns = this.spawnEntities(world, getType(stack), player.isSneaking() ? 1 : stack.getCount(),
                                                pos.getX() + 0.5D, pos.getY() + yShift, pos.getZ() + 0.4D + RNG.nextFloat() * 0.2D, stack);

            for( E trevor : spawns ) {
                if( trevor != null ) {
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

    public List<E> spawnEntities(World world, T type, final int count, double x, double y, double z, @Nonnull ItemStack dollStack) {
        if( type.isValid() ) {
            List<E> livings = new ArrayList<>();

            for( int i = 0; i < count; i++ ) {
                double xs = x - 0.1D + RNG.nextFloat() * 0.02D;
                double zs = z - 0.1D + RNG.nextFloat() * 0.02D;

                ItemStack newDollStack = dollStack = dollStack.copy();
                newDollStack.setCount(1);

                E aleks = this.createEntity(world, type, newDollStack);
                aleks.setLocationAndAngles(xs, y, zs, MathHelper.wrapDegrees(world.rand.nextFloat() * 360.0F), 0.0F);
                aleks.rotationYawHead = aleks.rotationYaw;
                aleks.renderYawOffset = aleks.rotationYaw;
                aleks.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(aleks)), null);
                if( dollStack.hasDisplayName() ) {
                    aleks.setCustomNameTag(dollStack.getDisplayName());
                }
                world.spawnEntity(aleks);
                float pitch = (RNG.nextFloat() - RNG.nextFloat()) * 0.2F + 1.0F;
                world.playSound(null, xs, y, zs, this.getPlacementSound(), SoundCategory.NEUTRAL, 1.0F, pitch);

                livings.add(aleks);
            }

            return livings;
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * Returns an array of types defined in the {@link IDollType} class provided.<br>
     * It is preferred to return a static array of {@link T} set from {@code Enum.values()}, for example return {@code EnumTypes.VALUES;},
     * in which {@code public static final EnumTypes[] VALUES = values();} is defined within {@code EnumTypes}.
     * @return an array of types
     */
    @Nonnull
    public abstract T[] getTypes();

    /**
     * Returns a new ItemStack of this item containing the provided type with a size of 1, saved either with NBT tags, damage value or whatever works for you.
     * @param type the type {@link T} to be saved into the new ItemStack
     * @return a new ItemStack of size 1
     */
    @Nonnull
    public abstract ItemStack getTypeStack(T type);

    /**
     * Returns the type saved inside the ItemStack provided. If no type is saved in the ItemStack or it is invalid, a default or "invalid" type should be returned.
     * @param stack the ItemStack containing the type
     * @return a type saved in the ItemStack or a default type
     */
    @Nonnull
    public abstract T getType(ItemStack stack);

    /**
     * Returns a new instance of the Entity {@link E} this Item should spawn.
     * @param world the world the Entity should spawn in
     * @param type the type {@link T} provided by this Item
     * @param newDollStack the ItemStack used to spawn the Entity with
     * @return a new Entity instance
     */
    @Nonnull
    public abstract E createEntity(World world, T type, ItemStack newDollStack);

    @Nonnull
    public abstract SoundEvent getPlacementSound();

    public boolean canBeResurrected(ItemStack stack, ISoldier<?> soldier) {
        return true;
    }
}
