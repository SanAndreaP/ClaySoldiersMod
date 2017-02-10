/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.network.packet;

public enum EnumParticleFx
{
    /**
     * DataTuple: <br>&nbsp;&nbsp;posX (Double)<br>&nbsp;&nbsp;posY (Double)<br>&nbsp;&nbsp;posZ (Double)<br>&nbsp;&nbsp;item name (String)
     */
    FX_BREAK,
    /**
     * DataTuple: <br>&nbsp;&nbsp;posX (Double)<br>&nbsp;&nbsp;posY (Double)<br>&nbsp;&nbsp;posZ (Double)
     */
    FX_CRIT,
    /**
     * DataTuple: <br>&nbsp;&nbsp;posX (Double)<br>&nbsp;&nbsp;posY (Double)<br>&nbsp;&nbsp;posZ (Double)<br>&nbsp;&nbsp;team name (String)
     */
    FX_SOLDIER_DEATH,
    /**
     * DataTuple: <br>&nbsp;&nbsp;posX (Double)<br>&nbsp;&nbsp;posY (Double)<br>&nbsp;&nbsp;posZ (Double)<br>&nbsp;&nbsp;horse type (Byte)
     */
    FX_HORSE_DEATH,
    /**
     * DataTuple: <br>&nbsp;&nbsp;posX (Double)<br>&nbsp;&nbsp;posY (Double)<br>&nbsp;&nbsp;posZ (Double)<br>&nbsp;&nbsp;block name (String)
     */
    FX_DIGGING,
    /**
     * DataTuple: <br>&nbsp;&nbsp;posX (Double)<br>&nbsp;&nbsp;posY (Double)<br>&nbsp;&nbsp;posZ (Double)
     * <br>&nbsp;&nbsp;red (Double)<br>&nbsp;&nbsp;green (Double)<br>&nbsp;&nbsp;blue (Double)
     */
    FX_SPELL,
    /**
     * DataTuple: <i>n/a</i>
     */
    FX_NEXUS,
    /**
     * DataTuple: <br>&nbsp;&nbsp;posX (Int)<br>&nbsp;&nbsp;posY (Int)<br>&nbsp;&nbsp;posZ (Int)
     */
    FX_SHOCKWAVE,
    /**
     * DataTuple: <br>&nbsp;&nbsp;posX (Int)<br>&nbsp;&nbsp;posY (Int)<br>&nbsp;&nbsp;posZ (Int)
     */
    FX_MAGMAFUSE,
    /**
     * DataTuple: <br>&nbsp;&nbsp;posX (Double)<br>&nbsp;&nbsp;posY (Double)<br>&nbsp;&nbsp;posZ (Double)<br>&nbsp;&nbsp;bunny type (Byte)
     */
    FX_BUNNY_DEATH,
    /**
     * DataTuple: <br>&nbsp;&nbsp;posX (Double)<br>&nbsp;&nbsp;posY (Double)<br>&nbsp;&nbsp;posZ (Double)<br>&nbsp;&nbsp;bunny type (Byte)
     */
    FX_TURTLE_DEATH;

    public static final EnumParticleFx[] VALUES = values();

    public final byte ordinalByte() {
        return (byte) ordinal();
    }
}
