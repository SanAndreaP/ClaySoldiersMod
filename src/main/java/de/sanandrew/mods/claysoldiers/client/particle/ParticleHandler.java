/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.particle;

import de.sanandrew.mods.claysoldiers.registry.TeamRegistry;
import de.sanandrew.mods.claysoldiers.util.EnumParticle;
import de.sanandrew.mods.sanlib.lib.Tuple;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleBreaking;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.EnumMap;
import java.util.UUID;

@SideOnly(Side.CLIENT)
@SuppressWarnings("unused")
public final class ParticleHandler
{
    private static final EnumMap<EnumParticle, ParticleFunc> PARTICLES = new EnumMap<>(EnumParticle.class);
    private static Minecraft mc;

    static {
        PARTICLES.put(EnumParticle.TEAM_BREAK, ParticleHandler::spawnTeamParticle);
        PARTICLES.put(EnumParticle.ITEM_BREAK, ParticleHandler::spawnItemParticle);
    }

    public static void spawn(EnumParticle particle, int dim, double x, double y, double z, Object... additData) {
        if( mc == null ) {
            mc = Minecraft.getMinecraft();
        }

        PARTICLES.get(particle).accept(dim, x, y, z, new Tuple(additData));
    }

    private static void spawnTeamParticle(int dim, double x, double y, double z, Tuple additData) {
        if( additData.checkValue(0, val -> val instanceof UUID) ) {
            for( int i = 0; i < 20; i++ ) {
                mc.effectRenderer.addEffect(new ParticleTeamBreaking(mc.world, x, y, z, TeamRegistry.INSTANCE.getTeam(additData.<UUID>getValue(0))));
            }
        }
    }

    private static void spawnItemParticle(int dim, double x, double y, double z, Tuple additData) {
        if( additData.checkValue(0, val -> val instanceof Integer) ) {
            ParticleBreaking.Factory bf = new ParticleBreaking.Factory();
            for( int i = 0; i < 20; i++ ) {
                mc.effectRenderer.addEffect(bf.createParticle(0, mc.world, x, y, z, 0.0D, 0.0D, 0.0D, additData.<Integer>getValue(0)));
            }
        }
    }
}
