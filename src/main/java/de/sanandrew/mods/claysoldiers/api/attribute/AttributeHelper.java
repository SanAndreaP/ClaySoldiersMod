/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.api.attribute;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;

@SuppressWarnings({"unused", "ConstantConditions"})
public final class AttributeHelper
{
    public static void tryApplyAttackDmgModifier(EntityCreature entity, AttributeModifier modifier) {
        tryApplyModifier(entity, SharedMonsterAttributes.ATTACK_DAMAGE, modifier);
    }

    public static void tryRemoveAttackDmgModifier(EntityCreature entity, AttributeModifier modifier) {
        tryRemoveModifier(entity, SharedMonsterAttributes.ATTACK_DAMAGE, modifier);
    }

    public static void tryApplyKnockbackResModifier(EntityCreature entity, AttributeModifier modifier) {
        tryApplyModifier(entity, CsmMobAttributes.KB_RESISTANCE, modifier);
    }

    public static void tryRemoveKnockbackResModifier(EntityCreature entity, AttributeModifier modifier) {
        tryRemoveModifier(entity, CsmMobAttributes.KB_RESISTANCE, modifier);
    }

    public static void tryApplyMoveSpeedModifier(EntityCreature entity, AttributeModifier modifier) {
        tryApplyModifier(entity, SharedMonsterAttributes.MOVEMENT_SPEED, modifier);
    }

    public static void tryRemoveMoveSpeedModifier(EntityCreature entity, AttributeModifier modifier) {
        tryRemoveModifier(entity, SharedMonsterAttributes.MOVEMENT_SPEED, modifier);
    }

    public static void tryApplyMaxHealthModifier(EntityCreature entity, AttributeModifier modifier) {
        tryApplyModifier(entity, SharedMonsterAttributes.MAX_HEALTH, modifier);
    }

    public static void tryRemoveMaxHealthResModifier(EntityCreature entity, AttributeModifier modifier) {
        tryRemoveModifier(entity, SharedMonsterAttributes.MAX_HEALTH, modifier);
    }

    public static void tryApplyFollowRangeModifier(EntityCreature entity, AttributeModifier modifier) {
        tryApplyModifier(entity, SharedMonsterAttributes.FOLLOW_RANGE, modifier);
    }

    public static void tryRemoveFollowRangeResModifier(EntityCreature entity, AttributeModifier modifier) {
        tryRemoveModifier(entity, SharedMonsterAttributes.FOLLOW_RANGE, modifier);
    }

    public static void tryApplyModifier(EntityCreature entity, IAttribute attribute, AttributeModifier modifier) {
        IAttributeInstance attribInst = entity.getEntityAttribute(attribute);
        if( attribInst != null && !attribInst.hasModifier(modifier) ) {
            attribInst.applyModifier(modifier);
        }
    }

    public static void tryRemoveModifier(EntityCreature entity, IAttribute attribute, AttributeModifier modifier) {
        IAttributeInstance attribInst = entity.getEntityAttribute(attribute);
        if( attribInst != null && attribInst.hasModifier(modifier) ) {
            attribInst.removeModifier(modifier);
        }
    }
}
