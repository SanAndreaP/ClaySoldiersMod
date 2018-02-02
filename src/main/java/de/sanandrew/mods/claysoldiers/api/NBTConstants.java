/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.api;

public final class NBTConstants
{
    public static final String E_DOLL_TYPE_HORSE = "horseType";             // integer
    public static final String E_DOLL_TYPE_TURTLE = "turtleType";           // integer
    public static final String E_DOLL_TYPE_BUNNY = "bunny_type";            // integer
    public static final String E_DOLL_TYPE_GECKO = "gecko_type";            // integer
    public static final String E_DOLL_ITEM = "dollItem";                    // compound tag

    public static final String E_MOUNT_TEXTURE_ID = "textureId";            // integer

    public static final String E_SOLDIER_TEAM = "soldier_team";             // string
    public static final String E_SOLDIER_TEXTYPE = "soldier_texture_type";  // byte
    public static final String E_SOLDIER_TEXID = "soldier_texture_id";      // byte
    public static final String E_SOLDIER_DOLL = "soldier_doll";             // compound tag
    public static final String E_SOLDIER_UPGRADES = "soldier_upgrades";     // compound tag list
    public static final String E_SOLDIER_EFFECTS = "soldier_effects";       // compound tag list

    public static final String N_UPGRADE_ID = "upg_id";                     // string
    public static final String N_UPGRADE_TYPE = "upg_type";                 // byte
    public static final String N_UPGRADE_NBT = "upg_nbt";                   // compound tag
    public static final String N_UPGRADE_ITEM = "upg_item";                 // compound tag
    public static final String N_EFFECT_ID = "eff_id";                      // string
    public static final String N_EFFECT_DURATION = "eff_duration";          // integer
    public static final String N_EFFECT_NBT = "eff_nbt";                    // compound tag

    public static final String S_DOLL_HORSE = "dollHorse";                  // sub - compound tag
    public static final String S_DOLL_PEGASUS = "dollPegasus";              // sub - compound tag
    public static final String S_DOLL_TURTLE = "dollTurtle";                // sub - compound tag
    public static final String S_DOLL_BUNNY = "doll_bunny";                 // sub - compound tag
    public static final String S_DOLL_GECKO = "doll_gecko";                 // sub - compound tag
    public static final String I_DOLL_TYPE = "type";                        // integer

}
