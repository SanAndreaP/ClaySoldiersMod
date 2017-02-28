/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util;

import de.sanandrew.mods.claysoldiers.api.CsmPlugin;
import de.sanandrew.mods.claysoldiers.api.ICsmPlugin;
import de.sanandrew.mods.claysoldiers.api.client.IRenderHookRegistry;
import de.sanandrew.mods.claysoldiers.api.soldier.ITeamRegistry;
import de.sanandrew.mods.claysoldiers.api.soldier.IUpgradeRegistry;
import de.sanandrew.mods.claysoldiers.client.renderer.soldier.RenderHookMainHandItem;
import de.sanandrew.mods.claysoldiers.registry.TeamRegistry;
import de.sanandrew.mods.claysoldiers.registry.UpgradeRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.UUID;

@CsmPlugin
public class CsmInternalPlugin
        implements ICsmPlugin
{
    @Override
    public void registerTeams(ITeamRegistry registry) {
        TeamRegistry.initialize(registry);
    }

    @Override
    public void registerUpgrades(IUpgradeRegistry registry) {
        UpgradeRegistry.initialize(registry);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerSoldierRenderHook(IRenderHookRegistry registry) {
        registry.registerSoldierHook(new RenderHookMainHandItem(0));
        registry.registerSoldierHook(new RenderHookMainHandItem(1));
    }
}
