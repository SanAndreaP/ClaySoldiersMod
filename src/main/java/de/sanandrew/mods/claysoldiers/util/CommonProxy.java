/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util;

import de.sanandrew.mods.claysoldiers.entity.EntityClaySoldier;
import de.sanandrew.mods.claysoldiers.entity.projectile.EntityProjectileFirecharge;
import de.sanandrew.mods.claysoldiers.entity.projectile.EntityProjectileGravel;
import de.sanandrew.mods.claysoldiers.entity.projectile.EntityProjectileSnow;
import de.sanandrew.mods.claysoldiers.event.EntityFallEventHandler;
import de.sanandrew.mods.claysoldiers.event.LivingAttackEventHandler;
import de.sanandrew.mods.claysoldiers.event.LivingSetAttackTargetEventHandler;
import de.sanandrew.mods.claysoldiers.network.PacketManager;
import de.sanandrew.mods.claysoldiers.network.packet.PacketParticle;
import de.sanandrew.mods.sanlib.lib.Tuple;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import java.util.Arrays;
import java.util.List;

public class CommonProxy
        implements IGuiHandler
{
    public static int entityCount = 0;

    public void preInit(FMLPreInitializationEvent event) {
        EntityRegistry.registerModEntity(EntityClaySoldier.class, "claySoldier", entityCount++, ClaySoldiersMod.instance, 64, 1, true);

        EntityRegistry.registerModEntity(EntityProjectileGravel.class, "gravelProjectile", entityCount++, ClaySoldiersMod.instance, 64, 1, true);
        EntityRegistry.registerModEntity(EntityProjectileSnow.class, "snowProjectile", entityCount++, ClaySoldiersMod.instance, 64, 1, true);
        EntityRegistry.registerModEntity(EntityProjectileFirecharge.class, "fireProjectile", entityCount++, ClaySoldiersMod.instance, 64, 1, true);

        MinecraftForge.EVENT_BUS.register(new EntityFallEventHandler());
        MinecraftForge.EVENT_BUS.register(new LivingAttackEventHandler());
        MinecraftForge.EVENT_BUS.register(new LivingSetAttackTargetEventHandler());
    }

    public void init(FMLInitializationEvent event) {

    }

    public void postInit(FMLPostInitializationEvent event) {

    }

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    public void spawnParticle(EnumParticle particle, int dim, double x, double y, double z, Object... additData) {
        List<Object> objList = Arrays.asList(dim, x, y, z);
        objList.addAll(Arrays.asList(additData));

        PacketManager.sendToAllAround(new PacketParticle(particle, new Tuple(objList.toArray())), dim, x, y, z, 64.0D);
    }

    public EntityPlayer getClientPlayer() {
        return null;
    }
}
