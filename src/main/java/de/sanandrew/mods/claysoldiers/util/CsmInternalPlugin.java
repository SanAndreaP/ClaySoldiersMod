/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util;

import de.sanandrew.mods.claysoldiers.api.CsmPlugin;
import de.sanandrew.mods.claysoldiers.api.ICsmPlugin;
import de.sanandrew.mods.claysoldiers.api.soldier.ITeamRegistry;
import net.minecraft.util.ResourceLocation;

import java.util.UUID;

@CsmPlugin
public class CsmInternalPlugin
        implements ICsmPlugin
{
    @Override
    public void registerTeams(ITeamRegistry registry) {
        registry.registerTeam(UUID.fromString("CA383B5D-DDAF-4523-9001-5A5685EF5FBA"), "clay", Resources.ITEM_SOLDIER_CLAY.resource, 0xC2C2C2,
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_NORM_CLAY.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_RARE_CLAY.resource});
        registry.registerTeam(UUID.fromString("77BFA9D5-2E49-4AD3-B6E6-DED0EE1AAD2D"), "red", Resources.ITEM_SOLDIER_CLAY.resource, 0xDE0D0D,
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_NORM_RED.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_RARE_RED.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_UNIQ_RED.resource});
        registry.registerTeam(UUID.fromString("BFDC0FF6-BBE2-4C54-ADF6-9599E16D157A"), "yellow", Resources.ITEM_SOLDIER_CLAY.resource, 0xFFFC2E,
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_NORM_YELLOW.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_RARE_YELLOW_1.resource, Resources.ENTITY_SOLDIER_RARE_YELLOW_2.resource, Resources.ENTITY_SOLDIER_RARE_YELLOW_3.resource});
        registry.registerTeam(UUID.fromString("2C521F69-846F-4294-95DD-2E9C76C19589"), "green", Resources.ITEM_SOLDIER_CLAY.resource, 0x0EAB00,
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_NORM_GREEN.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_RARE_GREEN.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_UNIQ_GREEN_1.resource, Resources.ENTITY_SOLDIER_UNIQ_GREEN_2.resource});
        registry.registerTeam(UUID.fromString("E8BB8A2C-3DEA-4193-AAC9-84E052A02A48"), "blue", Resources.ITEM_SOLDIER_CLAY.resource, 0x4949CC,
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_NORM_BLUE.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_RARE_BLUE.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_UNIQ_BLUE.resource});
        registry.registerTeam(UUID.fromString("F0A0E637-BB71-44BD-AC73-6886503C6FD6"), "orange", Resources.ITEM_SOLDIER_CLAY.resource, 0xFFB01C,
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_NORM_ORANGE.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_RARE_ORANGE.resource});
        registry.registerTeam(UUID.fromString("7EB78104-728C-4D36-85A8-98A6B5E2184C"), "magenta", Resources.ITEM_SOLDIER_CLAY.resource, 0xDE57DC,
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_NORM_MAGENTA.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_RARE_MAGENTA.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_UNIQ_MAGENTA.resource});
        registry.registerTeam(UUID.fromString("025A1385-278D-41B7-981B-087141F99120"), "lightblue", Resources.ITEM_SOLDIER_CLAY.resource, 0x91BAFF,
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_NORM_LIGHTBLUE.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_RARE_LIGHTBLUE.resource});
        registry.registerTeam(UUID.fromString("E9FE47F6-EA6E-4467-99A7-70E59A60835B"), "lime", Resources.ITEM_SOLDIER_CLAY.resource, 0x41ED1F,
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_NORM_LIME.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_RARE_LIME.resource});
        registry.registerTeam(UUID.fromString("7A11B9A2-87A7-45F5-939A-D0C121C32D84"), "pink", Resources.ITEM_SOLDIER_CLAY.resource, 0xF5A6F5,
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_NORM_PINK.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_RARE_PINK.resource});
        registry.registerTeam(UUID.fromString("A717B83F-B0EF-4F8F-9829-3CFF6EDF7CEC"), "cyan", Resources.ITEM_SOLDIER_CLAY.resource, 0x14CADE,
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_NORM_CYAN.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_RARE_CYAN.resource});
        registry.registerTeam(UUID.fromString("6EE60BC8-70C9-4941-9EB8-200A0E7AD867"), "purple", Resources.ITEM_SOLDIER_CLAY.resource, 0xA11DC2,
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_NORM_PURPLE.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_RARE_PURPLE_1.resource, Resources.ENTITY_SOLDIER_RARE_PURPLE_2.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_UNIQ_PURPLE_1.resource, Resources.ENTITY_SOLDIER_UNIQ_PURPLE_2.resource});
        registry.registerTeam(UUID.fromString("75B2C91A-BE22-4492-8AC7-CFF4767E37F1"), "brown", Resources.ITEM_SOLDIER_CLAY.resource, 0x8C6522,
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_NORM_BROWN.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_RARE_BROWN_1.resource, Resources.ENTITY_SOLDIER_RARE_BROWN_2.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_UNIQ_BROWN_1.resource, Resources.ENTITY_SOLDIER_UNIQ_BROWN_2.resource});
        registry.registerTeam(UUID.fromString("BC6913E6-859F-4714-9E90-28CE4283E9CC"), "black", Resources.ITEM_SOLDIER_CLAY.resource, 0x202020,
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_NORM_BLACK.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_RARE_BLACK.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_UNIQ_BLACK.resource});
        registry.registerTeam(UUID.fromString("77C678AB-ED0D-4E3D-9C8D-B1F8C9600CD3"), "gray", Resources.ITEM_SOLDIER_CLAY.resource, 0x808080,
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_NORM_GRAY.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_RARE_GRAY.resource});
        registry.registerTeam(UUID.fromString("7ECD63AF-21A5-42C7-AD32-8988014DA398"), "white", Resources.ITEM_SOLDIER_CLAY.resource, 0xFFFFFF,
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_NORM_WHITE.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_RARE_WHITE.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_UNIQ_WHITE_1.resource, Resources.ENTITY_SOLDIER_UNIQ_WHITE_2.resource});

        registry.registerTeam(UUID.fromString("400BEDA7-3463-46E9-A01B-16D874ADF728"), "melon", Resources.ITEM_SOLDIER_MELON.resource, -0x1,
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_NORM_MELON.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_RARE_MELON.resource});
        registry.registerTeam(UUID.fromString("81227ECB-F129-4D2E-80C7-07CEC076B53D"), "pumpkin", Resources.ITEM_SOLDIER_PUMPKIN.resource, -0x1,
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_NORM_PUMPKIN_1.resource, Resources.ENTITY_SOLDIER_NORM_PUMPKIN_2.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_RARE_PUMPKIN_1.resource, Resources.ENTITY_SOLDIER_RARE_PUMPKIN_2.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_UNIQ_PUMPKIN_1.resource, Resources.ENTITY_SOLDIER_UNIQ_PUMPKIN_2.resource});
        registry.registerTeam(UUID.fromString("0FF36671-62A0-4C41-9567-16A8071FD4AF"), "redstone", Resources.ITEM_SOLDIER_REDSTONE.resource, -0x1,
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_NORM_REDSTONE_1.resource, Resources.ENTITY_SOLDIER_NORM_REDSTONE_2.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_RARE_REDSTONE.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_UNIQ_REDSTONE_1.resource, Resources.ENTITY_SOLDIER_UNIQ_REDSTONE_2.resource});
        registry.registerTeam(UUID.fromString("F7D936D6-BFCD-48AA-88E5-1C6B12641943"), "coal", Resources.ITEM_SOLDIER_COAL.resource, -0x1,
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_NORM_COAL.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_RARE_COAL.resource},
                new ResourceLocation[] {Resources.ENTITY_SOLDIER_UNIQ_COAL.resource});
    }
}
