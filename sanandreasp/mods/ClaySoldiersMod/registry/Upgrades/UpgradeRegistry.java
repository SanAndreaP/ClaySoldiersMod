/*******************************************************************************************************************
 * Name: UpgradeRegistry.java
 * Author: SanAndreasP
 * Copyright: SanAndreasP and SilverChiren
 * License: Attribution-NonCommercial-ShareAlike 3.0 Unported
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/)
 *******************************************************************************************************************/

package sanandreasp.mods.ClaySoldiersMod.registry.Upgrades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.leftHand.UpgShearBladeL;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.leftHand.UpgShield;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.misc.UpgBoomDoom;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.misc.UpgDBlock;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.misc.UpgEggScent;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.misc.UpgExplosionProof;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.misc.UpgFlint;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.misc.UpgGlowstone;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.misc.UpgGlowstoneInf;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.misc.UpgIronShield;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.misc.UpgLeather;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.misc.UpgSuper;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.misc.UpgUnique;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.misc.UpgWool;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.rightHand.UpgBlazeRod;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.rightHand.UpgShearBladeR;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.rightHand.UpgStick;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;

public class UpgradeRegistry
{
    
    private final BiMap<Integer, IUpgradeItem> upgInt = HashBiMap.create();
    private final Map<Class<? extends IUpgradeItem>, Integer> upgCls = Maps
            .newHashMap();
    
    public UpgradeRegistry()
    {
        registerNewUpgrade(new UpgShield());
        registerNewUpgrade(new UpgShearBladeL());
        registerNewUpgrade(new UpgBoomDoom());
        registerNewUpgrade(new UpgStick());
        registerNewUpgrade(new UpgBlazeRod());
        registerNewUpgrade(new UpgShearBladeR());
        registerNewUpgrade(new UpgExplosionProof());
        registerNewUpgrade(new UpgEggScent());
        registerNewUpgrade(new UpgLeather());
        registerNewUpgrade(new UpgIronShield());
        registerNewUpgrade(new UpgFlint());
        registerNewUpgrade(new UpgWool());
        registerNewUpgrade(new UpgGlowstone());
        registerNewUpgrade(new UpgSuper());
        registerNewUpgrade(new UpgUnique());
        registerNewUpgrade(new UpgDBlock());
        registerNewUpgrade(new UpgGlowstoneInf());
    }
    
    public void registerNewUpgrade(IUpgradeItem upg)
    {
        int id = this.upgInt.size();
        this.upgInt.put(id, upg);
        this.upgCls.put(upg.getClass(), id);
    }
    
    public IUpgradeItem getUpgradeByID(int id)
    {
        if (this.upgInt.containsKey(id))
            return this.upgInt.get(id);
        
        return null;
    }
    
    public int getIDByUpgrade(IUpgradeItem upg)
    {
        if (this.upgInt.containsValue(upg))
            return this.upgInt.inverse().get(upg);
        
        return -1;
    }
    
    public int getIDByUpgradeClass(Class<? extends IUpgradeItem> upg)
    {
        if (this.upgCls.containsKey(upg))
            return this.upgCls.get(upg);
        
        return -1;
    }
    
    public List<IUpgradeItem> getUpgrades()
    {
        return new ArrayList<IUpgradeItem>(this.upgInt.values());
    }
}
