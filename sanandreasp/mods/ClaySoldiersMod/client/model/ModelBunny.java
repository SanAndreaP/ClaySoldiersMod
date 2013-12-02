/*******************************************************************************************************************
 * Name:      ModelBunny.java
 * Author:    SanAndreasP
 * Copyright: SanAndreasP and SilverChiren
 * License:   Attribution-NonCommercial-ShareAlike 3.0 Unported (http://creativecommons.org/licenses/by-nc-sa/3.0/)
 *******************************************************************************************************************/

package sanandreasp.mods.ClaySoldiersMod.client.model;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelBunny extends ModelBiped
{
  //fields
    ModelRenderer WolfHead;
    ModelRenderer Body;
    ModelRenderer Leg1;
    ModelRenderer Leg2;
    ModelRenderer Leg3;
    ModelRenderer Leg4;
    ModelRenderer Ear1;
    ModelRenderer Ear2;
    ModelRenderer Tail;
  
  public ModelBunny()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      WolfHead = new ModelRenderer(this, 0, 0);
      WolfHead.addBox(-1F, -1F, -2F, 2, 2, 2);
      WolfHead.setRotationPoint(0F, 21.5F, -1F);
      WolfHead.setTextureSize(64, 32);
      WolfHead.mirror = true;
      setRotation(WolfHead, 0F, 0F, 0F);
      Body = new ModelRenderer(this, 0, 4);
      Body.addBox(-1.5F, -2F, -1.5F, 3, 3, 2);
      Body.setRotationPoint(0F, 21.5F, 1F);
      Body.setTextureSize(64, 32);
      Body.mirror = true;
      setRotation(Body, 1.570796F, 0F, 0F);
      Leg1 = new ModelRenderer(this, 0, 9);
      Leg1.addBox(-1F, 0F, 0F, 1, 1, 1);
      Leg1.setRotationPoint(-0.5F, 23F, 1F);
      Leg1.setTextureSize(64, 32);
      Leg1.mirror = true;
      setRotation(Leg1, 0F, 0F, 0F);
      Leg2 = new ModelRenderer(this, 0, 9);
      Leg2.addBox(0F, 0F, 0F, 1, 1, 1);
      Leg2.setRotationPoint(0.5F, 23F, 1F);
      Leg2.setTextureSize(64, 32);
      Leg2.mirror = true;
      setRotation(Leg2, 0F, 0F, 0F);
      Leg3 = new ModelRenderer(this, 0, 9);
      Leg3.addBox(-1F, 0F, -1F, 1, 1, 1);
      Leg3.setRotationPoint(-0.5F, 23F, 0F);
      Leg3.setTextureSize(64, 32);
      Leg3.mirror = true;
      setRotation(Leg3, 0F, 0F, 0F);
      Leg4 = new ModelRenderer(this, 0, 9);
      Leg4.addBox(0F, 0F, -1F, 1, 1, 1);
      Leg4.setRotationPoint(0.5F, 23F, 0F);
      Leg4.setTextureSize(64, 32);
      Leg4.mirror = true;
      setRotation(Leg4, 0F, 0F, 0F);
      Ear1 = new ModelRenderer(this, 8, 0);
      Ear1.addBox(0F, -3.1F, -1F, 1, 3, 1);
      Ear1.setRotationPoint(0F, 22F, -1F);
      Ear1.setTextureSize(64, 32);
      Ear1.mirror = true;
      setRotation(Ear1, 0F, 0F, -0.6981317F);
      Ear2 = new ModelRenderer(this, 8, 0);
      Ear2.addBox(-1F, -3.1F, -1F, 1, 3, 1);
      Ear2.setRotationPoint(0F, 22F, -1F);
      Ear2.setTextureSize(64, 32);
      Ear2.mirror = true;
      setRotation(Ear2, 0F, 0F, 0.6981317F);
      Tail = new ModelRenderer(this, 4, 9);
      Tail.addBox(-0.5F, 0F, -0.5F, 1, 1, 1);
      Tail.setRotationPoint(0F, 21.5F, 2F);
      Tail.setTextureSize(64, 32);
      Tail.mirror = true;
      setRotation(Tail, 1.747395F, 0F, 0F);
  }
  
  @Override
public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    WolfHead.render(f5);
    Body.render(f5);
    Leg1.render(f5);
    Leg2.render(f5);
    Leg3.render(f5);
    Leg4.render(f5);
    Ear1.render(f5);
    Ear2.render(f5);
    Tail.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  

  @Override
public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
  {
      Leg1.rotateAngleX = Leg2.rotateAngleX = MathHelper.cos(f * 0.6662F) * 0.5F * f1;
      Leg3.rotateAngleX = Leg4.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 0.5F * f1;
      Leg1.rotateAngleY = Leg2.rotateAngleY = 0.0F;
      Leg3.rotateAngleY = Leg4.rotateAngleY = 0.0F;
  }

}
