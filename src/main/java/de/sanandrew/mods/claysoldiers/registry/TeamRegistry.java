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

public final class TeamRegistry
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

    @Override
    public ItemStack getNewTeamStack(int count, Team team) {
        return setTeam(new ItemStack(ItemRegistry.doll_soldier, count), team);
    }

    @Override
    public ItemStack getNewTeamStack(int count, UUID team) {
        return setTeam(new ItemStack(ItemRegistry.doll_soldier, count), team);
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

    public static void initialize(ITeamRegistry registry) {
        registry.registerTeam(SOLDIER_CLAY, "clay", Resources.ITEM_SOLDIER_CLAY.resource, 0x8e8e86,
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_NORM_CLAY.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_RARE_CLAY.resource});
        registry.registerTeam(SOLDIER_RED, "red", Resources.ITEM_SOLDIER_CLAY.resource, 0xa22823,
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_NORM_RED.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_RARE_RED.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_UNIQ_RED.resource});
        registry.registerTeam(SOLDIER_YELLOW, "yellow", Resources.ITEM_SOLDIER_CLAY.resource, 0xfcd030,
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_NORM_YELLOW.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_RARE_YELLOW_1.resource, Resources.ENTITY_SOLDIER_RARE_YELLOW_2.resource, Resources.ENTITY_SOLDIER_RARE_YELLOW_3.resource});
        registry.registerTeam(SOLDIER_GREEN, "green", Resources.ITEM_SOLDIER_CLAY.resource, 0x56701b,
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_NORM_GREEN.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_RARE_GREEN.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_UNIQ_GREEN_1.resource, Resources.ENTITY_SOLDIER_UNIQ_GREEN_2.resource});
        registry.registerTeam(SOLDIER_BLUE, "blue", Resources.ITEM_SOLDIER_CLAY.resource, 0x373ca1,
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_NORM_BLUE.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_RARE_BLUE.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_UNIQ_BLUE.resource});
        registry.registerTeam(SOLDIER_ORANGE, "orange", Resources.ITEM_SOLDIER_CLAY.resource, 0xee7110,
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_NORM_ORANGE.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_RARE_ORANGE.resource});
        registry.registerTeam(SOLDIER_MAGENTA, "magenta", Resources.ITEM_SOLDIER_CLAY.resource, 0xc64ebd,
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_NORM_MAGENTA.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_RARE_MAGENTA.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_UNIQ_MAGENTA.resource});
        registry.registerTeam(SOLDIER_LIGHTBLUE, "lightblue", Resources.ITEM_SOLDIER_CLAY.resource, 0x41b7de,
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_NORM_LIGHTBLUE.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_RARE_LIGHTBLUE.resource});
        registry.registerTeam(SOLDIER_LIME, "lime", Resources.ITEM_SOLDIER_CLAY.resource, 0x77bf1a,
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_NORM_LIME.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_RARE_LIME.resource});
        registry.registerTeam(SOLDIER_PINK, "pink", Resources.ITEM_SOLDIER_CLAY.resource, 0xef95b2,
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_NORM_PINK.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_RARE_PINK.resource});
        registry.registerTeam(SOLDIER_CYAN, "cyan", Resources.ITEM_SOLDIER_CLAY.resource, 0x159095,
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_NORM_CYAN.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_RARE_CYAN.resource});
        registry.registerTeam(SOLDIER_PURPLE, "purple", Resources.ITEM_SOLDIER_CLAY.resource, 0x7d2bad,
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_NORM_PURPLE.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_RARE_PURPLE_1.resource, Resources.ENTITY_SOLDIER_RARE_PURPLE_2.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_UNIQ_PURPLE_1.resource, Resources.ENTITY_SOLDIER_UNIQ_PURPLE_2.resource});
        registry.registerTeam(SOLDIER_BROWN, "brown", Resources.ITEM_SOLDIER_CLAY.resource, 0x784c2c,
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_NORM_BROWN.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_RARE_BROWN_1.resource, Resources.ENTITY_SOLDIER_RARE_BROWN_2.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_UNIQ_BROWN_1.resource, Resources.ENTITY_SOLDIER_UNIQ_BROWN_2.resource});
        registry.registerTeam(SOLDIER_BLACK, "black", Resources.ITEM_SOLDIER_CLAY.resource, 0x19191d,
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_NORM_BLACK.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_RARE_BLACK.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_UNIQ_BLACK.resource});
        registry.registerTeam(SOLDIER_GRAY, "gray", Resources.ITEM_SOLDIER_CLAY.resource, 0x545b5e,
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_NORM_GRAY.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_RARE_GRAY.resource});
        registry.registerTeam(SOLDIER_WHITE, "white", Resources.ITEM_SOLDIER_CLAY.resource, 0xeaeded,
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_NORM_WHITE.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_RARE_WHITE.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_UNIQ_WHITE_1.resource, Resources.ENTITY_SOLDIER_UNIQ_WHITE_2.resource});
        registry.registerTeam(SOLDIER_MELON, "melon", Resources.ITEM_SOLDIER_MELON.resource, -0x1,
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_NORM_MELON.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_RARE_MELON.resource});
        registry.registerTeam(SOLDIER_PUMPKIN, "pumpkin", Resources.ITEM_SOLDIER_PUMPKIN.resource, -0x1,
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_NORM_PUMPKIN_1.resource, Resources.ENTITY_SOLDIER_NORM_PUMPKIN_2.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_RARE_PUMPKIN_1.resource, Resources.ENTITY_SOLDIER_RARE_PUMPKIN_2.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_UNIQ_PUMPKIN_1.resource, Resources.ENTITY_SOLDIER_UNIQ_PUMPKIN_2.resource});
        registry.registerTeam(SOLDIER_REDSTONE, "redstone", Resources.ITEM_SOLDIER_REDSTONE.resource, -0x1,
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_NORM_REDSTONE_1.resource, Resources.ENTITY_SOLDIER_NORM_REDSTONE_2.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_RARE_REDSTONE.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_UNIQ_REDSTONE_1.resource, Resources.ENTITY_SOLDIER_UNIQ_REDSTONE_2.resource});
        registry.registerTeam(SOLDIER_COAL, "coal", Resources.ITEM_SOLDIER_COAL.resource, -0x1,
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_NORM_COAL.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_RARE_COAL.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_UNIQ_COAL.resource});
    }

    public static final UUID SOLDIER_CLAY = UUID.fromString("CA383B5D-DDAF-4523-9001-5A5685EF5FBA");
    public static final UUID SOLDIER_RED = UUID.fromString("77BFA9D5-2E49-4AD3-B6E6-DED0EE1AAD2D");
    public static final UUID SOLDIER_YELLOW = UUID.fromString("BFDC0FF6-BBE2-4C54-ADF6-9599E16D157A");
    public static final UUID SOLDIER_GREEN = UUID.fromString("2C521F69-846F-4294-95DD-2E9C76C19589");
    public static final UUID SOLDIER_BLUE = UUID.fromString("E8BB8A2C-3DEA-4193-AAC9-84E052A02A48");
    public static final UUID SOLDIER_ORANGE = UUID.fromString("F0A0E637-BB71-44BD-AC73-6886503C6FD6");
    public static final UUID SOLDIER_MAGENTA = UUID.fromString("7EB78104-728C-4D36-85A8-98A6B5E2184C");
    public static final UUID SOLDIER_LIGHTBLUE = UUID.fromString("025A1385-278D-41B7-981B-087141F99120");
    public static final UUID SOLDIER_LIME = UUID.fromString("E9FE47F6-EA6E-4467-99A7-70E59A60835B");
    public static final UUID SOLDIER_PINK = UUID.fromString("7A11B9A2-87A7-45F5-939A-D0C121C32D84");
    public static final UUID SOLDIER_CYAN = UUID.fromString("A717B83F-B0EF-4F8F-9829-3CFF6EDF7CEC");
    public static final UUID SOLDIER_PURPLE = UUID.fromString("6EE60BC8-70C9-4941-9EB8-200A0E7AD867");
    public static final UUID SOLDIER_BROWN = UUID.fromString("75B2C91A-BE22-4492-8AC7-CFF4767E37F1");
    public static final UUID SOLDIER_BLACK = UUID.fromString("BC6913E6-859F-4714-9E90-28CE4283E9CC");
    public static final UUID SOLDIER_GRAY = UUID.fromString("77C678AB-ED0D-4E3D-9C8D-B1F8C9600CD3");
    public static final UUID SOLDIER_WHITE = UUID.fromString("7ECD63AF-21A5-42C7-AD32-8988014DA398");
    public static final UUID SOLDIER_MELON = UUID.fromString("400BEDA7-3463-46E9-A01B-16D874ADF728");
    public static final UUID SOLDIER_PUMPKIN = UUID.fromString("81227ECB-F129-4D2E-80C7-07CEC076B53D");
    public static final UUID SOLDIER_REDSTONE = UUID.fromString("0FF36671-62A0-4C41-9567-16A8071FD4AF");
    public static final UUID SOLDIER_COAL = UUID.fromString("F7D936D6-BFCD-48AA-88E5-1C6B12641943");
}
