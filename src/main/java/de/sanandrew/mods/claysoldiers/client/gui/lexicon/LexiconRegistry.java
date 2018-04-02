/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.gui.lexicon;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconGroup;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconPageRender;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconRegistry;
import joptsimple.internal.Strings;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SideOnly(Side.CLIENT)
public final class LexiconRegistry
        implements ILexiconRegistry
{
    public static final LexiconRegistry INSTANCE = new LexiconRegistry();

    private final Map<String, ILexiconGroup> idToGroupMap;
    private final List<ILexiconGroup> groups;
    private final List<ILexiconGroup> groupsRO;
    private final Map<String, ILexiconPageRender> idToPageRenderMap;

    private LexiconRegistry() {
        this.idToGroupMap = new HashMap<>();
        this.groups = new ArrayList<>();
        this.groupsRO = Collections.unmodifiableList(this.groups);
        this.idToPageRenderMap = new HashMap<>();
    }

    @Override
    public boolean registerGroup(ILexiconGroup group) {
        if( group == null ) {
            CsmConstants.LOG.log(Level.ERROR, "Cannot register null as lexicon group!");
            return false;
        }

        String id = group.getId();
        if( Strings.isNullOrEmpty(id) ) {
            CsmConstants.LOG.log(Level.ERROR, "Cannot register a lexicon group without ID!");
            return false;
        }
        if( this.idToGroupMap.containsKey(id) ) {
            CsmConstants.LOG.log(Level.ERROR, "Cannot register a lexicon group with an already registered ID => \"{}\" !", id);
            return false;
        }

        this.idToGroupMap.put(id, group);
        this.groups.add(group);

        return true;
    }

    @Override
    public List<ILexiconGroup> getGroups() {
        return this.groupsRO;
    }

    @Override
    public ILexiconGroup getGroup(String id) {
        return this.idToGroupMap.get(id);
    }

    @Override
    public ILexiconGroup removeGroup(String id) {
        ILexiconGroup group = this.idToGroupMap.get(id);
        if( group != null ) {
            this.idToGroupMap.remove(id);
            this.groups.remove(group);
        }
        return group;
    }

    @Override
    public boolean registerPageRender(ILexiconPageRender render) {
        if( render == null ) {
            CsmConstants.LOG.log(Level.ERROR, "Cannot register null as lexicon page render!");
            return false;
        }

        String id = render.getId();
        if( Strings.isNullOrEmpty(id) ) {
            CsmConstants.LOG.log(Level.ERROR, "Cannot register a lexicon page render without ID!");
            return false;
        }
        if( this.idToGroupMap.containsKey(id) ) {
            CsmConstants.LOG.log(Level.ERROR, "Cannot register a lexicon page render with an already registered ID => \"{}\" !", id);
            return false;
        }

        this.idToPageRenderMap.put(id, render);

        return true;
    }

    @Override
    public ILexiconPageRender getPageRender(String id) {
        return this.idToPageRenderMap.get(id);
    }

    @Override
    public ILexiconPageRender removePageRender(String id) {
        return this.idToPageRenderMap.remove(id);
    }
}
