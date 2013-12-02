/*******************************************************************************************************************
* Name: Handler_ServerTicks.java
* Author: SanAndreasP
* Copyright: SanAndreasP and SilverChiren
* License: Attribution-NonCommercial-ShareAlike 3.0 Unported (http://creativecommons.org/licenses/by-nc-sa/3.0/)
*******************************************************************************************************************/

package sanandreasp.mods.ClaySoldiersMod.registry;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.Map.Entry;

import net.minecraft.client.Minecraft;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class Handler_ServerTicks implements ITickHandler {
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		Iterator<Entry<String, Integer>> waveSecs = CSMModRegistry.waveTimes.entrySet().iterator();
		
		while(waveSecs.hasNext()) {
			Entry<String, Integer> entry = waveSecs.next();
			CSMModRegistry.waveTimes.put(entry.getKey(), Math.min((int)entry.getValue() - 1, 0));
		}
	}
	
	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {  }

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.SERVER);
	}

	@Override
	public String getLabel() {
		return "CSMTickHandlerSrv";
	}

}
