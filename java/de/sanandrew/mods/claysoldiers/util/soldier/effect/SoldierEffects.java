/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util.soldier.effect;

import com.google.common.collect.Maps;
import cpw.mods.fml.common.FMLLog;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import org.apache.logging.log4j.Level;

import java.util.Map;
import java.util.Set;

public class SoldierEffects
{
    public static final String EFF_SLIMEFEET = "slimefeet";
    public static final String EFF_SLOWMOTION = "slowmotion";
    public static final String EFF_THUNDER = "thunder";
    public static final String EFF_REDSTONE = "redstone";
    public static final String EFF_MAGMABOMB = "magmabomb";

    private static final Map<String, ASoldierEffect> NAME_TO_EFFECT_MAP_ = Maps.newHashMap();
    private static final Map<ASoldierEffect, String> EFFECT_TO_NAME_MAP_ = Maps.newHashMap();
    private static final Map<ASoldierEffect, Byte> EFFECT_TO_RENDER_ID_MAP_ = Maps.newHashMap();
    private static final Map<Byte, ASoldierEffect> RENDER_ID_TO_EFFECT_MAP_ = Maps.newHashMap();
    private static byte s_currRenderId = 0;

    public static void initialize() {
        registerEffect(EFF_SLIMEFEET, new EffectSlimeFeet(), getNewRenderId());
        registerEffect(EFF_SLOWMOTION, new EffectSlowMotion());
        registerEffect(EFF_THUNDER, new EffectThunder(), getNewRenderId());
        registerEffect(EFF_REDSTONE, new EffectBlindingRedstone(), getNewRenderId());
        registerEffect(EFF_MAGMABOMB, new EffectMagmaBomb(), getNewRenderId());
    }

    public static void registerEffect(String name, ASoldierEffect instance) {
        registerEffect(name, instance, -1);
    }

    public static void registerEffect(String name, ASoldierEffect instance, int clientRenderId) {
        NAME_TO_EFFECT_MAP_.put(name, instance);
        EFFECT_TO_NAME_MAP_.put(instance, name);

        if( clientRenderId >= 0 ) {
            if( clientRenderId > 127 ) {
                FMLLog.log(ClaySoldiersMod.MOD_LOG, Level.WARN, "The Effect \"%s\" cannot be bound to the render ID! The render ID is greater than 127!", name);
            } else if( RENDER_ID_TO_EFFECT_MAP_.containsKey((byte) clientRenderId) ) {
                FMLLog.log(ClaySoldiersMod.MOD_LOG, Level.WARN, "The Effect \"%s\" cannot be bound to the render ID! The render ID is already registered!", name);
            } else {
                EFFECT_TO_RENDER_ID_MAP_.put(instance, (byte) clientRenderId);
                RENDER_ID_TO_EFFECT_MAP_.put((byte) clientRenderId, instance);
            }
        }
    }

    public static ASoldierEffect getEffect(String name) {
        return NAME_TO_EFFECT_MAP_.get(name);
    }

    public static String getEffectName(ASoldierEffect effect) {
        return EFFECT_TO_NAME_MAP_.get(effect);
    }

    public static byte getRenderId(ASoldierEffect upgrade) {
        if( EFFECT_TO_RENDER_ID_MAP_.containsKey(upgrade) ) {
            return EFFECT_TO_RENDER_ID_MAP_.get(upgrade);
        } else {
            return -1;
        }
    }

    public static ASoldierEffect getEffect(int renderId) {
        return RENDER_ID_TO_EFFECT_MAP_.get((byte) renderId);
    }

    public static Set<Byte> getRegisteredRenderIds() {
        return RENDER_ID_TO_EFFECT_MAP_.keySet();
    }

    public static byte getNewRenderId() {
        if( s_currRenderId == 127 ) {
            throw new RenderIdException();
        }

        return s_currRenderId++;
    }

    public static class RenderIdException
            extends RuntimeException
    {
        public RenderIdException() {
            super("There are no more render IDs for the soldier effect available!");
        }
    }
}
