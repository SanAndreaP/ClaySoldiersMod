/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.gui.lexicon;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconEntry;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconGroup;
import joptsimple.internal.Strings;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Level;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@SideOnly(Side.CLIENT)
public final class LexiconGroup
        implements ILexiconGroup
{
    private final String id;
    private final ResourceLocation icon;
    private final Map<String, ILexiconEntry> idToEntryMap;
    private final List<ILexiconEntry> entries;
    private final List<ILexiconEntry> entriesRO;

    public LexiconGroup(String id, ResourceLocation icon) {
        this.id = id;
        this.icon = icon;
        this.idToEntryMap = new HashMap<>();
        this.entries = new LinkedList<>();
        this.entriesRO = Collections.unmodifiableList(this.entries);
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getGroupName() {
        return I18n.format(String.format("%s.lexicon.%s.name", CsmConstants.ID, this.id));
    }

    @Override
    public ResourceLocation getIcon() {
        return this.icon;
    }

    @Override
    public List<ILexiconEntry> getEntries() {
        return this.entriesRO;
    }

    @Override
    public ILexiconEntry getEntry(String id) {
        return this.idToEntryMap.get(id);
    }

    @Override
    public boolean addEntry(ILexiconEntry entry) {
        if( entry == null ) {
            CsmConstants.LOG.log(Level.ERROR, "Cannot register null as lexicon entry for group {}!", this.id);
            return false;
        }

        String id = entry.getId();
        if( Strings.isNullOrEmpty(id) ) {
            CsmConstants.LOG.log(Level.ERROR, "Cannot register a lexicon entry without ID for group {}!", this.id);
            return false;
        }
        if( this.idToEntryMap.containsKey(id) ) {
            CsmConstants.LOG.log(Level.ERROR, "Cannot register a lexicon entry with an already registered ID => \"{}\" for group {}!", id, this.id);
            return false;
        }

        this.idToEntryMap.put(id, entry);
        this.entries.add(entry);

        return true;
    }

    @Override
    public ILexiconEntry removeEntry(String id) {
        ILexiconEntry entry = this.idToEntryMap.get(id);
        if( entry != null ) {
            this.idToEntryMap.remove(id);
            this.entries.remove(entry);
        }
        return entry;
    }
}
