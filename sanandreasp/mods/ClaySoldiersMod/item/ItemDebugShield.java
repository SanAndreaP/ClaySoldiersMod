/*******************************************************************************************************************
* Name: ItemDebugShield.java
* Author: SanAndreasP
* Copyright: SanAndreasP and SilverChiren
* License: Attribution-NonCommercial-ShareAlike 3.0 Unported (http://creativecommons.org/licenses/by-nc-sa/3.0/)
*******************************************************************************************************************/

package sanandreasp.mods.ClaySoldiersMod.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDebugShield extends ItemGlobal {
	
	@SideOnly(Side.CLIENT)
	private Icon studdedIcon, starIcon, studStarIcon;

	public ItemDebugShield(int par1) {
		super(par1);
	}
	
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("ClaySoldiersMod:shield");
        this.studdedIcon = par1IconRegister.registerIcon("ClaySoldiersMod:shields");
        this.starIcon = par1IconRegister.registerIcon("ClaySoldiersMod:starfruitSlice");
        this.studStarIcon = par1IconRegister.registerIcon("ClaySoldiersMod:starfruitStuds");
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int par1) {
		switch(par1) {
			case 1: return this.studdedIcon;
			case 2: return this.starIcon;
			case 3: return this.studStarIcon;
			default: return this.itemIcon;
		}
	}

}
