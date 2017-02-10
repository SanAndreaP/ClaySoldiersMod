/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.soldier.Team;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.Level;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class TeamStandard
        implements Team
{
    private final UUID id;
    private final String name;
    private final ResourceLocation itmModel;
    private final int itmColor;
    private final ResourceLocation nrmlTexture;
    private final Map<Integer, ResourceLocation> rareTextures;
    private int[] rareTextureIds;
    private final Map<Integer, ResourceLocation> uniqTextures;
    private int[] uniqTextureIds;

    public TeamStandard(UUID id, String name, ResourceLocation itmModel, int itmColor, ResourceLocation nrmlTexture, ResourceLocation[] rareTextures, ResourceLocation[] uniqTextures) {
        this.id = id;
        this.name = name;
        this.nrmlTexture = nrmlTexture;
        this.itmModel = itmModel;
        this.itmColor = itmColor;
        this.rareTextures = new Int2ObjectArrayMap<>();
        this.uniqTextures = new Int2ObjectArrayMap<>();

        if( rareTextures != null ) {
            List<ResourceLocation> lst = Arrays.asList(rareTextures);
            this.rareTextures.putAll(lst.stream().collect(Collectors.toMap(lst::indexOf, val -> val)));
            this.rareTextureIds = this.rareTextures.keySet().stream().mapToInt(val -> val).toArray();
        }
        if( uniqTextures != null ) {
            List<ResourceLocation> lst = Arrays.asList(uniqTextures);
            this.uniqTextures.putAll(lst.stream().collect(Collectors.toMap(lst::indexOf, val -> val)));
            this.uniqTextureIds = this.uniqTextures.keySet().stream().mapToInt(val -> val).toArray();
        }
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public ResourceLocation getItemModel() {
        return this.itmModel;
    }

    @Override
    public int getItemColor() {
        return this.itmColor;
    }

    @Override
    public ResourceLocation getNormalTexture() {
        return this.nrmlTexture;
    }

    @Override
    public ResourceLocation getRareTexture(int id) {
        return this.rareTextures.get(id);
    }

    @Override
    public int[] getRareTextureIds() {
        return this.rareTextureIds;
    }

    @Override
    public ResourceLocation getUniqueTexture(int id) {
        return this.uniqTextures.get(id);
    }

    @Override
    public int[] getUniqueTextureIds() {
        return this.uniqTextureIds;
    }

    @Override
    public boolean addRareTexture(int id, ResourceLocation texture) {
        if( !this.rareTextures.containsKey(id) ) {
            this.rareTextures.put(id, texture);
            this.rareTextureIds = this.rareTextures.keySet().stream().mapToInt(val -> val).toArray();

            return true;
        }

        CsmConstants.LOG.log(Level.DEBUG, String.format("Already registered rare texture with ID %d for team %s!", id, this.getName()));
        return false;
    }

    @Override
    public boolean addUniqueTexture(int id, ResourceLocation texture) {
        if( !this.uniqTextures.containsKey(id) ) {
            this.uniqTextures.put(id, texture);
            this.uniqTextureIds = this.uniqTextures.keySet().stream().mapToInt(val -> val).toArray();

            return true;
        }

        CsmConstants.LOG.log(Level.DEBUG, String.format("Already registered unique texture with ID %d for team %s!", id, this.getName()));
        return false;
    }
}
