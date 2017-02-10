/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class CsmConstants
{
    public static final String ID = "claysoldiers";
    public static final Logger LOG = LogManager.getLogger(ID);
    public static final String VERSION = "3.0.0-alpha.1";
    public static final String CHANNEL = "ClaySoldiersNWCH";
    public static final String NAME = "Clay Soldiers Mod";
    public static final String DEPENDENCIES = "required-after:Forge@[12.18.2.2099,];required-after:sanlib@[1.0.0,]";

    public static final String API_ID = "claysoldiers_api";
    public static final String API_VERSION = "1.0.0-alpha.1";

//    public static ITmrUtils utils;
//    public static IRepairKitRegistry repkitRegistry;
}
