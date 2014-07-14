/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util.soldier.effect;

import com.google.common.collect.Maps;
import com.google.common.primitives.Bytes;
import cpw.mods.fml.common.FMLLog;
import de.sanandrew.mods.claysoldiers.util.CSM_Main;
import org.apache.logging.log4j.Level;

import java.util.Map;

public class SoldierEffects
{
    private static final Map<String, ISoldierEffect> NAME_TO_EFFECT_MAP_ = Maps.newHashMap();
    private static final Map<ISoldierEffect, String> EFFECT_TO_NAME_MAP_ = Maps.newHashMap();
    private static final Map<ISoldierEffect, Byte> EFFECT_TO_RENDER_ID_MAP_ = Maps.newHashMap();
    private static final Map<Byte, ISoldierEffect> RENDER_ID_TO_EFFECT_MAP_ = Maps.newHashMap();

    private static byte currRenderId = 0;

    public static void registerEffect(String name, ISoldierEffect instance) {
        registerEffect(name, instance, -1);
    }

    public static void registerEffect(String name, ISoldierEffect instance, int clientRenderId) {
        NAME_TO_EFFECT_MAP_.put(name, instance);
        EFFECT_TO_NAME_MAP_.put(instance, name);

        if( clientRenderId >= 0 ) {
            if( clientRenderId > 127 ) {
                FMLLog.log(CSM_Main.MOD_LOG, Level.WARN, "The Effect \"%s\" cannot be bound to the render ID! The render ID is greater than 127!", name);
            } else if( RENDER_ID_TO_EFFECT_MAP_.containsKey((byte) clientRenderId) ) {
                FMLLog.log(CSM_Main.MOD_LOG, Level.WARN, "The Effect \"%s\" cannot be bound to the render ID! The render ID is already registered!", name);
            } else {
                EFFECT_TO_RENDER_ID_MAP_.put(instance, (byte) clientRenderId);
                RENDER_ID_TO_EFFECT_MAP_.put((byte) clientRenderId, instance);
            }
        }
    }

    public static ISoldierEffect getEffectFromName(String name) {
        return NAME_TO_EFFECT_MAP_.get(name);
    }

    public static String getNameFromEffect(ISoldierEffect effect) {
        return EFFECT_TO_NAME_MAP_.get(effect);
    }

    public static byte getRenderIdFromEffect(ISoldierEffect upgrade) {
        if( EFFECT_TO_RENDER_ID_MAP_.containsKey(upgrade) ) {
            return EFFECT_TO_RENDER_ID_MAP_.get(upgrade);
        } else {
            return -1;
        }
    }

    public static ISoldierEffect getEffectFromRenderId(int renderId) {
        return RENDER_ID_TO_EFFECT_MAP_.get((byte) renderId);
    }

    public static byte[] getRegisteredRenderIds() {
        return Bytes.toArray(RENDER_ID_TO_EFFECT_MAP_.keySet());
    }

    public static byte getNewRenderId() {
        if( currRenderId == 127 ) {
            throw new RenderIdException();
        }
        return currRenderId++;
    }

    public static final String EFF_SLIMEFEET = "slimefeet";

    static {
        registerEffect(EFF_SLIMEFEET, new EffectSlimeFeet(), getNewRenderId());
    }

    public static class RenderIdException extends RuntimeException {
        public RenderIdException() {
            super("There are no more render IDs for soldier upgrade available!");
        }
    }
}
