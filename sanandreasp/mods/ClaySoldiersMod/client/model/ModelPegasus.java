/*******************************************************************************************************************
 * Name:      ModelPegasus.java
 * Author:    SanAndreasP
 * Copyright: SanAndreasP and SilverChiren
 * License:   Attribution-NonCommercial-ShareAlike 3.0 Unported (http://creativecommons.org/licenses/by-nc-sa/3.0/)
 *******************************************************************************************************************/

package sanandreasp.mods.ClaySoldiersMod.client.model;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.src.*;

public class ModelPegasus extends ModelHorse
{

    public ModelPegasus()
    {
        this(0.0F);
    }

    public ModelPegasus(float f)
    {
        this(f, 0.0F);
    }

    public ModelPegasus(float f, float f1)
    {
        super(f, f1);
		
		wingRight = new ModelRenderer(this, 0, 22);
		wingRight.mirror = true;
		wingRight.addBox(-0.5F, 0.25F, -2.25F, 13, 1, 5, f);
		wingRight.setRotationPoint(1.5F, -0.5F + f1, 0F);
		
		wingLeft = new ModelRenderer(this, 0, 22);
		wingLeft.addBox(-12.5F, 0.25F, -2.25F, 13, 1, 5, f);
		wingLeft.setRotationPoint(-1.5F, -0.5F + f1, 0F);
    }

    @Override
	public void render(Entity e, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(e, f, f1, f2, f3, f4, f5);
		wingLeft.render(f5);
		wingRight.render(f5);
    }

    @Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e)
    {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
		
		wingLeft.rotateAngleY = -0.2F;
		wingRight.rotateAngleY = 0.2F;
		wingLeft.rotateAngleZ = -0.125F;
		wingRight.rotateAngleZ = 0.125F;
		
		wingLeft.rotateAngleY += Math.sin(sinage) / 6F;
		wingRight.rotateAngleY -= Math.sin(sinage) / 6F;
		wingLeft.rotateAngleZ += Math.cos(sinage) / (gonRound ? 8F : 3F);
		wingRight.rotateAngleZ -= Math.cos(sinage) / (gonRound ? 8F : 3F);
    }
	
	public ModelRenderer wingLeft, wingRight;
	
	public float sinage;
	public boolean gonRound;
}
