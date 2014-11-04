/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.world.gen.feature;

import com.google.common.collect.ImmutableList;
import cpw.mods.fml.common.FMLLog;
import de.sanandrew.core.manpack.util.javatuples.Triplet;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.item.ItemClayManDoll;
import de.sanandrew.mods.claysoldiers.util.ModConfig;
import de.sanandrew.mods.claysoldiers.util.RegistryBlocks;
import de.sanandrew.mods.claysoldiers.util.RegistryItems;
import de.sanandrew.mods.claysoldiers.util.soldier.ClaymanTeam;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgrades;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityFlowerPot;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;
import java.util.Random;

public class WorldGenClayHut
        extends WorldGenerator
{
    public static final String CHEST_CONTENT = "clayHut";

    private static final ImmutableList<Triplet<String, Integer, ItemStack>> POSSIBLE_TEAMS;

    static {
        ChestGenHooks.getInfo(CHEST_CONTENT).setMin(4);
        ChestGenHooks.getInfo(CHEST_CONTENT).setMax(8);

        ChestGenHooks.addItem(CHEST_CONTENT, new WeightedRandomChestContent(Items.clay_ball, 0, 8, 32, 15));
        ChestGenHooks.addItem(CHEST_CONTENT, new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.clay), 0, 1, 2, 15));
        ChestGenHooks.addItem(CHEST_CONTENT, new WeightedRandomChestContent(RegistryItems.disruptor, 0, 1, 1, 10));
        ChestGenHooks.addItem(CHEST_CONTENT, new WeightedRandomChestContent(RegistryItems.dollBrick, 0, 1, 2, 10));
        ChestGenHooks.addItem(CHEST_CONTENT, new WeightedSoldierDoll(1, 6, 10));
        ChestGenHooks.addItem(CHEST_CONTENT, new WeightedRandomChestContent(RegistryItems.dollHorseMount, 0, 1, 8, 5));
        ChestGenHooks.addItem(CHEST_CONTENT, new WeightedRandomChestContent(RegistryItems.dollTurtleMount, 0, 1, 8, 5));
        ChestGenHooks.addItem(CHEST_CONTENT, new WeightedRandomChestContent(RegistryItems.dollBunnyMount, 0, 1, 8, 5));
        ChestGenHooks.addItem(CHEST_CONTENT, new WeightedRandomChestContent(Item.getItemFromBlock(RegistryBlocks.clayNexus), 0, 1, 1, 1));

        POSSIBLE_TEAMS = ImmutableList.of(
                Triplet.with("red", 14, new ItemStack(Blocks.red_flower, 1, 0)),
                Triplet.with("yellow", 4, new ItemStack(Blocks.yellow_flower, 1, 0)),
                Triplet.with("green", 13, new ItemStack(Blocks.cactus, 1, 0)),
                Triplet.with("blue", 11, new ItemStack(Blocks.red_flower, 1, 1)),
                Triplet.with("pink", 6, new ItemStack(Blocks.red_flower, 1, 7)),
                Triplet.with("purple", 10, new ItemStack(Blocks.red_flower, 1, 2))
        );
    }

    @Override
    public boolean generate(World world, Random rand, int x, int y, int z) {
        Triplet<String, Integer, ItemStack> chosenTeam = POSSIBLE_TEAMS.get(rand.nextInt(POSSIBLE_TEAMS.size()));
        genFloorAndRoof(world, x, y, z, chosenTeam.getValue1());
        genWalls(world, x, y, z, chosenTeam.getValue1());
        carveRoom(world, x, y, z);
        setDoorAndInteriors(world, rand, x, y, z, chosenTeam.getValue1(), chosenTeam.getValue2());
        placeSoldiers(world, rand, x, y, z, chosenTeam.getValue0());
        return true;
    }

    public static void generate(World world, Random rand, int chunkX, int chunkZ) {
        int x = (chunkX << 4) + rand.nextInt(16);
        int z = (chunkZ << 4) + rand.nextInt(16);
        int y = getSuitableY(world, x, z);

        if( y > 0 ) {
            FMLLog.info("Structure WorldGenClayHut: %d | %d | %d", x, y, z);
            (new WorldGenClayHut()).generate(world, rand, x, y, z);
        }
    }

    private static int getSuitableY(World world, int x, int z) {
        BiomeGenBase genBiome = world.getBiomeGenForCoords(x, z);
        for( int i = 196; i >= 48; i-- ) {
            Block block = world.getBlock(x, i, z);
            if( block != null && block == genBiome.topBlock ) {
                return i;
            }
        }

        return 0;
    }

    private static void genFloorAndRoof(World world, int x, int y, int z, int color) {
        for( int i = -1; i <= 1; i++ ) {
            for( int j = -1; j <= 1; j++ ) {
                world.setBlock(x + i, y + 3, z + j, Blocks.stained_hardened_clay, color, 2);

                int k = y;
                do {
                    world.setBlock(x + i, k, z + j, Blocks.clay, 0, 2);
                } while( world.isAirBlock(x + i, --k, z + j) );
            }
        }
    }

    private static void genWalls(World world, int x, int y, int z, int color) {
        for( int i = -2; i <= 2; i++ ) {
            for( int j = -2; j <= 2; j++ ) {
                int k = y + 2;
                do {
                    if( (Math.abs(i) != 2 || Math.abs(j) != 2) && world.getBlock(x + i, k, z + j) != Blocks.clay ) {
                        world.setBlock(x + i, k, z + j, Blocks.stained_hardened_clay, color, 2);
                    }
                } while( --k >= y || world.isAirBlock(x + i, k, z + j) );
            }
        }
    }

    private static void carveRoom(World world, int x, int y, int z) {
        for( int i = -1; i <= 1; i++ ) {
            for( int j = -1; j <= 1; j++ ) {
                world.setBlock(x + i, y + 1, z + j, Blocks.air, 0, 2);
                world.setBlock(x + i, y + 2, z + j, Blocks.air, 0, 2);
            }
        }
    }

    private static void setDoorAndInteriors(World world, Random rand, int x, int y, int z, int color, ItemStack flower) {
        ForgeDirection doorDir = ForgeDirection.getOrientation(rand.nextInt(4) + 2);

        world.setBlock(x + doorDir.offsetX * 2, y, z + doorDir.offsetZ * 2, Blocks.clay, 0, 2);
        world.setBlock(x + doorDir.offsetX * 2, y + 1, z + doorDir.offsetZ * 2, Blocks.air, 0, 2);
        world.setBlock(x + doorDir.offsetX * 2, y + 2, z + doorDir.offsetZ * 2, Blocks.air, 0, 2);
        world.setBlock(x + doorDir.offsetX * 2, y + 3, z + doorDir.offsetZ * 2, Blocks.stained_hardened_clay, color, 2);

        for( int i = 2; i < 6; i++ ) {
            if( i != doorDir.ordinal() ) {
                ForgeDirection windowDir = ForgeDirection.getOrientation(i);

                world.setBlock(x + windowDir.offsetX * 2, y + 1, z + windowDir.offsetZ * 2, Blocks.stained_glass, color, 2);
            }
        }

        ForgeDirection torchDir = doorDir.getOpposite();
        world.setBlock(x + torchDir.offsetX, y + 2, z + torchDir.offsetZ, Blocks.torch, 0, 3);
        world.setBlock(x + torchDir.offsetX + torchDir.offsetZ, y + 1, z + torchDir.offsetZ + torchDir.offsetX, Blocks.chest, 0, 3);
        insertChestLoot(world, rand, x + torchDir.offsetX + torchDir.offsetZ, y + 1, z + torchDir.offsetZ + torchDir.offsetX);
        world.setBlock(x + torchDir.offsetX - torchDir.offsetZ, y + 1, z + torchDir.offsetZ - torchDir.offsetX, Blocks.flower_pot, 0, 3);
        insertFlower(world, x + torchDir.offsetX - torchDir.offsetZ, y + 1, z + torchDir.offsetZ - torchDir.offsetX, flower);
    }

    private static void placeSoldiers(World world, Random rand, int x, int y, int z, String team) {
        int soldiers = rand.nextInt(2) + 2;
        boolean zombified = ModConfig.clayHutZombieChance > 0 && rand.nextInt(ModConfig.clayHutZombieChance) == 0;
        if( zombified ) {
            world.setBlock(x, y + 4, z, Blocks.redstone_torch, 0, 3);
        }

        for( int i = 0; i < soldiers; i++ ) {
            double sldX = x + rand.nextFloat();
            double sldY = y + 1.5D;
            double sldZ = z + rand.nextFloat();

            EntityClayMan jack = new EntityClayMan(world, team);
            jack.setPositionAndRotation(sldX, sldY, sldZ, rand.nextFloat() * (float) Math.PI, 0.0F);

            if( zombified ) {
                jack.addUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_ENDERPEARL));
            } else {
                jack.addUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_WHEAT));
            }

            jack.nexusSpawn = true;

            world.spawnEntityInWorld(jack);
        }
    }

    private static void insertChestLoot(World world, Random rand, int chestX, int chestY, int chestZ) {
        TileEntityChest chest = (TileEntityChest) world.getTileEntity(chestX, chestY, chestZ);
        if( chest != null ) {
            WeightedRandomChestContent.generateChestContents(rand, ChestGenHooks.getItems(CHEST_CONTENT, rand), chest, ChestGenHooks.getCount(CHEST_CONTENT, rand));
        }
    }

    private static void insertFlower(World world, int potX, int potY, int potZ, ItemStack flower) {
        TileEntityFlowerPot pot = (TileEntityFlowerPot) world.getTileEntity(potX, potY, potZ);
        if( pot == null ) {
            pot = new TileEntityFlowerPot();
            world.setTileEntity(potX, potY, potZ, pot);
        }
        pot.func_145964_a(flower.getItem(), flower.getItemDamage());
        pot.markDirty();
        world.markBlockForUpdate(potX, potY, potZ);
    }

    private static final class WeightedSoldierDoll
            extends WeightedRandomChestContent
    {
        public WeightedSoldierDoll(int min, int max, int weight) {
            super(RegistryItems.dollSoldier, 0, min, max, weight);
        }

        @Override
        protected ItemStack[] generateChestContent(Random random, IInventory newInventory) {
            ItemStack[] stacks = super.generateChestContent(random, newInventory);
            List<String> teams = ClaymanTeam.getTeamNamesForDolls();
            for( ItemStack doll : stacks ) {
                ItemClayManDoll.setTeamForItem(teams.get(random.nextInt(teams.size())), doll);
            }

            return stacks;
        }
    }
}
