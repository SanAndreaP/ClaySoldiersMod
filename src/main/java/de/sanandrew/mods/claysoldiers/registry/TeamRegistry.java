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
import de.sanandrew.mods.sanlib.lib.util.ItemStackUtils;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import de.sanandrew.mods.sanlib.lib.util.UuidUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TeamRegistry
        implements ITeamRegistry
{
    public static final TeamRegistry INSTANCE = new TeamRegistry();

    private final Map<UUID, Team> teamFromUUID;
    private final ArrayList<Team> teams;

    private TeamRegistry() {
        this.teamFromUUID = new HashMap<>();
        this.teams = new ArrayList<>();
    }

    @Override
    public Team registerTeam(UUID id, String name, ResourceLocation itemModel, int itemColor, ResourceLocation[] normalTextures, ResourceLocation[] rareTextures, ResourceLocation[] uniqueTextures) {
        if( id == null || name == null || itemModel == null || normalTextures == null || normalTextures.length < 1 ) {
            CsmConstants.LOG.log(Level.WARN, String.format("Team ID, name, item model and normal texture cannot be null nor empty for ID %s with name %s!", id, name));
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

        Team newTeam = new TeamStandard(id, name, itemModel, itemColor, normalTextures, rareTextures, uniqueTextures);
        this.teamFromUUID.put(id, newTeam);
        this.teams.add(newTeam);

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

        if( id == null || name == null || team.getItemModel() == null || team.getNormalTextureIds() == null || team.getNormalTextureIds().length < 1 ) {
            CsmConstants.LOG.log(Level.WARN, String.format("Team ID, name, item model and normal texture cannot be null nor empty for ID %s with name %s!", id, name));
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
        this.teams.add(team);

        return true;
    }

    @Override
    public Team getTeam(UUID id) {
        return MiscUtils.defIfNull(this.teamFromUUID.get(id), NULL_TEAM);
    }

    @Override
    public ArrayList<Team> getTeams() {
        return new ArrayList<>(this.teams);
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public Team getTeam(ItemStack stack) {
        if( ItemStackUtils.isItem(stack, ItemRegistry.doll_soldier) ) {
            NBTTagCompound nbt = stack.getSubCompound("dollSoldier", false);
            if( nbt != null && nbt.hasKey("team", Constants.NBT.TAG_STRING) ) {
                try {
                    return this.getTeam(UUID.fromString(nbt.getString("team")));
                } catch( IllegalArgumentException ex ) {
                    return NULL_TEAM;
                }
            }
        }

        return NULL_TEAM;
    }

    @Override
    public ItemStack setTeam(ItemStack stack, Team team) {
        if( team != null && ItemStackUtils.isItem(stack, ItemRegistry.doll_soldier) ) {
            stack.getSubCompound("dollSoldier", true).setString("team", team.getId().toString());
        }

        return stack;
    }

    public static final Team NULL_TEAM = new Team()
    {
        @Override public UUID getId() { return UuidUtils.EMPTY_UUID; }
        @Override public String getName() { return "null"; }
        @Override public ResourceLocation getItemModel() { return null; }
        @Override public int getItemColor() { return 0x000000; }
        @Override public ResourceLocation getNormalTexture(int id) { return null; }
        @Override public int[] getNormalTextureIds() { return new int[] { 0 }; }
        @Override public ResourceLocation getRareTexture(int id) { return null; }
        @Override public int[] getRareTextureIds() { return new int[0]; }
        @Override public ResourceLocation getUniqueTexture(int id) { return null; }
        @Override public int[] getUniqueTextureIds() { return new int[0]; }
        @Override public boolean addNormalTexture(byte id, ResourceLocation texture) { return false; }
        @Override public boolean addRareTexture(byte id, ResourceLocation texture) { return false; }
        @Override public boolean addUniqueTexture(byte id, ResourceLocation texture) { return false; }
    };
}
