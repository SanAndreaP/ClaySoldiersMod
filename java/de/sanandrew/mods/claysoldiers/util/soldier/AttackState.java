package de.sanandrew.mods.claysoldiers.util.soldier;

/**
 * @author SanAndreas
 * @version 1.0
 *
 * <p>An Enum for determining the attack behavior of the soldier.</p>
 * <code>ALLOW</code> - forces the targeting soldier to take the calling soldier as target<br>
 * <code>DENY</code> - forces the targeting soldier to ignore the calling soldier<br>
 * <code>SKIP</code> - if the called soldier should skip this upgrade for checking
 */

public enum AttackState
{
    ALLOW, DENY, SKIP
}
