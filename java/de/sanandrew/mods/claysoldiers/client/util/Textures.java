/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.util;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import de.sanandrew.mods.claysoldiers.util.CSM_Main;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;

public final class Textures {
    public static IIcon shieldIcon;
    public static IIcon shieldStudIcon;
    public static IIcon starfruitShieldIcon;
    public static IIcon starfruitShieldStudIcon;

    public static final ResourceLocation CLAYMAN_LEATHER_ARMOR = new ResourceLocation(CSM_Main.MOD_ID, "textures/entity/wearables/leather.png");
    public static final ResourceLocation CLAYMAN_PADDING = new ResourceLocation(CSM_Main.MOD_ID, "textures/entity/wearables/padding.png");
    public static final ResourceLocation CLAYMAN_GUNPOWDER = new ResourceLocation(CSM_Main.MOD_ID, "textures/entity/wearables/gunpowder.png");
    public static final ResourceLocation CLAYMAN_MAGMACREAM = new ResourceLocation(CSM_Main.MOD_ID, "textures/entity/wearables/magmacream.png");
    public static final ResourceLocation CLAYMAN_SLIMEFEET = new ResourceLocation(CSM_Main.MOD_ID, "textures/entity/wearables/slimefeet.png");
    public static final ResourceLocation CLAYMAN_CROWN = new ResourceLocation(CSM_Main.MOD_ID, "textures/entity/wearables/crown.png");
    public static final ResourceLocation CLAYMAN_LILYPANTS = new ResourceLocation(CSM_Main.MOD_ID, "textures/entity/wearables/lilypants.png");
    public static final ResourceLocation CLAYMAN_GOGGLES = new ResourceLocation(CSM_Main.MOD_ID, "textures/entity/wearables/goggles.png");
    public static final ResourceLocation CLAYMAN_CAPE_BLANK = new ResourceLocation(CSM_Main.MOD_ID, "textures/entity/wearables/cape_blank.png");
    public static final ResourceLocation CLAYMAN_GOLD_HOODIE = new ResourceLocation(CSM_Main.MOD_ID, "textures/entity/wearables/gold_hoodie.png");

    public static final ResourceLocation NEXUS_TEXTURE = new ResourceLocation(CSM_Main.MOD_ID, "textures/entity/nexus/normal.png");
    public static final ResourceLocation NEXUS_GLOWING = new ResourceLocation(CSM_Main.MOD_ID, "textures/entity/nexus/glow_map.png");

    public static final ResourceLocation NEXUS_PARTICLE = new ResourceLocation(CSM_Main.MOD_ID, "textures/entity/nexus/particle.png");

    @SubscribeEvent
    public void onTextureStitchPre(TextureStitchEvent.Pre event) {
        if( event.map.getTextureType() == 1 ) {
            shieldIcon = event.map.registerIcon(CSM_Main.MOD_ID + ":shield");
            shieldStudIcon = event.map.registerIcon(CSM_Main.MOD_ID + ":shield_studs");
            starfruitShieldIcon = event.map.registerIcon(CSM_Main.MOD_ID + ":starfruit");
            starfruitShieldStudIcon = event.map.registerIcon(CSM_Main.MOD_ID + ":starfruit_studs");
        }
    }
}
