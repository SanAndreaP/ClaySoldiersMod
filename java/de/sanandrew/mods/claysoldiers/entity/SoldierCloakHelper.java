/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity;

public final class SoldierCloakHelper
{
    public double field_71091_bM;
    public double field_71096_bN;
    public double field_71097_bO;
    public double field_71094_bP;
    public double field_71095_bQ;
    public double field_71085_bR;

    public void onUpdate(double posX, double posY, double posZ) {
        this.field_71091_bM = this.field_71094_bP;
        this.field_71096_bN = this.field_71095_bQ;
        this.field_71097_bO = this.field_71085_bR;
        double d3 = posX - this.field_71094_bP;
        double d0 = posY - this.field_71095_bQ;
        double d1 = posZ - this.field_71085_bR;
        double d2 = 10.0D;

        if( d3 > d2 ) {
            this.field_71091_bM = this.field_71094_bP = posX;
        }

        if( d1 > d2 ) {
            this.field_71097_bO = this.field_71085_bR = posZ;
        }

        if( d0 > d2 ) {
            this.field_71096_bN = this.field_71095_bQ = posY;
        }

        if( d3 < -d2 ) {
            this.field_71091_bM = this.field_71094_bP = posX;
        }

        if( d1 < -d2 ) {
            this.field_71097_bO = this.field_71085_bR = posZ;
        }

        if( d0 < -d2 ) {
            this.field_71096_bN = this.field_71095_bQ = posY;
        }

        this.field_71094_bP += d3 * 0.25D;
        this.field_71085_bR += d1 * 0.25D;
        this.field_71095_bQ += d0 * 0.25D;
    }
}
