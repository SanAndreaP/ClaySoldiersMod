/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.api.client.lexicon;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@SideOnly(Side.CLIENT)
public interface ILexiconRegistry
{
    boolean registerGroup(ILexiconGroup group);

    List<ILexiconGroup> getGroups();

    ILexiconGroup getGroup(String id);

    ILexiconGroup removeGroup(String id);

    boolean registerPageRender(ILexiconPageRender render);

    ILexiconPageRender getPageRender(String id);

    ILexiconPageRender removePageRender(String id);
}
