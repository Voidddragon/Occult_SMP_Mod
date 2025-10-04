
package occult.smp.Sigil.AbilitySlot;

import occult.smp.Sigil.SigilType;

import java.util.HashMap;
import java.util.Map;

public class AbilityRegistry {
    private static final Map<SigilType, Map<AbilitySlot, Ability>> ABILITIES = new HashMap<>();
    
    static {
        // Initialize ability maps for each sigil type
        for (SigilType type : SigilType.values()) {
            ABILITIES.put(type, new HashMap<>());
        }
    }
    
    public static void register(SigilType sigilType, AbilitySlot slot, Ability ability) {
        ABILITIES.get(sigilType).put(slot, ability);
    }
    
    public static Ability getAbility(SigilType sigilType, AbilitySlot slot) {
        return ABILITIES.get(sigilType).get(slot);
    }
    
    public static boolean hasAbility(SigilType sigilType, AbilitySlot slot) {
        return ABILITIES.get(sigilType).containsKey(slot);
    }
    
    public static void clear() {
        ABILITIES.values().forEach(Map::clear);
    }
}
