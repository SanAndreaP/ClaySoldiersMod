package de.sanandrew.mods.claysoldiers.client.event;

import com.google.common.collect.Maps;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.ModConfig;
import de.sanandrew.mods.claysoldiers.util.ModItems;
import de.sanandrew.mods.claysoldiers.util.soldier.ClaymanTeam;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author SanAndreas
 * @version 1.0
 */
public class RenderHudOverlayEvent
    extends Gui
{
    @SubscribeEvent
    public void renderText(RenderGameOverlayEvent.Text event) {
        Minecraft mc = Minecraft.getMinecraft();

        if( !mc.gameSettings.showDebugInfo && mc.thePlayer.getCurrentEquippedItem() != null
                && mc.thePlayer.getCurrentEquippedItem().getItem() == ModItems.statDisplay )
        {
            this.renderSoldiers(mc);
        }
    }

    private void renderSoldiers(Minecraft mc) {
        @SuppressWarnings("unchecked")
        List<EntityClayMan> soldiers = mc.theWorld.getEntitiesWithinAABB(EntityClayMan.class, this.getRangeAaBbFromPlayer(mc.thePlayer));
        Map<String, Integer> teamCounts = Maps.newHashMap();

        for( EntityClayMan dex : soldiers ) {
            String team = dex.getClayTeam();
            if( teamCounts.containsKey(team) ) {
                teamCounts.put(team, teamCounts.get(team) + 1);
            } else {
                teamCounts.put(team, 1);
            }
        }

        this.drawGradientRect(6, 6, 106, 18, 0x00000000, 0x80FFFFFF);

        mc.fontRenderer.drawString("Soldiers", 53 - mc.fontRenderer.getStringWidth("Soldiers") / 2 - 1, 6, 0x000000);
        mc.fontRenderer.drawString("Soldiers", 53 - mc.fontRenderer.getStringWidth("Soldiers") / 2, 7, 0x000000);
        mc.fontRenderer.drawString("Soldiers", 53 - mc.fontRenderer.getStringWidth("Soldiers") / 2 + 1, 6, 0x000000);
        mc.fontRenderer.drawString("Soldiers", 53 - mc.fontRenderer.getStringWidth("Soldiers") / 2, 5, 0x000000);
        mc.fontRenderer.drawString("Soldiers", 53 - mc.fontRenderer.getStringWidth("Soldiers") / 2, 6, 0xFFFFFF);

        this.drawGradientRect(5, 5, 6, 18, 0x00000000, 0xC0000000);
        this.drawGradientRect(106, 5, 107, 18, 0x00000000, 0xC0000000);

        int pos = 0;
        for( Entry<String, Integer> teamCount : teamCounts.entrySet() ) {
            int teamColor = ClaymanTeam.getTeamFromName(teamCount.getKey()).getTeamColor();
            String text = teamCount.getKey() + ": " + teamCount.getValue().toString();
            drawRect(6, 18 + pos * 11, 106, 29 + pos * 11, 0x80FFFFFF);
            drawRect(6, 18 + pos * 11, 106, 28 + pos * 11, 0xC0000000 | teamColor);
            mc.fontRenderer.drawString(text, 53 - mc.fontRenderer.getStringWidth(text) / 2, 19 + pos * 11, this.getContrastTextColor(teamColor));
            pos++;
        }

        drawRect(5, 18, 6, 18 + pos * 11, 0xC0000000);
        drawRect(106, 18, 107, 18 + pos * 11, 0xC0000000);

        this.drawGradientRect(5, 18 + pos * 11, 6, 24 + pos * 11, 0xC0000000, 0x00000000);
        this.drawGradientRect(106, 18 + pos * 11, 107, 24 + pos * 11, 0xC0000000, 0x00000000);
        this.drawGradientRect(6, 18 + pos * 11, 106, 23 + pos * 11, 0x80FFFFFF, 0x00000000);
    }

    private AxisAlignedBB getRangeAaBbFromPlayer(EntityPlayer player) {
        return AxisAlignedBB.getBoundingBox(player.posX - ModConfig.statItemRange, player.posY - ModConfig.statItemRange, player.posZ - ModConfig.statItemRange,
                                            player.posX + ModConfig.statItemRange, player.posY + ModConfig.statItemRange, player.posZ + ModConfig.statItemRange);
    }

    private int getContrastTextColor(int bkgColor) {
        int r = ((bkgColor >> 16) & 255);
        int g = ((bkgColor >> 8) & 255);
        int b = (bkgColor & 255);
        int yiq = ((r*299)+(g*587)+(b*144)) / 1000;
        return (yiq >= 128) ? 0x000000 : 0xFFFFFF;
    }
}
