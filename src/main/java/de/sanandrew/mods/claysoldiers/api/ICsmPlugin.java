/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.api;

import de.sanandrew.mods.claysoldiers.api.soldier.ITeamRegistry;
import de.sanandrew.mods.claysoldiers.api.soldier.IUpgradeRegistry;

public interface ICsmPlugin
{
    void registerTeams(ITeamRegistry registry);

    void registerUpgrades(IUpgradeRegistry registry);
}
