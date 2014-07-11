package de.sanandrew.mods.claysoldiers.util.soldier.effect;

import de.sanandrew.core.manpack.util.javatuples.Pair;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.soldier.AttackState;
import net.minecraft.util.DamageSource;

/**
 * @author SanAndreas
 * @version 1.0
 */
public class EffectSlimeFeet
    implements ISoldierEffect
{
    @Override
    public void onConstruct(EntityClayMan clayMan, SoldierEffectInst effectInst) {
        effectInst.getNbtTag().setShort("ticksRemain", (short)100);
    }

    @Override
    public boolean onUpdate(EntityClayMan clayMan, SoldierEffectInst effectInst) {
        clayMan.canMove = false;
        short ticksRemain = (short) (effectInst.getNbtTag().getShort("ticksRemain") - 1);

        if( ticksRemain == 0 ) {
            return true;
        }

        effectInst.getNbtTag().setShort("ticksRemain", ticksRemain);
        return false;
    }

    @Override
    public void onClientUpdate(EntityClayMan clayMan) {
        clayMan.canMove = false;
    }

    @Override
    public AttackState onTargeting(EntityClayMan clayMan, SoldierEffectInst effectInst, EntityClayMan target) {
        return null;
    }

    @Override
    public AttackState onBeingTargeted(EntityClayMan clayMan, SoldierEffectInst effectInst, EntityClayMan attacker) {
        return null;
    }

    @Override
    public float onSoldierAttack(EntityClayMan clayMan, SoldierEffectInst effectInst, EntityClayMan target, float damage) {
        return 0;
    }

    @Override
    public void onSoldierDamage(EntityClayMan clayMan, SoldierEffectInst effectInst, EntityClayMan target) {

    }

    @Override
    public void onSoldierDeath(EntityClayMan clayMan, SoldierEffectInst effectInst, DamageSource source) {

    }

    @Override
    public Pair<Float, Boolean> onSoldierHurt(EntityClayMan clayMan, SoldierEffectInst effectInst, DamageSource source, float damage) {
        return null;
    }
}
