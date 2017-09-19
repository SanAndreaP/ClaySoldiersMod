/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry.team;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.soldier.ITeam;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.Level;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class TeamStandard
        implements ITeam
{
    private final UUID id;
    private final String name;
    private final ResourceLocation itmModel;
    private final int itmColor;
    private final Map<Integer, ResourceLocation> nrmlTextures;
    private int[] nrmlTextureIds;
    private final Map<Integer, ResourceLocation> rareTextures;
    private int[] rareTextureIds;
    private final Map<Integer, ResourceLocation> uniqTextures;
    private int[] uniqTextureIds;

    public TeamStandard(UUID id, String name, ResourceLocation itmModel, int itmColor, ResourceLocation[] nrmlTextures, ResourceLocation[] rareTextures, ResourceLocation[] uniqTextures) {
        this.id = id;
        this.name = name;
        this.itmModel = itmModel;
        this.itmColor = itmColor;
        this.nrmlTextures = new Int2ObjectArrayMap<>();
        this.rareTextures = new Int2ObjectArrayMap<>();
        this.uniqTextures = new Int2ObjectArrayMap<>();

        {
            List<ResourceLocation> lst = Arrays.asList(nrmlTextures);
            this.nrmlTextures.putAll(lst.stream().collect(Collectors.toMap(lst::indexOf, val -> val)));
            this.nrmlTextureIds = this.nrmlTextures.keySet().stream().mapToInt(val -> val).toArray();
        }

        if( rareTextures != null ) {
            List<ResourceLocation> lst = Arrays.asList(rareTextures);
            this.rareTextures.putAll(lst.stream().collect(Collectors.toMap(lst::indexOf, val -> val)));
            this.rareTextureIds = this.rareTextures.keySet().stream().mapToInt(val -> val).toArray();
        } else {
            this.rareTextureIds = new int[0];
        }

        if( uniqTextures != null ) {
            List<ResourceLocation> lst = Arrays.asList(uniqTextures);
            this.uniqTextures.putAll(lst.stream().collect(Collectors.toMap(lst::indexOf, val -> val)));
            this.uniqTextureIds = this.uniqTextures.keySet().stream().mapToInt(val -> val).toArray();
        } else {
            this.uniqTextureIds = new int[0];
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
    public ResourceLocation getNormalTexture(int id) {
        return this.nrmlTextures.get(id);
    }

    @Override
    public int[] getNormalTextureIds() {
        return this.nrmlTextureIds;
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
    public boolean addNormalTexture(byte id, ResourceLocation texture) {
        if( !this.nrmlTextures.containsKey((int) id) ) {
            this.nrmlTextures.put((int) id, texture);
            this.nrmlTextureIds = this.nrmlTextures.keySet().stream().mapToInt(val -> val).toArray();

            return true;
        }

        CsmConstants.LOG.log(Level.DEBUG, String.format("Already registered normal texture with ID %d for team %s!", id, this.name));
        return false;
    }

    @Override
    public boolean addRareTexture(byte id, ResourceLocation texture) {
        if( !this.rareTextures.containsKey((int) id) ) {
            this.rareTextures.put((int) id, texture);
            this.rareTextureIds = this.rareTextures.keySet().stream().mapToInt(val -> val).toArray();

            return true;
        }

        CsmConstants.LOG.log(Level.DEBUG, String.format("Already registered rare texture with ID %d for team %s!", id, this.name));
        return false;
    }

    @Override
    public boolean addUniqueTexture(byte id, ResourceLocation texture) {
        if( !this.uniqTextures.containsKey((int) id) ) {
            this.uniqTextures.put((int) id, texture);
            this.uniqTextureIds = this.uniqTextures.keySet().stream().mapToInt(val -> val).toArray();

            return true;
        }

        CsmConstants.LOG.log(Level.DEBUG, String.format("Already registered unique texture with ID %d for team %s!", id, this.name));
        return false;
    }
}
