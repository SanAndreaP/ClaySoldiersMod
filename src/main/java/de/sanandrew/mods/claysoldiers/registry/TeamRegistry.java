/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.soldier.ITeamRegistry;
import de.sanandrew.mods.claysoldiers.api.soldier.Team;
import de.sanandrew.mods.claysoldiers.util.Resources;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TeamRegistry
        implements ITeamRegistry
{
    private final Map<UUID, Team> teamFromUUID;

    private TeamRegistry() {
        this.teamFromUUID = new HashMap<>();
    }

    @Override
    public Team registerTeam(UUID id, String name, ResourceLocation itemModel, int itemColor, ResourceLocation normalTexture, ResourceLocation[] rareTextures, ResourceLocation[] uniqueTextures) {
        if( id == null || name == null || itemModel == null || normalTexture == null ) {
            CsmConstants.LOG.log(Level.WARN, String.format("Team ID, name, item model and normal texture cannot be null for ID %s with name %s!", id, name));
            return null;
        }

        if( this.teamFromUUID.containsKey(id) ) {
            CsmConstants.LOG.log(Level.WARN, String.format("Team UUID %s with name %s is already registered!", id, name));
            return null;
        }

        if( this.teamFromUUID.values().stream().anyMatch(team -> team.getName().equals(name)) ) {
            CsmConstants.LOG.log(Level.WARN, String.format("Team name %s with ID %s is already registered!", name, id));
            return null;
        }

        Team newTeam = new TeamStandard(id, name, itemModel, itemColor, normalTexture, rareTextures, uniqueTextures);
        this.teamFromUUID.put(id, newTeam);

        return newTeam;
    }

    @Override
    public boolean registerTeam(Team team) {
        if( team == null ) {
            CsmConstants.LOG.log(Level.WARN, "Team instance cannot be null!");
            return false;
        }

        UUID id = team.getId();
        String name = team.getName();

        if( id == null || name == null || team.getItemModel() == null || team.getNormalTexture() == null ) {
            CsmConstants.LOG.log(Level.WARN, String.format("Team ID, name, item model and normal texture cannot be null for ID %s with name %s!", id, name));
            return false;
        }

        if( this.teamFromUUID.containsKey(id) ) {
            CsmConstants.LOG.log(Level.WARN, String.format("Team UUID %s with name %s is already registered!", id, name));
            return false;
        }

        if( this.teamFromUUID.values().stream().anyMatch(iTeam -> iTeam.getName().equals(name)) ) {
            CsmConstants.LOG.log(Level.WARN, String.format("Team name %s with ID %s is already registered!", name, id));
            return false;
        }

        this.teamFromUUID.put(id, team);

        return true;
    }

    @Override
    public Team getTeam(UUID id) {
        return this.teamFromUUID.get(id);
    }

    @Override
    public List<Team> getTeams() {
        return new ArrayList<>(this.teamFromUUID.values());
    }

    public void initialize() {
        this.registerTeam(UUID.fromString("CA383B5D-DDAF-4523-9001-5A5685EF5FBA"), "clay", Resources.ITEM_SOLDIER_CLAY.getResource(), 0x808080,
                          Resources.ENTITY_SOLDIER_NORM_CLAY.getResource());
    }
}
