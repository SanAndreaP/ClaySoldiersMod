package de.sanandrew.mods.claysoldiers.util.upgrades.righthand;

import de.sanandrew.core.manpack.util.javatuples.Pair;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.upgrades.ISoldierUpgrade;
import de.sanandrew.mods.claysoldiers.util.upgrades.SoldierUpgradeInst;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

/**
 * @author SanAndreas
 * @version 1.0
 */
public abstract class UpgradeRightHanded
    implements ISoldierUpgrade
{
    @Override
    public AttackState onTargeting(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan target) {
        return AttackState.SKIP;
    }

    @Override
    public AttackState onBeingTargeted(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan attacker) {
        return AttackState.SKIP;
    }

    @Override
    public float onSoldierAttack(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan target, float damage) {
        return damage;
    }

    @Override
    public void onSoldierDamage(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan target) { }

    @Override
    public boolean onUpdate(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        return false;
    }

    @Override
    public Pair<Float, Boolean> onSoldierHurt(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, DamageSource source, float damage) {
        return Pair.with(damage, true);
    }

    @Override
    public boolean canBePickedUp(EntityClayMan clayMan, ItemStack stack, ISoldierUpgrade upgrade) {
        return !(upgrade instanceof UpgradeRightHanded);
    }

    @Override
    public void onPickup(EntityClayMan clayMan, ItemStack stack) { }

//    @Override
//    @SideOnly(Side.CLIENT)
//    public void onRender(RenderStage stage, EntityClayMan clayMan, RenderClayMan clayManRender, double x, double y, double z, float yaw, float partTicks) {
//        if( stage == RenderStage.EQUIPPED && this.getRenderedItem() != null ) {
//            GL11.glPushMatrix();
//            clayManRender.modelBipedMain.bipedRightArm.postRender(0.0625F);
//            GL11.glTranslatef(-0.1F, 0.6F, 0F);
//
//            float itemScale = 0.6F;
//            GL11.glScalef(itemScale, itemScale, itemScale);
//            GL11.glRotatef(140F, 0.0F, 0.0F, 1.0F);
//            GL11.glRotatef(-90F, 1.0F, 0.0F, 0.0F);
//            GL11.glRotatef(0F, 0.0F, 0.0F, 1.0F);
//
//            clayManRender.getItemRenderer().renderItem(clayMan, this.getRenderedItem(), 0);
//            GL11.glPopMatrix();
//        }
//    }

//    public ItemStack getRenderedItem() {
//        return null;
//    }
}
