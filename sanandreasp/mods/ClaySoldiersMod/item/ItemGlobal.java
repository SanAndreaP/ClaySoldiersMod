/*******************************************************************************************************************
* Name: ItemGlobal.java
* Author: SanAndreasP
* Copyright: SanAndreasP and SilverChiren
* License: Attribution-NonCommercial-ShareAlike 3.0 Unported (http://creativecommons.org/licenses/by-nc-sa/3.0/)
*******************************************************************************************************************/

package sanandreasp.mods.ClaySoldiersMod.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;


public class ItemGlobal extends Item {
	
	private String filename;

	public ItemGlobal(int par1) {
		super(par1);
	}
	
	public ItemGlobal setIconFile(String par1String) {
		this.filename = par1String;
		return this;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
	    this.itemIcon = par1IconRegister.registerIcon(filename);
	}
	
}
