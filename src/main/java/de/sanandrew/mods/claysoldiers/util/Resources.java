/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import net.minecraft.util.ResourceLocation;

public enum Resources
{
    //region soldiers
    ITEM_SOLDIER_CLAY("soldiers/clay"),
    ITEM_SOLDIER_MELON("soldiers/melon"),
    ITEM_SOLDIER_PUMPKIN("soldiers/pumpkin"),
    ITEM_SOLDIER_REDSTONE("soldiers/redstone"),
    ITEM_SOLDIER_COAL("soldiers/coal"),

    ENTITY_SOLDIER_NORM_CLAY("textures/entities/soldiers/lightgray.png"),
    ENTITY_SOLDIER_NORM_RED("textures/entities/soldiers/red.png"),
    ENTITY_SOLDIER_NORM_YELLOW("textures/entities/soldiers/yellow.png"),
    ENTITY_SOLDIER_NORM_GREEN("textures/entities/soldiers/green.png"),
    ENTITY_SOLDIER_NORM_BLUE("textures/entities/soldiers/blue.png"),
    ENTITY_SOLDIER_NORM_ORANGE("textures/entities/soldiers/orange.png"),
    ENTITY_SOLDIER_NORM_MAGENTA("textures/entities/soldiers/magenta.png"),
    ENTITY_SOLDIER_NORM_LIGHTBLUE("textures/entities/soldiers/lightblue.png"),
    ENTITY_SOLDIER_NORM_LIME("textures/entities/soldiers/lime.png"),
    ENTITY_SOLDIER_NORM_PINK("textures/entities/soldiers/pink.png"),
    ENTITY_SOLDIER_NORM_CYAN("textures/entities/soldiers/cyan.png"),
    ENTITY_SOLDIER_NORM_PURPLE("textures/entities/soldiers/purple.png"),
    ENTITY_SOLDIER_NORM_BROWN("textures/entities/soldiers/brown.png"),
    ENTITY_SOLDIER_NORM_BLACK("textures/entities/soldiers/black.png"),
    ENTITY_SOLDIER_NORM_GRAY("textures/entities/soldiers/gray.png"),
    ENTITY_SOLDIER_NORM_WHITE("textures/entities/soldiers/white.png"),
    ENTITY_SOLDIER_NORM_MELON("textures/entities/soldiers/melon.png"),
    ENTITY_SOLDIER_NORM_PUMPKIN_1("textures/entities/soldiers/pumpkin.png"),
    ENTITY_SOLDIER_NORM_PUMPKIN_2("textures/entities/soldiers/pumpkin2.png"),
    ENTITY_SOLDIER_NORM_REDSTONE_1("textures/entities/soldiers/redstone.png"),
    ENTITY_SOLDIER_NORM_REDSTONE_2("textures/entities/soldiers/redstone2.png"),
    ENTITY_SOLDIER_NORM_COAL("textures/entities/soldiers/coal.png"),

    ENTITY_SOLDIER_RARE_CLAY("textures/entities/soldiers/rare/lightgray.png"),
    ENTITY_SOLDIER_RARE_RED("textures/entities/soldiers/rare/red.png"),
    ENTITY_SOLDIER_RARE_YELLOW_1("textures/entities/soldiers/rare/yellow.png"),
    ENTITY_SOLDIER_RARE_YELLOW_2("textures/entities/soldiers/rare/yellow2.png"),
    ENTITY_SOLDIER_RARE_YELLOW_3("textures/entities/soldiers/rare/yellow3.png"),
    ENTITY_SOLDIER_RARE_GREEN("textures/entities/soldiers/rare/green.png"),
    ENTITY_SOLDIER_RARE_BLUE("textures/entities/soldiers/rare/blue.png"),
    ENTITY_SOLDIER_RARE_ORANGE("textures/entities/soldiers/rare/orange.png"),
    ENTITY_SOLDIER_RARE_MAGENTA("textures/entities/soldiers/rare/magenta.png"),
    ENTITY_SOLDIER_RARE_LIGHTBLUE("textures/entities/soldiers/rare/lightblue.png"),
    ENTITY_SOLDIER_RARE_LIME("textures/entities/soldiers/rare/lime.png"),
    ENTITY_SOLDIER_RARE_PINK("textures/entities/soldiers/rare/pink.png"),
    ENTITY_SOLDIER_RARE_CYAN("textures/entities/soldiers/rare/cyan.png"),
    ENTITY_SOLDIER_RARE_PURPLE_1("textures/entities/soldiers/rare/purple.png"),
    ENTITY_SOLDIER_RARE_PURPLE_2("textures/entities/soldiers/rare/purple2.png"),
    ENTITY_SOLDIER_RARE_BROWN_1("textures/entities/soldiers/rare/brown.png"),
    ENTITY_SOLDIER_RARE_BROWN_2("textures/entities/soldiers/rare/brown2.png"),
    ENTITY_SOLDIER_RARE_BLACK("textures/entities/soldiers/rare/black.png"),
    ENTITY_SOLDIER_RARE_GRAY("textures/entities/soldiers/rare/gray.png"),
    ENTITY_SOLDIER_RARE_WHITE("textures/entities/soldiers/rare/white.png"),
    ENTITY_SOLDIER_RARE_MELON("textures/entities/soldiers/rare/melon.png"),
    ENTITY_SOLDIER_RARE_PUMPKIN_1("textures/entities/soldiers/rare/pumpkin.png"),
    ENTITY_SOLDIER_RARE_PUMPKIN_2("textures/entities/soldiers/rare/pumpkin2.png"),
    ENTITY_SOLDIER_RARE_REDSTONE("textures/entities/soldiers/rare/redstone.png"),
    ENTITY_SOLDIER_RARE_COAL("textures/entities/soldiers/rare/coal.png"),

    ENTITY_SOLDIER_UNIQ_RED("textures/entities/soldiers/unique/red.png"),
    ENTITY_SOLDIER_UNIQ_GREEN_1("textures/entities/soldiers/unique/green.png"),
    ENTITY_SOLDIER_UNIQ_GREEN_2("textures/entities/soldiers/unique/green2.png"),
    ENTITY_SOLDIER_UNIQ_BLUE("textures/entities/soldiers/unique/blue.png"),
    ENTITY_SOLDIER_UNIQ_MAGENTA("textures/entities/soldiers/unique/magenta.png"),
    ENTITY_SOLDIER_UNIQ_PURPLE_1("textures/entities/soldiers/unique/purple.png"),
    ENTITY_SOLDIER_UNIQ_PURPLE_2("textures/entities/soldiers/unique/purple2.png"),
    ENTITY_SOLDIER_UNIQ_BROWN_1("textures/entities/soldiers/unique/brown.png"),
    ENTITY_SOLDIER_UNIQ_BROWN_2("textures/entities/soldiers/unique/brown2.png"),
    ENTITY_SOLDIER_UNIQ_BLACK("textures/entities/soldiers/unique/black.png"),
    ENTITY_SOLDIER_UNIQ_WHITE_1("textures/entities/soldiers/unique/white.png"),
    ENTITY_SOLDIER_UNIQ_WHITE_2("textures/entities/soldiers/unique/white2.png"),
    ENTITY_SOLDIER_UNIQ_PUMPKIN_1("textures/entities/soldiers/unique/pumpkin.png"),
    ENTITY_SOLDIER_UNIQ_PUMPKIN_2("textures/entities/soldiers/unique/pumpkin2.png"),
    ENTITY_SOLDIER_UNIQ_REDSTONE_1("textures/entities/soldiers/unique/redstone.png"),
    ENTITY_SOLDIER_UNIQ_REDSTONE_2("textures/entities/soldiers/unique/redstone2.png"),
    ENTITY_SOLDIER_UNIQ_COAL("textures/entities/soldiers/unique/coal.png"),

    ENTITY_SOLDIER_GOGGLES("models/entity/goggles.json");
    //endregion

    public final ResourceLocation resource;

    Resources(String texture) {
        this.resource = new ResourceLocation(CsmConstants.ID, texture);
    }

    @Override
    public String toString() {
        return this.resource.toString();
    }
}
