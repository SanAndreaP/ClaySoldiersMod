/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.mods.claysoldiers.client.render.entity.RenderClayMan;
import de.sanandrew.mods.claysoldiers.client.render.entity.RenderHorseMount;
import de.sanandrew.mods.claysoldiers.client.render.entity.projectile.RenderBlockProjectile;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.entity.mount.EntityHorseMount;
import de.sanandrew.mods.claysoldiers.entity.projectile.EntityEmeraldChunk;
import de.sanandrew.mods.claysoldiers.entity.projectile.EntityFirechargeChunk;
import de.sanandrew.mods.claysoldiers.entity.projectile.EntityGravelChunk;
import de.sanandrew.mods.claysoldiers.entity.projectile.EntitySnowChunk;
import net.minecraft.init.Blocks;

public final class RegistryEntities
{
    @SideOnly(Side.CLIENT)
    public static void registerRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(EntityClayMan.class, new RenderClayMan());
        RenderingRegistry.registerEntityRenderingHandler(EntityHorseMount.class, new RenderHorseMount());
        RenderingRegistry.registerEntityRenderingHandler(EntityGravelChunk.class, new RenderBlockProjectile(Blocks.gravel));
        RenderingRegistry.registerEntityRenderingHandler(EntitySnowChunk.class, new RenderBlockProjectile(Blocks.snow));
        RenderingRegistry.registerEntityRenderingHandler(EntityFirechargeChunk.class, new RenderBlockProjectile(Blocks.lava)); //TODO: substitude until proper
                                                                                                                               //TODO: texture arrives
    }

    public static void registerEntities(Object mod) {
        int entityId = 0;

        cpw.mods.fml.common.registry.EntityRegistry.registerModEntity(EntityClayMan.class, "clayman", entityId++, mod, 64, 1, true);
        cpw.mods.fml.common.registry.EntityRegistry.registerModEntity(EntityHorseMount.class, "horsemount", entityId++, mod, 64, 1, true);
        cpw.mods.fml.common.registry.EntityRegistry.registerModEntity(EntityGravelChunk.class, "gravelchunk", entityId++, mod, 64, 1, true);
        cpw.mods.fml.common.registry.EntityRegistry.registerModEntity(EntitySnowChunk.class, "snowchunk", entityId++, mod, 64, 1, true);
        cpw.mods.fml.common.registry.EntityRegistry.registerModEntity(EntityFirechargeChunk.class, "firechunk", entityId++, mod, 64, 1, true);
        cpw.mods.fml.common.registry.EntityRegistry.registerModEntity(EntityEmeraldChunk.class, "emeraldchunk", entityId++, mod, 64, 1, true);
    }
}
