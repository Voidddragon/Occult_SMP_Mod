
package occult.smp;

import occult.smp.Sigil.SigilType;

/**
 * Client-side state for HUD display
 */
public class ClientHudState {
    private static SigilType primarySigil = SigilType.NONE;
    private static SigilType secondarySigil = SigilType.NONE;
    private static int primaryCooldown = 0;
    private static int secondaryCooldown = 0;
    
    public static SigilType getPrimarySigil() {
        return primarySigil;
    }
    
    public static void setPrimarySigil(SigilType sigil) {
        primarySigil = sigil;
    }
    
    public static SigilType getSecondarySigil() {
        return secondarySigil;
    }
    
    public static void setSecondarySigil(SigilType sigil) {
        secondarySigil = sigil;
    }
    
    public static int getPrimaryCooldown() {
        return primaryCooldown;
    }
    
    public static void setPrimaryCooldown(int cooldown) {
        primaryCooldown = cooldown;
    }
    
    public static int getSecondaryCooldown() {
        return secondaryCooldown;
    }
    
    public static void setSecondaryCooldown(int cooldown) {
        secondaryCooldown = cooldown;
    }
    
    public static void setBothSigils(SigilType primary, SigilType secondary) {
        primarySigil = primary;
        secondarySigil = secondary;
    }
    
    public static void reset() {
        primarySigil = SigilType.NONE;
        secondarySigil = SigilType.NONE;
        primaryCooldown = 0;
        secondaryCooldown = 0;
    }
}
