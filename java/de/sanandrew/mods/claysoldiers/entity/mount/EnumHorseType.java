/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.mount;

import de.sanandrew.core.manpack.util.javatuples.Pair;
import de.sanandrew.mods.claysoldiers.util.CSM_Main;
import net.minecraft.util.ResourceLocation;

public enum EnumHorseType
{
    DIRT(35.0F, 0.6F, 0x9C5300, CSM_Main.MOD_ID + ":doll_horse", 0x9C5300, new ResourceLocation("claysoldiers", "textures/entity/mount/horses/dirt1.png"),
                                                                           new ResourceLocation("claysoldiers", "textures/entity/mount/horses/dirt2.png"),
                                                                           new ResourceLocation("claysoldiers", "textures/entity/mount/horses/dirt3.png"),
                                                                           new ResourceLocation("claysoldiers", "textures/entity/mount/horses/dirt4.png")
    ),
    SAND(30.0F, 0.7F, 0xF9FF80, CSM_Main.MOD_ID + ":doll_horse", 0xF9FF80, new ResourceLocation("claysoldiers", "textures/entity/mount/horses/sand.png")
    ),
    GRAVEL(45.0F, 0.4F, 0xD1BABA, CSM_Main.MOD_ID + ":doll_horse", 0xD1BABA, new ResourceLocation("claysoldiers", "textures/entity/mount/horses/gravel1.png"),
                                                                             new ResourceLocation("claysoldiers", "textures/entity/mount/horses/gravel2.png")
    ),
    SNOW(40.0F, 0.5F, 0xFFFFFF, CSM_Main.MOD_ID + ":doll_horse", 0xFFFFFF, new ResourceLocation("claysoldiers", "textures/entity/mount/horses/snow.png")
    ),
    GRASS(20.0F, 0.9F, 0x2ABA1A, CSM_Main.MOD_ID + ":doll_horse", 0x2ABA1A, new ResourceLocation("claysoldiers", "textures/entity/mount/horses/grass1.png"),
                                                                            new ResourceLocation("claysoldiers", "textures/entity/mount/horses/grass2.png")
    ),
    LAPIS(35.0F, 0.9F, 0x4430C2, CSM_Main.MOD_ID + ":doll_horse", 0x4430C2, new ResourceLocation("claysoldiers", "textures/entity/mount/horses/lapis.png")
    ),
    CLAY(35.0F, 0.6F, 0xA3A3A3, CSM_Main.MOD_ID + ":doll_horse", 0xA3A3A3, new ResourceLocation("claysoldiers", "textures/entity/mount/horses/clay.png")
    ),
    CARROT(35.0F, 0.9F, 0xF0A800, CSM_Main.MOD_ID + ":doll_horse", 0xF0A800, new ResourceLocation("claysoldiers", "textures/entity/mount/horses/carrot1.png"),
                                                                             new ResourceLocation("claysoldiers", "textures/entity/mount/horses/carrot2.png")
    ),
    SOULSAND(35.0F, 0.8F, 0x5C3100, CSM_Main.MOD_ID + ":doll_horse", 0x5C3100, new ResourceLocation("claysoldiers", "textures/entity/mount/horses/soulsand.png")
    ),
    CAKE(30.0F, 1.1F, 0xFFFFFF, CSM_Main.MOD_ID + ":doll_horse_cake", 0xFFFFFF, new ResourceLocation("claysoldiers", "textures/entity/mount/horses/cake.png")
    ),
    NIGHTMARE(50.0F, 1.2F, 0x000000, new ResourceLocation("claysoldiers", "textures/entity/mount/horses/spec_nightmare1.png"),       // special!
                                     new ResourceLocation("claysoldiers", "textures/entity/mount/horses/spec_nightmare2.png")
    );

    public final float health;
    public final float moveSpeed;
    public final ResourceLocation[] textures;
    public final Pair<String, Integer> itemData;
    public final int typeColor;

    private EnumHorseType(float health, float speed, int typeColor, ResourceLocation... textures) {
        this.health = health;
        this.moveSpeed = speed;
        this.textures = textures;
        this.itemData = null;
        this.typeColor = typeColor;
    }

    private EnumHorseType(float health, float speed, int typeColor, String itemTexture, int itemColor, ResourceLocation... textures) {
        this.health = health;
        this.moveSpeed = speed;
        this.textures = textures;
        this.itemData = Pair.with(itemTexture, itemColor);
        this.typeColor = typeColor;
    }

    public static final EnumHorseType[] values = values();
}
