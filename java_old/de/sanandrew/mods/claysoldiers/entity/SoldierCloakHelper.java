/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity;

public final class SoldierCloakHelper
{
    public double prevSwingPosX;
    public double prevSwingPosY;
    public double prevSwingPosZ;
    public double swingPosX;
    public double swingPosY;
    public double swingPosZ;

    public void onUpdate(double posX, double posY, double posZ) {
        this.prevSwingPosX = this.swingPosX;
        this.prevSwingPosY = this.swingPosY;
        this.prevSwingPosZ = this.swingPosZ;
        double deltaPosX = posX - this.swingPosX;
        double deltaPosY = posY - this.swingPosY;
        double deltaPosZ = posZ - this.swingPosZ;
        double maxSwing = 10.0D;

        if( deltaPosX > maxSwing ) {
            this.prevSwingPosX = this.swingPosX = posX;
        }

        if( deltaPosZ > maxSwing ) {
            this.prevSwingPosZ = this.swingPosZ = posZ;
        }

        if( deltaPosY > maxSwing ) {
            this.prevSwingPosY = this.swingPosY = posY;
        }

        if( deltaPosX < -maxSwing ) {
            this.prevSwingPosX = this.swingPosX = posX;
        }

        if( deltaPosZ < -maxSwing ) {
            this.prevSwingPosZ = this.swingPosZ = posZ;
        }

        if( deltaPosY < -maxSwing ) {
            this.prevSwingPosY = this.swingPosY = posY;
        }

        this.swingPosX += deltaPosX * 0.25D;
        this.swingPosZ += deltaPosZ * 0.25D;
        this.swingPosY += deltaPosY * 0.25D;
    }
}
