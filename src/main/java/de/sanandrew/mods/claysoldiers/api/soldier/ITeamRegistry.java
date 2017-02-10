/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.api.soldier;

import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.UUID;

public interface ITeamRegistry
{
    /**
     * registers a team with a standard implementation, which is returned if the registration succeeded.
     * @param id an unique ID for the team
     * @param name an unique name for the team
     * @param itemModel the model used for the item
     * @param itemColor the color used for the item
     * @param normalTexture the standard texture for the soldier entities
     * @param rareTextures the rare textures for the soldier entities, can be null if no rare textures should be used
     * @param uniqueTextures the unique textures for the soldier entities, can be null if no unique textures should be used
     * @return a standard {@link Team} implementation if registration succeeded, null otherwise
     */
    Team registerTeam(UUID id, String name, ResourceLocation itemModel, int itemColor, ResourceLocation normalTexture, ResourceLocation[] rareTextures, ResourceLocation[] uniqueTextures);

    /**
     * registers a team with a custom implementation.
     * @param team the custom implementation of {@link Team}
     * @return true, if registration succeeded, false otherwise
     */
    boolean registerTeam(Team team);

    Team getTeam(UUID id);

    List<Team> getTeams();

    /**
     * registers a team with a standard implementation without unique textures, which is returned if the registration succeeded.
     * @param id an unique ID for the team
     * @param name an unique name for the team
     * @param itemModel the model used for the item
     * @param itemColor the color used for the item
     * @param normalTexture the standard texture for the soldier entities
     * @param rareTextures the rare textures for the soldier entities, can be null if no rare textures should be used
     * @return a standard {@link Team} implementation if registration succeeded, null otherwise
     */
    default Team registerTeam(UUID id, String name, ResourceLocation itemModel, int itemColor, ResourceLocation normalTexture, ResourceLocation[] rareTextures) {
        return this.registerTeam(id, name, itemModel, itemColor, normalTexture, rareTextures, null);
    }

    /**
     * registers a team with a standard implementation without rare and unique textures, which is returned if the registration succeeded.
     * @param id an unique ID for the team
     * @param name an unique name for the team
     * @param itemModel the model used for the item
     * @param itemColor the color used for the item
     * @param normalTexture the standard texture for the soldier entities
     * @return a standard {@link Team} implementation if registration succeeded, null otherwise
     */
    default Team registerTeam(UUID id, String name, ResourceLocation itemModel, int itemColor, ResourceLocation normalTexture) {
        return this.registerTeam(id, name, itemModel, itemColor, normalTexture, null, null);
    }
}
