package sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.rightHand;

import java.util.Random;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import sanandreasp.mods.ClaySoldiersMod.client.render.UpgradeRenderHelper;
import sanandreasp.mods.ClaySoldiersMod.registry.CSMModRegistry;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.IUpgradeEntity;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class UpgStick extends RightHandUpgrade
{
    
    @Override
    public void initUpgrade(IUpgradeEntity entity, NBTTagCompound nbt)
    {
        // nbt.setByte("points", (byte) 20);
        nbt.setBoolean("upgraded", false);
    }
    
    @Override
    public float onAttack(IUpgradeEntity attacker, EntityLivingBase target,
            float initAmount)
    {
        int thisUpgID = CSMModRegistry.clayUpgRegistry.getIDByUpgrade(this);
        NBTTagCompound nbt = attacker.getUpgradeNBT(thisUpgID);
        // byte stickPts = (byte)(nbt.getByte("points") - 1);
        // nbt.setByte("points", stickPts);
        // if( stickPts <= 0 ) attacker.breakUpgrade(thisUpgID);
        return initAmount + 2.0F + attacker.getEntity().getRNG().nextFloat()
                + (nbt.getBoolean("upgraded") ? 2F : 0F);
    }
    
    @Override
    public ItemStack getItemStack(IUpgradeEntity attacker)
    {
        NBTTagCompound tag =
                attacker.getUpgradeNBT(CSMModRegistry.clayUpgRegistry
                        .getIDByUpgrade(this));
        return tag != null && tag.getBoolean("upgraded") ? new ItemStack(
                Item.arrow) : new ItemStack(Item.stick);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void onRenderEquipped(IUpgradeEntity entity, RenderManager manager,
            float partTicks, ModelBase model)
    {
        // UpgradeRenderHelper.onRightItemRender(this, manager, entity,
        // partTicks, model);
    }
    
    @Override
    public ItemStack getHeldItem(IUpgradeEntity attacker)
    {
        return this.getItemStack(attacker);
    }
    
}
