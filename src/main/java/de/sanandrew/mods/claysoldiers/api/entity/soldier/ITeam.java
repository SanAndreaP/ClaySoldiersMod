/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.api.entity.soldier;

import de.sanandrew.mods.claysoldiers.api.doll.IDollType;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import java.util.UUID;

public interface ITeam
        extends IDollType
{
    /**
     * gets the unique ID for that team.
     * @return the team ID
     */
    UUID getId();

    /**
     * gets the unique name for that team.
     * @return the team name
     */
    @Override
    String getName();

    /**
     * gets the item model for that team.
     * @return the team item model
     */
    ResourceLocation getItemModel();

    /**
     * gets the item color for that team.
     * @return the team item color as 0xRRGGBB
     */
    default int getItemColor() { return -1; }

    /**
     * gets the standard texture for this team to use for the soldier entity.<br>
     * Gets called when the soldier spawns for the first time.
     * @param id The ID of the texture to use
     * @return location to a texture
     */
    ResourceLocation getNormalTexture(int id);

    /**
     * gets the amount of rare textures defined for this team.
     * @return the texture count
     */
    int[] getNormalTextureIds();

    /**
     * gets a rare texture for this team to use for the soldier entity based on an ID.<br>
     * Should override the normal texture if not returning {@code null}.<br>
     * Gets called with a random ID when the soldier spawns for the first time with a chance of 1 out of 100.<br>
     * Gets called with the same ID when the soldier is loaded from NBT.
     * @param id The ID of the rare texture to use
     * @return location to a texture or {@code null}, if either the index can't be used or the team has no rare texture defined
     */
    ResourceLocation getRareTexture(int id);

    /**
     * gets the amount of rare textures defined for this team.
     * @return the rare texture count
     */
    int[] getRareTextureIds();

    /**
     * gets the unique texture for this team to use for the soldier entity based on an ID.<br>
     * Should override the normal and rare texture if not returning {@code null}.<br>
     * Gets called with a random ID when the soldier spawns for the first time with a chance of 1 out of 100000 or on special occassions.
     * @param id The ID of the rare texture to use
     * @return location to a texture or {@code null}, if either the index can't be used or the team has no rare texture defined
     */
    ResourceLocation getUniqueTexture(int id);

    /**
     * gets the amount of unique textures defined for this team.
     * @return the unique texture count
     */
    int[] getUniqueTextureIds();

    /**
     * adds a new texture to be used as a normal option.
     * @param id the unique ID for the normal texture
     * @param texture The normal texture
     * @return true, if the normal texture is successfully registered, false otherwise
     */
    boolean addNormalTexture(byte id, ResourceLocation texture);

    /**
     * adds a new texture to be used as a rare option.
     * @param id the unique ID for the rare texture
     * @param texture The rare texture
     * @return true, if the rare texture is successfully registered, false otherwise
     */
    boolean addRareTexture(byte id, ResourceLocation texture);

    /**
     * adds a new texture to be used as an unique option.
     * @param id the unique ID for the unique texture
     * @param texture The unique texture
     * @return true, if the unique texture is successfully registered, false otherwise
     */
    boolean addUniqueTexture(byte id, ResourceLocation texture);

    /**
     * checks wether or not an unique texture should be enforced on spawn (e.g. a special occassion like a custom name of the soldier)
     * @param entity The soldier entity
     * @return true, if an unique texture should be enforced, otherwise if false, check for the chance of 1 out of 100000
     */
    default boolean doForceUniqueTexture(Entity entity) { return false; }
}
