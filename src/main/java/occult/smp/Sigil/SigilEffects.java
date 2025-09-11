package occult.smp.Sigil;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.entry.RegistryEntry;
import occult.smp.Sigil.SigilType;

import java.util.EnumMap;

public final class SigilEffects {
    private static final EnumMap<SigilType, StatusEffectInstance> EFFECTS = new EnumMap<>(SigilType.class);

    static {
        // Define effects per sigil
        EFFECTS.put(SigilType.OCEAN, new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, 20, 2));
        EFFECTS.put(SigilType.LEAP, new StatusEffectInstance(StatusEffects.SPEED, 20, 1));
        EFFECTS.put(SigilType.EMERALD, new StatusEffectInstance(StatusEffects.HERO_OF_THE_VILLAGE, 20, 5));
        EFFECTS.put(SigilType.ICE, null); // No effect
        // Add more as needed
    }

    public static StatusEffectInstance getEffect(SigilType type) {
        return EFFECTS.get(type);
    }

    public static boolean hasEffect(SigilType type) {
        return EFFECTS.containsKey(type) && EFFECTS.get(type) != null;
    }

    public static RegistryEntry<StatusEffect> getEffectType(SigilType type) {
        StatusEffectInstance instance = getEffect(type);
        return instance != null ? instance.getEffectType() : null;
    }

}
