
package occult.smp.Sigil.AbilitySlot;

import occult.smp.OccultSmp;
import occult.smp.Sigil.SigilType;

import java.util.HashMap;
import java.util.Map;

/**
 * Registry for mapping sigil types to their abilities
 */
public class AbilityRegistry {
    private static final Map<SigilType, Ability> ABILITIES = new HashMap<>();
    
    /**
     * Register an ability for a sigil type
     */
    public static void register(SigilType type, Ability ability) {
        if (ABILITIES.containsKey(type)) {
            OccultSmp.LOGGER.warn("Overwriting ability for sigil type: {}", type);
        }
        ABILITIES.put(type, ability);
        OccultSmp.LOGGER.info("Registered ability for sigil type: {}", type);
    }
    
    /**
     * Get the ability for a sigil type
     */
    public static Ability getAbility(SigilType type) {
        return ABILITIES.get(type);
    }
    
    /**
     * Check if a sigil type has an ability registered
     */
    public static boolean hasAbility(SigilType type) {
        return ABILITIES.containsKey(type);
    }
    
    /**
     * Clear all registered abilities (for testing)
     */
    public static void clear() {
        ABILITIES.clear();
    }
}
