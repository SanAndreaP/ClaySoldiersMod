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
import de.sanandrew.mods.claysoldiers.util.CsmCreativeTabs;
import de.sanandrew.mods.sanlib.lib.util.ItemStackUtils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import java.util.Arrays;
import java.util.List;
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
        this.setHasSubtypes(true);
        this.maxStackSize = 1;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return getType(stack).damage;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        list.addAll(Arrays.stream(DisruptorType.VALUES).filter(type -> !type.name.equals("null")).map(type -> ItemDisruptor.setType(new ItemStack(this, 1), type)).collect(Collectors.toList()));
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack) + '.' + ItemDisruptor.getType(stack).name;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 0;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
        NBTTagCompound nbt = itemStackIn.getSubCompound("disruptor", true);
        long lastTimeMillis = nbt.getLong("lastActivated");
        long currTimeMillis = System.currentTimeMillis();

        if( lastTimeMillis + 2_000 < currTimeMillis ) {
            if( !worldIn.isRemote ) {
                AxisAlignedBB aabb = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 64.0D, 64.0D, 64.0D).offset(playerIn.posX, playerIn.posY, playerIn.posZ).offset(-32.0D, -32.0D, -32.0D);
                worldIn.getEntitiesWithinAABB(EntityCreature.class, aabb).stream().filter(entity -> entity instanceof IDisruptable).map(entity -> (IDisruptable) entity)
                       .collect(Collectors.toList()).forEach(IDisruptable::disrupt);
                nbt.setLong("lastActivated", currTimeMillis);
                if( itemStackIn.isItemStackDamageable() ) {
                    itemStackIn.damageItem(1, playerIn);
                }
            }

            return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
        } else {
            return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
        }
    }

    @SuppressWarnings("ConstantConditions")
    public static DisruptorType getType(ItemStack stack) {
        if( ItemStackUtils.isItem(stack, ItemRegistry.disruptor) ) {
            NBTTagCompound nbt = stack.getSubCompound("disruptor", false);
            if( nbt != null && nbt.hasKey("type", Constants.NBT.TAG_BYTE) ) {
                byte type = nbt.getByte("type");
                return type >= 0 && type < DisruptorType.VALUES.length ? DisruptorType.VALUES[type] : DisruptorType.UNKNOWN;
            }
        }

        return DisruptorType.UNKNOWN;
    }

    public static ItemStack setType(ItemStack stack, DisruptorType type) {
        if( ItemStackUtils.isItem(stack, ItemRegistry.disruptor) ) {
            NBTTagCompound nbt = stack.getSubCompound("disruptor", true);
            nbt.setByte("type", (byte) type.ordinal());
        }

        return stack;
    }

    public enum DisruptorType {
        CLAY("clay", 32),
        HARDENED("hardened", 128),
        OBSIDIAN("unbreaking", 0),
        UNKNOWN("null", 1);

        public final String name;
        public final int damage;

        public static final DisruptorType[] VALUES = DisruptorType.values();

        DisruptorType(String name, int damage) {
            this.name = name;
            this.damage = damage;
        }
    }
}
