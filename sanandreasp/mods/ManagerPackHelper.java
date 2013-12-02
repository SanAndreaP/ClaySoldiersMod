/*******************************************************************************************************************
* Name: ManagerPackHelper.java
* Author: SanAndreasP
* Copyright: SanAndreasP and SilverChiren
* License: Attribution-NonCommercial-ShareAlike 3.0 Unported (http://creativecommons.org/licenses/by-nc-sa/3.0/)
*******************************************************************************************************************/

package sanandreasp.mods;

import java.lang.reflect.Field;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import sanandreasp.core.manpack.managers.SAPConfigManager;
import sanandreasp.core.manpack.managers.SAPLanguageManager;
import sanandreasp.core.manpack.managers.SAPUpdateManager;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;

public class ManagerPackHelper {
	public boolean loading = true;
	private Object cfgmanInst, updmanInst, langmanInst;
	
	public void checkManPack(String modname) {
		try {
			Class cls = Class.forName("sanandreasp.core.manpack.mod.ModContainerManPack");
			if( cls == null ) {
				loading = false;
				stopLoad(String.format("You forgot to install the Manager Pack!\nPlease download the latest version from:\nhttp://www.minecraftforge.net/forum/index.php/topic,2828.0.html\n- Minecraft will load without this mod: >> %s <<! -", modname), "No Manager Pack!");
			}
			Field verFld = cls.getDeclaredField("version");
	        if( verFld != null ) {
	        	int verErr = checkVersion((String) verFld.get(null), "1.9");
	        	if( verErr > 1 ) {
	        		loading = false;
	        		stopLoad(String.format("Wrong version of the Manager Pack!\nPlease download the latest version from http://goo.gl/WZwtZ\n- Minecraft will load without this mod: >> %s <<! -", modname), "Manager Pack outdated!");
	        	} else if( verErr == 1 ) {
	        		FMLLog.warning("No up-to-date SAP Manager Pack!\nPlease get the latest version now at: http://goo.gl/WZwtZ\n- Expect bugs and crashes!");
	        	}
	        }
		} catch (ClassNotFoundException e) {
			stopLoad(String.format("You forgot to install the Manager Pack!\nPlease download the latest version from http://goo.gl/WZwtZ\n- Minecraft will load without this mod: >> %s <<! -", modname), "No Manager Pack!");
			loading = false;
		} catch (IllegalArgumentException e) {
			stopLoad(String.format("You forgot to install the Manager Pack!\nPlease download the latest version from http://goo.gl/WZwtZ\n- Minecraft will load without this mod: >> %s <<! -", modname), "No Manager Pack!");
			loading = false;
		} catch (IllegalAccessException e) {
			stopLoad(String.format("You forgot to install the Manager Pack!\nPlease download the latest version from http://goo.gl/WZwtZ\n- Minecraft will load without this mod: >> %s <<! -", modname), "No Manager Pack!");
			loading = false;
		} catch (NoSuchFieldException e) {
			stopLoad(String.format("You forgot to install the Manager Pack!\nPlease download the latest version from http://goo.gl/WZwtZ\n- Minecraft will load without this mod: >> %s <<! -", modname), "No Manager Pack!");
			loading = false;
		} catch (SecurityException e) {
			stopLoad(String.format("You forgot to install the Manager Pack!\nPlease download the latest version from http://goo.gl/WZwtZ\n- Minecraft will load without this mod: >> %s <<! -", modname), "No Manager Pack!");
			loading = false;
		}
	}
	
	public void initMan(SAPConfigManager cfgman, SAPLanguageManager langman, SAPUpdateManager updman) {
		this.cfgmanInst = cfgman;
		this.langmanInst = langman;
		this.updmanInst = updman;
	}
	
	public SAPConfigManager getCfgMan() {
		return (SAPConfigManager) (this.cfgmanInst != null && this.cfgmanInst instanceof SAPConfigManager ? this.cfgmanInst : null);
	}
	
	public SAPLanguageManager getLangMan() {
		return (SAPLanguageManager) (this.langmanInst != null && this.langmanInst instanceof SAPLanguageManager ? this.langmanInst : null);
	}
	
	public SAPUpdateManager getUpdMan() {
		return (SAPUpdateManager) (this.updmanInst != null && this.updmanInst instanceof SAPUpdateManager ? this.updmanInst : null);
	}

	private void stopLoad(final String msg, final String title) {
		FMLLog.severe(title + " Please get the latest version now at: http://goo.gl/WZwtZ");
		if( FMLCommonHandler.instance().getSide().isClient() ) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					JOptionPane pane = new JOptionPane(msg, JOptionPane.ERROR_MESSAGE);
					JDialog dlg = pane.createDialog(title);
					dlg.setModal(false);
					dlg.setVisible(true);
				}
			}).start();
		}
	}
	
	private int checkVersion(String currVer, String expectedVer) {
		String currSplit[] = currVer.split("\\.");
		String exptSplit[] = expectedVer.split("\\.");
		
		int majDiff = Integer.parseInt(currSplit[0]) - Integer.parseInt(exptSplit[0]);
		int minDiff = Integer.parseInt(currSplit[1]) - Integer.parseInt(exptSplit[1]);
		int revDiff = (currSplit.length < 3 ? 0 : Integer.parseInt(currSplit[2])) - (exptSplit.length < 3 ? 0 : Integer.parseInt(exptSplit[2]));
		
		if( majDiff < 0 ) {
			return 3;
		} else if( minDiff < 0 && majDiff == 0 ) {
			return 2;
		} else if( revDiff < 0 && minDiff == 0 && majDiff == 0 ) {
			return 1;
		}
		
		return 0;
	}
}
