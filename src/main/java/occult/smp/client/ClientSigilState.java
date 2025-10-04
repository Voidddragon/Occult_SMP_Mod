
package occult.smp.client;

import occult.smp.Sigil.SigilType;

import java.util.HashMap;
import java.util.Map;

public class ClientSigilState {
    private static SigilType primarySigil = SigilType.NONE;
    private static SigilType secondarySigil = SigilType.NONE;
    private static final Map<String, Long> cooldowns = new HashMap<>();
    private static final Map<String, Long> maxCooldowns = new HashMap<>();

    // Sigil Management
    public static void setPrimarySigil(SigilType type) {
        primarySigil = type;
    }

    public static SigilType getPrimarySigil() {
        return primarySigil;
    }

    public static void setSecondarySigil(SigilType type) {
        secondarySigil = type;
    }

    public static SigilType getSecondarySigil() {
        return secondarySigil;
    }

    // Cooldown Management
    public static void setCooldown(String key, long cooldown, long maxCooldown) {
        cooldowns.put(key, cooldown);
        maxCooldowns.put(key, maxCooldown);
    }

    public static long getCooldown(String key) {
        return cooldowns.getOrDefault(key, 0L);
    }

    public static long getMaxCooldown(String key) {
        return maxCooldowns.getOrDefault(key, 0L);
    }

    public static float getCooldownPercent(String key) {
        long cooldown = getCooldown(key);
        long maxCooldown = getMaxCooldown(key);
        
        if (maxCooldown <= 0 || cooldown <= 0) {
            return 0.0f;
        }
        
        return Math.min(1.0f, (float) cooldown / (float) maxCooldown);
    }

    public static boolean isOnCooldown(String key) {
        return getCooldown(key) > 0;
    }

    public static void tickCooldowns() {
        cooldowns.replaceAll((key, value) -> Math.max(0, value - 1));
    }

    public static void clear() {
        primarySigil = SigilType.NONE;
        secondarySigil = SigilType.NONE;
        cooldowns.clear();
        maxCooldowns.clear();
    }
}
