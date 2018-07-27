/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.compat.waila;

import de.sanandrew.mods.claysoldiers.entity.mount.EntityClayHorse;
import de.sanandrew.mods.claysoldiers.entity.mount.EntityGecko;
import de.sanandrew.mods.claysoldiers.entity.mount.EntityPegasus;
import de.sanandrew.mods.claysoldiers.entity.mount.EntityTurtle;
import de.sanandrew.mods.claysoldiers.entity.mount.EntityWoolBunny;
import de.sanandrew.mods.claysoldiers.entity.soldier.EntityClaySoldier;
import de.sanandrew.mods.claysoldiers.item.ItemRegistry;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaEntityAccessor;
import mcp.mobius.waila.api.IWailaEntityProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.entity.Entity;

import javax.annotation.Nonnull;
import java.util.List;

public class WailaEntityProvider
        implements IWailaEntityProvider
{
    public static final WailaEntityProvider INSTANCE = new WailaEntityProvider();

    public WailaEntityProvider() {}

    @Nonnull
    @Override
    public List<String> getWailaHead(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor, IWailaConfigHandler config) {
        currenttip.clear();
        if( entity instanceof EntityClaySoldier ) {
            currenttip.add(ItemRegistry.DOLL_SOLDIER.getTypeStack(((EntityClaySoldier) entity).getSoldierTeam()).getDisplayName());
        } else if( entity instanceof EntityPegasus ) {
            currenttip.add(ItemRegistry.DOLL_PEGASUS.getTypeStack(((EntityPegasus) entity).getType()).getDisplayName());
        } else if( entity instanceof EntityClayHorse ) {
            currenttip.add(ItemRegistry.DOLL_HORSE.getTypeStack(((EntityClayHorse) entity).getType()).getDisplayName());
        } else if( entity instanceof EntityTurtle ) {
            currenttip.add(ItemRegistry.DOLL_TURTLE.getTypeStack(((EntityTurtle) entity).getType()).getDisplayName());
        } else if( entity instanceof EntityWoolBunny ) {
            currenttip.add(ItemRegistry.DOLL_BUNNY.getTypeStack(((EntityWoolBunny) entity).getType()).getDisplayName());
        } else if( entity instanceof EntityGecko ) {
            currenttip.add(ItemRegistry.DOLL_GECKO.getTypeStack(((EntityGecko) entity).getType()).getDisplayName());
        }
        return currenttip;
    }

    public static void register(IWailaRegistrar registrar) {
        registrar.registerHeadProvider(INSTANCE, EntityClaySoldier.class);
        registrar.registerHeadProvider(INSTANCE, EntityClayHorse.class);
        registrar.registerHeadProvider(INSTANCE, EntityTurtle.class);
        registrar.registerHeadProvider(INSTANCE, EntityWoolBunny.class);
        registrar.registerHeadProvider(INSTANCE, EntityGecko.class);
    }
}
