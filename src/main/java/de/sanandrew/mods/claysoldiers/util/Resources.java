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
    ITEM_SOLDIER_GLASS("soldiers/glass"),
    ITEM_SOLDIER_CARROT("soldiers/carrot"),
    ITEM_SOLDIER_POTATO("soldiers/potato"),
    ITEM_SOLDIER_BEETROOT("soldiers/beetroot"),

    ENTITY_SOLDIER_N_CLAY("textures/entities/soldiers/lightgray.png"),
    ENTITY_SOLDIER_N_RED("textures/entities/soldiers/red.png"),
    ENTITY_SOLDIER_N_YELLOW("textures/entities/soldiers/yellow.png"),
    ENTITY_SOLDIER_N_GREEN("textures/entities/soldiers/green.png"),
    ENTITY_SOLDIER_N_BLUE("textures/entities/soldiers/blue.png"),
    ENTITY_SOLDIER_N_ORANGE("textures/entities/soldiers/orange.png"),
    ENTITY_SOLDIER_N_MAGENTA("textures/entities/soldiers/magenta.png"),
    ENTITY_SOLDIER_N_LIGHTBLUE("textures/entities/soldiers/lightblue.png"),
    ENTITY_SOLDIER_N_LIME("textures/entities/soldiers/lime.png"),
    ENTITY_SOLDIER_N_PINK("textures/entities/soldiers/pink.png"),
    ENTITY_SOLDIER_N_CYAN("textures/entities/soldiers/cyan.png"),
    ENTITY_SOLDIER_N_PURPLE("textures/entities/soldiers/purple.png"),
    ENTITY_SOLDIER_N_BROWN("textures/entities/soldiers/brown.png"),
    ENTITY_SOLDIER_N_BLACK("textures/entities/soldiers/black.png"),
    ENTITY_SOLDIER_N_GRAY("textures/entities/soldiers/gray.png"),
    ENTITY_SOLDIER_N_WHITE("textures/entities/soldiers/white.png"),
    ENTITY_SOLDIER_N_MELON("textures/entities/soldiers/melon.png"),
    ENTITY_SOLDIER_N_PUMPKIN_1("textures/entities/soldiers/pumpkin.png"),
    ENTITY_SOLDIER_N_PUMPKIN_2("textures/entities/soldiers/pumpkin2.png"),
    ENTITY_SOLDIER_N_REDSTONE_1("textures/entities/soldiers/redstone.png"),
    ENTITY_SOLDIER_N_REDSTONE_2("textures/entities/soldiers/redstone2.png"),
    ENTITY_SOLDIER_N_COAL("textures/entities/soldiers/coal.png"),
    ENTITY_SOLDIER_N_GLASSLIGHTGRAY("textures/entities/soldiers/lightgrayglass.png"),
    ENTITY_SOLDIER_N_GLASSRED("textures/entities/soldiers/redglass.png"),
    ENTITY_SOLDIER_N_GLASSYELLOW("textures/entities/soldiers/yellowglass.png"),
    ENTITY_SOLDIER_N_GLASSGREEN("textures/entities/soldiers/greenglass.png"),
    ENTITY_SOLDIER_N_GLASSBLUE("textures/entities/soldiers/blueglass.png"),
    ENTITY_SOLDIER_N_GLASSORANGE("textures/entities/soldiers/orangeglass.png"),
    ENTITY_SOLDIER_N_GLASSMAGENTA("textures/entities/soldiers/magentaglass.png"),
    ENTITY_SOLDIER_N_GLASSLIGHTBLUE("textures/entities/soldiers/lightblueglass.png"),
    ENTITY_SOLDIER_N_GLASSLIME("textures/entities/soldiers/limeglass.png"),
    ENTITY_SOLDIER_N_GLASSPINK("textures/entities/soldiers/pinkglass.png"),
    ENTITY_SOLDIER_N_GLASSCYAN("textures/entities/soldiers/cyanglass.png"),
    ENTITY_SOLDIER_N_GLASSPURPLE("textures/entities/soldiers/purpleglass.png"),
    ENTITY_SOLDIER_N_GLASSBROWN("textures/entities/soldiers/brownglass.png"),
    ENTITY_SOLDIER_N_GLASSBLACK("textures/entities/soldiers/blackglass.png"),
    ENTITY_SOLDIER_N_GLASSGRAY("textures/entities/soldiers/grayglass.png"),
    ENTITY_SOLDIER_N_GLASSWHITE("textures/entities/soldiers/whiteglass.png"),
    ENTITY_SOLDIER_N_CARROT("textures/entities/soldiers/carrot.png"),
    ENTITY_SOLDIER_N_POTATO("textures/entities/soldiers/potato.png"),
    ENTITY_SOLDIER_N_BEETROOT("textures/entities/soldiers/beetroot.png"),

    ENTITY_SOLDIER_R_CLAY("textures/entities/soldiers/rare/lightgray.png"),
    ENTITY_SOLDIER_R_RED("textures/entities/soldiers/rare/red.png"),
    ENTITY_SOLDIER_R_YELLOW_1("textures/entities/soldiers/rare/yellow.png"),
    ENTITY_SOLDIER_R_YELLOW_2("textures/entities/soldiers/rare/yellow2.png"),
    ENTITY_SOLDIER_R_YELLOW_3("textures/entities/soldiers/rare/yellow3.png"),
    ENTITY_SOLDIER_R_GREEN("textures/entities/soldiers/rare/green.png"),
    ENTITY_SOLDIER_R_BLUE("textures/entities/soldiers/rare/blue.png"),
    ENTITY_SOLDIER_R_ORANGE("textures/entities/soldiers/rare/orange.png"),
    ENTITY_SOLDIER_R_MAGENTA("textures/entities/soldiers/rare/magenta.png"),
    ENTITY_SOLDIER_R_LIGHTBLUE("textures/entities/soldiers/rare/lightblue.png"),
    ENTITY_SOLDIER_R_LIME("textures/entities/soldiers/rare/lime.png"),
    ENTITY_SOLDIER_R_PINK("textures/entities/soldiers/rare/pink.png"),
    ENTITY_SOLDIER_R_CYAN("textures/entities/soldiers/rare/cyan.png"),
    ENTITY_SOLDIER_R_PURPLE_1("textures/entities/soldiers/rare/purple.png"),
    ENTITY_SOLDIER_R_PURPLE_2("textures/entities/soldiers/rare/purple2.png"),
    ENTITY_SOLDIER_R_BROWN_1("textures/entities/soldiers/rare/brown.png"),
    ENTITY_SOLDIER_R_BROWN_2("textures/entities/soldiers/rare/brown2.png"),
    ENTITY_SOLDIER_R_BLACK("textures/entities/soldiers/rare/black.png"),
    ENTITY_SOLDIER_R_GRAY("textures/entities/soldiers/rare/gray.png"),
    ENTITY_SOLDIER_R_WHITE("textures/entities/soldiers/rare/white.png"),
    ENTITY_SOLDIER_R_MELON("textures/entities/soldiers/rare/melon.png"),
    ENTITY_SOLDIER_R_PUMPKIN_1("textures/entities/soldiers/rare/pumpkin.png"),
    ENTITY_SOLDIER_R_PUMPKIN_2("textures/entities/soldiers/rare/pumpkin2.png"),
    ENTITY_SOLDIER_R_REDSTONE("textures/entities/soldiers/rare/redstone.png"),
    ENTITY_SOLDIER_R_COAL("textures/entities/soldiers/rare/coal.png"),
    ENTITY_SOLDIER_R_GLASSLIGHTGRAY("textures/entities/soldiers/rare/lightgrayglass.png"),
    ENTITY_SOLDIER_R_GLASSRED("textures/entities/soldiers/rare/redglass.png"),
    ENTITY_SOLDIER_R_GLASSYELLOW("textures/entities/soldiers/rare/yellowglass.png"),
    ENTITY_SOLDIER_R_GLASSGREEN("textures/entities/soldiers/rare/greenglass.png"),
    ENTITY_SOLDIER_R_GLASSBLUE("textures/entities/soldiers/rare/blueglass.png"),
    ENTITY_SOLDIER_R_GLASSORANGE_1("textures/entities/soldiers/rare/orangeglass.png"),
    ENTITY_SOLDIER_R_GLASSORANGE_2("textures/entities/soldiers/rare/orangeglass2.png"),
    ENTITY_SOLDIER_R_GLASSMAGENTA("textures/entities/soldiers/rare/magentaglass.png"),
    ENTITY_SOLDIER_R_GLASSLIGHTBLUE("textures/entities/soldiers/rare/lightblueglass.png"),
    ENTITY_SOLDIER_R_GLASSLIME("textures/entities/soldiers/rare/limeglass.png"),
    ENTITY_SOLDIER_R_GLASSPINK("textures/entities/soldiers/rare/pinkglass.png"),
    ENTITY_SOLDIER_R_GLASSCYAN("textures/entities/soldiers/rare/cyanglass.png"),
    ENTITY_SOLDIER_R_GLASSPURPLE("textures/entities/soldiers/rare/purpleglass.png"),
    ENTITY_SOLDIER_R_GLASSBROWN("textures/entities/soldiers/rare/brownglass.png"),
    ENTITY_SOLDIER_R_GLASSBLACK("textures/entities/soldiers/rare/blackglass.png"),
    ENTITY_SOLDIER_R_GLASSGRAY("textures/entities/soldiers/rare/grayglass.png"),
    ENTITY_SOLDIER_R_GLASSWHITE("textures/entities/soldiers/rare/whiteglass.png"),
    ENTITY_SOLDIER_R_CARROT("textures/entities/soldiers/rare/carrot.png"),
    ENTITY_SOLDIER_R_POTATO("textures/entities/soldiers/rare/potato.png"),
    ENTITY_SOLDIER_R_BEETROOT("textures/entities/soldiers/rare/beetroot.png"),

    ENTITY_SOLDIER_U_RED("textures/entities/soldiers/unique/red.png"),
    ENTITY_SOLDIER_U_GREEN_1("textures/entities/soldiers/unique/green.png"),
    ENTITY_SOLDIER_U_GREEN_2("textures/entities/soldiers/unique/green2.png"),
    ENTITY_SOLDIER_U_BLUE("textures/entities/soldiers/unique/blue.png"),
    ENTITY_SOLDIER_U_MAGENTA("textures/entities/soldiers/unique/magenta.png"),
    ENTITY_SOLDIER_U_PURPLE_1("textures/entities/soldiers/unique/purple.png"),
    ENTITY_SOLDIER_U_PURPLE_2("textures/entities/soldiers/unique/purple2.png"),
    ENTITY_SOLDIER_U_BROWN_1("textures/entities/soldiers/unique/brown.png"),
    ENTITY_SOLDIER_U_BROWN_2("textures/entities/soldiers/unique/brown2.png"),
    ENTITY_SOLDIER_U_BLACK("textures/entities/soldiers/unique/black.png"),
    ENTITY_SOLDIER_U_WHITE_1("textures/entities/soldiers/unique/white.png"),
    ENTITY_SOLDIER_U_WHITE_2("textures/entities/soldiers/unique/white2.png"),
    ENTITY_SOLDIER_U_PUMPKIN_1("textures/entities/soldiers/unique/pumpkin.png"),
    ENTITY_SOLDIER_U_PUMPKIN_2("textures/entities/soldiers/unique/pumpkin2.png"),
    ENTITY_SOLDIER_U_REDSTONE_1("textures/entities/soldiers/unique/redstone.png"),
    ENTITY_SOLDIER_U_REDSTONE_2("textures/entities/soldiers/unique/redstone2.png"),
    ENTITY_SOLDIER_U_COAL("textures/entities/soldiers/unique/coal.png"),
    ENTITY_SOLDIER_U_GLASSLIME("textures/entities/soldiers/unique/limeglass.png"),
    ENTITY_SOLDIER_U_POTATO("textures/entities/soldiers/unique/potato.png"),
    ENTITY_SOLDIER_U_BEETROOT("textures/entities/soldiers/unique/beetroot.png"),

    ENTITY_WEARABLE_GUNPOWDER("textures/entities/wearables/gunpowder.png"),
    ENTITY_WEARABLE_GOLD_HOODIE("textures/entities/wearables/gold_hoodie.png"),
    ENTITY_WEARABLE_CAPE_PAPER("textures/entities/wearables/cape_blank.png"),
    ENTITY_WEARABLE_CAPE_DIAMOND("textures/entities/wearables/cape_diamond.png"),

    MODEL_WEARABLE_GOGGLES("models/entity/goggles.json"),
    MODEL_SOLDIER_CROWN("models/entity/crown.json"),
    MODEL_SOLDIER_LEATHER_ARMOR("models/entity/leather.json"),
    MODEL_SOLDIER_RABBIT_HIDE("models/entity/rabbithide.json"),
    MODEL_SOLDIER_WOOL_PADDING("models/entity/woolpadding.json"),
    MODEL_SOLDIER_LILYPANTS("models/entity/lilypants.json"),
    //endregion

    MODEL_CLAY_HORSE("models/entity/mount/horse.json"),
    MODEL_CLAY_PEGASUS("models/entity/mount/pegasus.json"),
    MODEL_TURTLE("models/entity/mount/turtle.json"),
    MODEL_BUNNY("models/entity/mount/bunny.json"),
    MODEL_GECKO("models/entity/mount/gecko.json"),

    LIGHTNING_OVERLAY("minecraft", "textures/entity/creeper/creeper_armor.png"),

    GUI_LEXICON("textures/gui/lexicon/background.png"),
    GUI_GROUPICON_UPGRADES("textures/gui/lexicon/group_upgrades.png"),

    SHADER_STENCIL("shader/stencil.frag"),

    STENCIL_LEXICON_GRP("textures/gui/lexicon/group_stencil.png");

    public final ResourceLocation resource;

    Resources(String texture) {
        this(CsmConstants.ID, texture);
    }

    Resources(String domain, String texture) {
        this.resource = new ResourceLocation(domain, texture);
    }

    @Override
    public String toString() {
        return this.resource.toString();
    }
}
