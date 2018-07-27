/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.compat.top;

import de.sanandrew.mods.claysoldiers.entity.mount.EntityClayHorse;
import de.sanandrew.mods.claysoldiers.entity.mount.EntityGecko;
import de.sanandrew.mods.claysoldiers.entity.mount.EntityPegasus;
import de.sanandrew.mods.claysoldiers.entity.mount.EntityTurtle;
import de.sanandrew.mods.claysoldiers.entity.mount.EntityWoolBunny;
import de.sanandrew.mods.claysoldiers.entity.soldier.EntityClaySoldier;
import de.sanandrew.mods.claysoldiers.item.ItemRegistry;
import mcjty.theoneprobe.Tools;
import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.IEntityDisplayOverride;
import mcjty.theoneprobe.api.IProbeHitEntityData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import mcjty.theoneprobe.api.TextStyleClass;
import mcjty.theoneprobe.config.Config;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class TOPEntityDisplayOverride
        implements IEntityDisplayOverride
{
    private static final Map<Class<? extends Entity>, Function<Entity, String>> TITLE_OVERRIDES = new HashMap<>();
    static {
        TITLE_OVERRIDES.put(EntityClaySoldier.class, (entity) -> ItemRegistry.DOLL_SOLDIER.getTypeStack(((EntityClaySoldier) entity).getSoldierTeam()).getDisplayName());
        TITLE_OVERRIDES.put(EntityClayHorse.class, (entity) -> ItemRegistry.DOLL_HORSE.getTypeStack(((EntityClayHorse) entity).getType()).getDisplayName());
        TITLE_OVERRIDES.put(EntityPegasus.class, (entity) -> ItemRegistry.DOLL_PEGASUS.getTypeStack(((EntityPegasus) entity).getType()).getDisplayName());
        TITLE_OVERRIDES.put(EntityTurtle.class, (entity) -> ItemRegistry.DOLL_TURTLE.getTypeStack(((EntityTurtle) entity).getType()).getDisplayName());
        TITLE_OVERRIDES.put(EntityWoolBunny.class, (entity) -> ItemRegistry.DOLL_BUNNY.getTypeStack(((EntityWoolBunny) entity).getType()).getDisplayName());
        TITLE_OVERRIDES.put(EntityGecko.class, (entity) -> ItemRegistry.DOLL_GECKO.getTypeStack(((EntityGecko )entity).getType()).getDisplayName());
    }

    @Override
    public boolean overrideStandardInfo(ProbeMode probeMode, IProbeInfo probeInfo, EntityPlayer entityPlayer, World world, Entity entity, IProbeHitEntityData iProbeHitEntityData) {
        if( entity != null && TITLE_OVERRIDES.containsKey(entity.getClass()) ) {
            if( Tools.show(probeMode, Config.getRealConfig().getShowModName()) ) {
                probeInfo.horizontal()
                          .entity(entity)
                          .vertical()
                          .text(TextStyleClass.NAME + TITLE_OVERRIDES.get(entity.getClass()).apply(entity))
                          .text(TextStyleClass.MODNAME + Tools.getModName(entity));
            } else {
                probeInfo.horizontal(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER))
                          .entity(entity)
                          .text(TextStyleClass.NAME + TITLE_OVERRIDES.get(entity.getClass()).apply(entity));
            }

            return true;
        }

        return false;
    }
}
