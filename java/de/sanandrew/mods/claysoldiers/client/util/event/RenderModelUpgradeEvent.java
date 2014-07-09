package de.sanandrew.mods.claysoldiers.client.util.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import de.sanandrew.mods.claysoldiers.client.event.SoldierRenderEvent;
import de.sanandrew.mods.claysoldiers.client.render.entity.RenderClayMan;
import de.sanandrew.mods.claysoldiers.client.util.Textures;
import de.sanandrew.mods.claysoldiers.util.upgrades.SoldierUpgrades;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import org.lwjgl.opengl.GL11;

/**
 * @author SanAndreas
 * @version 1.0
 */
public class RenderModelUpgradeEvent
{
    public ModelRenderer buffedBody;
    public ModelRenderer armorBody;
    public ModelRenderer armorRightArm;
    public ModelRenderer armorLeftArm;

    public boolean isInitialized = false;

    public void initRenderer(RenderClayMan renderClayMan) {
        this.buffedBody = new ModelRenderer(renderClayMan.modelBipedMain, 16, 16);
        this.buffedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 6, 4, 0.0F);
        this.buffedBody.setRotationPoint(0.0F, -0.4F, 0.0F);

        this.armorBody = new ModelRenderer(renderClayMan.modelBipedMain, 16, 16);
        this.armorBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F);
        this.armorBody.setRotationPoint(0.0F, -0.4F, 0.0F);
        this.armorRightArm = new ModelRenderer(renderClayMan.modelBipedMain, 40, 16);
        this.armorRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.armorRightArm.setRotationPoint(-5.0F, 1.6F, 0.0F);
        this.armorLeftArm = new ModelRenderer(renderClayMan.modelBipedMain, 40, 16);
        this.armorLeftArm.mirror = true;
        this.armorLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.armorLeftArm.setRotationPoint(5.0F, 1.6F, 0.0F);
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public void onSoldierRenderArmor(SoldierRenderEvent.RenderModelEvent event) {
        if( !this.isInitialized ) {
            this.isInitialized = true;
            this.initRenderer(event.clayManRender);
        }

        if( event.clayMan.hasUpgrade(SoldierUpgrades.getUpgradeFromName(SoldierUpgrades.UPG_GUNPOWDER)) ) {
            event.clayManRender.bindTexture(Textures.CLAYMAN_GUNPOWDER);
            GL11.glPushMatrix();
            event.clayManRender.modelBipedMain.render(event.clayMan, event.limbSwing, event.limbSwingAmount, event.rotFloat, event.yaw, event.pitch, event.partTicks);
            GL11.glPopMatrix();
        }

        if( event.clayMan.hasUpgrade(SoldierUpgrades.getUpgradeFromName(SoldierUpgrades.UPG_IRONINGOT)) ) {
            ModelBiped model = event.clayManRender.modelBipedMain;

            this.buffedBody.rotateAngleX = model.bipedBody.rotateAngleX;
            this.buffedBody.rotateAngleY = model.bipedBody.rotateAngleY;
            this.buffedBody.rotateAngleZ = model.bipedBody.rotateAngleZ;

            event.clayManRender.bindTexture(event.clayMan.getTexture());
            GL11.glPushMatrix();
            GL11.glScalef(1.5F, 1.5F, 1.5F);
            this.buffedBody.render(event.partTicks);
            GL11.glPopMatrix();
        }

        if( event.clayMan.hasUpgrade(SoldierUpgrades.getUpgradeFromName(SoldierUpgrades.UPG_LEATHER)) ) {
            ModelBiped model = event.clayManRender.modelBipedMain;

            this.armorBody.rotateAngleX = model.bipedBody.rotateAngleX;
            this.armorBody.rotateAngleY = model.bipedBody.rotateAngleY;
            this.armorBody.rotateAngleZ = model.bipedBody.rotateAngleZ;
            this.armorLeftArm.rotateAngleX = model.bipedLeftArm.rotateAngleX;
            this.armorLeftArm.rotateAngleY = model.bipedLeftArm.rotateAngleY;
            this.armorLeftArm.rotateAngleZ = model.bipedLeftArm.rotateAngleZ;
            this.armorRightArm.rotateAngleX = model.bipedRightArm.rotateAngleX;
            this.armorRightArm.rotateAngleY = model.bipedRightArm.rotateAngleY;
            this.armorRightArm.rotateAngleZ = model.bipedRightArm.rotateAngleZ;

            event.clayManRender.bindTexture(Textures.CLAYMAN_LEATHER_ARMOR);
            GL11.glPushMatrix();
            GL11.glScalef(1.2F, 1.2F, 1.2F);
            this.armorBody.render(event.partTicks);
            this.armorLeftArm.render(event.partTicks);
            this.armorRightArm.render(event.partTicks);
            GL11.glPopMatrix();

            if( event.clayMan.hasUpgrade(SoldierUpgrades.getUpgradeFromName(SoldierUpgrades.UPG_WOOL)) ) {
                float[] color = this.getSplittedColor(event.clayMan.getMiscColor());

                event.clayManRender.bindTexture(Textures.CLAYMAN_PADDING);
                GL11.glPushMatrix();
                GL11.glScalef(1.1F, 1.1F, 1.1F);
                GL11.glColor3f(color[0], color[1], color[2]);
                this.armorBody.render(event.partTicks);
                this.armorLeftArm.render(event.partTicks);
                this.armorRightArm.render(event.partTicks);
                GL11.glPopMatrix();
            }
        }
    }

    public float[] getSplittedColor(int color) {
        float[] splitColor = new float[3];

        splitColor[0] = (float)((color >> 16) & 0xFF) / 255.0F;
        splitColor[1] = (float)((color >> 8) & 0xFF) / 255.0F;
        splitColor[2] = (float)(color & 0xFF) / 255.0F;

        return splitColor;
    }
}
