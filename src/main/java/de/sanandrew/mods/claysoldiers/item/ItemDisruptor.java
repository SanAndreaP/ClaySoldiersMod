/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.item;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.IDisruptable;
import de.sanandrew.mods.claysoldiers.registry.ItemRegistry;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import de.sanandrew.mods.claysoldiers.util.CsmConfig;
import de.sanandrew.mods.claysoldiers.util.CsmCreativeTabs;
import de.sanandrew.mods.claysoldiers.util.EnumParticle;
import de.sanandrew.mods.claysoldiers.util.Lang;
import de.sanandrew.mods.sanlib.lib.util.ItemStackUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

public class ItemDisruptor
        extends Item
{
    private static final IItemPropertyGetter DISRUPTOR_TEX = (stack, worldIn, entityIn) -> getType(stack).ordinal();

    public ItemDisruptor() {
        super();
        this.setCreativeTab(CsmCreativeTabs.MISC);
        this.setUnlocalizedName(CsmConstants.ID + ":disruptor");
        this.addPropertyOverride(new ResourceLocation("disruptorType"), DISRUPTOR_TEX);
        this.maxStackSize = 1;
        this.setRegistryName(CsmConstants.ID, "disruptor");
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return getType(stack).damage;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
        if( this.isInCreativeTab(tab) ) {
            list.addAll(Arrays.stream(DisruptorType.VALUES).filter(type -> !type.name.equals("null")).map(type -> ItemDisruptor.setType(new ItemStack(this, 1), type))
                              .collect(Collectors.toList()));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack) + '.' + ItemDisruptor.getType(stack).name;
    }

    public String getTranslateKey(ItemStack stack, String subKey) {
        return super.getUnlocalizedName(stack) + '.' + subKey;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        IDisruptable.DisruptState state = getState(stack);
        tooltip.add(Lang.translate(getTranslateKey(stack, "tooltip"), Lang.translate(getTranslateKey(stack, "state." + state.name().toLowerCase(Locale.ROOT)))));
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 0;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if( disruptAction(world, stack, new Vec3d(player.posX, player.posY, player.posZ), player) ) {
            return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
        } else {
            return super.onItemRightClick(world, player, hand);
        }
    }

    public static boolean disruptAction(World world, ItemStack stack, Vec3d pos, @Nullable EntityPlayer player) {
        NBTTagCompound nbt = stack.getOrCreateSubCompound("disruptor");
        long lastTimeMillis = nbt.getLong("lastActivated");
        long currTimeMillis = System.currentTimeMillis();

        if( lastTimeMillis + 2_000 < currTimeMillis ) {
            if( !world.isRemote ) {
                final IDisruptable.DisruptState state = getState(stack);

                if( state != IDisruptable.DisruptState.CLAY && canBreak(player, state) ) {
                    AxisAlignedBB aabb = new AxisAlignedBB(-32.0D, -32.0D, -32.0D, 32.0D, 32.0D, 32.0D).offset(pos.x, pos.y, pos.z);
                    world.getEntitiesWithinAABB(EntityCreature.class, aabb).stream().filter(entity -> entity instanceof IDisruptable).map(entity -> (IDisruptable) entity)
                         .forEach(disruptable -> {
                             if( state == IDisruptable.DisruptState.ALL || state == IDisruptable.DisruptState.ALL_DOLLS || disruptable.getDisruptableState() == state ) {
                                 disruptable.disrupt();
                             }
                         });
                    if( stack.isItemStackDamageable() ) {
                        if( player != null ) {
                            stack.damageItem(1, player);
                        } else {
                            damageItemNE(world, stack, pos);
                        }
                    }
                }

                if( ItemStackUtils.isValid(stack) && (state == IDisruptable.DisruptState.CLAY || state == IDisruptable.DisruptState.ALL)
                    && canBreak(player, IDisruptable.DisruptState.CLAY) )
                {
                    AxisAlignedBB aabb = new AxisAlignedBB(-4.0D, -4.0D, -4.0D, 4.0D, 4.0D, 4.0D).offset(pos.x, pos.y, pos.z);
                    clayLoop:
                    for( int x = MathHelper.floor(aabb.minX), maxX = MathHelper.ceil(aabb.maxX); x <= maxX; x++ ) {
                        for( int y = MathHelper.floor(aabb.minY), maxY = MathHelper.ceil(aabb.maxY); y <= maxY; y++ ) {
                            for( int z = MathHelper.floor(aabb.minZ), maxZ = MathHelper.ceil(aabb.maxZ); z <= maxZ; z++ ) {
                                if( !ItemStackUtils.isValid(stack) ) {
                                    break clayLoop;
                                }

                                BlockPos blockPos = new BlockPos(x, y, z);
                                if( world.isBlockLoaded(blockPos) && world.canMineBlockBody(player, blockPos) ) {
                                    IBlockState blockState = world.getBlockState(blockPos);
                                    Block block = blockState.getBlock();
                                    if( block == Blocks.CLAY && (player == null || block.canHarvestBlock(world, blockPos, player)) ) {
                                        world.playEvent(2001, blockPos, Block.getStateId(blockState));
                                        if( player != null ) {
                                            block.removedByPlayer(blockState, world, blockPos, player, true);
                                            block.harvestBlock(world, player, blockPos, blockState, null, stack);
                                        } else {
                                            world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 3);
                                            int fortune = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
                                            block.dropBlockAsItem(world, blockPos, blockState, fortune);
                                        }

                                        if( stack.isItemStackDamageable() ) {
                                            if( player != null ) {
                                                stack.damageItem(1, player);
                                            } else {
                                                damageItemNE(world, stack, pos);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                nbt.setLong("lastActivated", currTimeMillis);
            }

            return true;
        } else {
            return false;
        }
    }

    private static boolean canBreak(@Nullable EntityPlayer player, IDisruptable.DisruptState state) {
        if( state != IDisruptable.DisruptState.CLAY ) {
            return CsmConfig.BlocksAndItems.Disruptor.enableAutomatedDollDisrupt || (player != null && !(player instanceof FakePlayer));
        } else {
            if( !CsmConfig.BlocksAndItems.Disruptor.enableClayBlockDisruptMode ) {
                return false;
            }

            return CsmConfig.BlocksAndItems.Disruptor.enableAutomatedClayBlockDisrupt || (player != null && !(player instanceof FakePlayer));
        }
    }

    private static void damageItemNE(World world, ItemStack stack, Vec3d pos) {
        int stackId = Item.getIdFromItem(stack.getItem());
        int damage = stack.getItemDamage();
        String nbt = stack.hasTagCompound() ? Objects.requireNonNull(stack.getTagCompound()).toString() : "";
        if( stack.attemptDamageItem(1, world.rand, null) ) {
            world.playSound(null, pos.x, pos.y, pos.z, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.BLOCKS, 0.8F, 0.8F + world.rand.nextFloat() * 0.4F);
            ClaySoldiersMod.proxy.spawnParticle(EnumParticle.ITEM_BREAK, world.provider.getDimension(), pos.x, pos.y, pos.z, stackId, damage, nbt);
            stack.shrink(1);
            stack.setItemDamage(0);
        }
    }

    @SuppressWarnings("ConstantConditions")
    public static DisruptorType getType(ItemStack stack) {
        if( ItemStackUtils.isItem(stack, ItemRegistry.DISRUPTOR) ) {
            NBTTagCompound nbt = stack.getSubCompound("disruptor");
            if( nbt != null && nbt.hasKey("type", Constants.NBT.TAG_ANY_NUMERIC) ) {
                int type = nbt.getByte("type");
                return type >= 0 && type < DisruptorType.VALUES.length ? DisruptorType.VALUES[type] : DisruptorType.UNKNOWN;
            }
        }

        return DisruptorType.UNKNOWN;
    }

    public static ItemStack setType(ItemStack stack, DisruptorType type) {
        if( ItemStackUtils.isItem(stack, ItemRegistry.DISRUPTOR) ) {
            NBTTagCompound nbt = stack.getOrCreateSubCompound("disruptor");
            nbt.setByte("type", (byte) type.ordinal());
        }

        return stack;
    }

    public static IDisruptable.DisruptState getState(ItemStack stack) {
        if( ItemStackUtils.isItem(stack, ItemRegistry.DISRUPTOR) ) {
            NBTTagCompound nbt = stack.getSubCompound("disruptor");
            if( nbt != null && nbt.hasKey("state", Constants.NBT.TAG_ANY_NUMERIC) ) {
                int type = nbt.getByte("state");
                return type >= 0 && type < IDisruptable.DisruptState.VALUES.length ? IDisruptable.DisruptState.VALUES[type] : IDisruptable.DisruptState.ALL_DOLLS;
            }
        }

        return IDisruptable.DisruptState.ALL_DOLLS;
    }

    @SuppressWarnings("UnusedReturnValue")
    public static ItemStack setState(ItemStack stack, IDisruptable.DisruptState type) {
        if( ItemStackUtils.isItem(stack, ItemRegistry.DISRUPTOR) ) {
            NBTTagCompound nbt = stack.getOrCreateSubCompound("disruptor");
            nbt.setByte("state", (byte) type.ordinal());
        }

        return stack;
    }

    @CsmConfig.Category(value = CsmConfig.BlocksAndItems.Disruptor.SUBCAT_DISRUPTOR, inherit = true)
    public enum DisruptorType {
        CLAY("clay", 32),
        HARDENED("hardened", 128),
        OBSIDIAN("unbreaking", 0),

        @CsmConfig.EnumExclude
        UNKNOWN("null", 1);

        @CsmConfig.Value(value = "%sDisruptorDurability", comment = "Durability of the %s disruptor. 0 durability = unbreakable")
        public int damage;
        public final String name;

        public static final DisruptorType[] VALUES = DisruptorType.values();

        DisruptorType(String name, int damage) {
            this.name = name;
            this.damage = damage;
        }
    }
}
