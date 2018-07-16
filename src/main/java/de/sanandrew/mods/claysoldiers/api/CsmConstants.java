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
    public static final String VERSION = "3.0.0-beta.1.1";
    public static final String CHANNEL = "ClaySoldiersNWCH";
    public static final String NAME = "Clay Soldiers Mod";
    public static final String DEPENDENCIES = "required-after:forge@[14.23.2.2611,];required-after:sanlib@[1.4.2,]";
    public static final String GUI_FACTORY = "de.sanandrew.mods.claysoldiers.client.gui.config.CsmGuiFactory";

    public static final String MOD_PROXY_CLIENT = "de.sanandrew.mods.claysoldiers.client.util.ClientProxy";
    public static final String MOD_PROXY_SERVER = "de.sanandrew.mods.claysoldiers.util.CommonProxy";

    public static final String API_ID = "claysoldiers_api";
    public static final String API_VERSION = "1.0.0-beta.1";
}
