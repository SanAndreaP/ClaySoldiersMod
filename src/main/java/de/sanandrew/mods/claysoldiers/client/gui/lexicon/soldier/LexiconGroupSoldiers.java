/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.gui.lexicon.soldier;

import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconGroup;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconRegistry;
import de.sanandrew.mods.claysoldiers.client.gui.lexicon.LexiconGroup;
import de.sanandrew.mods.claysoldiers.util.Resources;
import net.minecraft.util.ResourceLocation;

public class LexiconGroupSoldiers
        extends LexiconGroup
{
    public static final String GRP_NAME = "soldiers";

    protected LexiconGroupSoldiers() {
        super(GRP_NAME, Resources.GUI_GROUPICON_SOLDIERS.resource);
    }

    public static void register(ILexiconRegistry registry) {
        ILexiconGroup grp = new LexiconGroupSoldiers();
        registry.registerGroup(grp);


    }
}
