
package occult.smp.util;

import occult.smp.OccultSmp;
import occult.smp.Sigil.SigilType;

public class OccultDebug {
    private static final boolean DEBUG_ENABLED = true;
    
    private static void log(String message) {
        if (DEBUG_ENABLED) {
            OccultSmp.LOGGER.info("[Occult Debug] " + message);
        }
    }
    
    public static void logSigilEquip(String playerName, SigilType sigil, boolean isPrimary) {
        log(String.format("%s equipped %s to %s slot", 
            playerName, sigil, isPrimary ? "primary" : "secondary"));
    }
    
    public static void logSigilSync(String playerName, SigilType primary, SigilType secondary) {
        log(String.format("Synced sigils for %s - Primary: %s, Secondary: %s", 
            playerName, primary, secondary));
    }
    
    public static void logAbilityActivate(String playerName, SigilType sigil, boolean isPrimary) {
        log(String.format("%s activated %s ability (%s slot)", 
            playerName, sigil, isPrimary ? "primary" : "secondary"));
    }
    
    public static void logSigilReturn(String playerName, SigilType primary, SigilType secondary) {
        log(String.format("Returned sigils to %s - Primary: %s, Secondary: %s", 
            playerName, primary, secondary));
    }
    
    public static void logPlayerJoin(String playerName) {
        log(String.format("Player %s joined - syncing data", playerName));
    }
    
    public static void logPlayerLeave(String playerName) {
        log(String.format("Player %s left - returning sigils", playerName));
    }
    
    public static void logNoSigil(String playerName, boolean isPrimary) {
        log(String.format("%s tried to activate %s ability but no sigil equipped", 
            playerName, isPrimary ? "primary" : "secondary"));
    }
    
    public static void logNoAbility(String playerName, SigilType sigil) {
        log(String.format("%s tried to use %s but no ability registered", 
            playerName, sigil));
    }
}
