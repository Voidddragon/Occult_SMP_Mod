
package occult.smp.util;

import occult.smp.OccultSmp;
import occult.smp.Sigil.SigilType;

/**
 * Centralized debug logging for Occult SMP
 */
public class OccultDebug {
    private static final boolean DEBUG_MODE = true; // Set to false in production
    
    // Player Events
    public static void logPlayerJoin(String playerName) {
        if (DEBUG_MODE) {
            OccultSmp.LOGGER.info("[JOIN] Player {} connected", playerName);
        }
    }
    
    public static void logPlayerLeave(String playerName) {
        if (DEBUG_MODE) {
            OccultSmp.LOGGER.info("[LEAVE] Player {} disconnected", playerName);
        }
    }
    
    // Sigil Events
    public static void logSigilEquip(String playerName, SigilType sigil, boolean isPrimary) {
        if (DEBUG_MODE) {
            String slot = isPrimary ? "PRIMARY" : "SECONDARY";
            OccultSmp.LOGGER.info("[EQUIP] {} equipped {} sigil to {} slot", playerName, sigil, slot);
        }
    }
    
    public static void logSigilReturn(String playerName, SigilType sigil, boolean isPrimary) {
        if (DEBUG_MODE) {
            String slot = isPrimary ? "PRIMARY" : "SECONDARY";
            OccultSmp.LOGGER.info("[RETURN] {} sigil returned to {}'s inventory from {} slot", sigil, playerName, slot);
        }
    }
    
    public static void logSigilSync(String playerName, SigilType primary, SigilType secondary) {
        if (DEBUG_MODE) {
            OccultSmp.LOGGER.info("[SYNC] Syncing sigils to {}: PRIMARY={}, SECONDARY={}", 
                playerName, primary, secondary);
        }
    }
    
    // Ability Events
    public static void logAbilityActivate(String playerName, SigilType sigil, boolean isPrimary) {
        if (DEBUG_MODE) {
            String slot = isPrimary ? "PRIMARY" : "SECONDARY";
            OccultSmp.LOGGER.info("[ABILITY] {} activated {} ability from {} slot", 
                playerName, sigil, slot);
        }
    }
    
    public static void logNoSigil(String playerName, boolean isPrimary) {
        if (DEBUG_MODE) {
            String slot = isPrimary ? "PRIMARY" : "SECONDARY";
            OccultSmp.LOGGER.warn("[ABILITY] {} tried to activate {} ability but no sigil equipped", 
                playerName, slot);
        }
    }
    
    public static void logNoAbility(String playerName, SigilType sigil) {
        if (DEBUG_MODE) {
            OccultSmp.LOGGER.warn("[ABILITY] {} tried to activate {} but no ability registered", 
                playerName, sigil);
        }
    }
    
    public static void logCooldown(String playerName, SigilType sigil, int remainingTicks) {
        if (DEBUG_MODE) {
            OccultSmp.LOGGER.info("[COOLDOWN] {} ability on cooldown for {} ({} ticks)", 
                playerName, sigil, remainingTicks);
        }
    }
    
    // Configuration Events
    public static void logConfigSync(String playerName, String configType) {
        if (DEBUG_MODE) {
            OccultSmp.LOGGER.info("[CONFIG] Syncing {} config to {}", configType, playerName);
        }
    }
    
    public static void logConfigUpdate(String playerName, String configType, String details) {
        if (DEBUG_MODE) {
            OccultSmp.LOGGER.info("[CONFIG] {} updated {} config: {}", playerName, configType, details);
        }
    }
    
    // Network Events
    public static void logPacketSend(String packetType, String playerName) {
        if (DEBUG_MODE) {
            OccultSmp.LOGGER.debug("[NETWORK] Sending {} packet to {}", packetType, playerName);
        }
    }
    
    public static void logPacketReceive(String packetType, String playerName) {
        if (DEBUG_MODE) {
            OccultSmp.LOGGER.debug("[NETWORK] Received {} packet from {}", packetType, playerName);
        }
    }
    
    // Error Logging
    public static void logError(String context, Exception e) {
        OccultSmp.LOGGER.error("[ERROR] {} - {}", context, e.getMessage(), e);
    }
    
    public static void logWarning(String message) {
        OccultSmp.LOGGER.warn("[WARNING] {}", message);
    }
}
