/*******************************************************************************************************************
 * Name:      ModelTurtle.java
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

public class ModelTurtle extends ModelBiped

{

  //fields

    ModelRenderer bipedHead;
    ModelRenderer bipedBody;
    ModelRenderer bipedRightArm;
    ModelRenderer bipedLeftArm;
    ModelRenderer bipedRightLeg;
    ModelRenderer bipedLeftLeg;
    ModelRenderer bipedBody2;



    public ModelTurtle()
    {
        this(0.0F);
    }

    public ModelTurtle(float f)
    {
        this(f, 0.0F);
    }



    public ModelTurtle(float f, float f1)
    {

      bipedHead = new ModelRenderer(this, 0, 0);
      bipedHead.addBox(-1F, -1F, -2F, 2, 2, 2);
      bipedHead.setRotationPoint(0F, 21F, -5F);


      bipedBody = new ModelRenderer(this, 8, 0);
      bipedBody.addBox(-3F, 0F, -2F, 6, 2, 7);
      bipedBody.setRotationPoint(0F, 20F, -3F);


      bipedRightArm = new ModelRenderer(this, 0, 4);
      bipedRightArm.addBox(-1F, 0F, -1F, 1, 3, 1);
      bipedRightArm.setRotationPoint(-2F, 22F, -3F);
      setRotation(bipedRightArm, 0F, 0F, 1.003822F);


      bipedLeftArm = new ModelRenderer(this, 0, 4);
      bipedLeftArm.addBox(0F, 0F, -1F, 1, 3, 1);
      bipedLeftArm.setRotationPoint(2F, 22F, -3F);
      bipedLeftArm.mirror = true;
      setRotation(bipedLeftArm, 0F, 0F, -1.003822F);


      bipedRightLeg = new ModelRenderer(this, 4, 4);
      bipedRightLeg.addBox(-1F, 0F, 0F, 1, 3, 1);
      bipedRightLeg.setRotationPoint(-2F, 22F, 0F);
      setRotation(bipedRightLeg, 0F, 0F, 1.003822F);


      bipedLeftLeg = new ModelRenderer(this, 4, 4);
      bipedLeftLeg.addBox(0F, 0F, 0F, 1, 3, 1);
      bipedLeftLeg.setRotationPoint(2F, 22F, 0F);
      bipedLeftLeg.mirror = true;
      setRotation(bipedLeftLeg, 0F, 0F, -1.003822F);


      bipedBody2 = new ModelRenderer(this, 8, 9);
      bipedBody2.addBox(-2F, -1F, -1F, 4, 1, 5);
      bipedBody2.setRotationPoint(0F, 20F, -3F);
//      setRotation(bipedBody2, 0F, 0F, 0F);

  }



  @Override
public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
//    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    bipedHead.render(f5);
    bipedBody.render(f5);
    bipedRightArm.render(f5);
    bipedLeftArm.render(f5);
    bipedRightLeg.render(f5);
    bipedLeftLeg.render(f5);
    bipedBody2.render(f5);
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
//    super.setRotationAngles(f, f1, f2, f3, f4, f5);

//    head.rotateAngleY = f3 / 57.29578F;
//    head.rotateAngleX = (f4 / 57.29578F) + 0.79F;
    bipedRightArm.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 0.5F * f1;
    bipedLeftArm.rotateAngleX = MathHelper.cos(f * 0.6662F) * 0.5F * f1;
    bipedRightArm.rotateAngleY = 0.0F;
    bipedLeftArm.rotateAngleY = 0.0F;
    bipedRightLeg.rotateAngleX = MathHelper.cos(f * 0.6662F) * 0.5F * f1;
    bipedLeftLeg.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 0.5F * f1;
    bipedRightLeg.rotateAngleY = 0.0F;
    bipedLeftLeg.rotateAngleY = 0.0F;
  }
}
