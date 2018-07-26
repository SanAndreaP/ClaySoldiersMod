/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry.team;

import com.google.common.collect.ImmutableList;
import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.ITeam;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.ITeamRegistry;
import de.sanandrew.mods.claysoldiers.registry.ItemRegistry;
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
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class TeamRegistry
        implements ITeamRegistry
{
    public static final TeamRegistry INSTANCE = new TeamRegistry();

    private final Map<UUID, ITeam> teamFromUUID;
    private final ArrayList<ITeam> teams;

    private TeamRegistry() {
        this.teamFromUUID = new HashMap<>();
        this.teams = new ArrayList<>();
    }

    @Override
    public ITeam registerTeam(UUID id, String name, ResourceLocation itemModel, int itemColor, ResourceLocation[] normalTextures, ResourceLocation[] rareTextures, ResourceLocation[] uniqueTextures) {
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

        ITeam newTeam = new TeamStandard(id, name, itemModel, itemColor, normalTextures, rareTextures, uniqueTextures);
        this.teamFromUUID.put(id, newTeam);
        this.teams.add(newTeam);

        return newTeam;
    }

    @Override
    public boolean registerTeam(ITeam team) {
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
    public ITeam getTeam(UUID id) {
        return MiscUtils.defIfNull(this.teamFromUUID.get(id), NULL_TEAM);
    }

    @Override
    public List<ITeam> getTeams() {
        return ImmutableList.copyOf(this.teams);
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public ITeam getTeam(ItemStack stack) {
        if( ItemStackUtils.isItem(stack, ItemRegistry.DOLL_SOLDIER) ) {
            NBTTagCompound nbt = stack.getSubCompound("dollSoldier");
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
    public ItemStack setTeam(ItemStack stack, ITeam team) {
        if( team != null && ItemStackUtils.isItem(stack, ItemRegistry.DOLL_SOLDIER) ) {
            stack.getOrCreateSubCompound("dollSoldier").setString("team", team.getId().toString());
        }

        return stack;
    }

    @Override
    public ItemStack getNewTeamStack(int count, ITeam team) {
        return setTeam(new ItemStack(ItemRegistry.DOLL_SOLDIER, count), team);
    }

    @Override
    public ItemStack getNewTeamStack(int count, UUID team) {
        return setTeam(new ItemStack(ItemRegistry.DOLL_SOLDIER, count), team);
    }

    public static final ITeam NULL_TEAM = new ITeam()
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
        @Override public boolean isVisible() { return false; }
        @Override public boolean isValid() { return false; }
        @Override public ItemStack getTypeStack() { return ItemStack.EMPTY; }
    };
}
