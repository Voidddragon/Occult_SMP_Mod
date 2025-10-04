
package occult.smp.Sigil;

import occult.smp.Sigil.AbilitySlot.AbilitySlot;

import java.util.HashMap;
import java.util.Map;

public class ClientSigilState {
    private static SigilType primarySigil = SigilType.NONE;
    private static SigilType secondarySigil = SigilType.NONE;
    private static final Map<AbilitySlot, Integer> cooldowns = new HashMap<>();
    private static final Map<AbilitySlot, Integer> maxCooldowns = new HashMap<>();

    public static SigilType getPrimarySigil() {
        return primarySigil;
    }

    public static SigilType getSecondarySigil() {
        return secondarySigil;
    }

    public static void setPrimarySigil(SigilType sigil) {
        primarySigil = sigil;
    }

    public static void setSecondarySigil(SigilType sigil) {
        secondarySigil = sigil;
    }

    public static void setCooldown(AbilitySlot slot, int ticks, int maxTicks) {
        cooldowns.put(slot, ticks);
        maxCooldowns.put(slot, maxTicks);
    }

    public static int getCooldown(AbilitySlot slot) {
        return cooldowns.getOrDefault(slot, 0);
    }

    public static int getMaxCooldown(AbilitySlot slot) {
        return maxCooldowns.getOrDefault(slot, 0);
    }

    public static void clear() {
        primarySigil = SigilType.NONE;
        secondarySigil = SigilType.NONE;
        cooldowns.clear();
        maxCooldowns.clear();
    }

    public static void clientTick() {
        // Tick down cooldowns on client side
        cooldowns.replaceAll((slot, time) -> Math.max(0, time - 1));
    }
}
