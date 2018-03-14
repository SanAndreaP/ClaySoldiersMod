/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.item;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.doll.ItemDoll;
import de.sanandrew.mods.claysoldiers.api.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.soldier.ITeam;
import de.sanandrew.mods.claysoldiers.entity.soldier.EntityClaySoldier;
import de.sanandrew.mods.claysoldiers.registry.team.TeamRegistry;
import de.sanandrew.mods.claysoldiers.registry.team.Teams;
import de.sanandrew.mods.claysoldiers.util.CsmConfiguration;
import de.sanandrew.mods.claysoldiers.util.CsmCreativeTabs;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import de.sanandrew.mods.sanlib.lib.util.UuidUtils;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.BlockFence;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
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
import java.util.stream.Collectors;

public class ItemSoldier
        extends ItemDoll<EntityClaySoldier, ITeam>
{
    public ItemSoldier() {
        super(CsmConstants.ID, "doll_soldier", CsmCreativeTabs.DOLLS);
        this.maxStackSize = CsmConfiguration.soldierDollStackSize;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        if( CsmConfiguration.enableCauldronSoldierWash && !world.isRemote && player.canPlayerEdit(pos.offset(facing), facing, stack) && world.getBlockState(pos).getBlock() == Blocks.CAULDRON && hand != null ) {
            if( !player.isSneaking() && !UuidUtils.areUuidsEqual(TeamRegistry.INSTANCE.getTeam(stack).getId(), Teams.SOLDIER_CLAY) ) {
                IBlockState state = world.getBlockState(pos);
                int level = state.getValue(BlockCauldron.LEVEL);
                if( level > 0 ) {
                    player.setHeldItem(hand, TeamRegistry.INSTANCE.setTeam(stack.copy(), Teams.SOLDIER_CLAY));
                    player.inventoryContainer.detectAndSendChanges();

                    player.addStat(StatList.CAULDRON_USED);
                    Blocks.CAULDRON.setWaterLevel(world, pos, state, level - 1);

                    world.playSound(null, pos, SoundEvents.ENTITY_BOAT_PADDLE_WATER, SoundCategory.NEUTRAL, 1.0F, 1.0F);

                    return EnumActionResult.SUCCESS;
                }
            }

            return EnumActionResult.FAIL;
        } else {
            return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
        }
    }

    @Nonnull
    @Override
    public ITeam[] getTypes() {
        return TeamRegistry.INSTANCE.getTeams().toArray(new ITeam[0]);
    }

    @Nonnull
    @Override
    public ItemStack getTypeStack(ITeam type) {
        return TeamRegistry.INSTANCE.getNewTeamStack(1, type);
    }

    @Nonnull
    @Override
    public ITeam getType(ItemStack stack) {
        return TeamRegistry.INSTANCE.getTeam(stack);
    }

    @Nonnull
    @Override
    public EntityClaySoldier createEntity(World world, ITeam type, ItemStack newDollStack) {
        return new EntityClaySoldier(world, type, newDollStack);
    }

    @Override
    public SoundEvent getPlacementSound() {
        return SoundEvents.BLOCK_GRAVEL_BREAK;
    }

    @Override
    public boolean canBeResurrected(ItemStack stack, ISoldier<?> soldier) {
        return TeamRegistry.INSTANCE.getTeam(stack).getId().equals(soldier.getSoldierTeam().getId());
    }
}
