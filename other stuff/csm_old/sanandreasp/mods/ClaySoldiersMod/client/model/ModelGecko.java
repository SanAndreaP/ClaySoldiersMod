/*******************************************************************************************************************
 * Name:      ModelGecko.java
 * Author:    SanAndreasP
 * Copyright: SanAndreasP and SilverChiren
 * License:   Attribution-NonCommercial-ShareAlike 3.0 Unported (http://creativecommons.org/licenses/by-nc-sa/3.0/)
 *******************************************************************************************************************/

package sanandreasp.mods.ClaySoldiersMod.client.model;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.src.*;
import net.minecraft.util.MathHelper;

public class ModelGecko extends ModelBiped
{
  //fields
    ModelRenderer head;
    ModelRenderer body;
    ModelRenderer rightarm;
    ModelRenderer leftarm;
    ModelRenderer rightleg;
    ModelRenderer leftleg;
    ModelRenderer head2;
    ModelRenderer Shape1;

  public ModelGecko()
  {
    textureWidth = 64;
    textureHeight = 32;

      head = new ModelRenderer(this, 0, 0);
      head.addBox(-1F, 0F, -3F, 2, 1, 2);
      head.setRotationPoint(0F, 21F, -4F);
      head.setTextureSize(64, 32);
      head.mirror = true;
      setRotation(head, 0F, 0F, 0F);
      body = new ModelRenderer(this, 8, 0);
      body.addBox(-1.5F, -1F, -1.5F, 3, 1, 7);
      body.setRotationPoint(0F, 22F, -2F);
      body.setTextureSize(64, 32);
      body.mirror = true;
      setRotation(body, 0F, 0F, 0F);
      rightarm = new ModelRenderer(this, 0, 3);
      rightarm.addBox(-1F, 0F, -1F, 1, 4, 1);
      rightarm.setRotationPoint(-1F, 22F, -2F);
      rightarm.setTextureSize(64, 32);
      rightarm.mirror = true;
      setRotation(rightarm, 0F, -0.2602503F, 1.226894F);
      leftarm = new ModelRenderer(this, 0, 3);
      leftarm.addBox(0F, 0F, -1F, 1, 4, 1);
      leftarm.setRotationPoint(1F, 22F, -2F);
      leftarm.setTextureSize(64, 32);
      leftarm.mirror = true;
      setRotation(leftarm, 0F, 0.2602503F, -1.226894F);
      rightleg = new ModelRenderer(this, 4, 3);
      rightleg.addBox(-1F, 0F, 0F, 1, 4, 1);
      rightleg.setRotationPoint(-1F, 22F, 2F);
      rightleg.setTextureSize(64, 32);
      rightleg.mirror = true;
      setRotation(rightleg, 0F, 0.2602503F, 1.226894F);
      leftleg = new ModelRenderer(this, 4, 3);
      leftleg.addBox(0F, 0F, 0F, 1, 4, 1);
      leftleg.setRotationPoint(1F, 22F, 2F);
      leftleg.setTextureSize(64, 32);
      leftleg.mirror = true;
      setRotation(leftleg, 0F, -0.2602503F, -1.226894F);
      head2 = new ModelRenderer(this, 0, 8);
      head2.addBox(-1.5F, -1F, -1.5F, 3, 2, 2);
      head2.setRotationPoint(0F, 21F, -4F);
      head2.setTextureSize(64, 32);
      head2.mirror = true;
      setRotation(head2, 0F, 0F, 0F);
      Shape1 = new ModelRenderer(this, 0, 12);
      Shape1.addBox(-0.5F, 0F, -0.5F, 1, 8, 1);
      Shape1.setRotationPoint(0F, 21.5F, 3F);
      Shape1.setTextureSize(64, 32);
      Shape1.mirror = true;
      setRotation(Shape1, 1.487144F, 0F, 0F);
  }

  @Override
public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
//    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    head.render(f5);
    body.render(f5);
    rightarm.render(f5);
    leftarm.render(f5);
    rightleg.render(f5);
    leftleg.render(f5);
    head2.render(f5);
    Shape1.render(f5);
  }

  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }

  @Override
public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
    rightarm.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 0.5F * f1;
    leftarm.rotateAngleX = MathHelper.cos(f * 0.6662F) * 0.5F * f1;
    rightarm.rotateAngleY = 0.0F;
    leftarm.rotateAngleY = 0.0F;
    rightleg.rotateAngleX = MathHelper.cos(f * 0.6662F) * 0.5F * f1;
    leftleg.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 0.5F * f1;
    rightleg.rotateAngleY = 0.0F;
    leftleg.rotateAngleY = 0.0F;
    Shape1.rotateAngleY = MathHelper.cos(f * 0.6662F + 3.141593F) * 0.5F * f1;
//    tail.rotateAngleY = 0.0F;
  }

}
