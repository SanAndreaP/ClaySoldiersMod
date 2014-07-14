package de.sanandrew.mods.claysoldiers.entity.mounts;

import de.sanandrew.core.manpack.util.javatuples.Pair;
import de.sanandrew.mods.claysoldiers.util.CSM_Main;
import net.minecraft.util.ResourceLocation;

public enum EnumHorseType
{
    DIRT(35.0F, 0.6F, CSM_Main.MOD_ID + ":doll_horse", 0x9C5300, new ResourceLocation("claysoldiers", "textures/entity/horses/dirt1.png"),
                                                                      new ResourceLocation("claysoldiers", "textures/entity/horses/dirt2.png"),
                                                                      new ResourceLocation("claysoldiers", "textures/entity/horses/dirt3.png"),
                                                                      new ResourceLocation("claysoldiers", "textures/entity/horses/dirt4.png")
    ),
    SAND(30.0F, 0.7F, CSM_Main.MOD_ID + ":doll_horse", 0xF9FF80, new ResourceLocation("claysoldiers", "textures/entity/horses/sand.png")
    ),
    GRAVEL(45.0F, 0.4F, CSM_Main.MOD_ID + ":doll_horse", 0xD1BABA, new ResourceLocation("claysoldiers", "textures/entity/horses/gravel1.png"),
                                                                        new ResourceLocation("claysoldiers", "textures/entity/horses/gravel2.png")
    ),
    SNOW(40.0F, 0.5F, CSM_Main.MOD_ID + ":doll_horse", 0xFFFFFF, new ResourceLocation("claysoldiers", "textures/entity/horses/snow.png")
    ),
    GRASS(20.0F, 0.9F, CSM_Main.MOD_ID + ":doll_horse", 0x2ABA1A, new ResourceLocation("claysoldiers", "textures/entity/horses/grass1.png"),
                                                                       new ResourceLocation("claysoldiers", "textures/entity/horses/grass2.png")
    ),
    LAPIS(35.0F, 0.9F, CSM_Main.MOD_ID + ":doll_horse", 0x4430C2, new ResourceLocation("claysoldiers", "textures/entity/horses/lapis.png")
    ),
    CLAY(35.0F, 0.6F, CSM_Main.MOD_ID + ":doll_horse", 0xA3A3A3, new ResourceLocation("claysoldiers", "textures/entity/horses/clay.png")
    ),
    CARROT(35.0F, 0.9F, CSM_Main.MOD_ID + ":doll_horse", 0xF0A800, new ResourceLocation("claysoldiers", "textures/entity/horses/carrot1.png"),
                             new ResourceLocation("claysoldiers", "textures/entity/horses/carrot2.png")
    ),
    SOULSAND(35.0F, 0.8F, CSM_Main.MOD_ID + ":doll_horse", 0x5C3100, new ResourceLocation("claysoldiers", "textures/entity/horses/soulsand.png")
    ),
    CAKE(30.0F, 1.1F, CSM_Main.MOD_ID + ":doll_horse_cake", 0xFFFFFF, new ResourceLocation("claysoldiers", "textures/entity/horses/cake.png")
    ),
    NIGHTMARE(50.0F, 1.2F, new ResourceLocation("claysoldiers", "textures/entity/horses/spec_nightmare1.png"),       // special!
                                new ResourceLocation("claysoldiers", "textures/entity/horses/spec_nightmare2.png")
    );

    public final float health;
    public final float moveSpeed;
    public final ResourceLocation[] textures;
    public final Pair<String, Integer> itemData;

    private EnumHorseType(float health, float speed, ResourceLocation... textures) {
        this.health = health;
        this.moveSpeed = speed;
        this.textures = textures;
        this.itemData = null;
    }

    private EnumHorseType(float health, float speed, String itemTexture, int itemColor, ResourceLocation... textures) {
        this.health = health;
        this.moveSpeed = speed;
        this.textures = textures;
        this.itemData = Pair.with(itemTexture, itemColor);
    }

    public static final EnumHorseType[] values = values();
}
