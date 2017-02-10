/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.world.gen;

import cpw.mods.fml.common.IWorldGenerator;
import de.sanandrew.mods.claysoldiers.util.ModConfig;
import de.sanandrew.mods.claysoldiers.world.gen.feature.WorldGenClayHut;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderFlat;
import net.minecraft.world.gen.ChunkProviderGenerate;

import java.util.Random;

public class WorldGenerator
        implements IWorldGenerator
{
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        if( chunkGenerator instanceof ChunkProviderGenerate || chunkGenerator instanceof ChunkProviderFlat ) {
            if( ModConfig.clayHutSpawnChance > 0 && random.nextInt(ModConfig.clayHutSpawnChance) == 0 ) {
                WorldGenClayHut.generate(world, random, chunkX, chunkZ);
            }
        }
    }
}
