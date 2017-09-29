/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry.team;

import de.sanandrew.mods.claysoldiers.api.soldier.ITeamRegistry;
import de.sanandrew.mods.claysoldiers.util.Resources;
import net.minecraft.util.ResourceLocation;

import java.util.UUID;

public final class Teams
{
    public static final UUID SOLDIER_CLAY = UUID.fromString("CA383B5D-DDAF-4523-9001-5A5685EF5FBA");
    public static final UUID SOLDIER_RED = UUID.fromString("77BFA9D5-2E49-4AD3-B6E6-DED0EE1AAD2D");
    public static final UUID SOLDIER_YELLOW = UUID.fromString("BFDC0FF6-BBE2-4C54-ADF6-9599E16D157A");
    public static final UUID SOLDIER_GREEN = UUID.fromString("2C521F69-846F-4294-95DD-2E9C76C19589");
    public static final UUID SOLDIER_BLUE = UUID.fromString("E8BB8A2C-3DEA-4193-AAC9-84E052A02A48");
    public static final UUID SOLDIER_ORANGE = UUID.fromString("F0A0E637-BB71-44BD-AC73-6886503C6FD6");
    public static final UUID SOLDIER_MAGENTA = UUID.fromString("7EB78104-728C-4D36-85A8-98A6B5E2184C");
    public static final UUID SOLDIER_LIGHTBLUE = UUID.fromString("025A1385-278D-41B7-981B-087141F99120");
    public static final UUID SOLDIER_LIME = UUID.fromString("E9FE47F6-EA6E-4467-99A7-70E59A60835B");
    public static final UUID SOLDIER_PINK = UUID.fromString("7A11B9A2-87A7-45F5-939A-D0C121C32D84");
    public static final UUID SOLDIER_CYAN = UUID.fromString("A717B83F-B0EF-4F8F-9829-3CFF6EDF7CEC");
    public static final UUID SOLDIER_PURPLE = UUID.fromString("6EE60BC8-70C9-4941-9EB8-200A0E7AD867");
    public static final UUID SOLDIER_BROWN = UUID.fromString("75B2C91A-BE22-4492-8AC7-CFF4767E37F1");
    public static final UUID SOLDIER_BLACK = UUID.fromString("BC6913E6-859F-4714-9E90-28CE4283E9CC");
    public static final UUID SOLDIER_GRAY = UUID.fromString("77C678AB-ED0D-4E3D-9C8D-B1F8C9600CD3");
    public static final UUID SOLDIER_WHITE = UUID.fromString("7ECD63AF-21A5-42C7-AD32-8988014DA398");
    public static final UUID SOLDIER_MELON = UUID.fromString("400BEDA7-3463-46E9-A01B-16D874ADF728");
    public static final UUID SOLDIER_PUMPKIN = UUID.fromString("81227ECB-F129-4D2E-80C7-07CEC076B53D");
    public static final UUID SOLDIER_REDSTONE = UUID.fromString("0FF36671-62A0-4C41-9567-16A8071FD4AF");
    public static final UUID SOLDIER_COAL = UUID.fromString("F7D936D6-BFCD-48AA-88E5-1C6B12641943");
    public static final UUID SOLDIER_GLASSLIGHTGRAY = UUID.fromString("01CEDD36-E458-4F9B-A2C9-2D371D8B00FD");
    public static final UUID SOLDIER_GLASSRED = UUID.fromString("3734713A-1DEF-4CA5-9EC7-7F7A491D23C2");
    public static final UUID SOLDIER_GLASSYELLOW = UUID.fromString("64FBAD6E-68E6-42B7-930C-43472C5447B5");
    public static final UUID SOLDIER_GLASSGREEN = UUID.fromString("FB247529-33C0-4112-AD3D-4EF2A790FB38");
    public static final UUID SOLDIER_GLASSBLUE = UUID.fromString("DC5A256B-014B-4154-BCDC-20C3E01F40AF");
    public static final UUID SOLDIER_GLASSORANGE = UUID.fromString("2D1C7012-CB7F-435C-8D82-1FA7A71B6ECD");
    public static final UUID SOLDIER_GLASSMAGENTA = UUID.fromString("660C00B5-BBD7-4BAA-A7FD-573B776B853C");
    public static final UUID SOLDIER_GLASSLIGHTBLUE = UUID.fromString("4DB031DB-AA24-43B2-B461-B35883196C2E");
    public static final UUID SOLDIER_GLASSLIME = UUID.fromString("198EDE85-70BE-4E7B-A0D4-20BE68C54244");
    public static final UUID SOLDIER_GLASSPINK = UUID.fromString("1EA9C75F-3459-4D8F-BFF6-A49CFBF496BA");
    public static final UUID SOLDIER_GLASSCYAN = UUID.fromString("65BF9B51-39C0-409A-A176-8DE97E90A9C3");
    public static final UUID SOLDIER_GLASSPURPLE = UUID.fromString("F29329DC-50AF-4AA8-BE18-3FE42233438E");
    public static final UUID SOLDIER_GLASSBROWN = UUID.fromString("E7AC9C83-F31D-477F-BC67-FDBD9AB21C54");
    public static final UUID SOLDIER_GLASSBLACK = UUID.fromString("0100CB85-106F-4498-AC86-ACB269D51C89");
    public static final UUID SOLDIER_GLASSGRAY = UUID.fromString("516A2BE4-DFFC-454A-9D5C-4624C2A2C5B2");
    public static final UUID SOLDIER_GLASSWHITE = UUID.fromString("437EB12D-956A-4881-A4A6-220A8B0FDF4A");
    public static final UUID SOLDIER_CARROT = UUID.fromString("8E7B0ADF-756C-4B25-B352-E9ED21219024");
    public static final UUID SOLDIER_POTATO = UUID.fromString("C52FD8FB-AD68-4F8F-8BBD-EA9D399A735C");
    public static final UUID SOLDIER_BEETROOT = UUID.fromString("F2F99E13-D874-4C23-B454-B3383861E996");

    public static void initialize(ITeamRegistry registry) {
        registry.registerTeam(SOLDIER_CLAY, "clay", Resources.ITEM_SOLDIER_CLAY.resource, 0x8E8E86,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_CLAY.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_CLAY.resource});
        registry.registerTeam(SOLDIER_RED, "red", Resources.ITEM_SOLDIER_CLAY.resource, 0xA22823,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_RED.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_RED.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_U_RED.resource});
        registry.registerTeam(SOLDIER_YELLOW, "yellow", Resources.ITEM_SOLDIER_CLAY.resource, 0xFCD030,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_YELLOW.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_YELLOW_1.resource, Resources.ENTITY_SOLDIER_R_YELLOW_2.resource,
                                                      Resources.ENTITY_SOLDIER_R_YELLOW_3.resource});
        registry.registerTeam(SOLDIER_GREEN, "green", Resources.ITEM_SOLDIER_CLAY.resource, 0x56701B,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_GREEN.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_GREEN.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_U_GREEN_1.resource, Resources.ENTITY_SOLDIER_U_GREEN_2.resource});
        registry.registerTeam(SOLDIER_BLUE, "blue", Resources.ITEM_SOLDIER_CLAY.resource, 0x373CA1,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_BLUE.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_BLUE.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_U_BLUE.resource});
        registry.registerTeam(SOLDIER_ORANGE, "orange", Resources.ITEM_SOLDIER_CLAY.resource, 0xEE7110,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_ORANGE.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_ORANGE.resource});
        registry.registerTeam(SOLDIER_MAGENTA, "magenta", Resources.ITEM_SOLDIER_CLAY.resource, 0xC64EBD,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_MAGENTA.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_MAGENTA.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_U_MAGENTA.resource});
        registry.registerTeam(SOLDIER_LIGHTBLUE, "lightblue", Resources.ITEM_SOLDIER_CLAY.resource, 0x41B7DE,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_LIGHTBLUE.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_LIGHTBLUE.resource});
        registry.registerTeam(SOLDIER_LIME, "lime", Resources.ITEM_SOLDIER_CLAY.resource, 0x77BF1A,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_LIME.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_LIME.resource});
        registry.registerTeam(SOLDIER_PINK, "pink", Resources.ITEM_SOLDIER_CLAY.resource, 0xEF95B2,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_PINK.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_PINK.resource});
        registry.registerTeam(SOLDIER_CYAN, "cyan", Resources.ITEM_SOLDIER_CLAY.resource, 0x159095,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_CYAN.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_CYAN.resource});
        registry.registerTeam(SOLDIER_PURPLE, "purple", Resources.ITEM_SOLDIER_CLAY.resource, 0x7D2BAD,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_PURPLE.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_PURPLE_1.resource, Resources.ENTITY_SOLDIER_R_PURPLE_2.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_U_PURPLE_1.resource, Resources.ENTITY_SOLDIER_U_PURPLE_2.resource});
        registry.registerTeam(SOLDIER_BROWN, "brown", Resources.ITEM_SOLDIER_CLAY.resource, 0x784C2C,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_BROWN.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_BROWN_1.resource, Resources.ENTITY_SOLDIER_R_BROWN_2.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_U_BROWN_1.resource, Resources.ENTITY_SOLDIER_U_BROWN_2.resource});
        registry.registerTeam(SOLDIER_BLACK, "black", Resources.ITEM_SOLDIER_CLAY.resource, 0x19191D,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_BLACK.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_BLACK.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_U_BLACK.resource});
        registry.registerTeam(SOLDIER_GRAY, "gray", Resources.ITEM_SOLDIER_CLAY.resource, 0x545B5E,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_GRAY.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_GRAY.resource});
        registry.registerTeam(SOLDIER_WHITE, "white", Resources.ITEM_SOLDIER_CLAY.resource, 0xEAEDED,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_WHITE.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_WHITE.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_U_WHITE_1.resource, Resources.ENTITY_SOLDIER_U_WHITE_2.resource});
        registry.registerTeam(SOLDIER_MELON, "melon", Resources.ITEM_SOLDIER_MELON.resource, 0xFFFFFF,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_MELON.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_MELON.resource});
        registry.registerTeam(SOLDIER_PUMPKIN, "pumpkin", Resources.ITEM_SOLDIER_PUMPKIN.resource, 0xFFFFFF,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_PUMPKIN_1.resource, Resources.ENTITY_SOLDIER_N_PUMPKIN_2.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_PUMPKIN_1.resource, Resources.ENTITY_SOLDIER_R_PUMPKIN_2.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_U_PUMPKIN_1.resource, Resources.ENTITY_SOLDIER_U_PUMPKIN_2.resource});
        registry.registerTeam(SOLDIER_REDSTONE, "redstone", Resources.ITEM_SOLDIER_REDSTONE.resource, 0xFFFFFF,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_REDSTONE_1.resource, Resources.ENTITY_SOLDIER_N_REDSTONE_2.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_REDSTONE.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_U_REDSTONE_1.resource, Resources.ENTITY_SOLDIER_U_REDSTONE_2.resource});
        registry.registerTeam(SOLDIER_COAL, "coal", Resources.ITEM_SOLDIER_COAL.resource, 0xFFFFFF,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_COAL.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_COAL.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_U_COAL.resource});

        registry.registerTeam(SOLDIER_GLASSLIGHTGRAY, "lightgrayglass", Resources.ITEM_SOLDIER_GLASS.resource, 0x8E8E86,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_GLASSLIGHTGRAY.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_GLASSLIGHTGRAY.resource});
        registry.registerTeam(SOLDIER_GLASSRED, "redglass", Resources.ITEM_SOLDIER_GLASS.resource, 0xA22823,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_GLASSRED.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_GLASSRED.resource});
        registry.registerTeam(SOLDIER_GLASSYELLOW, "yellowglass", Resources.ITEM_SOLDIER_GLASS.resource, 0xFCD030,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_GLASSYELLOW.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_GLASSYELLOW.resource});
        registry.registerTeam(SOLDIER_GLASSGREEN, "greenglass", Resources.ITEM_SOLDIER_GLASS.resource, 0x56701B,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_GLASSGREEN.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_GLASSGREEN.resource});
        registry.registerTeam(SOLDIER_GLASSBLUE, "blueglass", Resources.ITEM_SOLDIER_GLASS.resource, 0x373CA1,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_GLASSBLUE.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_GLASSBLUE.resource});
        registry.registerTeam(SOLDIER_GLASSORANGE, "orangeglass", Resources.ITEM_SOLDIER_GLASS.resource, 0xEE7110,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_GLASSORANGE.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_GLASSORANGE_1.resource, Resources.ENTITY_SOLDIER_R_GLASSORANGE_2.resource});
        registry.registerTeam(SOLDIER_GLASSMAGENTA, "magentaglass", Resources.ITEM_SOLDIER_GLASS.resource, 0xC64EBD,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_GLASSMAGENTA.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_GLASSMAGENTA.resource});
        registry.registerTeam(SOLDIER_GLASSLIGHTBLUE, "lightblueglass", Resources.ITEM_SOLDIER_GLASS.resource, 0x41B7DE,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_GLASSLIGHTBLUE.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_GLASSLIGHTBLUE.resource});
        registry.registerTeam(SOLDIER_GLASSLIME, "limeglass", Resources.ITEM_SOLDIER_GLASS.resource, 0x77BF1A,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_GLASSLIME.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_GLASSLIME.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_U_GLASSLIME.resource});
        registry.registerTeam(SOLDIER_GLASSPINK, "pinkglass", Resources.ITEM_SOLDIER_GLASS.resource, 0xEF95B2,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_GLASSPINK.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_GLASSPINK.resource});
        registry.registerTeam(SOLDIER_GLASSCYAN, "cyanglass", Resources.ITEM_SOLDIER_GLASS.resource, 0x159095,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_GLASSCYAN.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_GLASSCYAN.resource});
        registry.registerTeam(SOLDIER_GLASSPURPLE, "purpleglass", Resources.ITEM_SOLDIER_GLASS.resource, 0x7D2BAD,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_GLASSPURPLE.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_GLASSPURPLE.resource});
        registry.registerTeam(SOLDIER_GLASSBROWN, "brownglass", Resources.ITEM_SOLDIER_GLASS.resource, 0x784C2C,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_GLASSBROWN.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_GLASSBROWN.resource});
        registry.registerTeam(SOLDIER_GLASSBLACK, "blackglass", Resources.ITEM_SOLDIER_GLASS.resource, 0x19191D,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_GLASSBLACK.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_GLASSBLACK.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_U_BLACK.resource});
        registry.registerTeam(SOLDIER_GLASSGRAY, "grayglass", Resources.ITEM_SOLDIER_GLASS.resource, 0x545B5E,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_GLASSGRAY.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_GLASSGRAY.resource});
        registry.registerTeam(SOLDIER_GLASSWHITE, "whiteglass", Resources.ITEM_SOLDIER_GLASS.resource, 0xEAEDED,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_GLASSWHITE.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_GLASSWHITE.resource});

        registry.registerTeam(SOLDIER_CARROT, "carrot", Resources.ITEM_SOLDIER_CARROT.resource, 0xFFFFFF,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_CARROT.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_CARROT.resource});
        registry.registerTeam(SOLDIER_POTATO, "potato", Resources.ITEM_SOLDIER_POTATO.resource, 0xFFFFFF,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_POTATO.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_POTATO.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_U_POTATO.resource});
        registry.registerTeam(SOLDIER_BEETROOT, "beetroot", Resources.ITEM_SOLDIER_BEETROOT.resource, 0xFFFFFF,
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_N_BEETROOT.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_R_BEETROOT.resource},
                              new ResourceLocation[] {Resources.ENTITY_SOLDIER_U_BEETROOT.resource});
    }
}
