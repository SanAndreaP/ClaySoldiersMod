package sanandreasp.mods.ClaySoldiersMod.registry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;

public class SoldierTeams {
	public static final Map<Integer, SoldierTeams> soldiers = Maps.newHashMap();
	
	public final int teamID;
	public final String itemIconStr;
	public final String localName;
	public Icon itemIconIco = null;
	
	public SoldierTeams(int id, String icon, String localizedName) {
		this.teamID = id;
		this.itemIconStr = icon;
		this.localName = localizedName;
		SoldierTeams.soldiers.put(id, this);
	}
	
	public void setIcon(Icon ico) {
		this.itemIconIco = ico;
	}
	
	public static Map<Integer, SoldierTeams> getTeamsList() {
		return Maps.newHashMap(SoldierTeams.soldiers);
	}
	
	public static void initDefTeams() {
		new SoldierTeams(0, "0x808080", "Clay Soldier");
		new SoldierTeams(1, "0xB24444", "Red Soldier");
		new SoldierTeams(2, "0xD2D228", "Yellow Soldier");
		new SoldierTeams(3, "0x309630", "Green Soldier");
		new SoldierTeams(4, "0x3458A4", "Blue Soldier");
		new SoldierTeams(5, "0xE8A033", "Orange Soldier");
		new SoldierTeams(6, "0x9044AA", "Purple Soldier");
		new SoldierTeams(7, "0xF16878", "Pink Soldier");
		new SoldierTeams(8, "0x553322", "Brown Soldier");
		new SoldierTeams(9, "0xFFFFFF", "White Soldier");
		new SoldierTeams(10, "0x282828", "Black Soldier");
		new SoldierTeams(11, "0x00FFFF", "Cyan Soldier");
		new SoldierTeams(12, "0xC0C0C0", "Light Gray Soldier");
		new SoldierTeams(13, "0x28FF28", "Lime Soldier");
		new SoldierTeams(14, "0x8080FF", "Light Blue Soldier");
		new SoldierTeams(15, "0xFF00FF", "Magenta Soldier");
		new SoldierTeams(16, "claysoldiersmod:dollMelon", "Melon Soldier");
		new SoldierTeams(17, "claysoldiersmod:dollPumpkin", "Pumpkin Soldier");
		new SoldierTeams(18, "0x141414", "Coal Soldier");
		new SoldierTeams(19, "0xC53333", "Redstone Soldier");
	}
	
/* 
 * --------------------------------------
 *          API for custom teams
 * --------------------------------------
 */
	
	public static final Map<Integer, SoldierStats> customTeams = Maps.newHashMap();
	
	public static void addCustomTeam(int id, String dollIcon, String name, ResourceLocation[][] teamTextures) {
		SoldierTeams.customTeams.put(id, new SoldierStats(name, dollIcon, teamTextures));
		SoldierTeams.soldiers.put(id + 4096, new SoldierTeams(id + 4096, dollIcon, name));
	}
	
	public static void addCustomTeam(int id, String dollIcon, String name, float health, float attackStrength, float moveSpeed, ResourceLocation[][] teamTextures) {
		SoldierTeams.customTeams.put(id, new SoldierStats(name, dollIcon, teamTextures, health, attackStrength, moveSpeed));
		SoldierTeams.soldiers.put(id + 4096, new SoldierTeams(id + 4096, dollIcon, name));
	}
	
	private static class SoldierStats {
		public final float health;
		public final float attackStrength;
		public final float walkSpeed;
		public final ResourceLocation[][] teamTextures;
		
		public SoldierStats(String name, String iconName, ResourceLocation[][] teamTex) {
			this(name, iconName, teamTex, 20F, 2F, 0.3F);
		}
		
		public SoldierStats(String name, String iconName, ResourceLocation[][] teamTex, float hlt, float atk, float wlk) {
			this.teamTextures = teamTex;
			this.health = hlt;
			this.attackStrength = atk;
			this.walkSpeed = wlk;
//			SoldierTeams.soldiers.add(new SoldierTeams(id + 4096, iconName, name));
		}
	}
}
