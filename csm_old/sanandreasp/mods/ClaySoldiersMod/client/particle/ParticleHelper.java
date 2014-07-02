package sanandreasp.mods.ClaySoldiersMod.client.particle;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityCritFX;
import net.minecraft.client.particle.EntityFX;

public class ParticleHelper {
    private static Random rand = new Random();
    public static void onSpawnSldCritical(float posX, float posY, float posZ) {
        for( int i = 0; i < 20; i++) {
            EntityFX crit = new EntityCritFX(Minecraft.getMinecraft().theWorld, posX, posY, posZ, (new Random()).nextDouble() - 0.5D, 0.5D, (new Random()).nextDouble() - 0.5D);
            crit.setRBGColorF(crit.getRedColorF() * 0.3F, crit.getGreenColorF() * 0.8F, crit.getBlueColorF());
            crit.nextTextureIndexX();
            Minecraft.getMinecraft().effectRenderer.addEffect(crit);
        }
    }
}
