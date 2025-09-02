package occult.smp.Sigil.AbilitySlot;

import occult.smp.Sigil.SigilType;

import java.util.EnumMap;
import java.util.Map;

public final class AbilityRegistry {
    private AbilityRegistry() {}

    // Holds abilities for each sigil, keyed by slot
    private static final Map<SigilType, EnumMap<AbilitySlot, Ability>> MAP =
            new EnumMap<>(SigilType.class);

    public static void set(SigilType sigil, Ability primary, Ability secondary) {
        var slots = new EnumMap<AbilitySlot, Ability>(AbilitySlot.class);
        slots.put(AbilitySlot.PRIMARY, primary);
        slots.put(AbilitySlot.SECONDARY, secondary);
        MAP.put(sigil, slots);
    }

    public static Ability get(SigilType sigil, AbilitySlot slot) {
        var slots = MAP.get(sigil);
        return slots != null ? slots.get(slot) : null;
    }
}
