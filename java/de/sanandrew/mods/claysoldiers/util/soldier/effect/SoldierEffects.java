package de.sanandrew.mods.claysoldiers.util.soldier.effect;

import com.google.common.collect.Maps;
import com.google.common.primitives.Bytes;
import cpw.mods.fml.common.FMLLog;
import de.sanandrew.mods.claysoldiers.util.CSM_Main;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.ISoldierUpgrade;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.Level;

import java.util.Map;

/**
 * @author SanAndreas
 * @version 1.0
 */
public class SoldierEffects
{
    private static final Map<String, ISoldierUpgrade> NAME_TO_UPGRADE_MAP_ = Maps.newHashMap();
    private static final Map<ISoldierUpgrade, String> UPGRADE_TO_NAME_MAP_ = Maps.newHashMap();
    private static final Map<ISoldierUpgrade, Byte> UPGRADE_TO_RENDER_ID_MAP_ = Maps.newHashMap();
    private static final Map<Byte, ISoldierUpgrade> RENDER_ID_TO_UPGRADE_MAP_ = Maps.newHashMap();

    private static byte currRenderId = 0;

    public static void registerUpgrade(String name, ItemStack item, ISoldierUpgrade instance) {
        registerUpgrade(name, item, instance, -1);
    }

    public static void registerUpgrade(String upgradeName, ItemStack upgradeItem, ISoldierUpgrade upgradeInst, int cltRenderId) {
        registerUpgrade(upgradeName, new ItemStack[]{upgradeItem}, upgradeInst, cltRenderId);
    }

    public static void registerUpgrade(String upgradeName, ItemStack[] upgradeItems, ISoldierUpgrade upgradeInst) {
        registerUpgrade(upgradeName, upgradeItems, upgradeInst, -1);
    }

    public static void registerUpgrade(String upgradeName, ItemStack[] upgradeItems, ISoldierUpgrade upgradeInst, int cltRenderId) {
        NAME_TO_UPGRADE_MAP_.put(upgradeName, upgradeInst);
        UPGRADE_TO_NAME_MAP_.put(upgradeInst, upgradeName);

        if( cltRenderId >= 0 ) {
            if( cltRenderId > 127 ) {
                FMLLog.log(CSM_Main.MOD_LOG, Level.WARN, "The Upgrade \"%s\" cannot be bound to the render ID! The render ID is greater than 127!", upgradeName);
            } else if( RENDER_ID_TO_UPGRADE_MAP_.containsKey((byte) cltRenderId) ) {
                FMLLog.log(CSM_Main.MOD_LOG, Level.WARN, "The Upgrade \"%s\" cannot be bound to the render ID! The render ID is already registered!", upgradeName);
            } else {
                UPGRADE_TO_RENDER_ID_MAP_.put(upgradeInst, (byte) cltRenderId);
                RENDER_ID_TO_UPGRADE_MAP_.put((byte) cltRenderId, upgradeInst);
            }
        }
    }

    public static ISoldierUpgrade getUpgradeFromName(String name) {
        return NAME_TO_UPGRADE_MAP_.get(name);
    }

    public static String getNameFromUpgrade(ISoldierUpgrade upgrade) {
        return UPGRADE_TO_NAME_MAP_.get(upgrade);
    }

    public static byte getRenderIdFromUpgrade(ISoldierUpgrade upgrade) {
        if( UPGRADE_TO_RENDER_ID_MAP_.containsKey(upgrade) ) {
            return UPGRADE_TO_RENDER_ID_MAP_.get(upgrade);
        } else {
            return -1;
        }
    }

    public static ISoldierUpgrade getUpgradeFromRenderId(int renderId) {
        return RENDER_ID_TO_UPGRADE_MAP_.get((byte) renderId);
    }

    public static byte[] getAvailableRenderIds() {
        return Bytes.toArray(RENDER_ID_TO_UPGRADE_MAP_.keySet());
    }

    public static byte getNewRenderId() {
        if( currRenderId == 127 ) {
            throw new RenderIdException();
        }
        return currRenderId++;
    }

    public static class RenderIdException extends RuntimeException {
        public RenderIdException() {
            super("There are no more render IDs for soldier upgrade available!");
        }
    }
}
