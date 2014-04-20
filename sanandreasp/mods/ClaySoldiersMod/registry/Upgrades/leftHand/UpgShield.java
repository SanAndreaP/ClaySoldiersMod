package sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.leftHand;

import java.util.Random;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import sanandreasp.mods.ClaySoldiersMod.client.render.UpgradeRenderHelper;
import sanandreasp.mods.ClaySoldiersMod.entity.EntityClayMan;
import sanandreasp.mods.ClaySoldiersMod.registry.CSMModRegistry;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.IUpgradeEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class UpgShield extends LeftHandUpgrade
{
    
    @Override
    public void initUpgrade(IUpgradeEntity entity, NBTTagCompound nbt)
    {
        // nbt.setByte("points", (byte) 20);
        // nbt.setBoolean("upgraded", false);
    }
    
    @Override
    public float onHit(IUpgradeEntity attacker, DamageSource source,
            float initAmount)
    {
        int thisUpgID = CSMModRegistry.clayUpgRegistry.getIDByUpgrade(this);
        EntityClayMan cs = (EntityClayMan) attacker;
        NBTTagCompound nbt = attacker.getUpgradeNBT(thisUpgID);
        if (nbt == null)
            return initAmount;
        // byte shieldPts = (byte)(nbt.getByte("points") - 1);
        // nbt.setByte("points", shieldPts);
        // if( shieldPts <= 0 ) attacker.breakUpgrade(thisUpgID);
        
        if (cs.studded == 1)
            initAmount -= 1F;
        return initAmount / 2F;
    }
    
    @Override
    public ItemStack getItemStack(IUpgradeEntity attacker)
    {
        return new ItemStack(Item.bowlEmpty);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void onRenderEquipped(IUpgradeEntity entity, RenderManager manager,
            float partTicks, ModelBase model)
    {
        UpgradeRenderHelper.onShieldRender(this, manager, entity, partTicks,
                model);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getHeldItem(IUpgradeEntity attacker)
    {
        // NBTTagCompound tag =
        // attacker.getUpgradeNBT(CSMModRegistry.clayUpgRegistry.getIDByUpgrade(this));
        EntityClayMan cs = (EntityClayMan) attacker;
        int dmg = 0;
        // if(tag!=null)
        if (cs.studded == 1)
            dmg = 1;
        // System.out.println("DERPMG"+dmg)
        if (dmg == 1)
            return new ItemStack(CSMModRegistry.upgshield, 1, 0);
        else
            return new ItemStack(CSMModRegistry.shield, 1, 0);
    }
    
}
