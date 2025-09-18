package occult.smp.Sigil;

import occult.smp.Sigil.AbilitySlot.AbilitySlot;

import java.util.HashMap;
import java.util.Map;

/**
 * Client-side state for active sigil and cooldowns.
 * Supports per-slot cooldown tracking.
 * The server is the authority — this only updates when a sync packet arrives.
 */
public class ClientSigilState {

    private static SigilType active = SigilType.NONE;

    // cooldowns[type][slot] = ticks remaining
    private static final Map<SigilType, Map<AbilitySlot, Integer>> cooldowns = new HashMap<>();
    private static final Map<SigilType, Map<AbilitySlot, Integer>> maxCooldowns = new HashMap<>();

    public static SigilType getActive() {
        return active;
    }

    public static void setActive(SigilType type) {
        active = type;
    }

    /**
     * Called when receiving a cooldown sync from the server.
     * Overwrites the local cooldown with the server's authoritative value.
     */
    public static void setCooldown(SigilType type, AbilitySlot slot, int ticks) {
        cooldowns.computeIfAbsent(type, t -> new HashMap<>()).put(slot, ticks);
        maxCooldowns.computeIfAbsent(type, t -> new HashMap<>()).put(slot, ticks);
    }

    /** Remaining cooldown for a given sigil/slot */
    public static int getCooldown(SigilType type, AbilitySlot slot) {
        return cooldowns.getOrDefault(type, Map.of()).getOrDefault(slot, 0);
    }

    /** Max cooldown for a given sigil/slot */
    public static int getMaxCooldown(SigilType type, AbilitySlot slot) {
        return maxCooldowns.getOrDefault(type, Map.of()).getOrDefault(slot, 0);
    }

    /**
     * Returns a value between 0.0 and 1.0 for HUD rendering.
     * 1.0 = full cooldown, 0.0 = ready.
     */
    public static float getCooldownProgress(SigilType type, AbilitySlot slot) {
        int max = getMaxCooldown(type, slot);
        int remaining = getCooldown(type, slot);
        return max > 0 ? (float) remaining / max : 0f;
    }

    /** Decrement all cooldowns once per client tick */
    public static void clientTick() {
        for (Map<AbilitySlot, Integer> slotMap : cooldowns.values()) {
            slotMap.replaceAll((slot, time) -> Math.max(0, time - 1));
        }
    }
}
