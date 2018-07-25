/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.sanlib.lib.util.LangUtils.TranslateKey;


public final class LangKeys
{
    public static final TranslateKey LEXICON_ENTRY_NAME = new TranslateKey("sanlib.lexicon.%s.%%s.%%s.name", CsmConstants.ID);
    public static final TranslateKey LEXICON_ENTRY_GRIDTEXT = new TranslateKey("sanlib.lexicon.%s.%%s.%%s.gridtext", CsmConstants.ID);
    public static final TranslateKey LEXICON_INFO_ITEM = new TranslateKey("sanlib.lexicon.%s.info.%%s", CsmConstants.ID);
}
