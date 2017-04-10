/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.api;

import de.sanandrew.mods.claysoldiers.api.client.IRenderHookRegistry;
import de.sanandrew.mods.claysoldiers.api.soldier.ITeamRegistry;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ICsmPlugin
{
    default void registerTeams(ITeamRegistry registry) { }

    default void registerUpgrades(IUpgradeRegistry registry) { }

    default void registerCsmEvents(EventBus bus) { }

    @SideOnly(Side.CLIENT)
    default void registerCsmClientEvents(EventBus bus) { }

    @SideOnly(Side.CLIENT)
    void registerSoldierRenderHook(IRenderHookRegistry registry);
}
