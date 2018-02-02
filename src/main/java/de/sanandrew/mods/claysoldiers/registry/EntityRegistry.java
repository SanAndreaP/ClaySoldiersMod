/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.client.renderer.mount.RenderClayHorse;
import de.sanandrew.mods.claysoldiers.client.renderer.mount.RenderClayPegasus;
import de.sanandrew.mods.claysoldiers.client.renderer.mount.RenderGecko;
import de.sanandrew.mods.claysoldiers.client.renderer.mount.RenderTurtle;
import de.sanandrew.mods.claysoldiers.client.renderer.mount.RenderWoolBunny;
import de.sanandrew.mods.claysoldiers.client.renderer.projectile.RenderProjectile;
import de.sanandrew.mods.claysoldiers.client.renderer.soldier.RenderClaySoldier;
import de.sanandrew.mods.claysoldiers.entity.mount.EntityClayHorse;
import de.sanandrew.mods.claysoldiers.entity.mount.EntityGecko;
import de.sanandrew.mods.claysoldiers.entity.mount.EntityPegasus;
import de.sanandrew.mods.claysoldiers.entity.mount.EntityTurtle;
import de.sanandrew.mods.claysoldiers.entity.mount.EntityWoolBunny;
import de.sanandrew.mods.claysoldiers.entity.projectile.EntityProjectileEmerald;
import de.sanandrew.mods.claysoldiers.entity.projectile.EntityProjectileFirecharge;
import de.sanandrew.mods.claysoldiers.entity.projectile.EntityProjectileGravel;
import de.sanandrew.mods.claysoldiers.entity.projectile.EntityProjectileSnow;
import de.sanandrew.mods.claysoldiers.entity.soldier.EntityClaySoldier;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class EntityRegistry
{
    public static int entityCount = 0;

    public static void initialize() {
        net.minecraftforge.fml.common.registry.EntityRegistry.registerModEntity(new ResourceLocation(CsmConstants.ID, "claySoldier"), EntityClaySoldier.class,
                                                                                "claysoldier", entityCount++, ClaySoldiersMod.instance, 64, 1, true);

        net.minecraftforge.fml.common.registry.EntityRegistry.registerModEntity(new ResourceLocation(CsmConstants.ID, "gravelProjectile"), EntityProjectileGravel.class,
                                                                                "gravelprojectile", entityCount++, ClaySoldiersMod.instance, 64, 1, true);
        net.minecraftforge.fml.common.registry.EntityRegistry.registerModEntity(new ResourceLocation(CsmConstants.ID, "snowProjectile"), EntityProjectileSnow.class,
                                                                                "snowprojectile", entityCount++, ClaySoldiersMod.instance, 64, 1, true);
        net.minecraftforge.fml.common.registry.EntityRegistry.registerModEntity(new ResourceLocation(CsmConstants.ID, "fireProjectile"), EntityProjectileFirecharge.class,
                                                                                "fireprojectile", entityCount++, ClaySoldiersMod.instance, 64, 1, true);
        net.minecraftforge.fml.common.registry.EntityRegistry.registerModEntity(new ResourceLocation(CsmConstants.ID, "emeraldProjectile"), EntityProjectileEmerald.class,
                                                                                "emeraldprojectile", entityCount++, ClaySoldiersMod.instance, 64, 1, true);

        net.minecraftforge.fml.common.registry.EntityRegistry.registerModEntity(new ResourceLocation(CsmConstants.ID, "mountHorse"), EntityClayHorse.class,
                                                                                "mounthorse", entityCount++, ClaySoldiersMod.instance, 64, 1, true);
        net.minecraftforge.fml.common.registry.EntityRegistry.registerModEntity(new ResourceLocation(CsmConstants.ID, "mountPegasus"), EntityPegasus.class,
                                                                                "mountpegasus", entityCount++, ClaySoldiersMod.instance, 64, 1, true);
        net.minecraftforge.fml.common.registry.EntityRegistry.registerModEntity(new ResourceLocation(CsmConstants.ID, "mountturtle"), EntityTurtle.class,
                                                                                "mountturtle", entityCount++, ClaySoldiersMod.instance, 64, 1, true);
        net.minecraftforge.fml.common.registry.EntityRegistry.registerModEntity(new ResourceLocation(CsmConstants.ID, "mountbunny"), EntityWoolBunny.class,
                                                                                "mountbunny", entityCount++, ClaySoldiersMod.instance, 64, 1, true);
        net.minecraftforge.fml.common.registry.EntityRegistry.registerModEntity(new ResourceLocation(CsmConstants.ID, "mountgecko"), EntityGecko.class,
                                                                                "mountgecko", entityCount++, ClaySoldiersMod.instance, 64, 1, true);
    }

    @SideOnly(Side.CLIENT)
    public static void registerRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(EntityClaySoldier.class, RenderClaySoldier::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityProjectileGravel.class, RenderProjectile.Gravel::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityProjectileSnow.class, RenderProjectile.Snow::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityProjectileFirecharge.class, RenderProjectile.Firecharge::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityProjectileEmerald.class, RenderProjectile.Emerald::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityClayHorse.class, RenderClayHorse::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityPegasus.class, RenderClayPegasus::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTurtle.class, RenderTurtle::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityWoolBunny.class, RenderWoolBunny::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityGecko.class, RenderGecko::new);
    }
}
