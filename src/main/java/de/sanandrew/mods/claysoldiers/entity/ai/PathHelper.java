/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.ai;

import de.sanandrew.mods.sanlib.lib.util.EntityUtils;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIWander;

public final class PathHelper
{
    private static final int MAX_TICK_MOTIONLESS = 100;

    private final EntityLiving entity;

    private int lastMotionTickstamp = 0;

    public PathHelper(EntityLiving entity) {
        this.entity = entity;
    }

    public void update() {
        double motionSum = this.entity.motionX + this.entity.motionZ;
        if( motionSum > 0.01D || motionSum < -0.01D ) {
            this.lastMotionTickstamp = this.entity.ticksExisted;
        } else if( this.entity.ticksExisted - this.lastMotionTickstamp > MAX_TICK_MOTIONLESS ) {
            this.entity.getNavigator().clearPath();
            EntityAIWander wanderAI = EntityUtils.getAisFromTaskList(this.entity.tasks.taskEntries, EntityAIWander.class).stream().findFirst().orElse(null);
            if( wanderAI != null ) {
                if( wanderAI.shouldExecute() ) {
                    wanderAI.startExecuting();
                }
            }
        }
    }
}
