
package occult.smp.util;

public class CooldownUtil {
    
    public static long secondsToMillis(int seconds) {
        return seconds * 1000L;
    }
    
    public static long minutesToMillis(int minutes) {
        return minutes * 60 * 1000L;
    }
    
    public static boolean isOnCooldown(long cooldownEnd) {
        return cooldownEnd > System.currentTimeMillis();
    }
    
    public static long getRemainingTime(long cooldownEnd) {
        long remaining = cooldownEnd - System.currentTimeMillis();
        return Math.max(0, remaining);
    }
    
    public static float getCooldownPercent(long cooldownEnd, long cooldownDuration) {
        if (cooldownDuration <= 0) return 0.0f;
        
        long remaining = getRemainingTime(cooldownEnd);
        return (float) remaining / (float) cooldownDuration;
    }
}
