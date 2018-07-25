/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.gui.lexicon;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.client.util.Shaders;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import de.sanandrew.mods.claysoldiers.util.CsmConfig;
import de.sanandrew.mods.claysoldiers.util.Resources;
import de.sanandrew.mods.sanlib.api.client.lexicon.ILexicon;
import de.sanandrew.mods.sanlib.api.client.lexicon.ILexiconInst;
import net.minecraft.util.ResourceLocation;

@de.sanandrew.mods.sanlib.api.client.lexicon.Lexicon
public class Lexicon
        implements ILexicon
{
    @Override
    public String getModId() {
        return CsmConstants.ID;
    }

    @Override
    public int getGuiSizeX() {
        return 200;
    }

    @Override
    public int getGuiSizeY() {
        return 213;
    }

    @Override
    public int getEntryPosX() {
        return 12;
    }

    @Override
    public int getEntryPosY() {
        return 19;
    }

    @Override
    public int getEntryWidth() {
        return 165;
    }

    @Override
    public int getEntryHeight() {
        return 165;
    }

    @Override
    public int getTitleColor() {
        return 0xFF8A4500;
    }

    @Override
    public int getTextColor() {
        return 0xFF000000;
    }

    @Override
    public int getLinkColor() {
        return 0xFF0080FF;
    }

    @Override
    public int getLinkVisitedColor() {
        return 0xFF808080;
    }

    @Override
    public int getGroupStencilId() {
        return Shaders.stencil;
    }

    @Override
    public ResourceLocation getGroupStencilTexture() {
        return Resources.STENCIL_LEXICON_GRP.resource;
    }

    @Override
    public ResourceLocation getGroupSearchIcon() {
        return Resources.GUI_GROUPICON_SEARCH.resource;
    }

    @Override
    public boolean forceUnicode() {
        return CsmConfig.Lexicon.lexiconForceUnicode;
    }

    @Override
    public ResourceLocation getBackgroundTexture() {
        return Resources.GUI_LEXICON.resource;
    }

    @Override
    public void initialize(ILexiconInst iLexiconInst) {
        ClaySoldiersMod.PLUGINS.forEach(plugin -> plugin.registerLexicon(iLexiconInst));
    }
}
