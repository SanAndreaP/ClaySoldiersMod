/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.api.client.lexicon;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

@SideOnly(Side.CLIENT)
public interface ILexiconEntry
{
    String getId();

    String getGroupId();

    String getPageRenderId();

    @Nonnull ItemStack getEntryIcon();

    @Nonnull String getSrcTitle();

    @Nonnull String getSrcText();

    default ResourceLocation getPicture() {
        return null;
    }

    default boolean divideAfter() {
        return false;
    }
}
