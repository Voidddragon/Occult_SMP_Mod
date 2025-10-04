
package occult.smp;

import occult.smp.Sigil.SigilType;

public class ClientHudState {
    private static SigilType primarySigil = SigilType.NONE;
    private static SigilType secondarySigil = SigilType.NONE;
    private static long primaryCooldown = 0;
    private static long secondaryCooldown = 0;

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

    public static void setPrimaryCooldown(long cooldown) {
        primaryCooldown = cooldown;
    }

    public static long getPrimaryCooldown() {
        return primaryCooldown;
    }

    public static void setSecondaryCooldown(long cooldown) {
        secondaryCooldown = cooldown;
    }

    public static long getSecondaryCooldown() {
        return secondaryCooldown;
    }

    public static float getPrimaryCooldownPercent() {
        long currentTime = System.currentTimeMillis();
        if (primaryCooldown <= currentTime) {
            return 0.0f;
        }
        return (float) (primaryCooldown - currentTime) / 1000.0f;
    }

    public static float getSecondaryCooldownPercent() {
        long currentTime = System.currentTimeMillis();
        if (secondaryCooldown <= currentTime) {
            return 0.0f;
        }
        return (float) (secondaryCooldown - currentTime) / 1000.0f;
    }

    public static void reset() {
        primarySigil = SigilType.NONE;
        secondarySigil = SigilType.NONE;
        primaryCooldown = 0;
        secondaryCooldown = 0;
    }
}
