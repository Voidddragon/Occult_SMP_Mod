
package occult.smp.Sigil.AbilitySlot;

import occult.smp.Sigil.SigilType;
import occult.smp.Sigil.abilities.*;

import java.util.HashMap;
import java.util.Map;

public class AbilityRegistry {
    private static final Map<SigilType, Ability> ABILITIES = new HashMap<>();
    
    public static void register(SigilType type, Ability ability) {
        if (ABILITIES.containsKey(type)) {
            throw new IllegalStateException("Ability already registered for sigil type: " + type);
        }
        ABILITIES.put(type, ability);
    }
    
    public static Ability getAbility(SigilType type) {
        return ABILITIES.get(type);
    }
    
    public static boolean hasAbility(SigilType type) {
        return ABILITIES.containsKey(type) && type != SigilType.NONE;
    }
}
