/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util.mount;

import de.sanandrew.core.manpack.util.javatuples.Triplet;
import de.sanandrew.mods.claysoldiers.util.CSM_Main;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

public enum EnumTurtleType
{
    COBBLE(40.0F, 0.4F, 0x919191, new ItemStack(Blocks.cobblestone), CSM_Main.MOD_ID + ":doll_turtle_shell", 0x4D3225, 0x919191,
        new ResourceLocation(CSM_Main.MOD_ID, "textures/entity/mount/turtle/cobble.png"),
        new ResourceLocation(CSM_Main.MOD_ID, "textures/entity/mount/turtle/cobble2.png")
    ),
    MOSSY(45.0F, 0.3F, 0x86A384, new ItemStack(Blocks.mossy_cobblestone), CSM_Main.MOD_ID + ":doll_turtle_shell", 0x4D3225, 0x86A384,
           new ResourceLocation(CSM_Main.MOD_ID, "textures/entity/mount/turtle/mossy.png")
    ),
    NETHERRACK(35.0F, 0.4F, 0xCC3131, new ItemStack(Blocks.netherrack), CSM_Main.MOD_ID + ":doll_turtle_shell", 0x4D3225, 0xCC3131,
          new ResourceLocation(CSM_Main.MOD_ID, "textures/entity/mount/turtle/netherrack.png")
    ),
    MELON(25.0F, 0.6F, 0x0DDB11, new ItemStack(Blocks.melon_block), CSM_Main.MOD_ID + ":doll_turtle_shell", 0xED5A5A, 0x0DDB11,
               new ResourceLocation(CSM_Main.MOD_ID, "textures/entity/mount/turtle/melon.png"),
               new ResourceLocation(CSM_Main.MOD_ID, "textures/entity/mount/turtle/melon2.png")
    ),
    SANDSTONE(30.0F, 0.4F, 0xFFF5AB, new ItemStack(Blocks.sandstone, 1, OreDictionary.WILDCARD_VALUE), CSM_Main.MOD_ID + ":doll_turtle_shell", 0x4D3225, 0xFFF5AB,
               new ResourceLocation(CSM_Main.MOD_ID, "textures/entity/mount/turtle/sandstone.png"),
               new ResourceLocation(CSM_Main.MOD_ID, "textures/entity/mount/turtle/sandstone2.png"),
               new ResourceLocation(CSM_Main.MOD_ID, "textures/entity/mount/turtle/sandstone3.png")
    ),
    ENDSTONE(45.0F, 0.4F, 0xDBD5A2, new ItemStack(Blocks.end_stone), CSM_Main.MOD_ID + ":doll_turtle_shell", 0x4D3225, 0xDBD5A2,
               new ResourceLocation(CSM_Main.MOD_ID, "textures/entity/mount/turtle/endstone.png")
    ),
    PUMPKIN(25.0F, 0.6F, 0xF2BD0F, new ItemStack(Blocks.pumpkin), CSM_Main.MOD_ID + ":doll_turtle_shell", 0x4D3225, 0xF2BD0F,
             new ResourceLocation(CSM_Main.MOD_ID, "textures/entity/mount/turtle/pumpkin.png"),
             new ResourceLocation(CSM_Main.MOD_ID, "textures/entity/mount/turtle/pumpkin.png")
    ),
    LAPIS(35.0F, 0.6F, 0x270AC9, new ItemStack(Blocks.lapis_block), CSM_Main.MOD_ID + ":doll_turtle_shell", 0x4D3225, 0x270AC9,
            new ResourceLocation(CSM_Main.MOD_ID, "textures/entity/mount/turtle/lapis.png")
    ),
    CAKE(30.0F, 0.6F, 0xFFA1A1, new ItemStack(Items.cake), CSM_Main.MOD_ID + ":doll_turtle_cakeshell", 0xA60000, 0xFFFFFF,
            new ResourceLocation(CSM_Main.MOD_ID, "textures/entity/mount/turtle/cake.png")
    ),
    KAWAKO(50.0F, 0.5F, 0x003300, null,
        new ResourceLocation(CSM_Main.MOD_ID, "textures/entity/mount/turtle/spec_kawako.png")        // special!
    );

    public final float health;
    public final float moveSpeed;
    public final ResourceLocation[] textures;
    public final Triplet<String, Integer, Integer> itemData;
    public final int typeColor;
    public final ItemStack item;

    private EnumTurtleType(float health, float speed, int typeColor, ItemStack materialItem, ResourceLocation... textures) {
        this.health = health;
        this.moveSpeed = speed;
        this.textures = textures;
        this.itemData = null;
        this.typeColor = typeColor;
        this.item = materialItem;
    }

    private EnumTurtleType(float health, float speed, int typeColor, ItemStack materialItem, String shellTexture, int itemColorBody, int itemColorShell,
                           ResourceLocation... textures) {
        this.health = health;
        this.moveSpeed = speed;
        this.textures = textures;
        this.itemData = Triplet.with(shellTexture, itemColorBody, itemColorShell);
        this.typeColor = typeColor;
        this.item = materialItem;
    }

    public static final EnumTurtleType[] values = values();
}
