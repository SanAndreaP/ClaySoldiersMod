/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.gui.lexicon.mount;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconEntryCraftingGrid;
import de.sanandrew.mods.claysoldiers.client.gui.lexicon.crafting.LexiconRenderCraftingGrid;
import de.sanandrew.mods.claysoldiers.registry.ItemRegistry;
import de.sanandrew.mods.claysoldiers.registry.mount.EnumTurtleType;
import de.sanandrew.mods.claysoldiers.registry.mount.EnumWoolBunnyType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class LexiconEntryBunny
        implements ILexiconEntryCraftingGrid
{
    private static final String ID = "bunny";
    private final ItemStack[] icons;
    private final ResourceLocation prevPic;

    public LexiconEntryBunny() {
        this.icons = Arrays.stream(EnumWoolBunnyType.VALUES).filter(EnumWoolBunnyType::isVisible).map(ItemRegistry.DOLL_BUNNY::getTypeStack).toArray(ItemStack[]::new);
        this.prevPic = new ResourceLocation(CsmConstants.ID, "textures/gui/lexicon/page_pics/mounts/" + CsmConstants.ID + "_bunnies.png");
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getGroupId() {
        return LexiconGroupMounts.GRP_NAME;
    }

    @Override
    public String getPageRenderId() {
        return LexiconRenderCraftingGrid.ID;
    }

    @Nonnull
    @Override
    public ItemStack getEntryIcon() {
        return this.icons[(int) ((System.nanoTime() / 1_000_000_000) % this.icons.length)];
    }

    @Override
    public ResourceLocation getPicture() {
        return this.prevPic;
    }

    @Override
    public boolean divideAfter() {
        return false;
    }

    @Nonnull
    @Override
    public NonNullList<ItemStack> getRecipeResults() {
        return NonNullList.from(ItemStack.EMPTY, this.icons);
    }
}
