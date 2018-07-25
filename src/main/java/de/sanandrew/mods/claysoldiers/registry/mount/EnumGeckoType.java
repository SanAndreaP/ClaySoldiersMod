/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry.mount;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.doll.IDollType;
import de.sanandrew.mods.claysoldiers.registry.ItemRegistry;
import de.sanandrew.mods.claysoldiers.util.CsmConfig;
import de.sanandrew.mods.sanlib.lib.util.config.Category;
import de.sanandrew.mods.sanlib.lib.util.config.EnumExclude;
import de.sanandrew.mods.sanlib.lib.util.config.Range;
import de.sanandrew.mods.sanlib.lib.util.config.Value;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;

@Category(value = EnumGeckoType.CFG_CAT, comment = "Gecko entity configuration")
public enum EnumGeckoType
        implements IDollType
{
    OAK_OAK(true, 0x4E9C39, 0x7F6139, "oak", "oak"),
    OAK_PINE(true, 0x4E9C39, 0x2E1D0A, "oak", "pine"),
    OAK_BIRCH(true, 0x4E9C39, 0xCFE3BA, "oak", "birch"),
    OAK_JUNGLE(true, 0x4E9C39, 0x181F05, "oak", "jungle"),
    OAK_ACACIA(true, 0x4E9C39, 0x846412, "oak", "acacia"),
    OAK_DARKOAK(true, 0x4E9C39, 0x442D12, "oak", "darkoak"),
    PINE_OAK(true, 0x395A39, 0x7F6139, "pine", "oak"),
    PINE_PINE(true, 0x395A39, 0x2E1D0A, "pine", "pine"),
    PINE_BIRCH(true, 0x395A39, 0xCFE3BA, "pine", "birch"),
    PINE_JUNGLE(true, 0x395A39, 0x181F05, "pine", "jungle"),
    PINE_ACACIA(true, 0x395A39, 0x846412, "pine", "acacia"),
    PINE_DARKOAK(true, 0x395A39, 0x442D12, "pine", "darkoak"),
    BIRCH_OAK(true, 0x90C679, 0x7F6139, "birch", "oak"),
    BIRCH_PINE(true, 0x90C679, 0x2E1D0A, "birch", "pine"),
    BIRCH_BIRCH(true, 0x90C679, 0xCFE3BA, "birch", "birch"),
    BIRCH_JUNGLE(true, 0x90C679, 0x181F05, "birch", "jungle"),
    BIRCH_ACACIA(true, 0x90C679, 0x846412, "birch", "acacia"),
    BIRCH_DARKOAK(true, 0x90C679, 0x442D12, "birch", "darkoak"),
    JUNGLE_OAK(true, 0x378020, 0x7F6139, "jungle", "oak"),
    JUNGLE_PINE(true, 0x378020, 0x2E1D0A, "jungle", "pine"),
    JUNGLE_BIRCH(true, 0x378020, 0xCFE3BA, "jungle", "birch"),
    JUNGLE_JUNGLE(true, 0x378020, 0x181F05, "jungle", "jungle"),
    JUNGLE_ACACIA(true, 0x378020, 0x846412, "jungle", "acacia"),
    JUNGLE_DARKOAK(true, 0x378020, 0x442D12, "jungle", "darkoak"),
    ACACIA_OAK(true, 0x72891B, 0x7F6139, "acacia", "oak"),
    ACACIA_PINE(true, 0x72891B, 0x2E1D0A, "acacia", "pine"),
    ACACIA_BIRCH(true, 0x72891B, 0xCFE3BA, "acacia", "birch"),
    ACACIA_JUNGLE(true, 0x72891B, 0x181F05, "acacia", "jungle"),
    ACACIA_ACACIA(true, 0x72891B, 0x846412, "acacia", "acacia"),
    ACACIA_DARKOAK(true, 0x72891B, 0x442D12, "acacia", "darkoak"),
    DARKOAK_OAK(true, 0x459633, 0x7F6139, "darkoak", "oak"),
    DARKOAK_PINE(true, 0x459633, 0x2E1D0A, "darkoak", "pine"),
    DARKOAK_BIRCH(true, 0x459633, 0xCFE3BA, "darkoak", "birch"),
    DARKOAK_JUNGLE(true, 0x459633, 0x181F05, "darkoak", "jungle"),
    DARKOAK_ACACIA(true, 0x459633, 0x846412, "darkoak", "acacia"),
    DARKOAK_DARKOAK(true, 0x459633, 0x442D12, "darkoak", "darkoak"),

    @EnumExclude
    UNKNOWN(false, 0x0, 0x0, null, null);

    public static final String CFG_CAT = CsmConfig.Entities.CAT_NAME + Configuration.CATEGORY_SPLITTER + "geckos";
    public static final EnumGeckoType[] VALUES = values();

    @Value(comment = "Maximum health of this type of gecko", range = @Range(minD = 0.0D, maxD = 1024.0D))
    public float maxHealth;
    @Value(comment = "Movement speed of this type of gecko", range = @Range(minD = 0.0D, maxD = 256.0D))
    public float movementSpeed;
    public final boolean visible;
    public final int itemColorBody;
    public final int itemColorSpots;
    public final ResourceLocation textureBody;
    public final ResourceLocation textureSpots;

    EnumGeckoType(boolean visible, int itemColorBody, int itemColorSpots, String textureBody, String textureSpots) {
        this.maxHealth = 20.0F;
        this.movementSpeed = 1.0F;
        this.visible = visible;
        this.itemColorBody = itemColorBody;
        this.itemColorSpots = itemColorSpots;
        this.textureBody = new ResourceLocation(CsmConstants.ID, String.format("textures/entities/mount/gecko/body_%s.png", textureBody));
        this.textureSpots = new ResourceLocation(CsmConstants.ID, String.format("textures/entities/mount/gecko/spots_%s.png", textureSpots));
    }

    @Override
    public boolean isVisible() {
        return this.visible;
    }

    @Override
    public boolean isValid() {
        return this != UNKNOWN;
    }

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public int getItemColor() {
        return this.itemColorSpots;
    }

    @Override
    public ItemStack getTypeStack() {
        return ItemRegistry.DOLL_GECKO.getTypeStack(this);
    }
}
